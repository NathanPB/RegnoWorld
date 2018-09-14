package cf.nathanpb.regno.world.entities

import cf.nathanpb.regno.world.enum.MapEntityType

/*
 * Created by NathanPB
 * at 14/09/2018
 * on regnoworld
 */

class Province(x : Int, y : Int) : MapEntity(x, y) {
    constructor(id : String) : this(id.substring(0, 2).toInt(), id.substring(3, 6).toInt())

    override val type = MapEntityType.PROVINCE
}