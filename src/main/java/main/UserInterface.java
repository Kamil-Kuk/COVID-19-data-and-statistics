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
import javax.persistence.Query;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInterface {

    private static final Scanner STRING_SCAN = new Scanner(System.in);
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    protected static COVID19DAO DAO = new COVID19DAO();
    private static String countryIso;
    private static Date startDate;
    private static Date endDate;
    private static List<CovidData> output;
    private static int optionInt;
    private static boolean dateFormatFlag;
    private static boolean exportFlag;
    private static boolean answerYNFlag;
    private static boolean isDrawable = true;


    protected static void initialMethod(EntityManager manager) {

        System.out.println("--------------COVID-19: DATA AND STATISTIC--------------");
        CsvBeanOWID bean = new CsvBeanOWID();
        CsvRead csvRead = new CsvRead(bean);

        //downloading latest data from the OWID server
        do {
            System.out.println("Do you want to download/update .csv file from 'Our World In Data' database? (Y/N)");
            System.out.println(">>> ");
            String answerYN = STRING_SCAN.next();
            if (answerYN.equalsIgnoreCase("y")) {
                try {
                    System.out.println("\n>>>>>>>>>>>>>DOWNLOADING FILE<<<<<<<<<<<<<");
                    Downloader.getFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                answerYNFlag = false;

            } else if (answerYN.equalsIgnoreCase("n")) {
                answerYNFlag = false;

            } else {
                System.out.println("Wrong input format. Try again.");
                answerYNFlag = true;
            }
        }
        while (answerYNFlag);

        answerYNFlag = true;

        //bulding SQL DATABASE
        try {
            System.out.println("\n>>>>>>>>>>>>BUILDING DATABASE<<<<<<<<<<<<");
            DAO.buildDatabase(csvRead);
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        }

        //main loop
        do {
            System.out.println("\nSelect Country (name by ISO CODE eg. POL for Poland). Press H for list of ISO_CODES" +
                    " or press Q to end program.");

            System.out.println(">>> ");
            countryIso = STRING_SCAN.next();

            if (countryIso.equalsIgnoreCase("q")) {
                answerYNFlag = false;
                System.exit(0);
            } else if (countryIso.equalsIgnoreCase("h")) {
                answerYNFlag = true;
                displayAvailableCountries(manager);
                STRING_SCAN.reset();
            } else if (validateCountry(manager)) {
                answerYNFlag = false;
                selectStartDate(manager);
                selectEndDate(manager);
                availableOptions(manager);
            } else {
                System.out.println("Wrong input format. Try again.");
            }
        }
        while (answerYNFlag);
    }


    private static void availableOptions(EntityManager manager) {
        System.out.println("\nSelect type of data to be displayed.");
        System.out.println("1.Total number of cases\n2.Daily number of new cases\n3.Total deaths due COVID-19\n" +
                "4.Daily number of new deaths due COVID-19\n5.Number of intensive care unit patients with SARS-Cov-2\n" +
                "6.Number of hospitalized Patients with SARS-Cov-2\n7.Total number of tests performed\n" +
                "8.Daily number of new tests performed\n9.Get all data");
        System.out.println("Type referring number:");
        boolean errorFlag = true;
        do {
            try {
                errorFlag = false;
                System.out.println(">>> ");
                optionInt = new Scanner(System.in).nextInt();

                switch (optionInt) {
                    case 1:
                        exportData();
                        selectTotalCases(manager);
                        break;
                    case 2:
                        exportData();
                        selectDailyNewCases(manager);
                        break;
                    case 3:
                        exportData();
                        selectTotalDeaths(manager);
                        break;
                    case 4:
                        exportData();
                        selectDailyNewDeaths(manager);
                        break;
                    case 5:
                        exportData();
                        selectIcuPatients(manager);
                        break;
                    case 6:
                        exportData();
                        selectHospitalizedPatients(manager);
                        break;
                    case 7:
                        exportData();
                        selectTotalTests(manager);
                        break;
                    case 8:
                        exportData();
                        selectDailyNewTests(manager);
                        break;
                    case 9:
                        exportData();
                        selectAllData(manager);
                        isDrawable = false; //flag isDrawable is set to false, because it is not possible to draw a graph for multiple results
                        break;
                    default: {
                        System.out.println("No such option. Try again.");
                        errorFlag = true;
                    }
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input format. Try again.");
                errorFlag = true;
            } catch (CsvRequiredFieldEmptyException | IOException | CsvDataTypeMismatchException e) {
                e.printStackTrace();
            }
        } while (errorFlag);
    }

    private static void selectTotalCases(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT o.id, o.date, o.iso_code, o.total_cases FROM CovidData o " +
                "WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? AND o.total_cases IS NOT NULL ORDER BY o.DATE", CovidData.class);
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
        setOutput(cdList);
    }

    private static void selectDailyNewCases(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT o.id, o.date, o.iso_code, o.daily_new_cases FROM CovidData o " +
                "WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? AND o.daily_new_cases IS NOT NULL ORDER BY o.DATE", CovidData.class);
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
        setOutput(cdList);
    }

    private static void selectTotalDeaths(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT o.id, o.date, o.iso_code, o.total_deaths FROM CovidData o " +
                "WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? AND o.total_deaths IS NOT NULL ORDER BY o.DATE",
                CovidData.class);
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
        setOutput(cdList);
    }

    private static void selectDailyNewDeaths(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT o.id, o.date, o.iso_code, o.daily_new_deaths FROM CovidData o " +
                "WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? AND o.daily_new_deaths IS NOT NULL ORDER BY o.DATE", CovidData.class);
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
        setOutput(cdList);
    }

    private static void selectIcuPatients(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT o.id, o.date, o.iso_code, o.icu_patients FROM CovidData o " +
                "WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? AND o.icu_patients IS NOT NULL ORDER BY o.DATE", CovidData.class);
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
        setOutput(cdList);
    }

    private static void selectHospitalizedPatients(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT o.id, o.date, o.iso_code, o.HOSPITALIZED_PATIENTS FROM CovidData o " +
                "WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? AND o.HOSPITALIZED_PATIENTS IS NOT NULL ORDER BY o.DATE", CovidData.class);
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
        setOutput(cdList);
    }

    private static void selectTotalTests(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT o.id, o.date, o.iso_code, o.total_tests FROM CovidData o " +
                "WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? AND o.total_tests IS NOT NULL ORDER BY o.DATE", CovidData.class);
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
        setOutput(cdList);
    }

    private static void selectDailyNewTests(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT o.id, o.date, o.iso_code, o.DAILY_NEW_TESTS FROM CovidData o " +
                "WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? AND o.DAILY_NEW_TESTS IS NOT NULL ORDER BY o.DATE", CovidData.class);
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
        setOutput(cdList);
    }

    private static void selectAllData(EntityManager manager) throws
            CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Query q = manager.createNativeQuery("SELECT * FROM CovidData o WHERE o.ISO_CODE=? AND o.date BETWEEN ? AND ? ORDER BY o.DATE", CovidData.class);
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
        setOutput(cdList);
    }

    private static void displayAvailableCountries(EntityManager manager) {
        Query q = manager.createNativeQuery("SELECT * FROM Country", Country.class);


        List<Country> countries = q.getResultList();
        for (Country country : countries) {
            System.out.println("\t" + country.getISO_code() + " | " + country.getName());
        }
    }

    private static boolean validateCountry(EntityManager manager) {
        String hql = "SELECT * FROM Country c WHERE c.iso_code= :ISO_CODE";
        Query qhql = manager.createNativeQuery(hql, Country.class);
        qhql.setParameter("ISO_CODE", countryIso);
        return qhql.getResultList().size() == 1;
    }

    private static void selectStartDate(EntityManager manager) {
        Pattern pattern = Pattern.compile("20[0-9]{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])");
        do {
            System.out.println("\nSelect start date.");
            System.out.println("Type 'start' to get first available date for " +
                    countryIso.toUpperCase() + " or type specific date (yyyy-MM-dd): ");
            System.out.println(">>> ");
            String input = new Scanner(System.in).next();
            Matcher matcher = pattern.matcher(input);
            dateFormatFlag = true;

            if (matcher.matches()) {
                try {
                    startDate = SIMPLE_DATE_FORMAT.parse(input);
                    dateFormatFlag = false;
                } catch (ParseException e) {
                    System.out.println("Wrong input. Try again.");
                    dateFormatFlag = true;
                }
            } else if (input.equalsIgnoreCase("start")) {
                selectFirstAvailableDate(manager);
                dateFormatFlag = false;
            } else {
                System.out.println("Wrong input. Try again.");
            }
        }
        while (dateFormatFlag);
    }

    private static void selectFirstAvailableDate(EntityManager manager) {
        String hql = "SELECT * FROM CovidData o WHERE o.iso_code= :isoCode order by o.date ";
        Query qhql = manager.createNativeQuery(hql, CovidData.class);
        qhql.setParameter("isoCode", countryIso);
        List<CovidData> list = qhql.getResultList();
        startDate = list.get(0).getDate();
    }


    private static void selectEndDate(EntityManager manager) {
        Pattern pattern = Pattern.compile("20[0-9]{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])");
        do {
            System.out.println("\nSelect end date.");
            System.out.println("Type 'end' to get the last available date for " +
                    countryIso.toUpperCase() + " or type specific date (yyyy-MM-dd): ");
            System.out.println(">>> ");
            String input = new Scanner(System.in).next();
            Matcher matcher = pattern.matcher(input);
            dateFormatFlag = true;

                if (matcher.matches()) {
                    try {
                        endDate = SIMPLE_DATE_FORMAT.parse(input);
                        dateFormatFlag = false;
                        if (endDate.before(startDate)){
                            System.out.println("Selected end date is earlier than" +
                                    " the start date. Try again.");
                        }
                    } catch (ParseException e) {
                        System.out.println("Wrong input. Try again.");
                        dateFormatFlag = true;
                    }
                } else if (input.equalsIgnoreCase("end")) {
                    selectLastAvailableDate(manager);
                    dateFormatFlag = false;
                } else {
                    System.out.println("Wrong input. Try again.");
                }

            }
        while (dateFormatFlag||endDate.before(startDate));
    }


    private static void selectLastAvailableDate(EntityManager manager) {
        String hql = "SELECT * FROM CovidData o WHERE o.iso_code= :isoCode order by o.date DESC";
        Query qhql = manager.createNativeQuery(hql, CovidData.class);
        qhql.setParameter("isoCode", countryIso);
        List<CovidData> list = qhql.getResultList();
        endDate = list.get(0).getDate();
    }

    private static void exportData() {
        answerYNFlag = true;
        do {
            System.out.println("\nDo you want to export results to .csv file? (Y/N): ");
            System.out.println(">>>");
            String exportFlagString = new Scanner(System.in).nextLine();
            if (exportFlagString.equalsIgnoreCase("Y")) {
                exportFlag = true;
                answerYNFlag = false;
                break;
            }
            if (exportFlagString.equalsIgnoreCase("N")) {
                exportFlag = false;
                answerYNFlag = false;
                break;
            } else {
                answerYNFlag = true;
                System.out.println("Wrong input format. Try again.");
            }
        }
        while (answerYNFlag);

        if (exportFlag)
            System.out.println("Results saved in ...\\COVID-19-data-and-statistics\\src\\main\\export\\");
    }

    public static List<CovidData> getOutput() {
        return output;
    }

    public static void setOutput(List<CovidData> output) {
        UserInterface.output = output;
    }

    public static boolean isDrawable() {
        return isDrawable;
    }

    public static int getOptionInt() {
        return optionInt;
    }
}
