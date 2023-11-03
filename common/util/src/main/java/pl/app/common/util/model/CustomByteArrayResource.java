package pl.app.common.util.model;

import org.springframework.core.io.ByteArrayResource;

import java.util.Base64;

public class CustomByteArrayResource extends ByteArrayResource {
    private String filename;

    public CustomByteArrayResource(String base64, String filename) {
        super(Base64.getDecoder().decode(base64));
        setFilename(filename);
    }
    public CustomByteArrayResource(byte[] byteArray) {
        super(byteArray);
        setFilename("");
    }
    public CustomByteArrayResource(byte[] byteArray, String filename) {
        super(byteArray);
        setFilename(filename);
    }

    public CustomByteArrayResource(byte[] byteArray, String filename, String description) {
        super(byteArray, description);
        setFilename(filename);
    }
    @Override
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename){
        this.filename = filename;
    }
}