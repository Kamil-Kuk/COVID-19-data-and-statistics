package main;

import DB.entities.Country;
import DB.entities.OWID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    private static LocalDate startLocalDate;
//    private static LocalDate endLocalDate;
    private static final Scanner INST_SCAN = new Scanner(System.in);
    private static final Scanner STRING_SCAN = new Scanner(System.in);
    private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysql_local");
    private static EntityManager manager = factory.createEntityManager();
    private static String countryIso;
    private static final Date DATE_NOW = new Date();

    private static Date startDate;
    private static Date endDate;

    public static void main(String[] args) throws ParseException {

        boolean format = true;
        boolean format2 = true;

        //===========================================temporary database========================================
//
        Date date20201101 = SIMPLE_DATE_FORMAT.parse("2020-11-01");
        Date date20201102 = SIMPLE_DATE_FORMAT.parse("2020-11-02");
        Date date20201103 = SIMPLE_DATE_FORMAT.parse("2020-11-03");
        Date date20201104 = SIMPLE_DATE_FORMAT.parse("2020-11-04");

        Country poland = new Country("POL", "Europe", "Poland", 38000000);
        Country usa = new Country("USA", "North America", "USA", 330000000);
        Country australia = new Country("AUS", "Australia", "Australia", 32000000);

        OWID pol20201101 = new OWID(poland, date20201101, 37990, 17171, 5783, 152,
                null, 152, 4585135, 48341);
        OWID pol20201102 = new OWID(poland, date20201102, 395480, 15578, 5875, 92,
                null, 17223, 4649236, 64101);
        OWID pol20201103 = new OWID(poland, date20201103, 414844, 19364, 6102, 227,
                null, 18160, 4712224, 62988);
        OWID pol20201104 = new OWID(poland, date20201104, 439536, 24692, 6475, 373,
                null, 18654, 4779914, 67690);
        OWID usa20201101 = new OWID(usa, date20201101, 9241521, 104327, 231623, 422,
                9665, 47615, 153426532, 877936);
        OWID usa20201102 = new OWID(usa, date20201102, 9324616, 83095, 232155, 532,
                9970, 48773, 154409790, 983258);
        OWID usa20201103 = new OWID(usa, date20201103, 9450988, 126372, 233720, 1565,
                10530, 50512, 155728586, 1318796);
        OWID usa20201104 = new OWID(usa, date20201104, 9554518, 103530, 234812, 1092,
                10892, 52166, 157298430, 1569844);
        OWID australia20201101 = new OWID(australia, date20201101, 27601, 6, 907, 0,
                null, null, 8825186, null);
        OWID australia20201102 = new OWID(australia, date20201102, 27610, 9, 907, 0,
                null, null, 8855401, 30215);
        OWID australia20201103 = new OWID(australia, date20201103, 27622, 12, 907, 0,
                null, null, 8887171, 31770);
        OWID australia20201104 = new OWID(australia, date20201104, 27630, 8, 907, 0,
                null, null, 8933563, 46392);


        manager.getTransaction().begin();
        manager.persist(poland);
        manager.persist(usa);
        manager.persist(australia);
        manager.persist(pol20201101);
        manager.persist(pol20201102);
        manager.persist(pol20201103);
        manager.persist(pol20201104);
        manager.persist(usa20201101);
        manager.persist(usa20201102);
        manager.persist(usa20201103);
        manager.persist(usa20201104);
        manager.persist(australia20201101);
        manager.persist(australia20201102);
        manager.persist(australia20201103);
        manager.persist(australia20201104);
        manager.getTransaction().commit();


        //===========================================temporary database========================================


        System.out.println("COVID-19: DATA AND STATISTIC");
        System.out.println("Select Country (name by ISO CODE eg. POL for Poland) or press Q to end program");
        countryIso = STRING_SCAN.next();
        if (countryIso.equalsIgnoreCase("q")) return;
        else {

            System.out.println("Select range of time. Available options:");
            System.out.println("1. From the beginning record till now");
            System.out.println("2. From the beginning record to selected date");
            System.out.println("3. From selected date till now");
            System.out.println("4. From selected date to selected date");
            System.out.println("5. For single date");

            int i = INST_SCAN.nextInt();

            switch (i) {
                case 1:
                    System.out.println("Available data for: " + countryIso);
                    startDate = SIMPLE_DATE_FORMAT.parse("2020-11-01");
                    endDate = DATE_NOW;
                    //  endDate = LocalDate.now();
                    availableOptions();
                    break;
                case 2:
                    while (format)
                        format = false;
                    try {
                        System.out.println("Select date: (yyyy-MM-dd)");
                        startDate = SIMPLE_DATE_FORMAT.parse("2019-11-01");
                        endDate = readDate(STRING_SCAN);
                        availableOptions();
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Wrong date format (yyyy-MM-dd). Try again.");
                        format = true;
                    }
                case 3:
                    while (format)
                        format = false;
                    try {
                        System.out.println("Select date: (yyyy-MM-dd)");
                        startDate = readDate(STRING_SCAN);
                        endDate = DATE_NOW;
                        availableOptions();
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Wrong date format (yyyy-MM-dd). Try again.");
                        format = true;
                    }
                case 4:
                    while (format)
                        format = false;
                    try {
                        System.out.println("Select start date: (yyyy-MM-dd)");
                        startDate = readDate(STRING_SCAN);
                        System.out.println("Select end date: (yyyy-MM-dd)");
                        endDate = readDate(STRING_SCAN);
                        availableOptions();
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Wrong date format (yyyy-MM-dd). Try again.");
                        format = true;
                    }
                case 5:
                    while (format)
                        format = false;
                    try {
                        System.out.println("Select date: (yyyy-MM-dd)");
                        startDate = readDate(STRING_SCAN);
                        endDate = startDate;
                        availableOptions();
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Wrong date format (yyyy-MM-dd). Try again.");
                        format = true;
                    }
                case 6:
                    selectTotalCases();
                    break;
                default:
                    System.out.println("No such option like " + i + ". Try again.");
                    break;
            }
        }

        factory.close();
    }


//    private static LocalDate readLocalDate(Scanner scan) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        return LocalDate.parse(scan.next(), formatter);
//    }

    private static Date readDate(Scanner scan) throws ParseException {
        return SIMPLE_DATE_FORMAT.parse(scan.next());
    }


    private static void availableOptions() {
        System.out.println("1.Total cases\n2.Daily new cases\n3.Total deaths\n4.Daily new deaths\n5.ICU Patients" +
                "\n6.Hospitalized Patients+\n7.Total_tests\n8.Daily new tests\n9.Get all data");


        int i = INST_SCAN.nextInt();
        switch (i) {
            case 1:
                selectTotalCases();
                break;
            case 2:
                selectDailyNewCases();
                break;
            case 3:
                selectTotalDeaths();
                break;
            case 4:
                selectDailyNewDeaths();
                break;
            case 5:
                selectIcuPatients();
                break;
            case 6:
                selectHospitalizedPatients();
                break;
            case 7:
                selectTotalTests();
                break;
            case 8:
                selectDailyNewTests();
            case 9:
                selectAllData();
                break;
        }
    }

    public static void selectTotalCases() {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM OWID o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", OWID.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);
//        OWID owid = (OWID) q.getSingleResult();
//        System.out.print("Date: " + owid.getDate());
//        System.out.print("  iso_code: " + owid.getCountry());
//        System.out.println("  total_cases: " + owid.getTotal_cases());

        List<OWID> owids = q.getResultList();
        for (OWID owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | total_cases: " + owid.getTotal_cases() + " |");
        }
        manager.close();
    }

    public static void selectDailyNewCases() {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM OWID o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", OWID.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<OWID> owids = q.getResultList();
        for (OWID owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | daily_new_cases: " + owid.getNew_cases() + " |");
        }
        manager.close();
    }

    public static void selectTotalDeaths() {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM OWID o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", OWID.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<OWID> owids = q.getResultList();
        for (OWID owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | total_deaths: " + owid.getTotal_deaths() + " |");
        }
        manager.close();
    }

    public static void selectDailyNewDeaths() {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM OWID o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", OWID.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<OWID> owids = q.getResultList();
        for (OWID owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | daily_new_deaths: " + owid.getNew_deaths() + " |");
        }
        manager.close();
    }

    public static void selectIcuPatients() {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM OWID o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", OWID.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<OWID> owids = q.getResultList();
        for (OWID owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | ICU Patients: " + owid.getIcu_patients() + " |");
        }
        manager.close();
    }

    public static void selectHospitalizedPatients() {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM OWID o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", OWID.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<OWID> owids = q.getResultList();
        for (OWID owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | Hospitalized Patients: " + owid.getHosp_patients() + " |");
        }
        manager.close();
    }

    public static void selectTotalTests() {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM OWID o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", OWID.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<OWID> owids = q.getResultList();
        for (OWID owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | Total tests: " + owid.getTotal_tests() + " |");
        }
        manager.close();
    }

    public static void selectDailyNewTests() {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM OWID o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", OWID.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<OWID> owids = q.getResultList();
        for (OWID owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | Daily new tests: " + owid.getNew_tests() + " |");
        }
        manager.close();
    }

    public static void selectAllData() {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM OWID o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", OWID.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<OWID> owids = q.getResultList();
        for (OWID owid : owids) {
            System.out.printf("| Date: %s | Country: %s | Total Cases: %d | Daily New Cases: %d |\n" +
                            "Total deaths: %d | Daily new deaths: %d | ICU patients: %d |\n" +
                            "Hospitalized patients: %d | Total Tests: %d | Daily new tests: %d |\n\n", owid.getDate(), owid.getCountry(),
                    owid.getTotal_cases(), owid.getNew_cases(), owid.getTotal_deaths(), owid.getNew_deaths(), owid.getIcu_patients(),
                    owid.getHosp_patients(), owid.getTotal_tests(), owid.getNew_tests());
        }
        manager.close();
    }

}
