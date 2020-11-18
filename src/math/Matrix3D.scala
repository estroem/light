package math

class Matrix3D(val v1: Vector3D, val v2: Vector3D, val v3: Vector3D) {
  def +(s: Double) = new Matrix3D(v1 + s, v2 + s, v3 + s)
  def -(s: Double) = new Matrix3D(v1 - s, v2 - s, v3 - s)
  def *(s: Double) = new Matrix3D(v1 * s, v1 * s, v1 * s)
  def /(s: Double) = new Matrix3D(v1 / s, v1 / s, v1 / s)
  def +(m: Matrix3D) = new Matrix3D(v1 + m.v1, v2 + m.v2, v3 + m.v3)
  def *(m: Matrix3D) = new Matrix3D(new Vector3D(row1 dot m.v1, row2 dot m.v1, row3 dot m.v1), new Vector3D(row1 dot m.v2, row2 dot m.v2, row3 dot m.v3), new Vector3D(row1 dot m.v3, row2 dot m.v3, row3 dot m.v3))
  def *(v: Vector3D) = new Vector3D(row1 dot v, row2 dot v, row3 dot v)

  def square: Matrix3D = this * this

  def row1 = new Vector3D(v1.x, v2.x, v3.x)
  def row2 = new Vector3D(v1.y, v2.y, v3.z)
  def row3 = new Vector3D(v1.z, v2.z, v3.z)
}

object Matrix3D {
  final val IDENTITY = new Matrix3D(Vector3D.UNIT_X, Vector3D.UNIT_Y, Vector3D.UNIT_Z)
}