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
    override fun toString(): String {
        val width = positions.maxOf { it.x }
        val height = positions.maxOf { it.y }
        val rows = mutableListOf<String>()
        for (row in 0..height) {
            rows.add(positions
                .filter { it.y == row }
                .sortedBy { it.x }
                .joinToString(
                    prefix = "\n│ ",
                    separator = " │ ",
                    postfix = " │",
                    transform = { p -> p.char.toString() }
                )
            )
        }
        return rows.joinToString(
            prefix = "╭─" + "──┬─".repeat(width) + "──╮",
            separator = "\n├─" + "──┼─".repeat(width) + "──┤",
            postfix = "\n╰─" + "──┴─".repeat(width) + "──╯",
        )
    }
}


fun main(args: Array<String>) {
//    println("Hello World! args.size: ${args.size} args: ${args.joinToString()}")
    args[0].let { problempath ->
        if (problempath.isBlank()) return
        solve(File(problempath).readText().trim())
    }
}