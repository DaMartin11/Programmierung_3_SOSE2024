package Simulation1;

import contract.Audio;
import domainLogic.AudioImpl;
import domainLogic.AudioManagement;

import java.util.List;
import java.util.Random;
public class DeleteThread extends Thread {
    private final AudioManagement audioManagement;
    private final Random random = new Random();
    private volatile boolean running = true;

    public DeleteThread(AudioManagement audioManagement){
        this.audioManagement = audioManagement;
    }
    @Override
    public void run() {
        while (running && !isInterrupted()) {
            synchronized (audioManagement) {
                List<Audio> audios = audioManagement.getAllAudios();
                if (!audios.isEmpty()) {
                    int index = random.nextInt(audios.size());
                    Audio audio = audios.get(index);
                    if (audio instanceof AudioImpl) {
                        audioManagement.deleteAudio((AudioImpl) audio);
                        System.out.println("Removed audio: " + audio.getAddress());
                    }
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Exit loop if interrupted
            }
        }
    }

    public void shutdown() {
        running = false;
        this.interrupt();
    }
}
