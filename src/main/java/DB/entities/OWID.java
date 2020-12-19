package DB.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class OWID {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(name = "ISO_CODE", nullable = false)
    private Country country;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE", nullable = false)
    private Date date;

    @Column(name = "TOTAL CASES", nullable = false)
    private Integer total_cases;

    @Column(name = "DAILY NEW CASES")
    private Integer new_cases;

    @Column(name = "TOTAL DEATHS")
    private Integer total_deaths;

    @Column(name = "DAILY NEW DEATHS")
    private Integer new_deaths;

    @Column(name = "ICU PATIENTS")
    private Integer icu_patients;

    @Column(name = "HOSPITALIZED PATIENTS")
    private Integer hosp_patients;

    @Column(name = "TOTAL TESTS")
    private Integer total_tests;

    @Column(name = "DAILY NEW TESTS")
    private Integer new_tests;
}