package models.member;


import commons.Validator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginService {

    private Validator<HttpServletRequest> validator;
    private MemberDAO memberDao;

    public LoginService(Validator<HttpServletRequest> validator,MemberDAO memberDao){
        this.validator = validator;
        this.memberDao =memberDao;
    }

    public void join(Member member){
        validator.check(member);
        memberDao.register(member);
    }
public void join(HttpServletRequest request){
    Member member =Member.builder()
                .userId(request.getParameter("userId"))
                .userPw(request.getParameter("userPw"))
                .confirmUserPw(request.getRemoteUser("confirmUserPw"))
                .email(request.getParameter("email"))
                .userNm(request.getParameter("userNm"))
                .agree(agree)
                .build();
        join(member);
}


    public void login(HttpServletRequest request){
        validator.check(request);


        String userId = request.getParameter("userId");
        Member member = memberDao.get(userId);
        HttpSession session = request.getSession();
        session.setAttribute("member",member);


    }
}
