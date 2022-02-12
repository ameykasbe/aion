package scala
// Imports
import scala.collection.mutable.Map
import scala.collection.mutable.Set
import org.slf4j.{Logger, LoggerFactory}
import scala.runtime.BoxedUnit

object aion:
  // Creating a private HashMap to store and map variables to values.
  // Represents memory of the DSL.
  // Explanation for var: Used a mutable variable instead of a immutable object because if a immutable object is used, once instantiated, can not accept any more DSL variable and value pair to store. If bindingScope is an immutable object, For every DSL variable, a new object would have to created, defeating the purpose of the language.
  private var bindingScope: scala.collection.mutable.Map[BasicType, BasicType] = Map()

  // Define BasicType
  type BasicType = Any

  // Creating a logger instance to log events
  val logger: Logger = LoggerFactory.getLogger(this.getClass.getSimpleName)

  enum Expression:
    // Expression enum. Representing all the functionalies of DSL - Constant, Variable, expressions etc.
    // In the documentation and comments, "expression" denotes a constant, variable or any other expression from the Expression enum
    case Val(value: BasicType) // A constant
    case Var(name: String) // A variable
    case Assign(name:BasicType, value: Expression) // Maps a variable to an expression
    case Insert(setToInsert: Expression, value: Expression*) // Inserts a number of DSL expressions into a set
    case Check(setName: Expression, value: Expression) // Checks if a value is present in a set. Returns a boolean
    case Delete(setToInsert: Expression, value: Expression) // Deletes an DSL expression from a set
    case Union(setName1: Expression, setName2: Expression) // Returns the union of sets
    case Intersect(setName1: Expression, setName2: Expression) // Returns the intersection of a set
    case Difference(setName1: Expression, setName2: Expression) // Returns the difference of a set
    case SymmetricDifference(setName1:Expression, setName2:Expression) // Returns the symmetric difference of a set
    case CrossProduct(setName1:Expression, setName2:Expression) // Returns the cross product of a set
    case Macro(macroName: Expression, operand: Expression) // Creates a Macro
    case MacroEval(macroName: Expression) // Evaluates a Macro

    def evaluate: BasicType =
      // Evaluates an expression.
      this.match{

        // Val(value): `Val` is the constant datatype for the DSL. `value` can be of any datatype of language Scala. Evaluating the expression `Val(value)` returns value.
        case Val(value) => value

        // Var(value): `Var` is the variable datatype for the DSL.
        // `name` is string of language Scala.
        // Evaluating the expression Var(name) returns the mapped value of Var(name) from HashMap. If there is no mapping of `name` in the HashMap, it returns `null`.
        // In short, `name` in DSL represents the variable name in any language. `bindingScope` represents memory. In the DSL, returning value from HashMap represents reading from memory location.
        case Var(name) => {
          if (bindingScope.contains(name)) {
            bindingScope(name)
          }
          else
          {
             null
          }
        }

        // Maps a variabe to its value. If the variable already exists in HashMap, updates the varibale.
        case Assign(name, value) => {
              bindingScope += (name -> value.evaluate)
        }

        // Inserts a number of DSL expressions into a set. First the expressions are evaluated and then inserted into the set. If the set does not exist in HashMap, error is displayed and program is exited.
        case Insert(setName, value*) => {
          val evaluatedSetName = setName.evaluate
          if (evaluatedSetName != null || evaluatedSetName.isInstanceOf[BoxedUnit]){
            for {v <- value}{
              val evaluatedValue = v.evaluate
              bindingScope.update(evaluatedSetName, evaluatedSetName.asInstanceOf[Set[BasicType]] += evaluatedValue)
            }
          }
          else{
            logger.error(s"Name $setName not assigned.")
            System.exit(1)
          }
        }
        // Checks if a value is present in a set.
        // Returns a boolean if the value is present.
        case Check(setName, value) => {
          val evaluatedSetName = setName.evaluate
          val evaluatedValue = value.evaluate
          if (evaluatedSetName == null || evaluatedSetName.isInstanceOf[BoxedUnit]){
            logger.error(s"Name $setName not assigned.")
            System.exit(1)
          }
          if (!evaluatedSetName.isInstanceOf[Set[Any]]){
            logger.error(s"Name $setName is not a set.")
            System.exit(1)
          }
          if (evaluatedSetName.asInstanceOf[Set[BasicType]].contains(evaluatedValue)){
            return true
          }
          return false
        }

        // Deletes an DSL expression from a set.
        // If the set does not exist in HashMap, error is displayed and program is exited.
        case Delete(setName, value) => {
          val evaluatedSetName = setName.evaluate
          val evaluatedValue = value.evaluate
          bindingScope.update(evaluatedSetName, evaluatedSetName.asInstanceOf[Set[BasicType]] -= evaluatedValue)
        }

        // Union of the sets A and B, denoted A ∪ B, is the set of all objects that are a member of A, or B, or both.
        // Returns the union of two sets.
        // The sets can be expressions. The expressions are evaluated first and then their union is returned.
        // If any of the sets does not exist in HashMap, error is displayed and program is exited.
        case Union(setName1, setName2) => {
          val set1Eval = setName1.evaluate
          val set2Eval = setName2.evaluate
          if (set1Eval == null || set1Eval.isInstanceOf[BoxedUnit]){
            logger.error(s"Name $setName1 not assigned.")
            System.exit(1)
          }
          if (set2Eval == null || set2Eval.isInstanceOf[BoxedUnit]){
            logger.error(s"Name $setName2 not assigned.")
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
          val set1Eval = setName1.evaluate
          val set2Eval = setName2.evaluate
          if (set1Eval == null || set1Eval.isInstanceOf[BoxedUnit]){
            logger.error(s"Name $setName1 not assigned.")
            System.exit(1)
          }
          if (set2Eval == null || set2Eval.isInstanceOf[BoxedUnit]){
            logger.error(s"Name $setName2 not assigned.")
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
          val set1Eval = setName1.evaluate
          val set2Eval = setName2.evaluate
          if (set1Eval == null || set1Eval.isInstanceOf[BoxedUnit]){
            logger.error(s"Name $setName1 not assigned.")
            System.exit(1)
          }
          if (set2Eval == null || set2Eval.isInstanceOf[BoxedUnit]){
            logger.error(s"Name $setName2 not assigned.")
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
          Union(diff1, diff2).evaluate
          }

        // Cartesian product or Cross product of A and B, denoted A × B, is the set whose members are all possible ordered pairs (a, b), where a is a member of A and b is a member of B.
        // Returns the cross product of two sets.
        // The sets can be expressions. The expressions are evaluated first and then their cross product is returned.
        // If any of the sets does not exist in HashMap, error is displayed and program is exited.
        case CrossProduct(setName1:Expression, setName2:Expression) => {
          val set1Eval = setName1.evaluate
          val set2Eval = setName2.evaluate
          if (set1Eval == null || set1Eval.isInstanceOf[BoxedUnit]){
            logger.error(s"Name $setName1 not assigned.")
            System.exit(1)
          }
          if (set2Eval == null || set2Eval.isInstanceOf[BoxedUnit]){
            logger.error(s"Name $setName2 not assigned.")
            System.exit(1)
          }
          val set1 = set1Eval.asInstanceOf[Set[BasicType]]
          val set2 = set2Eval.asInstanceOf[Set[BasicType]]
          val output = for {s1 <- set1; s2 <- set2} yield (s1,s2)
          return output.asInstanceOf[Set[BasicType]]
        }


        // Creates a Macro.
        // An operation is mapped to a variable.
        // Only Macro is being created, not evaluated.
        case Macro(macroName, operation) => {
          val MacroNameEval = macroName.evaluate
          bindingScope += (MacroNameEval -> operation)
        }

        // Evaluates a Macro
        // Macro is executed here only. This is lazy evaluation.
        case MacroEval(macroName) => {
          val macroNameEval = macroName.evaluate
          val returnIfAny = bindingScope(macroNameEval).asInstanceOf[Expression].evaluate
          returnIfAny
        }

      }

  @main def runAion: Unit =
    // Main function
    // Importing all expressions
    import Expression.*
    // Write your code here.



//    Assign("Amey", Val(Set(1))).evaluate
//    Insert(Var("Amey"), Val(2)).evaluate
//
//    Assign("Bobby", Val(Set(2))).evaluate
//    Insert(Var("Bobby"), Val(3)).evaluate
//
//
//    println(Var("Amey").evaluate)
//    println(Var("Bobby").evaluate)
//    println(Union(Var("Amey"), Var("Bobby")).evaluate)
//    println(Intersect(Var("Amey"), Var("Bobby")).evaluate)
//
//    Delete(Var("Bobby"), Val(2)).evaluate
//    println(Var("Bobby").evaluate)
//
//    Macro(Val("InsertIntoBobby1000"), Insert(Var("Bobby"), Val(1000))).evaluate
//    MacroEval(Val("InsertIntoBobby1000")).evaluate
//    println(Var("Bobby").evaluate)
//
//    Assign("Set1", Val(Set())).evaluate
//    Assign("Set2", Val(Set())).evaluate
//    Insert(Var("Set1"), Val(1)).evaluate
//    Insert(Var("Set1"), Val(2)).evaluate
//    Insert(Var("Set1"), Val(3)).evaluate
//    Insert(Var("Set2"), Val(2)).evaluate
//    Insert(Var("Set2"), Val(3)).evaluate
//    Insert(Var("Set2"), Val(4)).evaluate
//    Insert(Var("Set2"), Val(2), Val(6), Val(7)).evaluate
//    println(Var("Set1").evaluate)
//    println(Var("Set2").evaluate)
//    println(Union(Difference(Var("Set1"), (Var("Set2"))), Difference(Var("Set2"), (Var("Set1"))) ).evaluate)

//    Assign("Set1", Val(Set())).evaluate
//    Insert(Var("Set1"), Val(1)).evaluate
//    println(Check(Var("Set1"), Val(1)).evaluate)
//    println(Check(Var("Set1"), Val(2)).evaluate)
//    println(Check(Var("Set2"), Val(3)).evaluate)

//    Assign("Intger1", Val(1)).evaluate
//    println(Check(Var("Intger1"), Val(3)).evaluate)


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

