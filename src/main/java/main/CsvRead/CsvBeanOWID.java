package main.CsvRead;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class CsvBeanOWID implements CsvBean {

    @ToString.Exclude
    private final String FILE_NAME = "src/main/resources/csv/owid-covid-data.csv";

    @ToString.Exclude
    private final Path MY_PATH = Paths.get(FILE_NAME);

    @Override
    public Path getMY_PATH() {
        return MY_PATH;
    }

    @CsvBindByName
    private String ISO_code,
            continent,
            location,
            date,
            total_cases,
            new_cases,
            total_deaths,
            new_deaths,
            icu_patients,
            hosp_patients,
            total_tests,
            new_tests,
            population;
}
