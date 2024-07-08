package cli.service;
import contract.Audio;
import contract.Tag;
import domainLogic.AudioImpl;
import domainLogic.UploaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.math.BigDecimal;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

class CliViewerServiceTest {
    private ByteArrayInputStream testIn;
    private CliViewerService cliViewerService;
    private final InputStream originalSystemIn = System.in;


    @BeforeEach
    void setUp() {
        cliViewerService = new CliViewerService();
    }

    private void provideInput(String data){
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    public void testViewOptions_ValidOption_Success() {
        // Mock input for the viewOptions method
        provideInput("1");
        // systemIn.provideLines("1\n");

        // Run the method under test
        int choice = cliViewerService.viewOptions();

        // Verify the result
        assertEquals(1, choice);
    }

    @Test
    public void testAddMedia() {
        // Mock System.in to provide necessary inputs

        provideInput("44100");
        //when(getSamplingRateFromUser()).thenReturn(44100);
        System.setIn(originalSystemIn);
        provideInput("example3.mp3");
        //when(getAddressFromUser()).thenReturn("example3.mp3");
        System.setIn(originalSystemIn);
        provideInput("Animal");
        //when(getTagsFromUser()).thenReturn(new ArrayList<>());
        System.setIn(originalSystemIn);
        provideInput("2048");
        //when(getSizeFromUser()).thenReturn(2048L);
        System.setIn(originalSystemIn);
        provideInput("Test");
        //when(getUploaderFromUser()).thenReturn(new UploaderImpl("Test"));
        System.setIn(originalSystemIn);
        provideInput("60");
        //when(getDurationFromUser()).thenReturn(Duration.ofHours(1));
        System.setIn(originalSystemIn);
        provideInput("30.50");
        //when(getCostFromUser()).thenReturn(BigDecimal.valueOf(30.50));

        //            provideInput("example3.mp3");
        //          provideInput("Music");
        // provideInput("Music2048\nTest\n3600\n30.50\n");



        // List<AudioImpl> audioList = CliViewerService.viewAll();

        cliViewerService.addMedia();

        List<Audio> audios =
                cliViewerService.audioManagement.getAllAudios();
        assertEquals(3, audios.size());
        Audio newAudio = audios.get(2);
        assertEquals(44100, newAudio.getSamplingRate());
        assertEquals("example3.mp3", newAudio.getAddress());
        assertEquals(2048, newAudio.getSize());
        assertEquals(1, newAudio.getTags().size());
        assertEquals(Tag.Music, newAudio.getTags().iterator().next());
        assertEquals("Test", newAudio.getUploader().getName());
        assertEquals(BigDecimal.valueOf(30.50), newAudio.getCost());
    }

    @Test
    public void testEditMedia() {
        // Mock System.in to provide necessary inputs
        String input = "example1.mp3\nTest\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Mock audioManagement to return a known audio
        AudioImpl mockAudio = mock(AudioImpl.class);
        when(mockAudio.getAddress()).thenReturn("example1.mp3");
        when(cliViewerService.audioManagement.findAudioByAddress("example1.mp3")).thenReturn(mockAudio);

        cliViewerService.editMedia();

        // Verify that access count is updated
        Mockito.verify(cliViewerService.audioManagement).updateAccessCount("example1.mp3", 1L);
    }

    @Test
    public void testRemoveMedia() {
        // Mock System.in to provide input "example1.mp3"
        String input = "example1.mp3\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Mock audioManagement to return a known audio
        AudioImpl mockAudio = mock(AudioImpl.class);
        when(mockAudio.getAddress()).thenReturn("example1.mp3");
        when(cliViewerService.audioManagement.findAudioByAddress("example1.mp3")).thenReturn(mockAudio);

        cliViewerService.removeMedia();

        // Verify that deleteAudio method is called
        Mockito.verify(cliViewerService.audioManagement).deleteAudio(mockAudio);
    }

    @Test
    public void testGetUploaderFromUser() {
        // Mock System.in to provide input "TestUploader"
        String input = "TestUploader";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        UploaderImpl uploader = new UploaderImpl(input);

        assertEquals("TestUploader", uploader.getName());
    }
}
