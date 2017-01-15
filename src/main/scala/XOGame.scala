import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConverters._

class XOGame {
  val e = -1
  val X = 1
  val O = 0
  val justStep = 0
  val secondInRow = 1
  val mayBlockUserSecond = 2
  val mayLose = 3
  val mayWin = 4
  var winner = -1
  val field = ArrayBuffer(
  e, e, e,
  e, e, e,
  e, e, e)
  val drawer = new ImageDrawer(convertToList)

  def makeStep(stepPositionAsString:String):Unit={
    if(winner >= 0) return
    val stepPosition = parseArgsToInt(stepPositionAsString)
    if( getRandomPos == 0 ){
      return}
    if( field(stepPosition - 1) != e){
      return}
    field(stepPosition - 1) = X
    if(getRandomPos == 0){
      winner = 2
      return
    }
    makeBotStep(stepPosition)
    drawer.setField(convertToList)
  }

  def convertToList:java.util.List[java.lang.Integer] = {
    field.asJava.asInstanceOf[java.util.List[java.lang.Integer]]
  }

  def getRandomPos:Int={
    for(posStep <- field) if(posStep == e) return field.indexOf(posStep) + 1
    0
  }

  def checkCombo(pos1:Int, pos2:Int, pos3:Int):Array[Int]={
    var maxPriority = justStep
    var position = getRandomPos
    val maybeWin = checkWin(pos1, pos2, pos3)
    if(maybeWin > 0 && maxPriority < mayWin ) {maxPriority = mayWin; position = maybeWin}
    val maybeLose = checkLose(pos1, pos2, pos3)
    if(maybeLose > 0 && maxPriority < mayLose ) {maxPriority = mayLose; position = maybeLose}
    val mayBeSecondChar = checkSecondInRow(pos1, pos2, pos3)
    if(mayBeSecondChar > 0 && maxPriority < secondInRow) {maxPriority = secondInRow; position = mayBeSecondChar}
    Array(maxPriority,position)
  }


  def checkWin(pos1:Int,pos2:Int,pos3:Int):Int={
    if(field(pos1 - 1) == O  && field(pos2 - 1) == O && field(pos3 - 1) == e) return pos3
    if(field(pos2 - 1) == O && field(pos3 - 1) == O && field(pos1 - 1) == e) return pos1
    if(field(pos3 - 1) == O && field(pos1 - 1) == O && field(pos2 - 1) == e) return pos2
    0
  }

  def checkLose(pos1:Int,pos2:Int,pos3:Int):Int={
    if(field(pos1 - 1) == X  && field(pos2 - 1) == X && field(pos3 - 1) == e) return pos3
    if(field(pos2 - 1) == X && field(pos3 - 1) == X && field(pos1 - 1) == e) return pos1
    if(field(pos3 - 1) == X && field(pos1 - 1) == X && field(pos2 - 1) == e) return pos2
    0
  }

  def checkBlockSecond(userPos:Int):Int={
    if(userPos == 1) {
      if(field(0) == e) return 1
      if(field(3) == e) return 4
      if(field(4) == e) return 5
    }
    if(userPos == 2) {
      if(field(0) == e) return 1
      if(field(2) == e) return 3
      if(field(4) == e) return 5
    }
    if(userPos == 3){
      if(field(1) == e) return 2
      if(field(4) == e) return 5
      if(field(5) == e) return 6
    }
    if(userPos == 4){
      if(field(0) == e) return 1
      if(field(4) == e) return 4
      if(field(6) == e) return 6
    }
    if(userPos == 5) {
      if(field(0) == e) return 1
      if(field(1) == e) return 2
      if(field(2) == e) return 3
      if(field(3) == e) return 4
      if(field(5) == e) return 6
      if(field(6) == e) return 7
      if(field(7) == e) return 8
      if(field(8) == e) return 9
    }
    if(userPos == 6) {
      if(field(2) == e) return 3
      if(field(4) == e) return 5
      if(field(8) == e) return 9
    }
    if(userPos == 7){
      if(field(3) == e) return 4
      if(field(4) == e) return 5
      if(field(7) == e) return 8
    }
    if(userPos == 8) {
      if(field(4) == e) return 5
      if(field(6) == e) return 7
      if(field(8) == e) return 9
    }
    if(userPos == 9) {
      if(field(4) == e) return 5
      if(field(5) == e) return 6
      if(field(7) == e) return 8
    }
    0
  }

  def checkSecondInRow(pos1:Int, pos2:Int, pos3:Int):Int={
    if(field(pos1 - 1) == O && field(pos2 - 1) == e) return pos2
    if(field(pos1 - 1) == O && field(pos3 - 1) == e) return pos3
    if(field(pos2 - 1) == O && field(pos1 - 1) == e) return pos1
    if(field(pos2 - 1) == O && field(pos3 - 1) == e) return pos3
    if(field(pos3 - 1) == O && field(pos1 - 1) == e) return pos1
    if(field(pos3 - 1) == O && field(pos2 - 1) == e) return pos2
    0
  }

  def checkEndOfGame={
    def checkRow(pos1:Int, pos2:Int, pos3:Int)={
      if(field(pos1 - 1) == O && field(pos2 - 1)== O && field(pos3 - 1) == O) winner = O
      if(field(pos1 - 1) == X && field(pos2 - 1)== X && field(pos3 - 1) == X) winner = X
    }
    checkRow(1,2,3)
    checkRow(4,5,6)
    checkRow(7,8,9)
    checkRow(1,4,7)
    checkRow(2,4,8)
    checkRow(3,6,9)
    checkRow(1,5,9)
    checkRow(3,5,7)
  }

  def makeBotStep(userStep:Int):Unit={
    checkEndOfGame
    if(winner >= 0) return
    var maxPriority = justStep
    var position = getRandomPos
    //1st combo
    val firstComboPrior = checkCombo(1,2,3)(0)
    val firstComboStep = checkCombo(1,2,3)(1)
    if( firstComboPrior > maxPriority) { maxPriority = firstComboPrior; position = firstComboStep }
    //2nd combo
    val secondComboPrior = checkCombo(4,5,6)(0)
    val secondComboStep = checkCombo(4,5,6)(1)
    if( secondComboPrior > maxPriority) { maxPriority = secondComboPrior; position = secondComboStep }
    //3rd combo
    val thirdComboPrior = checkCombo(7,8,9)(0)
    val thirdComboStep = checkCombo(7,8,9)(1)
    if( thirdComboPrior > maxPriority) { maxPriority = thirdComboPrior; position = thirdComboStep }
    //4th combo
    val fourthComboPrior = checkCombo(1,4,7)(0)
    val fourthComboStep = checkCombo(1,4,7)(1)
    if( fourthComboPrior > maxPriority) { maxPriority = fourthComboPrior; position = fourthComboStep }
    //5th combo
    val fifthComboPrior = checkCombo(2,5,8)(0)
    val fifthComboStep = checkCombo(2,5,8)(1)
    if( fifthComboPrior > maxPriority) { maxPriority = fifthComboPrior; position = fifthComboStep }
    //6th combo
    val sixthComboPrior = checkCombo(3,6,9)(0)
    val sixthComboStep = checkCombo(3,6,9)(1)
    if( sixthComboPrior > maxPriority) { maxPriority = sixthComboPrior; position = sixthComboStep }
    //7th combo
    val seventhComboPrior = checkCombo(1,5,9)(0)
    val seventhComboStep = checkCombo(1,5,9)(1)
    if( seventhComboPrior > maxPriority) { maxPriority = seventhComboPrior; position = seventhComboStep }
    //8th combo
    val eighthComboPrior = checkCombo(3,5,7)(0)
    val eighthComboStep = checkCombo(3,5,7)(1)
    if( eighthComboPrior > maxPriority) { maxPriority = eighthComboPrior; position = eighthComboStep }
    if( maxPriority < mayBlockUserSecond) {
      position = checkBlockSecond(userStep)
    }
    field(position - 1) = O
    checkEndOfGame
  }

  def parseArgsToInt(args:String)={
    var str = args.trim
    str = str.substring(0,1)
    str.toInt
  }

  def getPicture = {
    drawer.getBytes
  }

}
