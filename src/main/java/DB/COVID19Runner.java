package DB;

import DB.entities.Country;
import DB.entities.OWID;
import main.CsvRead.CsvBean;
import main.CsvRead.CsvBeanOWID;
import main.CsvRead.CsvRead;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class COVID19Runner {

//        COVID19DAOImpl<Country> daoCountries = new COVID19DAOImpl<>();
//        daoCountries.openConnection();
//
//        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
//        List<CsvBean> beans = csvRead.getBeanFromCSV();
//        Set<Country> countries = new HashSet<>();


//        beans = beans.stream()
//                .map(bean -> (CsvBeanOWID) bean)
//                .filter(bean -> !bean.getISO_code().isEmpty())
//                .collect(Collectors.toList());

//        List<Country> countries = beans.stream()
//                .map(bean -> (CsvBeanOWID) bean)
//                .filter(bean-> !bean.getISO_code().isEmpty())
//                .map(bean -> new Country(bean))
//                .collect(Collectors.toList());

//        List<Country> countries = beans.stream()
//                .map(bean -> new Country(bean))
//                .forEach(country -> {
//                    country.set();
//                    System.out.println(country);
//                });


//        for (CsvBean bean : beans) {
//            Country country = new Country();
//            country.set((CsvBeanOWID) bean);
//            countries.add(country);
//        }
//
//        daoCountries.save(countries);
//        daoCountries.closeConnection();
//    }

    public static void buildTableCountry(CsvRead csvRead) {
        COVID19DAOImpl<Country> daoCountries = new COVID19DAOImpl<>();
        daoCountries.openConnection();
        List<CsvBean> beans = csvRead.getBeanFromCSV();
        Set<Country> countries = new HashSet<>();

        for (CsvBean bean : beans) {
            Country country = new Country();
            country.set((CsvBeanOWID) bean);
            countries.add(country);
        }

        daoCountries.save(countries);
        daoCountries.closeConnection();
    }

    public static void buildTableOWID(CsvRead csvRead) throws ParseException {
        COVID19DAOImpl<OWID> daoCountries = new COVID19DAOImpl<>();
        daoCountries.openConnection();
        List<CsvBean> beans = csvRead.getBeanFromCSV();
        List<OWID> owids = new ArrayList<>();

        for (CsvBean bean : beans) {
            OWID owid = new OWID();
            owid.set((CsvBeanOWID) bean);
            owids.add(owid);
        }

        daoCountries.save(owids);
        daoCountries.closeConnection();
    }
}
