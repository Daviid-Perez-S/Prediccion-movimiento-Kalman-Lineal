/*
 * Creado por: David Pérez S.
 * Matrícula: 163202
 * Materia: Desarrollo de Sistemas Inteligentes
 * Universidad Politécnica de Chiapas
 * Fecha de Creación: 12/06/2019
 */

import Herramientas.Graficador;
import Herramientas.Tabulador;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 *
 * @author David Pérez S.
 */
public class ControllerHome implements Initializable {

    @FXML private JFXTextField posInicialX;
    @FXML private JFXTextField posInicialY;
    @FXML private JFXTextField velInicialX;
    @FXML private JFXTextField velInicialY;
    @FXML private JFXTextField ruidoPos;
    @FXML private JFXTextField ruidoVel;
    @FXML private JFXTextField numTiempos;
    @FXML private JFXTextField numPredicciones;
    @FXML private JFXTextField observadorX;
    @FXML private JFXTextField observadorY;
    @FXML private JFXCheckBox cbShowTables;
    @FXML private JFXCheckBox cbShowGraph;
    
    private float posicionX;
    private float posicionY;
    private float velocidadX;
    private float velocidadY;
    private float ruidoPosicion;
    private float ruidoVelocidad;
    private int numeroTiempos;
    private int numeroPredicciones;
    private float posicionObservadorX;
    private float posicionObservadorY;
    
    @FXML
    private void version() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Predicción de movimiento");
        alert.setContentText("Versión 1.1.0\n\nHecho por: David Pérez S. y Luis Fernando Hernández Morales\nMatrícula: 163202\nI.D.S. / Universidad Politécnica de Chiapas");
        alert.showAndWait();
    }

    @FXML
    private void iniciarPrediccion() {
        
        if(obtenerValoresFrontend()) {
            
            // Estructuras que nos serán de ayuda
            ArrayList<float[][]> arrayMatrices = new ArrayList<>();     // Array en donde se guardarán todas las tablas generales creadas
            float[][] matrizTablaGeneral; // Matriz donde se almacenan los valores de una tabla general
            
            // Llenamos las tablas con los valores necesarios
            for (int i = 0; i <= (numeroPredicciones + 1); i++) {
                matrizTablaGeneral = new float[numeroTiempos + 1][11];
                if(i == 0)
                    arrayMatrices.add(llenarTablaGeneral(matrizTablaGeneral, true));
                else
                    arrayMatrices.add(llenarTablaGeneral(matrizTablaGeneral, false));
                imprimirTabla(arrayMatrices.get(i));
            }
            // Llenamos la tabla (array en realidad) con los datos de la posición del observador
            float[][] arrayObservador = new float[1][7];
            arrayMatrices.add(llenarMatrizObservador(arrayObservador));
            imprimirTabla(arrayMatrices.get(arrayMatrices.size()-1));
            
            System.out.println("*************************************************************************");
            
            // Si está seleccionado el checkBox "Mostrar Gráfica", esto se ejecutará
            if(cbShowGraph.isSelected()) {
                // Graficamos los datos de las tablas en una gráfica de líneas
                Graficador graf = new Graficador();
                graf.graficarDatos(arrayMatrices);
            }
            // Si está seleccionado el checkBox "Mostrar Tablas", esto se ejecutará
            if(cbShowTables.isSelected()) {
                // Tabulamos los datos de las tablas
                Tabulador tab = new Tabulador();
                for (int i = 0; i < (arrayMatrices.size() - 1); i++) {
                    tab.dibujarTabla(arrayMatrices.get(i), i);
                }
            }
        }
    }
    
    private float[][] llenarTablaGeneral(float[][] matrizTablaGeneral, boolean banderaMatrizIdeal) {
        // Llenado de la tabla general
        for (int i = 0; i < matrizTablaGeneral.length; i++) {
            for (int j = 0; j < matrizTablaGeneral[i].length; j++) {

                if (j == 0) {
                    matrizTablaGeneral[i][j] = i;
                }
                   
                // Esto sólo se aplica para la primera fila de la tabla
                if (i == 0) {
                    if (j >= 1 && j <= 4) {
                        matrizTablaGeneral[i][j] = 3;
                    } else {
                        switch (j) {
                            case 5:
                                matrizTablaGeneral[i][j] = posicionX;
                                break;
                            case 6:
                                matrizTablaGeneral[i][j] = posicionY;
                                break;
                            case 7:
                                matrizTablaGeneral[i][j] = velocidadX;
                                break;
                            case 8:
                                matrizTablaGeneral[i][j] = velocidadY;
                                break;
                            case 9:
                                float[] puntoA = {matrizTablaGeneral[i][5], matrizTablaGeneral[i][6]};
                                float[] puntoB = {posicionObservadorX ,posicionObservadorY};
                                
                                matrizTablaGeneral[i][j] = calcularDistanciaEntreDosPuntos(puntoA, puntoB);
                                break;
                            case 10:
                                float[] vectorA = {matrizTablaGeneral[i][5], matrizTablaGeneral[i][6]};
                                float[] vectorB = {posicionObservadorX ,posicionObservadorY};
                                
                                matrizTablaGeneral[i][j] = calcularAnguloEntreDosVesctores(vectorA, vectorB);
                                break;
                        }
                    }
                } else if (j >= 1 && j <= 2) {
                    if(banderaMatrizIdeal)
                        matrizTablaGeneral[i][j] = 0;
                    else
                        matrizTablaGeneral[i][j] = generarNumerosAleatorios(ruidoVelocidad * -1, ruidoVelocidad);
                } else if (j >= 3 && j <= 4) {
                    if(banderaMatrizIdeal)
                        matrizTablaGeneral[i][j] = 0;
                    else
                        matrizTablaGeneral[i][j] = generarNumerosAleatorios(ruidoPosicion * -1, ruidoPosicion);
                } else {
                    double temp;
                    switch (j) {
                        case 5:
                            temp = matrizTablaGeneral[i-1][j] + matrizTablaGeneral[i-1][j+2] + matrizTablaGeneral[i][j-1];
                            matrizTablaGeneral[i][j] = (float) formatearDecimales(temp, 4);
                            break;
                        case 6:
                            temp = matrizTablaGeneral[i-1][j] + matrizTablaGeneral[i-1][j+2] + matrizTablaGeneral[i][j-3];
                            matrizTablaGeneral[i][j] = (float) formatearDecimales(temp, 4);
                            break;
                        case 7:
                            temp = matrizTablaGeneral[i-1][j] + matrizTablaGeneral[i][j-5];
                            matrizTablaGeneral[i][j] = (float) formatearDecimales(temp, 4);
                            break;
                        case 8:
                            temp = matrizTablaGeneral[i-1][j] + matrizTablaGeneral[i][j-7];
                            matrizTablaGeneral[i][j] = (float) formatearDecimales(temp, 4);
                            break;
                        case 9:
                            float[] puntoA = {matrizTablaGeneral[i][5], matrizTablaGeneral[i][6]};
                            float[] puntoB = {posicionObservadorX ,posicionObservadorY};

                            matrizTablaGeneral[i][j] = calcularDistanciaEntreDosPuntos(puntoA, puntoB);
                            break;
                        case 10:
                            float[] vectorA = {matrizTablaGeneral[i][5], matrizTablaGeneral[i][6]};
                            float[] vectorB = {posicionObservadorX ,posicionObservadorY};

                            matrizTablaGeneral[i][j] = calcularAnguloEntreDosVesctores(vectorA, vectorB);
                            break;                
                    }
                }
            }
        }
        return matrizTablaGeneral;
    }
    
    private float[][] llenarMatrizObservador(float[][] matrizObservador) {
        for (float[] fila : matrizObservador) {
            for (int j = 0; j < fila.length; j++) {
                if (j >= 0 && j <= 4) {
                    fila[j] = 0;
                } else if (j == 5) {
                    fila[j] = posicionObservadorX;
                } else {
                    fila[j] = posicionObservadorY;
                }
            }
        }
        return matrizObservador;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Algo.
    }
    
    private void imprimirTabla(float[][] matrizTablaGeneral) {
        System.out.println("Matriz\n");
        for (float[] fila : matrizTablaGeneral) {
            for (int j = 0; j < matrizTablaGeneral[0].length; j++) {
                System.out.print(fila[j] + "|");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------");
    }
    
    private boolean obtenerValoresFrontend() {
        
        // Comprobamos si hay campos vacíos
        if(posInicialX.getText().equals("") || posInicialY.getText().equals("") || velInicialX.getText().equals("") 
            || velInicialY.getText().equals("") || ruidoPos.getText().equals("") || ruidoVel.getText().equals("") 
            || numTiempos.getText().equals("") || numPredicciones.getText().equals("") || observadorX.getText().equals("")
            || observadorY.getText().equals("")) {
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Predicción de movimiento");
            alert.setContentText("Debes de llenar todos los campos");
            alert.showAndWait();
            return false;
        } else if(numTiempos.getText().equals("0")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Predicción de movimiento");
            alert.setContentText("Debe haber al menos 1 tiempo/intervalo");
            alert.showAndWait();
            return false;
        } else if(!cbShowGraph.isSelected() && !cbShowTables.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Predicción de movimiento");
            alert.setContentText("Debes seleccionar al menos 1 opción");
            alert.showAndWait();
            return false;
        } else {
            // Obtenemos los valores del frontend
            posicionX = Float.valueOf(posInicialX.getText());
            posicionY = Float.valueOf(posInicialY.getText());
            velocidadX = Float.valueOf(velInicialX.getText());
            velocidadY = Float.valueOf(velInicialY.getText());
            ruidoPosicion = Float.valueOf(ruidoPos.getText());
            ruidoVelocidad = Float.valueOf(ruidoVel.getText());
            numeroTiempos = Integer.valueOf(numTiempos.getText());
            numeroPredicciones = Integer.valueOf(numPredicciones.getText());
            posicionObservadorX = Integer.valueOf(observadorX.getText());
            posicionObservadorY = Integer.valueOf(observadorY.getText());
            return true;            
        } 
    }

    private float generarNumerosAleatorios(float min, float max) {
        double random = (Math.random() * (max - min)) + min;
        return (float) formatearDecimales(random, 4);
    }
    
    private double formatearDecimales(double numero, int numDecimales) {
        return Math.round(numero * Math.pow(10, numDecimales)) / Math.pow(10, numDecimales);
    }
    
    private float calcularAnguloEntreDosVesctores(float[] puntoA, float[] puntoB) {
        
        float tempX = puntoA[0] - puntoB[0];
        float tempY = puntoA[1] - puntoB[1];
        double result = tempX / tempY;
        
        double grados = Math.abs(Math.toDegrees(Math.atan(result)));
        
        // Se retorna el angulo redondeado
        return (float) formatearDecimales(grados, 4);
    }
    
    private float calcularDistanciaEntreDosPuntos(float[] puntoA, float[] puntoB) {
        
        // Calcular distancia horizontal
        double distanciaHorizontal = Math.pow(puntoB[0] - puntoA[0], 2);
        
        // Calcular distancia horizontal
        double distanciaVertical = Math.pow(puntoB[1] - puntoA[1], 2);
        
        // Se calcula la distancia entre ambas dimensiones
        double distanciaFinal = Math.sqrt(distanciaHorizontal + distanciaVertical);
        
        // Se retorna el angulo redondeado
        return (float) formatearDecimales(distanciaFinal, 4);
    }
}
