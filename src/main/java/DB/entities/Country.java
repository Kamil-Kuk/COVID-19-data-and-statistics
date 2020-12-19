package DB.entities;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
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

    public void addRecordOWID(OWID record) { this.recordsOWID.add(record); }

    public void setRecordsOWID(Set<OWID> recordsOWID) {
        this.recordsOWID = recordsOWID;
    }
}