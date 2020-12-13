package main.CsvRead;

import com.opencsv.bean.CsvBindByName;
import lombok.NoArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@NoArgsConstructor
public class CsvBeanOWID extends CsvBean{

    private final String fileName = "src/main/resources/csv/owid-covid-data.csv";
    private final Path myPath = Paths.get(fileName);

    @Override
    public Path getMyPath() {
        return myPath;
    }

    @CsvBindByName
    private String ISO_Code;

    @CsvBindByName
    private String Continent;

    @CsvBindByName
    private String Location;

    @CsvBindByName
    private String Date;

    @CsvBindByName
    private String Total_Cases;

//    @CsvBindByName
//    private String Total_Cases_Per_Million;
//
//    @CsvBindByName
//    private String Total_Cases_Per_Million;
//
//    @CsvBindByName
//    private String Total_Cases_Per_Million;

    public String getISO_Code() {
        return ISO_Code;
    }

    public void setISO_Code(String ISO_Code) {
        this.ISO_Code = ISO_Code;
    }

    public String getContinent() {
        return Continent;
    }

    public void setContinent(String continent) {
        Continent = continent;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public LocalDate getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        return LocalDate.parse(Date, formatter);
    }

    public void setDate(String date) {
        Date = date;
    }

    public long getTotal_Cases() {
        if(!Total_Cases.isEmpty()) {
            return (long)Double.parseDouble(Total_Cases);
        } else {
            return 0;
        }
    }

    public void setTotal_Cases(String total_Cases) {
        Total_Cases = total_Cases;
    }

    @Override
    public String toString() {
        return "CsvBeanOWID{" +
                "ISO_Code='" + ISO_Code + '\'' +
                ", Continent='" + Continent + '\'' +
                ", Location='" + Location + '\'' +
                ", Date='" + getDate() + '\'' +
                ", Total_Cases=" + getTotal_Cases() +
                "} ";
    }
}
