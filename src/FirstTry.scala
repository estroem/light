import math.{Ray, Vector3D}
import shapes.Shape

object FirstTry {
  def render(w: Int, h: Int, objects: Vector[Shape], camera: Camera): Array[Byte] = {
    val data = new Array[Byte](w * h)

    val adjuster = new Vector3D(w / 2, h / 2, 0)

    for(y <- 0 until h) {
      for(x <- 0 until w) {
        val pos = camera.pos + new Vector3D(x, y, 0) - adjuster
        val ray = new Ray(pos, camera.direction)

        val objsInSight = objects.filter(o => o.distanceTo(ray).nonEmpty)
        val obj = if(objsInSight.nonEmpty) Option.apply(objsInSight.minBy(o => o.distanceTo(ray).get)) else Option.empty

        if(obj.nonEmpty) {
          data(y * w + x) = (obj.get.brightness * 127).asInstanceOf[Byte]
        }
      }
    }

    data
  }
}
