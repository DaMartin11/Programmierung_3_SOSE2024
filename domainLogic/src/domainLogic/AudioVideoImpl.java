package domainLogic;

import contract.AudioVideo;
import contract.Tag;
import contract.Uploader;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class AudioVideoImpl implements AudioVideo {
    @Override
    public int getSamplingRate() {
        return 0;
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public Collection<Tag> getTags() {
        return null;
    }

    @Override
    public long getAccessCount() {
        return 0;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public Uploader getUploader() {
        return null;
    }

    @Override
    public Duration getAvailability() {
        return null;
    }

    @Override
    public BigDecimal getCost() {
        return null;
    }

    @Override
    public int getResolution() {
        return 0;
    }
}
