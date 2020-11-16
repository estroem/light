package math

class Plane(var pos: Vector3D, var normal: Vector3D) {
  def intersection(ray: Ray): Vector3D =
    ray.pos - ray.direction * ((ray.pos - pos) dot normal) / (ray.direction dot normal)
}
