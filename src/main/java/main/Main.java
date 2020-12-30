package main;

import DB.COVID19Runner;
import main.CsvRead.CsvBeanOWID;
import main.CsvRead.CsvRead;

import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        CsvBeanOWID bean = new CsvBeanOWID();
        CsvRead csvRead = new CsvRead(bean);
        COVID19Runner.buildTableCountry(csvRead);
//        try {
//            COVID19Runner.buildTableOWID(csvRead);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }
}
