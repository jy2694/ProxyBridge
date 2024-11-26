package io.github.jy2694.proxyBridge.entity;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.Base64;
import java.util.UUID;

public class UserData implements Serializable {

    public static UserData adapt(Player player){
        Location location = player.getLocation();
        return new UserData(player.getUniqueId(), player.getName(), "",
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getPitch(),
                location.getYaw());
    }

    public static UserData deserialize(String data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(data));
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (UserData) ois.readObject();
    }

    private UUID uniqueId;
    private String name;
    private String server;
    private String world;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;

    public UserData(UUID uniqueId, String name, String server, String world, double x, double y, double z, float pitch, float yaw) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.server = server;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public String getServer() {
        return server;
    }

    public String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public String serialize() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
