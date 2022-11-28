import xyz.deftu.dpath.Node
import xyz.deftu.dpath.NodeCuboid
import xyz.deftu.dpath.NodeGrid
import xyz.deftu.dpath.Pathfinder

fun main(args: Array<String>) {
    val type = if (args.isNotEmpty()) args[0].toIntOrNull() ?: 0 else 0
    when (type) {
        0 -> testGrid()
        1 -> testCuboid()
    }
}

private fun testGrid() {
    // Create a 25x25 grid of nodes
    val grid = NodeGrid(25, 25) { x, y ->
        // Randomly generate a grid where 50% of the nodes are traversable
        Node(Math.random() < 0.5, x, y)
    }

    // Get the start and end nodes
    val start = grid[0, 0]
    val end = grid[(Math.random() * grid.width).toInt(), (Math.random() * grid.height).toInt()]

    // Find the path
    val path = Pathfinder.pathfindGrid(start, end)

    // Print a visual representation of the path
    for (y in 0 until grid.height) {
        for (x in 0 until grid.width) {
            val node = grid[x, y]
            print(if (node in path) "x" else if (node.traversable) "-" else "#")
        }

        println()
    }
}

private fun testCuboid() {
    // Create a 25x25x25 cuboid of nodes
    val cuboid = NodeCuboid(25, 25, 25) { x, y, z ->
        // Randomly generate a cuboid where 50% of the nodes are traversable
        Node(Math.random() < 0.5, x, y, z)
    }

    // Get the start and end nodes
    val start = cuboid[0, 0, 0]
    val end = cuboid[(Math.random() * cuboid.width).toInt(), (Math.random() * cuboid.height).toInt(), (Math.random() * cuboid.depth).toInt()]

    // Find the path
    val path = Pathfinder.pathfindCuboid(start, end)

    // Print a visual representation of the path
    for (z in 0 until cuboid.depth) {
        println("z = $z")
        for (y in 0 until cuboid.height) {
            for (x in 0 until cuboid.width) {
                val node = cuboid[x, y, z]
                print(if (node in path) "x" else if (node.traversable) "-" else "#")
            }

            println()
        }

        println()
    }
}
