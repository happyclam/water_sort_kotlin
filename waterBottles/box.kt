
package waterBottles
class Box(bottles: List<Bottle>) {
    companion object {
        private val colors = mapOf(
            Color.YELLOW to "黄色",
            Color.RED to "赤色",
            Color.SKY_BLUE to "水色",
            Color.AQUA_GREEN to "薄緑",
            Color.PINK to "桃色",
            Color.MAGENTA to "赤紫",
            Color.BLUE to "青色",
            Color.PURPLE to "紫色",
            Color.YELLOW_GREEN to "黄緑",
            Color.ORANGE to "橙色",
            Color.GRAY to "灰色",
            Color.DARK_GREEN to "深緑"
        )

        fun colors(): Map<Int, String> = colors
    }

    var bottles: MutableList<Bottle> = bottles.toMutableList()

    fun deepCopy(): Box {
        val member = bottles.map { Bottle().apply { addAll(it) } }
        val temp = Box(member)
        temp.bottles.forEachIndexed { index, bottle ->
            bottle.complete = bottles[index].complete
        }
        return temp
    }

    fun check(): Boolean {
        return bottles.all { it.check() }
    }

    fun display() {
        val lines = arrayOf("", "", "", "")
        bottles.forEach { bottle ->
            lines[0] += "|" + (colors[bottle.getOrNull(3)] ?: "　　")
            lines[1] += "|" + (colors[bottle.getOrNull(2)] ?: "　　")
            lines[2] += "|" + (colors[bottle.getOrNull(1)] ?: "　　")
            lines[3] += "|" + (colors[bottle.getOrNull(0)] ?: "　　")
        }
        println("-".repeat(bottles.size))
        lines.forEach { line ->
            println("$line|")
        }
    }

    // override fun equals(other: Any?): Boolean {
    //     if (this === other) return true
    //     if (other !is Box) return false
    //     return bottles == other.bottles
    // }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Box) return false
        if (bottles.size != other.bottles.size) return false

        // 順序を無視してBottleの内容を比較
        val thisSorted = bottles.sortedBy { it.toList().toString() }
        val otherSorted = other.bottles.sortedBy { it.toList().toString() }
        return thisSorted.zip(otherSorted).all { (a, b) ->
            a.toList() == b.toList()
        }
    }

    override fun hashCode(): Int = bottles.hashCode()
}
