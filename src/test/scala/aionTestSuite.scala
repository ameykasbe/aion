import org.scalatest.funspec.AnyFunSpec
import aion.Expression.*
import aion.{BasicType, bindingScopeClassInstances}

import scala.collection.mutable.Set

class aionTestSuite extends AnyFunSpec{
  // Test Suite

  // Test Case 1
  describe("Value"){
    it("should evalute DLS contants with their correct Scala values") {
      assert(Val(3).evaluate() == 3)
    }
  }

  // Test Case 2
  describe("Assign and Variable"){
    it("should bind DSL variable with DLS contants") {
      Assign("simpleVariable", Val(3)).evaluate()
      assert(Var("simpleVariable").evaluate() == 3)
    }
  }


  describe("Insert"){
    // Test Case 3
    it("should bind DSL variable with DLS set and insert an element into the set") {
      Assign("Set1", Val(Set())).evaluate()
      Insert(Var("Set1"), Val(1)).evaluate()
      assert(Var("Set1").evaluate() == Set(1))
    }
    // Test Case 4
    it("should insert multiple elements into set") {
      Assign("Set2", Val(Set())).evaluate()
      Insert(Var("Set2"), Val(1), Val(2), Val(3)).evaluate()
      assert(Var("Set2").evaluate() == Set(1, 2, 3))
    }
  }

  // Test Case 5
  describe("Delete") {
    it("should delete an element from the set") {
      Assign("Set3", Val(Set())).evaluate()
      Insert(Var("Set3"), Val(1), Val(2), Val(3)).evaluate()
      Delete(Var("Set3"), Val(3)).evaluate()
      assert(Var("Set3").evaluate() == Set(1, 2))
    }
  }

  // Test Case 6
  describe("Union") {
    it("should return correct union of sets") {
      Assign("Set1", Val(Set())).evaluate()
      Insert(Var("Set1"), Val(1), Val(2), Val(3)).evaluate()
      Assign("Set2", Val(Set())).evaluate()
      Insert(Var("Set2"), Val(2), Val(3), Val(4)).evaluate()
      assert(Union(Var("Set1"), Var("Set2")).evaluate() == Set(1, 2, 3, 4))
    }
  }

  // Test Case 7
  describe("Intersect") {
    it("should return correct intersect of sets") {
      Assign("Set1", Val(Set())).evaluate()
      Insert(Var("Set1"), Val(1), Val(2), Val(3)).evaluate()
      Assign("Set2", Val(Set())).evaluate()
      Insert(Var("Set2"), Val(2), Val(3), Val(4)).evaluate()
      assert(Intersect(Var("Set1"), Var("Set2")).evaluate() == Set(2, 3))
    }
  }

  // Test Case 8
  describe("Set Difference") {
    it("should return correct set difference of sets") {
      Assign("Set1", Val(Set())).evaluate()
      Insert(Var("Set1"), Val(1), Val(2), Val(3)).evaluate()
      Assign("Set2", Val(Set())).evaluate()
      Insert(Var("Set2"), Val(2), Val(3), Val(4)).evaluate()
      assert(Difference(Var("Set1"), Var("Set2")).evaluate() == Set(1))
    }
  }

  // Test Case 9
  describe("Symmetric Difference") {
    it("should return correct set symmetric difference of sets") {
      Assign("Set1", Val(Set())).evaluate()
      Insert(Var("Set1"), Val(1), Val(2), Val(3)).evaluate()
      Assign("Set2", Val(Set())).evaluate()
      Insert(Var("Set2"), Val(2), Val(3), Val(4)).evaluate()
      assert(SymmetricDifference(Var("Set1"), Var("Set2")).evaluate() == Set(1, 4))
    }
  }


  // Test Case 10
  describe("Cartesian Product") {
    it("should return correct set cartesian product of sets") {
      Assign("Set1", Val(Set())).evaluate()
      Insert(Var("Set1"), Val(1), Val(2)).evaluate()
      Assign("Set2", Val(Set())).evaluate()
      Insert(Var("Set2"), Val(2), Val(3), Val(4)).evaluate()
      assert(CrossProduct(Var("Set1"), Var("Set2")).evaluate() == Set((1, 2), (1, 3), (1, 4),(2, 2), (2, 3), (2, 4)))
    }
  }

  // Test Case 11
  describe("Macro") {
    it("should assign and evaluate macro correctly") {
      Assign("Set1", Val(Set())).evaluate()
      Insert(Var("Set1"), Val(1), Val(2)).evaluate()
      Assign("Set2", Val(Set())).evaluate()
      Insert(Var("Set2"), Val(2), Val(3), Val(4)).evaluate()
      Macro("macro1", Union(Var("Set1"), Var("Set2"))).evaluate()
      assert(MacroEval("macro1").evaluate() == Set(1, 2, 3, 4))
    }
  }

    // Test Case 12
    describe("Check") {
      it("should check if elements are present in a set") {
        Assign("Set1", Val(Set())).evaluate()
        Insert(Var("Set1"), Val(1)).evaluate()
        assert(Check(Var("Set1"), Val(1)).evaluate() == true)
      }
      it("should check if elements are absent in a set") {
        Assign("Set1", Val(Set())).evaluate()
        Insert(Var("Set1"), Val(1)).evaluate()
        assert(Check(Var("Set1"), Val(2)).evaluate() == false)
      }
    }

    // Test case 13 - Scope
    describe("Scope") {
      it("should only access variables of global scope") {
        Assign("Set1", Val(Set(1, 2, 3))).evaluate()
        Assign("Set2", Val(Set(2, 3, 4))).evaluate()
        assert(Union(Var("Set1"), Var("Set2")).evaluate() == Set(1, 2, 3, 4))
      }
      it("should only access variables of one scope") {
        Assign("Set1", Val(Set(1, 2, 3))).evaluate("a")
        Assign("Set2", Val(Set(2, 3, 4))).evaluate("a")
        Assign("Set1", Val(Set(10, 20, 30))).evaluate("b")
        Assign("Set2", Val(Set(20, 30 ,40))).evaluate("b")
        assert(Union(Var("Set1"), Var("Set2")).evaluate("a") == Set(1, 2, 3, 4))
      }
      it("should only access variables of the particular scope") {
        Assign("Set1", Val(Set(1, 2, 3))).evaluate("a")
        Assign("Set2", Val(Set(2, 3, 4))).evaluate("a")
        Assign("Set1", Val(Set(10, 20, 30))).evaluate("b")
        Assign("Set2", Val(Set(20, 30 ,40))).evaluate("b")
        assert(Union(Var("Set1"), Var("Set2")).evaluate("b") == Set(10, 20, 30, 40))
      }
      it("should only access variables of the global scope if not found in a particular scope") {
        Assign("Set1", Val(Set(1, 2, 3))).evaluate()
        Assign("Set2", Val(Set(2, 3, 4))).evaluate()
        Assign("Set1", Val(Set(10, 20, 30))).evaluate("b")
        Assign("Set2", Val(Set(20, 30, 40))).evaluate("b")
        assert(Union(Var("Set1"), Var("Set2")).evaluate("a") == Set(1, 2, 3, 4))
      }
    }

    // Test case 14 - Nested Expressions
    describe("Nested Expressions") {
      it("should return correct evaluation of nested operations") {
        Assign("Set1", Val(Set(1, 2, 3))).evaluate()
        Assign("Set2", Val(Set(2, 3, 4))).evaluate()
        Assign("Set3", Val(Set(10, 20, 30))).evaluate()
        Assign("Set4", Val(Set(20, 30, 40))).evaluate()
        assert(Union(Union(Var("Set1"), Var("Set2")), Union(Var("Set3"), Var("Set4"))).evaluate() == Set(1, 2, 3, 4, 10, 20, 30, 40))
      }
    }

    // Test case 15 - Class definition and create new object and constructor
    describe("Class definition and create new object and constructor") {
      it("should return correct evaluation of constructor assign operation after creating new object") {
        ClassDef("class1", Field("field1"), Constructor(Assign("field1", Val(2))), Method("method1", Union(Val(Set(1, 2, 3)), Val(Set(2, 3, 4))))).evaluate()
        NewObject("object1", "class1").evaluate()
        assert(GetField("object1", "field1").evaluate() === 2)
      }
    }
  // Class def test
  //    println(bindingScopeClass)

  // New object test
  //
  //    println(bindingScopeClassInstances)

}
