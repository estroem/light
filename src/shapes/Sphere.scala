package shapes

import math.{Ray, Plane, Vector3D}
import scala.math.{sqrt, pow}

class Sphere(pos: Vector3D, radius: Double) extends Shape {
  override def distanceTo(ray: Ray): Option[Double] = {
    val intersection = getPlanePerpendicularTo(ray.direction).intersection(ray)
    val distanceFromCenterToIntersection = pos.dist(intersection)

    if(distanceFromCenterToIntersection > radius) {
      return Option.empty
    }

    val distanceFromLineStartToIntersection = ray.pos.dist(intersection)
    val otherDistance = sqrt(pow(radius, 2) + pow(distanceFromCenterToIntersection, 2))

    Option.apply(distanceFromLineStartToIntersection - otherDistance)
  }

  private def getPlanePerpendicularTo(vec: Vector3D): Plane = new Plane(pos, vec)
}
