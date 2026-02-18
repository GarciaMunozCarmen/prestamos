package es.fplumara.dam1.prestamos.service;


import es.fplumara.dam1.prestamos.exception.MaterialNoDisponible;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.model.Portatil;
import es.fplumara.dam1.prestamos.model.Prestamo;
import es.fplumara.dam1.prestamos.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PrestamosServiceTest {

    // TODO (alumnos): añadir JUnit 5 y Mockito en el pom.xml y completar:
    //
    // - crearPrestamo_ok_cambiaEstado_y_guarda()
    // - crearPrestamo_materialNoExiste_lanzaNoEncontrado()
    // - crearPrestamo_materialNoDisponible_lanzaMaterialNoDisponible()
    // - devolverMaterial_ok_cambiaADisponible()
    //
    // Requisito: usar mocks de repositorios y verify(...)

    @Mock
    private Repository<Material> materialRepository;
    @Mock
    private Repository<Prestamo> prestamoRepository;
    private PrestamoService prestamoService;

    @BeforeEach
    void setUp(){
        prestamoService = new PrestamoService(materialRepository, prestamoRepository);
    }

    @Test
    public void crearPrestamo_ok_cambiaEstado_y_guarda(){
        when(materialRepository.findById("M001")).thenReturn(Optional.of(new Portatil("M001", "portatil", EstadoMaterial.DISPONIBLE, 5, new HashSet<>())));
        prestamoService.crearPrestamo("M001", "profesor", LocalDate.now());
        verify(prestamoRepository).save(any());
    }

    @Test
    public void crearPrestamo_materialNoExiste_lanzaNoEncontrado(){
        NoEncontradoException ex =assertThrows(NoEncontradoException.class, ()-> prestamoService.crearPrestamo("M001", "profesor", LocalDate.now()));
    }

    @Test
    public void crearPrestamo_materialNoDisponible_lanzaMaterialNoDisponible(){
        when(materialRepository.findById("M001")).thenReturn(Optional.of(new Portatil("M001", "portatil", EstadoMaterial.BAJA, 5, new HashSet<>())));
        MaterialNoDisponible ex = assertThrows(MaterialNoDisponible.class, () -> prestamoService.crearPrestamo("M001", "profesor", LocalDate.now()));
    }

    @Test
    public void devolverMaterial_ok_cambiaADisponible(){
        when(materialRepository.findById("M001")).thenReturn(Optional.of(new Portatil("M001", "portatil", EstadoMaterial.PRESTADO, 5, new HashSet<>())));
        prestamoService.devolverMaterial("M001");
        assertEquals(materialRepository.findById("M001").get().getEstado(), EstadoMaterial.DISPONIBLE);

    }
}
