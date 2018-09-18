package cf.nathanpb.regno.world.entities

import cf.nathanpb.regno.world.enum.MapEntityType
import org.bson.Document
import org.bson.types.ObjectId

/*
 * Created by NathanPB
 * at 14/09/2018
 * on regnoworld
 */

class ProvinceMarker(query : Document) : MapEntity(query) {
    companion object {
        val all : ArrayList<ProvinceMarker>
            get() = MapEntity.all.filter { it is ProvinceMarker } as ArrayList<ProvinceMarker>
    }

    constructor(id : String) : this(Document().append("_id", ObjectId(id)))
    constructor(x : Int, y : Int) : this(Document().append("x", x).append("y", y))

    override val type = MapEntityType.PROVINCE
}