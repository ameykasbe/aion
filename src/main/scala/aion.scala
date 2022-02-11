package scala
import scala.collection.mutable.Map
import scala.collection.mutable.Set
object aion extends App:
  private var bindingScope: scala.collection.mutable.Map[Any, Any] = Map()
  enum Exp:
    case Val(value: Any)
    case Var(name: String)
    case getVar(variable: Exp)
    case Assign(name:String, value: Exp)
    case Insert(setToInsert: Exp, value: Exp)
    case Delete(setToInsert: Exp, value: Exp)
    case Union(setName1: Exp, setName2: Exp)
    case Intersect(setName1: Exp, setName2: Exp)


    def evaluate: Any =
      this.match{
        case Val(value) => value
        case Var(name) => bindingScope(name)
        case Assign(name, value) => {
              bindingScope += (name -> value.evaluate)
        }
        case Insert(setName, value) => {
          val evaluatedSetName = setName.evaluate
          val evaluatedValue = value.evaluate
          bindingScope.update(evaluatedSetName, evaluatedSetName.asInstanceOf[Set[Any]] += evaluatedValue)
        }
        case Delete(setName, value) => {
          val evaluatedSetName = setName.evaluate
          val evaluatedValue = value.evaluate
          bindingScope.update(evaluatedSetName, evaluatedSetName.asInstanceOf[Set[Any]] -= evaluatedValue)
        }
        case Union(setName1, setName2) => {
          val set1 = setName1.evaluate.asInstanceOf[Set[Any]]
          val set2 = setName2.evaluate.asInstanceOf[Set[Any]]
          set1.union(set2)
        }
        case Intersect(setName1, setName2) => {
          val set1 = setName1.evaluate.asInstanceOf[Set[Any]]
          val set2 = setName2.evaluate.asInstanceOf[Set[Any]]
          set1.intersect(set2)
        }
      }

  @main def runAion: Unit =
    import Exp.*
    Assign("Amey", Val(Set(1))).evaluate
    Insert(Var("Amey"), Val(2)).evaluate

    Assign("Bobby", Val(Set(2))).evaluate
    Insert(Var("Bobby"), Val(3)).evaluate


    println(Var("Amey").evaluate)
    println(Var("Bobby").evaluate)
    println(Union(Var("Amey"), Var("Bobby")).evaluate)
    println(Intersect(Var("Amey"), Var("Bobby")).evaluate)

    Delete(Var("Bobby"), Val(2)).evaluate
    println(Var("Bobby").evaluate)
