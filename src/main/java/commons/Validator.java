package commons;


//검증할수 있는 항목(T)
public interface Validator<T> {
     void check(T t);
}
