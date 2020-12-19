package DB;

public interface COVID19DAO<T> {
    void openConnection();
    void closeConnection();
    void save(T t);
}
