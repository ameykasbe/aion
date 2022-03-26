import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable.{Map, Set}
import scala.runtime.BoxedUnit

object aion:
  // Creating a private HashMap to store and map variables to values.

  // Represents memory of the DSL.
  private val bindingScope: scala.collection.mutable.Map[BasicType, BasicType] = scala.collection.mutable.Map()

  // Binding scope to store Class members
  private val bindingScopeClass: scala.collection.mutable.Map[BasicType, BasicType] = scala.collection.mutable.Map()

  // Binding scope for instances of classes
  private val bindingScopeClassInstances: scala.collection.mutable.Map[BasicType, BasicType] = scala.collection.mutable.Map()

  // Access map to check access specifiers
  private val accessMap: scala.collection.mutable.Map[BasicType, BasicType] = scala.collection.mutable.Map()

  // Define BasicType
  type BasicType = Any

  // Creating a logger instance to log events
  val logger: Logger = LoggerFactory.getLogger(this.getClass.getSimpleName)

  enum Expression:
    // Expression enum. Representing all the functionalities of DSL - Constant, Variable, expressions etc.
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

    // Homework 2
    case ClassDef(name: String, members: Expression*) // Define a class
    case Field(name: String) // Returns the name of the field. Provides wrapper for Fields
    case Constructor(instructions: Expression*) // Returns the constructor instructions. Provides wrapper for instructions for constructor.
    case Method(name: String, params: List[BasicType], instructions: Expression*)  // Create a list of instructions (expressions)
    case InvokeMethod(name: String, methodName: String, params: BasicType*) // Invoke a method of an object of a class
    case NewObject(objectName: String, className: String) // Create object
    case Object(name: String) // Returns name of the object
    case GetField(objectName: String, fieldName:String) // Get field value of any field of any instance
    case Public(instruction: Expression) // Update Access Map with Public members
    case Private(instruction: Expression) // Update Access Map with Private members
    case Protected(instruction: Expression) // Update Access Map with Protected members

    // Homework 3
    case AbstractClassDef(name: String, members: Expression*) // Define an abstract class
    case AbstractMethod(name: String, params: List[BasicType]) // Define an abstract Method

    def evaluate(scopeName: String = "global", bindingHM: scala.collection.mutable.Map[BasicType, BasicType] = bindingScope, classNameEval: String = "default"): BasicType =
    // Evaluates an expression. Accepts an argument scopeName, bindingHM = bindingHM representing scope with default value as global.
      this match{

        // Val(value): `Val` is the constant datatype for the DSL. `value` can be of any datatype of language Scala. Evaluating the expression `Val(value)` returns value.
        // Var(value): `Var` is the variable datatype for the DSL.
        // `name` is string of language Scala.
        // Evaluating the expression Var(name) returns the mapped value of Var(name) from HashMap.
        // If there is no binding of `name` in the HashMap, it displays an error and exits the program.
        // In short, `name` in DSL represents the variable name in any language. `bindingScope` represents memory.
        // In the DSL, returning value from HashMap represents reading from memory location.

        // If scope is not global, before searching for bindings, the scope name is concatenated with the name and searched in the binding.
        // If not found in a particular scope, the binding is searched in global. If not found there, error is displayed and program is executed.
        case Val(value) => value

        case Var(name) =>
          if (scopeName == "global") {
            if (bindingHM.contains(name)) {
              bindingHM(name)
            }
            else {
              logger.error(s"Name $name not assigned.")
              System.exit(1)
            }
          }
          else{
            val varNameWScope = scopeName  + name
            if (bindingHM.contains(varNameWScope)) {
              bindingHM(varNameWScope)
            }
            else {
              if (bindingHM.contains(name)) {
                bindingHM(name)
              }
              else{
                logger.error(s"Name $name not assigned in scope $scopeName, bindingHM = bindingHM or in global.")
                System.exit(1)
              }
            }
          }


        // Maps a variable to its value. If the variable already exists in HashMap, updates the varible.
        // If there is any scope other than global scope passed as argument, variable name before binding changes accommodating the new scope. Scope name is concatenated with the name of the variable.
        case Assign(name, value) =>
          if (!value.isInstanceOf[Expression]) {
            println("Not supported data type.")
            System.exit(1)
          }
          if (scopeName == "global") {
            bindingHM += (name -> value.evaluate(scopeName, bindingHM = bindingHM))
          }
          else{
            val varNameWScope = scopeName + name
            bindingHM += (varNameWScope -> value.evaluate(scopeName, bindingHM = bindingHM))
          }


        // Inserts a number of DSL expressions into a set. First the expressions are evaluated and then inserted into the set. If the set does not exist in HashMap, error is displayed and program is exited.
        case Insert(setName, value*) =>
          val evaluatedSetName = setName.evaluate(scopeName, bindingHM = bindingHM)
          if (!evaluatedSetName.isInstanceOf[scala.collection.mutable.Set[Any]]) {
            logger.error(s"Name $setName is not a set.")
            System.exit(1)
          }
          for {v <- value} {
            val evaluatedValue = v.evaluate(scopeName, bindingHM = bindingHM)
            bindingHM.update(evaluatedSetName, evaluatedSetName.asInstanceOf[scala.collection.mutable.Set[BasicType]] += evaluatedValue)
          }


        // Checks if a value is present in a set.
        // Returns a boolean if the value is present.
        case Check(setName, value) =>
          val evaluatedSetName = setName.evaluate(scopeName, bindingHM = bindingHM)
          val evaluatedValue = value.evaluate(scopeName, bindingHM = bindingHM)
          if (!evaluatedSetName.isInstanceOf[scala.collection.mutable.Set[Any]]) {
            logger.error(s"Name $setName is not a set.")
            System.exit(1)
          }
          if (evaluatedSetName.asInstanceOf[scala.collection.mutable.Set[BasicType]].contains(evaluatedValue)) {
            return true
          }
          false


        // Deletes an DSL expression from a set.
        // If the set does not exist in HashMap, error is displayed and program is exited.
        case Delete(setName, value) =>
          val evaluatedSetName = setName.evaluate(scopeName, bindingHM = bindingHM)
          val evaluatedValue = value.evaluate(scopeName, bindingHM = bindingHM)
          if (!evaluatedSetName.isInstanceOf[scala.collection.mutable.Set[Any]]) {
            logger.error(s"Name $setName is not a set.")
            System.exit(1)
          }
          if (evaluatedSetName.asInstanceOf[scala.collection.mutable.Set[BasicType]].contains(evaluatedValue)) {
            bindingHM.update(evaluatedSetName, evaluatedSetName.asInstanceOf[scala.collection.mutable.Set[BasicType]] -= evaluatedValue)
          }
          else{
            logger.error(s"Name $setName does not contain $value.")
            System.exit(1)
          }



        // Union of the sets A and B, denoted A ∪ B, is the set of all objects that are a member of A, or B, or both.
        // Returns the union of two sets.
        // The sets can be expressions. The expressions are evaluated first and then their union is returned.
        // If any of the sets does not exist in HashMap, error is displayed and program is exited.
        case Union(setName1, setName2) =>
          val set1Eval = setName1.evaluate(scopeName, bindingHM = bindingHM)
          val set2Eval = setName2.evaluate(scopeName, bindingHM = bindingHM)

          if (!set1Eval.isInstanceOf[scala.collection.mutable.Set[Any]]) {
            logger.error(s"Name $setName1 is not a set.")
            System.exit(1)
          }

          if (!set2Eval.isInstanceOf[scala.collection.mutable.Set[Any]]) {
            logger.error(s"Name $setName2 is not a set.")
            System.exit(1)
          }
          val set1 = set1Eval.asInstanceOf[scala.collection.mutable.Set[BasicType]]
          val set2 = set2Eval.asInstanceOf[scala.collection.mutable.Set[BasicType]]
          set1.union(set2)


        // Intersection of the sets A and B, denoted A ∩ B, is the set of all objects that are members of both A and B.
        // Returns the intersection of two sets.
        // The sets can be expressions. The expressions are evaluated first and then their intersection is returned.
        // If any of the sets does not exist in HashMap, error is displayed and program is exited.
        case Intersect(setName1, setName2) =>
          val set1Eval = setName1.evaluate(scopeName, bindingHM = bindingHM)
          val set2Eval = setName2.evaluate(scopeName, bindingHM = bindingHM)
          if (!set1Eval.isInstanceOf[scala.collection.mutable.Set[Any]]) {
            logger.error(s"Name $setName1 is not a set.")
            System.exit(1)
          }

          if (!set2Eval.isInstanceOf[scala.collection.mutable.Set[Any]]) {
            logger.error(s"Name $setName2 is not a set.")
            System.exit(1)
          }
          val set1 = set1Eval.asInstanceOf[scala.collection.mutable.Set[BasicType]]
          val set2 = set2Eval.asInstanceOf[scala.collection.mutable.Set[BasicType]]
          set1.intersect(set2)


        // Set difference of U and A, denoted U \ A, is the set of all members of U that are not members of A.
        // Returns the set difference of two sets.
        // The sets can be expressions. The expressions are evaluated first and then their set difference is returned.
        // If any of the sets does not exist in HashMap, error is displayed and program is exited.
        case Difference(setName1, setName2) =>
          val set1Eval = setName1.evaluate(scopeName, bindingHM = bindingHM)
          val set2Eval = setName2.evaluate(scopeName, bindingHM = bindingHM)
          if (!set1Eval.isInstanceOf[scala.collection.mutable.Set[Any]]) {
            logger.error(s"Name $setName1 is not a set.")
            System.exit(1)
          }

          if (!set2Eval.isInstanceOf[scala.collection.mutable.Set[Any]]) {
            logger.error(s"Name $setName2 is not a set.")
            System.exit(1)
          }
          val set1 = set1Eval.asInstanceOf[scala.collection.mutable.Set[BasicType]]
          val set2 = set2Eval.asInstanceOf[scala.collection.mutable.Set[BasicType]]
          set1.diff(set2)


        // Symmetric difference of sets A and B, denoted A ⊖ B, is the set of all objects that are a member of exactly one of A and B (elements which are in one of the sets, but not in both). For instance, for the sets {1, 2, 3} and {2, 3, 4}, the symmetric difference set is {1, 4}. It is the set difference of the union and the intersection, (A ∪ B) \ (A ∩ B) or (A \ B) ∪ (B \ A).
        // Returns the symmetric difference of two sets.
        // The sets can be expressions. The expressions are evaluated first and then their symmetric difference is returned.
        // If any of the sets does not exist in HashMap, error is displayed and program is exited.
        case SymmetricDifference(set1, set2) =>
          val diff1 = Difference(set1, set2)
          val diff2 = Difference(set2, set1)
          Union(diff1, diff2).evaluate(scopeName, bindingHM = bindingHM)


        // Cartesian product or Cross product of A and B, denoted A × B, is the set whose members are all possible ordered pairs (a, b), where a is a member of A and b is a member of B.
        // Returns the cross product of two sets.
        // The sets can be expressions. The expressions are evaluated first and then their cross product is returned.
        // If any of the sets does not exist in HashMap, error is displayed and program is exited.
        case CrossProduct(setName1: Expression, setName2: Expression) =>
          val set1Eval = setName1.evaluate(scopeName, bindingHM = bindingHM)
          val set2Eval = setName2.evaluate(scopeName, bindingHM = bindingHM)
          if (!set1Eval.isInstanceOf[scala.collection.mutable.Set[Any]]) {
            logger.error(s"Name $setName1 is not a set.")
            System.exit(1)
          }

          if (!set2Eval.isInstanceOf[scala.collection.mutable.Set[Any]]) {
            logger.error(s"Name $setName2 is not a set.")
            System.exit(1)
          }
          val set1 = set1Eval.asInstanceOf[scala.collection.mutable.Set[BasicType]]
          val set2 = set2Eval.asInstanceOf[scala.collection.mutable.Set[BasicType]]
          val output = for {s1 <- set1; s2 <- set2} yield (s1, s2)
          output.asInstanceOf[scala.collection.mutable.Set[BasicType]]



        // Creates a Macro.
        // An operation is mapped to a variable.
        // Only Macro is being created, not evaluated.
        case Macro(macroName, operation) =>
          bindingHM += (macroName -> operation)


        // Evaluates a Macro
        // Macro is executed here only. This is lazy evaluation.
        case MacroEval(macroName) =>
          val returnIfAny = bindingHM(macroName).asInstanceOf[Expression].evaluate(scopeName, bindingHM = bindingHM)
          returnIfAny

        // Define a class
        case ClassDef(name, members*) =>
          if(bindingScopeClass.contains(name)){
            // If class already exists, pop error and exit the program.
            logger.error(s"Class name $name already assigned to a class.")
            System.exit(1)
          }
          // Binding scope of the class - contains all the fields, constructor and methods.
          val thisClassBindingScope = scala.collection.mutable.Map[BasicType,BasicType]("fields" -> null, "constructor" -> null, "methods" -> null )

          // Binding scope for fields of this class
          val thisClassBindingScopeFields = scala.collection.mutable.Map[BasicType,BasicType]()

          // Binding scope for the methods of the class
          val thisClassBindingScopeMethods = scala.collection.mutable.Map[BasicType,BasicType]()

          // Update accessMap - class members with access specifiers
          // Private members
          val privateMembers: scala.collection.mutable.Map[BasicType, BasicType] = scala.collection.mutable.Map()
          val privateField: scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          val privateMethod: scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          privateMembers += ("fields" -> privateField)
          privateMembers += ("methods" -> privateMethod)

          // Public members
          val publicMembers: scala.collection.mutable.Map[BasicType, BasicType] = scala.collection.mutable.Map()
          val publicField:scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          val publicMethod:scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          publicMembers += ("fields" -> publicField)
          publicMembers += ("methods" -> publicMethod)

          // Protected members
          val protectedMembers: scala.collection.mutable.Map[BasicType, BasicType] = scala.collection.mutable.Map()
          val protectedField:scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          val protectedMethod:scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          protectedMembers += ("fields" -> protectedField)
          protectedMembers += ("methods" -> protectedMethod)

          // Update access map
          val classAccess: scala.collection.mutable.Map[BasicType, BasicType] = scala.collection.mutable.Map()
          classAccess += ("public" -> publicMembers)
          classAccess += ("private" -> privateMembers)
          classAccess += ("protected" -> protectedMembers)

          // Maintain a access map to check if any member is public, private or protected.
          accessMap += (name -> classAccess)


          // Working on each member according to their type - Fields, Methods or Constructor
          members.foreach(member => {

            // Member should be instance of the Expression type
            val memberExp = member.asInstanceOf[Expression]

            // If member is a Constructor
            if (memberExp.isInstanceOf[Expression.Constructor]) {
              thisClassBindingScope("constructor") = memberExp
            }
              else{
              val memberExp2 = memberExp.evaluate(classNameEval = name).asInstanceOf[Expression] // Populate access specifier set.
              // If member is a Field
              if (memberExp2.isInstanceOf[Expression.Field]) {
                val fieldName = memberExp2.evaluate(bindingHM = bindingHM)

                // If field name already exists, pop error and exit the program.
                if(thisClassBindingScopeFields.contains(fieldName)){
                  logger.error(s"Field name $fieldName already exist in the class to a class.")
                  System.exit(1)
                }
                // If field does not exist, create fieldName binding to null.
                thisClassBindingScopeFields += (fieldName -> null)
              }

              // If member is a Method
              else if (memberExp2.isInstanceOf[Expression.Method]) {
                // Evaluate method to get list of expressions, bind the list of expressions to the method
                val methodInstructionsMapElement = memberExp2.evaluate(bindingHM = bindingHM).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]
                thisClassBindingScopeMethods .++= (methodInstructionsMapElement)

                //// Logical overview
                // thisClassBindingScopeMethods => { method1 => List[Expression}, method2 => List[Expression], method3 => List[Expression]...  }
              }
              else{
                logger.error(s"A member of the class is not a constructor, field or method.")
                System.exit(1)
              }
            }


          })

          // Binding Fields binding scope to fields
          thisClassBindingScope("fields") = thisClassBindingScopeFields

          // Binding Methods binding scope to fields
          thisClassBindingScope("methods") = thisClassBindingScopeMethods

          // Inheritance
          thisClassBindingScope("inheritance") = false

          // IsAbstract
          thisClassBindingScope("isAbstract") = false

          // Binding class
          bindingScopeClass += (name -> thisClassBindingScope)
          true

        //// Logical overview
        // classBindingScope => {
        //  class1 => {
        //    constructor => expressions,
        //    fields => {field1 => value1, field2 => value2},
        //    methods => {method1 => List[Expression], method2 => List[Expression], method3 => List[Expression]...}
        //  },
        //  class2 => {...}
        //  class3 => {...}
        //}


        // Field case. Return the name of the field
        // Values of the fields are stored in bindingScopeClassInstances for each instance
        case Field(name) => name

        // Method case
        // Create a list of instructions (expressions)
        case Method(name, params, instructions*) =>
          val tempMethodMap: scala.collection.mutable.Map[BasicType, BasicType] = Map("params" -> null, "exps" -> null)
          val methodMap: scala.collection.mutable.Map[BasicType, BasicType] = Map()


          // Create list of instructions
          val instructionList = scala.collection.mutable.Set[BasicType]()

          // Add instructions to instruction list of the method
          instructions.foreach(instruction => {instructionList += instruction})
          val tempVarMap = scala.collection.mutable.Map[Any,Any]()
          params.foreach(i => {
            tempVarMap += (i -> null)
          })

          tempMethodMap("exps") = instructionList
          tempMethodMap += ("params" -> tempVarMap)
          methodMap += (name -> tempMethodMap)
          methodMap
//          // Create a Map with single element - name (method's name) => instructionList
//          val instructionListMapSingleElement = scala.collection.mutable.Map[BasicType,BasicType]()
//          instructionListMapSingleElement += (name -> instructionList)
//          instructionListMapSingleElement

        // Invoke a method of an object of a class
        case InvokeMethod(name, methodName, params*) =>
          // Check if object exists in bindings
          if(!bindingScopeClassInstances.contains(name)){
            logger.error(s"Object name $name does not exist.")
            System.exit(1)
          }
          // Create a separate binding for the method for each parameter
          params.foreach(item => {
            val item1 = item.asInstanceOf[Expression]
            val objMap = bindingScopeClassInstances(name).
              asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]]("methods").asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]](methodName).asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]]("params").asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]]
            item1.evaluate(bindingHM = objMap)
          })

          // Add fields into the separate binding as well
          bindingScopeClassInstances(name).asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]]("fields").asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]].
            foreach{
              case (key, value) =>
                bindingScopeClassInstances(name).
                  asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]]("methods").asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]](methodName).asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]]("params").asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]] += (key -> value)

            }
          val tempMap = bindingScopeClassInstances(name).asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]]("methods").asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]](methodName).asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]]
          val operations1 = tempMap("exps").asInstanceOf[scala.collection.mutable.Set[Any]]
          val variableMap = tempMap("params").asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]]

          // Execute all the instructions of the method
          // Return the output of the last instruction
          operations1.foreach( operation => {
            val lastResult = operation.asInstanceOf[Expression].evaluate(bindingHM = variableMap)
            if (operation == operations1.last) {
              return lastResult // returns last operation's result
            }
          }
          )

        // Object case
        case Object(name) => name

        // Constructor
        // Simply returns the instructions
        case Constructor(instructions*) => instructions

        // Create object
        case NewObject(objectName, className) =>
          // If class is an abstract class, pop error and exit.
            if(bindingScopeClass(className).asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]]("isAbstract") == true){
              logger.error("Can not create an instance of abstract class.")
              System.exit(1)
            }

          // If object name already exists, pop error and exit the program.
          if(bindingScopeClassInstances.contains(objectName)){
            logger.error(s"Object name $objectName already exist of class $className")
            System.exit(1)
          }

          // Check if class name exists
          if(!bindingScopeClass.contains(className)){
            logger.error(s"Class name $className does not exist.")
            System.exit(1)
          }

          // Create a binding map for the object to store class name, fields and methods
          val thisObjectMap = scala.collection.mutable.Map[BasicType,BasicType]()
          thisObjectMap += ("className" -> className)

          // Extracting getting the class object from class binding Map
          val classObject = bindingScopeClass(className)

          // Extracting fields of the class
          val fields = classObject.asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]]("fields")

          // Extracting methods of the class
          val methods = classObject.asInstanceOf[scala.collection.mutable.Map[BasicType,BasicType]]("methods")

          // Create a copy of the fields and methods in the object
          thisObjectMap += ("fields" -> fields)
          thisObjectMap += ("methods" -> methods)

          // Binding object name to newly created Map
          bindingScopeClassInstances += (objectName -> thisObjectMap)

          // Execute constructor
          // Get constructor instructions
          val constructorInstructions = bindingScopeClass(className).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("constructor").asInstanceOf[Expression].evaluate(bindingHM = bindingHM)

          // Get binding scope - Fields map of the object from the object-class Map
          val bindingScopeFields = bindingScopeClassInstances(objectName).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("fields")

          // Execute instructions of the constructor
          constructorInstructions.asInstanceOf[scala.collection.immutable.ArraySeq[Expression]].foreach(instruction => {
            instruction.evaluate(bindingHM = bindingScopeFields.asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]])
          })

        // Get field value of any field of any instance
        case GetField(objectName, fieldName) =>
          if(!bindingScopeClassInstances.contains(objectName)){
            // If object name does not exist, pop error and exit the program.
            logger.error(s"Object name $objectName does not exist.")
            System.exit(1)
          }

          val temp = bindingScopeClassInstances(objectName).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("fields").asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]
          if (!temp.contains(fieldName)){
            // If field name does not exist, pop error and exit the program.
            logger.error(s"Field name $fieldName does not exist.")
            System.exit(1)
          }
          // Get class name
          val cname = bindingScopeClassInstances(objectName).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("className")

          // Get class private fields
          val privateFields = accessMap(cname).asInstanceOf[scala.collection.mutable.Map[Any, Any]]("private").asInstanceOf[scala.collection.mutable.Map[Any, Any]]("fields").asInstanceOf[scala.collection.mutable.HashSet[Any]]

          // Get class public fields
          val publicFields = accessMap(cname).asInstanceOf[scala.collection.mutable.Map[Any, Any]]("public").asInstanceOf[scala.collection.mutable.Map[Any, Any]]("fields").asInstanceOf[scala.collection.mutable.HashSet[Any]]

          // Get class protected fields
          val protectedFields = accessMap(cname).asInstanceOf[scala.collection.mutable.Map[Any, Any]]("protected").asInstanceOf[scala.collection.mutable.Map[Any, Any]]("fields").asInstanceOf[scala.collection.mutable.HashSet[Any]]

          // If field name is private or protected, it can not be accessed from Main
          if (privateFields.contains(fieldName)){
            logger.error(s"Field name $fieldName can not be accessed.")
            System.exit(1)
          }
          else if (protectedFields.contains(fieldName)){
            logger.error(s"Field name $fieldName can not be accessed.")
            System.exit(1)
          }
          // Else return the field name
          temp(fieldName)

        // Update Access Map with Public members
        case Public(instruction: Expression) =>
          if(instruction.isInstanceOf[Field]) {
            val instructionName = instruction.evaluate(bindingHM = bindingHM)
            accessMap(classNameEval).asInstanceOf[scala.collection.mutable.Map[Any, Any]]("public").asInstanceOf[scala.collection.mutable.Map[Any, Any]]("fields").asInstanceOf[scala.collection.mutable.Set[Any]] += instructionName
          }
          else if (instruction.isInstanceOf[Method]){
            val instructionName = instruction.methodEval()
            accessMap(classNameEval).asInstanceOf[scala.collection.mutable.Map[Any, Any]]("public").asInstanceOf[scala.collection.mutable.Map[Any, Any]]("methods").asInstanceOf[scala.collection.mutable.Set[Any]] += instructionName
          }
          else{
            val instructionName = instruction.methodEval()
            accessMap(classNameEval).asInstanceOf[scala.collection.mutable.Map[Any, Any]]("public").asInstanceOf[scala.collection.mutable.Map[Any, Any]]("abstractMethods").asInstanceOf[scala.collection.mutable.Set[Any]] += instructionName
          }
          // Return the actual instruction
          instruction

        // Update Access Map with Private members
        case Private(instruction: Expression) =>
          if(instruction.isInstanceOf[Field]) {
            val instructionName = instruction.evaluate(bindingHM = bindingHM)
            accessMap(classNameEval).asInstanceOf[scala.collection.mutable.Map[Any, Any]]("private").asInstanceOf[scala.collection.mutable.Map[Any, Any]]("fields").asInstanceOf[scala.collection.mutable.Set[Any]] += instructionName
          }
          else if (instruction.isInstanceOf[Method]){
            val instructionName = instruction.methodEval()
            accessMap(classNameEval).asInstanceOf[scala.collection.mutable.Map[Any, Any]]("private").asInstanceOf[scala.collection.mutable.Map[Any, Any]]("methods").asInstanceOf[scala.collection.mutable.Set[Any]] += instructionName
          }
          else{
            val instructionName = instruction.methodEval()
            accessMap(classNameEval).asInstanceOf[scala.collection.mutable.Map[Any, Any]]("private").asInstanceOf[scala.collection.mutable.Map[Any, Any]]("abstractMethods").asInstanceOf[scala.collection.mutable.Set[Any]] += instructionName
          }
          // Return the actual instruction
          instruction

        //   Update Access Map with Protected members
        case Protected(instruction: Expression) =>
          if(instruction.isInstanceOf[Field]) {
            val instructionName = instruction.evaluate(bindingHM = bindingHM)
            accessMap(classNameEval).asInstanceOf[scala.collection.mutable.Map[Any, Any]]("protected").asInstanceOf[scala.collection.mutable.Map[Any, Any]]("fields").asInstanceOf[scala.collection.mutable.Set[Any]] += instructionName
          }
          else if (instruction.isInstanceOf[Method]){
            val instructionName = instruction.methodEval()
            accessMap(classNameEval).asInstanceOf[scala.collection.mutable.Map[Any, Any]]("protected").asInstanceOf[scala.collection.mutable.Map[Any, Any]]("methods").asInstanceOf[scala.collection.mutable.Set[Any]] += instructionName
          }
          else{
            val instructionName = instruction.methodEval()
            accessMap(classNameEval).asInstanceOf[scala.collection.mutable.Map[Any, Any]]("protected").asInstanceOf[scala.collection.mutable.Map[Any, Any]]("abstractMethods").asInstanceOf[scala.collection.mutable.Set[Any]] += instructionName
          }

          // Return the actual instruction
          instruction

        // AbstractMethod case
        case AbstractMethod(name, params) =>
          val tempMethodMap: scala.collection.mutable.Map[BasicType, BasicType] = Map("params" -> null, "exps" -> null)
          val methodMap: scala.collection.mutable.Map[BasicType, BasicType] = Map()


          // Create list of instructions
          // val instructionList = scala.collection.mutable.Set[BasicType]()

          // Add instructions to instruction list of the method
          // instructions.foreach(instruction => {instructionList += instruction})
          val tempVarMap = scala.collection.mutable.Map[Any,Any]()
          params.foreach(i => {
            tempVarMap += (i -> null)
          })

          // tempMethodMap("exps") = instructionList
          tempMethodMap += ("params" -> tempVarMap)
          methodMap += (name -> tempMethodMap)
          methodMap

        // Define a class
        case AbstractClassDef(name, members*) =>
          if(bindingScopeClass.contains(name)){
            // If a class with same name already exists, pop error and exit the program.
            logger.error(s"Class name $name already assigned to a class.")
            System.exit(1)
          }



          // Binding scope of the class - contains all the fields, constructor and methods.
          val thisClassBindingScope = scala.collection.mutable.Map[BasicType,BasicType]("fields" -> null, "constructor" -> null, "methods" -> null, "abstractMethods" -> null)


          // Binding scope for fields of this class
          val thisClassBindingScopeFields = scala.collection.mutable.Map[BasicType,BasicType]()

          // Binding scope for the methods of the class
          val thisClassBindingScopeMethods = scala.collection.mutable.Map[BasicType,BasicType]()

          // Binding scope for the abstract methods of the class
          val thisClassBindingScopeAbstractMethods = scala.collection.mutable.Map[BasicType,BasicType]()

          // Update accessMap - class members with access specifiers
          // Private members
          val privateMembers: scala.collection.mutable.Map[BasicType, BasicType] = scala.collection.mutable.Map()
          val privateField: scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          val privateMethod: scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          val privateAbstractMethod: scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          privateMembers += ("fields" -> privateField)
          privateMembers += ("methods" -> privateMethod)
          privateMembers += ("abstractMethods" -> privateAbstractMethod)

          // Public members
          val publicMembers: scala.collection.mutable.Map[BasicType, BasicType] = scala.collection.mutable.Map()
          val publicField:scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          val publicMethod:scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          val publicAbstractMethod: scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          publicMembers += ("fields" -> publicField)
          publicMembers += ("methods" -> publicMethod)
          publicMembers += ("abstractMethods" -> publicAbstractMethod)

          // Protected members
          val protectedMembers: scala.collection.mutable.Map[BasicType, BasicType] = scala.collection.mutable.Map()
          val protectedField:scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          val protectedMethod:scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          val protectedAbstractMethod: scala.collection.mutable.Set[BasicType] = scala.collection.mutable.Set()
          protectedMembers += ("fields" -> protectedField)
          protectedMembers += ("methods" -> protectedMethod)
          protectedMembers += ("abstractMethods" -> protectedAbstractMethod)

          // Update access map
          val classAccess: scala.collection.mutable.Map[BasicType, BasicType] = scala.collection.mutable.Map()
          classAccess += ("public" -> publicMembers)
          classAccess += ("private" -> privateMembers)
          classAccess += ("protected" -> protectedMembers)

          // Maintain a access map to check if any member is public, private or protected.
          accessMap += (name -> classAccess)


          // Working on each member according to their type - Fields, Methods or Constructor
          members.foreach(member => {

            // Member should be instance of the Expression type
            val memberExp = member.asInstanceOf[Expression]

            // If member is a Constructor
            if (memberExp.isInstanceOf[Expression.Constructor]) {
              thisClassBindingScope("constructor") = memberExp
            }
            else{
              val memberExp2 = memberExp.evaluate(classNameEval = name).asInstanceOf[Expression] // Populate access specifier set.
              // If member is a Field
              if (memberExp2.isInstanceOf[Expression.Field]) {
                val fieldName = memberExp2.evaluate(bindingHM = bindingHM)

                // If field name already exists, pop error and exit the program.
                if(thisClassBindingScopeFields.contains(fieldName)){
                  logger.error(s"Field name $fieldName already exist in the class to a class.")
                  System.exit(1)
                }
                // If field does not exist, create fieldName binding to null.
                thisClassBindingScopeFields += (fieldName -> null)
              }

              // If member is a Method
              else if (memberExp2.isInstanceOf[Expression.Method]) {
                // Evaluate method to get list of expressions, bind the list of expressions to the method
                val methodInstructionsMapElement = memberExp2.evaluate(bindingHM = bindingHM).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]
                thisClassBindingScopeMethods .++= (methodInstructionsMapElement)

              }

              else if (memberExp2.isInstanceOf[Expression.AbstractMethod]) {
                // Evaluate method to get list of expressions, bind the list of expressions to the method
                val methodInstructionsMapElement = memberExp2.evaluate(bindingHM = bindingHM).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]
                thisClassBindingScopeAbstractMethods .++= (methodInstructionsMapElement)

                //// Logical overview
                // thisClassBindingScopeMethods => { method1 => List[Expression}, method2 => List[Expression], abstractMethod1 => List[]}
              }
              else{
                logger.error(s"A member of the class is not a constructor, field, method or abstract method.")
                System.exit(1)
              }
            }


          })

          // Binding Fields binding scope to fields
          thisClassBindingScope("fields") = thisClassBindingScopeFields

          // Binding Methods binding scope to fields
          thisClassBindingScope("methods") = thisClassBindingScopeMethods

          // Binding Abstract Methods binding scope to fields
          thisClassBindingScope("abstractMethods") = thisClassBindingScopeAbstractMethods
          if(thisClassBindingScopeAbstractMethods.size == 0) {
            logger.error("No abstract method found for abstract class.")
            accessMap -= (name)
            System.exit(1)
          }

          // Inheritance
          thisClassBindingScope("inheritance") = false

          // IsAbstract
          thisClassBindingScope("isAbstract") = true

          // Binding class
          bindingScopeClass += (name -> thisClassBindingScope)
          true
      }

    // Inheritance
    def Extends(parentClass: String, bindingScopeClass:scala.collection.mutable.Map[BasicType, BasicType] = bindingScopeClass, accessMap:scala.collection.mutable.Map[BasicType, BasicType] = accessMap) : BasicType =
      // First create the child class with fields, constructor and methods
      this.evaluate()

      // Check for multiple inheritance
      if (bindingScopeClass(this.asInstanceOf[ClassDef].name).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("inheritance").asInstanceOf[Boolean]){
        logger.error(s"Multiple inheritance is not supported.")
        System.exit(1)
      }
      // Set flag for multiple inheritance to True
      bindingScopeClass(this.asInstanceOf[ClassDef].name).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("inheritance") = true

      // Get all Public fields of Parent class and add them to Child class
      // Access Map helps extract public fields. These names are searched in bindingScopeClass and added to child class
      accessMap(parentClass).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("public").asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("fields").asInstanceOf[scala.collection.mutable.Set[BasicType]].foreach {
        field => {
          bindingScopeClass(this.asInstanceOf[ClassDef].name).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("fields").asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]] += (field -> null)
        }
      }
      // Get all Protected fields of Parent class and add them to Child class
      // Access Map helps extract protected fields. These names are searched in bindingScopeClass and added to child class
      accessMap(parentClass).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("protected").asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("fields").asInstanceOf[scala.collection.mutable.Set[BasicType]].foreach {
        field => {
          bindingScopeClass(this.asInstanceOf[ClassDef].name).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("fields").asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]] += (field -> null)
        }
      }

      // Get all Public methods of Parent class and add them to Child class
      // Access Map helps extract protected methods. These names are searched in bindingScopeClass and added to child class
      accessMap(parentClass).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("public").asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("methods").asInstanceOf[scala.collection.mutable.Set[BasicType]].foreach {
        method => {
          // Get method definition
          val methodDefinition = bindingScopeClass(parentClass).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("methods").asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]](method)
          // Add method with it's definition in child class
          bindingScopeClass(this.asInstanceOf[ClassDef].name).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("methods").asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]] += (method -> methodDefinition)}
      }

      // Get all Protected methods of Parent class and add them to Child class
      // Access Map helps extract protected methods. These names are searched in bindingScopeClass and added to child class
      accessMap(parentClass).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("protected").asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("methods").asInstanceOf[scala.collection.mutable.Set[BasicType]].foreach {
        method => {
          // Get method definition
          val methodDefinition = bindingScopeClass(parentClass).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("methods").asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]](method)
          // Add method with it's definition in child class
          bindingScopeClass(this.asInstanceOf[ClassDef].name).asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]]("methods").asInstanceOf[scala.collection.mutable.Map[BasicType, BasicType]] += (method -> methodDefinition)
        }
      }

    // To extract method name from Method Expression case
    def methodEval() =
      this match{
        case Method(name, params, instructions*) => name
        case AbstractMethod(name, params) => name
      }

  @main def runAion(): Unit =
    // Main function
    // Importing all expressions
    import Expression.*

    // Error - no abstract methods
//    AbstractClassDef("class1", Public(Field("field1")), Constructor(Assign("field1", Val(1))), Public(Method("method1", List("p1", "p2"), Union(Var("p1"), Var("p2"))))).evaluate()

    AbstractClassDef("class1", Public(Field("field1")), Constructor(Assign("field1", Val(1))), Public(AbstractMethod("method1", List("p1", "p2")))).evaluate()
    println(bindingScopeClass)
    println(accessMap)
    NewObject("childObject1", "class1").evaluate()


    // WRITE YOUR CODE HERE
    // TEST SUITE IS PRESENT IN aionTestSuite.scala