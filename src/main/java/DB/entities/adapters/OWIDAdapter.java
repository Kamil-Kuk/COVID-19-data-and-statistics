package DB.entities.adapters;

import DB.entities.Country;
import DB.entities.OWID;
import main.CsvRead.CsvBeanOWID;

import java.util.Date;

public class OWIDAdapter extends OWID {
    private CsvBeanOWID bean;

    public OWIDAdapter(CsvBeanOWID bean) {
        this.bean = bean;
    }

    @Override
    public void setCountry(Country country) {
        super.setCountry(country);
    }

    @Override
    public void setDate(Date date) {
        super.setDate(date);
    }

    @Override
    public void setTotal_cases(Integer total_cases) {
        super.setTotal_cases(total_cases);
    }

    @Override
    public void setNew_cases(Integer new_cases) {
        super.setNew_cases(new_cases);
    }

    @Override
    public void setTotal_deaths(Integer total_deaths) {
        super.setTotal_deaths(total_deaths);
    }

    @Override
    public void setNew_deaths(Integer new_deaths) {
        super.setNew_deaths(new_deaths);
    }

    @Override
    public void setIcu_patients(Integer icu_patients) {
        super.setIcu_patients(icu_patients);
    }

    @Override
    public void setHosp_patients(Integer hosp_patients) {
        super.setHosp_patients(hosp_patients);
    }

    @Override
    public void setTotal_tests(Integer total_tests) {
        super.setTotal_tests(total_tests);
    }

    @Override
    public void setNew_tests(Integer new_tests) {
        super.setNew_tests(new_tests);
    }
}
