package main;

import DB.entities.Country;
import DB.entities.OWID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private static final Scanner INST_SCAN = new Scanner(System.in);
    private static final Scanner STRING_SCAN = new Scanner(System.in);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysql_local");
    private static EntityManager manager = factory.createEntityManager();

    public static void main(String[] args) throws ParseException {
        UserInterface UI = new UserInterface();
        LocalDate startDate;
        LocalDate endDate;
        boolean format = true;
        boolean format2 = true;

        //===========================================temporary database========================================
//
        Date date20201101 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-01");
        Date date20201102 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-02");
        Date date20201103 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-03");
        Date date20201104 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-04");




        Country poland=new Country("POL","Europe","Poland",38000000);
        Country usa=new Country("USA","North America","USA",330000000);
        Country australia=new Country("AUS","Australia","Australia",32000000);

        OWID pol20201101=new OWID(poland,date20201101,37990,17171,5783,152,
                null,152,4585135,48341);
        OWID pol20201102=new OWID(poland,date20201102,395480,15578,5875,92,
                null,17223,4649236,64101);
        OWID pol20201103=new OWID(poland,date20201103,414844,19364,6102,227,
                null,18160,4712224,62988);
        OWID pol20201104=new OWID(poland,date20201104,439536,24692,6475,373,
                null,18654,4779914,67690);
        OWID usa20201101=new OWID(usa,date20201101,9241521,104327,231623,422,
                9665,47615,153426532,877936);
        OWID usa20201102=new OWID(usa,date20201102,9324616,83095,232155,532,
                9970, 48773,154409790,983258);
        OWID usa20201103=new OWID(usa,date20201103,9450988,126372,233720,1565,
                10530,50512,155728586,1318796);
        OWID usa20201104=new OWID(usa,date20201104,9554518,103530,234812,1092,
                10892, 52166,157298430,1569844);
        OWID australia20201101=new OWID(australia,date20201101,27601,6,907,0,
                null,null,8825186,null);
        OWID australia20201102=new OWID(australia,date20201102,27610,9,907,0,
                null,null,8855401,30215);
        OWID australia20201103=new OWID(australia,date20201103,27622,12,907,0,
                null,null,8887171,31770);
        OWID australia20201104=new OWID(australia,date20201104,27630,8,907,0,
                null,null,8933563,46392);


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
        String countryIso = STRING_SCAN.next();
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
                    availableOptions();
                    break;
                case 2:
                    while (format)
                        format = false;
                    try {
                        System.out.println("Select date: (yyyy-MM-dd)");
                        startDate = LocalDate.parse("2019-11-01", FORMATTER);
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
                        endDate = LocalDate.now();
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
                        availableOptions();
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Wrong date format (yyyy-MM-dd). Try again.");
                        format = true;
                    }
                case 6:
                    UI.selectTotalCases();
                    break;
                default:
                    System.out.println("No such option like " + i + ". Try again.");
                    break;
            }
        }
    }


    private static LocalDate readDate(Scanner scan) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(scan.next(), formatter);
    }


    private static void availableOptions() {
        System.out.println("1.Total cases\n2.New cases\n3.Total deaths\n4.New deaths\n5.total_cases_per_million" +
                "\n6.new_cases_per_million\n7.total_deaths_per_million\n8.new_deaths_per_million" +
                "\n9.icu_patients\n10.icu_patients_per_million\n11.hosp_patients\n12.hosp_patients_per_million" +
                "\n13.total_tests\n14.new_tests\n15.get all data");


//        int i = INST_SCAN.nextInt();
//        switch (i) {
//            case 1:
//            case 2:
//            case 3:
//            case 4:
//            case 5:
//            case 6:
//            case 7:
//            case 8:
//            case 9:
//            case 10:
//            case 11:
//            case 12:
//            case 13:
//            case 14:
//        }
    }

    public void selectTotalCases() {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT o.date, o.iso_code, o.total_cases FROM OWID o WHERE o.ISO_CODE='POL' AND o.date='2020-11-01'");
        //   q.setParameter(1, "'POL'");
//        OWID owid = (OWID) q.getSingleResult();
//        System.out.print("Date: " + owid.getDate());
//        System.out.print("  iso_code: " + owid.getCountry());
//        System.out.println("  total_cases: " + owid.getTotal_cases());

        List<OWID> owids = q.getResultList();
        for (OWID owid:owids) {
            System.out.print("Date: " + owid.getDate());
            System.out.print("  iso_code: " + owid.getCountry());
            System.out.println("  total_cases: " + owid.getTotal_cases());
        }
        manager.close();
        factory.close();

    }
}
