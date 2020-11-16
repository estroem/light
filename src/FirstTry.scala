import math.{Ray, Vector3D}
import shapes.Shape

object FirstTry {
  val z: Int = -50

  def render(w: Int, h: Int, objects: Vector[Shape]): Array[Byte] = {
    val data = new Array[Byte](w * h)

    val dir = new Vector3D(0, 0, -1)

    for(x <- 0 until w) {
      for(y <- 0 until h) {
        val pos = new Vector3D(x, y, z)
        val ray = new Ray(pos, dir)

        if(objects.map(o => o.distanceTo(ray)).exists(op => op.nonEmpty)) {
          data(x * w + y) = 127
        }
      }
    }

    data
  }
}
