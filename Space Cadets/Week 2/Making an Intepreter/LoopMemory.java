import java.util.*;
public class LoopMemory{
  public List<String[]> words = new ArrayList<String[]>();

  public void store(List<String[]> toStore){
      for (int i=0; i<toStore.size();i++ ){
        words.add(toStore.get(i));
      }
    }
    public String returnVariable(){
      return words.get(0)[1];
    }
  }
