package DB.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Country {

    public Country(String ISO_code, String continent, String name, Integer population) {
        this.ISO_code = ISO_code;
        this.continent = continent;
        this.name = name;
        this.population = population;
    }

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

    public void addRecordOWID(OWID record) { this.recordsOWID.add(record); }

    public void setRecordsOWID(Set<OWID> recordsOWID) {
        this.recordsOWID = recordsOWID;
    }
}