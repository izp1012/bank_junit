package shop.mtcoding.bank.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.user.UserRequestDto;
import shop.mtcoding.bank.dto.user.UserResponseDto;
import shop.mtcoding.bank.dto.user.UserResponseDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional  //트랜잭션이 메서드 시작할떄, 시작되고, 종료될 떄 함께 종료
    public UserResponseDto.JoinRespDto 회원가입(UserRequestDto.JoinReqDto joinReqDto){
        // 1. 동일 유저 네임 존재 검사
        Optional<User> userOP = userRepository.findByUsername(joinReqDto.getUsername());

        if(userOP.isPresent()){
            //유저네임 중복되었다는 뜻
            throw new CustomApiException("동일한 username이 존재합니다");
        }

        // 2. 패스워드 인코딩
        User userPS = userRepository.save(joinReqDto.toEntity(bCryptPasswordEncoder));
        
        // 3. dto 응답
        return new UserResponseDto.JoinRespDto(userPS);
    }
}
