package sexy.criss.multiarrows.utility

import com.google.common.collect.Lists
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import sexy.criss.multiarrows.MultiArrows

object Utils {

    fun f(str: String): String {
        return ChatColor.translateAlternateColorCodes('&', str)
    }

    fun f(list: List<String>): List<String> {
        val out = Lists.newArrayList<String>()
        var a = 0
        while (a < list.size) {
            out.add(f(list[a]))
            a++
        }
        return out
    }

    fun printLogo() {
        val array = Utils.f(Lists.newArrayList(
                "&8========================================",
                "&7Plugin: &eMultiArrows",
                "&7Author: &ed3ever",
                "&7Version: &e${MultiArrows.instance!!.description.version}",
                "",
                "&7Bug report: &chttps://vk.com/please_kill_society",
                "&8========================================"
        ))

        array.forEach(Bukkit.getConsoleSender()::sendMessage)
    }

}