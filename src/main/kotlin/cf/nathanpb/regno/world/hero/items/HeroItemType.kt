package cf.nathanpb.regno.world.hero.items

abstract class HeroItemType {
    abstract val id : String



    //todo implement event listener
    open fun onResourceProduction(level : Int){}

    open fun onCombat(level : Int){}

    open fun onTroopMovement(level : Int){}

    open fun onResourceTaken(level : Int){}
}