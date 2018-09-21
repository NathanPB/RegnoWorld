package cf.nathanpb.regno.world.troops

import cf.nathanpb.regno.world.core.Core
import java.lang.Exception

/*
 * Created by NathanPB
 * at 21/09/2018
 * on regnoworld
 */

abstract class TroopType(val id : String) {
    val config = Core.config.getJSONObject("troops").getJSONObject(id)

    val enabled : Boolean
        get() = config.getBoolean("enable")

    val speed : Int
        get() = config.getInt("speed")

    val provisionsTaken : Int
        get() = config.getJSONObject("cost").getInt("provisions")

    val foodTaken : Int
        get() = config.getJSONObject("cost").getInt("food")

    val ironTaken : Int
        get() = config.getJSONObject("cost").getInt("iron")
    val clayTaken : Int
        get() = config.getJSONObject("cost").getInt("clay")
    val woodTaken : Int
        get() = config.getJSONObject("cost").getInt("wood")

    fun def(type : TroopType) : Int {
        return try{
            config.getJSONObject("def")
                .getInt(type.id)
        } catch (ex : Exception) {
            0
        }
    }

    fun atk(type : TroopType) : Int {
        return try{
            config.getJSONObject("atk")
                .getInt(type.id)
        } catch (ex : Exception) {
            0
        }
    }

}