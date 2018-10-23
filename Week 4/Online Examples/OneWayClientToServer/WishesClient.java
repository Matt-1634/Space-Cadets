import java.net.Socket;
import java.io.OutputStream;
import java.io.DataOutputStream;

public class WishesClient{
  
   public static void main(String args[]) throws Exception{

     // The socket class takes 2 inputs, the ip address to be used and the port number
     Socket sock = new Socket("127.0.0.1", 5000);
     String message1 = "Accept Best Wishes, Serverji";

     // Here the getOutputStream() method of Socket class returns an object of OutputStream, here the object is ostream.
     // This is the starting point of the whole communication (program). Here, the socket gets linked to streams.
     // Streams are instrumental to pass the data.
     OutputStream ostream = sock.getOutputStream();

     // As OutputStream is an abstract class; it cannot be used directly.
     // In the above code, it is chained to a concrete class DataOutputStream.writeBytes()
     //method of DataOutputStream takes the string message and passes to the Socket. Now the client socket sends to the other socket on the server.
     // When the job is over close the streams and socket. It releases the handles (links) connected to the system resources.
     DataOutputStream dos = new DataOutputStream(ostream);
     dos.writeBytes(message1);
     dos.close();
     ostream.close();
     sock.close();
  }
}
