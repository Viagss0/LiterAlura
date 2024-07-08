package com.aluracursos.challengelibros.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long libroId;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private String idioma;  //depronto crear enum
    private Long cantidadDescargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.libroId = datosLibro.libroId();
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idioma().get(0);
        this.autor = new Autor(datosLibro.datosAutor().get(0));
        this.cantidadDescargas = datosLibro.cantidadDescargas();
    }

    @Override
    public String toString() {
        return
                "----- LIBRO -----\n" +
                "Titulo: " + titulo +
                "\nAutor: " + autor.getNombre()+
                "\nIdioma: " + idioma + '\''+
                "\nNumero de descargas=" + cantidadDescargas +
                        "\n---------------";
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLibroId() {
        return libroId;
    }

    public void setLibroId(Long libroId) {
        this.libroId = libroId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Long cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

}
