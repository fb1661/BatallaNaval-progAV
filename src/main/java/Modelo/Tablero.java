package Modelo;

import java.util.ArrayList;
import java.util.List;
public class Tablero {
    // ─── Constantes ───────────────────────────────────────────────────────────

    /** Tamaño del tablero (filas y columnas). */
    public static final int TAMANIO = 10;

    // ─── Atributos ────────────────────────────────────────────────────────────

    /** Matriz principal: casillas[fila][columna]. */
    private Casilla[][] casillas;

    /** Lista de barcos colocados en este tablero. */
    private List<Barco> barcos;

    // ─── Constructor ──────────────────────────────────────────────────────────

    /**
     * Inicializa el tablero creando todas las casillas en estado AGUA
     * y una lista vacía de barcos.
     */
    public Tablero() {
        casillas = new Casilla[TAMANIO][TAMANIO];
        barcos   = new ArrayList<>();

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                casillas[i][j] = new Casilla(i, j);
            }
        }
    }

    // ─── Métodos públicos ─────────────────────────────────────────────────────

    /**
     * Intenta colocar un barco en el tablero.
     * Valida que no se salga de los límites ni se superponga con otro barco.
     *
     * @param barco       barco a colocar
     * @param fila        fila de la celda inicial
     * @param col         columna de la celda inicial
     * @param orientacion "HORIZONTAL" o "VERTICAL"
     * @return true si el barco fue colocado con éxito; false si la posición es inválida
     */
    public boolean colocarBarco(Barco barco, int fila, int col, String orientacion) {
        if (!esPosicionValida(barco, fila, col, orientacion)) {
            return false;
        }

        int longitud = barco.getLongitud();
        int[][] posiciones = new int[longitud][2];

        for (int i = 0; i < longitud; i++) {
            int f = (orientacion.equalsIgnoreCase("VERTICAL"))   ? fila + i : fila;
            int c = (orientacion.equalsIgnoreCase("HORIZONTAL")) ? col  + i : col;

            casillas[f][c].setEstado(Estado.BARCO);
            posiciones[i][0] = f;
            posiciones[i][1] = c;
        }

        barco.setPosicion(posiciones);
        barco.setOrientacion(orientacion.toUpperCase());
        barcos.add(barco);
        return true;
    }

    /**
     * Procesa un disparo en la coordenada indicada.
     * Actualiza el estado de la casilla y el barco correspondiente si es impacto.
     *
     * @param fila fila del disparo (0–9)
     * @param col  columna del disparo (0–9)
     * @return resultado del disparo: "AGUA", "GOLPE" o "HUNDIDO"
     * @throws IllegalArgumentException si la coordenada ya fue disparada o está fuera de rango
     */
    public String recibirDisparo(int fila, int col) {
        validarCoordenada(fila, col);

        Casilla casilla = casillas[fila][col];

        if (casilla.fueDisparada()) {
            throw new IllegalArgumentException(
                    "La casilla [" + fila + "," + col + "] ya fue disparada anteriormente.");
        }

        if (!casilla.tieneBarco()) {
            casilla.setEstado(Estado.GOLPE_AGUA);
            return "AGUA";
        }

        // Hay barco: buscar cuál y registrar el golpe
        Barco barcoImpactado = buscarBarcoPorCasilla(fila, col);
        if (barcoImpactado != null) {
            barcoImpactado.recibirGolpe();

            if (barcoImpactado.estaHundido()) {
                marcarBarcoComotHundido(barcoImpactado);
                return "HUNDIDO";
            } else {
                casilla.setEstado(Estado.GOLPE_BARCO);
                return "GOLPE";
            }
        }

        // Caso de seguridad (no debería ocurrir)
        casilla.setEstado(Estado.GOLPE_BARCO);
        return "GOLPE";
    }

    /**
     * Indica si todos los barcos del tablero han sido hundidos
     * (condición de derrota para el dueño de este tablero).
     *
     * @return true si todos los barcos están hundidos
     */
    public boolean todosHundidos() {
        for (Barco b : barcos) {
            if (!b.estaHundido()) {
                return false;
            }
        }
        return !barcos.isEmpty();
    }

    /**
     * Valida si un barco puede colocarse en la posición y orientación dadas.
     * Verifica límites del tablero y superposición con otros barcos.
     *
     * @param barco       barco a colocar
     * @param fila        fila inicial
     * @param col         columna inicial
     * @param orientacion "HORIZONTAL" o "VERTICAL"
     * @return true si la posición es válida
     */
    public boolean esPosicionValida(Barco barco, int fila, int col, String orientacion) {
        int longitud = barco.getLongitud();

        for (int i = 0; i < longitud; i++) {
            int f = (orientacion.equalsIgnoreCase("VERTICAL"))   ? fila + i : fila;
            int c = (orientacion.equalsIgnoreCase("HORIZONTAL")) ? col  + i : col;

            // Verificar límites
            if (f < 0 || f >= TAMANIO || c < 0 || c >= TAMANIO) {
                return false;
            }

            // Verificar superposición
            if (casillas[f][c].tieneBarco()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica si una coordenada ya fue disparada previamente.
     * Útil para la validación de disparo repetido requerida por el sistema.
     *
     * @param fila fila a verificar
     * @param col  columna a verificar
     * @return true si la casilla ya fue disparada
     */
    public boolean yaFueDisparada(int fila, int col) {
        validarCoordenada(fila, col);
        return casillas[fila][col].fueDisparada();
    }

    /**
     * Devuelve la casilla en la posición indicada.
     *
     * @param fila fila (0–9)
     * @param col  columna (0–9)
     * @return casilla correspondiente
     */
    public Casilla getCasilla(int fila, int col) {
        validarCoordenada(fila, col);
        return casillas[fila][col];
    }

    /**
     * Reinicia el tablero: todas las casillas vuelven a AGUA
     * y se elimina la lista de barcos.
     */
    public void reiniciar() {
        barcos.clear();
        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                casillas[i][j].setEstado(Estado.AGUA);
            }
        }
    }

    // ─── Métodos privados ─────────────────────────────────────────────────────

    /**
     * Busca el barco que ocupa la casilla en (fila, col).
     *
     * @return el barco encontrado, o null si ninguno ocupa esa casilla
     */
    private Barco buscarBarcoPorCasilla(int fila, int col) {
        for (Barco b : barcos) {
            if (b.ocupaCasilla(fila, col)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Cambia a HUNDIDO todas las casillas que ocupa un barco hundido.
     *
     * @param barco barco que acaba de ser hundido
     */
    private void marcarBarcoComotHundido(Barco barco) {
        for (int[] coord : barco.getPosicion()) {
            casillas[coord[0]][coord[1]].setEstado(Estado.HUNDIDO);
        }
    }

    /**
     * Lanza excepción si (fila, col) está fuera del rango 0–9.
     */
    private void validarCoordenada(int fila, int col) {
        if (fila < 0 || fila >= TAMANIO || col < 0 || col >= TAMANIO) {
            throw new IllegalArgumentException(
                    "Coordenada fuera de rango: [" + fila + "," + col + "]");
        }
    }

    // ─── Getters ──────────────────────────────────────────────────────────────

    public Casilla[][] getCasillas() {
        return casillas;
    }

    public List<Barco> getBarcos() {
        return new ArrayList<>(barcos); // copia defensiva
    }

    public int cantidadBarcos() {
        return barcos.size();
    }
}
