package main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TemporaryDatabaseCreator {
    public static void main(String[] args) {
        EntityManagerFactory factory= Persistence.createEntityManagerFactory("mysql_local");
        EntityManager manager=factory.createEntityManager();

        System.out.println("Connection to MySQL is open?: " + manager.isOpen());
manager.close();
factory.close();



    }
}
