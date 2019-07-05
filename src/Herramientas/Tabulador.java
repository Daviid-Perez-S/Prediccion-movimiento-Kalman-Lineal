package Herramientas;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/*
 * Creado por: David Pérez S.
 * Matrícula: 163202
 * Materia: Desarrollo de Sistemas Inteligentes
 * Universidad Politécnica de Chiapas
 * Fecha de Creación: 17/06/2019
 */

/**
 * @author David Pérez S.
 */
public class Tabulador {
    
    private TableView<MatrizDatos> table;
    private ObservableList<MatrizDatos> data;

    public void dibujarTabla(float[][] matrizTablaGeneral, int numTabla) {
        
        table = new TableView<>();  // Tabla de datos JavaFX
        data =  FXCollections.observableArrayList();
        
        // Obtenemos los valores de la tabla de datos y los añadimos a la lista observable
        for (int i = 0; i < matrizTablaGeneral.length; i++) {
            data.add(new MatrizDatos((i), matrizTablaGeneral[i][5], matrizTablaGeneral[i][6], 
                    matrizTablaGeneral[i][7], matrizTablaGeneral[i][8], matrizTablaGeneral[i][9], matrizTablaGeneral[i][10]));
        }
        
        // Creamos nuestro stage
        Stage stage = new Stage();
        stage.setTitle("Predicción de movimiento");
        stage.setWidth(750);
        stage.setHeight(600);
        
        Scene scene = new Scene(new Group());
        final Label label;
        
        // Asignamos nombre a la tabla
        switch (numTabla) {
            case 0:
                label = new Label("Movimiento ideal");
                break;
            case 1:
                label = new Label("Movimiento observado");
                break;
            default:
                label = new Label("Predicción " + (numTabla - 1));
                break;
        }
        
        label.setFont(new Font(20));
        
        // Configuramos los datos de la tabla
        TableColumn colInterval = new TableColumn("Intervalo");    
        colInterval.setMinWidth(100);
        colInterval.setCellValueFactory(new PropertyValueFactory<>("interval"));
        
        TableColumn colPosX = new TableColumn("Posición X");    
        colPosX.setMinWidth(100);
        colPosX.setCellValueFactory(new PropertyValueFactory<>("posX"));
        
        TableColumn colPosY = new TableColumn("Posición Y");
        colPosY.setMinWidth(100);
        colPosY.setCellValueFactory(new PropertyValueFactory<>("posY"));
        
        TableColumn colVelX = new TableColumn("Velocidad X");
        colVelX.setMinWidth(100);
        colVelX.setCellValueFactory(new PropertyValueFactory<>("velX"));
        
        TableColumn colVelY = new TableColumn("Velocidad Y");
        colVelY.setMinWidth(100);
        colVelY.setCellValueFactory(new PropertyValueFactory<>("velY"));
        
        TableColumn colDistanciaR = new TableColumn("Distancia R");
        colDistanciaR.setMinWidth(100);
        colDistanciaR.setCellValueFactory(new PropertyValueFactory<>("distanciaR"));
        
        TableColumn colAnguloTeta = new TableColumn("Angulo θ");
        colAnguloTeta.setMinWidth(100);
        colAnguloTeta.setCellValueFactory(new PropertyValueFactory<>("anguloTeta"));
        
        // Agregamos los datos a la tabla
        table.setItems(data);
        table.getColumns().addAll(colInterval, colPosX, colPosY, colVelX, colVelY, colDistanciaR, colAnguloTeta);
        
        // Dibujamos la tabla
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        table.setPrefHeight(500);
        vbox.getChildren().addAll(label, table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
    
    // Clase de ayuda para almacenar los datos de la matriz de datos general en el Table View
    public static class MatrizDatos {
        
        private final SimpleStringProperty interval;
        private final SimpleStringProperty posX;
        private final SimpleStringProperty posY;
        private final SimpleStringProperty velX;
        private final SimpleStringProperty velY;
        private final SimpleStringProperty distanciaR;
        private final SimpleStringProperty anguloTeta;
        
        private MatrizDatos(int interval, float posX, float posY, float velX, float velY, float distanciaR, float anguloTeta) {
            this.interval = new SimpleStringProperty(String.valueOf(interval));
            this.posX = new SimpleStringProperty(String.valueOf(posX));
            this.posY = new SimpleStringProperty(String.valueOf(posY));
            this.velX = new SimpleStringProperty(String.valueOf(velX));
            this.velY = new SimpleStringProperty(String.valueOf(velY));
            this.distanciaR = new SimpleStringProperty(String.valueOf(distanciaR));
            this.anguloTeta = new SimpleStringProperty(String.valueOf(anguloTeta));
        }
        
        public String getInterval() {
            return interval.get();
        }
        
        public String getPosX() {
            return posX.get();
        }
        
        public String getPosY() {
            return posY.get();
        }
        
        public String getVelX() {
            return velX.get();
        }
        
        public String getVelY() {
            return velY.get();
        }
        
        public String getDistanciaR() {
            return distanciaR.get();
        }
        
        public String getAnguloTeta() {
            return anguloTeta.get();
        }
    }
}
