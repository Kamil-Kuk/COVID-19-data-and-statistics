package main.CsvRead;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CsvReadTest {

    @Test
    void shouldResultListNotEmpty() {
        //given
        CsvRead csvRead = new CsvRead(new CsvBeanOWID());

        //when
        List<CsvBeanOWID> result = csvRead.getBeanFromCSV();

        //then
        Assertions.assertTrue(!result.isEmpty());
    }

    //Test for comparing single columns
//    @Test
//    void shouldGetValidDateFirstEntry() {
//        //given
//        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
//        List<CsvBeanOWID> result = csvRead.getBeanFromCSV();
//        CsvBeanOWID testBean = new CsvBeanOWID();
//
//        //when
//        testBean.setISO_Code("AFG");
//        testBean.setContinent("Asia");
//        testBean.setLocation("Afghanistan");
//        testBean.setDate("2020-01-23");
//        testBean.setTotal_Cases("0");
//
//        //then
//        Assertions.assertEquals(result.get(0).getDate(), testBean.getDate());
//    }
//
//    @Test
//    void shouldGetValidTotalCasesFirstEntry() {
//        //given
//        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
//        List<CsvBeanOWID> result = csvRead.getBeanFromCSV();
//        CsvBeanOWID testBean = new CsvBeanOWID();
//
//        //when
//        testBean.setISO_Code("AFG");
//        testBean.setContinent("Asia");
//        testBean.setLocation("Afghanistan");
//        testBean.setDate("2020-01-23");
//        testBean.setTotal_Cases("0");
//
//        //then
//        Assertions.assertEquals(result.get(0).getTotal_Cases(), testBean.getTotal_Cases());
//    }
//
//    @Test
//    void shouldGetValidISOFirstEntry() {
//        //given
//        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
//        List<CsvBeanOWID> result = csvRead.getBeanFromCSV();
//        CsvBeanOWID testBean = new CsvBeanOWID();
//
//        //when
//        testBean.setISO_Code("AFG");
//        testBean.setContinent("Asia");
//        testBean.setLocation("Afghanistan");
//        testBean.setDate("2020-01-23");
//        testBean.setTotal_Cases("0");
//
//        //then
//        Assertions.assertEquals(result.get(0).getISO_Code(), testBean.getISO_Code());
//    }
//
//    @Test
//    void shouldGetValidContinentFirstEntry() {
//        //given
//        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
//        List<CsvBeanOWID> result = csvRead.getBeanFromCSV();
//        CsvBeanOWID testBean = new CsvBeanOWID();
//
//        //when
//        testBean.setISO_Code("AFG");
//        testBean.setContinent("Asia");
//        testBean.setLocation("Afghanistan");
//        testBean.setDate("2020-01-23");
//        testBean.setTotal_Cases("0");
//
//        //then
//        Assertions.assertEquals(result.get(0).getContinent(), testBean.getContinent());
//    }
//
//    @Test
//    void shouldGetValidLocationFirstEntry() {
//        //given
//        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
//        List<CsvBeanOWID> result = csvRead.getBeanFromCSV();
//        CsvBeanOWID testBean = new CsvBeanOWID();
//
//        //when
//        testBean.setISO_Code("AFG");
//        testBean.setContinent("Asia");
//        testBean.setLocation("Afghanistan");
//        testBean.setDate("2020-01-23");
//        testBean.setTotal_Cases("0");
//
//        //then
//        Assertions.assertEquals(result.get(0).getLocation(), testBean.getLocation());
//    }

    @Test
    void shouldGetValidDataFirstEntry() {
        //given
        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
        List<CsvBeanOWID> result = csvRead.getBeanFromCSV();
        CsvBeanOWID testBean = new CsvBeanOWID();

        //when
        testBean.setISO_Code("AFG");
        testBean.setContinent("Asia");
        testBean.setLocation("Afghanistan");
        testBean.setDate("2020-01-23");
        testBean.setTotal_Cases("0");

        //then
        Assertions.assertEquals(result.get(0), testBean);
    }

    @Test
    void shouldGetValidData1000thEntry() {
        //given
        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
        List<CsvBeanOWID> result = csvRead.getBeanFromCSV();
        CsvBeanOWID testBean = new CsvBeanOWID();

        //when
        testBean.setISO_Code("AND");
        testBean.setContinent("Europe");
        testBean.setLocation("Andorra");
        testBean.setDate("2020-02-19");
        testBean.setTotal_Cases("0");

        //then
        Assertions.assertEquals(result.get(1000-1), testBean);
    }

    @Test
    void shouldGetValidDataLastEntry() {
        //given
        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
        List<CsvBeanOWID> result = csvRead.getBeanFromCSV();
        CsvBeanOWID testBean = new CsvBeanOWID();

        //when
        testBean.setISO_Code("ZWE");
        testBean.setContinent("Africa");
        testBean.setLocation("Zimbabwe");
        testBean.setDate("2020-12-11");
        testBean.setTotal_Cases("11162");

        //then
        Assertions.assertEquals(result.get(result.size()-1), testBean);
    }

}