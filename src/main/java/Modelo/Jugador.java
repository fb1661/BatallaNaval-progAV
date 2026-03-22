/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author taka
 */
import java.util.List;
public class Jugador {
    // ─── Atributos protegidos ─────────────────────────────────────────────────

    /** Tablero personal del jugador donde se colocan sus barcos. */
    protected Tablero tablero;

    /** Lista de barcos que posee el jugador (5 en total según los requerimientos). */
    protected List<Barco> barcos;

    /** Nombre identificador del jugador. */
    protected String nombre;

    // ─── Constructor ──────────────────────────────────────────────────────────

    /**
     * Crea un jugador con nombre dado e inicializa su tablero.
     *
     * @param nombre nombre del jugador
     */
    public Jugador(String nombre) {
        this.nombre  = nombre;
        this.tablero = new Tablero();
    }

    // ─── Métodos abstractos ───────────────────────────────────────────────────

    /**
     * Coloca los 5 barcos reglamentarios en el tablero del jugador.
     * La implementación varía: el humano los coloca manualmente
     * y el bot los posiciona de forma aleatoria.
     */
    public abstract void colocarBarcos();

    /**
     * Realiza un disparo al tablero enemigo en la coordenada indicada.
     *
     * @param fila    fila del disparo (0–9)
     * @param col     columna del disparo (0–9)
     * @return resultado: "AGUA", "GOLPE" o "HUNDIDO"
     */
    public abstract String realizarDisparo(int fila, int col);

    // ─── Métodos concretos ────────────────────────────────────────────────────

    /**
     * Verifica si todos los barcos de este jugador han sido hundidos,
     * lo que implica que ha perdido la partida.
     *
     * @return true si el jugador ha perdido
     */
    public boolean perdio() {
        return tablero.todosHundidos();
    }

    /**
     * Reinicia el tablero y la lista de barcos del jugador
     * para comenzar una nueva partida.
     */
    public void reiniciar() {
        tablero.reiniciar();
        if (barcos != null) {
            barcos.clear();
        }
    }

    // ─── Getters y Setters ────────────────────────────────────────────────────

    public Tablero getTablero() {
        return tablero;
    }

    public List<Barco> getBarcos() {
        return barcos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
