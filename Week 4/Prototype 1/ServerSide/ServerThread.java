import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// this class is where you put all the features of the chat application. Helpful guide on https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server
// i used the second solution for guidence
class ServerThread extends Thread{
  public Socket socket;

  // this constructor will assign the socket to this specific class
  public ServerThread(Socket clientSocket){
    this.socket = clientSocket;
  }

  public void run(){
    System.out.println("User Connected to server!");

    // defines the input and output streams
    is= new BufferedReader(new InputStreamReader(socket.getInputStream()));
    os= new PrintWriter(socket.getOutputStream());

    currentLine = is.readLine();
    while(line.compareTo("QUIT")!=0){
      // Print the line then start a new one
      os.println(line);
      os.flush();
      // prints the response back out 
      System.out.println("Response to Client  :  "+line);
      line=is.readLine();
    }
  }
}
