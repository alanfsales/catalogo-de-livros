package br.com.catalogo.principal;

import br.com.catalogo.modelo.Autor;
import br.com.catalogo.modelo.Livro;
import br.com.catalogo.modelo.LivroDTO;
import br.com.catalogo.repository.LivroRepository;
import br.com.catalogo.service.ConsultaAPI;
import br.com.catalogo.service.ConverteDados;

import java.time.Year;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private ConsultaAPI consultaAPI = new ConsultaAPI();
    private ConverteDados converteDados = new ConverteDados();
    private Scanner leitura = new Scanner(System.in);
    private LivroRepository repository;
    private String endereco = "https://gutendex.com/books/?search=";

    public Principal(LivroRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu(){
        int opcao = -1;
        while (opcao != 0){
            String menu = """
                ----------------------------
                ***Escolha uma das opções***
                1 - Buscar livros por título
                2 - Listar livros registrados
                3 - Listar Autores
                4 - Listar Autores vivos em determinado ano
                5 - Listar Livros em determinado Idioma
                0 - Sair
                """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao){
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosNoAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
            }
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                Digite o idioma para busca
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
        String idioma = leitura.nextLine();
        List<Livro> livros = repository.findByIdioma(idioma);
        if (!livros.isEmpty()){
            livros.forEach(System.out::println);
        }else{
            System.out.println("Não exite livros nesse idioma cadastrado");
        }

    }

    private void listarAutoresVivosNoAno() {
        System.out.println("Digite o ano:");
        int ano = leitura.nextInt();
        leitura.nextLine();

        List<Autor> autores = repository.buscarAutoresVivosNoAno(Year.of(ano));
        autores.forEach(System.out::println);

    }

    private void listarAutores() {
        List<Autor> autores = repository.buscarAutores();
        autores.forEach(System.out::println);
    }

    private void listarLivros() {
        List<Livro> livros = repository.findAll();
        livros.forEach(System.out::println);
    }

    private void buscarLivro() {
        System.out.println("Digite o nome do Livro: ");
        var nomeLivro = leitura.nextLine();
        System.out.println("Buscando...");
        String enderecoBusca = endereco.concat(nomeLivro.replace(" ", "+").toLowerCase().trim());

        String json = consultaAPI.buscar(enderecoBusca);
        String jsonLivro = converteDados.extraiObjetoJson(json, "results");

        List<LivroDTO> livrosDTO = converteDados.obterLista(jsonLivro, LivroDTO.class);

       if (livrosDTO.size() > 0){
           Livro livro = new Livro(livrosDTO.get(0));

           Autor autor = repository.buscarAutorPeloNome(livro.getAutor().getAutor());
           if (autor != null){
               livro.setAutor(autor);
           }

           livro = repository.save(livro);
           System.out.println(livro);
       }else {
           System.out.println("Livro não encontrado");
       }
    }
}
