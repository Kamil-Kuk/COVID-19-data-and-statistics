package main.CsvRead;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@NoArgsConstructor
public class CsvBeanOWID implements CsvBean{

    private final String FILE_NAME = "src/main/resources/csv/owid-covid-data.csv";
    private final Path MY_PATH = Paths.get(FILE_NAME);

    @Override
    public Path getMY_PATH() {
        return MY_PATH;
    }

    @CsvBindByName
    private String  ISO_Code,
                    Continent,
                    Location,
                    Date,
                    Total_Cases;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CsvBeanOWID that = (CsvBeanOWID) o;
        return Objects.equals(getISO_Code(), that.getISO_Code()) &&
                Objects.equals(getContinent(), that.getContinent()) &&
                Objects.equals(getLocation(), that.getLocation()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getTotal_Cases(), that.getTotal_Cases());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getISO_Code(), getContinent(), getLocation(), getDate(), getTotal_Cases());
    }

    @Override
    public String toString() {
        return "CsvBeanOWID{" +
                "ISO_Code='" + ISO_Code + '\'' +
                ", Continent='" + Continent + '\'' +
                ", Location='" + Location + '\'' +
                ", Date='" + getDate() + '\'' +
                ", Total_Cases='" + getTotal_Cases() + '\'' +
                '}';


    }
}
