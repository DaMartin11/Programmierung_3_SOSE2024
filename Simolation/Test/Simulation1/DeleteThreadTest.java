package Simulation1;

import contract.Audio;
import domainLogic.AudioImpl;
import domainLogic.AudioManagement;
import domainLogic.UploaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeleteThreadTest {

        private AudioManagement audioManagement;
        //private Audio audio;
        private DeleteThread deleteThread;

        @BeforeEach
        public void setUp() {
            audioManagement = new AudioManagement();
            deleteThread = new DeleteThread(audioManagement);

            // Adding some audio objects to the management for testing purposes
            for (int i = 0; i < 5; i++) {
                audioManagement.addAudio(
                        44100,
                        "address" + i,
                        Collections.emptyList(),
                        0,
                        0,
                        new UploaderImpl("uploader" + i),
                        Duration.ofHours(24),
                        BigDecimal.valueOf(0.99)
                );
            }
        }

        @Test
        public void testDeleteThreadRemovesAudio() throws InterruptedException {
            // Start the DeleteThread
            deleteThread.start();

            // Allow some time for the thread to delete some audios
            Thread.sleep(500);

            // Shutdown the DeleteThread
            deleteThread.shutdown();
            deleteThread.join();

            // Check that audios were deleted
            List<Audio> audios = audioManagement.getAllAudios();
            assertTrue(audios.size() < 5, "Audio list should have fewer elements than initially added.");

            // Ensure the deleted audios are no longer in the list
            for (int i = 0; i < 5; i++) {
                AudioImpl audio = new AudioImpl(
                        44100,
                        "address" + i,
                        Collections.emptyList(),
                        0,
                        0,
                        new UploaderImpl("uploader" + i),
                        Duration.ofHours(24),
                        BigDecimal.valueOf(0.99)
                );
                assertFalse(audios.contains(audio), "Deleted audio should not be in the list.");
            }
        }

        @Test
        public void testShutdown() throws InterruptedException {
            // Start the DeleteThread
            deleteThread.start();

            // Allow some time for the thread to run
            Thread.sleep(200);

            // Shutdown the DeleteThread
            deleteThread.shutdown();
            deleteThread.join();

            // Capture the current size
            int sizeAfterShutdown = audioManagement.getAllAudios().size();

            // Allow some additional time to ensure thread is fully stopped
            Thread.sleep(200);

            // Verify that no new audio was deleted after shutdown
            assertEquals(sizeAfterShutdown, audioManagement.getAllAudios().size(), "No new audio should be deleted after shutdown.");
        }

    }
