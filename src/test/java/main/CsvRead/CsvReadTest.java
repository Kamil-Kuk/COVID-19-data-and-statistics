package main.CsvRead;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

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
        testBean.setISO_code("AFG");
        testBean.setContinent("Asia");
        testBean.setLocation("Afghanistan");
        testBean.setDate("2020-01-23");
        testBean.setTotal_cases("");
        testBean.setNew_cases("0.0");
        testBean.setTotal_deaths("");
        testBean.setNew_deaths("0.0");
        testBean.setIcu_patients("");
        testBean.setHosp_patients("");
        testBean.setTotal_tests("");
        testBean.setNew_tests("");
        testBean.setPopulation("38928341.0");

        //then
        Assertions.assertEquals(result.get(0), testBean);
    }

    @Test
    void shouldGetValidDataLastEntry() {
        //given
        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
        List<CsvBean> result = csvRead.getBeanFromCSV();
        CsvBeanOWID testBean = new CsvBeanOWID();

        //when
        testBean.setISO_code("ZWE");
        testBean.setContinent("Africa");
        testBean.setLocation("Zimbabwe");
        testBean.setDate("2020-12-11");
        testBean.setTotal_cases("11162.0");
        testBean.setNew_cases("81.0");
        testBean.setTotal_deaths("306.0");
        testBean.setNew_deaths("1.0");;
        testBean.setIcu_patients("");
        testBean.setHosp_patients("");
        testBean.setTotal_tests("");
        testBean.setNew_tests("");
        testBean.setPopulation("14862927.0");

        //then
        Assertions.assertEquals(result.get(result.size()-1), testBean);

    }

}
