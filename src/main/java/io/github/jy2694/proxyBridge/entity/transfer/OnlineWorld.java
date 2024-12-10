package io.github.jy2694.proxyBridge.entity.valina;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class OnlineWorld {

    public static OnlineWorld adapt(World world){
        return new OnlineWorld(world.getName(), "");
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
            if(location.getServer().equals("")) new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ()).getBlock().getBiome();
            //todo : request biome
            return Biome.PLAINS;
        });
    }

    public CompletableFuture<Boolean> setBiome(OnlineLocation location, Biome biome){
        return CompletableFuture.supplyAsync(() -> {
            if(location.getServer().equals("")) {
                new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ()).getBlock().setBiome(biome);
                return true;
            }
            //todo : request set biome
            return true;
        });
    }

    public CompletableFuture<Long> getTime(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals("")) return Bukkit.getWorld(name).getTime();
            //todo : request time
            return 0L;
        });
    }

    public CompletableFuture<Boolean> setTime(long time){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals("")) {
                Bukkit.getWorld(name).setTime(time);
                return true;
            }
            //todo : request set time
            return true;
        });
    }

    public CompletableFuture<OnlineLocation> getSpawnLocation(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals("")) return OnlineLocation.adapt(Bukkit.getWorld(name).getSpawnLocation(), "");
            //todo : request spawn location
            return null;
        });
    }

    public CompletableFuture<Boolean> setSpawnLocation(OnlineLocation location){
        return CompletableFuture.supplyAsync(()->{
            if(server.equals("")) {
                Bukkit.getWorld(name).setSpawnLocation(
                        new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ()));
                return true;
            }
            //todo : request set spawn location
            return true;
        });
    }

    public CompletableFuture<Boolean> getAllowMonsters(){
        return CompletableFuture.supplyAsync(()->{
            if(server.equals("")) return Bukkit.getWorld(name).getAllowMonsters();
            //todo : request allow monsters
            return true;
        });
    }
    public CompletableFuture<Boolean> getAllowAnimals(){
        return CompletableFuture.supplyAsync(()->{
            if(server.equals("")) return Bukkit.getWorld(name).getAllowAnimals();
            //todo : request allow animals
            return true;
        });
    }

    public CompletableFuture<Difficulty> getDifficulty(){
        return CompletableFuture.supplyAsync(()->{
            if(server.equals("")) return Bukkit.getWorld(name).getDifficulty();
            //todo : request difficulty
            return Difficulty.NORMAL;
        });
    }

    public CompletableFuture<Boolean> setDifficulty(Difficulty difficulty){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals("")){
                Bukkit.getWorld(name).setDifficulty(difficulty);
                return true;
            }
            //todo : request set difficulty
            return true;
        });
    }

    public CompletableFuture<World.Environment> getEnvironment(){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals("")) return Bukkit.getWorld(name).getEnvironment();
            //todo : request environment
            return World.Environment.NORMAL;
        });
    }

    public CompletableFuture<Boolean> dropItem(OnlineLocation location, ItemStack itemStack){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals("")){
                Bukkit.getWorld(name).dropItem(new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ()), itemStack);
                return true;
            }
            //todo : request drop item
            return true;
        });
    }

    public CompletableFuture<Boolean> dropItemNaturally(OnlineLocation location, ItemStack itemStack){
        return CompletableFuture.supplyAsync(() -> {
            if(server.equals("")){
                Bukkit.getWorld(name).dropItemNaturally(new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ()), itemStack);
                return true;
            }
            //todo : request drop item naturally
            return true;
        });
    }

}
