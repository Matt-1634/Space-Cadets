import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;

public class BareBones{ // The Class to read the barebones code

  Variables[] variables = new Variables[10];  // define the variable array
  List<String[]> codeInLoop = new ArrayList<String[]>();
  LoopMemory[] allLoops = new LoopMemory[10];
  Integer nestedLoopCounter = 0;

  public static void main(String[] args) throws Exception { // Our main method
    BareBones myBareBones = new BareBones();
    if (args.length==1){
      myBareBones.readFile(args[0]);
    }
  }

  public void readFile(String fileLocation) throws Exception {  // our file reader method
    String fileContents = new String(Files.readAllBytes(Paths.get(fileLocation))); // This

    String lines[] = fileContents.split("\\r?\\n"); // This splits the lines up
    for (int i=0;i<lines.length;i++){ // run the interpret method for each line in the file
      String words[] = lines[i].split(" ",0);// this keeps 4 spaces as the tabs
      interpret(words);
    }
    for (int x =0; x<variables.length;x++){
      if (variables[x]!=null){
        System.out.println("Variable "+variables[x].name+ " has value "+variables[x].currentValue);
      }
    }
  }

  public void interpret(String[] words) throws Exception{
    if (words[0].equals("") && nestedLoopCounter>0){
      words[0]=words[nestedLoopCounter*3]; // used to be words[3] and [4]
      if (words[0].equals("end;")==false){
        words[1]=words[nestedLoopCounter*3+1];
      }
    }

    if (words[0].equals("clear")) // if the command is "clear" run the clear method
      clear(words[1]);

    if (words[0].equals("while")||codeInLoop.size()>0||words[0].equals("end;")){ // if the command is a while loop run the corresponding method
      if (words[0].equals("end;")){
        codeInLoop.add(words);
        Integer x = 0;
        while (x<allLoops.length){
          if(allLoops[x]==null){
            break;
          }
          else x+=1;
        }
        allLoops[x] = new LoopMemory();
        allLoops[x].store(codeInLoop);

        codeInLoop.clear();
        whileLoops();

      }
      else{
        codeInLoop.add(words);
      }
    }
    if (words[0].equals("incr")||words[0].equals("decr")){  // if you are changing the value
      String variableName = words[1];
      variableName= variableName.replace(";","");// add a while runnning counter so it doesnt end a while loop when nested

      Boolean exists = false; // make sure variable defined
      Integer i = 0;  // our counter

      while (!exists && i<variables.length){    // while we havent found the variable and the list hasnt been exhausted
        if (variables[i] != null){  // if there is something in the list space
          if (variables[i].name.equals(variableName)){  // if the name of the object is the same as the variable defined
            variables[i].change(words[0]);  // then change the value according to the instruction

            exists = true;  // set that it exists so the while loop ends
          }
          else i +=1;  // add one to the counter to prevent infinate loop
        }
        else i +=1;
      }
    }
  }


  public void clear(String variable) throws Exception{  // this is the clear method
    variable = variable.replace(";","");
    Boolean exists = false;
    Integer i = 0;
    Integer nullCounter = 0;

    while (!exists && i<variables.length){

      if (variables[i] != null){

        if (variables[i].name.equals(variable)){

          variables[i].currentValue = 0;
          exists = true;
        }
        else i+=1;
      }

      else{
        i+=1;
        nullCounter+=1;
      }
    }

    if (!exists){
      variables[10-nullCounter]= new Variables();
      variables[10-nullCounter].name = variable;
    }

    if (exists){
      variables[i].currentValue = 0;
    }
  }


  public void whileLoops()throws Exception{
    Integer lastLoop = 0;
    nestedLoopCounter+=1;
    while (lastLoop<allLoops.length){
      if (allLoops[lastLoop]==null){
        break;
      }
      else lastLoop+=1;
    }
    lastLoop-=1;
    String variableName = allLoops[lastLoop].returnVariable();// make this find the variable name for the newest loop
    Boolean exists = false; // all this code between the two comments is basically error checking, this should be made its own method it does fetch us the value though
    Integer i = 0;

    while (!exists && i<variables.length){

      if (variables[i] != null){

        if (variables[i].name.equals(variableName)){

          exists = true;
        }
        else i+=1;
      }

      else i+=1;
    } // error checking needs to go next line in case variable isnt defined

    while(variables[i].currentValue!=Integer.parseInt(allLoops[lastLoop].words.get(0)[nestedLoopCounter*3])){// error is its not in location 3
      for (int x = 1; x<allLoops[lastLoop].words.size()-1;x++){ // for each line in the loop excluding end(the -1) and the while definintion(starts at 1)

        interpret(allLoops[lastLoop].words.get(x));
      }
    }
    nestedLoopCounter-=1;
  }
}
