package io.github.jy2694.proxyBridge.entity;

import io.github.jy2694.proxyBridge.entity.transfer.*;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public enum MessageType {

    //Common Message Type
    NO_REPLY(void.class),
    PROCESS_ENQUEUE(boolean.class, RunnableData.class),

    //Player Message Type
    PLAYER_GET_HEALTH_SCALE(double.class, OnlinePlayer.class),
    PLAYER_SET_HEALTH_SCALE(boolean.class, OnlinePlayer.class, double.class),
    PLAYER_GET_HEALTH(double.class, OnlinePlayer.class),
    PLAYER_SET_HEALTH(boolean.class, OnlinePlayer.class, double.class),
    PLAYER_GET_FLY_SPEED(float.class, OnlinePlayer.class),
    PLAYER_SET_FLY_SPEED(boolean.class, OnlinePlayer.class, float.class),
    PLAYER_GET_WALK_SPEED(float.class, OnlinePlayer.class),
    PLAYER_SET_WALK_SPEED(boolean.class, OnlinePlayer.class, float.class),
    PLAYER_GET_NAME(String.class, OnlinePlayer.class),
    PLAYER_GET_UUID(UUID.class, OnlinePlayer.class),
    PLAYER_GET_GAMEMODE(GameMode.class, OnlinePlayer.class),
    PLAYER_SET_GAMEMODE(boolean.class, OnlinePlayer.class, GameMode.class),
    PLAYER_GET_ALLOW_FLIGHT(boolean.class, OnlinePlayer.class),
    PLAYER_SET_ALLOW_FLIGHT(boolean.class, OnlinePlayer.class, boolean.class),
    PLAYER_GET_OP(boolean.class, OnlinePlayer.class),
    PLAYER_SET_OP(boolean.class, OnlinePlayer.class, boolean.class),
    PLAYER_GET_LOCATION(OnlineLocation.class, OnlinePlayer.class),
    PLAYER_GET_BED_SPAWN_LOCATION(OnlineLocation.class, OnlinePlayer.class),
    PLAYER_GET_COMPASS_TARGET(OnlineLocation.class, OnlinePlayer.class),
    PLAYER_GET_EXP(float.class, OnlinePlayer.class),
    PLAYER_SET_EXP(boolean.class, OnlinePlayer.class, float.class),
    PLAYER_GET_TOTAL_EXP(int.class, OnlinePlayer.class),
    PLAYER_SET_TOTAL_EXP(boolean.class, OnlinePlayer.class, int.class),
    PLAYER_GET_LEVEL(int.class, OnlinePlayer.class),
    PLAYER_SET_LEVEL(boolean.class, OnlinePlayer.class, int.class),
    PLAYER_GET_WORLD(OnlineWorld.class, OnlinePlayer.class),
    PLAYER_GET_FOOD_LEVEL(int.class, OnlinePlayer.class),
    PLAYER_SET_FOOD_LEVEL(boolean.class, OnlinePlayer.class, int.class),
    PLAYER_GET_SERVER(String.class, OnlinePlayer.class),
    SEND_MESSAGE(boolean.class, OnlinePlayer.class, String.class),
    SEND_ACTIONBAR(boolean.class, OnlinePlayer.class, String.class),
    SEND_TITLE(boolean.class, OnlinePlayer.class, String.class, String.class, int.class, int.class, int.class),
    TELEPORT(boolean.class, OnlinePlayer.class, OnlineLocation.class),

    //World Message Type
    WORLD_GET_BIOME(Biome.class, OnlineWorld.class, OnlineLocation.class),
    WORLD_SET_BIOME(boolean.class, OnlineWorld.class, OnlineLocation.class, Biome.class),
    WORLD_GET_TIME(long.class, OnlineWorld.class),
    WORLD_SET_TIME(boolean.class, OnlineWorld.class, long.class),
    WORLD_GET_SPAWN_LOCATION(OnlineLocation.class, OnlineWorld.class),
    WORLD_SET_SPAWN_LOCATION(boolean.class, OnlineWorld.class, OnlineLocation.class),
    WORLD_GET_ALLOW_MONSTERS(boolean.class, OnlineWorld.class),
    WORLD_GET_ALLOW_ANIMALS(boolean.class, OnlineWorld.class),
    WORLD_GET_DIFFICULTY(Difficulty.class, OnlineWorld.class),
    WORLD_SET_DIFFICULTY(boolean.class, OnlineWorld.class, Difficulty.class),
    WORLD_GET_ENVIRONMENT(World.Environment.class, OnlineWorld.class),
    WORLD_DROP_ITEM(boolean.class, OnlineWorld.class, OnlineLocation.class, ItemStack.class),
    WORLD_DROP_ITEM_NATURALLY(boolean.class, OnlineWorld.class, OnlineLocation.class, ItemStack.class),
    WORLD_SPAWN_ENTITY(boolean.class, OnlineWorld.class, OnlineLocation.class, EntityType.class),
    
    //Server Message Type
    SERVER_BROADCAST_MESSAGE(boolean.class, String.class),
    SERVER_GET_ONLINE_PLAYERS(String[].class, void.class),
    SERVER_GET_ONLINE_PLAYERS_COUNT(int.class, void.class),
    SERVER_GET_ALLOW_END(boolean.class, void.class),
    SERVER_GET_ALLOW_NETHER(boolean.class, void.class),
    SERVER_GET_ALLOW_FLIGHT(boolean.class, void.class),
    SERVER_GET_DEFAULT_GAMEMODE(GameMode.class, void.class),
    SERVER_SET_DEFAULT_GAMEMODE(boolean.class, GameMode.class),
    SERVER_BAN_IP(boolean.class, String.class),
    SERVER_GET_BANNED_PLAYERS(String[].class, void.class),
    SERVER_GET_MAX_PLAYERS(int.class, void.class),
    SERVER_SET_MAX_PLAYERS(boolean.class, int.class),
    SERVER_GET_WORLDS(String[].class, void.class),
    SERVER_SET_MOTD(boolean.class, String.class),
    SERVER_GET_MOTD(String.class, void.class),
    SERVER_GET_BUKKIT_VERSION(String.class, void.class);
    
    private Class<?>[] requestType;
    private Class<?> responseType;

    MessageType(Class<?> responseType, Class<?>... requestType) {
        this.requestType = requestType;
        this.responseType = responseType;
    }

    public String getKey() {
        return this.toString();
    }

    public Class<?>[] getRequestType() {
        return requestType;
    }

    public Class<?> getResponseType() {
        return responseType;
    }
    
    public static String parameterSerialize(Object object){
        if(object.getClass().isPrimitive()){
            return object.toString();
        } else if(object.getClass() == String.class){
            return  (String) object;
        } else if(object.getClass() == OnlineLocation.class){
            return ObjectSerializer.serialize(object);
        } else if(object.getClass() == OnlinePlayer.class){
            return ObjectSerializer.serialize(object);
        } else if(object.getClass() == UUID.class){
            return object.toString();
        } else if(object.getClass() == OnlineWorld.class){
            return ObjectSerializer.serialize(object);
        } else if(object.getClass() == RunnableData.class){
            return ObjectSerializer.serialize(object);
        } else if(object.getClass() == GameMode.class){
            return ObjectSerializer.serialize(object);
        } else if(object.getClass() == Biome.class){
            return ObjectSerializer.serialize(object);
        } else if(object.getClass() == Difficulty.class){
            return ObjectSerializer.serialize(object);
        } else if(object.getClass() == World.Environment.class){
            return ObjectSerializer.serialize(object);
        } else if(object.getClass() == EntityType.class){
            return ObjectSerializer.serialize(object);
        } else if(object.getClass() == ItemStack.class){
            return ObjectSerializer.serialize(object);
        } else if(object.getClass() == String[].class){
            return ObjectSerializer.serialize(object);
        }
        return "";
    }
    
    public static <T> T parameterDeserialize(String line, Class<T> tclass){
        if(tclass == int.class){
            return tclass.cast(Integer.parseInt(line));
        } else if(tclass == double.class){
            return tclass.cast(Double.parseDouble(line));
        } else if(tclass == float.class){
            return tclass.cast(Float.parseFloat(line));
        } else if(tclass == long.class){
            return tclass.cast(Long.parseLong(line));
        } else if(tclass == short.class){
            return tclass.cast(Short.parseShort(line));
        } else if(tclass == byte.class){
            return tclass.cast(Byte.parseByte(line));
        } else if(tclass == char.class){
            return tclass.cast(line.charAt(0));
        } else if(tclass == boolean.class){
            return tclass.cast(Boolean.parseBoolean(line));
        } else if(tclass == String.class){
            return tclass.cast(line);
        } else if(tclass == OnlineLocation.class){
            return tclass.cast(ObjectSerializer.deserialize(line, OnlineLocation.class));
        } else if(tclass == OnlinePlayer.class){
            return tclass.cast(ObjectSerializer.deserialize(line, OnlinePlayer.class));
        } else if(tclass == UUID.class){
            return tclass.cast(UUID.fromString(line));
        } else if(tclass == OnlineWorld.class){
            return tclass.cast(ObjectSerializer.deserialize(line, OnlineWorld.class));
        } else if(tclass == RunnableData.class){
            return tclass.cast(ObjectSerializer.deserialize(line, RunnableData.class));
        } else if(tclass == GameMode.class){
            return tclass.cast(ObjectSerializer.deserialize(line, GameMode.class));
        } else if(tclass == Biome.class){
            return tclass.cast(ObjectSerializer.deserialize(line, Biome.class));
        } else if(tclass == Difficulty.class){
            return tclass.cast(ObjectSerializer.deserialize(line, Difficulty.class));
        } else if(tclass == World.Environment.class){
            return tclass.cast(ObjectSerializer.deserialize(line, World.Environment.class));
        } else if(tclass == EntityType.class){
            return tclass.cast(ObjectSerializer.deserialize(line, EntityType.class));
        } else if(tclass == ItemStack.class){
            return tclass.cast(ObjectSerializer.deserialize(line, ItemStack.class));
        } else if(tclass == String[].class){
            return tclass.cast(ObjectSerializer.deserialize(line, String[].class));
        }
        return null;
    }
        
}
