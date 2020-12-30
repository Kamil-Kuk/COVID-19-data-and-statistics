package DB;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class COVID19DAOImpl<T> implements COVID19DAO<T> {
    private EntityManagerFactory factory;
    private EntityManager manager;


    @Override
    public void openConnection() {
        factory = Persistence.createEntityManagerFactory("mysql_local");
        manager = factory.createEntityManager();
    }

    @Override
    public void closeConnection() {
        manager.close();
        factory.close();
    }

    @Override
    public void save(T t) {
        manager.getTransaction().begin();
        manager.persist(t);
        manager.getTransaction().commit();
    }

    @Override
    public void save(Collection<T> collection) {
        manager.getTransaction().begin();
        for(T item:collection){
            manager.persist(item);
        }
        manager.getTransaction().commit();
    }
}
