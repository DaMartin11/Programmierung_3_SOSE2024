package domainLogic;

import contract.Uploader;

import java.io.Serializable;

public class UploaderImpl implements Uploader, Serializable {
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for serialization
    private final String name;
    public UploaderImpl(String name){
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }
}
