package main;

import DB.COVID19DAO;
import main.CsvRead.CsvBeanOWID;
import main.CsvRead.CsvRead;


public class Main {
    public static void main(String[] args) {
        CsvBeanOWID bean = new CsvBeanOWID();
        CsvRead csvRead = new CsvRead(bean);
        COVID19DAO dao = new COVID19DAO();
        dao.buildDatabase(csvRead);
    }
}
