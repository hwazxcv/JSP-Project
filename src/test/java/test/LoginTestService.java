package test;

import commons.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import models.member.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("로그인 기능 테스트")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class LoginTestService {

    private LoginService loginService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private Member member;

    @BeforeEach
    void init() {
        loginService = ServiceManager.getInstance().loginService();
        member = getMember();

        JoinService joinService = ServiceManager.getInstance().joinservice();
        joinService.join(member);

        given(request.getSession()).willReturn(session);
    }

    private Member getMember(){
        return Member.builder()
                .userId("user"+System.currentTimeMillis())
                .userPw("123456")
                .confirmUserPw("123456")
                .userNm("사용자")
                .email("user01@test.org")
                .agree(true)
                .build();
    }

    private void createRequestData(String userId, String userPw){
        given(request.getParameter("userId")).willReturn(userId);
        given(request.getParameter("userPw")).willReturn(userPw);
    }

    @Test
    @DisplayName("로그인 성공시 예외 X")
    void loginSuccess() {
        createRequestData(member.getUserId(), member.getUserPw());

        assertDoesNotThrow(() -> {
            loginService.login(request);
        });
    }
    
    @Test
    @DisplayName("필수 항목 검증(아이디 , 비밀번호) 검증 실패시 예외 발생")
    void requiredFieldCheck(){
        assertAll(
                ()-> {
                    //아이디 검증
                    createRequestData(null, member.getUserPw());
                    fieldEachCheck(request, "아이디");

                    createRequestData("  ", member.getUserPw());
                    fieldEachCheck(request, "아이디");
                },
                ()->{
                    createRequestData(member.getUserId(),null);
                    fieldEachCheck(request, "비밀번호");

                    createRequestData(member.getUserId(), "   ");
                    fieldEachCheck(request , "비밀번호");

                });


    }

    private void fieldEachCheck(HttpServletRequest request , String word){
        BadRequestException thrown = assertThrows(BadRequestException.class , ()->{
            loginService.login(request);
        });

        assertTrue(thrown.getMessage().contains(word));
    }
    
    @Test
    @DisplayName("아이디에 해당하는 회원 정보가 있는지 체크 검증 실패시 예외 발생")
    void memberExistsCheck(){
        assertThrows(MemberNotFoundException.class,()->{
            createRequestData(member.getUserId()+"**",member.getUserPw());
            loginService.login(request);
        });
    }
}


