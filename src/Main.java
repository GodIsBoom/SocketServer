
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Main extends Thread{

    private ServerSocket serverSocket;
    public Main(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(1000);
    }
    public void run(){

        int count = 0;
        while (true){
            try {

                Socket server = serverSocket.accept();
                InputStream inputStream = server.getInputStream();
                DataInputStream in = new DataInputStream(inputStream);
                System.out.println("my guest:"+in.readUTF());

                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("thx:"+server.getRemoteSocketAddress()+"\nGoodbye");
            }catch (SocketTimeoutException e){
                System.out.println("Time out!");
                count++;
                if (count>10){
                    break;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {

        int port = 4242;
        try {
            Thread t = new Main(port);
            t.run();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
