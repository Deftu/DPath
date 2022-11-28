package xyz.deftu.dpath

import kotlin.math.abs

data class Node(
    val traversable: Boolean,
    val x: Int,
    val y: Int,
    var gCost: Int = -1,
    var hCost: Int = -1,
    var parent: Node? = null
) {
    val fCost: Int
        get() = gCost + hCost

    fun getNeighbours(
        diagonals: Boolean = false,
        vertical: Boolean = false
    ): List<Node> {
        val neighbours = mutableListOf<Node>()
        if (diagonals) {
            neighbours.add(Node(traversable, x - 1, y - 1, gCost, hCost))
            neighbours.add(Node(traversable, x + 1, y - 1, gCost, hCost))
            neighbours.add(Node(traversable, x - 1, y + 1, gCost, hCost))
            neighbours.add(Node(traversable, x + 1, y + 1, gCost, hCost))
        }

        if (vertical) {
            neighbours.add(Node(traversable, x, y - 1, gCost, hCost))
            neighbours.add(Node(traversable, x, y + 1, gCost, hCost))
        }

        if (diagonals && vertical) {
            neighbours.add(Node(traversable, x - 1, y - 2, gCost, hCost))
            neighbours.add(Node(traversable, x + 1, y - 2, gCost, hCost))
            neighbours.add(Node(traversable, x - 1, y + 2, gCost, hCost))
            neighbours.add(Node(traversable, x + 1, y + 2, gCost, hCost))
            neighbours.add(Node(traversable, x - 2, y - 1, gCost, hCost))
            neighbours.add(Node(traversable, x - 2, y + 1, gCost, hCost))
            neighbours.add(Node(traversable, x + 2, y - 1, gCost, hCost))
            neighbours.add(Node(traversable, x + 2, y + 1, gCost, hCost))
        }

        neighbours.add(Node(traversable, x - 1, y, gCost, hCost))
        neighbours.add(Node(traversable, x + 1, y, gCost, hCost))
        neighbours.add(Node(traversable, x, y - 1, gCost, hCost))
        neighbours.add(Node(traversable, x, y + 1, gCost, hCost))
        return neighbours
    }

    fun distanceTo(other: Node): Int {
        val dx = abs(x - other.x)
        val dy = abs(y - other.y)
        return dx + dy
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Node) return false
        return x == other.x && y == other.y
    }
}
