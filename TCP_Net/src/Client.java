import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Client {
    public Client(){

    }
    public void start(String hostname, int port) throws IOException {
        Socket clientSocket = new Socket(hostname, port);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        Scanner scanner = new Scanner(System.in);
        String userInput;
        //BufferedReader stdIn =
                //new BufferedReader(new InputStreamReader(System.in));

        System.out.println(" :c Switch to insert mode\n :d Switch to delete " +
                "mode \n :p Change to persistence mode\n exit to quit");
        System.out.println("enter Command:");

        while (true) {
            System.out.println("Response from server: " + in.readLine());
            System.out.println("enter Command:");
            userInput = scanner.nextLine();
            if ("exit".equalsIgnoreCase(userInput)){
                break;
            }
            out.println(userInput);
            String serverResponse;
            while ((serverResponse = in.readLine()) != null){
            System.out.println("Server response: " + serverResponse);
            break;
            }
        }
    }
}
