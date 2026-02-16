package es.fplumara.dam1.prestamos.exception;

public class MaterialNoDisponible extends RuntimeException {
    public MaterialNoDisponible(String message) {
        super(message);
    }
    public MaterialNoDisponible(){}
}
