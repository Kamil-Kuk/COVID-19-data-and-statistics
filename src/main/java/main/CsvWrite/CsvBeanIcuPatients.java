package main.CsvWrite;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CsvBeanIcuPatients implements CsvBean{

    private LocalDate date;
    private Integer icu_patients;
}
