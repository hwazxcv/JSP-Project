package models.member;

import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;


//데이터 접근 클래스

public class MemberDAO {
    private static Map<String, Member> members = new HashMap<>();

    public void register(Member member) {
        // 비밀번호 암호화 하기
        String userPw = BCrypt.hashpw(member.getUserPw(), BCrypt.gensalt(12));
        member.setUserPw(userPw);
        members.put(member.getUserId(), member);
    }

    public Member get(String userId) {
        return members.get(userId);
    }

    public boolean exists(String userId) {
        //키가 있으면 중복
        return members.containsKey(userId);
    }

    public static void clearData() {
        members.clear();
    }
}