package tests

import org.scalatest.funsuite.AnyFunSuite

class TestSuite extends AnyFunSuite {
  test("Abc") {
    assert(0 == 0)
  }
}

//import org.spec2.mutable._
//
//class Test extends Specification {
//  "The 'Hello world' string" should {
//    "contain 11 characters" in {
//      "Hello world" must have size (11)
//    }
//    "start with 'Hello'" in {
//      "Hello world" must startWith("Hello")
//    }
//    "end with 'world'" in {
//      "Hello world" must endWith("world")
//    }
//  }
//}
