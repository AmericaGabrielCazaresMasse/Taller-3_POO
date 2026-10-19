package com.example.sudoku;

import com.example.sudoku.controlador.SudokuControlador;
import com.example.sudoku.modelo.Sudoku;
import com.example.sudoku.vista.SudokuVista;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        InputStream fuente = getClass().getResourceAsStream("PressStart2P-Regular.ttf");
        if (fuente != null) {
            Font.loadFont(fuente, 12);
        }

        Sudoku modelo = new Sudoku();
        SudokuVista vista = new SudokuVista();
        new SudokuControlador(modelo, vista);

        Scene escena = new Scene(vista.getPanel());
        escena.getStylesheets().add(getClass().getResource("estilo.css").toExternalForm());

        stage.setTitle("Sudoku");
        stage.setScene(escena);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}