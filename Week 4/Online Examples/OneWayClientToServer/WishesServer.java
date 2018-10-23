import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.DataInputStream;

public class WishesServer{
   public static void main(String args[]) throws Exception{

     // This defines the port that is going to be accessed
     ServerSocket sersock = new ServerSocket(5000);
     System.out.println("server is ready");  //  message to know the server is running

     // accept() is a method of ServerSocket class used, used by the server, to bind the connection on the port number 5000, requested by the client.
     Socket sock = sersock.accept();

     // getInputStream() method of Socket returns an object of InputStream and this is the starting point on the server program.
     // The server uses input stream as it is receiving the message.
     InputStream istream = sock.getInputStream();
     DataInputStream dstream = new DataInputStream(istream);

     // readLine() method of DataInputStream reads the message string from the socket and returns. This message is printed at the console.
     String message2 = dstream.readLine();
     System.out.println(message2);
     dstream .close(); istream.close(); sock.close(); sersock.close();
  }
}
