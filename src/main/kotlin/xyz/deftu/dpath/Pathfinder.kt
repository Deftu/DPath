package xyz.deftu.dpath

import org.slf4j.LoggerFactory
import xyz.deftu.dpath.exceptions.PathfindingException
import kotlin.jvm.Throws

object Pathfinder {
    private val LOGGER = LoggerFactory.getLogger(Pathfinder::class.java)

    @JvmStatic
    @Throws(PathfindingException::class)
    fun pathfindGrid(start: Node, end: Node): List<Node> {
        if (!start.traversable) throw PathfindingException("The start node is not traversable!", start)
        if (!end.traversable) throw PathfindingException("The end node is not traversable!", end)

        val open = mutableListOf<Node>()
        val closed = mutableListOf<Node>()
        val path = mutableListOf<Node>()

        open.add(start)

        while (open.isNotEmpty()) {
            val current = open.minByOrNull {
                it.fCost
            } ?: throw PathfindingException("No path found!", start, end)
            LOGGER.debug("Current node: {}", current)
            open.remove(current)
            closed.add(current)

            if (current == end) {
                var node = current
                while (node != start) {
                    path.add(node)
                    node = node.parent!!
                }

                path.add(start)
                val path = path.reversed()
                LOGGER.debug("Path between {} and {} found: {}", start, end, path)
                return path
            }

            for (neighbor in current.getNeighbours(true, false)) {
                if (!neighbor.traversable || closed.contains(neighbor)) continue
                val newG = current.gCost + 1
                if (newG < neighbor.gCost || !open.contains(neighbor)) {
                    neighbor.gCost = newG
                    neighbor.hCost = neighbor.distanceTo(end)
                    neighbor.parent = current
                    if (!open.contains(neighbor)) open.add(neighbor)
                }
            }
        }

        return path
    }

    @JvmStatic
    @Throws(PathfindingException::class)
    fun pathfindCuboid(start: Node, end: Node): List<Node> {
        if (!start.traversable) throw PathfindingException("The start node is not traversable!", start)
        if (!end.traversable) throw PathfindingException("The end node is not traversable!", end)

        val open = mutableListOf<Node>()
        val closed = mutableListOf<Node>()
        val path = mutableListOf<Node>()

        open.add(start)

        while (open.isNotEmpty()) {
            val current = open.minByOrNull {
                it.fCost
            }!!
            LOGGER.debug("Current node: {}", current)
            if (current == end) {
                var node: Node? = current
                while (node != null) {
                    path.add(node)
                    node = node.parent
                }

                path.add(start)
                val path = path.reversed()
                LOGGER.debug("Path between {} and {} found: {}", start, end, path)
                return path
            }

            open.remove(current)
            closed.add(current)

            for (neighbor in current.getNeighbours(true, true)) {
                if (!neighbor.traversable || closed.contains(neighbor)) continue

                val newG = current.gCost + 1

                if (!open.contains(neighbor) || newG < neighbor.gCost) {
                    neighbor.gCost = newG
                    neighbor.hCost = neighbor.distanceTo(end)
                    neighbor.parent = current

                    if (!open.contains(neighbor)) open.add(neighbor)
                }
            }
        }

        return path
    }
}
