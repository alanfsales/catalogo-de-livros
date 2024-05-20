package br.com.catalogo;

import br.com.catalogo.principal.Principal;
import br.com.catalogo.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogoDeLivrosApplication implements CommandLineRunner {

	@Autowired
	private LivroRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(CatalogoDeLivrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.exibeMenu();
	}
}
