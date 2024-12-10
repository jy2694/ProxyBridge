package io.github.jy2694.proxyBridge.entity;

import org.bukkit.Bukkit;

import java.util.UUID;

public class Message {

    public static final String PARAMETER_DELIMITER = "<PARAM_DELI/>";
    public static final String MESSAGE_DELIMITER = ":";

    public static Message ofRequest(MessageType type, String to, Object... parameter){
        return new Message(type, to, "" , parameter);
    }

    public static Message ofResponse(UUID messageId, MessageType type, String to, Object parameter){
        return new Message(messageId, type, to, "", parameter);
    }

    public static Message parseAsRequest(String text) {
        String[] parts = text.split(MESSAGE_DELIMITER);
        UUID messageId = UUID.fromString(parts[0]);
        MessageType type = MessageType.valueOf(parts[1]);
        String[] body = parts[2].replaceAll("<colons/>", MESSAGE_DELIMITER).split(PARAMETER_DELIMITER);
        Object[] data = body.length > 0 && !body[0].equals("null") ? new Object[body.length] : null;
        String to = parts[3];
        String from = parts[4];
        if (body.length > 0 && !body[0].equals("null")) {
            Class<?>[] types = type.getRequestType();
            for (int i = 0; i < body.length; i++) {
                data[i] = MessageType.parameterDeserialize(body[i], types[i]);
            }
        }
        return new Message(messageId, type, to, from, data);
    }

    public static Message parseAsResponse(String text){
        String[] parts = text.split(MESSAGE_DELIMITER);
        UUID messageId = UUID.fromString(parts[0]);
        MessageType type = MessageType.valueOf(parts[1]);
        String[] body = parts[2].replaceAll("<colons/>", MESSAGE_DELIMITER).split(PARAMETER_DELIMITER);
        Object[] data = body.length > 0 && !body[0].equals("null") ? new Object[body.length] : null;
        String to = parts[3];
        String from = parts[4];
        if (body.length > 0 && !body[0].equals("null")) {
            for (int i = 0; i < body.length; i++) {
                data[i] = MessageType.parameterDeserialize(body[i], type.getResponseType());
            }
        }
        return new Message(messageId, type, to, from, data);
    }

    private UUID messageId;
    private MessageType type;
    private Object[] body;
    private String to;
    private String from;
    public Message(UUID messageId,  MessageType type, String to, String from, Object... body) {
        this.messageId = messageId;
        this.type = type;
        this.body = body;
        this.to = to;
        this.from = from;
    }
    public Message(MessageType type, String to, String from, Object... body) {
        this(UUID.randomUUID(), type, to, from, body);
    }
    public Message(UUID messageId, MessageType type, String to, Object... body) {
        this(messageId, type, to, "", body);
    }
    public Message(MessageType type, String to, Object... body) {
        this(type, to, "", body);
    }
    public Message(MessageType type, Object... body) {
        this(type, "ALL", body);
    }

    public UUID getMessageId() {
        return messageId;
    }

    public MessageType getType() {
        return type;
    }

    public Object[] getBody() {
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
        StringBuilder bodyBuilder = new StringBuilder();
        for(Object object : getBody()){
            if(!bodyBuilder.isEmpty()) bodyBuilder.append(PARAMETER_DELIMITER);
            bodyBuilder.append(MessageType.parameterSerialize(object));
        }
        return messageId.toString()
                +MESSAGE_DELIMITER+type.getKey()
                +MESSAGE_DELIMITER+(body == null || body[0] == null ? "null": bodyBuilder.toString())
                +MESSAGE_DELIMITER+to
                +MESSAGE_DELIMITER+from;
    }
}
