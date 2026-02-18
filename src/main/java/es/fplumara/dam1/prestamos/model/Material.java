package es.fplumara.dam1.prestamos.model;

import java.util.Set;

public abstract class Material implements Identificable{
    String id;
    String nombre;
    EstadoMaterial estado;
    Set<String> etiquetas;

    public Material(String id, String nombre, EstadoMaterial estado, Set<String> etiquetas) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.etiquetas = etiquetas;
    }

    // METODO ABSTRACTO
    abstract String getTipo();

    // GETTERS Y SETTERS

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public EstadoMaterial getEstado() {
        return estado;
    }

    public Set<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEstado(EstadoMaterial estado) {
        this.estado = estado;
    }
}
