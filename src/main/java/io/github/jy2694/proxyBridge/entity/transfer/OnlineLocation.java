package io.github.jy2694.proxyBridge.entity.valina;

import org.bukkit.Location;

import java.io.Serializable;
import java.util.Objects;

public class OnlineLocation implements Serializable {

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof OnlineLocation that)) return false;
        return Double.compare(x, that.x) == 0
                && Double.compare(y, that.y) == 0
                && Double.compare(z, that.z) == 0
                && Float.compare(pitch, that.pitch) == 0
                && Float.compare(yaw, that.yaw) == 0
                && Objects.equals(server, that.server)
                && Objects.equals(world, that.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, server, world, pitch, yaw);
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
