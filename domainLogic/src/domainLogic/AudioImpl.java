package domainLogic;

import contract.Audio;
import contract.Tag;
import contract.Uploader;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class AudioImpl implements Audio, Serializable {
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for serialization

    private int samplingRate;
    private String address;
    private Collection<Tag> tags;
    private long accessCount;
    private long size;
    private Uploader uploader;
    private Duration availability;
    private BigDecimal cost;

    public AudioImpl(int samplingRate,String address, Collection<Tag>tags,long accessCount,long size,Uploader uploader,Duration availability,BigDecimal cost){
        this.samplingRate = samplingRate;
        this.address = address;
        this.tags = tags;
        this.accessCount = accessCount;
        this.size = size;
        this.uploader = uploader;
        this.availability = availability;
        this.cost = cost;
        }
        @Override
        public int getSamplingRate() {
            return samplingRate;
        }
        public void setSamplingRate(int samplingRate){
            this.samplingRate = samplingRate;
        }

        @Override
        public String getAddress() {
            return address;
        }
        public void setAddress(String address){
            this.address = address;
        }

        @Override
        public Collection<Tag> getTags() {
            return tags;
        }
        public void setTags(Collection<Tag> tags){
            this.tags = tags;
        }

        @Override
        public long getAccessCount() {
            return accessCount;
        }
        public void setAccessCount(long accessCount){
            this.accessCount = accessCount;
        }

        @Override
        public long getSize() {
            return size;
        }
        public void setSize(long size){
            this.size = size;
        }

        @Override
        public Uploader getUploader() {
            return uploader;
        }

        public void setUploader(Uploader uploader){
            this.uploader = uploader;
        }

        @Override
        public Duration getAvailability() {
            return availability;
        }

        public void setAvailability(Duration availability){
            this.availability = availability;
        }

        @Override
        public BigDecimal getCost() {
            return cost;
        }
        public void setCost(BigDecimal cost){
            this.cost = cost;
        }
        public String toString() {
         return "AudioImpl{" +
                "samplingRate=" + samplingRate +
                ", address='" + address + '\'' +
                ", tags=" + tags +
                ", accessCount=" + accessCount +
                ", size=" + size +
                ", uploader=" + uploader.getName() +
                ", cost=" + cost +
                '}';
    }

}
