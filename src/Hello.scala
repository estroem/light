import java.awt.image.{BufferedImage, ColorModel, DataBuffer, Raster}

object Hello {
  def main(args: Array[String]) = {
/*
    val w: Int = 500
    val h: Int = 500
    val data = new Array[Int](w * h)

    val i = 100
    val j = 100

    data(i * w + j) = 0x55555555
    data((i + 2) * w + j) = 0x55555555
    data((i + 3) * w + j) = 0x55555555
    data((i + 4) * w + j) = 0x55555555
    data((i + 5) * w + j) = 0x55555555
    data((i + 6) * w + j) = 0x55555555

    // 4 -> 3; 3 -> 2; 2 -> 1; 1 -> 4

    /*
    for(y <- 0 until h) {
      val off = multiply(y, w)

      for(x <- 0 until w) {
        data(sum(x, off)) = mod(multiply(x, y), Int.MaxValue)
      }
    }*/

    val colorModel = ColorModel.getRGBdefault
    val raster = colorModel.createCompatibleWritableRaster(w, h)
    raster.setDataElements(0, 0, w, h, data)
    image.setData(raster)
*/

    val w = 600
    val h = 500
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

    draw(w, h, data)
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

  def multiply(m: Int, n: Int): Int = m * n

  def sum(m: Int, n: Int): Int = m + n

  def mod(m: Int, n: Int): Int = m % n

}
