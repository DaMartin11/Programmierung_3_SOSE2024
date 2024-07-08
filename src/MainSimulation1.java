import Simulation1.CreateThread;
import Simulation1.DeleteThread;
import domainLogic.AudioManagement;

public class MainSimulation1 {
    public static void main(String[] args) {

        AudioManagement audioManagement = new AudioManagement();


        CreateThread createThread = new CreateThread(audioManagement);
        DeleteThread deleteThread = new DeleteThread(audioManagement);

        createThread.start();
        deleteThread.start();

        // Add shutdown hook to stop threads gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown initiated...");
            createThread.shutdown();
            deleteThread.interrupt(); // Interrupt deleteThread to stop it

            try {
                createThread.join();
                deleteThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Main thread interrupted during shutdown.");
            }
            System.out.println("Shutdown complete.");
        }));

        // Simulate main thread work
        try {
            Thread.sleep(60000); // Main thread sleeps for 60 seconds (or however long you want)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Main thread finished.");
    }

    private static int getCapacityFromArgs(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.parseInt(args[0]); // Parse capacity from command line arguments
            } catch (NumberFormatException e) {
                System.err.println("Invalid capacity argument. Using default capacity of 10.");
            }
        }
        return 10; // Default capacity if not provided or invalid
    }
}