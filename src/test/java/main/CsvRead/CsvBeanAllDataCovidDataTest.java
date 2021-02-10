package main.CsvRead;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

class CsvBeanAllDataCovidDataTest {

    @Test
    void shouldCSVFileExist() {
        //given
        CsvBeanOWID csvBean = new CsvBeanOWID();

        String testFilePath = "src/test/resources/csv/owid-covid-data-test.csv";
        //when
        File file = Paths.get(testFilePath).toFile();

        //then
        Assertions.assertTrue(file.isFile());
    }

}
