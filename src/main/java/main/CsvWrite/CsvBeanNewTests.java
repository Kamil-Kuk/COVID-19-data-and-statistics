package main.CsvWrite;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CsvBeanNewTests implements CsvBean{

    private LocalDate date;
    private Integer new_tests;
}
