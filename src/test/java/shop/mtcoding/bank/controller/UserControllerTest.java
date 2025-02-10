package shop.mtcoding.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.user.UserRequestDto;
import shop.mtcoding.bank.dummy.DummyObject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest extends DummyObject {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach //모든 메서드가 실행되기 직전에 실행되는 어노테이션
    public void setUp() throws Exception {
        dataSetting();
    }

    @Test
    public void join_test() throws Exception{
        // given
        UserRequestDto.JoinReqDto dto = new UserRequestDto.JoinReqDto();
        dto.setUsername("john");
        dto.setPassword("1234");
        dto.setEmail("izp1012@naver.com");
        dto.setFullname("송인효");

        String requestBody = om.writeValueAsString(dto);
//        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
//        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(status().isCreated());

    }

    @Test
    public void joim_fail_test() throws Exception{
        // given
        UserRequestDto.JoinReqDto dto = new UserRequestDto.JoinReqDto();
        dto.setUsername("ssar");
        dto.setPassword("1234");
        dto.setEmail("ssar@naver.com");
        dto.setFullname("쌀");

        String requestBody = om.writeValueAsString(dto);
//        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);


        //then
//        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(status().isBadRequest());

    }

    private void dataSetting() throws Exception{
        userRepository.save(newUser("ssar","쌀"));
    }
}
