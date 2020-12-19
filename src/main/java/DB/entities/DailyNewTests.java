package DB.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DailyNewTests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(name = "ISO_CODE", nullable = false)
    private Country country;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE", nullable = false)
    private Date date;

    @Column(name = "DAILY NEW TESTS")
    private Integer new_tests;
}
