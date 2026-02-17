package es.fplumara.dam1.prestamos.repository;

import es.fplumara.dam1.prestamos.model.Identificable;

import java.util.*;

public class BaseRepository <T extends Identificable> implements Repository<T>{

    Map<String, T> datos = new HashMap<>();

    @Override
    public void save(T elemento) {
        datos.put(elemento.getId(), elemento);
    }

    @Override
    public Optional<T> findById(String id) {
        Optional<T> elemento = Optional.empty();
        if(datos!= null){
            elemento = Optional.of(datos.get(id));
        }

        return elemento;
    }

    @Override
    public List<T> listAll() {
        return new ArrayList<>(datos.values());
    }

    @Override
    public void delete(String id) {
        datos.remove(id);
    }
}
