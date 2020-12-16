package main;

import java.text.ParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    private static final Scanner INST_SCAN = new Scanner(System.in);
    private static final Scanner STRING_SCAN = new Scanner(System.in);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) throws ParseException {
        LocalDate startDate;
        LocalDate endDate;
        boolean format = true;
        boolean format2 = true;
        System.out.println("COVID-19: DATA AND STATISTIC");
        System.out.println("Select Country (name by ISO CODE eg. POL for Poland) or press Q to end program");
        String country = STRING_SCAN.next();
        if (country.equalsIgnoreCase("q")) return;
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
                    System.out.println("Available data for: " + country);
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
                default:
                    System.out.println("No such option like " + i + ". Try again.");
                    break;
            }
        }
    }


    public static LocalDate readDate(Scanner scan) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(scan.next(), formatter);
    }


    public static void availableOptions() {
        System.out.println("1.Total cases\n2.New cases\n3.Total deaths\n4.New deaths\n5.total_cases_per_million" +
                "\n6.new_cases_per_million\n7.total_deaths_per_million\n8.new_deaths_per_million" +
                "\n9.icu_patients\n10.icu_patients_per_million\n11.hosp_patients\n12.hosp_patients_per_million" +
                "\n13.total_tests\n14.new_tests");
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
}
