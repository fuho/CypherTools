import java.io.File

fun solve(input: String) {
    println("Input:\n$input")
    val positions = mutableListOf<Position>()
    input.lines().forEachIndexed { row, line ->
        line.trim().split(" ").forEachIndexed { column, letter ->
            positions.add(Position(column, row, letter.first()))
        }
    }
    val board = Board(positions)
    println(board)
}

data class Position(val x: Int, val y: Int, val char: Char)

data class Board(val positions: Iterable<Position>) {

    fun get(x: Int, y: Int): Position? = positions.firstOrNull() {
        it.y == y && it.x == x
    }

    override fun toString(): String {
        val maxCol = positions.maxOf { it.x }
        val maxRow = positions.maxOf { it.y }
        val rows = mutableListOf<String>()
        for (row in 0..maxRow) {
            val rowValues = mutableListOf<Position?>()
            for (col in 0..maxCol) {
                rowValues.add(get(col, row))
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
            prefix = "╭─" + "──┬─".repeat(maxCol) + "──╮",
            separator = "\n├─" + "──┼─".repeat(maxCol) + "──┤",
            postfix = "\n╰─" + "──┴─".repeat(maxCol) + "──╯",
        )
    }
}


fun main(args: Array<String>) {
    args[0].let { problempath ->
        if (problempath.isBlank()) return
        solve(File(problempath).readText().trim())

/*
        println(
            Board(
                listOf(
                    Position(0, 0, '0'),
                    Position(0, 2, '8'),
                    Position(10, 5, 'X'),
                    Position(3, 2, 'B'),
                    Position(3, 3, 'F'),
                )
            )
        )
*/
    }
}