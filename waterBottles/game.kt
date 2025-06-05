package waterBottles
import waterBottles.Bottle

tailrec fun dfs(
    node: Node,
    box: Box,
    depth: Int,
    bottleIndex: Int = 0,
    destIndex: Int = 0,
    emptyBottleFlg: Boolean = false,
    result: Box? = null
): Box? {
    if (depth < 0) {
        return result
    }
    if (box.check()) {
        println("depth = $depth")
        println("Complete!!")
        return box
    }
    if (bottleIndex >= box.bottles.size) {
        return result
    }

    val bottle = box.bottles[bottleIndex]
    if (destIndex >= box.bottles.size) {
        // 次のボトルへ進む
        return dfs(node, box, depth, bottleIndex + 1, 0, false, result)
    }

    val dest = box.bottles[destIndex]
    if (destIndex == bottleIndex ||
        dest.size >= Bottle.MAX_UNIT ||
        bottle.size <= 0 ||
        bottle.check() ||
        emptyBottleFlg && dest.isEmpty() ||
        bottle.monoCheck() && dest.isEmpty() ||
        !(bottle.lastOrNull() == dest.lastOrNull() || dest.isEmpty())
    ) {
        // 次の宛先ボトルへ進む
        return dfs(node, box, depth, bottleIndex, destIndex + 1, emptyBottleFlg, result)
    }

    val newEmptyBottleFlg = emptyBottleFlg || dest.isEmpty()
    val temp = box.deepCopy()
    temp.bottles[bottleIndex].pour(temp.bottles[destIndex])
    val md5hash = Node.makeHash(temp)
    if (Node.checkDup(md5hash)) {
        // ハッシュが重複した場合は次の宛先ボトルへ
        return dfs(node, box, depth, bottleIndex, destIndex + 1, newEmptyBottleFlg, result)
    } else {
        Node.setDup(md5hash)
    }

    val ret = node.replace(Node(temp), box)
    if (!ret) {
        println("Error in dfs.replace: Could not find target box")
        println("temp = ${temp.bottles}")
        println("target = ${box.bottles}")
        println("node.value = ${node.value.bottles}")
        // System.exit(1) // デバッグのためコメントアウト
    }

    val newResult = if (temp.check()) {
        println("Complete!!")
        temp
    } else {
        null
    }

    // 現在の状態で再帰探索
    val subResult = dfs(node, temp, depth - 1, 0, 0, false, newResult)
    if (subResult != null) {
        return subResult
    }

    // 次の宛先ボトルへ進む
    return dfs(node, box, depth, bottleIndex, destIndex + 1, newEmptyBottleFlg, result)
}

fun bfs(node: Node): Box? {
    var result: Box? = null
    val queue = ArrayDeque<Box>()
    queue.add(node.value.deepCopy())
    var cnt = 0

    while (queue.isNotEmpty()) {
        val box = queue.removeFirst()
        box.bottles.forEachIndexed { i, bottle ->
            var emptyBottleFlg = false
            box.bottles.forEachIndexed { j, dest ->
                if (j == i) return@forEachIndexed
                if (dest.size >= Bottle.MAX_UNIT) return@forEachIndexed
                if (bottle.size <= 0) return@forEachIndexed
                if (bottle.check()) return@forEachIndexed
                if (emptyBottleFlg) return@forEachIndexed
                if (dest.isEmpty()) {
                    emptyBottleFlg = true
                }
                if (bottle.monoCheck() && dest.isEmpty()) return@forEachIndexed
                if (!(bottle.lastOrNull() == dest.lastOrNull() || dest.isEmpty())) return@forEachIndexed

                cnt++
                val temp = box.deepCopy()
                temp.bottles[i].pour(temp.bottles[j])
                val md5hash = Node.makeHash(temp)
                if (Node.checkDup(md5hash)) {
                    return@forEachIndexed
                } else {
                    Node.setDup(md5hash)
                }

                val ret = node.add(Node(temp), box)
                if (!ret) {
                    println("Error in bfs.add: Could not find target box")
                    println("cnt = $cnt")
                    println("temp = ${temp.bottles}")
                    println("target = ${box.bottles}")
                    println("node.value = ${node.value.bottles}")
                    // System.exit(1) // コメントアウトして続行
                }

                if (temp.check()) {
                    println("cnt = $cnt")
                    println("Complete!!")
                    result = temp
                    return@forEachIndexed
                }
                queue.add(temp.deepCopy())
            }
            if (result != null) return@forEachIndexed
        }
        if (result != null) break
    }
    return result
}

fun main() {
    // val box = Box(
    //     mutableListOf(
    //         Bottle().apply { addAll(listOf(Color.RED, Color.SKY_BLUE, Color.RED, Color.YELLOW)) },
    //         Bottle().apply { addAll(listOf(Color.MAGENTA, Color.PINK, Color.AQUA_GREEN, Color.RED)) },
    //         Bottle().apply { addAll(listOf(Color.AQUA_GREEN, Color.YELLOW_GREEN, Color.PURPLE, Color.BLUE)) },
    //         Bottle().apply { addAll(listOf(Color.SKY_BLUE, Color.MAGENTA, Color.ORANGE, Color.YELLOW)) },
    //         Bottle().apply { addAll(listOf(Color.PURPLE, Color.PINK, Color.ORANGE, Color.YELLOW_GREEN)) },
    //         Bottle().apply { addAll(listOf(Color.PURPLE, Color.BLUE, Color.PINK, Color.AQUA_GREEN)) },
    //         Bottle().apply { addAll(listOf(Color.ORANGE, Color.YELLOW, Color.AQUA_GREEN, Color.PURPLE)) },
    //         Bottle().apply { addAll(listOf(Color.SKY_BLUE, Color.BLUE, Color.GRAY, Color.GRAY)) },
    //         Bottle().apply { addAll(listOf(Color.SKY_BLUE, Color.YELLOW_GREEN, Color.MAGENTA, Color.RED)) },
    //         Bottle().apply { addAll(listOf(Color.YELLOW, Color.MAGENTA, Color.ORANGE, Color.GRAY)) },
    //         Bottle().apply { addAll(listOf(Color.YELLOW_GREEN, Color.GRAY, Color.PINK, Color.BLUE)) },
    //         Bottle(),
    //         Bottle()
    //     )
    // )

    val box = Box(
        mutableListOf(
            Bottle().apply { addAll(listOf(Color.ORANGE, Color.RED, Color.ORANGE, Color.RED)) },
            Bottle().apply { addAll(listOf(Color.BLUE, Color.BLUE, Color.ORANGE, Color.RED)) },
            Bottle().apply { addAll(listOf(Color.RED, Color.ORANGE, Color.BLUE, Color.BLUE)) },
            Bottle(),
            Bottle()
        )
    )

    Node.init()
    box.display()
    val node = Node(box)
    val start = System.currentTimeMillis()
    // val answer = dfs(node, box, 100)
    val answer = bfs(node)
    if (answer != null) {
        node.search(answer)
        val endTime = System.currentTimeMillis()
        println("-".repeat(answer.bottles.size))
        println("手数: ${Node.counter()}手")
        println("経過時間: ${(endTime - start) / 1000.0}秒")
    } else {
        println("解無し？あるいは読む深さが足りません")
    }
}
