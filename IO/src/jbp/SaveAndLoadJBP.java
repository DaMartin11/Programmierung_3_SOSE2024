package jbp;
import domainLogic.*;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.List;

public class SaveAndLoadJBP {
    private static final String filename1 = "src/IO/jbp/AudioItem.xml";
    public static void write(AudioManagement audiomanagement, String filename){
        try(XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(
                new FileOutputStream(filename)
        ))){
            encoder.writeObject(audiomanagement.getAllAudios());
            System.err.println("\nSavingJBP Successful... Checkout your specified output file..\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // load
    public static List<AudioImpl> read(String filename){
        List<AudioImpl> loadedList = null;
        try (XMLDecoder decoder=new XMLDecoder(new BufferedInputStream(
                new FileInputStream(filename))) ){
            loadedList= (List<AudioImpl>) decoder.readObject();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return loadedList;
    }
    public static void saveAudioManagementJBP(AudioManagement audiomanagement){
        write(audiomanagement,filename1);
    }
    public static List<AudioImpl> loadAudioManagementJBP()throws Exception{
        return read(filename1);
    }
}
