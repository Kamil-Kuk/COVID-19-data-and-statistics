package DB.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import main.CsvRead.CsvBeanOWID;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class Country {

    @Id
    @Column(name = "ISO_CODE")
    private String ISO_code;

    @Column(name = "CONTINENT")
    private String continent;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POPULATION")
    private Integer population;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<OWID> recordsOWID = new HashSet<>();

    public Country() {
    }

    public void setISO_code(String ISO_code) {
        this.ISO_code = ISO_code;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    private void setISO_codeFromBean(CsvBeanOWID bean) {
        if(!bean.getISO_code().isEmpty()) setISO_code(bean.getISO_code());
    }

    private void setContinentFromBean(CsvBeanOWID bean) {
        if(!bean.getContinent().isEmpty()) setContinent(bean.getContinent());
    }

    private void setNameFromBean(CsvBeanOWID bean) {
        if(!bean.getLocation().isEmpty()) setName(bean.getLocation());
    }

    private void setPopulationFromBean(CsvBeanOWID bean) {
        if(!bean.getPopulation().isEmpty()) {
            Double populationDouble = Double.parseDouble(bean.getPopulation());
            setPopulation(populationDouble.intValue());
        }
    }

    public void set(CsvBeanOWID bean){
        setISO_codeFromBean(bean);
        setContinentFromBean(bean);
        setNameFromBean(bean);
        setPopulationFromBean(bean);
    }

    public void addRecordOWID(OWID record) { this.recordsOWID.add(record); }

    public void setRecordsOWID(Set<OWID> recordsOWID) {
        this.recordsOWID = recordsOWID;
    }
}
