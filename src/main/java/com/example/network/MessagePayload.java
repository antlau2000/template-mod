package com.example.network;

import com.example.TemplateMod;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record MessagePayload(byte[] data) implements CustomPayload {
    public static final Id<MessagePayload> ID = new Id<>(Identifier.of(TemplateMod.MOD_ID, "message"));

    public static final PacketCodec<PacketByteBuf, MessagePayload> CODEC = PacketCodec.of(
            (payload, buf) -> buf.writeBytes(payload.data),
            buf -> {
                byte[] data = new byte[buf.readableBytes()];
                buf.readBytes(data);
                return new MessagePayload(data);
            }
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
