import shapes.Shape
import world.{Camera, World}

class Scene[B](override val func: World => (World, B)) extends State[World, B](func) {}

object Scene {
  def getHeight: Scene[Int] = new Scene(world => (world, world.h))
  def getWidth: Scene[Int] = new Scene(world => (world, world.w))
  def getObjects: Scene[Vector[Shape]] = new Scene(world => (world, world.objects))
  def getCamera: Scene[Camera] = new Scene(world => (world, world.camera))
}