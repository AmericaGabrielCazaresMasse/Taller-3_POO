package com.example.sudoku.controlador;

import com.example.sudoku.modelo.Sudoku;
import com.example.sudoku.vista.SudokuVista;

public class SudokuControlador {

    private Sudoku modelo;
    private SudokuVista vista;
    private int nivelActual = 1;

    private boolean cambiandoTexto = false;

    public SudokuControlador(Sudoku modelo, SudokuVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        vista.getBtnFacil().setOnAction(e -> cargarNivel(1));
        vista.getBtnMedio().setOnAction(e -> cargarNivel(2));
        vista.getBtnDificil().setOnAction(e -> cargarNivel(3));
        vista.getBtnReiniciar().setOnAction(e -> cargarNivel(nivelActual));
        vista.getBtnVerificar().setOnAction(e -> verificar());

        conectarCasillas();
        cargarNivel(1);
    }

    private void cargarNivel(int nivel) {
        nivelActual = nivel;
        modelo.cargarTablero(nivel);

        cambiandoTexto = true;
        vista.mostrar(modelo);
        cambiandoTexto = false;

        vista.quitarUltima();

        if (nivel == 1) {
            vista.setEstado("Nivel Fácil cargado");
        } else if (nivel == 2) {
            vista.setEstado("Nivel Medio cargado");
        } else {
            vista.setEstado("Nivel Difícil cargado");
        }
    }

    private void verificar() {
        if (modelo.estaResuelto()) {
            vista.setEstado("¡Sudoku resuelto! Elige un nivel para volver a jugar");
        } else if (!modelo.estaCompleto()) {
            vista.setEstado("Aún faltan casillas por llenar");
        } else {
            vista.setEstado("Hay números repetidos, revisa el tablero");
        }
    }

    private void conectarCasillas() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                final int fila = i;
                final int col = j;

                vista.getCampo(i, j).textProperty().addListener((obs, viejo, nuevo) -> {
                    if (!cambiandoTexto) {
                        alEscribir(fila, col, viejo, nuevo);
                    }
                });
            }
        }
    }

    private void alEscribir(int fila, int col, String viejo, String nuevo) {
        if (nuevo.length() > 1) {
            nuevo = nuevo.substring(nuevo.length() - 1);
            ponerTexto(fila, col, nuevo);
        }

        if (nuevo.isEmpty()) {
            modelo.ponerNumero(fila, col, 0);
            vista.marcarErrores(modelo);
            vista.quitarUltima();
            vista.setEstado("");
            return;
        }

        char letra = nuevo.charAt(0);
        if (letra < '1' || letra > '9') {
            ponerTexto(fila, col, viejo);
            return;
        }

        int numero = Integer.parseInt(nuevo);
        modelo.ponerNumero(fila, col, numero);
        vista.marcarErrores(modelo);
        vista.marcarUltima(fila, col);

        if (modelo.estaCompleto()) {
            vista.setEstado("Tablero lleno. Haz clic en Verificar");
        } else if (!modelo.esNumeroCorrecto(fila, col, numero)) {
            vista.setEstado("El " + numero + " se repite en la fila, columna o cuadro");
        } else {
            vista.setEstado("");
        }
    }

    private void ponerTexto(int fila, int col, String texto) {
        cambiandoTexto = true;
        vista.getCampo(fila, col).setText(texto);
        cambiandoTexto = false;
    }
}