package Modelo;

public enum Estado {
    /** La casilla es agua y no ha sido disparada. */
    AGUA,

    /** La casilla contiene un barco y no ha sido disparada. */
    BARCO,

    /** La casilla fue disparada y era agua (fallo). */
    GOLPE_AGUA,

    /** La casilla fue disparada y contenía parte de un barco (impacto). */
    GOLPE_BARCO,

    /** Todas las casillas del barco en este sector han sido impactadas (hundido). */
    HUNDIDO,
}
