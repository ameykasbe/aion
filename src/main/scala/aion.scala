import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable.{Map, Set}
import scala.runtime.BoxedUnit

object aion:
  // Creating a private HashMap to store and map variables to values.
  // Represents memory of the DSL.
  private val bindingScope: scala.collection.mutable.Map[BasicType, BasicType] = Map()

  // Define BasicType
  type BasicType = Any

  // Creating a logger instance to log events
  val logger: Logger = LoggerFactory.getLogger(this.getClass.getSimpleName)

  enum Expression:
    // Expression enum. Representing all the functionalies of DSL - Constant, Variable, expressions etc.
    // In the documentation and comments, "expression" denotes a constant, variable or any other expression from the Expression enum
    case Val(value: BasicType) // A constant
    case Var(name: String) // A variable
    case Assign(name: BasicType, value: Expression) // Maps a variable to an expression
    case Insert(setToInsert: Expression, value: Expression*) // Inserts a number of DSL expressions into a set
    case Check(setName: Expression, value: Expression) // Checks if a value is present in a set. Returns a boolean
    case Delete(setToInsert: Expression, value: Expression) // Deletes an DSL expression from a set
    case Union(setName1: Expression, setName2: Expression) // Returns the union of sets
    case Intersect(setName1: Expression, setName2: Expression) // Returns the intersection of a set
    case Difference(setName1: Expression, setName2: Expression) // Returns the difference of a set
    case SymmetricDifference(setName1: Expression, setName2: Expression) // Returns the symmetric difference of a set
    case CrossProduct(setName1: Expression, setName2: Expression) // Returns the cross product of a set
    case Macro(macroName: String, operand: Expression) // Creates a Macro
    case MacroEval(macroName: String) // Evaluates a Macro

    def evaluate(scopeName: String = "global"): BasicType =
    // Evaluates an expression. Accepts an argument scopeName representing scope with default value as global.
      this.match {

        // Val(value): `Val` is the constant datatype for the DSL. `value` can be of any datatype of language Scala. Evaluating the expression `Val(value)` returns value.
        case Val(value) => value

        // Var(value): `Var` is the variable datatype for the DSL.
        // `name` is string of language Scala.
        // Evaluating the expression Var(name) returns the mapped value of Var(name) from HashMap.
        // If there is no binding of `name` in the HashMap, it displays an error and exits the program.
        // In short, `name` in DSL represents the variable name in any language. `bindingScope` represents memory.
        // In the DSL, returning value from HashMap represents reading from memory location.

        // If scope is not global, before searching for bindings, the scope name is concatenated with the name and searched in the binding.
        // If not found in a particular scope, the binding is searched in global. If not found there, error is displayed and program is executed.
        case Var(name) => {
          if (scopeName == "global") {
            if (bindingScope.contains(name)) {
              bindingScope(name)
            }
            else {
              logger.error(s"Name $name not assigned.")
              System.exit(1)
            }
          }
          else{
            val varNameWScope = scopeName + name
            if (bindingScope.contains(varNameWScope)) {
              bindingScope(varNameWScope)
            }
            else {
              if (bindingScope.contains(name)) {
                bindingScope(name)
              }
              else{
                logger.error(s"Name $name not assigned in scope $scopeName or in global.")
                System.exit(1)
              }
            }
          }
        }

        // Maps a variable to its value. If the variable already exists in HashMap, updates the varible.
        // If there is any scope other than global scope passed as argument, variable name before binding changes accommodating the new scope. Scope name is concatenated with the name of the variable.
        case Assign(name, value) => {
          if (!value.isInstanceOf[Expression]) {
            println("Not supported data type.")
            System.exit(1)
          }
          if (scopeName == "global") {
            bindingScope += (name -> value.evaluate(scopeName))
          }
          else{
            val varNameWScope = scopeName + name
            bindingScope += (varNameWScope -> value.evaluate(scopeName))
          }
        }

        // Inserts a number of DSL expressions into a set. First the expressions are evaluated and then inserted into the set. If the set does not exist in HashMap, error is displayed and program is exited.
        case Insert(setName, value*) => {
          val evaluatedSetName = setName.evaluate(scopeName)
          if (!evaluatedSetName.isInstanceOf[Set[Any]]) {
            logger.error(s"Name $setName is not a set.")
            System.exit(1)
          }
          for {v <- value} {
            val evaluatedValue = v.evaluate(scopeName)
            bindingScope.update(evaluatedSetName, evaluatedSetName.asInstanceOf[Set[BasicType]] += evaluatedValue)
          }
        }

        // Checks if a value is present in a set.
        // Returns a boolean if the value is present.
        case Check(setName, value) => {
          val evaluatedSetName = setName.evaluate(scopeName)
          val evaluatedValue = value.evaluate(scopeName)
          if (!evaluatedSetName.isInstanceOf[Set[Any]]) {
            logger.error(s"Name $setName is not a set.")
            System.exit(1)
          }
          if (evaluatedSetName.asInstanceOf[Set[BasicType]].contains(evaluatedValue)) {
            return true
          }
          return false
        }

        // Deletes an DSL expression from a set.
        // If the set does not exist in HashMap, error is displayed and program is exited.
        case Delete(setName, value) => {
          val evaluatedSetName = setName.evaluate(scopeName)
          val evaluatedValue = value.evaluate(scopeName)
          if (!evaluatedSetName.isInstanceOf[Set[Any]]) {
            logger.error(s"Name $setName is not a set.")
            System.exit(1)
          }
          if (evaluatedSetName.asInstanceOf[Set[BasicType]].contains(evaluatedValue)) {
            return bindingScope.update(evaluatedSetName, evaluatedSetName.asInstanceOf[Set[BasicType]] -= evaluatedValue)
          }
          else{
            logger.error(s"Name $setName does not contain $value.")
            System.exit(1)
          }

        }

        // Union of the sets A and B, denoted A ∪ B, is the set of all objects that are a member of A, or B, or both.
        // Returns the union of two sets.
        // The sets can be expressions. The expressions are evaluated first and then their union is returned.
        // If any of the sets does not exist in HashMap, error is displayed and program is exited.
        case Union(setName1, setName2) => {
          val set1Eval = setName1.evaluate(scopeName)
          val set2Eval = setName2.evaluate(scopeName)

          if (!set1Eval.isInstanceOf[Set[Any]]) {
            logger.error(s"Name $setName1 is not a set.")
            System.exit(1)
          }

          if (!set2Eval.isInstanceOf[Set[Any]]) {
            logger.error(s"Name $setName2 is not a set.")
            System.exit(1)
          }
          val set1 = set1Eval.asInstanceOf[Set[BasicType]]
          val set2 = set2Eval.asInstanceOf[Set[BasicType]]
          set1.union(set2)
        }

        // Intersection of the sets A and B, denoted A ∩ B, is the set of all objects that are members of both A and B.
        // Returns the intersection of two sets.
        // The sets can be expressions. The expressions are evaluated first and then their intersection is returned.
        // If any of the sets does not exist in HashMap, error is displayed and program is exited.
        case Intersect(setName1, setName2) => {
          val set1Eval = setName1.evaluate(scopeName)
          val set2Eval = setName2.evaluate(scopeName)
          if (!set1Eval.isInstanceOf[Set[Any]]) {
            logger.error(s"Name $setName1 is not a set.")
            System.exit(1)
          }

          if (!set2Eval.isInstanceOf[Set[Any]]) {
            logger.error(s"Name $setName2 is not a set.")
            System.exit(1)
          }
          val set1 = set1Eval.asInstanceOf[Set[BasicType]]
          val set2 = set2Eval.asInstanceOf[Set[BasicType]]
          set1.intersect(set2)
        }

        // Set difference of U and A, denoted U \ A, is the set of all members of U that are not members of A.
        // Returns the set difference of two sets.
        // The sets can be expressions. The expressions are evaluated first and then their set difference is returned.
        // If any of the sets does not exist in HashMap, error is displayed and program is exited.
        case Difference(setName1, setName2) => {
          val set1Eval = setName1.evaluate(scopeName)
          val set2Eval = setName2.evaluate(scopeName)
          if (!set1Eval.isInstanceOf[Set[Any]]) {
            logger.error(s"Name $setName1 is not a set.")
            System.exit(1)
          }

          if (!set2Eval.isInstanceOf[Set[Any]]) {
            logger.error(s"Name $setName2 is not a set.")
            System.exit(1)
          }
          val set1 = set1Eval.asInstanceOf[Set[BasicType]]
          val set2 = set2Eval.asInstanceOf[Set[BasicType]]
          set1.diff(set2)
        }

        // Symmetric difference of sets A and B, denoted A ⊖ B, is the set of all objects that are a member of exactly one of A and B (elements which are in one of the sets, but not in both). For instance, for the sets {1, 2, 3} and {2, 3, 4}, the symmetric difference set is {1, 4}. It is the set difference of the union and the intersection, (A ∪ B) \ (A ∩ B) or (A \ B) ∪ (B \ A).
        // Returns the symmetric difference of two sets.
        // The sets can be expressions. The expressions are evaluated first and then their symmetric difference is returned.
        // If any of the sets does not exist in HashMap, error is displayed and program is exited.
        case SymmetricDifference(set1, set2) => {
          val diff1 = Difference(set1, set2)
          val diff2 = Difference(set2, set1)
          Union(diff1, diff2).evaluate(scopeName)
        }

        // Cartesian product or Cross product of A and B, denoted A × B, is the set whose members are all possible ordered pairs (a, b), where a is a member of A and b is a member of B.
        // Returns the cross product of two sets.
        // The sets can be expressions. The expressions are evaluated first and then their cross product is returned.
        // If any of the sets does not exist in HashMap, error is displayed and program is exited.
        case CrossProduct(setName1: Expression, setName2: Expression) => {
          val set1Eval = setName1.evaluate(scopeName)
          val set2Eval = setName2.evaluate(scopeName)
          if (!set1Eval.isInstanceOf[Set[Any]]) {
            logger.error(s"Name $setName1 is not a set.")
            System.exit(1)
          }

          if (!set2Eval.isInstanceOf[Set[Any]]) {
            logger.error(s"Name $setName2 is not a set.")
            System.exit(1)
          }
          val set1 = set1Eval.asInstanceOf[Set[BasicType]]
          val set2 = set2Eval.asInstanceOf[Set[BasicType]]
          val output = for {s1 <- set1; s2 <- set2} yield (s1, s2)
          return output.asInstanceOf[Set[BasicType]]
        }


        // Creates a Macro.
        // An operation is mapped to a variable.
        // Only Macro is being created, not evaluated.
        case Macro(macroName, operation) => {
          bindingScope += (macroName -> operation)
        }

        // Evaluates a Macro
        // Macro is executed here only. This is lazy evaluation.
        case MacroEval(macroName) => {
          val returnIfAny = bindingScope(macroName).asInstanceOf[Expression].evaluate(scopeName)
          returnIfAny
        }
      }

  @main def runAion: Unit =
    // Main function
    // Importing all expressions
    import Expression.*

    // WRITE YOUR CODE HERE
    // TEST SUITE IS PRESENT IN aionTestSuite.scala

    Assign("Set1", Val(Set(1, 2, 3))).evaluate()
    Assign("Set2", Val(Set(2, 3, 4))).evaluate()
    Assign("Set3", Val(Set(10, 20, 30))).evaluate()
    Assign("Set4", Val(Set(20, 30, 40))).evaluate()
    println(Union(Union(Var("Set1"), Var("Set2")), Union(Var("Set3"), Var("Set4"))).evaluate())