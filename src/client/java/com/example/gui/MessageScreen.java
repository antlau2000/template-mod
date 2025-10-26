package com.example.gui;

import com.example.Messages;
import com.example.client.TemplateModClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import static com.example.client.TemplateModClient.sendMessageToServer;

public class MessageScreen extends Screen {
    private TextFieldWidget textField;
    private ButtonWidget sendButton;

    public MessageScreen() {
        super(Text.literal("Send Message"));
    }

    @Override
    protected void init() {
        super.init();

        this.textField = new TextFieldWidget(
                this.textRenderer,
                this.width / 2 - 100, this.height / 2 - 30, 200, 20,
                Text.literal("Enter message")
        );
        this.addDrawableChild(this.textField);

        this.sendButton = ButtonWidget.builder(
                        Text.literal("Send Message"),
                        button -> this.sendMessage()
                )
                .position(this.width / 2 - 100, this.height / 2)
                .size(200, 20)
                .build();
        this.addDrawableChild(this.sendButton);

        this.setInitialFocus(this.textField);
    }

    private void sendMessage() {
        String messageText = this.textField.getText();
        if (messageText.isEmpty()) {
            return;
        }

        TemplateModClient.LOGGER.info("Sending message: {}", messageText);

        try {
            Messages.Message message = Messages.Message.newBuilder()
                    .setText(messageText)
                    .build();

            byte[] data = message.toByteArray();
            TemplateModClient.LOGGER.info("Message serialized, size: {} bytes", data.length);

            sendMessageToServer(data);

        } catch (Exception e) {
            TemplateModClient.LOGGER.error("Failed to send message", e);
        }

        this.close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xCC000000);

        context.drawCenteredTextWithShadow(this.textRenderer, this.title,
                this.width / 2, this.height / 2 - 60, 0xFFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void close() {
        super.close();
    }
}
