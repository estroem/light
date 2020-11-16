import java.awt.image.{BufferedImage, ColorModel}

import math.Vector3D
import shapes.{Shape, Sphere}

object Hello {
  val w = 800
  val h = 500

  def main(args: Array[String]) = {
    //val data = runtest(w, h)

    val shapes: Vector[Shape] = Vector(new Sphere(new Vector3D(0, 0, 0), 20))

    val data = FirstTry.render(w, h, shapes)

    draw(w, h, data)
  }

  def runtest(w: Int, h: Int): Array[Byte] = {
    val data = new Array[Byte](w * h)

    val x = 490
    val y = 490
    data(x * w + y) = 127
    data(x * w + y + 1) = 127
    data(x * w + y + 2) = 127
    data(x * w + y + 3) = 127
    data(x * w + y + 4) = 127
    data((x+1) * w + y) = 127
    data((x+1) * w + y + 1) = 127
    data((x+1) * w + y + 2) = 127
    data((x+1) * w + y + 3) = 127
    data((x+1) * w + y + 4) = 127

    data
  }

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
    val newW = 125
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
