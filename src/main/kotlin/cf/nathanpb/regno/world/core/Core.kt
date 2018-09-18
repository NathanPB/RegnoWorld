package cf.nathanpb.regno.world.core

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import org.apache.log4j.Logger
import org.apache.log4j.spi.LoggerFactory
import org.json.JSONObject

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
        var mongoClient : MongoClient? = null
        var db : MongoDatabase? = null
    }
}