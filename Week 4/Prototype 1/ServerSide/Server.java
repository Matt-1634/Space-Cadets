import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public static void main(String args[]){
    Socket socket = null;
    // This is a mechanism for the server to listen to clients and establish a connection
    ServerSocket serverSocket = null;

    System.out.println("Server waiting for user...");

    // Here we try and assign the connection found to the port 5000, the IO exception catches if theres a port error
    try{
        serverSocket = new ServerSocket(5000);
    }
    catch(IOException e){
      e.printStackTrace();
      System.out.println("Server error");
    }

    // This will run as long as the server is up to keep accepting new clients
    while(true){

      // Accept the client as a socket, then run a new thread as the socket
      try{
        socket= serverSocket.accept();
        System.out.println("connection Established");
        ServerThread st=new ServerThread(socket);
        st.start();
      }

      // This exception error is important as it will catch connection errors if people close down windows suddenly
      catch(Exception e){
        e.printStackTrace();
        System.out.println("Connection Error");
      }
    }
  }
}
