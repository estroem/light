package math

import scala.math.{pow, sqrt}

class Vector3D(var x: Double, var y: Double, var z: Double) {
  def normalize: Vector3D = this / length
/*
  def rotateAround(v: Vector3D, radians: Double): Vector3D = v.normalize.rotationMatrix(radians) * this
*/

  def rotateAround(v: Vector3D, radians: Double): Vector3D =
    this * Math.cos(radians) + (v.normalize cross this) * Math.cos(radians) + v.normalize * (v.normalize dot this) * (1 - Math.cos(radians))

  def rotateAroundYAxis(radians: Double): Vector3D =
    new Vector3D(x * Math.cos(radians) + z * Math.sin(radians), y, -x * Math.sin(radians) + z * Math.cos(radians))

  def rotateAroundXAxis(radians: Double): Vector3D =
    new Vector3D(x, y * Math.cos(radians) - z * Math.sin(radians), y * Math.sin(radians) + z * Math.cos(radians))

  def rotationMatrix(radians: Double): Matrix3D = Matrix3D.IDENTITY + (W* Math.sin(radians)) + (W.square * 2 * Math.pow(Math.sin(radians / 2), 2))
  def W = new Matrix3D(new Vector3D(0, z, -y), new Vector3D(-z, 0, x), new Vector3D(y, -x, 0))

  def dist(vec1: Vector3D): Double = sqrt(pow(vec1.x - x, 2) + pow(vec1.y - y, 2) + pow(vec1.z - z, 2))
  def +(v: Vector3D) = new Vector3D(x + v.x, y + v.y, z + v.z)
  def -(v: Vector3D) = new Vector3D(x - v.x, y - v.y, z - v.z)
  def +(s: Double) = new Vector3D(x + s, y + s, z + s)
  def -(s: Double) = new Vector3D(x - s, y - s, z - s)
  def *(s: Double) = new Vector3D(x * s, y * s, z * s)
  def /(s: Double) = new Vector3D(x / s, y / s, z / s)
  def unary_- = Vector3D.ORIGIN - this
  def dot(v: Vector3D): Double = x * v.x + y * v.y + z * v.z
  def cross(v: Vector3D): Vector3D = new Vector3D(y * v.z - z * v.y, x * v.z - z * v.x, x * v.y - y * v.x)
  def length: Double = sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2))
  override def toString = s"($x, $y, $z)"
}

object Vector3D {
  final val ORIGIN = new Vector3D(0, 0, 0)
  final val UNIT_X = new Vector3D(1, 0, 0)
  final val UNIT_Y = new Vector3D(0, 1, 0)
  final val UNIT_Z = new Vector3D(0, 0, 1)

  def fromScalar(d: Double): Vector3D = new Vector3D(d, d, d)
}
