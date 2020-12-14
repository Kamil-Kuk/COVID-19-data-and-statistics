package main;

import main.CsvRead.CsvBeanOWID;
import main.CsvRead.CsvRead;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CsvBeanOWID bean = new CsvBeanOWID();
        CsvRead csvRead = new CsvRead(bean);

        List<CsvBeanOWID> result = csvRead.getBeanFromCSV();
        result.forEach(System.out :: println);
        System.out.println(result.get(0));
    }
}
