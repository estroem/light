package shapes
import math.{Plane, Ray, Vector3D}

class Rectangle(pos: Vector3D, normal: Vector3D, parallel: Vector3D, height: Double, width: Double, val brightness: Double) extends Shape {
  override def distanceTo(ray: Ray): Option[Double] =
    Option.apply(intersection(ray)).filter(contains).map(ray.pos.dist)

  def contains(point: Vector3D): Boolean =
    counterParallelRay.distanceTo(point) <= height && parallelRay.distanceTo(point) <= width

  def counterParallel: Vector3D = normal cross parallel
  def counterParallelRay: Ray = new Ray(pos, counterParallel)

  def parallelRay: Ray = new Ray(pos, parallel)

  def plane: Plane = new Plane(pos, normal)
  def intersection: Ray => Vector3D = plane.intersection
}
