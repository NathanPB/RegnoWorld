package cf.nathanpb.regno.world.utils

import sun.reflect.generics.reflectiveObjects.NotImplementedException

/*
 * Created by NathanPB
 * at 20/09/2018
 * on regnoworld
 */

class BoundArray<T>(private val addL: (T) -> Unit, private val removeL : (T) -> Unit, private val queryAll : () -> Array<T>) : MutableList<T> {

    override val size = kotlin.run(queryAll).size

    override fun add(element: T): Boolean {
        addL(element)
        return true
    }
    override fun remove(element: T): Boolean {
        removeL(element)
        return true
    }

    override fun iterator() = queryAll().toMutableList().iterator()
    override fun listIterator() = queryAll().toMutableList().listIterator()

    override fun contains(element: T) = queryAll().contains(element)
    override fun containsAll(elements: Collection<T>) = elements.map{this::contains}.any {true}
    override fun removeAll(elements: Collection<T>) = elements.map(this::add).any{true}
    override fun addAll(elements: Collection<T>) =  elements.map {this.add(it)}.any{true}
    override fun clear() = forEach {this.remove(it)}
    override fun isEmpty() = size == 0

    /*
     * Not Implemented shit
     */

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        throw NotImplementedException()
    }
    override fun retainAll(elements: Collection<T>): Boolean {
        throw NotImplementedException()
    }
    override fun removeAt(index: Int): T {
        throw NotImplementedException()
    }
    override fun lastIndexOf(element: T): Int {
        throw NotImplementedException()
    }
    override fun indexOf(element: T): Int {
        throw NotImplementedException()
    }
    override fun add(index: Int, element: T) {
        throw NotImplementedException()
    }
    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        throw NotImplementedException()
    }
    override fun set(index: Int, element: T): T {
        throw NotImplementedException()
    }
    override fun listIterator(index: Int): MutableListIterator<T> {
        throw NotImplementedException()
    }
    override fun get(index: Int): T {
        throw NotImplementedException()
    }
}