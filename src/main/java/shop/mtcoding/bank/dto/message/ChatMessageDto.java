package shop.mtcoding.bank.dto.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ChatMessageDto {
    private String sender;
    private String text;
    private LocalDateTime timestamp;

    public ChatMessageDto(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }
}
