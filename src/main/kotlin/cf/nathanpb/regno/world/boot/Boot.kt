package cf.nathanpb.regno.world.boot

import cf.nathanpb.regno.world.core.Core
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
                val file = File("config.json")
                if(!file.exists()) {
                    file.createNewFile()
                    val fos = FileOutputStream(file)
                    val ps = PrintStream(fos)
                    ps.print("{}")
                    fos.close()
                    ps.close()
                }
                val fr = FileReader(file)
                Core.config = JSONObject(fr.readText())
                fr.close()
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

        Core.ready = true
        Core.logger.info("Regno World is Done! ${Core.initTime.timeSince()}")
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
}

fun main(args : Array<String>){
    Boot().start()
}