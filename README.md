# JSP 프로젝트

## TDD를 통한 설계
- 의존성 추가 
    - junit5
    - mockito
    - maven-sure-fire plugin
    - servlet-api
    - jstl api
    - jstl implementation
    - lombok
    - 톰캣서버 설정
1. 회원가입 기능 설계(JoinService -> models/member/JoinService.java)
2. 필수 항목 검증(아이디 , 비밀번호,비밀번호 확인 ,회원명 , 이메일 , 회원가입 약관 동의)
3. 아이디 중복 여부 체크 - 중복된 경우 가입 불가
4. 아이디 자리수 (6자리 이상) , 비밀번호 체크
5. 비밀번호 , 비밀번호 확인 입력 데이터 일치여부 체크
6. 회원 정보를 저장


- 로그인 기능 설계(LoginService) 
  -models/member/LoginService.java
  -필수 항목 검즘(아이디 , 비밀번호)
  -아이디에 해당하는 회원 정보가 있는지 체크
  - 로그인 처리(세션에 회원 정보를 저장)

## 기능 통합
- 회원 가입 
  - Controller : /member/join
    - controllers/member/JoinController
    - Get : 회원가입 양식
    - POST : 회원 가입 처리
    - View : /WEB-INF/templates/member/join.jsp
- 로그인
  - Controller : /member/login
  - controllers/member/LoginController.java
- 메인 페이지
  - 로그인한 경우 사용자 명(아이디)님 로그인 메세지 출력

- 로그아웃
  - controller
    - controllers/member/LogoutController.java
    - GET,POST 메서드 상관 없이 기능 할 수 있도록 처리

## 완성

## 회원가입

## 로그인

## 메인페이지
