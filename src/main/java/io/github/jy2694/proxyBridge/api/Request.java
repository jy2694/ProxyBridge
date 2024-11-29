package io.github.jy2694.proxyBridge.api;

import io.github.jy2694.proxyBridge.entity.*;
import io.github.jy2694.proxyBridge.network.Channel;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Request {

    private Channel channel;

    public Request(Channel channel) {
        this.channel = channel;
    }

    public CompletableFuture<UserData> getUserData(String username) {
        return CompletableFuture.supplyAsync(()->{
            Message<String> request = Message.ofUserDataRequest(username);
            channel.send(request);
            channel.waitReply(request);
            while(!channel.isReplyExpired(request) && channel.getReply(request) == null) Thread.onSpinWait();
            return (UserData) channel.getReply(request);
        });
    }

    public CompletableFuture<UserData> getUserData(UUID uniqueId) {
        return CompletableFuture.supplyAsync(()->{
            Message<UUID> request = Message.ofUserDataRequest(uniqueId);
            channel.send(request);
            channel.waitReply(request);
            while(!channel.isReplyExpired(request) && channel.getReply(request) == null) Thread.onSpinWait();
            return (UserData) channel.getReply(request);
        });
    }

    public CompletableFuture<ServerData> getServerData(String serverName) {
        return CompletableFuture.supplyAsync(()->{
            Message<Void> request = Message.ofServerDataRequest(serverName);
            channel.send(request);
            channel.waitReply(request);
            while(!channel.isReplyExpired(request) && channel.getReply(request) == null) Thread.onSpinWait();
            return (ServerData) channel.getReply(request);
        });
    }

    public void sendTitle(UserData user, String title, String subtitle, int fadeIn, int stay, int fadeOut){
        Message<String> message = Message.ofSendTitle(user.getServer(), user.getName(), title, subtitle, fadeIn, stay, fadeOut);
        channel.send(message);
    }

    public void sendActionbar(UserData user, String message){
        Message<String> msg = Message.ofSendActionBar(user.getServer(), user.getName(), message);
        channel.send(msg);
    }

    public void sendMessage(UserData user, String message){
        Message<String> msg = Message.ofSendMessage(user.getServer(), user.getName(), message);
        channel.send(msg);
    }

    public void produceRunnable(String server, String key, SerializableRunnable runnable){
        produceRunnable(server, new RunnableData(key, runnable));
    }

    public void produceRunnable(String server, RunnableData data){
        Message<RunnableData> msg = Message.ofQueueRunnable(server, data);
        channel.send(msg);
    }

    public void consumeAllRunnable(String key){
        while(consumeRunnable(key));
    }

    public boolean consumeRunnable(String key){
        List<SerializableRunnable> serializableRunnableList = channel.queuedRunnable.get(key);
        if(serializableRunnableList == null) return false;
        if(serializableRunnableList.size() == 0) return false;
        serializableRunnableList.remove(0).run();
        return true;
    }
}
