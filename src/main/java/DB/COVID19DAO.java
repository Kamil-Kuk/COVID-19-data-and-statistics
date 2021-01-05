package DB;

import DB.entities.Country;
import DB.entities.CovidData;
import main.CsvRead.CsvBean;
import main.CsvRead.CsvBeanOWID;
import main.CsvRead.CsvRead;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.nio.file.NoSuchFileException;
import java.text.ParseException;
import java.util.*;

public class COVID19DAO {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private Set<Country> countries;
    private List<CovidData> covidData;

    public void openConnection() {
        factory = Persistence.createEntityManagerFactory("mysql_local");
        manager = factory.createEntityManager();
    }

    public void closeConnection() {
        manager.close();
        factory.close();
    }

    private void buildEntitySetCountry(CsvRead csvRead) {
        List<CsvBean> beans = csvRead.getBeanFromCSV();
        countries = new HashSet<>();
        for (CsvBean bean : beans) {
            CsvBeanOWID beanOwid = (CsvBeanOWID) bean;
            if(!beanOwid.getISO_code().isEmpty()) {
                Country country = new Country();
                country.set(beanOwid);
                this.countries.add(country);
            }

        }
    }

    private void buildEntityListCovidData(CsvRead csvRead) {
        List<CsvBean> beans = csvRead.getBeanFromCSV();
        covidData = new ArrayList<>();

        for (CsvBean bean : beans) {
            CsvBeanOWID beanOwid = (CsvBeanOWID) bean;
            if (!beanOwid.getISO_code().isEmpty() && !beanOwid.getTotal_cases().isEmpty()) {
                CovidData covidRecord = new CovidData();
                try {
                    covidRecord.set(beanOwid);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                for (Country country : this.countries) {
                    if (beanOwid.getISO_code().equals(country.getISO_code())) {
                        covidRecord.setCountries(country);
                        country.addRecordCovidData(covidRecord);
                    }
                }

                covidData.add(covidRecord);

            } else {
                continue;
            }
        }
    }

    public void buildDatabase(CsvRead csvRead) throws NoSuchFileException {
        buildEntitySetCountry(csvRead);
        buildEntityListCovidData(csvRead);

        manager.getTransaction().begin();
        for (Country country : this.countries) {
            manager.persist(country);
        }
        for (CovidData covidRecord : covidData) {
            manager.persist(covidRecord);
        }
        manager.getTransaction().commit();

    }

    public EntityManager getManager() {
        return manager;
    }

}
