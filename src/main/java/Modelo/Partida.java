package Modelo;

public class Partida {
    public static final String TURNO_JUGADOR = "JUGADOR";
    public static final String TURNO_BOT     = "BOT";

    // ─── Atributos ────────────────────────────────────────────────────────────

    /** Jugador humano de la partida. */
    private Jugador_humano jugador;

    /** Oponente controlado por la máquina. */
    private Bot bot;

    /** Indica de quién es el turno actual: "JUGADOR" o "BOT". */
    private String turnoActual;

    /** Indica si la partida está en curso. */
    private boolean enJuego;

    /** Nombre del ganador al terminar la partida; null si aún no hay ganador. */
    private String ganador;

    // ─── Constructor ──────────────────────────────────────────────────────────

    /**
     * Crea una partida nueva con el jugador humano y el bot,
     * sin iniciarla todavía.
     *
     * @param nombreJugador nombre del jugador humano
     */
    public Partida(String nombreJugador) {
        this.jugador     = new Jugador_humano(nombreJugador);
        this.bot         = new Bot();
        this.enJuego     = false;
        this.turnoActual = TURNO_JUGADOR;
        this.ganador     = null;
    }


    /**
     * Inicia la partida: el bot coloca sus barcos y se da comienzo al juego.
     * El jugador debe colocar los suyos antes de llamar este método
     * (gestionado por el controlador y la vista).
     */
    public void iniciar() {
        bot.colocarBarcos();
        enJuego     = true;
        turnoActual = TURNO_JUGADOR;
        ganador     = null;
    }

    /**
     * Cambia el turno al siguiente jugador.
     * Si el último disparo fue un acierto, el mismo jugador recibe turno extra.
     *
     * @param fueAcierto true si el disparo del turno actual fue GOLPE o HUNDIDO
     */
    public void cambiarTurno(boolean fueAcierto) {
        if (!fueAcierto) {
            // Sin acierto: alternar turno
            turnoActual = turnoActual.equals(TURNO_JUGADOR) ? TURNO_BOT : TURNO_JUGADOR;
        }
        // Con acierto: el turno extra mantiene al mismo jugador (no se cambia)
    }

    /**
     * Verifica si alguno de los jugadores ha ganado.
     * Actualiza el campo {@link #ganador} y detiene la partida si corresponde.
     *
     * @return true si la partida terminó
     */
    public boolean detectarGanador() {
        if (jugador.perdio()) {
            ganador = bot.getNombre();
            enJuego = false;
            return true;
        }
        if (bot.perdio()) {
            ganador = jugador.getNombre();
            enJuego = false;
            return true;
        }
        return false;
    }

    /**
     * Reinicia la partida para jugar de nuevo sin cerrar la aplicación.
     * Reinicia tableros, barcos, turno y estado general.
     */
    public void reiniciar() {
        jugador.reiniciar();
        bot.reiniciar();
        enJuego     = false;
        turnoActual = TURNO_JUGADOR;
        ganador     = null;
    }

    /**
     * Indica si actualmente es el turno del jugador humano.
     *
     * @return true si turnoActual == TURNO_JUGADOR
     */
    public boolean esTurnoJugador() {
        return TURNO_JUGADOR.equals(turnoActual);
    }

    /**
     * Indica si actualmente es el turno del bot.
     *
     * @return true si turnoActual == TURNO_BOT
     */
    public boolean esTurnoBot() {
        return TURNO_BOT.equals(turnoActual);
    }

    // ─── Getters y Setters ────────────────────────────────────────────────────

    public Jugador_humano getJugador() {
        return jugador;
    }

    public Bot getBot() {
        return bot;
    }

    public String getTurnoActual() {
        return turnoActual;
    }

    public void setTurnoActual(String turnoActual) {
        this.turnoActual = turnoActual;
    }

    public boolean isEnJuego() {
        return enJuego;
    }

    public void setEnJuego(boolean enJuego) {
        this.enJuego = enJuego;
    }

    public String getGanador() {
        return ganador;
    }
}
