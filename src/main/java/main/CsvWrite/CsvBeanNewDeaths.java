package main.CsvWrite;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CsvBeanNewDeaths implements CsvBean{

    private LocalDate date;
    private Integer new_deaths;
}
