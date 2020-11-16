import math.{Ray, Vector3D}
import shapes.Shape

object FirstTry {
  def render(w: Int, h: Int, objects: Vector[Shape], camera: Camera): Array[Byte] = {
    val data = new Array[Byte](w * h)

    for(x <- 0 until w) {
      for(y <- 0 until h) {
        val pos = camera.pos + new Vector3D(x, y, 0)
        val ray = new Ray(pos, camera.direction)

        if(objects.map(o => o.distanceTo(ray)).exists(op => op.nonEmpty)) {
          data(x * w + y) = 127
        }
      }
    }

    data
  }
}
