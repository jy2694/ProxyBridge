package io.github.jy2694.proxyBridge.network;

import io.github.jy2694.proxyBridge.entity.Message;
import io.github.jy2694.proxyBridge.entity.ServerData;
import io.github.jy2694.proxyBridge.entity.UserData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Channel {

    private Map<UUID, Integer> responseCounter = new ConcurrentHashMap<>();
    private Map<UUID, Object> responseResult = new ConcurrentHashMap<>();

    public abstract void open();
    public abstract void close();
    public abstract boolean isOpen();
    public void receiveHandle(Message message){
        if(!message.getTo().equals("") && !message.getTo().equals("ALL")) return;
        switch (message.getType()) {
            case NO_REPLY -> {
                if(responseCounter.containsKey(message.getMessageId())) responseCounter.put(message.getMessageId(), responseCounter.getOrDefault(message.getMessageId(), 0)-1);
            }
            case USERDATA_REQUEST_BY_NAME -> {
                String name = (String) message.getBody();
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
                if(offlinePlayer.hasPlayedBefore()){
                    send(Message.ofUserDataResponse(message.getMessageId(), message.getFrom(), UserData.adapt(offlinePlayer.getPlayer())));
                } else {
                    send(Message.ofNoReply(message.getMessageId(), message.getFrom()));
                }
            }
            case USERDATA_REQUEST_BY_UUID -> {
                UUID uniqueId = (UUID) message.getBody();
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uniqueId);
                if(offlinePlayer.hasPlayedBefore()){
                    send(Message.ofUserDataResponse(message.getMessageId(), message.getFrom(), UserData.adapt(offlinePlayer.getPlayer())));
                } else {
                    send(Message.ofNoReply(message.getMessageId(), message.getFrom()));
                }
            }
            case USERDATA_RESPONSE, SERVER_STATUS_RESPONSE -> {
                responseResult.put(message.getMessageId(), message.getBody());
                responseCounter.remove(message.getMessageId());
            }
            case SERVER_STATUS_REQUEST -> send(Message.ofServerDataResponse(message.getMessageId(), message.getFrom(), ServerData.adapt(Bukkit.getServer())));
        }
    }
    public abstract void send(Message message);

    public void waitReply(Message message) {
        responseCounter.put(message.getMessageId(), 5);
    }

    public Object getReply(Message message) {
        return responseResult.get(message.getMessageId());
    }

    public boolean isReplyExpired(Message message) {
        return responseCounter.get(message.getMessageId()) == 0;
    }
}
