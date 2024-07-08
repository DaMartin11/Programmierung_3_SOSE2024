import cli.controller.MediaController;
import cli.service.CliViewerService;
import cli.service.ICliViewerService;
import domainLogic.AudioManagement;
import jos.SaveAndLoadJOS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ScatteringByteChannel;
import java.sql.SQLOutput;
import java.util.Scanner;

import static java.lang.System.out;


public class Server {

    private final Scanner scanner = new Scanner(System.in);
    AudioManagement audioManagement = new AudioManagement();
    SaveAndLoadJOS saveAndLoadJOS = new SaveAndLoadJOS();
    ICliViewerService cliViewerService =
            new CliViewerService(audioManagement, saveAndLoadJOS);
    //ICliViewerService cliViewerService = new CliViewerService();
    MediaController mediaController = new MediaController();

    public void start(int port) throws IOException {
        out.println("Server listening on port " + port);
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();
        PrintWriter stdOut = new PrintWriter(clientSocket.getOutputStream(),
                true);
        BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String message;


        while ((message = stdIn.readLine()) != null) {
            switch (message) {
                case "c":
                    out.println("add a Media");
                    //String addInput = stdIn.readLine();
                    try {
                        cliViewerService.addMedia();
                        out.println("Media added");
                    } catch (Exception e) {
                        out.println("Error while inserting" + e.getMessage());
                    }
                    break;

                case "d":
                    out.println("delete a Media");
                    try {
                        cliViewerService.removeMedia();
                        out.println("Media deleted");
                    } catch (Exception e) {
                        out.println("Error while deleting");
                    }
                    break;


                case "r":
                    cliViewerService.viewAll();
                    out.println("Media shown");

                    break;

                case "u":
                    cliViewerService.editMedia();
                    out.println("Media updated");

                    break;

                case "p":
                    cliViewerService.persistencemode();
                    out.println("persistence mode ran ");

                    break;

            }
        }

    }
}