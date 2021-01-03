package main.CsvWrite;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CsvBeanHospPatients implements CsvBean{

    private LocalDate date;
    private Integer hosp_patients;
}
