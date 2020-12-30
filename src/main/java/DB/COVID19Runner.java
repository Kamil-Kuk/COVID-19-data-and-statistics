package DB;

import DB.entities.Country;
import DB.entities.adapters.CountryAdapter;
import main.CsvRead.CsvBean;
import main.CsvRead.CsvBeanOWID;
import main.CsvRead.CsvRead;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Iterator;
import java.util.List;

public class COVID19Runner {
    public static void main(String[] args) {

        COVID19DAOImpl<Country> daoCountries = new COVID19DAOImpl<>();
        daoCountries.openConnection();

        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
        List<CsvBeanOWID> result = csvRead.getBeanFromCSV();

        CsvBeanOWID bean = new CsvBeanOWID();
        bean.setISO_code("POL");
        bean.setContinent("Europe");
        bean.setLocation("Poland");
        bean.setPopulation("38000000.0");

        Country entity = new Country();
        entity.set(bean);
        System.out.println(entity.getClass());

        daoCountries.save(entity);
//        CountryAdapter countryEntry =  new CountryAdapter((CsvBeanOWID) beans.get(0));
//        countryEntry.setISO_code();
//        daoCountries.save(countryEntry);

//        for(CsvBean bean : beans) {
//            countryEntry = new CountryAdapter((CsvBeanOWID) bean);
//            countryEntry.setISO_code();
//            countryEntry.setContinent();
//            countryEntry.setName();
//            countryEntry.setPopulation();
//            daoCountries.save(countryEntry);
//        }

        daoCountries.closeConnection();

//        CsvBeanOWID bean = new CsvBeanOWID();
//        CountryAdapter entity1 = new CountryAdapter(bean);
//        bean.setISO_code("");
//        entity1.setISO_code();
//        System.out.println(entity1.getISO_code());
    }
}
