package io.github.jy2694.proxyBridge.entity;

import java.util.UUID;

public enum MessageType {
    NO_REPLY(void.class),
    USERDATA_REQUEST_BY_NAME(String.class),
    USERDATA_REQUEST_BY_UUID(UUID.class),
    USERDATA_RESPONSE(UserData.class),
    SERVER_STATUS_REQUEST(void.class),
    SERVER_STATUS_RESPONSE(ServerData.class),
    QUEUE_RUNNABLE(RunnableData.class),
    SEND_MESSAGE(String.class),
    SEND_TITLE(String.class),
    SEND_ACTIONBAR(String.class);
    private Class<?> bodyType;

    MessageType(Class<?> bodyType) {
        this.bodyType = bodyType;
    }

    public String getKey() {
        return this.toString();
    }

    public Class<?> getBodyType() {
        return bodyType;
    }
}
