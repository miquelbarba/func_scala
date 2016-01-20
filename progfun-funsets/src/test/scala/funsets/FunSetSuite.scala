package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */
  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val s5 = singletonSet(5)
    val s7 = singletonSet(7)
    val s1000 = singletonSet(1000)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }
  
  test("diff") {
    new TestSets {
      val s = union(s1, s2)
      val t = union(s1, s3)
      val res = diff(s, t)

      assert(!contains(res, 1), "diff1")
      assert(contains(res, 2), "diff2")
      assert(!contains(res, 3), "diff3")
    }
  }
  
  test("filter") {
    new TestSets {
      val s = union(union(union(union(s1, s2), s3), s4), s7)
      val res = filter(s, x => x % 2 == 0)

      assert(!contains(res, 1), "filter1")
      assert(contains(res, 2), "filter2")
      assert(!contains(res, 3), "filter3")
      assert(contains(res, 4), "filter4")
      assert(!contains(res, 5), "filter5")
      assert(!contains(res, 6), "filter6")
      assert(!contains(res, 7), "filter7")
    }
  }
  
  test("failed  given {1,3,4,5,7,1000}") {
    new TestSets {
      val s = union(union(union(union(union(s1, s3), s4), s5), s7), s1000)
      assert(!exists(s, x => x == 2))
    }
  }

  test("forall: odds") {
    new TestSets {
      val s = union(s1, union(s3, union(s5, s7)))
      
      assert(forall(s, (x) => x % 2 == 1), true)
    }
  }    
  
  test("exists & filter: even") {
    new TestSets {
      val s = union(s2, union(s4, union(s1000, s7)))
      
      assert(forall(s, (x) => x % 2 == 0), true)
    }
  }  
  
  test("{1,3,4,5,7,1000}< 5") {
    new TestSets {
      val s = union(union(union(union(union(s1, s3), s4), s5), s7), s1000)
      assert(!forall(s, (x) => x < 5))
    }
  }
  
  test("exists: {1,3,4,5,7,1000}") {
    new TestSets {
      val s = union(s1, union(s3, union(s4, union(s5, union(s7, s1000)))))

      assert(exists(s, (x) => x == 1))
      assert(!exists(s, (x) => x == 2))
      assert(exists(s, (x) => x == 7))
      assert(!exists(s, (x) => x == 8))
      assert(exists(s, (x) => x == 1000))
      assert(!exists(s, (x) => x == 1001))
    }
  }  
 
  test("map function test"){
	new TestSets{
	  val s = union(union(s1, s2), s3)
      val sA = map(s, x => x * 2)
      assert(contains(sA, 6))
      assert(!contains(sA, 3))
    }
  }  
}
