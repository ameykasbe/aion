# AION
## Domain Specific Language for Set Operations 
### Name Amey Kasbe
#### UIN 674285381
#### Email akasbe2@uic.edu

## Project Description
The intention of the project is to create a Domain Specific Language (DSL) for users of the set theory to create and evaluate binary operations on sets using variables and scopes where elements of the sets can be objects of any type.


## Syntax
<table>
    <thead>
        <tr>
            <td><b>Syntax type</b></td>            
            <td><b>AION Syntax</b></td>
            <td><b>Scala Analogy</b></td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><b>DataType</b><br>
            <i>
                Examples - <br>
                Integer<br>
                String<br>
                Set<br>
            </i>
            </td>
            <td>
                <b>Val(value)</b><br>
                <i>
                Examples - <br>
                Val(3) <br>
                Val("someString") <br>
                Val(Set(1, 2, 3)) <br>
                </i>
            </td>
            <td>
                <b>Int, String, Set[Any]</b><br>
                <i>
                Examples - <br>
                3 <br>
                "someString" <br>
                Set(1, 2, 3) <br>
                </i>
            </td>
        </tr>
        <tr>
            <td><b>Variable Initialization</b><br>
            <i>
                Examples - <br>
                Integer<br>
                String<br>
                Set<br>
                Empty Set<br>
            </i>
            </td>
            <td>
                <b>Assign(name, value)</b><br>
                <i>
                Examples - <br>
                Assign("simpleVariable", Val(3)).evaluate() <br>
                Assign("simpleVariable", Val("someString")).evaluate() <br>
                Assign("simpleVariable", Val(Set(1,2,3)).evaluate() <br>
                Assign("simpleVariable", Val(Set()).evaluate() <br>
                </i>
            </td>
            <td>
                <b>val simpleVariable = value</b><br>
                <i>
                Examples - <br>
                val simpleVariable = 3 <br>
                val simpleVariable = "someString" <br>
                val simpleVariable = Set(1, 2, 3) <br>
                val simpleVariable = Set() <br>
                </i>
            </td>
        </tr>
        <tr>
            <td><b>Access Variable</b><br>
            <i>
                Examples - <br>
                Integer<br>
                String<br>
                Set<br>
            </i>
            </td>
            <td>
                <b>Var(name)</b><br>
                <i>
                Examples - <br>
                Var(integerVariable).evaluate() <br>
                Var(stringVariable).evaluate() <br>
                Var(setVariable).evaluate() <br>
                </i>
            </td>
            <td>
                <b>name</b><br>
                <i>
                Examples - <br>
                integerVariable <br>
                simpleVariable <br>
                setVariable <br>
                </i>
            </td>
        </tr>
        <tr>
            <td><b>Create empty set</b><br>
            </td>
            <td>val simpleVariable = Set()</td>
            <td>val simpleVariable = Set()</td>
        </tr>
        <tr>
            <td><b>Insert into set</b><br>
            <i>Returns: null </i> 
            </td>
            <td>
                <b>Insert(Var("name"), Val(value)*).evaluate()</b><br>
                <i>
                Examples - <br>
                Insert(Var("Set1"), Val(1), Val(2), Val(3)).evaluate()
                </i>
            </td>
            <td>
                <b>name += value</b><br>
                <i>
                Examples - <br>
                set1 += (1, 2, 3) <br>
                </i>
            </td>
        </tr>
        <tr>
            <td><b>Delete from set</b><br><i>Returns: null </i></td>
            <td>
                <b>Delete(Var("name"), Val(value)).evaluate()</b><br>
                <i>
                    Examples - <br>
                    Delete(Var("Set1"), Val(1)).evaluate()
                </i>
            </td>
            <td>
                <b>name -= value</b><br>
                <i>
                    Examples - <br>
                    set1 -= 1 <br>
                </i>
            </td>
        </tr>
        <tr>
            <td><b>Check if value exists in set</b><br><i>Returns: Boolean </i></td>
            <td>
                <b>Check(Var("name"), Val(value)).evaluate()</b><br>
                <i>
                    Examples - <br>
                    Check(Var("Set1"), Val(1)).evaluate()
                </i>
            </td>
            <td>
                <b>name.contains(value)</b><br>
                <i>
                    Examples - <br>
                    set1.contains(1)
                </i>
            </td>
        </tr>
        <tr>
            <td><b>Union of two sets</b><br><i>Returns: Set </i></td>
            <td>
                <b>Union(Var("Set1"), Var("Set2")).evaluate()</b><br>
                <i>
                    Examples - <br>
                    Union(Var("Set1"), Var("Set2")).evaluate()
                </i>
            </td>
            <td>
                <b>set1.union(set2)</b><br>
                <i>
                    Examples - <br>
                    set1.union(set2)
                </i>
            </td>
        </tr>
        <tr>
            <td><b>Intersection of two sets</b><br><i>Returns: Set </i></td>
            <td>
                <b>Intersect(Var("Set1"), Var("Set2")).evaluate()</b><br>
                <i>
                    Examples - <br>
                    Intersect(Var("Set1"), Var("Set2")).evaluate()
                </i>
            </td>
            <td>
                <b>set1.intersect(set2)</b><br>
                <i>
                    Examples - <br>
                    set1.intersect(set2)
                </i>
            </td>
        </tr>
        <tr>
            <td><b>Set difference of two sets</b><br><i>Returns: Set </i></td>
            <td>
                <b>Difference(Var("Set1"), Var("Set2")).evaluate()</b><br>
                <i>
                    Examples - <br>
                    Difference(Var("Set1"), Var("Set2")).evaluate()
                </i>
            </td>
            <td>
                <b>set1.diff(set2)</b><br>
                <i>
                    Examples - <br>
                    set1.diff(set2)
                </i>
            </td>
        </tr>
        <tr>
            <td><b>Symmetric difference of two sets</b><br><i>Returns: Set </i></td>
            <td>
                <b>SymmetricDifference(Var("Set1"), Var("Set2")).evaluate()</b><br>
                <i>
                    Examples - <br>
                    SymmetricDifference(Var("Set1"), Var("Set2")).evaluate()
                </i>
            </td>
            <td>
                <b>(set1.diff(set2)).union(set2.diff(set1)</b><br>
                <i>
                    Examples - <br>
                    (set1.diff(set2)).union(set2.diff(set1)
                </i>
            </td>
        </tr>
        <tr>
            <td><b>Cartesian product of two sets</b><br><i>Returns: Set </i></td>
            <td>
                <b>CrossProduct(Var("Set1"), Var("Set2")).evaluate()</b><br>
                <i>
                    Examples - <br>
                    CrossProduct(Var("Set1"), Var("Set2")).evaluate()
                </i>
            </td>
            <td>
                <b>for {s1 <- set1; s2 <- set2} yield (s1, s2)</b><br>
            </td>
        </tr>
            </tbody>
        <tr>
            <td><b>Macro Assign</b><br><i>Returns: null </i></td>
            <td>
                <b>Macro("someString", expression).evaluate()</b><br>
                <i>
                    Examples - <br>
                    Macro("macro1", Union(Var("Set1"), Var("Set2"))).evaluate()
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Macro Execution</b><br><i>Returns: Any </i></td>
            <td>
                <b>MacroEval("someString").evaluate()</b><br>
                <i>
                    Examples - <br>
                    MacroEval("macro1").evaluate()
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Class Definition</b><br><i>Returns: null </i></td>
            <td>
                <b>ClassDef("className", Access(Field*), Constructor(), Access(Methods*) ).evaluate()</b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Create Object</b><br><i>Returns: null </i></td>
            <td>
                <b>NewObject("objectName", "className").evaluate()</b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Invoke Method</b><br><i>Returns: output (Any) </i></td>
            <td>
                <b>InvokeMethod("objectName", "methodName", parameters*).evaluate()</b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Get Field</b><br><i>Returns: field value (Any) </i></td>
            <td>
                <b>GetField("objectName", "fieldName").evaluate()</b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Inheritance</b><br><i>Returns: null </i></td>
            <td>
                <b>ClassDef(child_class_definition) Extends "ParentClass"</b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Abstract Class Definition</b><br><i>Returns: null </i></td>
            <td>
                <b>AbstractClassDef("className", Access(Field*), Constructor(), Access(Methods*), Access(AbstractMethods*) ).evaluate()</b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Abstract Method</b><br><i>Returns: null </i></td>
            <td>
                <b>AbstractMethod("methodName", parameters*)</b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Inheriting Abstract Class</b><br><i>Returns: null </i></td>
            <td>
                <b>ClassDef(child_class_definition) Extends "AbstractClassName"</b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Interface</b><br><i>Returns: null </i></td>
            <td>
                <b>Interface("interfaceName", Access(Field*), Access(AbstractMethods*) ).evaluate()</b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Implementing Interface</b><br><i>Returns: null </i></td>
            <td>
                <b>ClassDef(class_definition) Implements "InterfaceName"</b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Conditional Statements</b></td>
            <td>
                <b>If(Condition),Then(Operations-if-condition-true),Else(Operations-if-condition-false).evaluate()
                </b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Define an exception</b></td>
            <td>
                <b>ExceptionDef(exceptionName, fieldOperation)</b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Throw an exception</b></td>
            <td>
                <b>Throw(exceptionName, Assign("Reason", reasonOfException))</b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Exception Handling</b></td>
            <td>
                <b>Try("Exception-Name", Operations*, Catch("Exception-Name", Operations*)).evaluate()
                </b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Partial Evaluation</b></td>
            <td>
                <b>No Syntax change as previously used
                </b>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Optimization Function</b></td>
            <td>
                <b>MonadicsOptimize(Expression(Inputs*)).map(optimizationFunction)
                </b><br>
                <i>
                    Examples - <br>
                    Kindly check Semantics for example
                </i>
            </td>
            <td>
            </td>
        </tr>
    </tbody>
</table>


## Semantics
### DataTypes
* There is one kind of 'primitive' datatype in AION which is `Val`.
* It encloses all the datatypes of Scala e.g. Int, String, Set etc. in the format of `Val(value)`
* Any operation needed to be performed must evaluate to a `Val` datatype. 

Examples -
* Create an integer Val - `Val(100)`
* Create a string Val - `Val("someRandomString")`
* Create a set val - `Val(Set(10, 20, 30))`

### Variables
#### Initialization
* In AION, variables are initialized as -
  `Assign(name, value)`
* Here, name must always be a Scala string. 
* The string is only required at the time of initialization.
* Value must be any `Val(value)` which covers Val(<i>ScalaInt</i>), Val(<i>ScalaString</i>), Val(<i>Set</i>) etc. OR it can be an expression which must evaluate to any `Val(value)`. 

Examples -
* Create an integer variable - `Assign("simpleVariable", Val(3)).evaluate() `
* Create a string variable - `Assign("simpleVariable", Val("someString")).evaluate()`
* Create a set variable - `Assign("simpleVariable", Val(Set(1,2,3)).evaluate()`
* Create an empty set variable - `Assign("simpleVariable", Val(Set()).evaluate()`


#### Access
* To access a variable you must enclose the variable name (the string defined during initialization) with `Var()`

Examples -
* Access an integer variable - `Var("simpleVariable").evaluate() `
* Access a string variable - `Var("simpleVariable").evaluate()`
* Access a set variable - `Var("simpleVariable").evaluate()`
* Access an empty set variable - `Var("simpleVariable").evaluate()`

#### How initialization and access of variables works internally in AION? 
* Memory is represented by a HashMap `bindingScope`. 
* The string defined at the time of initialization is mapped to the values in this HashMap.
* When any variable is accessed by Var(), it is searched in the `bindingScope`, if it is present, the value is returned else an error message is displayed and the program is exited.

#### Analogy to Scala
* For example, in scala there can not be an expression that will result into the variable's name itself. <br >Example - <br>
val integerVariable = 2 <br>
There can not be an expression that evaluates to integerVariable's name integerVariable i.e. no expression can evaluate to the name of the variable itself, other than using the intergerVariable name while initialization, accessing and modifying the variable. 
* These initialization, access and modification are done in AION like - <br>
  * Initialization - `Assign("simpleVariable", Val(3)).evaluate()` <br>
  * Modification - `Assign("simpleVariable", Val(100)).evaluate()` <br>
  * Access - `Var("simpleVariable").evaluate()` <br>
* So there is no need for Var() to be present in the LHS of the Assign operation i.e. there is no need for `Assign(Var('simpleVaribale), Val('someString')).evaluate()`

### Execution of any statement
* To execute any statement or evaluate any expression, the method `evaluate()` must be called from the expression.
* Examples -
  * `Assign("simpleVariable", Val(3)).evaluate()`
  * `Var(integerVariable).evaluate()`
  * `Insert(Var("Set1"), Val(1), Val(2), Val(3)).evaluate()`
  * `Delete(Var("Set1"), Val(1)).evaluate()`
  * `Union(Var("Set1"), Var("Set2")).evaluate()`

### Sets
#### Initialization, Updation and Access
* Sets are initialized, updated and accessed as mentioned above - 
    * Initialization - `Assign("simpleVariable", Val(Set(1, 2, 3))).evaluate()` <br>
    * Initialization of an empty set - `Assign("simpleVariable", Val(Set())).evaluate()` <br>
    * Modification - `Assign("simpleVariable", Val(Set(4, 5, 6))).evaluate()` <br>
    * Access - `Var("simpleVariable").evaluate()` <br>

#### Insertion
* An element can be inserted into a by the expression - `Insert(Var("name"), Val(value)*).evaluate()`
* Examples - 
  * Single element insertion - `Insert(Var("Set1"), Val(1)).evaluate()`
  * Multiple elements insertion - `Insert(Var("Set1"), Val(1), Val(2), Val(3)).evaluate()`
* Note:
  * There must be a set present in the memory with binding of the name of the variable.
  * If there is no binding or if the binding is not of a set, then the operation results into an error and the program is exited.

#### Deletion
* An element can be deleted from a set by the expression - `Delete(Var("name"), Val(value)).evaluate()`
* Examples - `Delete(Var("Set1"), Val(1)).evaluate()`
* Note:
  * There must be a set present in the memory with binding of the name of the variable.
  * If there is no binding or if the binding is not of a set, then the operation results into an error and the program is exited.

#### Check if value exists in set
* An element can be searched in a set by the expression - `Check(Var("name"), Val(value)).evaluate()`
* Examples - `Check(Var("Set1"), Val(1)).evaluate()`
* Note:
    * There must be a set present in the memory with binding of the name of the variable.
    * If there is no binding or if the binding is not of a set, then the operation results into an error and the program is exited.

#### Union
<i>Returns: Set</i>
* The union of two sets can be evaluated by expression - `Union(Var("Set1"), Var("Set2")).evaluate()`
* Examples - 
  * `Union(Var("Set1"), Var("Set2")).evaluate()`
  * `Union(Val(Set(1, 2, 3))), Val(Set(2, 3, 4))).evaluate()`

#### Intersection
<i>Returns: Set</i>
* The intersection of two sets can be evaluated by expression - `Intersect(Var("Set1"), Var("Set2")).evaluate()`
* Examples -
  * `Intersect(Var("Set1"), Var("Set2")).evaluate()`
  * `Intersect(Val(Set(1, 2, 3))), Val(Set(2, 3, 4))).evaluate()`

#### Set difference
<i>Returns: Set</i>
* The set difference of two sets can be evaluated by expression - `Difference(Var("Set1"), Var("Set2")).evaluate()`
* Examples -
  * `Difference(Var("Set1"), Var("Set2")).evaluate()`
  * `Difference(Val(Set(1, 2, 3))), Val(Set(2, 3, 4))).evaluate()`

#### Symmetric difference
<i>Returns: Set</i>
* The symmetric difference of two sets can be evaluated by expression - `SymmetricDifference(Var("Set1"), Var("Set2")).evaluate()`
* Examples -
  * `SymmetricDifference(Var("Set1"), Var("Set2")).evaluate()`
  * `SymmetricDifference(Val(Set(1, 2, 3))), Val(Set(2, 3, 4))).evaluate()`

#### Cartesian product
<i>Returns: Set</i>
* The cartesian product of two sets can be evaluated by expression - `CrossProduct(Var("Set1"), Var("Set2")).evaluate()`
* Examples -
  * `CrossProduct(Var("Set1"), Var("Set2")).evaluate()`
  * `CrossProduct(Val(Set(1, 2, 3))), Val(Set(2, 3, 4))).evaluate()`

### Macro
#### Macro Assignment
* Macros are assigned by expression - `Macro("someString", expression).evaluate()`
* Example - 
  * `Macro("macro1", Union(Var("Set1"), Var("Set2"))).evaluate()`

#### Macro Evaluation
* Macros are evaluated by expression - `MacroEval("someString").evaluate()`
* Examples - 
  * `Macro("macro1", Union(Var("Set1"), Var("Set2"))).evaluate()`
  * `MacroEval("macro1").evaluate()`

#### Assignment similar as variables -
* Macros are assigned in the similar way as variables in AION. 
* String passed as the macro name is mapped to the operation.
* When executed, the macro name string fetches the operation and evaluates it. 

### Scope
* Up until now the scope of all the expressions was global.
* To assign a scope to any expression, pass a string with the scope name in the evaluate method.
* Format - `Expression.evaluate(scopeName)`
* Examples - 
  * Global scope
  ```
  Assign("Set1", Val(Set(1, 2, 3))).evaluate()
  Assign("Set2", Val(Set(2, 3, 4))).evaluate()
  println(Union(Var("Set1"), Var("Set2")).evaluate())
  ```
  ```
  Output: Set(1, 2, 3, 4)
  ```
  
  * User defined scopes
  ```
  Assign("Set1", Val(Set(1, 2, 3))).evaluate("a")
  Assign("Set2", Val(Set(2, 3, 4))).evaluate("a")
  Assign("Set1", Val(Set(10, 20, 30))).evaluate("b")
  Assign("Set2", Val(Set(20, 30 ,40))).evaluate("b")
  println(Union(Var("Set1"), Var("Set2")).evaluate("a"))
  ```
  ```
  Output: Set(1, 2, 3, 4)
  ```
  * User defined scopes
  ```
  Assign("Set1", Val(Set(1, 2, 3))).evaluate("a")
  Assign("Set2", Val(Set(2, 3, 4))).evaluate("a")
  Assign("Set1", Val(Set(10, 20, 30))).evaluate("b")
  Assign("Set2", Val(Set(20, 30 ,40))).evaluate("b")
  println(Union(Var("Set1"), Var("Set2")).evaluate("b"))
  ```
  ```
  Output: Set(10, 20, 30, 40)
  ```
  * If scope not found, then global scope is searched
  ```
  Assign("Set1", Val(Set(1, 2, 3))).evaluate()
  Assign("Set2", Val(Set(2, 3, 4))).evaluate()
  Assign("Set1", Val(Set(10, 20, 30))).evaluate("b")
  Assign("Set2", Val(Set(20, 30, 40))).evaluate("b")
  println(Union(Var("Set1"), Var("Set2")).evaluate("a"))
  ```
  ```
  Output: Set(1, 2, 3, 4)
  ```

* The scopes are parallel to each other except global.
* The hierarchy of scopes is as follows - 
  * global scope
    * User defined scope 1 
    * User defined scope 2
    * and so on...


### Nested expressions example
  ```
  Assign("Set1", Val(Set(1, 2, 3))).evaluate()
  Assign("Set2", Val(Set(2, 3, 4))).evaluate()
  Assign("Set3", Val(Set(10, 20, 30))).evaluate()
  Assign("Set4", Val(Set(20, 30, 40))).evaluate()
  println(Union(Union(Var("Set1"), Var("Set2")), Union(Var("Set3"), Var("Set4"))).evaluate())
  ```
  ```Output: Set(1, 2, 3, 4, 10, 20, 30, 40)```

### Class Definition
* In AION, class is defined with ClassDef.
* Syntax
  ```
  ClassDef("className", Access(Field*), Constructor(), Access(Methods*) ).evaluate()
  ```
* Similar to variables, classes are also binded with strings. 

#### Access Specifiers
* AION supports three access specifiers
  * Public
  * Private
  * Protected
* The functionality is similar to the fundamental Object-Oriented principles.
* All fields and methods should be encapsulated with ONE of the three access specifiers.
* Fields and Methods can be placed in any order in the arguments.

#### Fields
* Fields should be wrapped with Field case.
* Any number of fields can be added with no boundation on the order of the arguments.

#### Constructor
* Only one constructor should be placed in the Class definition.
* Constructor should be wrapped with Constructor()
* Inside constructor, multiple instructions/expressions can be written.
* No boundation on the order of constructor in the class definition.

#### Methods
* Methods have a particular format
  * First argument is the method name
  * Second argument is the List of parameters
  * After second argument, arbitrary number of parameters can be passed. These specify the instructions.

* Examples -
  ```
  ClassDef("class1", Public(Field("field1")), Constructor(Assign("field1", Val(1))), Public(Method("method1", List("p1", "p2"), Union(Var("p1"), Var("p2"))))).evaluate()
  ```
### Create Object
* An instance of a particular class is created.
* After creating instance, the constructor is executed for the instance.
* Syntax
  ```
  NewObject("objectName", "className").evaluate()
  ```
* Examples -
  ```
  ClassDef("class1", Public(Field("field1")), Constructor(Assign("field1", Val(1))), Public(Method("method1", List("p1", "p2"), Union(Var("p1"), Var("p2"))))).evaluate()
  NewObject("object1", "class1").evaluate()
  ```

### Get Field
* To get the data of a field of a particular object of a class.
* Only the public data can be accessed.
* If private or protected data is accessed, error message is displayed and the program exits. 
* Syntax
  ```
  GetField("objectName", "fieldName").evaluate()
  ```
* Examples -
  ```
  ClassDef("class1", Public(Field("field1")), Constructor(Assign("field1", Val(1))), Public(Method("method1", List("p1", "p2"), Union(Var("p1"), Var("p2"))))).evaluate()
  NewObject("object1", "class1").evaluate()
  println(GetField("object1", "field1").evaluate())
  ```
  Output
  ```
  1
  ```
### Invoke Method
* Any object's method can be invoked using InvokeMethod.
* Syntax
  ```
  InvokeMethod("objectName", "methodName", parameters*).evaluate()
  ```
* It takes first argument a string which is the name of the object.
* Second argument is method name.
* Then it takes a number of arguments equal to the number of arguments of the method. Internally, values are assigned to the arguments.   
* Example -
  ```
  ClassDef("class2", Public(Field("field2")), Constructor(Assign("field2", Val(1))), Public(Method("method2", List("p1", "p2"), Union(Var("p3"), Var("p4"))))).evaluate()
  NewObject("object2", "class2").evaluate()
  val result = InvokeMethod("object2", "method2", Assign("p3", Val(Set(1, 2, 3))), Assign("p4", Val(Set(1, 2, 4)))).evaluate()
  ```
  Output
  ```
  HashSet(1,2,3,4)
  ```
### Inheritance
* Syntax
  ```
  ClassDef(child_class_definition) Extends "ParentClass"
  ```
  
* In AION, inheritance works with `Extends` keyword.
* The child class definition is executed first.
* Then, all the Public and Protected members of the parent class is inherited in the child class.
* Private members of the parent class is not inherited to the child class.
* Methods get the same definition.
* Fields are initialized with null by default which can be updated.
* Internally, inheritance creates a copy of the public and protected members of the parent class into the child class. 

* Examples -
  ```
  ClassDef("parentClass", Public(Field("parentField")), Constructor(Assign("parentField", Val(1)))).evaluate()
  ClassDef("childClass", Public(Field("childField")), Constructor(Assign("childField", Val(2)))) Extends "parentClass"
  ```
  
### Abstract class
* Syntax
```
AbstractClassDef("className", Access(Field*), Constructor(), Access(Methods*), Access(AbstractMethods*) ).evaluate()
```
* An abstract class that cannot be instantiated, but they can be inherited.
* At least one method of an abstract class should be an abstract method.
* An abstract method is a method that is declared without an implementation.
* All abstract methods should be overridden.

* Example - 
```
AbstractClassDef("parentClass3", Public(Field("parentField")), Constructor(Assign("parentField", Val(1))), Public(Method("method1", List("p1", "p2"), Union(Var("p1"), Var("p2")))), Protected(AbstractMethod("methodParent", List("p1", "p2")))).evaluate()
```
* Error scenarios -
  * If there is no abstract method, then abstract class can not be created. 
  ```
  AbstractClassDef("class1", Public(Field("field1")), Constructor(Assign("field1", Val(1))), Public(Method("method1", List("p1", "p2"), Union(Var("p1"), Var("p2"))))).evaluate()
  ```
  * Instance of an abstract class can not be created.
  ```
  AbstractClassDef("parent8",  Public(Field("parentField")), Protected(AbstractMethod("methodParent", List("p1", "p2")))).evaluate()
  NewObject("childObject8", "parent8").evaluate()
  ```
  * If any abstract method is not overridden, then error occurs.
  ```
  AbstractClassDef("parent",  Public(Field("parentField")), Constructor(Assign("parentField", Val(1))), Protected(AbstractMethod("methodParent", List("p1", "p2"))), Public(AbstractMethod("methodParent2", List("p8", "p9")))).evaluate()
  ClassDef("child",  Public(Field("parentField")), Constructor(Assign("parentField", Val(1))), Protected(Method("methodParent", List("p1", "p2"), Difference(Var("p3"), Var("p4"))))) Extends "parent"
  ```


#### Inheriting Abstract Class
* Abstract classes are inherited using "Extends" keyword
* Syntax
```
ClassDef(child_class_definition) Extends "ParentClass"
```
* Example - 
```
AbstractClassDef("parentClass3", Public(Field("parentField")), Constructor(Assign("parentField", Val(1))), Public(Method("method1", List("p1", "p2"), Union(Var("p1"), Var("p2")))), Protected(AbstractMethod("methodParent", List("p1", "p2")))).evaluate()
ClassDef("childClass3", Constructor(Assign("childField2", Val(2))), Public(Method("methodParent", List("p1", "p2"), Difference(Var("p1"), Var("p2"))))) Extends "parentClass3"
```
  
### Interface
* An interface is a group of related methods with empty bodies. [Source](https://docs.oracle.com/javase/tutorial/java/concepts/interface.html)
* Interface can also not be instantiated, but they can be implemented.
* Interface should contain at least one abstract method in AION.
* An abstract method is a method that is declared without an implementation.
* All abstract methods should be overridden by implementing class.
* Syntax
```
Interface("interfaceName", Access(Field*), Access(AbstractMethods*) ).evaluate()
```
* Example
```
Interface("parentClass6", Public(Field("parentField")), Protected(AbstractMethod("methodParent", List("p1", "p2")))).evaluate()
```
#### Implementing Interface
* Any interface is implemented using "Implements" keyword in AION.
* Syntax
```
ClassDef(class_definition) Implements "InterfaceName"
```
* Example
```
Interface("parentClass6", Public(Field("parentField")), Protected(AbstractMethod("methodParent", List("p1", "p2")))).evaluate()
ClassDef("childClass6", Constructor(Assign("childField2", Val(2))), Public(Method("methodParent", List("p1", "p2"), Difference(Var("p1"), Var("p2"))))) Implements "parentClass6"
```
* Error scenarios -
  * If there is a constructor or a concreate method, then interface can not be created.
  ```
  Interface("parent",  Public(Field("parentField")), Constructor(Assign("parentField", Val(1))), Protected(AbstractMethod("methodParent", List("p1", "p2"))), Protected(Method("methodParent2", List("p8", "p9")))).evaluate()
  ```
  * Instance of an interface can not be created.
  ```
  Interface("parent9",  Public(Field("parentField")), Protected(AbstractMethod("methodParent", List("p1", "p2")))).evaluate()
  val result = NewObject("childObject9", "parent9").evaluate()
  ```
  * If any abstract method is not overridden, then error occurs.
  ```
  Interface("parent",  Public(Field("parentField")), Protected(AbstractMethod("methodParent", List("p1", "p2"))), Public(AbstractMethod("methodParent2", List("p8", "p9")))).evaluate()
  ClassDef("child",  Public(Field("parentField")), Constructor(Assign("parentField", Val(1))), Protected(Method("methodParent", List("p1", "p2"), Difference(Var("p3"), Var("p4"))))) Implements "parent"
  ```

## FAQs
* Difference between abstract classes and Interface
  * Key difference between interface and an abstract class is that: Abstract classes can have attributes, method declarations  and defined methods, whereas interfaces can only have attributes and methods declarations. [Source](https://stackoverflow.com/questions/1913098/what-is-the-difference-between-an-interface-and-abstract-class#:~:text=The%20key%20technical%20differences%20between,have%20constants%20and%20methods%20stubs.)

* Can a class/interface inherit from itself?
  * No, a class/interface can not inherit from itself. It makes no sense to inherit attributes from itself, might form an endless loop of inheritance.
  
* Can an interface inherit from an abstract class with all pure methods?
  * An interface can not inherit from an abstract class because interface can only inherit from other interfaces. By definition, interface can only contain attributes and method declarations while classes (including abstract classes) can have attributes, hence inheritance is not possible in all cases even if all the methods are pure. 
* Can an interface implement another interface?
  * No, an interface can not implement another interface. By definition, interface can only have method declarations and to implement any interface, it's methods are defined, which by definition can not be done in another interface.
* Can a class implement two or more different interfaces that declare methods with exactly the same signatures?
  * Yes, a class can implement two or more different interfaces that declare methods with exactly the same signatures. If the method signatures are same, then both the methods would be overridden by the class. The "Implements" design in AION works in this case as well. The class created and interfaces' declarations check would be done in similar manner.   
* Can an abstract class implement interfaces?
  * Yes, an abstract class can implement interfaces. AION's implements design works in this case as well. Abstract class is a type of class in AION and by definition, abstract class can have method definitions. Defining all abstract methods of interfaces would result in implementing interface in an abstract class. You can have any abstract methods in the abstract class. 
* Can a class implement two or more interfaces that have methods whose signatures differ only in return types?
  * No, a class can not implement two or more interfaces that have methods whose signatures differ only in return types.
* Can an abstract class inherit from a concrete class?
  * Yes, an abstract class can inherit from a concrete class. AION's "Extends" design works in this case. 
* Can an abstract class/interface be instantiated as anonymous concrete classes?
  * By definition abstract class can not be instantiated. But anonymous class can implement abstract class or interface. 

### Conditional Statements
* Conditional statements makes decisions based on certain conditions.
* Decisions are made if the pre-stated conditions are true or false.
* In AION, if the condition present in the "If" condition is true, then, operations present only in "Then" clause are executed.
* If not, then the operations present only in "Else" clause are executed.
* Syntax
  ```
  If(Condition),Then(Operations-if-condition-true),Else(Operations-if-condition-false).evaluate()
  ```
* Example
  ```
  Assign("set2", Val(Set(1, 2, 3))).evaluate()
  If(Check(Var("set2"), Val(4)),
    Then(Delete(Var("set2"), Val(4))),
    Else(Insert(Var("set2"), Val(4))),
  ).evaluate()
  ```

### Define an Exception
* This defines an exception in the exception binding.
* User can define own exceptions using `ExceptionDef`.
* You need to pass a field "Reason" as well.
* Syntax
  ```
  ExceptionDef(exceptionName, fieldOperation)
  ```
* Example
  ```
  ExceptionDef("DataNotFoundError", Field("Reason")).evaluate()
  ```

### Throw an exception
* To throw an exception use "Throw" clause.
* Syntax
  ```
  Throw(exceptionName, Assign("Reason", reasonOfException))
  ```
* Example
  ```
  Throw("DataNotFoundError", Assign("Reason", Val("Data is not present.")))
  ```

### Exception Handling
* This is a process of responding to the occurrence of exceptions – anomalous or exceptional conditions requiring special processing – during the execution of a program. [Source](https://en.wikipedia.org/wiki/Exception_handling)
* Exception Handling is a mechanism to handle runtime errors. [Source](https://www.javatpoint.com/exception-handling-in-java)
* The "try" keyword is used to specify a block where we should place an exception code. It means we can't use try block alone. The try block must be followed by either catch or finally. [Source](https://www.javatpoint.com/exception-handling-in-java)
* The "catch" block is used to handle the exception. It must be preceded by try block which means we can't use catch block alone. [Source](https://www.javatpoint.com/exception-handling-in-java)
* Syntax
```
Try("Exception-Name", Operations*, Catch("Exception-Name", Operations*)).evaluate()
```
* Example
```
ExceptionDef("DataNotFoundError", Field("Reason")).evaluate()
Assign("set4", Val(Set(1, 2, 3))).evaluate()
val x = Try("DataNotFoundError",
  // Throw an error if data is not present.
  Throw("DataNotFoundError", Assign("Reason", Val("Data is not present."))),
  Delete(Var("set4"), Val(4)),
  // Catch
  Catch("DataNotFoundError", Insert(Var("set4"), Val(100)))
).evaluate()
```


### Partial Evaluation
* Syntax
  * No change in syntax.
* Allow undefined variables during evaluation by preserving syntactical phrases if they cannot be evaluated completely.
* If any variable is not defined, instead of raising an exception, return the expression with variable intact without evaluating.

* Examples
  * partially evaluated union of sets when one of the sets are not defined
    ```
    Assign("Set2", Val(Set(1, 2, 3))).evaluate()
    Union(Var("Set1"), Var("Set2")).evaluate() 
    ```
    Output
    ```
    Union(Var("Set1"),Val(Set(1,2,3))))
    ```
  * should return partially evaluated union of sets when both sets are not defined
    ```
    Union(Var("Set1"), Var("Set2")).evaluate() 
    ```
    Output
    ```
    Union(Var("Set1"),Var("Set2")))
    ```
    
### Optimization
* Aim is to optimize evaluation of expressions in AION.
* There are certain axioms in Set theory which reduces the computation.
* Syntax
  ```
  MonadicsOptimize(Expression(Inputs*)).map(optimizationFunction)
  ```
  Example
  ```
  MonadicsOptimize(Difference(Var("Set1"), Var("Set2"))).map(optimizedEvaluate)
  ```
  
`* Note that there was no complete evaluation involved in the below cases.`
* Other Examples -
  * Union of two identical sets is equal to any one of the two sets.
  ```
  Assign("Set1", Val(Set(1, 2, 3))).evaluate()
  Assign("Set2", Val(Set(1, 2, 3))).evaluate()
  MonadicsOptimize(Union(Var("Set1"), Var("Set2"))).map(optimizedEvaluate)
  ```
  Output
  ```
  Set(1, 2, 3)
  ```
  * Union of one set with empty set is the non-empty set.
  ```
  Assign("Set1", Val(Set(1, 2, 3))).evaluate()
  Assign("Set2", Val(Set())).evaluate()
  MonadicsOptimize(Union(Var("Set1"), Var("Set2"))).map(optimizedEvaluate)
  ```
  Output
  ```
  Set(1, 2, 3)
  ```
  * Intersection of one set with empty set is an empty set.
  ```
  Assign("Set1", Val(Set(1, 2, 3))).evaluate()
  Assign("Set2", Val(Set())).evaluate()
  MonadicsOptimize(Intersect(Var("Set1"), Var("Set2"))).map(optimizedEvaluate)
  ```
  Output
  ```
  Set()
  ```
  * Difference of one set from empty set is an empty set.
  ```
  Assign("Set1", Val(Set())).evaluate()
  Assign("Set2", Val(Set(1, 2, 3))).evaluate()
  MonadicsOptimize(Difference(Var("Set1"), Var("Set2"))).map(optimizedEvaluate)
  ```
  Output
  ```
  Set()
  ```
      

## Files
### Source Code
* Source code is present in `aion.scala`
* Test suite is present in `aionTestSuite.scala`

## Execution Procedure
### Using IntelliJ Idea
1. Clone this repository.
2. Import the project in IntelliJ Idea.
3. Write your syntactically and semantically correct AION code in main method `runAion`of `aion.scala`.
4. Run the main method `runAion` of `aion.scala` using IntelliJ Idea.

### By SBT run command
1. Clone this repository.
2. In terminal, navigate to root path.
3. Write your syntactically and semantically correct AION code in main method `runAion`of `aion.scala`.
4. Execute - <br>
   `sbt clean compile run`

## Unit testing procedure
### Using IntelliJ Idea
1. Clone this repository.
2. Import the project in IntelliJ Idea.
3. Run the `aionTestSuite` class of `aionTestSuite.scala` using IntelliJ Idea.

### By SBT test command
1. Clone this repository.
2. In terminal, navigate to root path.
3. Execute - <br>
`sbt clean compile test`

Note: If java.io.IOException is caught. Enter 'y' to create a new server.
```
sbt thinks that server is already booting because of this exception:
java.io.IOException: Could not create lock for \\.\pipe\sbt-load938732335309032081_lock, error 5
Create a new server? y/n (default y)
```
~ Enter: y


