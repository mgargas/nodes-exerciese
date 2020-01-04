import model.Node
import org.apache.poi.ss.usermodel.WorkbookFactory
import parser.NodesParser

import scala.language.postfixOps

object Main extends App {
  private val workbook          = WorkbookFactory.create(getClass.getResourceAsStream("test1.xlsx"))
  private val sheet             = workbook.getSheetAt(0) // Assuming they're in the first sheet here.
  private val nodes: List[Node] = NodesParser.parseSheet(sheet, 3)
  println(nodes)
}
