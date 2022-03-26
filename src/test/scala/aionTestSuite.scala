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

    // HOMEWORK 2 TEST CASES
    // Test case 15 - Class definition and create new object and constructor
    describe("Class definition and create new object and constructor") {
      it("should return correct evaluation of constructor assign operation after creating new object") {
        ClassDef("class1", Public(Field("field1")), Constructor(Assign("field1", Val(1))), Public(Method("method1", List("p1", "p2"), Union(Var("p1"), Var("p2"))))).evaluate()
        NewObject("object1", "class1").evaluate()
        assert(GetField("object1", "field1").evaluate() === 1)
      }
    }


  // Test case 16 - Invoke method
  describe("Invoke method") {
    it("should return correct evaluation of method with correct parameters") {
      ClassDef("class2", Public(Field("field2")), Constructor(Assign("field2", Val(1))), Public(Method("method2", List("p1", "p2"), Union(Var("p3"), Var("p4"))))).evaluate()
      NewObject("object2", "class2").evaluate()
      val result = InvokeMethod("object2", "method2", Assign("p3", Val(Set(1, 2, 3))), Assign("p4", Val(Set(1, 2, 4)))).evaluate()
      assert(result == Set(1, 2, 3, 4))
    }
  }

  // Test case 17
  describe("Inheritance1") {
    it("child should return its own field correctly") {
      ClassDef("parentClass", Public(Field("parentField")), Constructor(Assign("parentField", Val(1)))).evaluate()
      ClassDef("childClass", Public(Field("childField")), Constructor(Assign("childField", Val(2)))) Extends "parentClass"
      NewObject("childObject", "childClass").evaluate()
      assert(GetField("childObject", "childField").evaluate() === 2)
    }
  }

  // Test case 18
  describe("Inheritance2") {
    it("child should contain its parents fields with null value") {
      ClassDef("parentClass1", Public(Field("parentField1")), Constructor(Assign("parentField1", Val(1)))).evaluate()
      ClassDef("childClass1", Public(Field("childField1")), Constructor(Assign("childField1", Val(2)))) Extends "parentClass1"
      NewObject("childObject1", "childClass1").evaluate()
      assert(GetField("childObject1", "parentField1").evaluate() == null)
    }
  }

  // Test case 19
  describe("Inheritance3") {
    it("child should be able to take parent's protected fields") {
      ClassDef("parentClass2", Protected(Field("parentField2")), Constructor(Assign("parentField2", Val(1)))).evaluate()
      ClassDef("childClass2", Public(Field("childField2")), Constructor(Assign("childField2", Val(2)))) Extends "parentClass2"
      NewObject("childObject2", "childClass2").evaluate()
      assert(GetField("childObject2", "parentField2").evaluate() == null)
    }
  }

  // Test case 20
  describe("Abstract Class1") {
    it("child should be able to inherit abstract class (parent) attributes as null") {
      AbstractClassDef("parentClass3", Public(Field("parentField")), Constructor(Assign("parentField", Val(1))), Public(Method("method1", List("p1", "p2"), Union(Var("p1"), Var("p2")))), Protected(AbstractMethod("methodParent", List("p1", "p2")))).evaluate()
      ClassDef("childClass3", Constructor(Assign("childField2", Val(2))), Public(Method("methodParent", List("p1", "p2"), Difference(Var("p1"), Var("p2"))))) Extends "parentClass3"
      NewObject("childObject3", "childClass3").evaluate()
      assert(GetField("childObject3", "parentField").evaluate() == null)
    }
  }

  // Test case 21
  describe("Abstract Class2") {
    it("child should be able to override abstract class (parent) abstract method") {
      AbstractClassDef("parent5",  Public(Field("parentField")), Protected(AbstractMethod("methodParent", List("p1", "p2")))).evaluate()
      ClassDef("child5",  Public(Field("parentField")), Constructor(Assign("parentField", Val(1))), Protected(Method("methodParent", List("p1", "p2"), Difference(Var("p3"), Var("p4"))))) Extends "parent5"
      NewObject("childObject5", "child5").evaluate()
      val result = InvokeMethod("childObject5", "methodParent", Assign("p3", Val(Set(1, 2, 3))), Assign("p4", Val(Set(1, 2, 4)))).evaluate()
      assert(result == Set(3))
    }
  }

  // Test case 22
  describe("Interface1") {
    it("child should be able to inherit interface attributes as null") {
      Interface("parentClass6", Public(Field("parentField")), Protected(AbstractMethod("methodParent", List("p1", "p2")))).evaluate()
      ClassDef("childClass6", Constructor(Assign("childField2", Val(2))), Public(Method("methodParent", List("p1", "p2"), Difference(Var("p1"), Var("p2"))))) Implements "parentClass6"
      NewObject("childObject6", "childClass6").evaluate()
      assert(GetField("childObject6", "parentField").evaluate() == null)
    }
  }

  // Test case 23
  describe("Interface2") {
    it("child should be able to override interface's abstract method") {
      Interface("parent7",  Public(Field("parentField")), Protected(AbstractMethod("methodParent", List("p1", "p2")))).evaluate()
      ClassDef("child7",  Public(Field("parentField")), Constructor(Assign("parentField", Val(1))), Protected(Method("methodParent", List("p1", "p2"), Difference(Var("p3"), Var("p4"))))) Implements "parent7"
      NewObject("childObject7", "child7").evaluate()
      val result = InvokeMethod("childObject7", "methodParent", Assign("p3", Val(Set(1, 2, 3))), Assign("p4", Val(Set(1, 2, 4)))).evaluate()
      assert(result == Set(3))
    }
  }

  // Test case 24
  describe("Abstract class 3") {
    it("Abstract class's instance should not be created") {
      AbstractClassDef("parent8",  Public(Field("parentField")), Protected(AbstractMethod("methodParent", List("p1", "p2")))).evaluate()
      val result = NewObject("childObject8", "parent8").evaluate()
      assert(result == "Can not create an instance of an abstract class or an interface.")
    }
  }

  // Test case 25
  describe("Interface 3") {
    it("Interface's instance should not be created") {
      Interface("parent9",  Public(Field("parentField")), Protected(AbstractMethod("methodParent", List("p1", "p2")))).evaluate()
      val result = NewObject("childObject9", "parent9").evaluate()
      assert(result == "Can not create an instance of an abstract class or an interface.")
    }
  }

}
