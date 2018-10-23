import java.net.*;
import java.io.*;
import java.util.*; // 225.225.225.225 is a valid code for all ips on an LAN find more online ones
//224.0.0.1 to 239.255.255.255 anything between these numbers should be allowed

public class MultiCast{

private static final String EXIT_COMMAND = "Exit";
static Boolean finished = false;
static String name;

// at the start of the main we take 2 arguments. one for the host ip one for the port number
public static void main(String[] args){
  if (args.length != 2)
    System.out.println("Two arguments required:<multicast-host> <port-number>");

  // if the arguments are correct we then run the program
  else{
    try{
      // InetAddress is a class that finds ip addresses online
      InetAddress group = InetAddress.getByName(args[0]);
      int port = Integer.parseInt(args[1]);

      System.out.print("Enter your name: ");
      Scanner sc = new Scanner(System.in);
      name = sc.nextLine();
      MulticastSocket socket = new MulticastSocket(port);
      socket.setTimeToLive(0);  // set to 0 for localhost, 1 for subnet

      // This next line joins the online ip group
      socket.joinGroup(group);

      // This creates the thread that will run in the ReadThread class
      Thread readMessagesThread = new Thread(new ReadThread(socket,group,port));
      readMessagesThread.start();

      System.out.println("Connected to server!");

      while(true){
        String message = sc.nextLine();

        if(message.equalsIgnoreCase(MultiCast.EXIT_COMMAND)){
          finished = true;
          socket.leaveGroup(group);
          socket.close();
          break;
        }

        message = name + ": " + message;
        byte[] buffer = message.getBytes();
        // A datagram is just a message sent over a network
        DatagramPacket datagram = new DatagramPacket(buffer,buffer.length,group,port);
        socket.send(datagram);
      }
    }
      catch(SocketException se){
      System.out.println("Error creating socket");
      se.printStackTrace();
      }
      catch(IOException ie){
      System.out.println("Error reading/writing from/to socket");
      ie.printStackTrace();
      }
    }
  }
}


class ReadThread implements Runnable{
  private MulticastSocket socket;
    private InetAddress group;
    private int port;
    //private static final int MAX_LEN = 1000;
    ReadThread(MulticastSocket socket,InetAddress group,int port)
    {
        this.socket = socket;
        this.group = group;
        this.port = port;
    }

    public void run(){
      while(!MultiCast.finished){
        byte[] buffer = new byte[1000];
        DatagramPacket datapacket = new DatagramPacket(buffer,buffer.length,group,port);
        String message;

        try{
          socket.receive(datapacket);
          message = new String(buffer,0,datapacket.getLength(),"UTF-8");
          System.out.println(message);
        }
        catch(IOException e){
          System.out.println("Socket closed!");
        }
      }
    }
}
