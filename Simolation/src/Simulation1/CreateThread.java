package Simulation1;

import domainLogic.AudioManagement;
import domainLogic.UploaderImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.Random;

public class CreateThread extends Thread {
    private final AudioManagement audioManagement;
    private final Random random = new Random();
    private volatile boolean running = true;

    public CreateThread(AudioManagement audioManagement) {
        this.audioManagement = audioManagement;
    }

    @Override
    public void run() {
        while (running && !isInterrupted()) {
            try {
                int samplingRate = random.nextInt(44100);
                String address = "address" + random.nextInt(1000);
                int accessCount = random.nextInt(1000);
                int size = random.nextInt(1000);
                UploaderImpl uploader = new UploaderImpl("uploader" + random.nextInt(1000));
                Duration availability = Duration.ofHours(random.nextInt(24));
                BigDecimal cost = BigDecimal.valueOf(random.nextDouble());

                audioManagement.addAudio(samplingRate, address, Collections.emptyList(), accessCount, size, uploader, availability, cost);
                System.out.println("Created audio: " + address);

                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }catch (Exception e){
                System.err.println("Error adding audio: " + e.getMessage());
            }
        }
    }
    public void shutdown(){
        running = false;
        this.interrupt();
    }
}
