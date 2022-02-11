import org.slf4j.{Logger, LoggerFactory}
object practice extends App{
  @main def runAion: Unit = {
    val logger: Logger = LoggerFactory.getLogger(this.getClass.getSimpleName)
    logger.info("Created ActorSystem and ActorMaterializer instances.")

  }

}





    // Earlier assign
//    case Assign(name, value) => {
//      if (bindingScope.contains(name)){
//        val setInMap = bindingScope(name).asInstanceOf[Set[Any]] // If immutable set, converting to mutable set for ++ operator to append
//        bindingScope += (name -> (setInMap ++ Set(value)))
//      }
//      else {
//        bindingScope += (name -> Set(value))
//      }
//    }




//    println("Amey".asInstanceOf[Set[Any]])

//    var dictionary: Map[String, Set[Any]] = Map()
//    dictionary += ("a" -> Set("Amey"))
//
//    val a: Set[Any] = dictionary("a")
//    dictionary += ("a" -> (dictionary("a") ++ (Set("Kasbe"))))
//    println(dictionary)



//      var sett : Set[Any] = Set()
//      sett += 1
//      sett += 2
//      sett += "Amey"
//      sett += "Amey"
//      println(sett)
//      val set2: Set[Any] = sett ++ Set(100)
//      println(set2)


  //       Map in Scala
//        var dictionary : Map[String, Int] = Map()
//        dictionary += ("amey" -> 2) // Add values
//        dictionary += ("amey" -> 3) // Update
//        dictionary += ("amey" -> (dictionary("amey") - 1)) // Update using values
//        dictionary += ("amey" -> 4) // Modify dictionary
//        println(dictionary("amey"))// Get values


// evaluate is not a member of Any -> Change Any to Exp in definition and declaration


  //        case printMap() => bindingScope
  //        case Union(setName1, setName2) => {
  //          val set1 = setName1.evaluate.asInstanceOf[Set[Any]]
  //          val set2 = setName2.evaluate.asInstanceOf[Set[Any]]
  //          bindingScope(setName1).asInstanceOf[Set[Any]].union(bindingScope(setName2).asInstanceOf[Set[Any]])
  //        }
  //        case Intersect(setName1, setName2) => {
  //          bindingScope(setName1).asInstanceOf[Set[Any]].intersect(bindingScope(setName2).asInstanceOf[Set[Any]])
  //        }
  //        case Check(setName, element) => {
  //
  //        }



