package DB;

import java.util.Collection;

public interface COVID19DAO<T> {
    void openConnection();
    void closeConnection();
    void save(T t);
    void save(Collection<T> collection);
}
