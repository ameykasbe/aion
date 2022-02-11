package scala

object aion extends App:
  private var bindingDataToVar: Map[String, Any] = Map.empty[String, Any]
  enum Exp:
    case MapNameValue(name:String, value: Any)
    case Value(value: Any)
    case Var(name: String)
    case Assign(name:String, value: Any)
    case getMap()
    case printMap()
    case Insert(setToInsert:String, value:Exp)
    case Union(setName1: String, setName2: String)

    def evaluate: Any =
      this.match{
        case Value(value) => value
        case Var(name) => bindingDataToVar(name)
        case printMap() => bindingDataToVar
        case Assign(name, value) => {
            if (bindingDataToVar.contains(name)){
                val setInMap = bindingDataToVar(name).asInstanceOf[Set[Any]]
                bindingDataToVar += (name -> (setInMap ++ Set(value)))
            }
            else {
              bindingDataToVar += (name -> Set(value))
            }
        }
        case Insert(setName, value) => {
          Assign(setName, value.evaluate).evaluate
        }
        case Union(setName1, setName2) => {
          bindingDataToVar(setName1).asInstanceOf[Set[Any]] ++ bindingDataToVar(setName2).asInstanceOf[Set[Any]]
        }
      }

  @main def runAion: Unit =
    import Exp.*
    Assign("amey", "Amey").evaluate
    println(printMap().evaluate)
    Assign("amey", "Kasbe").evaluate
    println(printMap().evaluate)
    Assign("amey", Value("Bobby")).evaluate
    println(printMap().evaluate)
    Assign("amey", Var("amey")).evaluate
    println(printMap().evaluate)

    val x = Union("amey", "amey").evaluate


    println(x)