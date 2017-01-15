
object MessageHolder {
  def checkValid(args:String):Boolean={
    if(args.isEmpty) return false
    val str = args.trim
    if(!str.charAt(0).isDigit) return false
    if(str.length > 1) return false
    if( !((1 to 9) contains str.substring(0,1).toInt) ) return false
    return true
  }
}
