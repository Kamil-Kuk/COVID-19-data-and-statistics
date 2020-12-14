package main.CsvRead;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CsvBeanOWIDTest {

    @Test
    void shouldSetStringDateReturnLocalDate() {
        //given
        CsvBeanOWID csvBean = new CsvBeanOWID();
        String date = "1991-01-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");

        //when
        csvBean.setDate(date);
        LocalDate result = csvBean.getDate();

        //then
        Assertions.assertEquals(LocalDate.parse("1991-01-01",formatter),result);
    }

    @Test
    void shouldCSVFileExist() {
        //given
        CsvBeanOWID csvBean = new CsvBeanOWID();

        //when
        File file = csvBean.getMY_PATH().toFile();

        //then
        Assertions.assertTrue(file.isFile());
    }

}