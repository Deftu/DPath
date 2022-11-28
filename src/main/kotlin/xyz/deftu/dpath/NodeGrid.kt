package xyz.deftu.dpath

data class NodeGrid(
    val width: Int,
    val height: Int,
    val nodes: Array<Array<Node>>
) {
    constructor(
        width: Int,
        height: Int,
        nodeCreator: (Int, Int) -> Node
    ) : this(width, height, Array(width) { x ->
        Array(height) { y ->
            nodeCreator(x, y)
        }
    })

    operator fun get(x: Int, y: Int) = nodes[x][y]

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NodeGrid

        if (width != other.width) return false
        if (height != other.height) return false
        if (!nodes.contentDeepEquals(other.nodes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        result = 31 * result + nodes.contentDeepHashCode()
        return result
    }
}
