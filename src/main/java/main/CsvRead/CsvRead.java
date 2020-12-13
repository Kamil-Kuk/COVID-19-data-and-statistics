package main.CsvRead;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class CsvRead {

    private CsvBeanOWID csvBean;


    public CsvRead(CsvBeanOWID csvBean) {
        this.csvBean = csvBean;
    }

    public List<CsvBeanOWID> getBeanFromCSV() {
        try (BufferedReader br = Files.newBufferedReader(csvBean.getMyPath(), StandardCharsets.UTF_8)) {
            HeaderColumnNameMappingStrategy<CsvBeanOWID> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(CsvBeanOWID.class);

            CsvToBean<CsvBeanOWID> csvToBean = new CsvToBeanBuilder<CsvBeanOWID>(br)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
