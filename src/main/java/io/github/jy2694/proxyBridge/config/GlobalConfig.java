package io.github.jy2694.proxyBridge.config;

public class GlobalConfig {
    private static String serverName;
    private static int serverCount;
    private static int requestTimeOutTicks;

    private static boolean pluginMessageChannel;
    private static boolean redisChannel;
    private static String redisHost;
    private static int redisPort;
    private static String redisPassword;

    public static void registerServerInfo(String serverName, int serverCount, int requestTimeOutTicks){
        GlobalConfig.serverName = serverName;
        GlobalConfig.serverCount = serverCount;
        GlobalConfig.requestTimeOutTicks = requestTimeOutTicks;
    }

    public static void enablePluginMessageChannel(){
        GlobalConfig.pluginMessageChannel = true;
    }

    public static void enableRedisChannel(String host, int port, String password){
        GlobalConfig.redisChannel = true;
        GlobalConfig.redisHost = host;
        GlobalConfig.redisPort = port;
        GlobalConfig.redisPassword = password;
    }

    public static String getServerName(){
        return serverName;
    }

    public static int getServerCount(){
        return serverCount;
    }

    public static int getRequestTimeOutTicks(){
        return requestTimeOutTicks;
    }

    public static boolean isPluginMessageChannel(){
        return pluginMessageChannel;
    }

    public static boolean isRedisChannel(){
        return redisChannel;
    }

    public static String getRedisHost(){
        return redisHost;
    }

    public static int getRedisPort(){
        return redisPort;
    }

    public static String getRedisPassword(){
        return redisPassword;
    }
}
