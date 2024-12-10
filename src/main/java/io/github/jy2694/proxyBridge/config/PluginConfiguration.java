package io.github.jy2694.proxyBridge.config;

import io.github.jy2694.proxyBridge.ProxyBridge;

public class PluginConfiguration extends AbstractConfiguration {

    public PluginConfiguration() {
        super("config.yml");
    }

    @Override
    public void load() {
        String serverName = config.getString("velocity.server");
        int serverCount = config.getInt("velocity.server-count");
        int requestTimeOutTicks = config.getInt("velocity.request-time-out");
        GlobalConfig.registerServerInfo(serverName, serverCount, requestTimeOutTicks);

        String messageChannelType = config.getString("message-channel.type");
        if(messageChannelType.equalsIgnoreCase("PLUGIN_MESSAGE")){
            GlobalConfig.enablePluginMessageChannel();
        } else if(messageChannelType.equalsIgnoreCase("REDIS")){
            String host = config.getString("redis.host");
            int port = config.getInt("redis.port");
            String password = config.getString("redis.password");
            if(host == null || port == 0 || password == null){
                ProxyBridge.getInstance().getLogger().severe("Redis is not configured correctly. Please check the config.yml file.");
                return;
            }
            GlobalConfig.enableRedisChannel(host, port, password);
        }
    }
}
