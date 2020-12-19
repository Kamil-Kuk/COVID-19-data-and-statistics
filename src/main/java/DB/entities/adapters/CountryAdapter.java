package DB.entities.adapters;

import DB.entities.Country;
import main.CsvRead.CsvBean;
import main.CsvRead.CsvBeanOWID;

public class CountryAdapter extends Country {
    private CsvBeanOWID bean;

    public CountryAdapter(CsvBeanOWID bean) {
        this.bean = bean;
    }

    public void setISO_code() {
        if(!bean.getISO_code().isEmpty()) setISO_code(bean.getISO_code());
    }

    public void setContinent() {
        if(!bean.getContinent().isEmpty()) setContinent(bean.getContinent());
    }

    public void setName() {
        if(!bean.getLocation().isEmpty()) setName(bean.getLocation());
    }

    public void setPopulation() {
        if(!bean.getPopulation().isEmpty()) {
            Double populationDouble = Double.parseDouble(bean.getPopulation());
            setPopulation(populationDouble.intValue());
        }
    }
}
