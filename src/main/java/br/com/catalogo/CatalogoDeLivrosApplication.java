package br.com.catalogo;

import br.com.catalogo.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogoDeLivrosApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoDeLivrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
