public class Variables{
  Integer currentValue = 0;
  String name = "";
  public void change(String amount){
    if (amount.equals("incr")){
      currentValue+=1;
    }
    else currentValue-=1;
  }
}
