package cf.nathanpb.regno.world.enum

import cf.nathanpb.regno.world.entities.Province
import cf.nathanpb.regno.world.entities.Town
import kotlin.reflect.KClass

/*
 * Created by NathanPB
 * at 14/09/2018
 * on regnoworld
 */

enum class MapEntityType(val id : Int, val clazz : KClass<*>) {
    TOWN(0, Town::class),
    PROVINCE(1, Province::class)
}