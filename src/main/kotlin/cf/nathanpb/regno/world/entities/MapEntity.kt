package cf.nathanpb.regno.world.entities

import cf.nathanpb.regno.world.enum.MapEntityType
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.bson.types.ObjectId

open abstract class MapEntity(override val id : String) : IDatabaseEntity {
    companion object {
        //todo find the collection from database
        //val col = null as MongoCollection<Document>
    }
    constructor(x : Int, y : Int) : this(String.format("%03d", x) + String.format("%03d", y))

    init {
        if(col().countDocuments(Document().append("_id", ObjectId(id))) < 1){
            col().insertOne(Document("_id", id))
        }
    }

    abstract val type : MapEntityType
    val x : Int
        get() = find().getObjectId("_id").toString().toInt().toString().substring(0, 2).toInt()
    val y : Int
        get() = find().getObjectId("_id").toString().toInt().toString().substring(3, 6).toInt()

    override fun col(): MongoCollection<Document> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}