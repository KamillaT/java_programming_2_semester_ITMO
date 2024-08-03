package database;

public interface ThrowableConsumer<T> {
    void accept(T value) throws Exception;
}
