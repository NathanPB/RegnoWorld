package cf.nathanpb.regno.world.entities

import cf.nathanpb.regno.world.core.Core
import cf.nathanpb.regno.world.exceptions.InvalidInstantiationException
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.bson.types.ObjectId

class Army(val query : Document) : IDatabaseEntity {

    companion object {
        val col = Core.db?.getCollection("army") as MongoCollection
    }

    constructor() : this({
        val tempkey = Math.random()
        col.insertOne(Document().append("tempkey", tempkey))
        val q = Document().append("_id", col.find(Document().append("tempkey", tempkey)).first().getObjectId("_key"))
        col.updateOne(q, Document().append("\$unset", Document().append("tempkey", tempkey)))
        q
    }())
    override val id: ObjectId =  if(col().countDocuments(query) < 1){
        if(query.containsKey("_id")){
            col().insertOne(query)
            query.getObjectId("_id")
        } else if(query.containsKey("x") && query.containsKey("y")){
            col().insertOne(query)
            col().find(query).first().getObjectId("_id")
        } else {
            throw InvalidInstantiationException("Army cannot be instantiated with no _id")
        }
    } else {
        col().find(query).first().getObjectId("_id")
    }

    var belongsTo : Town?
        get() = Town(Document().append("_id", find().getObjectId("belongs_to")))
        set(it) { update(Document().append("\$set", Document("belongs_to", it?.id))) }

    override fun col() = col

}