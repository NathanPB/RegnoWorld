package cf.nathanpb.regno.world.entities

import cf.nathanpb.regno.world.enum.MapEntityType
import cf.nathanpb.regno.world.utils.BoundArray
import org.bson.Document
import org.bson.types.ObjectId

/*
 * Created by NathanPB
 * at 17/09/2018
 * on regnoworld
 */

class Town constructor(query : Document) : MapEntity(query){
    companion object {
        val all : ArrayList<Town>
            get() = MapEntity.all.filter { it is Town } as ArrayList<Town>
    }

    constructor(id : String) : this(Document().append("_id", ObjectId(id)))
    constructor(x : Int, y : Int) : this(Document().append("x", x).append("y", y))

    override val type = MapEntityType.TOWN

    val nativeArmy : Army
        get() {
            var army = Army(Document().append("_id", find().get("armies", Document::class.java).getObjectId("native")))
            if(army == null){
                army = Army()
                update(Document().append("\$set", Document()))
            }
            return army
        }

    val supportArmy = BoundArray(
        {
            update(Document().append("\$push", Document().append("armies", Document().append("support", it.id))))
        },
        {
            update(Document().append("\$pull", Document().append("armies", Document().append("support", it.id))))
        },
        {
            (find().get("armies", Document::class.java).get("support") as List<Document>)
                    .map { Army(Document("_id", it.getObjectId("_id"))) }
                    .toTypedArray()
        }
    )
}