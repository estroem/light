package world

import math.{Ray, Vector3D}

class Camera(override val pos: Vector3D, override val direction: Vector3D, val up: Vector3D) extends Ray(pos, direction) {
  def pan(radians: Double) = new Camera(pos, direction.rotateAroundYAxis(radians), up.rotateAroundYAxis(radians))
  def tilt(radians: Double) = new Camera(pos, direction.rotateAround(right, radians), up.rotateAround(right, radians))

  def right: Vector3D = direction cross up
}
