package jos;

import domainLogic.AudioManagement;
import java.io.*;

public class SaveAndLoadJOS {

    private static final String filename2 = "src/IO/jos/savedAudioManagement";
    public void serialize(OutputStream out,AudioManagement audioManagement){
        try{
            ObjectOutputStream objectOut = new ObjectOutputStream(out);
            objectOut.writeObject(audioManagement);
            objectOut.close();
            System.err.println("\nSavingJOS Successful... Checkout your specified output file..\n");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public AudioManagement deserialize(InputStream in){
        AudioManagement audioManagement = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            audioManagement = (AudioManagement) objectInputStream.readObject();
            objectInputStream.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return audioManagement;
    }

    public void saveAudioManagementJOS(AudioManagement audioManagement){
        try {
            File file = new File(filename2);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs(); // Create directories if they do not exist
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            serialize(fileOutputStream, audioManagement);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AudioManagement loadAudioManagementJOS() {
        AudioManagement audioManagement = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filename2);
            audioManagement = deserialize(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return audioManagement;

    }
}
