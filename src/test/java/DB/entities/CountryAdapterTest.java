package DB.entities;

import DB.entities.adapters.CountryAdapter;
import main.CsvRead.CsvBean;
import main.CsvRead.CsvBeanOWID;

import static org.assertj.core.api.Assertions.assertThat;

import main.CsvRead.CsvRead;
import org.junit.jupiter.api.Test;

import java.util.List;


class CountryAdapterTest {

    @Test
    void shouldSetValidGivenInput() {
        //given
        CsvBeanOWID bean = new CsvBeanOWID();
        bean.setISO_code("POL");
        bean.setContinent("Europe");
        bean.setLocation("Poland");
        bean.setPopulation("38000000.0");
        CountryAdapter testAdapter = new CountryAdapter(bean);

        //when
        testAdapter.setISO_code();
        testAdapter.setContinent();
        testAdapter.setName();
        testAdapter.setPopulation();

        //then
        assertThat(testAdapter.getISO_code()).isEqualTo("POL");
        assertThat(testAdapter.getContinent()).isEqualTo("Europe");
        assertThat(testAdapter.getName()).isEqualTo("Poland");
        assertThat(testAdapter.getPopulation()).isEqualTo(38_000_000);
    }

    @Test
    void shouldNotSetInvalidGivenInput() {
        //given
        CsvBeanOWID bean = new CsvBeanOWID();
        bean.setISO_code("");
        bean.setContinent("");
        bean.setLocation("");
        bean.setPopulation("0.0");
        CountryAdapter testAdapter = new CountryAdapter(bean);

        //when
        testAdapter.setISO_code();
        testAdapter.setContinent();
        testAdapter.setName();
        testAdapter.setPopulation();

        //then
        assertThat(testAdapter.getISO_code()).isNull();
        assertThat(testAdapter.getContinent()).isNull();
        assertThat(testAdapter.getName()).isNull();
        assertThat(testAdapter.getPopulation()).isEqualTo(0);
    }

    @Test
    void shouldSetFromCsvBean() {
        //given
        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
        List<CsvBean> beans = csvRead.getBeanFromCSV();
        CountryAdapter testAdapter = new CountryAdapter((CsvBeanOWID) beans.get(0));

        //when
        testAdapter.setISO_code();
        testAdapter.setContinent();
        testAdapter.setName();
        testAdapter.setPopulation();

        //then
        assertThat(testAdapter.getISO_code()).isEqualTo("AFG");
        assertThat(testAdapter.getContinent()).isEqualTo("Asia");
        assertThat(testAdapter.getName()).isEqualTo("Afghanistan");
        assertThat(testAdapter.getPopulation()).isEqualTo(38928341);
    }

    @Test
    void shouldSetFromCsvBeanAnotherTest() {
        //given
        CsvRead csvRead = new CsvRead(new CsvBeanOWID());
        List<CsvBean> beans = csvRead.getBeanFromCSV();
        CountryAdapter testAdapter = new CountryAdapter((CsvBeanOWID) beans.get(beans.size()-1));

        //when
        testAdapter.setISO_code();
        testAdapter.setContinent();
        testAdapter.setName();
        testAdapter.setPopulation();

        //then
        assertThat(testAdapter.getISO_code()).isEqualTo("ZWE");
        assertThat(testAdapter.getContinent()).isEqualTo("Africa");
        assertThat(testAdapter.getName()).isEqualTo("Zimbabwe");
        assertThat(testAdapter.getPopulation()).isEqualTo(14862927);
    }

}