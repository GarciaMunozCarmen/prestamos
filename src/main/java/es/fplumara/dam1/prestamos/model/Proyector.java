package es.fplumara.dam1.prestamos.model;

import java.util.Set;

public class Proyector extends Material{
    int lumens;

    public Proyector(String id, String nombre, EstadoMaterial estado, int lumens, Set<String> etiquetas) {
        super(id, nombre, estado, etiquetas);
        this.lumens = lumens;
    }

    @Override
    public String getTipo() {
        return "PROYECTOR";
    }

    @Override
    public String toString() {
        return "Proyector{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                ", lumens=" + lumens +
                ", etiquetas=" + etiquetas +
                '}';
    }

    public int getLumens() {
        return lumens;
    }

    public void setLumens(int lumens) {
        this.lumens = lumens;
    }
}
