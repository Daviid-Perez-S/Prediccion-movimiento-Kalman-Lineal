package Herramientas;


import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/*
 * Creado por: David Pérez S.
 * Matrícula: 163202
 * Materia: 163202
 * Universidad Politécnica de Chiapas
 * Fecha de Creación: 13/06/2018 
 */

/**
 * @author David Pérez S.
 */
public class Graficador {

    public void graficarDatos(ArrayList<float[][]> arrayMatricesDatos) {
        
        Stage stage = new Stage();
        stage.setTitle("Predicción de movimiento");
        
        final NumberAxis xAxis = new NumberAxis();  // Eje X
        xAxis.setLabel("Eje X");
        final NumberAxis yAxis = new NumberAxis();  // Eje Y
        yAxis.setLabel("Eje Y");
        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis,yAxis);   // Gráfica de líneas
       
        lineChart.setTitle("Gráfica de movimiento (Kalman Lineal)");
        XYChart.Series series;  // Serie de valores
        float[][] temp; // Matriz de valores temporal de ayuda
        
        for (int i = 0; i < arrayMatricesDatos.size(); i++) {
            series = new XYChart.Series();
            if(i == (arrayMatricesDatos.size() - 1))
                series.setName("Observador");
            else
                switch (i) {
                    case 0:
                        series.setName("Movimiento ideal");
                        break;
                    case 1:
                        series.setName("Movimiento observado");
                        break;
                    default:
                        series.setName("Predicción " + (i-1));
                        break;
                }
            temp = arrayMatricesDatos.get(i);
            for (float[] temp1 : temp) {
                series.getData().add(new XYChart.Data(temp1[5], temp1[6])); // Creamos los puntos de datos
            }
            lineChart.getData().addAll(series); // Añadimos la serie de valores a la gráfica
        }
        
        Scene scene  = new Scene(lineChart,800,800);       
       
        stage.setScene(scene);
        stage.show();
    }
}
