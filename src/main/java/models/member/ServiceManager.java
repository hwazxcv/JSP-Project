package models.member;

//객체 조립 클래스

public class ServiceManager {

    private static ServiceManager instance;

    private ServiceManager() {}

    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
        }

        return instance;
    }

    public MemberDAO memberDao() {
        return new MemberDAO();
    }

    public JoinValidator joinValidator() {
        JoinValidator validator = new JoinValidator();
        validator.setMemberDao(memberDao());

        return validator;
    }

    public JoinService joinService() {
        return new JoinService(joinValidator(), memberDao());
    }

    public LoginValidator loginValidator() {

        return new LoginValidator(memberDao());
    }

    public LoginService loginService() {
        return new LoginService(loginValidator(), memberDao());
    }
}