package Modelo;

package batalla_naval.modelo;
import java.util.ArrayList;
import java.util.Random;

public class Bot extends Jugador {
    // ─── Atributos ────────────────────────────────────────────────────────────

    /** Generador de números aleatorios. */
    private final Random random;

    /**
     * Última coordenada impactada (no hundida aún).
     * Se usa para el algoritmo simple: disparar adyacente tras un golpe.
     */
    private int[] ultimoImpacto;

    /**
     * Indica si el bot está en modo "caza" (acaba de impactar y busca hundir).
     */
    private boolean modosCaza;

    // ─── Constructor ──────────────────────────────────────────────────────────

    public Bot() {
        super("Bot");
        this.random      = new Random();
        this.barcos      = new ArrayList<>();
        this.modosCaza   = false;
        this.ultimoImpacto = null;
    }

    // ─── Implementación de métodos abstractos ─────────────────────────────────

    /**
     * Coloca los 5 barcos del bot de forma completamente aleatoria en su tablero.
     * Garantiza que ningún barco se superponga con otro y ninguno salga del tablero.
     *
     * Barcos: longitudes 5, 4, 3, 3 y 2 según los requerimientos.
     */
    @Override
    public void colocarBarcos() {
        barcos.clear();
        int[] longitudes = {5, 4, 3, 3, 2};

        for (int longitud : longitudes) {
            Barco barco = new Barco(longitud, "HORIZONTAL");
            colocarBarcosAleatorio(barco);
            barcos.add(barco);
        }
    }

    /**
     * Realiza un disparo al tablero enemigo.
     * Si está en modo caza (último disparo fue impacto), intenta disparar en
     * casillas adyacentes; de lo contrario dispara aleatoriamente.
     *
     * @param fila fila objetivo (ignorado si el bot elige aleatoriamente)
     * @param col  columna objetivo (ignorado si el bot elige aleatoriamente)
     * @return "PENDIENTE" — el controlador llama tableroRival.recibirDisparo()
     *         con las coordenadas devueltas por {@link #elegirCoordenada(Tablero)}
     */
    @Override
    public String realizarDisparo(int fila, int col) {
        // El controlador usa elegirCoordenada() para obtener las coordenadas reales.
        return "PENDIENTE";
    }

    // ─── Lógica del algoritmo del bot ─────────────────────────────────────────

    /**
     * Elige la coordenada de disparo aplicando el algoritmo simple:
     * <ol>
     *   <li>Si hay un impacto previo no hundido, dispara en casilla adyacente.</li>
     *   <li>Si no hay candidato adyacente válido, dispara aleatoriamente.</li>
     * </ol>
     *
     * @param tableroRival tablero del jugador humano (para verificar disparos previos)
     * @return arreglo {fila, columna} de la coordenada elegida
     */
    public int[] elegirCoordenada(Tablero tableroRival) {
        if (modosCaza && ultimoImpacto != null) {
            int[] coordAdyacente = buscarAdyacenteValido(tableroRival);
            if (coordAdyacente != null) {
                return coordAdyacente;
            }
            // No hay adyacente válido: salir de modo caza
            modosCaza    = false;
            ultimoImpacto = null;
        }
        return dispararAleatorio(tableroRival);
    }

    /**
     * Notifica al bot el resultado de su último disparo para actualizar
     * la estrategia de caza.
     *
     * @param fila      fila donde disparó
     * @param col       columna donde disparó
     * @param resultado "AGUA", "GOLPE" o "HUNDIDO"
     */
    public void notificarResultado(int fila, int col, String resultado) {
        switch (resultado) {
            case "GOLPE" -> {
                modosCaza    = true;
                ultimoImpacto = new int[]{fila, col};
            }
            case "HUNDIDO" -> {
                modosCaza    = false;
                ultimoImpacto = null;
            }
            default -> {
                // AGUA: mantener estado actual
            }
        }
    }

    // ─── Métodos privados ─────────────────────────────────────────────────────

    /**
     * Coloca un barco de forma aleatoria en el tablero del bot.
     * Reintenta hasta encontrar una posición válida.
     *
     * @param barco barco a colocar
     */
    private void colocarBarcosAleatorio(Barco barco) {
        boolean colocado = false;
        while (!colocado) {
            int fila         = random.nextInt(Tablero.TAMANIO);
            int col          = random.nextInt(Tablero.TAMANIO);
            String orientacion = random.nextBoolean() ? "HORIZONTAL" : "VERTICAL";

            colocado = tablero.colocarBarco(barco, fila, col, orientacion);
        }
    }

    /**
     * Elige una coordenada aleatoria que no haya sido disparada previamente.
     *
     * @param tableroRival tablero del rival
     * @return arreglo {fila, columna}
     */
    private int[] dispararAleatorio(Tablero tableroRival) {
        int fila, col;
        do {
            fila = random.nextInt(Tablero.TAMANIO);
            col  = random.nextInt(Tablero.TAMANIO);
        } while (tableroRival.yaFueDisparada(fila, col));

        return new int[]{fila, col};
    }

    /**
     * Busca una casilla adyacente al último impacto que no haya sido disparada.
     * Orden: arriba, abajo, izquierda, derecha.
     *
     * @param tableroRival tablero del rival
     * @return coordenada adyacente válida o null si no hay ninguna
     */
    private int[] buscarAdyacenteValido(Tablero tableroRival) {
        int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] delta : deltas) {
            int f = ultimoImpacto[0] + delta[0];
            int c = ultimoImpacto[1] + delta[1];

            if (f >= 0 && f < Tablero.TAMANIO
                    && c >= 0 && c < Tablero.TAMANIO
                    && !tableroRival.yaFueDisparada(f, c)) {
                return new int[]{f, c};
            }
        }
        return null;
    }

    // ─── Getters ──────────────────────────────────────────────────────────────

    public boolean isModosCaza() {
        return modosCaza;
    }

    public int[] getUltimoImpacto() {
        return ultimoImpacto;
    }
}
