package io.github.jy2694.proxyBridge.entity;

import org.bukkit.Server;

import java.io.*;
import java.util.Base64;

public class ServerData implements Serializable {

    public static ServerData adapt(Server server){
        return new ServerData(server.getOnlinePlayers().size(), server.getMaxPlayers(), server.getOnlinePlayers().stream().map(p -> p.getName()).toArray(String[]::new));
    }

    public static UserData deserialize(String data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(data));
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (UserData) ois.readObject();
    }

    private int onlinePlayers;
    private int maxPlayers;
    private String[] playerList;

    public ServerData(int onlinePlayers, int maxPlayers, String[] playerList) {
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
        this.playerList = playerList;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String[] getPlayerList() {
        return playerList;
    }

    public String serialize() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
