package cf.nathanpb.regno.world.utils

class BoundMap<K, V>(private val _set : (K, V) -> Unit, private val _delete : (K) -> Unit, private val queryAll : () -> MutableMap<K, V>) : MutableMap<K, V> {

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = queryAll().entries

    override val keys: MutableSet<K>
        get() = queryAll().keys

    override val values: MutableCollection<V>
        get() = queryAll().values

    override val size: Int
        get() = queryAll().size

    override fun clear() = keys.forEach { this.remove(it) }
    override fun containsKey(key: K) = keys.contains(key)
    override fun containsValue(value: V) = values.contains(value)
    override fun isEmpty() = size < 1

    override fun get(key: K): V? = entries.filter { it.key == key }.firstOrNull()?.value
    override fun put(key: K, value: V): V? {
        _set(key, value)
        return value
    }

    override fun putAll(from: Map<out K, V>) = from.forEach{ this[it.key] = it.value}
    override fun remove(key: K): V? {
        val a = get(key)
        _delete(key)
        return a
    }
}