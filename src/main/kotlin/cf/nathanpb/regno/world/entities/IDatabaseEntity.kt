package cf.nathanpb.regno.world.entities

import com.mongodb.client.MongoCollection
import org.bson.Document
import org.bson.types.ObjectId

interface IDatabaseEntity {
    fun col() : MongoCollection<Document>
    val id : ObjectId

    fun find() = col().find(Document().append("_id", id)).first()
    fun update(doc : Document) = col().updateOne(Document().append("id", id), doc)
}