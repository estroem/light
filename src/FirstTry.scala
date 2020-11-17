import world.{Camera, World}
import math.{Ray, Vector3D}
import shapes.Shape

object FirstTry {
  def render3(): State[World, Array[Byte]] = render3(0)

  def render3(startRow: Int): State[World, Array[Byte]] = for {
    height <- Scene.getHeight
    data <- if (startRow < height) for {
      data <- render3Row(startRow, 0)
      data2 <- render3(startRow + 1)
    } yield data ++ data2 else State.ret[World, Array[Byte]](Array())
  } yield data

  def render3Row(rowIx: Int, startCol: Int): State[World, Array[Byte]] = for {
    width <- Scene.getWidth
    data <- if (startCol < width) for {
      data <- render3Cell(rowIx, startCol)
      data2 <- render3Row(rowIx, startCol + 1)
    } yield data +: data2 else State.ret[World, Array[Byte]](Array())
  } yield data

  def render3Cell(rowIx: Int, colIx: Int): State[World, Byte] = for {
    cameraRay <- getCameraRay(rowIx, colIx)
    objsInSight <- getObjectsTouchedByRay(cameraRay)
    obj <- if(objsInSight.nonEmpty)
      State.ret[World, Option[Shape]](Option.apply(objsInSight.minBy(o => o.distanceTo(cameraRay).get)))
    else State.ret[World, Option[Shape]](Option.empty)
  } yield if (obj.nonEmpty) (obj.get.brightness * 127).asInstanceOf[Byte] else 0

  def getObjectsTouchedByRay(ray: Ray): State[World, Vector[Shape]] =
    Scene.getObjects.map(objs => objs.filter(o => o.distanceTo(ray).nonEmpty))

  def getCameraRay(rowIx: Int, colIx: Int): State[World, Ray] = for {
    rayPos <- camPos.flatMap(moveCamToPos(colIx, rowIx))
    rayDir <- Scene.getCamera.map(cam => cam.direction)
  } yield new Ray(rayPos, rayDir)

/*
  def render(w: Int, h: Int, objects: Vector[Shape], camera: Camera): Array[Byte] =
    render(w, h, objects, camera, 0)

  def render(w: Int, h: Int, objects: Vector[Shape], camera: Camera, startRow:Int): Array[Byte] =
    if(startRow < h) renderRow(w, h, objects, camera, startRow, 0) ++ render(w, h, objects, camera, startRow+1)
    else Array(0)

  def renderRow(w: Int, h: Int, objects: Vector[Shape], camera: Camera, rowIx:Int, startCol: Int): Array[Byte] =
    if(startCol < w) renderCell(w, h, objects, camera, rowIx, startCol) ++ renderRow(w, h, objects, camera, rowIx, startCol + 1)
    else Array(0)

  def renderCell(w: Int, h: Int, objects: Vector[Shape], camera: Camera, rowIx:Int, colIx: Int): Array[Byte] = for {

  }
*/
  def camPos: State[World, Vector3D] = Scene.getCamera.map(cam => cam.pos).flatMap(moveCamToCenter)

  def moveCamToCenter(pos: Vector3D): State[World, Vector3D] =
    State.binApp((a: Int, b: Int) => new Vector3D(a / 2, b / 2, 0), Scene.getWidth, Scene.getHeight).map(p => pos - p)

  def moveCamToPos(x: Int, y: Int)(pos: Vector3D): State[World, Vector3D] =
    State.ret(new Vector3D(x, y, 0)).map(p => p + pos)
/*
  def camPos(w: Int, h: Int, camera: Camera): Vector3D = camera.pos - new Vector3D(w / 2, h / 2, 0)  + new Vector3D(rowIx, colIx, 0)

  def render2(w: Int, h: Int, objects: Vector[Shape], camera: Camera): Array[Byte] = {
    val data = new Array[Byte](w * h)

    val adjuster = new Vector3D(w / 2, h / 2, 0)

    for(y <- 0 until h) {
      for(x <- 0 until w) {
        val pos = camera.pos + new Vector3D(x, y, 0) - adjuster
        val ray = new Ray(pos, camera.direction)

        val objsInSight = objects.filter(o => o.distanceTo(ray).nonEmpty)
        val obj = if(objsInSight.nonEmpty) Option.apply(objsInSight.minBy(o => o.distanceTo(ray).get)) else Option.empty

        if(obj.nonEmpty) {
          data(y * w + x) = (obj.get.brightness * 127).asInstanceOf[Byte]
        }
      }
    }

    data
  }*/
}
