package sexy.criss.multiarrows.handler

import org.bukkit.*
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import sexy.criss.multiarrows.MultiArrows
import sexy.criss.multiarrows.utility.Utils

class ShootingHandle : Listener {

    @EventHandler fun onShooting(event: EntityShootBowEvent) {
        if(event.entity !is Player) return
        val p = event.entity as Player
        val bow = event.bow
        val name = MultiArrows.instance!!.shootData!!.getName()
        val lore = MultiArrows.instance!!.shootData!!.getLore()
        val arrows_size = if(p.gameMode == GameMode.CREATIVE) MultiArrows.instance!!.shootData!!.getArrowsSize()
        else if (getArrowsSize(p.inventory) > MultiArrows.instance!!.shootData!!.getArrowsSize()) MultiArrows
                .instance!!.shootData!!.getArrowsSize() else if(getArrowsSize(p.inventory) < 3) 0 else getArrowsSize(p.inventory)

        val variable =
                (bow.itemMeta != null) && (bow.itemMeta.hasDisplayName() && Utils.f(bow.itemMeta.displayName) ==
                        Utils.f(name))
                        && (bow.itemMeta.hasLore() && Utils.f(bow.itemMeta.lore) == Utils.f(lore!!))
        if(!variable) return
        event.isCancelled = true

        if(MultiArrows.instance!!.shootData!!.isPermissionMode())
            if(!(p.hasPermission(MultiArrows.instance!!.shootData!!.getPermission()))) {
                p.sendMessage(Utils.f(MultiArrows.instance!!.shootData!!.getPermissionMessage()))
                return
            }

        if(p.gameMode != GameMode.CREATIVE) {
            if(arrows_size == 0) {
                p.sendMessage(Utils.f(MultiArrows.instance!!.shootData!!.getArrowsNotFoundMessage()))
                return
            }

            takeArrows(arrows_size, p.inventory)
        }

        val size = Integer.parseInt((arrows_size / 2).toString())

        val startVec = event.projectile.velocity

        var xVec = startVec
        var zVec = startVec

        shoot(event, startVec)
        var x = 0
        while (x < size) {
            xVec = Vector(xVec.x - (xVec.z / arrows_size), xVec.y, xVec.z + (xVec.x / arrows_size))
            zVec = Vector(zVec.x + (zVec.z / arrows_size), zVec.y, zVec.z - (zVec.x / arrows_size))

            shoot(event, xVec)
            shoot(event, zVec)
            x++
        }

    }

    private fun shoot(event: EntityShootBowEvent, vec: Vector) {
        if(event.entity is Player) (event.entity as Player).launchProjectile(Arrow::class.java, vec)
    }

    private fun getArrowsSize(inventory: Inventory): Int {

        var i = 0
        while (i < inventory.contents.size) {
            val stack = inventory.contents[i]
            if(stack != null && stack.type == Material.ARROW) return stack.amount
            i++
        }

        return 0
    }

    private fun takeArrows(size: Int, inventory: Inventory) {
        var i = 0
        while (i < inventory.contents.size) {
            var stack = inventory.contents[i]
            if(stack != null && stack.type == Material.ARROW) {
                if(stack.amount >= size) {
                    stack.amount = stack.amount - size
                    inventory.contents[i] = stack
                } else {
                    stack = ItemStack(Material.AIR)
                    inventory.contents[i] = stack
                }
            }
            i++
        }
    }
}