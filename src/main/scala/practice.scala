object practice extends App{
  @main def runAion: Unit = {
    var dictionary: Map[String, Set[Any]] = Map()
    dictionary += ("a" -> Set("Amey"))

    val a: Set[Any] = dictionary("a")
    dictionary += ("a" -> (dictionary("a") ++ (Set("Kasbe"))))
    println(dictionary)
  }



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


}



