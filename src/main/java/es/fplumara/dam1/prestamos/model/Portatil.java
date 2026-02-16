package es.fplumara.dam1.prestamos.model;

public class Portatil extends Material{
    int ramGB;

    @Override
    String getTipo() {
        return "Portátil";
    }
}
