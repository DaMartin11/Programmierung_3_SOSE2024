package cli.service;


import contract.Audio;
import contract.Tag;
import contract.Uploader;
import domainLogic.AudioImpl;
import domainLogic.AudioManagement;
import domainLogic.UploaderImpl;
import jos.SaveAndLoadJOS;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import static cli.service.CliViewerHelper.*;


public class CliViewerService implements ICliViewerService {
    AudioManagement audioManagement = new AudioManagement();
    List<Audio> audios = audioManagement.getAllAudios();


    private final Scanner scan = new Scanner(System.in);

    private SaveAndLoadJOS saveAndLoadJOS;

    public CliViewerService(AudioManagement audioManagement, SaveAndLoadJOS saveAndLoadJOS) {
        this.audioManagement = audioManagement;
        this.saveAndLoadJOS = saveAndLoadJOS;
    }


    public CliViewerService() {
        Collection<Tag> tags =    new ArrayList<Tag>();
        tags.add(Tag.Music);
        tags.add(Tag.Animal);
        Uploader uploader = new UploaderImpl("Test");
        // Add initial data to the list
        audioManagement.createAudio(44100, "example1.mp3", tags,
                0, 1024,uploader , Duration.ofMinutes(30),
                BigDecimal.TEN);
        audioManagement.createAudio(48000, "example2.mp3", tags, 0, 2048, uploader, Duration.ofHours(1), BigDecimal.valueOf(20));
        // initialize saveAndLoadJOS
        this.saveAndLoadJOS = new SaveAndLoadJOS();
    }

    @Override
    public int viewOptions() {

        String sb = "\n===== Media Manager =====\n\n" +
                "\t1) View all Media\n" +
                "\t2) Add a Media\n" +
                "\t3) Edit a Media\n" +
                "\t4) Remove a Media\n" +
                "\t5) persistence mode\n" +
                "\t6) Exit\n\n" +
                "Choose [1-6]: ";

        System.out.print(sb);
        Scanner sc = new Scanner(System.in);


        String selection = sc.next();
        while (!selection.matches("[1-6]")) {
            System.out.print("Please choose correct choice [1-6]: ");
            selection = sc.next();
        }

        int choice = Integer.parseInt(selection);
        return choice;
    }

    @Override
    public void viewAll() {
        audios = audioManagement.getAllAudios();
        if (audios.isEmpty()) {
            System.out.println("-----------------------");
            System.out.println("there is no audios");
            System.out.println("-----------------------");
            return;
        }

        audios.forEach((audio) -> {
            Uploader uploader = audio.getUploader();
            System.out.println("------------------------------------------");
            System.out.println("Audio Address: " + audio.getAddress());
            System.out.println("Audio Uploader: " + uploader.getName());
            System.out.println("Audio Access Count: " + audio.getAccessCount());
            System.out.println("------------------------------------------");
        });
    }

    @Override
    public void addMedia() {
        System.out.println("\n===== Add a Media =====\n");
        int samplingRate = getSamplingRateFromUser();
        String address = getAddressFromUser();
        Collection<Tag> tags = getTagsFromUser();
        long size = getSizeFromUser();
        Uploader uploader = getUploaderFromUser();
        Duration duration = getDurationFromUser();
        BigDecimal cost = getCostFromUser();

        audioManagement.addAudio(samplingRate, address, tags, 0, size, uploader, duration, cost);
    }



    @Override
    public void editMedia () {
        Scanner sc = new Scanner(System.in);

        StringBuilder sb = new StringBuilder();
        sb.append("\n===== Edit a audio =====\n\n");
        for ( Audio audio : audios ){
            sb.append("\t[" + audio.getAddress() + "] " + audio.getUploader().getName() + "\n");
        }
        sb.append("\nEnter the audio address of the audio you want to edit; " +
                "to " +
                "return press <Enter>.\n\n");
        System.out.print(sb);
        sc.nextLine();
        System.out.print("audio address: ");
        String address = sc.nextLine();
        while (address.isEmpty()) {
            System.out.print("\tPlease enter correct audio address; to return " +
                    "press <Enter>.\n\n");
            System.out.print("audio address: ");
            address = sc.nextLine();
        }
        System.out.println(address);
        if (!address.isEmpty()) {

            AudioImpl oldAudio = audioManagement.findAudioByAddress(address);
            if (oldAudio != null) {
                System.out.print("\nInput the following information. To leave a field unchanged, hit <Enter>.\n\n");

                System.out.print("\tAddress: [" + oldAudio.getAddress() + "]: ");
                address = sc.nextLine();
                System.out.print("\tUploader: [" + oldAudio.getUploader().getName() + "]: ");
                String uploader = sc.nextLine();

                if (address.isEmpty() && uploader.isEmpty()) {
                    System.out.println("\nThere are not any changes for audi " + "with Address [" + address + "]");
                } else {
                    long oldAccessCount = oldAudio.getAccessCount();
                    long newAccessCount =  oldAccessCount + 1;
                    audioManagement.updateAccessCount(oldAudio.getAddress(), newAccessCount);
                    System.out.println("access count has been updated for " +
                            "media; old access count: " +  oldAccessCount + " new access count: " + newAccessCount);
                }
            } else {
                System.out.println("\tThere is no Audio with Address [" + address + "]");
            }
        }
    }

    @Override
    public void removeMedia() {
        System.out.println("\n===== Remove a Media =====\n");
        String address = getAddressToRemoveFromUser();

        AudioImpl audioToRemove = audioManagement.findAudioByAddress(address);
        if (audioToRemove != null) {
            audioManagement.deleteAudio(audioToRemove);
            System.out.println("Media file removed successfully.");
        } else {
            System.out.println("Media file with address '" + address + "' not found.");
        }
    }

    private String getAddressToRemoveFromUser() {
        //Scanner sc = new Scanner(System.in);

        System.out.print("Enter the address of the media file to remove: ");
        String address = scan.next();
        return  address;
    }
    @Override
    public void persistencemode(){
        System.out.println("Persistence mode: Enter 'saveJOS' to save or 'loadJOS' to load.");
        String input = scan.next();
        if (input.equals("saveJOS")) {
            saveAndLoadJOS.saveAudioManagementJOS(audioManagement);
        } else if (input.equals("loadJOS")) {
             audioManagement = saveAndLoadJOS.loadAudioManagementJOS();
            if (audioManagement != null) {
                List<Audio> loadedAudios = audioManagement.getAllAudios();
                for (Audio audio : loadedAudios) {
                    System.out.println(audio);
                }
            } else {
                System.out.println("Failed to load audio management data.");
            }
        } else {
            System.out.println("Invalid option. Please enter 'saveJOS' to save or 'loadJOS' to load.");
        }
    }
}

