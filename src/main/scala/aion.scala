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

//    case getMap()
//    case printMap()
//    case Union(setName1: Exp, setName2: Exp)
//    case Intersect(setName1: String, setName2: String)
//    case Check(setName: String, element: Any)

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





//        case printMap() => bindingScope
//        case Union(setName1, setName2) => {
//          val set1 = setName1.evaluate.asInstanceOf[Set[Any]]
//          val set2 = setName2.evaluate.asInstanceOf[Set[Any]]
//          set1.union(set2)
////          bindingScope(setName1).asInstanceOf[Set[Any]].union(bindingScope(setName2).asInstanceOf[Set[Any]])
//        }
//        case Intersect(setName1, setName2) => {
//          bindingScope(setName1).asInstanceOf[Set[Any]].intersect(bindingScope(setName2).asInstanceOf[Set[Any]])
//        }
//        case Check(setName, element) => {
//
//        }


      }

  @main def runAion: Unit =
    import Exp.*
    Assign("Amey", Val(Set(1))).evaluate
    Insert(Var("Amey"), Val(2)).evaluate
    println(Var("Amey").evaluate)
//    Assign("amey", "Kasbe").evaluate
//    Assign("amey", Val("Bobby")).evaluate
//    Assign("amey", Var("amey")).evaluate
//
//    Assign("bobby", "Aikya").evaluate
//    Assign("bobby", "Samihan").evaluate
//    Assign("bobby", "Chinmay").evaluate
//    Assign("bobby", "Amey").evaluate

//    println(printMap().evaluate)

//
//    val x = Union(Var("amey"), Var("bobby")).evaluate
//    println(x)
//    val y = Intersect("amey", "bobby").evaluate
//    println(y)
