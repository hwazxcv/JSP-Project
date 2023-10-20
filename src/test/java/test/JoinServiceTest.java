package test;


import commons.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
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

@DisplayName("회원가입 기능 테스트")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class JoinServiceTest {

    private JoinService joinService;
    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void init(){
        MemberDAO.clearData();
        joinService = ServiceManager.getInstance().joinservice();

    }
    private Member getMember(){

        return Member.builder()
                .userId("user"+System.currentTimeMillis())
                .userPw("123456")
                .userNm("사용자")
                .confirmUserPw("123456")
                .email("user01@test.org")
                .agree(true)
                .build();
    }

    @Test
    @DisplayName("회원가입 성공시 예외 X")
    void joinSuccess(){
        assertDoesNotThrow(()->{
            joinService.join(getMember());
            
        });
    }
    @Test
    @DisplayName("HttpServletRequest 요청 데이터로 성공 테스트")
    void joinSuccessByRequest(){
        Member member =getMember();
        given(request.getParameter("userId")).willReturn(member.getUserId());
        given(request.getParameter("userPw")).willReturn(member.getUserPw());
        given(request.getParameter("confirmUserPw")).willReturn(member.getConfirmUserPw());
        given(request.getParameter("userNm")).willReturn(member.getUserNm());
        given(request.getParameter("agree")).willReturn(member.getEmail());

        joinService.join(request);
    }
    
    @Test
    @DisplayName("필수 항목 검증 실패시 예외")

    void requiredFieldCheck(){
        //아이디가 null이거나 빈값일때
                    assertAll(
                            () -> {
                                Member member = getMember();
                                member.setUserId(null);
                                fieldEachCheck(member, "아이디");

                                member.setUserId("  ");
                                fieldEachCheck(member, "아이디");

                            },
                            () -> {
                                Member member = getMember();
                                member.setUserPw(null);
                                fieldEachCheck(member, "비밀 번호");

                                member.setUserPw("  ");
                                fieldEachCheck(member, "비밀 번호");
                            },
                            () -> {
                                Member member = getMember();
                                member.setConfirmUserPw(null);
                                fieldEachCheck(member, "비밀번호가 맞는지");

                                member.setConfirmUserPw("  ");
                                fieldEachCheck(member, "비밀번호가 맞는지");
                            },

                            () -> {
                                Member member = getMember();
                                member.setUserNm(null);
                                fieldEachCheck(member, "이름을");


                                member.setUserNm("  ");
                                fieldEachCheck(member, "이름을");
                            },
                            () -> {
                                Member member = getMember();
                                member.setEmail(null);
                                fieldEachCheck(member, "이메일을");

                                member.setEmail("  ");
                                fieldEachCheck(member, "이메일을");
                            },
                            () -> {
                                Member member = getMember();
                                member.setAgree(false);
                                fieldEachCheck(member, "회원가입 약관에");
                            }
                    );
                }

    //문구에 대한 예외 검증
    private void fieldEachCheck(Member member , String word){
        BadRequestException thrown = assertThrows(BadRequestException.class , ()->{
            joinService.join(member);
        });
        
        assertTrue(thrown.getMessage().contains(word));
    }

    @Test
    @DisplayName("아이디 (6자리 이상) 비밀번호(8자리 이상) 최소 자리수 체크 실패시 예외 발생")
    void fieldLengthCheck(){
        assertAll(
                ()->{
                    //아이디 6자리 이상 검증
                    Member member = getMember();
                    member.setUserId("user");
                    fieldEachCheck(member,"아이디는 6자리 이상으로");
        },
                ()->{
                    //비밀번호8자리 이상 검증
                    Member member = getMember();
                    member.setUserPw("1234");
                    fieldEachCheck(member,"비밀번호는 8자리 이상으로");
                }
        );
    }
    @Test
    @DisplayName("비밀번호, 비밀번호 확인 입력 데이터 일치여부")
    void passwordConfirmCheck(){
       BadRequestException thrown =  assertThrows(BadRequestException.class, ()->{
           Member member = getMember();
           member.setConfirmUserPw(member.getUserPw()+"**");
           joinService.join(member);
        });
       assertTrue(thrown.getMessage().contains("비밀번호가 일치"));
    }
    @Test
    @DisplayName("중복가입 체크 중복이면 예외 발생")
    void duplicateJoinCheck(){
        assertThrows(DuplicateMemberException.class, ()->{
           Member member = getMember();
           String userPw =member.getUserPw();
           joinService.join(member);

            member.setUserPw(userPw);
           joinService.join(member);
        });
    }
    


}



//            Member member = getMember();

//            member.setUserId(null);
//
//            joinService.join(member);
//
//            member.setUserId("   ");
//            joinService.join(member);
//   });
//
//        //던진 예외메세지를 체크
//        String message = thrown.getMessage();
//        //assertEquals("아이디를 입력하세요" , message);
//        assertTrue(message.contains("아이디"));