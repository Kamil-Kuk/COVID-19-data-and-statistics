package main.CsvWrite;

import DB.entities.CovidData;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CsvWrite {
    //final String FILE_NAME = "src/main/resources/csv/result.csv";

    public static void writeCsvFromBean(List<CsvBean> list, String fileName) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
        String FILE_NAME = "src/main/export/" + fileName + ".csv";
        File theDir = new File("src/main/export");
        theDir.mkdir();

        Writer writer = new FileWriter(FILE_NAME);
        StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder<CsvBean>(writer)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();
        sbc.write(list);
        writer.close();
    }
}
