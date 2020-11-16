import math.{Ray, Vector3D}

class Camera(override val pos: Vector3D, override val direction: Vector3D) extends Ray(pos, direction) {

}
