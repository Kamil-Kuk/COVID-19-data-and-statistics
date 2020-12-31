package main;

import DB.COVID19DAO;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import main.CsvRead.CsvBeanOWID;
import main.CsvRead.CsvRead;
import main.CsvWrite.CsvBean;
import main.CsvWrite.CsvWrite;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) {
//        CsvBeanOWID bean = new CsvBeanOWID();
//        CsvRead csvRead = new CsvRead(bean);
//        COVID19DAO dao = new COVID19DAO();
//        dao.buildDatabase(csvRead);

        List<CsvBean> beans = new ArrayList<>();


        for (int i = 0; i < 20; i++) {
            CsvBean bean = new CsvBean();
            bean.setISO_code("POL");
            bean.setTotal_cases(i * 99);
            bean.setNew_cases(i * 3);
            bean.setDate(new Date(1609438882141L + 86_400_000*i)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
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

