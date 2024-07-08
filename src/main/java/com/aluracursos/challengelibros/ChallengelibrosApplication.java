package com.aluracursos.challengelibros;

import com.aluracursos.challengelibros.principal.Principal;
import com.aluracursos.challengelibros.repository.AutorRepository;
import com.aluracursos.challengelibros.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengelibrosApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengelibrosApplication.class, args);
	}

	public void run(String... args) throws Exception {

		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraMenu();
	}
}
