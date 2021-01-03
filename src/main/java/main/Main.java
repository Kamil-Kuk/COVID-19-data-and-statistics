package main;

import DB.COVID19DAO;
import DB.entities.Country;
import DB.entities.CovidData;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import main.CsvRead.CsvBeanOWID;
import main.CsvRead.CsvRead;
import main.CsvWrite.CsvBean;
import main.CsvWrite.CsvWrite;

import java.io.IOException;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) throws ParseException {
//        CsvBeanOWID bean = new CsvBeanOWID();
//        CsvRead csvRead = new CsvRead(bean);
//        COVID19DAO dao = new COVID19DAO();
//        dao.buildDatabase(csvRead);

        List<CovidData> beans = new ArrayList<>();


        Country poland = new Country("POL", "Europe", "Poland", 38000000);
        for (int i = 0; i < 20; i++) {
            Date date = new Date(1609438882141L + 86_400_000*i);
            CovidData bean = new CovidData(poland,date, i*i*1000,1*99,i*15,i*2,0,0,0,0);
            beans.add(bean);
        }
        try {
            CsvWrite.writeCsvFromBean(beans, "resultTest");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }


}

