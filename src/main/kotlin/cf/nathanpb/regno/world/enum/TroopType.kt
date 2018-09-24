package cf.nathanpb.regno.world.enum

import cf.nathanpb.regno.world.troops.Troop

enum class TroopType(val instance : Troop) {
    SPEARMAN(Troop("spearman")),
    SWORDMAN(Troop("swordman")),
    ARCHER(Troop("archer"))
}