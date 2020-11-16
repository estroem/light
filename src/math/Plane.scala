package math

class Plane(var pos: Vector3D, var normal: Vector3D) {
  def findIntersection(ray: Ray): Vector3D = {
    val diff = ray.pos.subtract(pos)
    val prod1 = diff.dot(normal)
    val prod2 = ray.direction.dot(normal)
    val prod3 = prod1 / prod2
    ray.pos.subtract(ray.direction.multiply(prod3))
  }
}
