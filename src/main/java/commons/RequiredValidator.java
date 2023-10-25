package commons;

public interface RequiredValidator {

    //필수 검증 항목 검증 메서드(null이거나 빈값이면 예외)
    default void requiredCheck(String str, RuntimeException e) {
        if (str == null || str.isBlank()) {
            throw e;
        }
    }

    //약관 동의 항목 처리(값이 false이면 예외)
    default void requiredTrue(boolean result, RuntimeException e) {
        if (!result) {
            throw e;
        }
    }
}