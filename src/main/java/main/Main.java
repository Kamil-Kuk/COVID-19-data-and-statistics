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
        if (output.size() > 1 && isDrawable()) {
            stage.setTitle("COVID-19 Data and statistics");
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Date");

            //creating the chart
            final LineChart<String, Number> lineChart =
                    new LineChart<String, Number>(xAxis, yAxis);
            Field resultType = null;
            try {
                resultType = getResultType(output);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            lineChart.setTitle("COVID-19 Data and statistics - number of " + resultType
                    .getName()
                    .replace("_", " "));
            lineChart.setCreateSymbols(false);

            //defining a series
            XYChart.Series series = new XYChart.Series();
            series.setName(output.get(0).getCountry().getName());

            for (CovidData entry : output) {
                try {
                    resultType.setAccessible(true);
                    series.getData().add(new XYChart.Data<>(entry
                            .getDate()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                            .toString(),
                            resultType.get(entry)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            Scene scene = new Scene(lineChart, 800, 600);
            lineChart.getData().add(series);
            stage.setScene(scene);
            stage.show();

        } else {
            System.out.println("Cannot draw graph for this query");
        }
    }

//    private Field getResultType(List<CovidData> output) throws NoSuchFieldException {
//        //TODO -refactor this method
//        if(output.get(0).getTotal_cases() != null){
//            return output.get(0).getClass().getDeclaredField("total_cases");
//        } else if(output.get(0).getNew_cases() != null){
//            return output.get(0).getClass().getDeclaredField("new_cases");
//        } else if(output.get(0).getTotal_deaths() != null){
//            return output.get(0).getClass().getDeclaredField("total_deaths");
//        } else if(output.get(0).getNew_deaths() != null){
//            return output.get(0).getClass().getDeclaredField("new_deaths");
//        } else if(output.get(0).getIcu_patients() != null){
//            return output.get(0).getClass().getDeclaredField("icu_patients");
//        } else if(output.get(0).getHosp_patients() != null){
//            return output.get(0).getClass().getDeclaredField("hosp_patients");
//        } else if(output.get(0).getTotal_tests() != null){
//            return output.get(0).getClass().getDeclaredField("total_tests");
//        } else if(output.get(0).getNew_cases() != null){
//            return output.get(0).getClass().getDeclaredField("new_tests");
//        }
//        return null;
//    }

    private Field getResultType(List<CovidData> output) throws NoSuchFieldException {
        switch (getOptionInt()) {
            case 1:
                return output.get(0).getClass().getDeclaredField("total_cases");
            case 2:
                return output.get(0).getClass().getDeclaredField("new_cases");
            case 3:
                return output.get(0).getClass().getDeclaredField("total_deaths");
            case 4:
                return output.get(0).getClass().getDeclaredField("new_deaths");
            case 5:
                return output.get(0).getClass().getDeclaredField("icu_patients");
            case 6:
                return output.get(0).getClass().getDeclaredField("hosp_patients");
            case 7:
                return output.get(0).getClass().getDeclaredField("total_tests");
            case 8:
                return output.get(0).getClass().getDeclaredField("new_tests");
            case 9:
                return null;
        }
        return null;
    }
}

