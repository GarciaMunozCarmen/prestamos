package es.fplumara.dam1.prestamos.service;

import es.fplumara.dam1.prestamos.exception.MaterialNoDisponible;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.model.Prestamo;
import es.fplumara.dam1.prestamos.repository.Repository;

import java.time.LocalDate;

public class PrestamoService {
    Repository<Material> materialRepository;
    Repository<Prestamo> prestamoRepository;

    Prestamo crearPrestamo (String idMaterial, String profesor, LocalDate fecha){
        if(idMaterial == null || idMaterial.isEmpty() || idMaterial.isBlank()|| profesor == null || profesor.isBlank() || profesor.isEmpty() || fecha == null){
            throw new IllegalArgumentException();
        } else if (materialRepository.findById(idMaterial).isEmpty()) {
            throw new NoEncontradoException();
        } else if (!materialRepository.findById(idMaterial).get().getEstado().equals(EstadoMaterial.DISPONIBLE)) {
            throw new MaterialNoDisponible();
        }else{
           return new Prestamo(); //NO TERMINADO
        }
    }
}
