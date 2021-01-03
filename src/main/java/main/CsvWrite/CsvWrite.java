package main.CsvWrite;

import DB.entities.CovidData;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.ResultSetHelperService;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CsvWrite {
    //final String FILE_NAME = "src/main/resources/csv/result.csv";

    public static void writeCsvFromBean(List<CovidData> list, String fileName) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
        String FILE_NAME = "src/main/resources/csv/" + fileName + ".csv";

        Writer writer = new FileWriter(FILE_NAME);
        StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder<CovidData>(writer)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();
        sbc.write(list);
        writer.close();
    }

//    public static void writeCsvFromResultSet(ResultSet resultSet, String fileName) throws SQLException, IOException {
//        ResultSetHelperService service = new ResultSetHelperService();
//        StringWriter writer = new StringWriter(); // put your own writer here
//        CSVWriterBuilder builder = new CSVWriterBuilder(writer);
//
//        ICSVWriter csvWriter = builder
//                .withResultSetHelper(service)
//                .build();
//
//        csvWriter.writeAll(resultSet, true);
//    }
}
