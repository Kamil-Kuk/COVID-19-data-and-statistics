package main.CsvWrite;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CsvBeanTotalCases implements CsvBean{
    private LocalDate date;
    private Integer total_cases;
}
