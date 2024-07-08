package com.aluracursos.challengelibros.repository;

import com.aluracursos.challengelibros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<Libro> findByIdioma(String idioma);
    boolean existsByTitulo(String titulo);
}
