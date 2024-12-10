package io.github.jy2694.proxyBridge.entity.transfer;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import io.github.jy2694.proxyBridge.ProxyBridge;
import io.github.jy2694.proxyBridge.config.GlobalConfig;
import io.github.jy2694.proxyBridge.entity.Message;
import io.github.jy2694.proxyBridge.entity.MessageType;

public class OnlineWorld {

    public static OnlineWorld adapt(World world){
        return new OnlineWorld(world.getName(), GlobalConfig.getServerName());
    }

    private final String name;
    private final String server;

    public OnlineWorld(String name, String server) {
        this.name = name;
        this.server = server;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof OnlineWorld world)) return false;
        return world.getName().equals(name) && world.getServer().equals(server);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, server);
    }

    public String getName() {
        return name;
    }

    public String getServer() {
        return server;
    }

    public CompletableFuture<Biome> getBiome(OnlineLocation location){
        return CompletableFuture.supplyAsync(() -> {
            if(location.getServer().equals(GlobalConfig.getServerName())) new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ()).getBlock().getBiome();
            Message message = Message.ofRequest(MessageType.WORLD_GET_BIOME, "ALL", this, location);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (Biome) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> setBiome(OnlineLocation location, Biome biome){
        return CompletableFuture.supplyAsync(() -> {
            if(location.getServer().equals(GlobalConfig.getServerName())) {
                new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ()).getBlock().setBiome(biome);
                return true;
            }
            Message message = Message.ofRequest(MessageType.WORLD_SET_BIOME, "ALL", this, location, biome);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return false;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Long> getTime(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getWorld(name).getTime();
            Message message = Message.ofRequest(MessageType.WORLD_GET_TIME, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (long) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> setTime(long time){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) {
                Bukkit.getWorld(name).setTime(time);
                return true;
            }
            Message message = Message.ofRequest(MessageType.WORLD_SET_TIME, "ALL", this, time);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return false;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<OnlineLocation> getSpawnLocation(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) return OnlineLocation.adapt(Bukkit.getWorld(name).getSpawnLocation(), GlobalConfig.getServerName());
            Message message = Message.ofRequest(MessageType.WORLD_GET_SPAWN_LOCATION, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (OnlineLocation) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> setSpawnLocation(OnlineLocation location){
        return CompletableFuture.supplyAsync(()->{
            if(server.equals(GlobalConfig.getServerName())) {
                Bukkit.getWorld(name).setSpawnLocation(
                        new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ()));
                return true;
            }
            Message message = Message.ofRequest(MessageType.WORLD_SET_SPAWN_LOCATION, "ALL", this, location);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return false;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> getAllowMonsters(){
        return CompletableFuture.supplyAsync(()->{
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getWorld(name).getAllowMonsters();
            Message message = Message.ofRequest(MessageType.WORLD_GET_ALLOW_MONSTERS, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
    public CompletableFuture<Boolean> getAllowAnimals(){
        return CompletableFuture.supplyAsync(()->{
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getWorld(name).getAllowAnimals();
            Message message = Message.ofRequest(MessageType.WORLD_GET_ALLOW_ANIMALS, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Difficulty> getDifficulty(){
        return CompletableFuture.supplyAsync(()->{
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getWorld(name).getDifficulty();
            Message message = Message.ofRequest(MessageType.WORLD_GET_DIFFICULTY, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (Difficulty) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> setDifficulty(Difficulty difficulty){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())){
                Bukkit.getWorld(name).setDifficulty(difficulty);
                return true;
            }
            Message message = Message.ofRequest(MessageType.WORLD_SET_DIFFICULTY, "ALL", this, difficulty);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return false;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<World.Environment> getEnvironment(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())) return Bukkit.getWorld(name).getEnvironment();
            Message message = Message.ofRequest(MessageType.WORLD_GET_ENVIRONMENT, "ALL", this);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return null;
            return (World.Environment) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> dropItem(OnlineLocation location, ItemStack itemStack){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())){
                Bukkit.getWorld(name).dropItem(new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ()), itemStack);
                return true;
            }
            Message message = Message.ofRequest(MessageType.WORLD_DROP_ITEM, "ALL", this, location, itemStack);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return false;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> dropItemNaturally(OnlineLocation location, ItemStack itemStack){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())){
                Bukkit.getWorld(name).dropItemNaturally(new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ()), itemStack);
                return true;
            }
            Message message = Message.ofRequest(MessageType.WORLD_DROP_ITEM_NATURALLY, "ALL", this, location, itemStack);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return false;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }

    public CompletableFuture<Boolean> spawnEntity(OnlineLocation location, EntityType type){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals(GlobalConfig.getServerName())){
                Bukkit.getWorld(name).spawnEntity(new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ()), type);
                return true;
            }
            Message message = Message.ofRequest(MessageType.WORLD_SPAWN_ENTITY, "ALL", this, location, type);
            ProxyBridge.getInstance().getMessageChannel().waitReply(message);
            ProxyBridge.getInstance().getMessageChannel().send(message);
            while(!ProxyBridge.getInstance().getMessageChannel().canReceiveReply(message)) Thread.onSpinWait();
            if(ProxyBridge.getInstance().getMessageChannel().getReply(message) == null) return false;
            return (boolean) ProxyBridge.getInstance().getMessageChannel().getReply(message);
        });
    }
}
