package es.fplumara.dam1.prestamos.model;

import java.time.LocalDate;

public class Prestamo implements Identificable{
    String id;
    String idMaterial;
    String profesor;
    LocalDate fecha;

    public Prestamo(String id, String idMaterial, String profesor, LocalDate fecha) {
        this.id = id;
        this.idMaterial = idMaterial;
        this.profesor = profesor;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id='" + id + '\'' +
                ", idMaterial='" + idMaterial + '\'' +
                ", profesor='" + profesor + '\'' +
                ", fecha=" + fecha +
                '}';
    }

    // GETTER
    public String getId() {
        return id;
    }
}
