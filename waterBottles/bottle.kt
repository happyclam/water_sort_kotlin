package waterBottles
class Color{
    companion object {
        const val YELLOW: Int = 0
        const val RED: Int = 1
        const val SKY_BLUE = 2
        const val AQUA_GREEN = 3
        const val PINK = 4
        const val MAGENTA = 5
        const val BLUE = 6
        const val PURPLE = 7
        const val YELLOW_GREEN = 8
        const val ORANGE = 9
        const val GRAY = 10
        const val DARK_GREEN = 11
    }
}
class Bottle : MutableList<Int> by ArrayList() {
    var complete: Boolean = false

    companion object {
        const val MAX_UNIT = 4
    }

    fun pour(bottle: Bottle): Boolean {
        val org = this.lastOrNull() ?: return true
        while (this.isNotEmpty() && bottle.size < MAX_UNIT && this.lastOrNull() == org) {
            val buf = this.removeLast()
            bottle.add(buf)
        }
        return true
    }

    fun monoCheck(): Boolean {
        return this.distinct().size == 1
    }

    fun check(): Boolean {
        if (this.isEmpty()) return true
        val org = this.first()
        this.forEachIndexed { index, color ->
            if (color != org) return false
            if (index + 1 == MAX_UNIT) {
                complete = true
                return true
            }
        }
        return false
    }

    override fun toString(): String {
        val colorNames = this.map { color -> Box.colors()[color] ?: "不明" }
        return colorNames.toString()
    }
}
