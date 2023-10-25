package models.member;

import commons.Validator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//로그인 기능 클래스
public class LoginService {

    private Validator<HttpServletRequest> validator;
    private MemberDAO memberDao;

    //생성자 주입
    public LoginService(Validator<HttpServletRequest> validator, MemberDAO memberDao) {
        this.validator = validator;
        this.memberDao = memberDao;
    }

    public void login(HttpServletRequest request) {

        validator.check(request);

        // 로그인 처리
        String userId = request.getParameter("userId");
        Member member = memberDao.get(userId);
        HttpSession session = request.getSession();
        session.setAttribute("member", member);
    }
}