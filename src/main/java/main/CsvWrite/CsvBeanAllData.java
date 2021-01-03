package main.CsvWrite;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CsvBeanAllData implements CsvBean{

    private String ISO_code;
    private LocalDate date;
    private Integer total_cases;
    private Integer new_cases;
    private Integer total_deaths;
    private Integer new_deaths;
    private Integer icu_patients;
    private Integer hosp_patients;
    private Integer total_tests;
    private Integer new_tests;

}
