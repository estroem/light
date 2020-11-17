package math

class Ray(val pos: Vector3D, val direction: Vector3D) {
  def distanceTo(point: Vector3D): Double = ((pos - sec) cross (point - sec)).length / pos.dist(sec)

  def sec: Vector3D = pos + direction
}
