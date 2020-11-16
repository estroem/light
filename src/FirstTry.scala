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

        if(objects.map(o => o.distanceTo(ray)).exists(op => op.nonEmpty)) {
          data(y * w + x) = 127
        }
      }
    }

    data
  }
}
