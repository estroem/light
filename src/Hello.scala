import java.awt.image.{BufferedImage, ColorModel}

import world.{Camera, World}
import math.Vector3D
import math.Vector3D.{ORIGIN, UNIT_X, UNIT_Y, UNIT_Z}
import shapes.{Rectangle, Shape, Sphere}

object Hello {
  final val W = 800
  final val H = 500

  def main(args: Array[String]) = {
    val s1 = new Sphere(new Vector3D(0, 0, 0), 50, 1)
    val s2 = new Sphere(new Vector3D(0, 0, 100), 30, 0.5)

    val floor  = new Rectangle(new Vector3D(0, 50, 0), UNIT_Y, UNIT_X, 100, 50, 0.4)
    val leftW  = new Rectangle(new Vector3D(-100, 0, 0), UNIT_X, UNIT_Y, 100, 100, 0.5)
    val rightW = new Rectangle(new Vector3D(100, 0, 0), UNIT_X, UNIT_Y, 100, 100, 0.5)
    val backW  = new Rectangle(new Vector3D(0, 0, -50), UNIT_Z, UNIT_Y, 50, 100, 0.6)

    val shapes: Vector[Shape] = Vector(s1, floor, backW)
    val camera: Camera = new Camera(new Vector3D(-30, 30, 50), -UNIT_Z - UNIT_Y, UNIT_Y - UNIT_Z)
        .pan(-Math.PI / 4)
//    val camera: Camera = new Camera(new Vector3D(-30, 30, 50), new Vector3D(0, -0.70711, -0.70711), new Vector3D(0, 0.70711, -0.70711))
//    val camera: Camera = new Camera(new Vector3D(-30, 30, 50), -UNIT_Z ,UNIT_Y)
//        .pan((7 / 4) * Math.PI)
//        .tilt(7 * Math.PI / 4)

    assert(Math.abs(camera.direction dot camera.up) < 0.00001, "Camera vectors not orthogonal")

    val world = new World(W, H, shapes, camera)

    val data = FirstTry.render().run(world)

    draw(W, H, data)
  }

  def getFour(): State[World, Int] = State.ret(4)
  def getHeight(): State[World, Int] = State.get(a => a.h)

  def draw(w: Int, h: Int, data: Array[Byte]): Unit = {
    val convertedArray = convert(w, h, data)
    val colorModel = ColorModel.getRGBdefault
    val raster = colorModel.createCompatibleWritableRaster(w, h)
    raster.setDataElements(0, 0, w, h, convertedArray)
    val image = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY)
    image.setData(raster)

    ViewImage.viewImage(image, "asdf")
  }

  def convert(w: Int, h: Int, data: Array[Byte]): Array[Int] = {
    val newW = w / 4
    val array = new Array[Int](w * h)

    for(i <- 0 until h) {
      val offset = i * w

      for(j <- 0 until newW) {
        val index = offset + j * 4
        array(i * w + j) = ((data(index + 3) << 24) + (data(index) << 16) + (data(index + 1) << 8) + data(index + 2)) * 2
      }
    }

    array
  }
}
