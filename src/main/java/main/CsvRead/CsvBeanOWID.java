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
    private String ISO_Code,
            Continent,
            Location,
            Date,
            Total_Cases,
            New_Cases,
            Total_Deaths,
            New_Deaths,
            Total_Cases_Per_Million,
            New_Cases_Per_Million,
            Total_Deaths_Per_Million,
            New_Deaths_Per_Million,
            Icu_Patients,
            Icu_Patients_Per_Million,
            Hosp_Patients,
            Hosp_Patients_Per_Million,
            Total_Tests,
            New_Tests;
}
