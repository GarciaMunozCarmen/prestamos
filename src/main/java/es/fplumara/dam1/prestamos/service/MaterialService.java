package es.fplumara.dam1.prestamos.service;

import es.fplumara.dam1.prestamos.exception.DuplicadoException;
import es.fplumara.dam1.prestamos.exception.MaterialNoDisponible;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.repository.Repository;

import java.util.List;
import java.util.Optional;

public class MaterialService {
    Repository<Material> materialRepository;

    public MaterialService(Repository<Material> materialRepository) {
        this.materialRepository = materialRepository;
    }

    public void registrarMaterial(Material m){
        if(m == null || m.getId().isEmpty() || m.getId() == null ||m.getId().isBlank()){
            throw new IllegalArgumentException();
        }else if(materialRepository.findById(m.getId()).equals(m)){
            throw new DuplicadoException();
        }else {
            materialRepository.save(m);
        }
    }

    public void darDeBaja(String idMaterial){
        Optional<Material> material = materialRepository.findById(idMaterial);
        if(material.isEmpty()){
            throw new NoEncontradoException();
        }else if(material.get().getEstado().equals(EstadoMaterial.BAJA)){
            throw new MaterialNoDisponible();
        }else{
            material.get().setEstado(EstadoMaterial.BAJA);
        }

    }

    public List<Material> listar(){
        return materialRepository.listAll();
    }
}
