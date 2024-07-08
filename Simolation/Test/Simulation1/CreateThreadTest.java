package Simulation1;

import contract.Audio;
import domainLogic.AudioManagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateThreadTest {
    private AudioManagement audioManagement;
    private CreateThread createThread;

    @BeforeEach
    public void setUp() {
        audioManagement = new AudioManagement();
        createThread = new CreateThread(audioManagement);
    }

    @Test
    public void testCreateThreadAddsAudio() throws InterruptedException {
        // Start the CreateThread
        createThread.start();

        // Allow some time for the thread to add some audios
        Thread.sleep(500);

        // Shutdown the CreateThread
        createThread.shutdown();
        createThread.join();

        // Check that audios were added
        List<Audio> audios = audioManagement.getAllAudios();
        assertFalse(audios.isEmpty(), "Audio list should not be empty.");

        // Check the properties of the added audio (basic verification)
        for (Audio audio : audios) {
            assertNotNull(audio.getAddress(), "Audio address should not be null.");
            assertNotNull(audio.getUploader(), "Audio uploader should not be null.");
            assertTrue(audio.getSamplingRate() > 0, "Audio sampling rate should be greater than 0.");
            assertTrue(audio.getAccessCount() >= 0, "Audio access count should be non-negative.");
            assertTrue(audio.getSize() >= 0, "Audio size should be non-negative.");
            assertNotNull(audio.getAvailability(), "Audio availability should not be null.");
            assertNotNull(audio.getCost(), "Audio cost should not be null.");
        }
    }

    @Test
    public void testShutdown() throws InterruptedException {
        // Start the CreateThread
        createThread.start();

        // Allow some time for the thread to run
        Thread.sleep(200);

        // Shutdown the CreateThread
        createThread.shutdown();
        createThread.join();

        // Capture the current size
        int sizeAfterShutdown = audioManagement.getAllAudios().size();

        // Allow some additional time to ensure thread is fully stopped
        Thread.sleep(200);

        // Verify that no new audio was added after shutdown
        assertEquals(sizeAfterShutdown, audioManagement.getAllAudios().size(), "No new audio should be added after shutdown.");
    }

}