package shapes

import math.Ray

trait Shape {
  def distanceTo(ray: Ray): Option[Double]
  val brightness: Double
}
