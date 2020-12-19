package DB.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Country {

    //@Id
    @Column(name = "ISO_CODE")
    protected String ISO_code;

    @Column(name = "CONTINENT")
    protected String continent;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "POPULATION")
    protected Integer population;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<OWID> recordsOWID = new HashSet<>();

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

    public void addRecordOWID(OWID record) { this.recordsOWID.add(record); }

    public void setRecordsOWID(Set<OWID> recordsOWID) {
        this.recordsOWID = recordsOWID;
    }
}
