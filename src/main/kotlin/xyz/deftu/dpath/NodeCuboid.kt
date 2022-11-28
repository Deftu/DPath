package xyz.deftu.dpath

data class NodeCuboid(
    val width: Int,
    val height: Int,
    val depth: Int,
    val nodes: Array<Array<Array<Node>>>
) {
    constructor(
        width: Int,
        height: Int,
        depth: Int,
        generator: (x: Int, y: Int, z: Int) -> Node
    ) : this(width, height, depth, Array(width) { x ->
        Array(height) { y ->
            Array(depth) { z ->
                generator(x, y, z)
            }
        }
    })

    operator fun get(x: Int, y: Int, z: Int) = nodes[x][y][z]

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NodeCuboid

        if (width != other.width) return false
        if (height != other.height) return false
        if (depth != other.depth) return false
        if (!nodes.contentDeepEquals(other.nodes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        result = 31 * result + depth
        result = 31 * result + nodes.contentDeepHashCode()
        return result
    }
}
