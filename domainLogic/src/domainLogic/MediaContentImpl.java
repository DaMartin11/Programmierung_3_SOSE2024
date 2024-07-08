package domainLogic;

import contract.MediaContent;
import contract.Tag;

import java.util.Collection;

public class MediaContentImpl implements MediaContent {
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
}
