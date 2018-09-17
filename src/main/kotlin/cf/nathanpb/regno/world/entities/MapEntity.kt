package cf.nathanpb.regno.world.entities

import cf.nathanpb.regno.world.enum.MapEntityType
import cf.nathanpb.regno.world.exceptions.InvalidInstantiationException
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.bson.types.ObjectId
import java.lang.RuntimeException

open abstract class MapEntity(val query : Document) : IDatabaseEntity {
    companion object {
        //todo find the collection from database
        val col = null as MongoCollection<Document>

        val all : ArrayList<MapEntity>
            get() {
                return col.find()
                    . map {
                        MapEntityType.values()
                            .filter { type -> type.id == it.getInteger("type") }
                            .firstOrNull()
                            ?.clazz?.constructors?.filter { c ->
                                c.typeParameters.any{t -> t is Document}
                            }?.firstOrNull()?.call(it.getInteger("x"), it.getInteger("y"))
                    }.filter { it != null } as ArrayList<MapEntity>
            }
    }
    constructor(x : Int, y : Int) : this(Document().append("x", x).append("y", y))
    constructor(id : String) : this(Document().append("_id", id))
    override val id: ObjectId =  if(col().countDocuments(query) < 1){
        if(query.containsKey("_id")){
            col().insertOne(query)
            query.getObjectId("_id")
        } else if(query.containsKey("x") && query.containsKey("y")){
            col().insertOne(query)
            col().find(query).first().getObjectId("_id")
        } else {
            throw InvalidInstantiationException("MapEntity cannot be instantiated with no _id or (x, y) properties")
        }
    } else {
        col().find(query).first().getObjectId("_id")
    }


    init {
       //todo initialize default properties
    }

    abstract val type : MapEntityType
    val x : Int
        get() = find().getInteger("x")
    val y : Int
        get() = find().getInteger("y")

    override fun col(): MongoCollection<Document> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}