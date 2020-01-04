package model

case class ParsedCell(
  id: Int,
  name: String,
  level: Int
)

case class Node(
  id: Int,
  name: String,
  nodes: List[Node]
)
