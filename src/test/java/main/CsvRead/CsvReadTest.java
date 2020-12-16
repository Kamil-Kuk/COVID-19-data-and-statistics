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
        List<CsvBean> result = csvRead.getBeanFromCSV();

        //then
        Assertions.assertTrue(!result.isEmpty());
    }

    @Test
    void shouldGetValidDataFirstEntry() {
        //given
        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
        List<CsvBean> result = csvRead.getBeanFromCSV();
        CsvBeanOWID testBean = new CsvBeanOWID();

        //when
        testBean.setISO_Code("AFG");
        testBean.setContinent("Asia");
        testBean.setLocation("Afghanistan");
        testBean.setDate("2020-01-23");
        testBean.setTotal_Cases("");
        testBean.setNew_Cases("0.0");
        testBean.setTotal_Deaths("");
        testBean.setNew_Deaths("0.0");
        testBean.setTotal_Cases_Per_Million("");
        testBean.setNew_Cases_Per_Million("0.0");
        testBean.setTotal_Deaths_Per_Million("");
        testBean.setNew_Deaths_Per_Million("0.0");
        testBean.setIcu_Patients("");
        testBean.setIcu_Patients_Per_Million("");
        testBean.setHosp_Patients("");
        testBean.setHosp_Patients_Per_Million("");
        testBean.setTotal_Tests("");
        testBean.setNew_Tests("");

        //then
        Assertions.assertEquals(result.get(0), testBean);
    }
/*
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
*/
    @Test
    void shouldGetValidDataLastEntry() {
        //given
        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
        List<CsvBean> result = csvRead.getBeanFromCSV();
        CsvBeanOWID testBean = new CsvBeanOWID();

        //when
        testBean.setISO_Code("ZWE");
        testBean.setContinent("Africa");
        testBean.setLocation("Zimbabwe");
        testBean.setDate("2020-12-11");
        testBean.setTotal_Cases("11162.0");
        testBean.setNew_Cases("81.0");
        testBean.setTotal_Deaths("306.0");
        testBean.setNew_Deaths("1.0");
        testBean.setTotal_Cases_Per_Million("750.996");
        testBean.setNew_Cases_Per_Million("5.45");
        testBean.setTotal_Deaths_Per_Million("20.588");
        testBean.setNew_Deaths_Per_Million("0.067");
        testBean.setIcu_Patients("");
        testBean.setIcu_Patients_Per_Million("");
        testBean.setHosp_Patients("");
        testBean.setHosp_Patients_Per_Million("");
        testBean.setTotal_Tests("");
        testBean.setNew_Tests("");

        //then
        Assertions.assertEquals(result.get(result.size()-1), testBean);

    }

}