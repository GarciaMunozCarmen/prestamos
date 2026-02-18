package es.fplumara.dam1.prestamos.model;

import java.util.Set;

public class Portatil extends Material{
    int ramGB;

    public Portatil(String id, String nombre, EstadoMaterial estado, int ramGB, Set<String> etiquetas) {
        super(id, nombre, estado, etiquetas);
        this.ramGB = ramGB;
    }

    @Override
    String getTipo() {
        return "Portátil";
    }

    @Override
    public String toString() {
        return "Portatil{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                ", ramGB=" + ramGB +
                ", etiquetas=" + etiquetas +
                '}';
    }
}
