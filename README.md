# AION
## Domain Specific Language for Set Operations 
## Due Date: 12-Feb-2022 11:59PM
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
                Assign("simpleVariable", Val(3)).evaluate <br>
                Assign("simpleVariable", Val("someString")).evaluate <br>
                Assign("simpleVariable", Val(Set(1,2,3)).evaluate <br>
                Assign("simpleVariable", Val(Set()).evaluate <br>
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
                Var(integerVariable).evaluate <br>
                Var(stringVariable).evaluate <br>
                Var(setVariable).evaluate <br>
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
                <b>Insert(Var("name"), Val(value)*).evaluate</b><br>
                <i>
                Examples - <br>
                Insert(Var("Set1"), Val(1), Val(2), Val(3)).evaluate
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
                <b>Delete(Var("name"), Val(value)).evaluate</b><br>
                <i>
                    Examples - <br>
                    Delete(Var("Set1"), Val(1)).evaluate
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
            <td><b>Check if value in set</b><br><i>Returns: Boolean </i></td>
            <td>
                <b>Check(Var("name"), Val(value)).evaluate</b><br>
                <i>
                    Examples - <br>
                    Delete(Var("Set1"), Val(1)).evaluate
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
                <b>Union(Var("Set1"), Var("Set2")).evaluate</b><br>
                <i>
                    Examples - <br>
                    Union(Var("Set1"), Var("Set2")).evaluate
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
                <b>Intersect(Var("Set1"), Var("Set2")).evaluate</b><br>
                <i>
                    Examples - <br>
                    Intersect(Var("Set1"), Var("Set2")).evaluate
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
                <b>Difference(Var("Set1"), Var("Set2")).evaluate</b><br>
                <i>
                    Examples - <br>
                    Difference(Var("Set1"), Var("Set2")).evaluate
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
                <b>SymmetricDifference(Var("Set1"), Var("Set2")).evaluate</b><br>
                <i>
                    Examples - <br>
                    SymmetricDifference(Var("Set1"), Var("Set2")).evaluate
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
                <b>CrossProduct(Var("Set1"), Var("Set2")).evaluate</b><br>
                <i>
                    Examples - <br>
                    CrossProduct(Var("Set1"), Var("Set2")).evaluate
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
                <b>Macro("someString", expression).evaluate</b><br>
                <i>
                    Examples - <br>
                    Macro("macro1", Union(Var("Set1"), Var("Set2"))).evaluate
                </i>
            </td>
            <td>
            </td>
        </tr>
        <tr>
            <td><b>Macro Execution</b><br><i>Returns: Any </i></td>
            <td>
                <b>MacroEval("someString").evaluate</b><br>
                <i>
                    Examples - <br>
                    MacroEval("macro1").evaluate
                </i>
            </td>
            <td>
            </td>
        </tr>
    </tbody>
</table>


## Semantics
* How are we binding variables to values?
* How to use language with tons of examples.
* Insert, delete etc. Need a set to be present in memory to add. Else error message is diplayed.

### Unit testing procedure
#### Using IntelliJ Idea
1. Clone this repository.
2. Import the project in IntelliJ Idea.
3. Write your syntactically and semantically correct AION code in main method `runAion`of `aion.scala`.
4. Run the `runAion` main method of `aion.scala` using IntelliJ Idea.

#### By SBT test command
1. Clone this repository.
2. Write your syntactically and semantically correct AION code in main method `runAion`of `aion.scala`.
3. In terminal, navigate to root path.
4. Execute - <br>
`sbt clean compile test`

  
