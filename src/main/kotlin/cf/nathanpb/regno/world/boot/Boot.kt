package cf.nathanpb.regno.world.boot

import cf.nathanpb.regno.world.core.Core
import cf.nathanpb.regno.world.entities.MapEntity
import cf.nathanpb.regno.world.entities.ProvinceMarker
import cf.nathanpb.regno.world.entities.Town
import cf.nathanpb.regno.world.troops.Troop
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.mongodb.MongoClient
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoDatabase
import org.json.JSONObject
import java.io.*
import java.util.*
import java.util.concurrent.TimeUnit

/*
 * Created by NathanPB
 * at 17/09/2018
 * on regnoworld
 */

class Boot {

    private val phases = arrayListOf(
            Phase("Loading Configurations"){
                val file = Core.configFile
                if(!file.exists()) {
                    file.createNewFile()
                    JSONObject().writeTo(file)
                }
                val fr = FileReader(file)

                Core.config = JSONObject(fr.readText())
                fr.close()

                if(!Core.config.has("db_name")){
                    Core.config.put("db_name", "regno_world")
                }
                if(!Core.config.has("db_port")){
                    Core.config.put("db_port", 27017)
                }
                if(!Core.config.has("db_user")){
                    Core.config.put("db_user", "admin")
                }
                if(!Core.config.has("db_pwd")){
                    Core.config.put("db_pwd", "admin")
                }
                if(!Core.config.has("db_host")){
                    Core.config.put("db_host", "localhost")
                }
                if(!Core.config.has("world_id")){
                    Core.config.put("world_id", "RegnoWorld")
                }
                if(!Core.config.has("world_display")){
                    Core.config.put("world_display", "Regno World")
                }
                if(!Core.config.has("troops")){
                    Core.config.put("troops", JSONObject())
                }
            },
            Phase("Registering Troops"){

                //Register Troops
                Core.troops +
                        Troop("spearman") +
                        Troop("swordman") +
                        Troop("archer") +
                        Troop("beserker")

                //Check for troops configurations on config file
                val troops = Core.config.getJSONObject("troops")
                for(from in Core.troops.map{it.id}){
                    if(!troops.has(from)){
                        troops.put(from, JSONObject())
                    }
                    if(!troops.getJSONObject(from).has("cost")){
                        troops.getJSONObject(from).put("cost", JSONObject())
                    }
                    if(!troops.getJSONObject(from).has("speed")){
                        troops.getJSONObject(from).put("speed", 0)
                    }
                    if(!troops.getJSONObject(from).has("enable")){
                        troops.getJSONObject(from).put("enable", true)
                    }

                    val obj = troops.getJSONObject(from).getJSONObject("cost")
                    for(mode in arrayOf("wood", "clay", "iron", "food", "provisions")){
                        if(!obj.has(mode)){
                            obj.put(mode, 0)
                        }
                    }
                    for (mode in arrayOf("def", "atk")){
                        if(!troops.getJSONObject(from).has(mode)){
                            troops.getJSONObject(from).put(mode, JSONObject())
                        }
                        for(against in Core.troops.map{it.id}){
                            if(!troops.getJSONObject(from).getJSONObject(mode).has(against)){
                                troops.getJSONObject(from).getJSONObject(mode).put(against, 0)
                            }
                        }
                    }
                }
                Core.config.writeTo(Core.configFile)
            },
            Phase("Connecting to Database"){
                Core.mongoClient = MongoClient(
                        ServerAddress(Core.config.getString("db_host"), Core.config.getInt("db_port")),
                        Arrays.asList(MongoCredential.createCredential(
                                Core.config.getString("db_user"),
                                Core.config.getString("db_name"),
                                Core.config.getString("db_pwd").toCharArray()
                        ))
                )
                Core.db = Core.mongoClient?.getDatabase(Core.config.getString("db_name"))
            },
            Phase("Checking Database"){
                if(Core.db != null){
                    val db = Core.db as MongoDatabase
                    if(!db.hasCollection("map")){
                        Core.logger.warn("Collection 'map' doesn't exists! Creating one...")
                        db.createCollection("map")
                    }
                }
            }
    )

    var core : Core? = null

    fun start(){
        core = Core()
        Core.logger.info("RegnoWorld ${Core.version} is starting!")
        Core.logger.info(
                "\n" +
                        "______                        \n" +
                        "| ___ \\                       \n" +
                        "| |_/ /___  __ _ _ __   ___   \n" +
                        "|    // _ \\/ _` | '_ \\ / _ \\  \n" +
                        "| |\\ \\  __/ (_| | | | | (_) | \n" +
                        "\\_| \\_\\___|\\__, |_| |_|\\___/  \n" +
                        "            __/ |             \n" +
                        "           |___/              \n" +
                        "By NathanPB - https://github.com/NathanPB/RegnoWorld"
        )
        var count = 1

        for(phase in phases){
            val time = System.currentTimeMillis()
            Core.logger.info("["+count+"/"+phases.size+"] " + phase.getDisplay())
            phase.start()
            Core.logger.info("["+count+"/"+phases.size+"] Done in ${time.timeSince()}")
            count++
        }

        Core.logger.info("Regno World '${Core.config.getString("world_display")} | ${Core.config.getString("world_id")}' is Done! ${Core.initTime.timeSince()}")
        Core.logger.info("Loaded ${MapEntity.all.size} map entities (${Town.all.size} Towns | ${ProvinceMarker.all.size} Province Markers)")
        Core.ready = true
    }


    /*
     extension shit
     */

    fun MongoDatabase.hasCollection(name : String) : Boolean {
        return this.listCollectionNames().contains(name)
    }

    fun Long.timeSince() : String {
        val time = System.currentTimeMillis() - this;
        if(time < 1000){
            return time.toString()+"ms"
        } else if(time < 60000){
            return TimeUnit.MILLISECONDS.toSeconds(time).toString()+"sec"
        } else {
            return TimeUnit.MILLISECONDS.toMinutes(time).toString()+"min"
        }
    }

    fun JSONObject.writeTo(file : File){
        val str = GsonBuilder().setPrettyPrinting().create().toJson(JsonParser().parse(this.toString()).asJsonObject)
        val fw = FileWriter(file)
        fw.write(str)
        fw.close()
    }
}

fun main(args : Array<String>){
    Boot().start()
}