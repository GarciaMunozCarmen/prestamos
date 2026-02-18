package es.fplumara.dam1.prestamos.model;

import java.util.Set;

public class Proyector extends Material{
    int lumens;

    public Proyector(String id, String nombre, EstadoMaterial estado, int lumens, Set<String> etiquetas) {
        super(id, nombre, estado, etiquetas);
        this.lumens = lumens;
    }

    @Override
    String getTipo() {
        return "Proyector";
    }
}
