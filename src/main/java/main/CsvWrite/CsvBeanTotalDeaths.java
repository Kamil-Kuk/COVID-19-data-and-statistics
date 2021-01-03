package main.CsvWrite;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CsvBeanTotalDeaths implements CsvBean{

    private LocalDate date;
    private Integer total_deaths;
}
