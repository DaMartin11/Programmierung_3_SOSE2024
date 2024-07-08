package cli.controller;


//import cli_old.service.CliViewerService;
//import cli_old.service.ICliViewerService;

import cli.service.CliViewerService;
import cli.service.ICliViewerService;

public class MediaController {
    ICliViewerService viewerService =
            new CliViewerService();
    public void run() {


        // viewerService.load();

        int choice = viewerService.viewOptions();
        while (choice != 6) {
            switch (choice) {
                case 1:
                    viewerService.viewAll();
                    break;
                case 2:

                    viewerService.addMedia();
                    break;
                case 3:

                    viewerService.editMedia();
                    break;
                case 4:

                    viewerService.removeMedia();
                    break;
                case 5:

                    viewerService.persistencemode();

                case 6:



                    break;
            }
            choice = viewerService.viewOptions();
        }
        // Exit without save
        System.exit(0);
    }
}
