package models.member;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException() {
        super("아직 가입되지 않은 회원입니다.");
    }
}