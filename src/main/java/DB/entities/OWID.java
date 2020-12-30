package DB.entities;

import lombok.*;
import main.CsvRead.CsvBeanOWID;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
public class OWID {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ISO_CODE")
    private Country country;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE")
    private Date date;

    @Column(name = "TOTAL_CASES")
    private Integer total_cases;

    @Column(name = "DAILY_NEW_CASES")
    private Integer new_cases;

    @Column(name = "TOTAL_DEATHS")
    private Integer total_deaths;

    @Column(name = "DAILY_NEW_DEATHS")
    private Integer new_deaths;

    @Column(name = "ICU_PATIENTS")
    private Integer icu_patients;

    @Column(name = "HOSPITALIZED_PATIENTS")
    private Integer hosp_patients;

    @Column(name = "TOTAL_TESTS")
    private Integer total_tests;

    @Column(name = "DAILY_NEW_TESTS")
    private Integer new_tests;

    public OWID() {
    }


    public void setCountry(Country country) {
        this.country = country;
    }

    private void setDate(Date date) {
        this.date = date;
    }

    private void setTotal_cases(Integer total_cases) {
        this.total_cases = total_cases;
    }

    private void setNew_cases(Integer new_cases) {
        this.new_cases = new_cases;
    }

    private void setTotal_deaths(Integer total_deaths) {
        this.total_deaths = total_deaths;
    }

    private void setNew_deaths(Integer new_deaths) {
        this.new_deaths = new_deaths;
    }

    private void setIcu_patients(Integer icu_patients) {
        this.icu_patients = icu_patients;
    }

    private void setHosp_patients(Integer hosp_patients) {
        this.hosp_patients = hosp_patients;
    }

    private void setTotal_tests(Integer total_tests) {
        this.total_tests = total_tests;
    }

    private void setNew_tests(Integer new_tests) {
        this.new_tests = new_tests;
    }

    private void setDateFromBean(CsvBeanOWID bean) throws ParseException {
        if (!bean.getDate().isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(bean.getDate());
            setDate(date);
        }
    }

    private void setTotal_CasesFromBean(CsvBeanOWID bean) {
        if (!bean.getTotal_cases().isEmpty()) {
            Double total_CasesDouble = Double.parseDouble(bean.getTotal_cases());
            setTotal_cases(total_CasesDouble.intValue());
        }
    }

    private void setNew_CasesFromBean(CsvBeanOWID bean) {
        if (!bean.getNew_cases().isEmpty()) {
            Double new_CasesDouble = Double.parseDouble(bean.getNew_cases());
            setNew_cases(new_CasesDouble.intValue());
        }
    }

    private void setTotal_DeathsFromBean(CsvBeanOWID bean) {
        if (!bean.getTotal_deaths().isEmpty()) {
            Double total_DeathsDouble = Double.parseDouble(bean.getTotal_deaths());
            setTotal_deaths(total_DeathsDouble.intValue());
        }
    }

    private void setNew_DeathsFromBean(CsvBeanOWID bean) {
        if (!bean.getNew_deaths().isEmpty()) {
            Double new_DeathsDouble = Double.parseDouble(bean.getNew_deaths());
            setNew_deaths(new_DeathsDouble.intValue());
        }
    }

    private void setIcu_PatientsFromBean(CsvBeanOWID bean) {
        if (!bean.getIcu_patients().isEmpty()) {
            Double icu_PatientsDouble = Double.parseDouble(bean.getIcu_patients());
            setIcu_patients(icu_PatientsDouble.intValue());
        }
    }

    private void setHosp_PatientsFromBean(CsvBeanOWID bean) {
        if (!bean.getHosp_patients().isEmpty()) {
            Double hosp_PatientsDouble = Double.parseDouble(bean.getHosp_patients());
            setHosp_patients(hosp_PatientsDouble.intValue());
        }
    }

    private void setTotal_TestsFromBean(CsvBeanOWID bean) {
        if (!bean.getTotal_tests().isEmpty()) {
            Double total_TestsDouble = Double.parseDouble(bean.getTotal_tests());
            setTotal_tests(total_TestsDouble.intValue());
        }
    }

    private void setNew_TestsFromBean(CsvBeanOWID bean) {
        if (!bean.getNew_tests().isEmpty()) {
            Double new_TestsDouble = Double.parseDouble(bean.getNew_tests());
            setNew_tests(new_TestsDouble.intValue());
        }
    }

    public void set(CsvBeanOWID bean) throws ParseException {
        setDateFromBean(bean);
        setTotal_CasesFromBean(bean);
        setNew_CasesFromBean(bean);
        setTotal_DeathsFromBean(bean);
        setNew_DeathsFromBean(bean);
        setIcu_PatientsFromBean(bean);
        setHosp_PatientsFromBean(bean);
        setTotal_TestsFromBean(bean);
        setNew_TestsFromBean(bean);
    }

}
