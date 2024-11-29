package io.github.jy2694.proxyBridge.entity.valina;

import org.bukkit.Location;

public class OnlineLocation {

    public static OnlineLocation adapt(Location location, String server){
        return new OnlineLocation(
                location.getX(),
                location.getY(),
                location.getZ(),
                server,
                location.getWorld().getName(),
                location.getPitch(),
                location.getYaw()
        );
    }

    private final double x;
    private final double y;
    private final double z;
    private final String server;
    private final String world;
    private final float pitch;
    private final float yaw;

    public OnlineLocation(double x, double y, double z, String server, String world, float pitch, float yaw) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.server = server;
        this.world = world;
        this.pitch = pitch;
        this.yaw = yaw;
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

    public String getServer() {
        return server;
    }

    public String getWorld() {
        return world;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }
}
