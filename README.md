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


## Files
### Source Code
* Source code is present in `aion.scala`
* Test suite is present in `aionTestSuite.scala`

## Unit testing procedure
### Using IntelliJ Idea
1. Clone this repository.
2. Import the project in IntelliJ Idea.
3. Write your syntactically and semantically correct AION code in main method `runAion`of `aion.scala`.
4. Run the `runAion` main method of `aion.scala` using IntelliJ Idea.

### By SBT test command
1. Clone this repository.
2. Write your syntactically and semantically correct AION code in main method `runAion`of `aion.scala`.
3. In terminal, navigate to root path.
4. Execute - <br>
`sbt clean compile test`

  
