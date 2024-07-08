package com.aluracursos.challengelibros.principal;

import com.aluracursos.challengelibros.model.Autor;
import com.aluracursos.challengelibros.model.DatosLibro;
import com.aluracursos.challengelibros.model.DatosResults;
import com.aluracursos.challengelibros.model.Libro;
import com.aluracursos.challengelibros.repository.AutorRepository;
import com.aluracursos.challengelibros.repository.LibroRepository;
import com.aluracursos.challengelibros.service.ConsumoAPI;
import com.aluracursos.challengelibros.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();

    private List<Libro> libros = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    @Autowired
    public Principal(LibroRepository repositoryL, AutorRepository autorRepository) {
        this.libroRepository = repositoryL;
        this.autorRepository = autorRepository;
    }

    public void muestraMenu() {

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                            
                            BIENVENIDO
                            
                    Elija la opción a través de su número:
                    
                    1 - Buscar libro por título 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    autoresVivosEnFecha();
                    break;
                case 5:
                    librosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    teclado.close();
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DatosLibro getDatosLibro(){
        System.out.println("Escribe nombre del libro que desea buscar: ");
        var nombreLibro = teclado.nextLine().toLowerCase();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ","%20"));
        DatosResults datos = conversor.obtenerDatos(json, DatosResults.class);

        if (datos.resultadoLibro().isEmpty()){
            return null;
        } else {
            DatosLibro primerLibro = datos.resultadoLibro().get(0);
            return primerLibro;
        }
    }

    private void buscarLibroWeb() {
        DatosLibro datos = getDatosLibro();

        if (datos == null) {
            System.out.println("No se encontraron resultados.");
        } else if (libroRepository.existsByTitulo(datos.titulo())) {
            System.out.println("El título ya existe en el repositorio.");
        } else {
            Libro libro = new Libro(datos);
            libroRepository.save(libro);
            System.out.println(libro);
        }
    }

    private void listarLibrosRegistrados() {
        libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
//        autores = autorRepository.findAll();
//        autores.forEach(System.out::println);
        autores = autorRepository.findAll();
        Set<String> nombresUnicos = new HashSet<>();

        autores.stream()
                .filter(autor -> autor.getNombre() != null && nombresUnicos.add(autor.getNombre()))
                .forEach(System.out::println);
    }

    private void autoresVivosEnFecha() {
        System.out.println("Indica el año para consultar que autores estan vivos: \n");
        var anioBuscado = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autoresAnio =autorRepository.findAutoresVivosEnAnio(anioBuscado);
        if (autoresAnio.isEmpty()){
            System.out.println("No se encontraron autores que vivieran en la fecha");
        } else {
            autoresAnio.forEach(System.out::println);
        }
    }

    private void librosPorIdioma() {
        //HACER CICLO PARA OPCIONES VALIDAS
        System.out.println("es - español");
        System.out.println("en - ingles");
        System.out.println("fr - frances");
        System.out.println("pt - portugues\n");
        String idioma;

        while (true){
            System.out.println("Ingrese el idioma para buscar libros:");
            idioma = teclado.nextLine().toLowerCase();

            if (idioma.equals("en") || idioma.equals("es") || idioma.equals("fr") || idioma.equals("pt")){

                List<Libro> librosIdioma = libroRepository.findByIdioma(idioma);

                if (librosIdioma.isEmpty()){
                    System.out.println("No se encontraron libros en el idioma");
                } else {
                    librosIdioma.forEach(System.out::println);
                    System.out.println("\n Total de libros encontrados: "+ librosIdioma.size());
                }
                break;
            }
            else {
                System.out.println("-----------INGRESE UN DATO VALIDO -----------");
            }
        }

    }

}
