package sexy.criss.multiarrows.data

import com.google.common.collect.Lists
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import sexy.criss.multiarrows.MultiArrows
import java.io.FileReader

class ShootData  {

    private var bow_name = ""
    private var bow_lore: MutableList<String>? = null
    private var bow_arrow_size = 0
    private var permissionMode = false
    private var default_permission = ""
    private var permission_message = ""
    private var arrows_not_found_message = ""

    fun initialize() {

        val bow_name = getValue("bow", "name") as String
        val bow_lore = Lists.newArrayList<String>()
        val bow_arrow_size = getValue("bow", "arrows_size")
        val permissionMode = getValue("bow", "permissionMode")
        val default_permission = getValue("bow", "default_permission") as String
        val permission_message = getValue("messages", "permissionsError") as String
        val arrows_not_found_message = getValue("messages", "arrows_not_found_message") as String


        var i = 0
        while (i < (getValue("bow", "lore") as JSONArray).size) {
            bow_lore.add((getValue("bow", "lore") as JSONArray)[i] as String)
            i++
        }


        this.bow_name = bow_name
        this.bow_lore = bow_lore
        this.bow_arrow_size = Integer.parseInt(bow_arrow_size.toString())
        this.permissionMode = permissionMode.toString().toBoolean()
        this.default_permission = default_permission
        this.permission_message = permission_message
        this.arrows_not_found_message = arrows_not_found_message
    }

    fun getName(): String {
        return bow_name
    }

    fun getLore(): MutableList<String>? {
        return bow_lore
    }

    fun getArrowsSize(): Int {
        return bow_arrow_size
    }

    fun isPermissionMode(): Boolean {
        return permissionMode
    }

    fun getPermission(): String {
        return default_permission
    }

    fun getPermissionMessage(): String {
        return permission_message
    }

    fun getArrowsNotFoundMessage(): String {
        return arrows_not_found_message
    }

    private fun getValue(path: String, key: String): Any {
        var out: Any? = null
        val parser = JSONParser()
        val file = MultiArrows.instance!!.settings
        try {
            val jsonObject = parser.parse(FileReader(file)) as JSONObject
            out = (jsonObject[path] as JSONObject)[key] as Any
        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
        return out!!
    }

}