package models.member;


import lombok.Builder;
import lombok.Data;

//데이터 객체(DTO , VO)

@Data
@Builder
public class Member {
    private String userId;
    private String userPw;
    private String confirmUserPw;
    private String userNm;
    private String email;
    private boolean agree;

}
