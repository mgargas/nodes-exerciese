import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.ss.usermodel.{Cell, Row, WorkbookFactory}
import org.apache.poi.xssf.streaming.SXSSFCell
import org.apache.poi.xssf.usermodel.XSSFCell

import scala.jdk.CollectionConverters._
import scala.language.postfixOps
case class Node(
  id: Int,
  name: String,
  level: Int,
  nodes: List[Node]
)

object Main extends App {
  def parse(xlsResourceName: String, levelsNumber: Int): List[Node] = {
    val workbook = WorkbookFactory.create(getClass.getResourceAsStream(xlsResourceName))
    val sheet    = workbook.getSheetAt(0) // Assuming they're in the first sheet here.
    sheet
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
        } yield Node(id, name, level, List.empty[Node])
      })
      .toList
      .flatten
  }
  parse("test1.xlsx", 3).foreach(println(_))
}
