package commons;

public interface LengthValidator {

    //아이디 길이 제한 조건에 맞지않으면 예외 발생
    default void lengthCheck(String str, int min, int max, RuntimeException e) {
        if (min > 0 && str.length() < min) {
            throw e;
        }

        if (max > 0 && str.length() > max) {
            throw e;
        }
    }

    default void lengthCheck(String str, int min, RuntimeException e) {
        lengthCheck(str, min, 0, e);
    }
}