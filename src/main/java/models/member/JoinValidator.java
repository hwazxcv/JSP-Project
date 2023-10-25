package models.member;

import commons.BadRequestException;
import commons.LengthValidator;
import commons.RequiredValidator;
import commons.Validator;

public class JoinValidator implements Validator<Member> , RequiredValidator, LengthValidator {

    private MemberDAO memberDao;

    // setter메서드 주입
    public void setMemberDao(MemberDAO memberDao){
        this.memberDao = memberDao;

    }

    @Override
    public void check(Member member) {
        String userId= member.getUserId();
        String userPw = member.getUserPw();
        String confirmUserPw = member .getConfirmUserPw();

        //필수 항목 검증
        requiredCheck(userId, new BadRequestException("아이디를 입력하세요"));
        requiredCheck(userPw, new BadRequestException("비밀 번호를 입력하세요"));
        requiredCheck(confirmUserPw, new BadRequestException("비밀번호가 맞는지 확인하세요"));
        requiredCheck(member.getUserNm(), new BadRequestException("이름을 입력하세요"));
        requiredCheck(member.getEmail(), new BadRequestException("이메일을 입력하세요"));

        requiredTrue(member.isAgree() , new BadRequestException("회원가입 약관에 동의 하세요"));

        //아이디 비밀번호 자리수 검증
        lengthCheck(userId, 6,new BadRequestException("아이디는 6자리 이상 입력하세요"));
        lengthCheck(userPw,8,new BadRequestException("비밀번호는 8자리 이상 입력하세요"));

        //비밀번호 일치여부
        requiredTrue(userPw.equals(confirmUserPw), new BadRequestException("비밀번호가 일치하지 않습니다."));

        //중복 가입 여부
        requiredTrue(!memberDao.exists(userId),new DuplicateMemberException());






    }
}
