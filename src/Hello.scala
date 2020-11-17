import java.awt.image.{BufferedImage, ColorModel}

import world.{Camera, World}
import math.Vector3D
import shapes.{Shape, Sphere}

object Hello {
  final val W = 800
  final val H = 500

  def main(args: Array[String]) = {
    val s1 = new Sphere(new Vector3D(50, 50, -20), 20, 1)
    val s2 = new Sphere(new Vector3D(0, 0, 0), 30, 0.5)

    val shapes: Vector[Shape] = Vector(s1, s2)
    val camera: Camera = new Camera(new Vector3D(0, 0, 0), new Vector3D(0, 0, -1))

    val world = new World(W, H, shapes, camera)

    val data = FirstTry.render3().run(world)

//    val data = FirstTry.render(W, H, shapes, camera)

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
