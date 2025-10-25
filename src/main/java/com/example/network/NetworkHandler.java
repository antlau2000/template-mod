package com.example.network;

import com.example.Messages;
import com.example.TemplateMod;
import com.google.protobuf.InvalidProtocolBufferException;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class NetworkHandler {

    public static void registerServerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(MessagePayload.ID, (payload, context) -> {
            byte[] data = payload.data();

            try (var server = context.server()) {
                server.execute(() -> {
                    try {
                        Messages.Message message = Messages.Message.parseFrom(data);
                        TemplateMod.LOGGER.info("Received message from {}: {}",
                                context.player().getGameProfile().getName(),
                                message.getText());
                    } catch (InvalidProtocolBufferException e) {
                        TemplateMod.LOGGER.error("Failed to parse message", e);
                    }
                });
            }
        });
    }
}
