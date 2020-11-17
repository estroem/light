class State[A, B](val func: A => (A, B)) {
  def map[C](f: B => C): State[A, C] = new State(x => { val (a, b) = func(x); (a, f(b)) })
  def flatMap[C](f: B => State[A, C]): State[A, C] = new State(x => { val (a, b) = func(x); f(b).func(a) })
  def run(inp: A): B = func(inp)._2

  def >>=[C](s: B => State[A, C]): State[A, C] = flatMap(s)
}

object State {
  def create[A](start: A): State[A, _] = new State(_ => (start, null))
  def ret[A, B](value: B): State[A, B] = new State(inp => (inp, value))
  def get[A, B](f: A => B): State[A, B] = new State(inp => (inp, f(inp)))

  def binApp[A, B, C, D](f: (A, B) => C, state1: State[D, A], state2: State[D, B]): State[D, C] = for {
    one <- state1
    two <- state2
  } yield f(one, two)

  def <*>[A, B, C, D](f: (B, C) => D): (State[A, B], State[A, C]) => State[A, D] = (x, y) => for {
    one <- x
    two <- y
  } yield f(one, two)

  def <*>[A, B, C, D](f: B => C => D): State[A, B] => State[A, C] => State[A, D] = x => y => for {
    one <- x
    two <- y
  } yield f(one)(two)
}
