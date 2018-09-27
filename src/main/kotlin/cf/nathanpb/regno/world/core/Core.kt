package cf.nathanpb.regno.world.core

import cf.nathanpb.regno.world.hero.items.HeroItemType
import cf.nathanpb.regno.world.troops.Troop
import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import org.apache.log4j.Logger
import org.json.JSONObject
import java.io.File

/*
 * Created by NathanPB
 * at 17/09/2018
 * on regnoworld
 */

class Core {
    companion object {
        val logger = Logger.getLogger(Core::class.java)
        val version = "0.1 SNAPSHOT"
        val initTime = System.currentTimeMillis()
        var ready = false

        var config: JSONObject = JSONObject()
        val configFile = File("config.json")

        var mongoClient : MongoClient? = null
        var db : MongoDatabase? = null
        val troops = ArrayList<Troop>()
        val heroItems = ArrayList<HeroItemType>()
    }
}