package shop.mtcoding.bank.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

@Getter
@Setter
public class UserRequestDto {
    @Getter
    @Setter
    public static class JoinReqDto{
        //유효성 검사
        @NotEmpty // null이거나, 공백일 수 없다
        private String username;
        @NotEmpty // null이거나, 공백일 수 없다
        private String password;
        @NotEmpty // null이거나, 공백일 수 없다
        private String email;
        @NotEmpty // null이거나, 공백일 수 없다
        private String fullname;

        public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder){
            //유효성 검사
            return User.builder()
                    .username(username)
                    .password(bCryptPasswordEncoder.encode(password))
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER)
                    .build();
        }
    }
}