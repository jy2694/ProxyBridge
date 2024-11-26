package io.github.jy2694.proxyBridge.entity;

import java.io.IOException;
import java.util.UUID;

public class Message<T> {

    public static Message<Void> ofNoReply(UUID messageId, String to) {
        return new Message<>(messageId, MessageType.NO_REPLY, null, to);
    }

    public static Message<String> ofUserDataRequest(String name) {
        return new Message<>(MessageType.USERDATA_REQUEST_BY_NAME, name);
    }

    public static Message<UUID> ofUserDataRequest(UUID uuid) {
        return new Message<>(MessageType.USERDATA_REQUEST_BY_UUID, uuid);
    }

    public static Message<UserData> ofUserDataResponse(UUID messageId, String to, UserData body) {
        return new Message<>(messageId, MessageType.USERDATA_RESPONSE, body, to);
    }

    public static Message<Void> ofServerDataRequest(String server){
        return new Message<>(MessageType.SERVER_STATUS_REQUEST, null, server);
    }

    public static Message<ServerData> ofServerDataResponse(UUID messageId, String to, ServerData body){
        return new Message<>(messageId, MessageType.SERVER_STATUS_RESPONSE, body, to);
    }

    public static Message<?> parse(String text) throws IOException, ClassNotFoundException {
        String[] parts = text.split(":");
        UUID messageId = UUID.fromString(parts[0]);
        MessageType type = MessageType.valueOf(parts[1]);
        String body = parts[2].replaceAll("<colons/>", ":");
        Object data = null;
        if(!body.equals("null")) {
            switch (type) {
                case USERDATA_RESPONSE:
                    data = UserData.deserialize(body);
                    break;
                case SERVER_STATUS_RESPONSE:
                    data = ServerData.deserialize(body);
                    break;
                case USERDATA_REQUEST_BY_UUID:
                    data = UUID.fromString(body);
                    break;
                case SERVER_STATUS_REQUEST:
                case NO_REPLY:
                    data = null;
                    break;
                case USERDATA_REQUEST_BY_NAME:
                case SEND_MESSAGE:
                case SEND_ACTIONBAR:
                case SEND_TITLE:
                    data = body;
            }
        }
        String to = parts[3];
        String from = parts[4];
        return new Message<>(messageId, type, data, to, from);
    }

    private UUID messageId;
    private MessageType type;
    private T body;
    private String to;
    private String from;
    public Message(UUID messageId,  MessageType type, T body, String to, String from) {
        this.messageId = messageId;
        this.type = type;
        this.body = body;
        this.to = to;
        this.from = from;
    }
    public Message(MessageType type, T body, String to, String from) {
        this(UUID.randomUUID(), type, body, to, from);
    }
    public Message(UUID messageId, MessageType type, T body, String to) {
        this(messageId, type, body, to, "");
    }
    public Message(MessageType type, T body, String to) {
        this(type, body, to, "");
    }
    public Message(MessageType type, T body) {
        this(type, body, "ALL");
    }

    public UUID getMessageId() {
        return messageId;
    }

    public MessageType getType() {
        return type;
    }

    public T getBody() {
        return body;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    @Override
    public String toString(){
        return messageId.toString()+":"+type.getKey()+":"+(body == null ? "null":body.toString().replaceAll(":", "<colons/>"))+":"+to+":"+from;
    }
}
