package com.example.network;

import com.example.Messages;
import com.example.TemplateMod;
import com.example.database.DatabaseManager;
import com.example.entity.MessageEntity;
import com.google.protobuf.InvalidProtocolBufferException;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import java.util.UUID;

public class NetworkHandler {

    public static void registerServerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(MessagePayload.ID, (payload, context) -> {
            byte[] data = payload.data();

            var server = context.server();
            server.execute(() -> {
                try {
                    Messages.Message message = Messages.Message.parseFrom(data);
                    String playerName = context.player().getGameProfile().getName();
                    UUID playerUuid = context.player().getUuid(); // ПОЛУЧАЕМ UUID
                    String messageText = message.getText();

                    TemplateMod.LOGGER.info("Received message from {} (UUID: {}): {}",
                            playerName, playerUuid, messageText);

                    MessageEntity messageEntity = new MessageEntity(playerUuid, messageText);
                    DatabaseManager.saveMessage(messageEntity);
                    TemplateMod.LOGGER.info("Message {} saved to database", messageEntity.getId());

                } catch (InvalidProtocolBufferException e) {
                    TemplateMod.LOGGER.error("Failed to parse message", e);
                } catch (Exception e) {
                    TemplateMod.LOGGER.error("Failed to save message to database", e);
                }
            });
        });
    }
}
