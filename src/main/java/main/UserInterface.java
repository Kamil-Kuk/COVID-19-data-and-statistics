package main;

import DB.COVID19DAO;
import DB.entities.Country;
import DB.entities.CovidData;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import main.CsvRead.CsvBeanOWID;
import main.CsvRead.CsvRead;
import main.CsvWrite.*;
import main.downloader.Downloader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

public class UserInterface {

    private static final Scanner INT_SCAN = new Scanner(System.in);
    private static final Scanner STRING_SCAN = new Scanner(System.in);
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static COVID19DAO dao = new COVID19DAO();
    //    private static EntityManager manager = dao.getManager();
//    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("mysql_local");
//    private static final EntityManager manager = FACTORY.createEntityManager();
    private static final Scanner EXPORT_SCAN = new Scanner(System.in);
    private static String countryIso;
    private static final Date DATE_NOW = new Date();

    private static Date startDate;
    private static Date endDate;
    private static boolean errorFlag;
    private static boolean dateFormatFlag;
    private static boolean exportFlag;
    private static boolean answerYNFlag;

    public static void main(String[] args) throws ParseException, NullPointerException {

        //===========================================construction of database========================================

//        CsvBeanOWID bean = new CsvBeanOWID();
//        CsvRead csvRead = new CsvRead(bean);
//        COVID19DAO dao = new COVID19DAO();
        dao.openConnection();
        EntityManager manager = dao.getManager();
//        dao.buildDatabase(csvRead);


        //===========================================construction of database========================================


        //===========================================temporary database========================================

//        Date date20201101 = SIMPLE_DATE_FORMAT.parse("2020-11-01");
//        Date date20201102 = SIMPLE_DATE_FORMAT.parse("2020-11-02");
//        Date date20201103 = SIMPLE_DATE_FORMAT.parse("2020-11-03");
//        Date date20201104 = SIMPLE_DATE_FORMAT.parse("2020-11-04");
//
//        Country poland = new Country("POL", "Europe", "Poland", 38000000);
//        Country usa = new Country("USA", "North America", "USA", 330000000);
//        Country australia = new Country("AUS", "Australia", "Australia", 32000000);
//
//        CovidData pol20201101 = new CovidData(poland, date20201101, 37990, 17171, 5783, 152,
//                666, 152, 4585135, 48341);
//        CovidData pol20201102 = new CovidData(poland, date20201102, 395480, 15578, 5875, 92,
//                666, 17223, 4649236, 64101);
//        CovidData pol20201103 = new CovidData(poland, date20201103, 414844, 19364, 6102, 227,
//                666, 18160, 4712224, 62988);
//        CovidData pol20201104 = new CovidData(poland, date20201104, 439536, 24692, 6475, 373,
//                666, 18654, 4779914, 67690);
//        CovidData usa20201101 = new CovidData(usa, date20201101, 9241521, 104327, 231623, 422,
//                9665, 47615, 153426532, 877936);
//        CovidData usa20201102 = new CovidData(usa, date20201102, 9324616, 83095, 232155, 532,
//                9970, 48773, 154409790, 983258);
//        CovidData usa20201103 = new CovidData(usa, date20201103, 9450988, 126372, 233720, 1565,
//                10530, 50512, 155728586, 1318796);
//        CovidData usa20201104 = new CovidData(usa, date20201104, 9554518, 103530, 234812, 1092,
//                10892, 52166, 157298430, 1569844);
//        CovidData australia20201101 = new CovidData(australia, date20201101, 27601, 6, 907, 0,
//                666, 666, 8825186, 666);
//        CovidData australia20201102 = new CovidData(australia, date20201102, 27610, 9, 907, 0,
//                666, 666, 8855401, 30215);
//        CovidData australia20201103 = new CovidData(australia, date20201103, 27622, 12, 907, 0,
//                666, 666, 8887171, 31770);
//        CovidData australia20201104 = new CovidData(australia, date20201104, 27630, 8, 907, 0,
//                666, 666, 8933563, 46392);
//
//
//        manager.getTransaction().begin();
//        manager.persist(poland);
//        manager.persist(usa);
//        manager.persist(australia);
//        manager.persist(pol20201101);
//        manager.persist(pol20201102);
//        manager.persist(pol20201103);
//        manager.persist(pol20201104);
//        manager.persist(usa20201101);
//        manager.persist(usa20201102);
//        manager.persist(usa20201103);
//        manager.persist(usa20201104);
//        manager.persist(australia20201101);
//        manager.persist(australia20201102);
//        manager.persist(australia20201103);
//        manager.persist(australia20201104);
//        manager.getTransaction().commit();
//

        //===========================================temporary database========================================
        initialMethod(manager);
        dao.closeConnection();
    }


    private static void initialMethod(EntityManager manager) {

        System.out.println("COVID-19: DATA AND STATISTIC");
        CsvBeanOWID bean = new CsvBeanOWID();
        CsvRead csvRead = new CsvRead(bean);
        do {
            System.out.println("Do you want to download/update csv file from OWID database? (Y/N)");
            String answerYN = STRING_SCAN.next();
            if (answerYN.equalsIgnoreCase("y")) {
                answerYNFlag = false;
                try {
                    Downloader.getFile();
                    dao.buildDatabase(csvRead);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (answerYN.equalsIgnoreCase("n")) {
                try {
                    answerYNFlag = false;
                    dao.buildDatabase(csvRead);
                } catch (NoSuchFileException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Wrong input format. Try again.");
                answerYNFlag = true;
            }
        }
        while (answerYNFlag);

        answerYNFlag=true;
        do {
            System.out.println("Select Country (name by ISO CODE eg. POL for Poland). Press H for list of ISO_CODES" +
                    " or press Q to end program.");
            // dodać zapytanie o tworzenie i aktualizację bazy danych


            countryIso = STRING_SCAN.next();
            if (countryIso.equalsIgnoreCase("q")){
                answerYNFlag=false;
                System.exit(0);
            }
            if (countryIso.equalsIgnoreCase("h")) {
                answerYNFlag=true;
                displayAvailableCountries(manager);
                STRING_SCAN.reset();
            } else {
                answerYNFlag=false;
                selectStartDate(INT_SCAN);
                selectEndDate(INT_SCAN);
                availableOptions(INT_SCAN, manager);
            }
        }
        while (answerYNFlag);
    }


    private static void availableOptions(Scanner scan, EntityManager manager) {
        System.out.println("1.Total number of cases\n2.Daily number of new cases\n3.Total deaths due COVID-19\n" +
                "4.Daily number of new deaths due COVID-19\n5.Number of intensive care unit patients with SARS-Cov-2\n" +
                "6.Number of hospitalized Patients with SARS-Cov-2\n7.Total number of tests performed\n" +
                "8.Daily number of new tests performed\n9.Get all data");
        errorFlag = true;
        do {
            try {
                errorFlag = false;
                int i = scan.nextInt();

                System.out.println("Do you want to export results to .csv file? (Y/N): ");
                String exportFlagString = EXPORT_SCAN.nextLine();
                if (exportFlagString.equalsIgnoreCase("Y")) exportFlag = true;

                switch (i) {
                    case 1:
                        selectTotalCases(manager);
                        break;
                    case 2:
                        selectDailyNewCases(manager);
                        break;
                    case 3:
                        selectTotalDeaths(manager);
                        break;
                    case 4:
                        selectDailyNewDeaths(manager);
                        break;
                    case 5:
                        selectIcuPatients(manager);
                        break;
                    case 6:
                        selectHospitalizedPatients(manager);
                        break;
                    case 7:
                        selectTotalTests(manager);
                        break;
                    case 8:
                        selectDailyNewTests(manager);
                        break;
                    case 9:
                        selectAllData(manager);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input format. Try again.");
                scan.reset();
            } catch (CsvRequiredFieldEmptyException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvDataTypeMismatchException e) {
                e.printStackTrace();
            }
        } while (errorFlag);
        if (exportFlag)
            System.out.println("Results saved in ...\\COVID-19-data-and-statistics\\src\\main\\export\\");
    }

    private static void selectTotalCases(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> cdList = q.getResultList();
        for (CovidData covidData : cdList) {
            System.out.print("| Date: " + covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            System.out.print(" | iso_code: " + covidData.getCountry());
            System.out.println(" | total_cases: " + covidData.getTotal_cases() + " |");
        }
        if (exportFlag) {
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData covidData : cdList) {
                CsvBeanTotalCases bean = new CsvBeanTotalCases();
                bean.setDate(covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setTotal_cases(covidData.getTotal_cases());
                beans.add(bean);
            }
            String filename = String.format("%s-TOTAL_CASES", cdList.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans, filename);
        }
    }

    private static void selectDailyNewCases(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> cdList = q.getResultList();
        for (CovidData covidData : cdList) {
            System.out.print("| Date: " + covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            System.out.print(" | iso_code: " + covidData.getCountry());
            System.out.println(" | daily_new_cases: " + covidData.getNew_cases() + " |");
        }
        if (exportFlag) {
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData covidData : cdList) {
                CsvBeanNewCases bean = new CsvBeanNewCases();
                bean.setDate(covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setNew_cases(covidData.getNew_cases());
                beans.add(bean);
            }
            String filename = String.format("%s-NEW_CASES", cdList.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans, filename);
        }
    }

    private static void selectTotalDeaths(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> cdList = q.getResultList();
        for (CovidData covidData : cdList) {
            System.out.print("| Date: " + covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            System.out.print(" | iso_code: " + covidData.getCountry());
            System.out.println(" | total_deaths: " + covidData.getTotal_deaths() + " |");
        }
        if (exportFlag) {
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData covidData : cdList) {
                CsvBeanTotalDeaths bean = new CsvBeanTotalDeaths();
                bean.setDate(covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setTotal_deaths(covidData.getTotal_deaths());
                beans.add(bean);
            }
            String filename = String.format("%s-TOTAL_DEATHS", cdList.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans, filename);
        }
    }

    private static void selectDailyNewDeaths(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> cdList = q.getResultList();
        for (CovidData covidData : cdList) {
            System.out.print("| Date: " + covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            System.out.print(" | iso_code: " + covidData.getCountry());
            System.out.println(" | daily_new_deaths: " + covidData.getNew_deaths() + " |");
        }
        if (exportFlag) {
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData covidData : cdList) {
                CsvBeanNewDeaths bean = new CsvBeanNewDeaths();
                bean.setDate(covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setNew_deaths(covidData.getNew_deaths());
                beans.add(bean);
            }
            String filename = String.format("%s-NEW_DEATHS", cdList.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans, filename);
        }
    }

    private static void selectIcuPatients(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> cdList = q.getResultList();
        for (CovidData covidData : cdList) {
            System.out.print("| Date: " + covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            System.out.print(" | iso_code: " + covidData.getCountry());
            System.out.println(" | ICU Patients: " + covidData.getIcu_patients() + " |");
        }
        if (exportFlag) {
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData covidData : cdList) {
                CsvBeanIcuPatients bean = new CsvBeanIcuPatients();
                bean.setDate(covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setIcu_patients(covidData.getIcu_patients());
                beans.add(bean);
            }
            String filename = String.format("%s-ICU_PATIENTS", cdList.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans, filename);
        }
    }

    private static void selectHospitalizedPatients(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> cdList = q.getResultList();
        for (CovidData covidData : cdList) {
            System.out.print("| Date: " + covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            System.out.print(" | iso_code: " + covidData.getCountry());
            System.out.println(" | Hospitalized Patients: " + covidData.getHosp_patients() + " |");
        }
        if (exportFlag) {
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData covidData : cdList) {
                CsvBeanHospPatients bean = new CsvBeanHospPatients();
                bean.setDate(covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setHosp_patients(covidData.getHosp_patients());
                beans.add(bean);
            }
            String filename = String.format("%s-HOSP_PATIENTS", cdList.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans, filename);
        }
    }

    private static void selectTotalTests(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> cdList = q.getResultList();
        for (CovidData covidData : cdList) {
            System.out.print("| Date: " + covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            System.out.print(" | iso_code: " + covidData.getCountry());
            System.out.println(" | Total tests: " + covidData.getTotal_tests() + " |");
        }
        if (exportFlag) {
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData covidData : cdList) {
                CsvBeanTotalTests bean = new CsvBeanTotalTests();
                bean.setDate(covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setTotal_tests(covidData.getTotal_tests());
                beans.add(bean);
            }
            String filename = String.format("%s-TOTAL_TESTS", cdList.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans, filename);
        }
    }

    private static void selectDailyNewTests(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> cdList = q.getResultList();
        for (CovidData covidData : cdList) {
            System.out.print("| Date: " + covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            System.out.print(" | iso_code: " + covidData.getCountry());
            System.out.println(" | Daily new tests: " + covidData.getNew_tests() + " |");
        }
        if (exportFlag) {
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData covidData : cdList) {
                CsvBeanNewTests bean = new CsvBeanNewTests();
                bean.setDate(covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setNew_tests(covidData.getNew_tests());
                beans.add(bean);
            }
            String filename = String.format("%s-NEW_TESTS", cdList.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans, filename);
        }
    }

    private static void selectAllData(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ?", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> cdList = q.getResultList();
        for (CovidData covidData : cdList) {
            System.out.printf("| Date: %s | Country: %s | Total Cases: %d | Daily New Cases: %d |\n" +
                            "Total deaths: %d | Daily new deaths: %d | ICU patients: %d |\n" +
                            "Hospitalized patients: %d | Total Tests: %d | Daily new tests: %d |\n\n",
                    covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    covidData.getCountry(), covidData.getTotal_cases(), covidData.getNew_cases(),
                    covidData.getTotal_deaths(), covidData.getNew_deaths(), covidData.getIcu_patients(),
                    covidData.getHosp_patients(), covidData.getTotal_tests(), covidData.getNew_tests());
        }
        if (exportFlag) {
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData covidData : cdList) {
                CsvBeanAllData bean = new CsvBeanAllData();
                bean.setDate(covidData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setTotal_cases(covidData.getTotal_cases());
                bean.setNew_cases(covidData.getNew_cases());
                bean.setTotal_deaths(covidData.getTotal_deaths());
                bean.setNew_deaths(covidData.getNew_deaths());
                bean.setIcu_patients(covidData.getIcu_patients());
                bean.setHosp_patients(covidData.getHosp_patients());
                bean.setTotal_tests(covidData.getTotal_tests());
                bean.setNew_tests(covidData.getNew_tests());
                beans.add(bean);
            }
            String filename = String.format("%s-ALL_DATA", cdList.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans, filename);
        }
    }

    private static void displayAvailableCountries(EntityManager manager) {
        Query q = manager.createNativeQuery("SELECT * FROM Country", Country.class);


        List<Country> countries = q.getResultList();
        for (Country country : countries) {
            System.out.println("\t" + country.getISO_code() + " | " + country.getName());
        }
    }

//    private static Date selectFirstAvailableDate(String countryIso){
//        manager.getTransaction().begin();
//        Query q = manager.createNativeQuery("SELECT MIN(o.DATE) FROM CovidData o WHERE o.ISO_CODE=?", CovidData.class);
//        q.setParameter(1, countryIso);
//        return (Date)q.getSingleResult();
//    }

    private static Date readDate(Scanner scan) throws ParseException {
        SIMPLE_DATE_FORMAT.setLenient(false);
        return SIMPLE_DATE_FORMAT.parse(scan.next());
    }


    private static Date selectStartDate(Scanner scan) {
        dateFormatFlag = true;
        do {
            try {
                dateFormatFlag = false;
                System.out.println("Select start date: (yyyy-MM-dd)");
                startDate = readDate(scan);
            } catch (ParseException e) {
                System.out.println("Wrong date format (yyyy-MM-dd). Try again.");
                dateFormatFlag = true;
                scan.reset();
            }
        }
        while (dateFormatFlag);
        return startDate;
    }

    private static void selectEndDate(Scanner scan) {
        dateFormatFlag = true;
        do {
            try {
                dateFormatFlag = false;
                System.out.println("Select end date: (yyyy-MM-dd)");
                endDate = readDate(scan);
            } catch (ParseException e) {
                System.out.println("Wrong date format (yyyy-MM-dd). Try again.");
                dateFormatFlag = true;
                scan.reset();
            }
        }
        while (dateFormatFlag);

    }
}
