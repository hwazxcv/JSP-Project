package models.member;


import commons.Validator;

public class JoinService {
   private Validator validator;
   private MemberDAO memberDao;


   public JoinService (Validator validator, MemberDAO memberDao){
       this.validator = validator;
       this.memberDao = memberDao;
   }
    public void join(Member member){

        validator.check(member);
        memberDao.register(member);

    }
}
