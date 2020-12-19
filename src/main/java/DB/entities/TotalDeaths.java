package DB.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TotalDeaths {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(name = "ISO_CODE", nullable = false)
    private Country country;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE", nullable = false)
    private Date date;

    @Column(name = "TOTAL DEATHS")
    private Integer total_deaths;

}
