package DB.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(exclude = "country")
//@ToString(exclude = "country")
@Entity
public class CovidData {

  public CovidData(Country country, Date date, Integer total_cases, Integer new_cases, Integer total_deaths, Integer new_deaths, Integer icu_patients, Integer hosp_patients, Integer total_tests, Integer new_tests) {
    this.country = country;
    this.date = date;
    this.total_cases = total_cases;
    this.new_cases = new_cases;
    this.total_deaths = total_deaths;
    this.new_deaths = new_deaths;
    this.icu_patients = icu_patients;
    this.hosp_patients = hosp_patients;
    this.total_tests = total_tests;
    this.new_tests = new_tests;
  }

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  //  @Column(name = "ISO_CODE", nullable = false)
    @ManyToOne
    @JoinColumn(name="ISO_CODE", nullable =false)
    private Country country;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE", nullable = false)
    private Date date;

    @Column(name = "TOTAL_CASES", nullable = false)
    private Integer total_cases;

    @Column(name = "DAILY_NEW_CASES")
    private Integer new_cases;

    @Column(name = "TOTAL_DEATHS")
    private Integer total_deaths;

    @Column(name = "DAILY_NEW_DEATHS")
    private Integer new_deaths;

    @Column(name = "ICU_PATIENTS")
    private Integer icu_patients;

    @Column(name = "HOSPITALIZED_PATIENTS")
    private Integer hosp_patients;

    @Column(name = "TOTAL_TESTS")
    private Integer total_tests;

    @Column(name = "DAILY_NEW_TESTS")
    private Integer new_tests;
}