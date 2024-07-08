package domainLogic;

import contract.Audio;
import contract.Tag;
import contract.Uploader;

import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class AudioManagement extends Observable implements Serializable {
    private final List<AudioImpl> listOfAudios = new ArrayList<>();


    public synchronized void createAudio(int samplingRate, String address,
                             Collection<Tag> tags, long accessCount, long size, Uploader uploader, Duration availability, BigDecimal cost)throws IllegalArgumentException{
        if (address == null || tags == null || uploader == null || availability == null || cost == null) {
            throw new IllegalArgumentException("One or more parameters are null");
        }
        AudioImpl audio = new AudioImpl(samplingRate,address,tags,accessCount,size,uploader,availability,cost);
        listOfAudios.add(audio);
    }
    public synchronized void  addAudio(int samplingRate, String address,
                          Collection<Tag> tags, long accessCount, long size, Uploader uploader, Duration availability, BigDecimal cost)throws IllegalArgumentException{
        if (address == null || tags == null || uploader == null || availability == null || cost == null) {
            throw new IllegalArgumentException("One or more parameters are null");
        }
        AudioImpl audio = new AudioImpl(samplingRate,address,tags,accessCount,size,uploader,availability,cost);
        listOfAudios.add(audio);
        setChanged();
        notifyObservers("Added audio: " + address);
    }
    public synchronized void deleteAudio (AudioImpl audio){
        listOfAudios.remove(audio);
        setChanged();
        notifyObservers("Deleted audio: " + audio.getAddress());
    }
    public synchronized List<Audio>getAllAudios(){
        return new ArrayList<>(listOfAudios);
    }
    public synchronized AudioImpl findAudioByAddress (String address){
        for (AudioImpl audio : listOfAudios){
            if(audio.getAddress().equals(address)){
                return audio;
            }
        }
        return null; // not found
    }
    public synchronized void updateAccessCount(String address,long newAccessCount){
        AudioImpl audio = findAudioByAddress(address);
        if (audio != null){
            audio.setAccessCount(newAccessCount);
            setChanged();
            notifyObservers("Updated access count for audio: " + address);
        }
    }
    public void saveState(String method) throws IOException{
        if (method.equals("JOS")){
            try(ObjectOutputStream oos =
                        new ObjectOutputStream(new FileOutputStream(
                                "audioManagement.ser"))){
                oos.writeObject(this);
            }
        }else if (method.equals("JBP")){
            // Implement JBP (JavaBeans Persistence) logic here

        }else{
            throw new IllegalArgumentException("unknown method: " + method);
        }
    }

    public static AudioManagement loadState(String method) throws IOException
            , ClassNotFoundException {
        if (method.equals("JOS")) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("audioManagement.ser"))) {
                return (AudioManagement) ois.readObject();
            }
        } else if (method.equals("JBP")) {
            // Implement JBP (JavaBeans Persistence) logic here

            return null;
        } else {
            throw new IllegalArgumentException("Unknown method: " + method);
        }

    }
}
