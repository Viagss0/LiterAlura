package com.aluracursos.challengelibros.repository;

import com.aluracursos.challengelibros.model.Autor;
import org.hibernate.annotations.DialectOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor,Long> {

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :fecha AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento >= :fecha)")
    List<Autor> findAutoresVivosEnAnio(Integer fecha);
}
