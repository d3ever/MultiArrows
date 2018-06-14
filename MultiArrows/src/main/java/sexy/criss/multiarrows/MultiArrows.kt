package sexy.criss.multiarrows

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import sexy.criss.multiarrows.data.ShootData
import sexy.criss.multiarrows.handler.ShootingHandle
import sexy.criss.multiarrows.utility.Utils
import java.io.File

open class MultiArrows : JavaPlugin() {
    var shootData: ShootData? = null
    var settings: File? = null

    override fun onEnable() {
        instance = this

        if(!dataFolder.exists()) dataFolder.mkdirs()
        if(!File(dataFolder, "settings.json").exists()) saveResource("settings.json", false)

        settings = File(dataFolder, "settings.json")
        shootData = ShootData()


        shootData!!.initialize()

        Bukkit.getPluginManager().registerEvents(ShootingHandle(), this)

        Utils.printLogo()
    }

    override fun onDisable() {
        Utils.printLogo()
    }

    companion object {
        var instance: MultiArrows? = null
    }

}