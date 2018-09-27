package cf.nathanpb.regno.world.hero

import cf.nathanpb.regno.world.entities.Player
import cf.nathanpb.regno.world.utils.BoundMap
import org.bson.Document

data class Hero(val owner : Player){
    val inventory = BoundMap(
            {   k, v ->
                owner.update(
                        Document().append("\$set",
                                Document().append("hero",
                                        Document().append("inventory",
                                                Document().append(k.toString(), v)
                                        )
                                )
                        )
                )
            },
            {
                owner.update(
                        Document().append("\$usnet",
                                Document().append("hero",
                                        Document().append("inventory", it)
                                )
                        )
                )
            },
            {
                owner.find()
                        .get("hero", Document::class.java)
                        .get("inventory", Document::class.java)
                        .toMap().mapKeys { it.toString().toInt() }.mapValues { HeroItem((it as Document)) }
                        .toMutableMap()
            }
    )
}