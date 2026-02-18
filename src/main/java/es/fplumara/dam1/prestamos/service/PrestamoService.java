package es.fplumara.dam1.prestamos.service;

import es.fplumara.dam1.prestamos.exception.MaterialNoDisponible;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.model.Prestamo;
import es.fplumara.dam1.prestamos.repository.Repository;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

public class PrestamoService {
    Repository<Material> materialRepository;
    Repository<Prestamo> prestamoRepository;

    public PrestamoService(Repository<Material> materialRepository, Repository<Prestamo> prestamoRepository) {
        this.materialRepository = materialRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public Prestamo crearPrestamo (String idMaterial, String profesor, LocalDate fecha){
        if(idMaterial == null || idMaterial.isEmpty() || idMaterial.isBlank()|| profesor == null || profesor.isBlank() || profesor.isEmpty() || fecha == null){
            throw new IllegalArgumentException();
        } else if (materialRepository.findById(idMaterial).isEmpty()) {
            throw new NoEncontradoException();
        } else if (!materialRepository.findById(idMaterial).get().getEstado().equals(EstadoMaterial.DISPONIBLE)) {
            throw new MaterialNoDisponible();
        }else{
           Prestamo prestamo = new Prestamo(UUID.randomUUID().toString(), idMaterial, profesor, fecha);
           prestamoRepository.save(prestamo);
           materialRepository.findById(idMaterial).get().setEstado(EstadoMaterial.PRESTADO);
           return prestamo;
        }
    }

    public void devolverMaterial(String idMaterial){
        if(idMaterial == null || idMaterial.isEmpty() || idMaterial.isBlank()){
            throw new IllegalArgumentException();
        } else if (materialRepository.findById(idMaterial).isEmpty()) {
            throw new NoEncontradoException();
        }else if(!materialRepository.findById(idMaterial).get().getEstado().equals(EstadoMaterial.PRESTADO)){
            throw new MaterialNoDisponible();
        }else{
            materialRepository.findById(idMaterial).get().setEstado(EstadoMaterial.DISPONIBLE);
        }
    }

    public List<Prestamo> listarPrestamos(){
        return prestamoRepository.listAll();
    }
}
