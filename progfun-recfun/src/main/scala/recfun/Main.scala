package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = 
    if (r == 0 || c == 0) 1
    else if (r == c) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = { 
    def balanceHelper(chars: List[Char], open: Int) : Boolean =
      if (chars.isEmpty) open == 0
      else if (chars.head == ')' && open <= 0) false
      else if (chars.head == ')') balanceHelper(chars.tail, open - 1)
      else if (chars.head == '(') balanceHelper(chars.tail, open + 1)
      else balanceHelper(chars.tail, open)
  
    balanceHelper(chars, 0)  
  }
  
  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def countChangeHelper(money: Int, coins: List[Int], num: Int) : Int =
      if (money < 0 || coins.isEmpty) num
      else if (money == 0) num + 1
      else countChangeHelper(money - coins.head, coins, num) + 
    	   countChangeHelper(money, coins.tail, num)
    	 
    if (money == 0) 0
    else countChangeHelper(money, coins, 0)
  }    	 
}
