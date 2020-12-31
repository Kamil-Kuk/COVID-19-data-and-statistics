package main.CsvRead;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

class CsvBeanCovidDataTest {

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