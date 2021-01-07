package main;

import javax.persistence.EntityManager;

import static main.UserInterface.DAO;
import static main.UserInterface.initialMethod;


public class Main {
    public static void main(String[] args) {
        DAO.openConnection();
        EntityManager manager = DAO.getManager();
        initialMethod(manager);
        DAO.closeConnection();
    }
}

