package main.CsvWrite;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CsvBeanTotalTests implements CsvBean {

    private LocalDate date;
    private Integer total_tests;

}
