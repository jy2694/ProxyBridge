# ProxyBridge

ProxyBridge is a development library designed to enable communication between Minecraft servers. It provides various APIs that allow developers to easily exchange player information, location data, and more between servers. Please note that this library will not function on its own and requires integration into a larger system or application.

## Installation

1. Download the ProxyBridge library.
2. Place the downloaded JAR file into the `plugins` folder of your server.
3. Restart the server.

## Usage

### Using ProxyBridgeAPI

The `ProxyBridgeAPI` class offers the main APIs of the ProxyBridge library. With this API, developers can easily manage player information, location data, and more.

#### Accessing an Instance

You can access an instance of ProxyBridgeAPI through the `ProxyBridge.getAPI()` method. Below are the main methods of `ProxyBridgeAPI` and their usage explanations.

#### Main Methods

1. `getPlayer(UUID uuid)`: Returns player information for the given UUID.
    ```java
    UUID playerUUID = UUID.fromString("player-uuid-string");
    OnlinePlayer player = ProxyBridge.getAPI().getPlayer(playerUUID);
    ```

2. `getPlayer(String name)`: Returns player information for the given name.
    ```java
    String playerName = "playerName";
    OnlinePlayer player = ProxyBridge.getAPI().getPlayer(playerName);
    ```

3. `getPlayer(Player player)`: Converts the given `Player` object to an `OnlinePlayer` and returns it.
    ```java
    Player player = Bukkit.getPlayer("playerName");
    OnlinePlayer onlinePlayer = ProxyBridge.getAPI().getPlayer(player);
    ```

4. `getLocation(Location location, String serverName)`: Returns an `OnlineLocation` object based on the given location and server name.
    ```java
    Location location = new Location(Bukkit.getWorld("world"), x, y, z);
    String serverName = "serverName";
    OnlineLocation onlineLocation = ProxyBridge.getAPI().getLocation(location, serverName);
    ```

5. `getLocation(String world, double x, double y, double z, String serverName)`: Returns an `OnlineLocation` object based on the given world name, coordinates (x, y, z), and server name.
    ```java
    String serverName = "serverName";
    OnlineLocation onlineLocation = ProxyBridge.getAPI().getLocation("world", 0, 0, 0, serverName);
    ```

6. `getWorld(String worldName)`: Returns an `OnlineWorld` object for the given world name.
    ```java
    String worldName = "worldName";
    OnlineWorld onlineWorld = ProxyBridge.getAPI().getWorld(worldName);
    ```

7. `getServer(String serverName)`: Returns an `OnlineServer` object for the given server name.
    ```java
    String serverName = "serverName";
    OnlineServer onlineServer = ProxyBridge.getAPI().getServer(serverName);
    ```

8. `getQueuedRunnables(String key)`: Returns a list of `SerializableRunnable` that are queued for the given key.
    ```java
    String key = "someKey";
    List<SerializableRunnable> runnables = ProxyBridge.getAPI().getQueuedRunnables(key);
    ```

9. `publishRunnable(String key, SerializableRunnable runnable, String serverName)`: Publishes a `SerializableRunnable` with the given key and server name.
    ```java
    String key = "someKey";
    SerializableRunnable runnable = new SomeRunnable();
    String serverName = "serverName";
    CompletableFuture<Boolean> result = ProxyBridge.getAPI().publishRunnable(key, runnable, serverName);
    ```

10. `OnlinePlayer`, `OnlineWorld`, and `OnlineServer` are designed with methods that allow easy access and modification of information on other servers. All these methods return a `CompletableFuture` instance. For example, the `getHealthScale` method of `OnlinePlayer` asynchronously retrieves the player's health scale.
    ```java
    UUID playerUUID = UUID.fromString("player-uuid-string");
    OnlinePlayer player = ProxyBridge.getAPI().getPlayer(playerUUID);
    CompletableFuture<Double> healthScaleFuture = player.getHealthScale();
    healthScaleFuture.thenAccept(healthScale -> {
        System.out.println("Player's health scale: " + healthScale);
    });
    ```

11. Example of using the asynchronous method of `OnlinePlayer`
    ```java
    UUID playerUUID = UUID.fromString("player-uuid-string");
    OnlinePlayer player = ProxyBridge.getAPI().getPlayer(playerUUID);
    CompletableFuture<Double> healthScaleFuture = player.getHealthScale();
    healthScaleFuture.thenAccept(healthScale -> {
        System.out.println("Player's health scale: " + healthScale);
    });
    ```

12. Example of modifying the health scale of `OnlinePlayer`
    ```java
    UUID playerUUID = UUID.fromString("player-uuid-string");
    OnlinePlayer player = ProxyBridge.getAPI().getPlayer(playerUUID);
    CompletableFuture<Boolean> setResult = player.setHealthScale(20.0);
    setResult.thenAccept(success -> {
        if(success){
            System.out.println("Health scale was successfully set.");
        } else {
            System.out.println("Failed to set the health scale.");
        }
    });
    ```

13. Example of using the asynchronous method of `OnlineWorld`
    ```java
    String worldName = "world_name";
    OnlineWorld onlineWorld = ProxyBridge.getAPI().getWorld(worldName);
    CompletableFuture<Void> setGameModeFuture = onlineWorld.setDefaultGameMode(GameMode.SURVIVAL);
    setGameModeFuture.thenAccept(aVoid -> {
        System.out.println("Default game mode was successfully set.");
    });
    ```

14. Example of using the asynchronous method of `OnlineServer`
    ```java
    String serverName = "server_name";
    OnlineServer onlineServer = ProxyBridge.getAPI().getServer(serverName);
    CompletableFuture<Integer> maxPlayersFuture = onlineServer.getMaxPlayers();
    maxPlayersFuture.thenAccept(maxPlayers -> {
        System.out.println("Maximum number of players on the server: " + maxPlayers);
    });
    ```
