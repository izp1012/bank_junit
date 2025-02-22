package shop.mtcoding.bank.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import shop.mtcoding.bank.dto.message.ChatMessageDto;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDto sendMessage(ChatMessageDto message) {
        System.out.println("📨 받은 메시지: " + message.getText() + " / From : " + message.getSender());
        return message;
    }
}