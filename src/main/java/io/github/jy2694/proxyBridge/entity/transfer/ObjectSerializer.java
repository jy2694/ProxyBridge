package io.github.jy2694.proxyBridge.entity.transfer;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class ObjectSerializer {

    public static String serialize(Object object){
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(object);
            dataOutput.close();
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch(Exception e){
            return null;
        }
    }

    public static <T> T deserialize(String string, Class<T> tClass){
        try{
            byte[] data = Base64.getDecoder().decode(string);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Object object = dataInput.readObject();
            dataInput.close();
            return tClass.cast(object);
        } catch(Exception e){
            return null;
        }
    }
}
