package io.github.jy2694.proxyBridge.network;

import io.github.jy2694.proxyBridge.entity.Message;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

import java.io.IOException;

public class RedisChannel extends Channel implements RedisPubSubListener<String, String> {

    private String host;
    private int port;
    private String password;

    private RedisClient redisClient;
    private StatefulRedisPubSubConnection<String, String> subscribeConnection;
    private StatefulRedisPubSubConnection<String, String> publishConnection;

    public RedisChannel(String host, int port, String password) {
        this.host = host;
        this.port = port;
        this.password = password;
    }

    @Override
    public void open() {
        RedisURI uri = RedisURI.builder()
                .withHost(host)
                .withPort(port)
                .withPassword(password.toCharArray())
                .build();
        redisClient = RedisClient.create(uri);
        String pong = redisClient.connect().sync().ping();
        if (!pong.equals("PONG")) throw new RuntimeException("Redis connection failed");
        subscribeConnection = redisClient.connectPubSub();
        subscribeConnection.addListener(this);
        subscribeConnection.async().subscribe("proxy_bridge");
        publishConnection = redisClient.connectPubSub();
    }

    @Override
    public void close() {
        if(publishConnection != null && publishConnection.isOpen()) publishConnection.close();
        if(subscribeConnection != null && subscribeConnection.isOpen()) subscribeConnection.close();
        if(redisClient != null) redisClient.shutdown();
    }

    @Override
    public boolean isOpen() {
        return (publishConnection != null && publishConnection.isOpen())
                || (subscribeConnection != null && subscribeConnection.isOpen())
                || redisClient != null;
    }

    @Override
    public void send(Message message) {
        publishConnection.async().publish("proxy_bridge", message.toString());
    }

    @Override
    public void message(String channel, String data) {
        if(!channel.equals("proxy_bridge")) return;
        try {
            Message message = Message.parse(data);
            receiveHandle(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void message(String object, String k1, String object2) {}

    @Override
    public void subscribed(String object, long l) {}

    @Override
    public void psubscribed(String object, long l) {}

    @Override
    public void unsubscribed(String object, long l) {}

    @Override
    public void punsubscribed(String object, long l) {}
}
