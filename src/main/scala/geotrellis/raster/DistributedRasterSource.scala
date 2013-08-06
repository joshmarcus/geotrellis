package geotrellis.raster

import geotrellis._
import geotrellis.raster.op._
import geotrellis.SourceBuilder
import geotrellis.logic.Collect

object DistributedRasterSource {
  implicit def canBuildSourceFrom: CanBuildSourceFrom[DistributedRasterSource, Raster, DistributedRasterSource] = new CanBuildSourceFrom[DistributedRasterSource, Raster, DistributedRasterSource] {
    def apply() = new DistributedRasterSourceBuilder
    def apply(dfn: RasterDefinition, op: Op[Seq[Op[Raster]]]) = 
      new DistributedRasterSourceBuilder().setOp(null).setDataDefinition(dfn)
  }
}

class DistributedRasterSource(val rasterDef:Op[RasterDefinition]) extends RasterSourceLike[DistributedRasterSource] {
  def partitions = rasterDef.map(_.tiles)
  val dataDefinition =  rasterDef
  def converge = rasterDef.map { rd =>
    val re = rd.re
    Collect(rd.tiles).map(s => Raster(TileArrayRasterData(s.toArray,rd.tileLayout,re), re))
  }
}

object Foo {
  def main() = {
    val d1 = new DistributedRasterSource(null)
    val d2: DistributedRasterSource = d1.map(local.Add(_, 3))
    val d3: DistributedRasterSource = d1.localAdd(3)

    val l1 = new LocalRasterSource(null)
    val l2: LocalRasterSource = l1.map(local.Add(_, 3))

    val l3: LocalRasterSource = l1.localAdd(2)
  }
}