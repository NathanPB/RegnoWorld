package cf.nathanpb.regno.world.boot

/*
 * Created by NathanPB
 * at 17/09/2018
 * on regnoworld
 */

class Phase constructor(display : String, run : () -> Unit){

    private val run : () -> Unit = run
    private val display : String = display

    fun start(){
        kotlin.run(run)
    }

    fun getDisplay() : String {
        return display
    }
}