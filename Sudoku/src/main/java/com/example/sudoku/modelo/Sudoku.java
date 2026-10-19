package com.example.sudoku.modelo;

public class Sudoku {

    private int[][] tablero = new int[9][9];
    private boolean[][] fijas = new boolean[9][9];

    private int[][] facil = {
            {0, 7, 2, 5, 0, 3, 0, 4, 0},
            {3, 0, 6, 8, 2, 1, 7, 0, 0},
            {1, 0, 0, 4, 0, 6, 2, 8, 0},
            {2, 8, 0, 1, 0, 0, 4, 3, 0},
            {0, 0, 3, 7, 0, 0, 5, 9, 0},
            {4, 5, 7, 0, 0, 0, 0, 0, 2},
            {5, 2, 0, 0, 3, 0, 0, 0, 0},
            {0, 0, 8, 6, 0, 4, 0, 0, 1},
            {9, 0, 0, 0, 0, 8, 0, 7, 5}
    };

    private int[][] medio = {
            {0, 1, 0, 8, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 2, 0, 0, 4, 0},
            {0, 0, 9, 0, 0, 6, 2, 0, 1},
            {0, 0, 0, 5, 0, 0, 1, 6, 0},
            {0, 9, 6, 2, 4, 0, 0, 8, 0},
            {0, 0, 0, 6, 8, 1, 0, 0, 0},
            {4, 6, 0, 0, 5, 0, 8, 0, 0},
            {8, 3, 0, 9, 0, 0, 0, 0, 5},
            {0, 2, 0, 3, 0, 8, 0, 0, 7}
    };

    private int[][] dificil = {
            {0, 3, 0, 2, 5, 0, 0, 4, 0},
            {0, 0, 8, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 4, 9, 0, 0, 5},
            {0, 0, 3, 0, 0, 6, 0, 0, 0},
            {0, 0, 0, 7, 0, 0, 1, 0, 0},
            {0, 0, 0, 5, 9, 0, 0, 0, 0},
            {0, 0, 0, 1, 2, 0, 8, 0, 9},
            {0, 2, 0, 0, 0, 4, 6, 3, 0},
            {0, 1, 0, 0, 0, 3, 5, 0, 0}
    };

    public void cargarTablero(int nivel) {
        int[][] plantilla = facil;
        if (nivel == 2) {
            plantilla = medio;
        }
        if (nivel == 3) {
            plantilla = dificil;
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tablero[i][j] = plantilla[i][j];
                if (plantilla[i][j] != 0) {
                    fijas[i][j] = true;
                } else {
                    fijas[i][j] = false;
                }
            }
        }
    }

    public int getValor(int fila, int col) {
        return tablero[fila][col];
    }

    public boolean esFija(int fila, int col) {
        return fijas[fila][col];
    }

    public void ponerNumero(int fila, int col, int numero) {
        if (fijas[fila][col]) {
            return;
        }
        tablero[fila][col] = numero;
    }

    public boolean validarFila(int fila, int col, int numero) {
        for (int j = 0; j < 9; j++) {
            if (j != col && tablero[fila][j] == numero) {
                return false;
            }
        }
        return true;
    }

    public boolean validarColumna(int fila, int col, int numero) {
        for (int i = 0; i < 9; i++) {
            if (i != fila && tablero[i][col] == numero) {
                return false;
            }
        }
        return true;
    }

    public boolean validarCuadro(int fila, int col, int numero) {
        int inicioFila = fila / 3 * 3;
        int inicioCol = col / 3 * 3;

        for (int i = inicioFila; i < inicioFila + 3; i++) {
            for (int j = inicioCol; j < inicioCol + 3; j++) {
                if ((i != fila || j != col) && tablero[i][j] == numero) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean esNumeroCorrecto(int fila, int col, int numero) {
        if (validarFila(fila, col, numero) && validarColumna(fila, col, numero)
                && validarCuadro(fila, col, numero)) {
            return true;
        }
        return false;
    }

    public boolean estaCompleto() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tablero[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean estaResuelto() {
        if (!estaCompleto()) {
            return false;
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!esNumeroCorrecto(i, j, tablero[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
}