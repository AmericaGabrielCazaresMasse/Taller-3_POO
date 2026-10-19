package com.example.sudoku.vista;

import com.example.sudoku.modelo.Sudoku;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SudokuVista {

    private BorderPane panel = new BorderPane();
    private GridPane cuadricula = new GridPane();
    private TextField[][] campos = new TextField[9][9];

    private TextField ultimaCasilla = null;

    private Label titulo = new Label(" ★ ☆ ★ SUDOKU ★ ☆ ★ ");
    private Label estado = new Label("Elige un nivel");

    private Button btnFacil = new Button("Fácil");
    private Button btnMedio = new Button("Medio");
    private Button btnDificil = new Button("Difícil");
    private Button btnVerificar = new Button("Verificar");
    private Button btnReiniciar = new Button("Reiniciar");

    public SudokuVista() {
        crearCuadricula();

        titulo.getStyleClass().add("titulo");
        estado.getStyleClass().add("estado");

        HBox botones = new HBox(10);
        botones.setAlignment(Pos.CENTER);
        botones.getChildren().addAll(btnFacil, btnMedio, btnDificil, btnVerificar, btnReiniciar);

        VBox arriba = new VBox(15);
        arriba.setAlignment(Pos.CENTER);
        arriba.setPadding(new Insets(0, 0, 15, 0));
        arriba.getChildren().addAll(titulo, botones);

        panel.setTop(arriba);
        panel.setCenter(cuadricula);
        panel.setBottom(estado);
        panel.setPadding(new Insets(20));
        BorderPane.setAlignment(estado, Pos.CENTER);
        BorderPane.setMargin(estado, new Insets(15, 0, 0, 0));
    }

    private void crearCuadricula() {
        cuadricula.setAlignment(Pos.CENTER);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField campo = new TextField();
                campo.setPrefSize(50, 50);
                campo.setAlignment(Pos.CENTER);
                campo.getStyleClass().add("casilla");

                int arriba = 1;
                int derecha = 0;
                int abajo = 0;
                int izquierda = 1;
                if (i % 3 == 0) {
                    arriba = 3;
                }
                if (j % 3 == 0) {
                    izquierda = 3;
                }
                if (i == 8) {
                    abajo = 3;
                }
                if (j == 8) {
                    derecha = 3;
                }
                campo.setStyle("-fx-border-width: " + arriba + " " + derecha + " "
                        + abajo + " " + izquierda + ";");

                campos[i][j] = campo;
                cuadricula.add(campo, j, i);
            }
        }
    }


    public void mostrar(Sudoku sudoku) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int valor = sudoku.getValor(i, j);

                if (valor == 0) {
                    campos[i][j].setText("");
                } else {
                    campos[i][j].setText("" + valor);
                }

                campos[i][j].getStyleClass().remove("fija");
                if (sudoku.esFija(i, j)) {
                    campos[i][j].setEditable(false);
                    campos[i][j].getStyleClass().add("fija");
                } else {
                    campos[i][j].setEditable(true);
                }
            }
        }
        marcarErrores(sudoku);
    }

    public void marcarErrores(Sudoku sudoku) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int valor = sudoku.getValor(i, j);

                campos[i][j].getStyleClass().remove("error");
                if (valor != 0 && !sudoku.esNumeroCorrecto(i, j, valor)) {
                    campos[i][j].getStyleClass().add("error");
                }
            }
        }
    }

    public void marcarUltima(int fila, int col) {
        quitarUltima();
        ultimaCasilla = campos[fila][col];
        ultimaCasilla.getStyleClass().add("ultima");
    }

    public void quitarUltima() {
        if (ultimaCasilla != null) {
            ultimaCasilla.getStyleClass().remove("ultima");
            ultimaCasilla = null;
        }
    }

    public BorderPane getPanel() {
        return panel;
    }

    public TextField getCampo(int fila, int col) {
        return campos[fila][col];
    }

    public Button getBtnFacil() {
        return btnFacil;
    }

    public Button getBtnMedio() {
        return btnMedio;
    }

    public Button getBtnDificil() {
        return btnDificil;
    }

    public Button getBtnVerificar() {
        return btnVerificar;
    }

    public Button getBtnReiniciar() {
        return btnReiniciar;
    }

    public void setEstado(String texto) {
        estado.setText(texto);
    }
}