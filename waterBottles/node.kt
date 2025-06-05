package waterBottles

import java.security.MessageDigest

class Node(val value: Box) {
    companion object {
        private var counter: Int = 0
        private val duplication = mutableListOf<String>()

        fun counter(): Int = counter

        fun duplication(): List<String> = duplication

        fun checkDup(hash: String): Boolean = duplication.contains(hash)

        fun setDup(hash: String) {
            duplication.add(hash)
        }

        fun init() {
            counter = 0
            duplication.clear()
        }

        fun makeHash(box: Box): String {
            val rec = box.bottles.map { it.toList() }
            val sorted = rec.sortedBy { it.toString() }.toString()
            return MessageDigest.getInstance("MD5")
                .digest(sorted.toByteArray())
                .joinToString("") { "%02x".format(it) }
        }
    }

    val child: MutableList<Node> = mutableListOf()

    tailrec fun add(nd: Node, target: Box, index: Int = 0): Boolean {
        if (index >= child.size) {
            // return if (value == target) {
            return if (value.equals(target)) {
                child.add(nd)
                true
            } else {
                false
            }
        }

        val c = child[index]
        // return if (c.value == target) {
        return if (c.value.equals(target)) {
            c.child.add(nd)
            true
        } else if (c.add(nd, target, 0)) {
            true
        } else {
            add(nd, target, index + 1)
        }
    }

    tailrec fun replace(nd: Node, target: Box, index: Int = 0): Boolean {
        if (index >= child.size) {
            // return if (value == target) {
            return if (value.equals(target)) {
                child.clear()
                child.add(nd)
                true
            } else {
                false
            }
        }

        val c = child[index]
        // return if (c.value == target) {
        return if (c.value.equals(target)) {
            c.child.clear()
            c.child.add(nd)
            true
        } else if (c.replace(nd, target, 0)) {
            true
        } else {
            replace(nd, target, index + 1)
        }
    }
    tailrec fun search(target: Box, index: Int = 0): Boolean {
        if (index >= child.size) {
            return false
        }

        val c = child[index]
        return if (c.value == target) {
            counter++
            println("counter = $counter")
            c.value.display()
            true
        } else if (c.search(target, 0)) {
            counter++
            println("counter = $counter")
            c.value.display()
            true
        } else {
            search(target, index + 1)
        }
    }

    // fun search(target: Box): Boolean {
    //     var ret = false
    //     for (c in child) {
    //         if (c.value == target) {
    //             ret = true
    //             counter++
    //             println("counter = $counter")
    //             c.value.display()
    //             break
    //         } else {
    //             ret = c.search(target)
    //             if (ret) {
    //                 counter++
    //                 println("counter = $counter")
    //                 c.value.display()
    //                 break
    //             }
    //         }
    //     }
    //     return ret
    // }
}
