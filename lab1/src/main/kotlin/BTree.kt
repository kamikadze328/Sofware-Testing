class BTree<V : Comparable<V>> {
    //val MAXIMUM_ENTRY: Int = 4
    private var root: Node = Node(null)
    private var height: Int = 0
    private var size: Int = 0

    inner class Node(var parent: Node?) {
        var entries: MutableList<Entry> = mutableListOf()
        override fun toString(): String {
            return entries.joinToString(prefix = "", postfix = ", ")
        }
    }

    inner class Entry(var value: V, var less: Node? = null, var more: Node? = null) : Comparable<Entry> {
        override fun compareTo(other: Entry): Int = this.value.compareTo(other.value)
        override fun toString(): String {
            return value.toString()
        }
    }

    fun isEmpty(): Boolean = size == 0

    fun get(v: V): V? = search(root, v, height)?.value

    private fun search(node: Node, value: V, height: Int): Entry? {
        return searchNode(node, value, height)?.entries?.find { it.value == value }
    }

    private fun searchNode(node: Node, value: V, height: Int): Node? {
        val entries = node.entries
        when {
            height == 0 -> {
                for (i in 0..entries.size) {
                    if (value == entries[i].value)
                        return node
                }
            }
            entries.isEmpty() -> {
                return null
            }
            else -> {
                for (i in 0 until entries.size) {
                    when {
                        value < entries[i].value -> return entries[i].less?.let { searchNode(it, value, height - 1) }
                        value == entries[i].value -> return node
                        i + 1 == entries.size -> return entries[i].more?.let { searchNode(it, value, height - 1) }
                    }
                }
            }
        }
        return null
    }

    fun put(value: V) {
        val u: Node? = insert(root, Entry(value), height)
        size++
        if (u == null) return


    }

    private fun insert(node: Node, entry: Entry, height: Int): Node? {
        val value = entry.value
        val entries = node.entries
        var index = entries.size

        if (height == 0) {
            for (i in 0 until entries.size) {
                if (value < entries[i].value) {
                    index = i
                    break
                }
            }
        } else {
            val u: Node?
            for (i in 0 until entries.size) {
                if (value < entries[i].value || i + 1 == entries.size) {
                    val isLess = value < entries[i].value
                    u = insert((if (isLess) entries[i].less!! else entries[i].more!!), entry, height - 1)
                    if (u == null) return null
                    //

                    break
                }
            }
        }
        if (index == entries.size)
            node.entries.add(entry)
        else
            node.entries.add(index, entry)
        if (index > 0)
            node.entries[index - 1].more = entry.less
        if (index < entries.size - 2)
            node.entries[index - 1].less = entry.more

        return if (node.entries.size == 4) split(node)
        else null
    }

    private fun split(nodeToSplit: Node): Node? {

        if (nodeToSplit.parent == null) {
            val newNodeRight = Node(nodeToSplit)
            nodeToSplit.entries[2].less?.let { it.parent = newNodeRight }
            nodeToSplit.entries[3].less?.let { it.parent = newNodeRight }
            nodeToSplit.entries[3].more?.let { it.parent = newNodeRight }
            newNodeRight.entries.add(nodeToSplit.entries[2])
            newNodeRight.entries.add(nodeToSplit.entries[3])

            val newNodeLeft = Node(nodeToSplit)
            nodeToSplit.entries[0].less?.let { it.parent = newNodeLeft }
            nodeToSplit.entries[0].more?.let { it.parent = newNodeLeft }
            newNodeLeft.entries.add(nodeToSplit.entries[0])

            nodeToSplit.entries.removeAt(3)
            nodeToSplit.entries.removeAt(2)
            nodeToSplit.entries.removeAt(0)

            nodeToSplit.entries[0].less = newNodeLeft
            nodeToSplit.entries[0].more = newNodeRight


            height++
            return null
        } else {
            val newNode = Node(nodeToSplit.parent)
            newNode.entries.add(nodeToSplit.entries[2])
            newNode.entries.add(nodeToSplit.entries[3])

            val entry = Entry(nodeToSplit.entries[1].value, nodeToSplit, newNode)
            nodeToSplit.entries.removeAt(3)
            nodeToSplit.entries.removeAt(2)
            nodeToSplit.entries.removeAt(1)

            return insert(nodeToSplit.parent!!, entry, 0)
        }
    }

    override fun toString(): String {
        return toString(root, height)
    }

    private fun toString(node: Node, height: Int): String {
        val s = StringBuilder()
        if (height == 0) {
            s.append("$node")
        } else {
            for (i in 0 until node.entries.size) {
                if (node.entries[i].less !== null)
                    s.append(toString(node.entries[i].less!!, height - 1))
                s.append("${node.entries[i]}, ")
                if (i == node.entries.size - 1 && node.entries[i].more !== null)
                    s.append(toString(node.entries[i].more!!, height - 1))
            }
        }
        return s.toString()
    }

    fun toStringByBuckets(): String {
        val s = StringBuilder()
        for (h in 0..height)
            s.append(toStringByBuckets(root, h) + "\n")
        return s.toString()
    }

    private fun toStringByBuckets(node: Node, height: Int): String {
        val s = StringBuilder()
        if (height == 0) {

            return "${node.entries}(${numberOfChildren(node)})"
            //return ",".repeat((1 + node.entries.size) * level * 3) + "${node.entries}" + ".".repeat(if(level==0) (4 - node.entries.size) * 3 else ((1 + node.entries.size) * level * 3) + ((4 - node.entries.size) * 3))
        } else {
            for (i in 0 until node.entries.size) {
                if (node.entries[i].less !== null)
                    s.append(toStringByBuckets(node.entries[i].less!!, height - 1))
                if (i == node.entries.size - 1 && node.entries[i].more !== null)
                    s.append(toStringByBuckets(node.entries[i].more!!, height - 1))
            }
        }
        return s.toString()
    }

    private fun numberOfChildren(node: Node): Int {
        var number = 0
        for (i in 0 until node.entries.size) {
            if (node.entries[i].less != null) number++
            if (i == node.entries.size - 1 && node.entries[i].more != null) number++
        }
        return number
    }

    fun remove(value: V): V? {
        val node = searchNode(root, value, height)
        return if (node == null) null
        else {
            remove(node, value, false)
            size--
            value
        }
    }

    private fun remove(node: Node, value: V, isList: Boolean) {
        val entry = search(node, value, 0)
        val index = node.entries.indexOf(entry)
        if (node.entries[0].less == null) {
            if (node.parent == null || node.entries.size > 1) node.entries.removeAt(index).value
            else {
                val parentEntryForNext = node.parent!!.entries.find { it.value >= value }
                val nextNode = parentEntryForNext?.more

                val parentEntryForPrev = node.parent!!.entries.findLast { it.value < value }
                val prevNode = parentEntryForPrev?.less

                if (nextNode != null && nextNode.entries.size > 1) {
                    node.entries[0].value = parentEntryForNext.value
                    parentEntryForNext.value = nextNode.entries[0].value
                    nextNode.entries.removeAt(0)
                    return
                } else if (prevNode != null && prevNode.entries.size > 1) {
                    node.entries[0].value = parentEntryForPrev.value
                    parentEntryForPrev.value = prevNode.entries[prevNode.entries.size - 1].value
                    prevNode.entries.removeAt(prevNode.entries.size - 1)
                    return
                } else {
                    if (prevNode == null) {
                        nextNode!!.entries.add(0, Entry(parentEntryForNext.value))
                        node.parent!!.entries[0].less = nextNode
                        if (node.parent!!.entries.size > 1) node.parent!!.entries.remove(parentEntryForNext)
                        else remove(node.parent!!, parentEntryForNext.value, true)
                    } else {
                        prevNode.entries.add(Entry(parentEntryForPrev.value))
                        if (parentEntryForNext != null) parentEntryForNext.less = prevNode
                        prevNode.parent = node.parent
                        if (node.parent!!.entries.size > 1) node.parent!!.entries.remove(parentEntryForPrev)
                        else remove(node.parent!!, parentEntryForPrev.value, true)
                    }
                }
            }
        } else {
            if (isList) {
                if (node.parent == null) {
                    root = node.entries[0].less!!
                    root.parent = null
                } else {
                    val parentEntryForNext = node.parent!!.entries.find { it.value >= value }
                    val nextNode = parentEntryForNext?.more

                    val parentEntryForPrev = node.parent!!.entries.findLast { it.value < value }
                    val prevNode = parentEntryForPrev?.less

                    if (nextNode != null && nextNode.entries.size > 1) {
                        node.entries[0].value = parentEntryForNext.value
                        parentEntryForNext.value = nextNode.entries[0].value
                        node.entries[0].more = nextNode.entries[0].less
                        node.entries[0].more?.let { it.parent = node }
                        nextNode.entries.removeAt(0)
                    } else if (prevNode != null && prevNode.entries.size > 1) {
                        node.entries[0].value = parentEntryForPrev.value
                        parentEntryForPrev.value = prevNode.entries[prevNode.entries.size - 1].value
                        prevNode.entries.removeAt(prevNode.entries.size - 1)
                    } else {
                        if (prevNode == null) {
                            nextNode!!.entries.add(0, Entry(parentEntryForNext.value))
                            nextNode.entries[0].less = node.entries[0].less
                            node.parent!!.entries[0].less = nextNode
                            if (node.parent!!.entries.size > 1) node.parent!!.entries.removeAt(0)
                            else remove(node.parent!!, node.parent!!.entries[0].value, isList)
                        } else {
                            println("Kek!!")
                        }
                    }
                }
            } else {
                val childrenNode = entry!!.less
                val childrenEntry = childrenNode!!.entries[childrenNode.entries.size - 1]
                entry.value = childrenEntry.value
                return remove(childrenNode, childrenEntry.value, childrenEntry.less != null)
            }
        }
    }
}