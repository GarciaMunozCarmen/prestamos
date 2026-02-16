package es.fplumara.dam1.prestamos.model;

import java.time.LocalDate;

public class Prestamo implements Identificable{
    String id;
    String idMaterial;
    String profesor;
    LocalDate fecha;

    // GETTER
    public String getId() {
        return id;
    }
}
