import world.{Camera, World}
import math.{Ray, Vector3D}
import shapes.Shape

object FirstTry {
  def render(): State[World, Array[Byte]] = render(0)

  def render(startRow: Int): State[World, Array[Byte]] = for {
    height <- Scene.getHeight
    data <- if (startRow < height) for {
      data <- renderRow(startRow, 0)
      data2 <- render(startRow + 1)
    } yield data ++ data2 else State.ret[World, Array[Byte]](Array())
  } yield data

  def renderRow(rowIx: Int, startCol: Int): State[World, Array[Byte]] = for {
    width <- Scene.getWidth
    data <- if (startCol < width) for {
      data <- renderCell(rowIx, startCol)
      data2 <- renderRow(rowIx, startCol + 1)
    } yield data +: data2 else State.ret[World, Array[Byte]](Array())
  } yield data

  def renderCell(rowIx: Int, colIx: Int): State[World, Byte] = for {
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

  def camPos: State[World, Vector3D] = Scene.getCamera.map(cam => cam.pos).flatMap(moveCamToCenter)

  def moveCamToCenter(pos: Vector3D): State[World, Vector3D] =
    State.binApp((a: Int, b: Int) => new Vector3D(a / 2, b / 2, 0), Scene.getWidth, Scene.getHeight).map(p => pos - p)

  def moveCamToPos(x: Int, y: Int)(pos: Vector3D): State[World, Vector3D] =
    State.ret(new Vector3D(x, y, 0)).map(p => p + pos)
}
