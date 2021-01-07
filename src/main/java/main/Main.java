package main;

import DB.entities.CovidData;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import javax.persistence.EntityManager;

import java.lang.reflect.Field;
import java.time.ZoneId;
import java.util.List;

import static main.UserInterface.*;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DAO.openConnection();
        EntityManager manager = DAO.getManager();
        initialMethod(manager);
        List<CovidData> output = getOutput();
        drawGraph(stage, output);
        DAO.closeConnection();
    }

    private void drawGraph(Stage stage, List<CovidData> output) {
        if (output.size() > 0 && isDrawable()) {
            stage.setTitle("COVID-19 Data and statistics");
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Date");
            //creating the chart
            final LineChart<String, Number> lineChart =
                    new LineChart<String, Number>(xAxis, yAxis);

            lineChart.setTitle("COVID-19 Data and statistics");
            lineChart.setCreateSymbols(false);

            //defining a series
            XYChart.Series series = new XYChart.Series();
            series.setName(output.get(0).getCountry().getName());

            for (CovidData entry : output) {
                series.getData().add(new XYChart.Data<>(entry
                        .getDate()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .toString(),
                        entry.getTotal_cases()));
            }
            Scene scene = new Scene(lineChart, 800, 600);
            lineChart.getData().add(series);
            stage.setScene(scene);
            stage.show();

        } else {
            System.out.println("Cannot draw graph for this query");
        }
    }

//    private Field getResultNameType(List<CovidData> output) {
//        return Field.class;
//
//    }
}

