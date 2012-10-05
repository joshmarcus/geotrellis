package geotrellis.raster

import geotrellis._
import org.scalatest.FunSpec
import org.scalatest.matchers.MustMatchers
import geotrellis.process.TestServer

@org.junit.runner.RunWith(classOf[org.scalatest.junit.JUnitRunner])
class RasterizationSpec extends FunSpec with MustMatchers {
  
  describe("Tile intersection with polygon in 4326") {
    it("should rasterize") {
      // tile is: POLYGON ((-125.14732160410001 35.15178531379998, -125.14732160410001 16.866071086599973, -143.4330358313 16.866071086599973, -143.4330358313 35.15178531379998, -125.14732160410001 35.15178531379998))
      val polygonWkt = "POLYGON ((-125.14732160410001 17.508078395860338, -131.4 17.5, -130.90042117036415 35.15178531379998, -125.14732160410001 35.15178531379998, -125.14732160410001 17.508078395860338))"
      val polygon = TestServer().run(io.LoadWkt(polygonWkt))  
      val e = Extent(-143.4330358313, 16.866071086599973 , -125.14732160410001, 35.15178531379998 )
      val g = RasterExtent(e, 2.2857142784, 2.2857142784, 8, 8)
      val data = (0 until 64).toArray
      val r = Raster(data, g)
      
      
    }
  }
}
