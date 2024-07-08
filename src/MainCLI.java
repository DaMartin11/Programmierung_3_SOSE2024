
import cli.controller.MediaController;
import cli.service.CliViewerService;

public class MainCLI {
    public static void main(String[] args) {
        MediaController mediaController = new MediaController();

        CliViewerService cliViewerService = new CliViewerService();
       // cliViewerService.addMedia();
        mediaController.run();

    }
}