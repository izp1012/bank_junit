package shop.mtcoding.bank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.user.UserRequestDto;
import shop.mtcoding.bank.dto.user.UserResponseDto;
import shop.mtcoding.bank.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping(value = "/api")
@RestController
public class UserController { // 데이터를 리턴하는 서버
     private final UserService userService;

     @PostMapping("/join")
     public ResponseEntity<?> join(@RequestBody @Valid UserRequestDto.JoinReqDto joinReqDto, BindingResult bindingResult) {

          UserResponseDto.JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
          return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinRespDto), HttpStatus.CREATED);
     }
}
