package math

import scala.math.{pow, sqrt}

class Vector3D(var x: Double, var y: Double, var z: Double) {
  def distanceTo(vec1: Vector3D): Double = sqrt(sqr(vec1.x - x) + sqr(vec1.y - y) + sqr(vec1.z - z))
  def sqr(a: Double): Double = pow(a, 2)
  def dot(vec: Vector3D): Double = x * vec.x + y * vec.y + z * vec.z
  def add(vec: Vector3D): Vector3D = new Vector3D(x + vec.x, y + vec.y, z + vec.z)
  def subtract(vec: Vector3D): Vector3D = new Vector3D(x - vec.x, y - vec.y, z - vec.z)
  def multiply(vec: Vector3D): Vector3D = new Vector3D(x * vec.x, y * vec.y, z * vec.z)
  def divide(vec: Vector3D): Vector3D = new Vector3D(x / vec.x, y / vec.y, z / vec.z)
  def add(num: Double): Vector3D = new Vector3D(x + num, y + num, z + num)
  def subtract(num: Double): Vector3D = new Vector3D(x - num, y - num, z - num)
  def multiply(num: Double): Vector3D = new Vector3D(x * num, y * num, z * num)
  def divide(num: Double): Vector3D = new Vector3D(x / num, y / num, z / num)
}
