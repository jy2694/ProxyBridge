package io.github.jy2694.proxyBridge.entity.valina;

import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.block.Biome;

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

    public String getName() {
        return name;
    }

    public String getServer() {
        return server;
    }

    public CompletableFuture<Biome> getBiome(OnlineLocation location){

    }

    public CompletableFuture<Boolean> setBiome(OnlineLocation location, Biome biome){

    }

    public CompletableFuture<Long> getTime(){

    }

    public CompletableFuture<Boolean> setTime(long time){

    }

    public CompletableFuture<OnlineLocation> getSpawnLocation(){

    }

    public CompletableFuture<Boolean> setSpawnLocation(OnlineLocation location){

    }

    public CompletableFuture<Boolean> getAllowMonsters(){

    }

    public CompletableFuture<Boolean> setAllowMonsters(boolean allow){

    }

    public CompletableFuture<Boolean> getAllowAnimals(){

    }

    public CompletableFuture<Boolean> setAllowAnimals(boolean allow){

    }

    public CompletableFuture<Difficulty> getDifficulty(){

    }

    public CompletableFuture<Boolean> setDifficulty(Difficulty difficulty){

    }

    public CompletableFuture<World.Environment> getEnvironment(){

    }

    public CompletableFuture<Boolean> dropItem(OnlineLocation location, OnlineItemStack itemStack){

    }

    public CompletableFuture<Boolean> dropItemNaturally(OnlineLocation location, OnlineItemStack itemStack){

    }

}
