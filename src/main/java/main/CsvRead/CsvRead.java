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

    private CsvBean csvBean;

    public CsvRead(CsvBean csvBean) {
        this.csvBean = csvBean;
    }

    public List<CsvBean> getBeanFromCSV() {
        try (BufferedReader br = Files.newBufferedReader(csvBean.getMY_PATH(), StandardCharsets.UTF_8)) {
            HeaderColumnNameMappingStrategy<CsvBean> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(csvBean.getClass());

            CsvToBean<CsvBean> csvToBean = new CsvToBeanBuilder<CsvBean>(br)
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
