package main;

import DB.COVID19DAO;
import DB.entities.Country;
import DB.entities.CovidData;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import main.CsvRead.CsvBeanOWID;
import main.CsvRead.CsvRead;
import main.CsvWrite.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    //    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    private static LocalDate startLocalDate;
//    private static LocalDate endLocalDate;
    private static final Scanner INST_SCAN = new Scanner(System.in);
    private static final Scanner STRING_SCAN = new Scanner(System.in);
    private static final Scanner EXPORT_SCAN = new Scanner(System.in);
    private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    //private static COVID19DAO dao = new COVID19DAO();
    //private static EntityManager manager = dao.getManager();
    //private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysql_local");
    //private static EntityManager manager = factory.createEntityManager();
    private static String countryIso;
    private static final Date DATE_NOW = new Date();

    private static Date startDate;
    private static Date endDate;
    private static boolean exportFlag;

    public static void main(String[] args) throws ParseException {

        boolean format = true;
        boolean format2 = true;

        //===========================================construction of database========================================

        CsvBeanOWID bean = new CsvBeanOWID();
        CsvRead csvRead = new CsvRead(bean);
        COVID19DAO dao = new COVID19DAO();
        dao.openConnection();
        EntityManager manager = dao.getManager();
        dao.buildDatabase(csvRead);


        //===========================================construction of database========================================


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
                    try {
                        availableOptions(manager);
                    } catch (CsvRequiredFieldEmptyException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (CsvDataTypeMismatchException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    while (format)
                        format = false;
                    try {
                        System.out.println("Select date: (yyyy-MM-dd)");
                        startDate = SIMPLE_DATE_FORMAT.parse("2019-11-01");
                        endDate = readDate(STRING_SCAN);
                        availableOptions(manager);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Wrong date format (yyyy-MM-dd). Try again.");
                        format = true;
                    } catch (CsvRequiredFieldEmptyException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (CsvDataTypeMismatchException e) {
                        e.printStackTrace();
                    }
                case 3:
                    while (format)
                        format = false;
                    try {
                        System.out.println("Select date: (yyyy-MM-dd)");
                        startDate = readDate(STRING_SCAN);
                        endDate = DATE_NOW;
                        availableOptions(manager);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Wrong date format (yyyy-MM-dd). Try again.");
                        format = true;
                    } catch (CsvRequiredFieldEmptyException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (CsvDataTypeMismatchException e) {
                        e.printStackTrace();
                    }
                case 4:
                    while (format)
                        format = false;
                    try {
                        System.out.println("Select start date: (yyyy-MM-dd)");
                        startDate = readDate(STRING_SCAN);
                        System.out.println("Select end date: (yyyy-MM-dd)");
                        endDate = readDate(STRING_SCAN);
                        availableOptions(manager);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Wrong date format (yyyy-MM-dd). Try again.");
                        format = true;
                    } catch (CsvRequiredFieldEmptyException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (CsvDataTypeMismatchException e) {
                        e.printStackTrace();
                    }
                case 5:
                    while (format)
                        format = false;
                    try {
                        System.out.println("Select date: (yyyy-MM-dd)");
                        startDate = readDate(STRING_SCAN);
                        endDate = startDate;
                        availableOptions(manager);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Wrong date format (yyyy-MM-dd). Try again.");
                        format = true;
                    } catch (CsvRequiredFieldEmptyException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (CsvDataTypeMismatchException e) {
                        e.printStackTrace();
                    }
                case 6:
                    try {
                        selectTotalCases(manager);
                    } catch (CsvRequiredFieldEmptyException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (CsvDataTypeMismatchException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("No such option like " + i + ". Try again.");
                    break;
            }
        }

        dao.closeConnection();
    }


//    private static LocalDate readLocalDate(Scanner scan) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        return LocalDate.parse(scan.next(), formatter);
//    }

    private static Date readDate(Scanner scan) throws ParseException {
        return SIMPLE_DATE_FORMAT.parse(scan.next());
    }


    private static void availableOptions(EntityManager manager) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("1.Total cases\n2.Daily new cases\n3.Total deaths\n4.Daily new deaths\n5.ICU Patients" +
                "\n6.Hospitalized Patients+\n7.Total_tests\n8.Daily new tests\n9.Get all data");


        int i = INST_SCAN.nextInt();

        System.out.println("Do you want to export results to .csv file? (yes/no): ");
        String exportFlagString = EXPORT_SCAN.nextLine();
        if(exportFlagString.equalsIgnoreCase("yes")) exportFlag = true;


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
            case 9:
                selectAllData(manager);
                break;
        }

        if(exportFlag) System.out.println("Results saved in ...\\COVID-19-data-and-statistics\\src\\main\\export\\");
    }

    public static void selectTotalCases(EntityManager manager) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? ORDER BY o.date", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> owids = q.getResultList();

        for (CovidData owid : owids) {
            System.out.print("| Date: " + owid.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | total_cases: " + owid.getTotal_cases() + " |");
        }
        if(exportFlag){
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData owid : owids) {
                CsvBeanTotalCases bean = new CsvBeanTotalCases();
                bean.setDate(owid.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setTotal_cases(owid.getTotal_cases());
                beans.add(bean);
            }
            String filename = String.format("%s-TOTAL_CASES",owids.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans,filename);
        }
    }

    public static void selectDailyNewCases(EntityManager manager) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? ORDER BY o.date", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> owids = q.getResultList();
        for (CovidData owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | daily_new_cases: " + owid.getNew_cases() + " |");
        }
        if(exportFlag){
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData owid : owids) {
                CsvBeanNewCases bean = new CsvBeanNewCases();
                bean.setDate(owid.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setNew_cases(owid.getTotal_cases());
                beans.add(bean);
            }
            String filename = String.format("%s-NEW_CASES",owids.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans,filename);
        }
    }

    public static void selectTotalDeaths(EntityManager manager) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? ORDER BY o.date", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> owids = q.getResultList();
        for (CovidData owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | total_deaths: " + owid.getTotal_deaths() + " |");
        }
        if(exportFlag){
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData owid : owids) {
                CsvBeanTotalDeaths bean = new CsvBeanTotalDeaths();
                bean.setDate(owid.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setTotal_deaths(owid.getTotal_cases());
                beans.add(bean);
            }
            String filename = String.format("%s-TOTAL_DEATHS",owids.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans,filename);
        }
    }

    public static void selectDailyNewDeaths(EntityManager manager) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? ORDER BY o.date", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> owids = q.getResultList();
        for (CovidData owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | daily_new_deaths: " + owid.getNew_deaths() + " |");
        }
        if(exportFlag){
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData owid : owids) {
                CsvBeanNewDeaths bean = new CsvBeanNewDeaths();
                bean.setDate(owid.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setNew_deaths(owid.getTotal_cases());
                beans.add(bean);
            }
            String filename = String.format("%s-NEW_DEATHS",owids.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans,filename);
        }
    }

    public static void selectIcuPatients(EntityManager manager) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? ORDER BY o.date", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> owids = q.getResultList();
        for (CovidData owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | ICU Patients: " + owid.getIcu_patients() + " |");
        }
        if(exportFlag){
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData owid : owids) {
                CsvBeanIcuPatients bean = new CsvBeanIcuPatients();
                bean.setDate(owid.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setIcu_patients(owid.getTotal_cases());
                beans.add(bean);
            }
            String filename = String.format("%s-ICU_PATIENTS",owids.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans,filename);
        }
    }

    public static void selectHospitalizedPatients(EntityManager manager) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? ORDER BY o.date", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> owids = q.getResultList();
        for (CovidData owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | Hospitalized Patients: " + owid.getHosp_patients() + " |");
        }
        if(exportFlag){
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData owid : owids) {
                CsvBeanHospPatients bean = new CsvBeanHospPatients();
                bean.setDate(owid.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setHosp_patients(owid.getTotal_cases());
                beans.add(bean);
            }
            String filename = String.format("%s-HOSP_PATIENTS",owids.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans,filename);
        }
    }

    public static void selectTotalTests(EntityManager manager) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? ORDER BY o.date", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> owids = q.getResultList();
        for (CovidData owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | Total tests: " + owid.getTotal_tests() + " |");
        }
        if(exportFlag){
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData owid : owids) {
                CsvBeanTotalTests bean = new CsvBeanTotalTests();
                bean.setDate(owid.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setTotal_tests(owid.getTotal_cases());
                beans.add(bean);
            }
            String filename = String.format("%s-TOTAL_TESTS",owids.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans,filename);
        }
    }

    public static void selectDailyNewTests(EntityManager manager) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? ORDER BY o.date", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> owids = q.getResultList();
        for (CovidData owid : owids) {
            System.out.print("| Date: " + owid.getDate());
            System.out.print(" | iso_code: " + owid.getCountry());
            System.out.println(" | Daily new tests: " + owid.getNew_tests() + " |");
        }
        if(exportFlag){
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData owid : owids) {
                CsvBeanNewTests bean = new CsvBeanNewTests();
                bean.setDate(owid.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setNew_tests(owid.getTotal_cases());
                beans.add(bean);
            }
            String filename = String.format("%s-NEW_TESTS",owids.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans,filename);
        }
    }

    public static void selectAllData(EntityManager manager) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? ORDER BY o.date", CovidData.class);
        q.setParameter(1, countryIso);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);

        List<CovidData> owids = q.getResultList();
        for (CovidData owid : owids) {
            System.out.printf("| Date: %s | Country: %s | Total Cases: %d | Daily New Cases: %d |\n" +
                            "Total deaths: %d | Daily new deaths: %d | ICU patients: %d |\n" +
                            "Hospitalized patients: %d | Total Tests: %d | Daily new tests: %d |\n\n", owid.getDate(), owid.getCountry(),
                    owid.getTotal_cases(), owid.getNew_cases(), owid.getTotal_deaths(), owid.getNew_deaths(), owid.getIcu_patients(),
                    owid.getHosp_patients(), owid.getTotal_tests(), owid.getNew_tests());
        }
        if(exportFlag){
            List<CsvBean> beans = new ArrayList<>();
            for (CovidData owid : owids) {
                CsvBeanAllData bean = new CsvBeanAllData();
                bean.setDate(owid.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bean.setTotal_cases(owid.getTotal_cases());
                bean.setNew_cases(owid.getNew_cases());
                bean.setTotal_deaths(owid.getTotal_deaths());
                bean.setNew_deaths(owid.getNew_deaths());
                bean.setIcu_patients(owid.getIcu_patients());
                bean.setHosp_patients(owid.getHosp_patients());
                bean.setTotal_tests(owid.getTotal_tests());
                bean.setNew_tests(owid.getTotal_cases());
                beans.add(bean);
            }
            String filename = String.format("%s-ALL_DATA",owids.get(0).getCountry().toString());
            CsvWrite.writeCsvFromBean(beans,filename);
        }
    }

}