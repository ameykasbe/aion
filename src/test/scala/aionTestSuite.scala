import org.scalatest.funspec.AnyFunSpec

import scala.aion.Expression.*
import scala.collection.mutable.Set

class aionTestSuite extends AnyFunSpec{
  // Test Suite

  // Test Case 1
  describe("Value"){
    it("should evalute DLS contants with their correct Scala values") {
      assert(Val(3).evaluate == 3)
    }
  }

  // Test Case 2
  describe("Assign and Variable"){
    it("should bind DSL variable with DLS contants") {
      Assign("simpleVariable", Val(3)).evaluate
      assert(Var("simpleVariable").evaluate == 3)
    }
  }


  describe("Insert"){
    // Test Case 3
    it("should bind DSL variable with DLS set and insert an element into the set") {
      Assign("Set1", Val(Set())).evaluate
      Insert(Var("Set1"), Val(1)).evaluate
      assert(Var("Set1").evaluate == Set(1))
    }
    // Test Case 4
    it("should insert multiple elements into set") {
      Assign("Set2", Val(Set())).evaluate
      Insert(Var("Set2"), Val(1), Val(2), Val(3)).evaluate
      assert(Var("Set2").evaluate == Set(1, 2, 3))
    }
  }

  // Test Case 5
  describe("Delete") {
    it("should delete an element from the set") {
      Assign("Set3", Val(Set())).evaluate
      Insert(Var("Set3"), Val(1), Val(2), Val(3)).evaluate
      Delete(Var("Set3"), Val(3)).evaluate
      assert(Var("Set3").evaluate == Set(1, 2))
    }
  }

  // Test Case 6
  describe("Union") {
    it("should return correct union of sets") {
      Assign("Set1", Val(Set())).evaluate
      Insert(Var("Set1"), Val(1), Val(2), Val(3)).evaluate
      Assign("Set2", Val(Set())).evaluate
      Insert(Var("Set2"), Val(2), Val(3), Val(4)).evaluate
      assert(Union(Var("Set1"), Var("Set2")).evaluate == Set(1, 2, 3, 4))
    }
  }

  // Test Case 7
  describe("Intersect") {
    it("should return correct intersect of sets") {
      Assign("Set1", Val(Set())).evaluate
      Insert(Var("Set1"), Val(1), Val(2), Val(3)).evaluate
      Assign("Set2", Val(Set())).evaluate
      Insert(Var("Set2"), Val(2), Val(3), Val(4)).evaluate
      assert(Intersect(Var("Set1"), Var("Set2")).evaluate == Set(2, 3))
    }
  }

  // Test Case 8
  describe("Set Difference") {
    it("should return correct set difference of sets") {
      Assign("Set1", Val(Set())).evaluate
      Insert(Var("Set1"), Val(1), Val(2), Val(3)).evaluate
      Assign("Set2", Val(Set())).evaluate
      Insert(Var("Set2"), Val(2), Val(3), Val(4)).evaluate
      assert(Difference(Var("Set1"), Var("Set2")).evaluate == Set(1))
    }
  }

  // Test Case 9
  describe("Symmetric Difference") {
    it("should return correct set symmetric difference of sets") {
      Assign("Set1", Val(Set())).evaluate
      Insert(Var("Set1"), Val(1), Val(2), Val(3)).evaluate
      Assign("Set2", Val(Set())).evaluate
      Insert(Var("Set2"), Val(2), Val(3), Val(4)).evaluate
      assert(SymmetricDifference(Var("Set1"), Var("Set2")).evaluate == Set(1, 4))
    }
  }


  // Test Case 10
  describe("Cartesian Product") {
    it("should return correct set cartesian product of sets") {
      Assign("Set1", Val(Set())).evaluate
      Insert(Var("Set1"), Val(1), Val(2)).evaluate
      Assign("Set2", Val(Set())).evaluate
      Insert(Var("Set2"), Val(2), Val(3), Val(4)).evaluate
      assert(CrossProduct(Var("Set1"), Var("Set2")).evaluate == Set((1, 2), (1, 3), (1, 4),(2, 2), (2, 3), (2, 4)))
    }
  }

  // Test Case 11
  describe("Macro") {
    it("should assign and evaluate macro correctly") {
      Assign("Set1", Val(Set())).evaluate
      Insert(Var("Set1"), Val(1), Val(2)).evaluate
      Assign("Set2", Val(Set())).evaluate
      Insert(Var("Set2"), Val(2), Val(3), Val(4)).evaluate
      Macro(Val("macro1"), Union(Var("Set1"), Var("Set2"))).evaluate
      assert(MacroEval(Val("macro1")).evaluate == Set(1, 2, 3, 4))
    }
  }






}


//  Insert(Var("Set1"), Val(1)).evaluate
//
//
//  Assign("Set2", Val(Set())).evaluate
//
//  Insert(Var("Set1"), Val(2)).evaluate
//  Insert(Var("Set1"), Val(3)).evaluate
//  Insert(Var("Set2"), Val(2)).evaluate
//  Insert(Var("Set2"), Val(3)).evaluate
//  Insert(Var("Set2"), Val(4)).evaluate
//  Insert(Var("Set2"), Val(2), Val(6), Val(7)).evaluate
//  println(Var("Set1").evaluate)
//  println(Var("Set2").evaluate)
//  println(Union(Difference(Var("Set1"), (Var("Set2"))), Difference(Var("Set2"), (Var("Set1"))) ).evaluate)



  //    println((Var("Set1").evaluate))
  //    println((Var("Set2").evaluate))
  //    println(Union(Var("Set1"), Var("Set2")).evaluate)
  //    println(Union(Var("Set2"), Var("Set1")).evaluate)
  //    println(Union(Var("Amey"), Var("Set1")).evaluate)
  //    println(Union(Var("Amey"), Var("Amey2")).evaluate)

  //    println(Intersect(Var("Set1"), Var("Set2")).evaluate)
  //    println(Intersect(Var("Set2"), Var("Set1")).evaluate)
  //    println(Intersect(Var("Amey"), Var("Set1")).evaluate)
  //    println(Intersect(Var("Amey"), Var("Amey2")).evaluate)
  //
  //    println(Difference(Var("Set1"), Var("Set2")).evaluate)
  //    println(Difference(Var("Set2"), Var("Set1")).evaluate)
  //    println(Difference(Var("Amey"), Var("Set1")).evaluate)
  //    println(Difference(Var("Amey"), Var("Amey2")).evaluate)
  //
  //    println(SymmetricDifference(Var("Set1"), Var("Set2")).evaluate)
  //    println(SymmetricDifference(Var("Set2"), Var("Set1")).evaluate)
  //    println(SymmetricDifference(Var("Amey"), Var("Set1")).evaluate)
  //    println(SymmetricDifference(Var("Amey"), Var("Amey2")).evaluate)


  //    println((Var("Set1").evaluate))
  //    println((Var("Set2").evaluate))
  //    println(CrossProduct(Var("Set1"), Var("Set2")).evaluate)
  //    println(CrossProduct(Var("Set2"), Var("Set1")).evaluate)
  //    println(CrossProduct(Var("Amey"), Var("Set1")).evaluate)
  //    println(CrossProduct(Var("Bobby"), Var("Amey")).evaluate)





