package xyz.deftu.dpath.exceptions

import xyz.deftu.dpath.Node

class PathfindingException(
    message: String,
    vararg nodes: Node
) : Exception(message)
