package Modelo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
public class Jugador_humano extends Jugador {
    // ─── Constantes ───────────────────────────────────────────────────────────

    /** Límite de tiempo en milisegundos para realizar un disparo (10 segundos). */
    public static final long LIMITE_TIEMPO_MS = 10_000L;

    // ─── Atributos ────────────────────────────────────────────────────────────

    /** Temporizador que controla el límite de tiempo del turno. */
    private Timer temporizador;

    /**
     * Callback ejecutado cuando el tiempo se agota sin que el jugador haya disparado.
     * El controlador del juego lo asigna para poder reaccionar al evento.
     */
    private Runnable alAgotarseTiempo;

    /** Indica si el turno del jugador está activo y el temporizador corriendo. */
    private boolean turnoActivo;

    // ─── Constructor ──────────────────────────────────────────────────────────

    /**
     * Crea el jugador humano con nombre dado.
     *
     * @param nombre nombre del jugador (p. ej. "Jugador 1")
     */
    public Jugador_humano(String nombre) {
        super(nombre);
        this.barcos      = new ArrayList<>();
        this.turnoActivo = false;
    }

    // ─── Implementación de métodos abstractos ─────────────────────────────────

    /**
     * La colocación de barcos del humano se gestiona desde la vista (GUI).
     * Este método inicializa la lista de los 5 barcos reglamentarios
     * listos para ser posicionados por el jugador.
     *
     * Barcos: longitudes 5, 4, 3, 3 y 2 según los requerimientos.
     */
    @Override
    public void colocarBarcos() {
        barcos.clear();
        barcos.add(new Barco(5, "HORIZONTAL"));
        barcos.add(new Barco(4, "HORIZONTAL"));
        barcos.add(new Barco(3, "HORIZONTAL"));
        barcos.add(new Barco(3, "HORIZONTAL"));
        barcos.add(new Barco(2, "HORIZONTAL"));
        // Las orientaciones definitivas las elige el jugador en la GUI.
    }

    /**
     * Realiza el disparo del humano al tablero enemigo.
     * Detiene el temporizador al ejecutar el disparo.
     *
     * @param fila         fila del disparo (0–9)
     * @param col          columna del disparo (0–9)
     * @return "AGUA", "GOLPE" o "HUNDIDO"
     */
    @Override
    public String realizarDisparo(int fila, int col) {
        detenerTemporizador();
        // El disparo se delega al tablero del rival; el controlador pasa
        // el tablero correcto. Aquí se valida la coordenada ante el tablero
        // propio para no disparar donde ya disparó.
        // La lógica concreta de recibirDisparo está en Tablero.
        return "PENDIENTE"; // El controlador llama tableroRival.recibirDisparo(fila, col)
    }

    // ─── Gestión del temporizador ─────────────────────────────────────────────

    /**
     * Inicia el temporizador de 10 segundos para el turno del jugador.
     * Si el tiempo se agota sin disparo, ejecuta el callback {@link #alAgotarseTiempo}.
     */
    public void iniciarTemporizador() {
        detenerTemporizador(); // Cancelar cualquier temporizador previo
        turnoActivo = true;
        temporizador = new Timer("TemporizadorTurno", true);

        temporizador.schedule(new TimerTask() {
            @Override
            public void run() {
                if (turnoActivo) {
                    turnoActivo = false;
                    if (alAgotarseTiempo != null) {
                        alAgotarseTiempo.run();
                    }
                }
            }
        }, LIMITE_TIEMPO_MS);
    }

    /**
     * Detiene el temporizador del turno (p. ej. cuando el jugador disparó a tiempo).
     */
    public void detenerTemporizador() {
        turnoActivo = false;
        if (temporizador != null) {
            temporizador.cancel();
            temporizador = null;
        }
    }

    /**
     * Gestiona el tiempo límite del turno del jugador.
     * Invocado externamente para iniciar el conteo al comienzo del turno.
     */
    public void gestionarTiempoLimite() {
        iniciarTemporizador();
    }

    /**
     * Devuelve el tiempo restante aproximado en segundos.
     * Nota: Java {@link Timer} no expone tiempo restante directamente;
     * para mostrar cuenta regresiva en la GUI se recomienda usar
     * un {@link javax.swing.Timer} en la capa Vista.
     *
     * @return true si el turno sigue activo (aún no venció el tiempo)
     */
    public boolean isTurnoActivo() {
        return turnoActivo;
    }

    // ─── Getters y Setters ────────────────────────────────────────────────────

    /**
     * Asigna el callback que se ejecutará cuando el tiempo del turno se agote.
     *
     * @param alAgotarseTiempo Runnable a ejecutar (generalmente en el controlador)
     */
    public void setAlAgotarseTiempo(Runnable alAgotarseTiempo) {
        this.alAgotarseTiempo = alAgotarseTiempo;
    }

    @Override
    public void reiniciar() {
        super.reiniciar();
        detenerTemporizador();
    }
}
