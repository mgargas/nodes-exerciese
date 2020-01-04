package parser

import model.{Node, ParsedCell}
import org.apache.poi.ss.usermodel.{Row, Sheet}

import scala.jdk.CollectionConverters._

object NodesParser {

  def parseSheet(sheet: Sheet, levelsNumber: Int): List[Node] = {
    val parsedCells = sheet
      .iterator()
      .asScala
      .drop(1)
      .map(row => {
        for {
          (name, level) <- row
            .cellIterator()
            .asScala
            .zipWithIndex
            .map(tuple => (tuple._1.getStringCellValue, tuple._2))
            .find(_._1.nonEmpty)
          id <- Option(row.getCell(levelsNumber, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getNumericCellValue.toInt)
        } yield ParsedCell(id, name, level)
      })
      .toList
      .flatten

    groupCellsIntoTrees(parsedCells, 0)
      .map(buildTreeFromCells)
  }

  private def groupCellsIntoTrees(cells: List[ParsedCell], rootLevel: Int): List[List[ParsedCell]] = cells match {
    case ::(head, next) =>
      if (head.level == rootLevel)
        (head :: next.takeWhile(_.level != rootLevel)) :: groupCellsIntoTrees(
          next.dropWhile(_.level != rootLevel),
          rootLevel
        )
      else Nil
    case Nil => Nil
  }

  private def buildTreeFromCells(cells: List[ParsedCell]): Node = cells match {
    case ::(head, next) =>
      Node(
        head.id,
        head.name,
        groupCellsIntoTrees(next, head.level + 1).map(buildTreeFromCells)
      )
    case Nil => null
  }
}
