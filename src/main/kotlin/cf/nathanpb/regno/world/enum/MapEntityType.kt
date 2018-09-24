package cf.nathanpb.regno.world.enum

import cf.nathanpb.regno.world.entities.ProvinceMarker
import cf.nathanpb.regno.world.entities.Town
import kotlin.reflect.KClass

/*
 * Created by NathanPB
 * at 14/09/2018
 * on regnoworld
 */

enum class MapEntityType(val id : String, val clazz : KClass<*>) {
    TOWN("town", Town::class),
    PROVINCE("province", ProvinceMarker::class)
}