package scala
import scala.collection.mutable.Map
import scala.collection.mutable.Set
import org.slf4j.{Logger, LoggerFactory}
object aion extends App:
  private var bindingScope: scala.collection.mutable.Map[Any, Any] = Map()
  val logger: Logger = LoggerFactory.getLogger(this.getClass.getSimpleName)

  enum Exp:
    case Val(value: Any)
    case Var(name: String)
    case getVar(variable: Exp)
    case Assign(name:Any, value: Exp)
    case Insert(setToInsert: Exp, value: Exp)
    case Delete(setToInsert: Exp, value: Exp)
    case Union(setName1: Exp, setName2: Exp)
    case Intersect(setName1: Exp, setName2: Exp)
    case Difference(setName1: Exp, setName2: Exp)
    case Macro(macroName: Exp, operand: Exp)
    case MacroEval(macroName: Exp)

    def evaluate: Any =
      this.match{
        case Val(value) => value
        case Var(name) => {
          if (bindingScope.contains(name)) {
            bindingScope(name)
          }
          else
          {
             null
          }
        }
        case Assign(name, value) => {
              bindingScope += (name -> value.evaluate)
        }
        case Insert(setName, value) => {
          val evaluatedSetName = setName.evaluate
          val evaluatedValue = value.evaluate
          if (evaluatedSetName != null){
            bindingScope.update(evaluatedSetName, evaluatedSetName.asInstanceOf[Set[Any]] += evaluatedValue)
          }
          else{
            logger.error(s"Name $setName not assigned.")
          }
        }
        case Delete(setName, value) => {
          val evaluatedSetName = setName.evaluate
          val evaluatedValue = value.evaluate
          bindingScope.update(evaluatedSetName, evaluatedSetName.asInstanceOf[Set[Any]] -= evaluatedValue)
        }
        case Union(setName1, setName2) => {
          val set1 = setName1.evaluate.asInstanceOf[Set[Any]]
          val set2 = setName2.evaluate.asInstanceOf[Set[Any]]
          if ((set1 != null) && (set2 != null)){
            set1.union(set2)
          }
          else{
            if (set1 == null){
              logger.error(s"Name $setName1 not assigned.")
            }
            else if (set2 == null){
              logger.error(s"Name $setName1 not assigned.")
            }
            else{
              logger.error("Unexpected error occured. Please check documentation.")
            }
          }
        }
        case Intersect(setName1, setName2) => {
          val set1 = setName1.evaluate.asInstanceOf[Set[Any]]
          val set2 = setName2.evaluate.asInstanceOf[Set[Any]]
          if ((set1 != null) && (set2 != null)){
            set1.intersect(set2)
          }
          else{
            if (set1 == null){
              logger.error(s"Name $setName1 not assigned.")
            }
            else if (set2 == null){
              logger.error(s"Name $setName1 not assigned.")
            }
            else{
              logger.error("Unexpected error occured. Please check documentation.")
            }
          }
        }
        case Difference(setName1, setName2) => {
          val set1 = setName1.evaluate.asInstanceOf[Set[Any]]
          val set2 = setName2.evaluate.asInstanceOf[Set[Any]]
          if ((set1 != null) && (set2 != null)) {
            set1.diff(set2)
          }
          else{
            if (set1 == null){
              logger.error(s"Name $setName1 not assigned.")
            }
            else if (set2 == null){
              logger.error(s"Name $setName1 not assigned.")
            }
            else{
              logger.error("Unexpected error occured. Please check documentation.")
            }
          }
        }
        case Macro(macroName, operand) => {
          val MacroNameEval = macroName.evaluate
          bindingScope += (MacroNameEval -> operand)
        }
        case MacroEval(macroName) => {
          val macroNameEval = macroName.evaluate
          val returnIfAny = bindingScope(macroNameEval).asInstanceOf[Exp].evaluate
          returnIfAny
        }
      }

  @main def runAion: Unit =
    import Exp.*
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

    Assign("Set1", Val(Set())).evaluate
    Assign("Set2", Val(Set())).evaluate
    Insert(Var("Set1"), Val(1)).evaluate
    Insert(Var("Set1"), Val(2)).evaluate
    Insert(Var("Set1"), Val(3)).evaluate
    Insert(Var("Set2"), Val(2)).evaluate
    Insert(Var("Set2"), Val(3)).evaluate
    Insert(Var("Set2"), Val(4)).evaluate

    println((Var("Set1").evaluate))
    println((Var("Set2").evaluate))
    println(Difference(Var("Set1"), Var("Set2")).evaluate)
    println(Difference(Var("Set2"), Var("Set1")).evaluate)
    println(Difference(Var("Amey"), Var("Bobby")).evaluate)
    println(Difference(Var("Bobby"), Var("Amey")).evaluate)

