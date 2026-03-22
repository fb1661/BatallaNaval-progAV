/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author taka
 */
public class Barco {
    // ─── Atributos ────────────────────────────────────────────────────────────

    /** Número de casillas que ocupa el barco. */
    private int longitud;

    /**
     * Coordenadas exactas [fila][columna] de cada casilla que ocupa el barco.
     * posicion[i] = {fila, columna} de la i-ésima celda.
     */
    private int[][] posicion;

    /**
     * Orientación del barco: "HORIZONTAL" o "VERTICAL".
     */
    private String orientacion;

    /** Cantidad de golpes recibidos. El barco se hunde cuando golpes == longitud. */
    private int golpes;

    // ─── Constructor ──────────────────────────────────────────────────────────

    /**
     * Crea un barco con la longitud y orientación indicadas.
     * Las posiciones se asignan después mediante {@link #setPosicion(int[][])}.
     *
     * @param longitud    número de casillas (2, 3, 4 o 5)
     * @param orientacion "HORIZONTAL" o "VERTICAL"
     */
    public Barco(int longitud, String orientacion) {
        this.longitud    = longitud;
        this.orientacion = orientacion;
        this.golpes      = 0;
        this.posicion    = new int[longitud][2];
    }

    // ─── Métodos ──────────────────────────────────────────────────────────────

    /**
     * Registra un golpe recibido por el barco.
     * Se llama cada vez que una casilla ocupada por este barco es impactada.
     */
    public void recibirGolpe() {
        if (!estaHundido()) {
            golpes++;
        }
    }

    /**
     * Determina si el barco ha sido completamente hundido.
     *
     * @return true si todos sus segmentos han sido impactados
     */
    public boolean estaHundido() {
        return golpes >= longitud;
    }

    /**
     * Comprueba si el barco ocupa la casilla indicada por (fila, columna).
     *
     * @param fila    fila a verificar
     * @param columna columna a verificar
     * @return true si alguna de sus posiciones coincide
     */
    public boolean ocupaCasilla(int fila, int columna) {
        for (int[] coord : posicion) {
            if (coord[0] == fila && coord[1] == columna) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve cuántos golpes le faltan para hundirse.
     *
     * @return diferencia entre longitud y golpes recibidos
     */
    public int golpesFaltantes() {
        return longitud - golpes;
    }

    // ─── Getters y Setters ────────────────────────────────────────────────────

    public int getLongitud() {
        return longitud;
    }

    public int[][] getPosicion() {
        return posicion;
    }

    /**
     * Asigna las coordenadas que ocupa este barco en el tablero.
     *
     * @param posicion arreglo de [longitud][2] con pares {fila, columna}
     */
    public void setPosicion(int[][] posicion) {
        this.posicion = posicion;
    }

    public String getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(String orientacion) {
        this.orientacion = orientacion;
    }

    public int getGolpes() {
        return golpes;
    }

    @Override
    public String toString() {
        return "Barco[longitud=" + longitud
                + ", orientacion=" + orientacion
                + ", golpes=" + golpes + "/" + longitud
                + ", hundido=" + estaHundido() + "]";
    }
}
