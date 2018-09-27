package cf.nathanpb.regno.world.entities

import cf.nathanpb.regno.world.core.Core
import cf.nathanpb.regno.world.hero.Hero
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.bson.types.ObjectId

class Player(query : Document)  : IDatabaseEntity {
    companion object {
        val col = Core.db?.getCollection("players") as MongoCollection
    }

    override val id = if(query.containsKey("_id")){
        query.getObjectId("_id")
    } else {
        if(col.countDocuments(query) < 1){
            col().insertOne(query)
        }
        col().find(query).first().getObjectId("_id")
    }

    constructor(id : ObjectId) : this(Document().append("_id", id))
    constructor(nickname : String) : this(Document().append("nickname", nickname))

    val hero = Hero(this)





    override fun col() = col

}