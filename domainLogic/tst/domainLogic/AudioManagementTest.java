package domainLogic;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import contract.Audio;
import contract.Tag;
import domainLogic.AudioImpl;
import domainLogic.AudioManagement;
import domainLogic.UploaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class AudioManagementTest {
    private AudioManagement audioManagement;
   @BeforeEach
   public void setUP(){
       audioManagement = new AudioManagement();
   }
    @Test
    void testCreatAudio() {
       audioManagement.createAudio(55100,"audio.mp3", Collections.singleton(Tag.Music),0 , 1024, new UploaderImpl("Martin"), Duration.ofHours(24), BigDecimal.valueOf(10.99));
        List<Audio> allAudios = audioManagement.getAllAudios();
        assertEquals(1, allAudios.size());
        assertEquals("audio.mp3", allAudios.get(0).getAddress());
   }

    @Test
    void testAddAudio() {
        audioManagement.addAudio(55100,"audio.mp3", Collections.singleton(Tag.Music),0 , 1024, new UploaderImpl("John Doe"), Duration.ofHours(24), BigDecimal.valueOf(10.99));
        List<Audio> allAudios = audioManagement.getAllAudios();
        assertEquals(1, allAudios.size());
        assertEquals("audio.mp3", allAudios.get(0).getAddress());
   }
   @Test
   void testAddAudioWithNullParameters(){
       assertThrows(IllegalArgumentException.class,() ->{
           audioManagement.addAudio(44100, null, Collections.singleton(Tag.Music), 0, 1024, new UploaderImpl("John Doe"), Duration.ofHours(24), BigDecimal.valueOf(10.99));
       });
   }

    @Test
    void testDeleteAudio() {
        audioManagement.createAudio(44100, "audio1.mp3", Collections.singleton(Tag.Music), 0, 1024, new UploaderImpl("John Doe"), Duration.ofHours(24), BigDecimal.valueOf(10.99));
        audioManagement.createAudio(44100, "audio2.mp3", Collections.singleton(Tag.News), 0, 1024, new UploaderImpl("John Doe"), Duration.ofHours(24), BigDecimal.valueOf(9.99));
        // Delete an audio object
        AudioImpl audioToRemove = audioManagement.findAudioByAddress("audio1.mp3");
        audioManagement.deleteAudio(audioToRemove);

        // Verify that the audio object is removed
        List<Audio> allAudios = audioManagement.getAllAudios();
        assertEquals(1, allAudios.size());
        assertNull(audioManagement.findAudioByAddress("audio1.mp3"));
    }

    @Test
    void testGetAllAudios() {
        // Create audio objects
        AudioImpl audio1 = new AudioImpl(44100, "audio1.mp3", Collections.singleton(Tag.Music), 0, 1024, new UploaderImpl("John Doe"), Duration.ofHours(24), BigDecimal.valueOf(10.99));
        AudioImpl audio2 = new AudioImpl(44100, "audio2.mp3", Collections.singleton(Tag.News), 0, 1024, new UploaderImpl("Jane Doe"), Duration.ofHours(24), BigDecimal.valueOf(9.99));

        // Add audio objects to AudioManagement
        audioManagement.addAudio(audio1.getSamplingRate(), audio1.getAddress(), audio1.getTags(), audio1.getAccessCount(), audio1.getSize(), audio1.getUploader(), audio1.getAvailability(), audio1.getCost());
        audioManagement.addAudio(audio2.getSamplingRate(), audio2.getAddress(), audio2.getTags(), audio2.getAccessCount(), audio2.getSize(), audio2.getUploader(), audio2.getAvailability(), audio2.getCost());

        // Retrieve all audio objects
        List<Audio> allAudios = audioManagement.getAllAudios();

        // Verify that the correct list of audio objects is returned
        assertEquals(2, allAudios.size());
        assertTrue(allAudios.stream().anyMatch(audio -> audio.getAddress().equals("audio1.mp3")));
        assertTrue(allAudios.stream().anyMatch(audio -> audio.getAddress().equals("audio2.mp3")));
    }

    @Test
    void testFindAudioByAddress() {
       audioManagement.createAudio(44100, "audio1.mp3", Collections.singleton(Tag.Music), 0, 1024, new UploaderImpl("John Doe"), Duration.ofHours(24), BigDecimal.valueOf(10.99));
       audioManagement.createAudio(44100, "audio2.mp3", Collections.singleton(Tag.News), 0, 1024, new UploaderImpl("Jane Doe"), Duration.ofHours(24), BigDecimal.valueOf(9.99));
       AudioImpl audio = audioManagement.findAudioByAddress("audio1.mp3");
       assertNotNull(audio);
       assertEquals("audio1.mp3", audio.getAddress());
    }
    @Test
    void testFindNonExistentAudio() {
        // Ensure that the audio list is empty
        assertTrue(audioManagement.getAllAudios().isEmpty());

        // Try to find an audio with a non-existent address
        AudioImpl nonExistentAudio = audioManagement.findAudioByAddress("non_existent_audio.mp3");

        // Verify that the result is null
        assertNull(nonExistentAudio);
    }

    @Test
    void testUpdateAccessCount(){
       audioManagement.createAudio(44100,"audio.mp3",Collections.singleton(Tag.Music),0,1024,new UploaderImpl("John Doe"),Duration.ofHours(24),BigDecimal.valueOf(19.99));
       audioManagement.updateAccessCount("audio.mp3",100);
       AudioImpl updateAudio = audioManagement.findAudioByAddress("audio.mp3");
       assertNotNull(updateAudio);
       assertEquals(100,updateAudio.getAccessCount());
    }


    @Test
    void testDmmleteAudio() {
       /* audioManagement.createAudio(44100, "audio1.mp3",
                Collections.singleton(Tag.Music), 0, 1024, new UploaderImpl
                ("John Doe"), Duration.ofHours(24), BigDecimal.valueOf(10.99)
                );*/

        audioManagement.createAudio(44100, "audio2.mp3", Collections.singleton(Tag.News), 0, 1024, new UploaderImpl("John Doe"), Duration.ofHours(24), BigDecimal.valueOf(9.99));
        // Delete an audio object



        // Verify that the audio object is removed
        List<Audio> allAudios = audioManagement.getAllAudios();
        allAudios.get(0);
        assertEquals(1, allAudios.size());
        assertNull(audioManagement.findAudioByAddress("audio1.mp3"));
    }


}