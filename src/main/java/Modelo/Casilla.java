/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author taka
 */
public class Casilla {
    // ─── Atributos ────────────────────────────────────────────────────────────

    /** Índice de fila (0–9). */
    private int fila;

    /** Índice de columna (0–9). */
    private int columna;

    /** Estado actual de la casilla. Por defecto es AGUA. */
    private Estado estado;

    // ─── Constructor ──────────────────────────────────────────────────────────

    /**
     * Crea una casilla en la posición indicada con estado inicial AGUA.
     *
     * @param fila    índice de fila (0–9)
     * @param columna índice de columna (0–9)
     */
    public Casilla(int fila, int columna) {
        this.fila    = fila;
        this.columna = columna;
        this.estado  = Estado.AGUA;
    }

    // ─── Métodos ──────────────────────────────────────────────────────────────

    /**
     * Indica si la casilla ya fue disparada (no es AGUA ni BARCO intacto).
     *
     * @return true si el estado es GOLPE_AGUA, GOLPE_BARCO o HUNDIDO
     */
    public boolean fueDisparada() {
        return estado == Estado.GOLPE_AGUA
                || estado == Estado.GOLPE_BARCO
                || estado == Estado.HUNDIDO;
    }

    /**
     * Indica si la casilla contiene parte de un barco (aún no golpeado).
     *
     * @return true si el estado es BARCO
     */
    public boolean tieneBarco() {
        return estado == Estado.BARCO;
    }

    // ─── Getters y Setters ────────────────────────────────────────────────────

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public Estado getEstado() {
        return estado;
    }

    /**
     * Actualiza el estado de la casilla.
     *
     * @param estado nuevo estado a asignar
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Casilla[" + fila + "," + columna + "]=" + estado;

    }
}
