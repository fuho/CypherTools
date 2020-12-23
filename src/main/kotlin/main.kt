import java.io.File

data class Point(val x: Int, val y: Int) {
    operator fun plus(v: Point) = Point(x + v.x, y + v.y)
}

data class Rectangle(val a: Point, val b: Point) {
    operator fun contains(p: Point) = p.x in a.x..b.x && p.y in a.y..b.y
}


enum class CardinalDirection(val point: Point) {
    SOUTH(Point(0, 1)),
    WEST(Point(-1, 0)),
    NORTH(Point(0, -1)),
    EAST(Point(1, 0));

    val x get() = point.x
    val y get() = point.y
}

data class BoardField(var x: Int, var y: Int, val char: Char)

data class Board(val positions: Iterable<BoardField>) {

    private fun getPosition(x: Int, y: Int): BoardField? = positions.firstOrNull() {
        it.y == y && it.x == x
    }

    private val maxX get(): Int = positions.maxOf { it.x }
    private val maxY get(): Int = positions.maxOf { it.y }

    fun solve() {
        var cardinalDirectionIndex = 0
        val world = Rectangle(Point(0, 0), Point(maxX, maxY))
        for (y in maxY downTo 0) {
            for (x in maxX downTo 0) {
                cardinalDirectionIndex %= CardinalDirection.values().size
                val cardinalDirection = CardinalDirection.values()[cardinalDirectionIndex++]
                if (world.contains(Point(x, y) + cardinalDirection.point)) {
                    val itemA = getPosition(x, y)
                    val itemB = getPosition(x + cardinalDirection.x, y + cardinalDirection.y)
                    itemA?.let {
                        it.x += cardinalDirection.x
                        it.y += cardinalDirection.y
                    }
                    itemB?.let {
                        it.x -= cardinalDirection.x
                        it.y -= cardinalDirection.y
                    }
                } else {
                    continue
                }
            }
        }
    }

    override fun toString(): String {
        val rows = mutableListOf<String>()
        for (row in 0..maxY) {
            val rowValues = mutableListOf<BoardField?>()
            for (col in 0..maxX) {
                rowValues.add(getPosition(col, row))
            }
            rows.add(rowValues
                .joinToString(
                    prefix = "\n│ ",
                    separator = " │ ",
                    postfix = " │",
                    transform = { p -> p?.char?.toString() ?: " " }
                )
            )
        }
        return rows.joinToString(
            prefix = "╭─" + "──┬─".repeat(maxX) + "──╮",
            separator = "\n├─" + "──┼─".repeat(maxX) + "──┤",
            postfix = "\n╰─" + "──┴─".repeat(maxX) + "──╯",
        )
    }
}

fun solveCypher(input: String) {
    println("Input:\n$input")
    val positions = mutableListOf<BoardField>()
    input.lines().forEachIndexed { row, line ->
        line.trim().split(" ").forEachIndexed { column, letter ->
            positions.add(BoardField(column, row, letter.first()))
        }
    }
    val board = Board(positions)
    println("Solving: \n$board \n Solved!:\n${board.also { it.solve() }}")
}

fun main(args: Array<String>) {
    args[0].let { problempath ->
        if (problempath.isBlank()) return
        solveCypher(File(problempath).readText().trim())
    }
}