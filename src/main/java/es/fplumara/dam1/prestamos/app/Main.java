package es.fplumara.dam1.prestamos.app;

import es.fplumara.dam1.prestamos.csv.*;
import es.fplumara.dam1.prestamos.model.*;
import es.fplumara.dam1.prestamos.repository.MaterialRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.PrestamoRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.Repository;
import es.fplumara.dam1.prestamos.service.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Main de ejemplo para demostrar el flujo mínimo del examen (sin menú complejo).
 * La idea es que este método ejecute una "demo" por consola.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Examen DAM1 - Préstamo de Material (Java 21)");

        /*
         * FLUJO MÍNIMO OBLIGATORIO (lo que debe hacer tu main)
         *
         * 1) Crear repositorios en memoria
         *    - Crear MaterialRepositoryImpl (almacena materiales en memoria).
         *    - Crear PrestamoRepositoryImpl (almacena préstamos en memoria).
         */

        Repository<Material> materialRepository = new MaterialRepositoryImpl();
        Repository<Prestamo> prestamoRepository = new PrestamoRepositoryImpl();

         /*
         * 2) Crear servicios
         *    - Crear MaterialService usando el repositorio de materiales.
         *    - Crear PrestamoService usando el repositorio de materiales y el de préstamos.
         */

        MaterialService materialService = new MaterialService(materialRepository);
        PrestamoService prestamoService = new PrestamoService(materialRepository,prestamoRepository);

        /*
         * 3) Cargar materiales desde CSV (código proporcionado)
         *    - Usar CsvMaterialImporter para leer "materiales.csv".
         *    - El importer devuelve registros (por ejemplo RegistroMaterialCsv).
         *    - Convertir cada registro a tu modelo:
         *        - Si tipo == "PORTATIL" -> crear Portatil (extra = ramGB)
         *        - Si tipo == "PROYECTOR" -> crear Proyector (extra = lumens)
         *      (aplicando estado y etiquetas)
         *    - Registrar cada Material llamando a MaterialService.registrarMaterial(...)
         */

        CSVMaterialImporter csvMaterialImporter = new CSVMaterialImporter();

        List<RegistroMaterialCsv> registrosCSV = csvMaterialImporter.leer("data/materiales.csv");

        List<Portatil> portatiles = new ArrayList<>();
        List<Proyector> proyectores = new ArrayList<>();

        for(RegistroMaterialCsv r : registrosCSV){
            if(r.tipo().equalsIgnoreCase("PROYECTOR")){
                proyectores.add(new Proyector(r.id(), r.nombre(), EstadoMaterial.valueOf(r.estado()), r.extra(), r.etiquetas()));
            }else{
                portatiles.add(new Portatil(r.id(), r.nombre(), EstadoMaterial.valueOf(r.estado()), r.extra(), r.etiquetas()));
            }
        }

        portatiles.forEach(materialService::registrarMaterial);
        proyectores.forEach(materialService::registrarMaterial);

        /*
         * 4) Crear un préstamo
         *    - Elegir un id de material existente (por ejemplo "M001").
         *    - Llamar a PrestamoService.crearPrestamo("M001", "Nombre Profesor", fecha)
         *    - Comprobar que el material pasa a estado PRESTADO
         */

        prestamoService.crearPrestamo("M001", "Iván", LocalDate.now());
        System.out.println("Estado: " + materialRepository.findById("M001").get().getEstado().toString());

        /*
         * 5) Listar por consola
         *    - Imprimir todos los materiales (MaterialService.listar()) mostrando: id, nombre, estado, tipo.
         *    - Imprimir todos los préstamos (PrestamoService.listarPrestamos()) mostrando: id, idMaterial, profesor, fecha.
         */

        System.out.println(materialService.listar());
        System.out.println(prestamoService.listarPrestamos());

        /*
         * 6) Devolver el material
         *    - Llamar a PrestamoService.devolverMaterial("M001")
         *    - Comprobar que vuelve a estado DISPONIBLE
         */

        prestamoService.devolverMaterial("M001");
        System.out.println("Estado: " + materialRepository.findById("M001").get().getEstado().toString());

        /*
         * 7) Exportar a CSV (código proporcionado)
         *    - Convertir tu lista de Material a la estructura que pida el exporter (por ejemplo RegistroMaterialCsv).
         *    - Usar CsvMaterialExporter para escribir "salida_materiales.csv".
         *
         * Nota:
         * - No hace falta interfaz, ni menú, ni pedir datos por teclado: valores fijos y salida por consola es suficiente.
         */

        List<List<? extends Material>> materiales = List.of(portatiles, proyectores);
        List<RegistroMaterialCsv> importerMaterialCsv = new ArrayList<>();

        for (Portatil por : portatiles){
            importerMaterialCsv.add(new RegistroMaterialCsv(por.getTipo(), por.getId(), por.getNombre(), String.valueOf(por.getEstado()),por.getRamGB(), por.getEtiquetas()));
        }

        for (Proyector pro : proyectores){
            importerMaterialCsv.add(new RegistroMaterialCsv(pro.getTipo(), pro.getId(), pro.getNombre(), String.valueOf(pro.getEstado()),pro.getLumens(), pro.getEtiquetas()));
        }

        CSVMaterialExporter csvMaterialExporter = new CSVMaterialExporter();
        csvMaterialExporter.escribir("./data/salida_materiales.csv", importerMaterialCsv);
    }
}