import akka.http.scaladsl.server.{HttpApp, Route}
import de.heikoseeberger.akkahttpjackson.JacksonSupport
import model.Node
import org.apache.poi.ss.usermodel.WorkbookFactory
import parser.NodesParser

import scala.language.postfixOps

object Main extends HttpApp with App with JacksonSupport {

  private val workbook          = WorkbookFactory.create(getClass.getResourceAsStream("test1.xlsx"))
  private val sheet             = workbook.getSheetAt(0) // Assuming they're in the first sheet here.
  private val nodes: List[Node] = NodesParser.parseSheet(sheet, 3)
  private val nodesJson         = JacksonSupport.defaultObjectMapper.writeValueAsString(nodes)
  println(nodesJson)

  override protected def routes: Route = path("nodes") {
    get {
      complete(nodes)
    }
  }
  startServer("localhost", 8080)
}
