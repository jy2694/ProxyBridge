package io.github.jy2694.proxyBridge.entity;

import java.io.*;
import java.util.Base64;

public class RunnableData implements Serializable {

    public static RunnableData deserialize(String data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(data));
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (RunnableData) ois.readObject();
    }

    private String key;
    private SerializableRunnable runnable;

    public RunnableData(String key, SerializableRunnable runnable) {
        this.key = key;
        this.runnable = runnable;
    }

    public String getKey() {
        return key;
    }

    public SerializableRunnable getRunnable() {
        return runnable;
    }

    public String serialize() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
