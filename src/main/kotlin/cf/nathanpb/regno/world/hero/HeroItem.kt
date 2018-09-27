package cf.nathanpb.regno.world.hero

import cf.nathanpb.regno.world.core.Core
import cf.nathanpb.regno.world.hero.items.HeroItemType
import org.bson.Document

data class HeroItem (val type : HeroItemType, val level : Int = 1, val slot : Int? = null){
    constructor(doc : Document) : this(
            Core.heroItems.filter { it.id == doc.getString("type") }.first(),
            doc.getInteger("level"),
            doc.getInteger("slot")
    )
}