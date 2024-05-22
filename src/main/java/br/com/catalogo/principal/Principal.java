package br.com.catalogo.principal;

import br.com.catalogo.modelo.Autor;
import br.com.catalogo.modelo.Livro;
import br.com.catalogo.modelo.LivroDTO;
import br.com.catalogo.repository.LivroRepository;
import br.com.catalogo.service.ConsultaAPI;
import br.com.catalogo.service.ConverteDados;

import java.time.Year;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
                \n-------------------------------------------
                       *** Escolha uma das opções ***
                -------------------------------------------
                1 - Buscar livro por título
                2 - Listar livros registrados
                3 - Listar Autores
                4 - Listar Autores vivos em determinado ano
                5 - Listar Livros em determinado Idioma
                6 - Exibir a quantidade de livros em um determinado idioma.
                0 - Sair
                -------------------------------------------
                """;
            try {
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
                    case 6:
                        quantidadeLivrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            }catch (InputMismatchException e){
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
                leitura.nextLine();
            }
        }
    }

    private void buscarLivro() {
        System.out.println("Digite o nome do Livro: ");
        var nomeLivro = leitura.nextLine();
        System.out.println("Buscando...");
        String enderecoBusca = endereco.concat(nomeLivro.replace(" ", "+").toLowerCase().trim());

        String json = consultaAPI.buscar(enderecoBusca);
        String jsonLivro = converteDados.extraiObjetoJson(json, "results");

        List<LivroDTO> livrosDTO = converteDados.obterLista(jsonLivro, LivroDTO.class);

        if (livrosDTO.size() > 0) {
            Livro livro = new Livro(livrosDTO.get(0));

            //Verifica se o Autor já está cadastrado
            Autor autor = repository.buscarAutorPeloNome(livro.getAutor().getAutor());
            if (autor != null) {
                livro.setAutor(null);
                repository.save(livro);
                livro.setAutor(autor);
            }
            livro = repository.save(livro);
            System.out.println(livro);
        } else {
            System.out.println("Livro não encontrado");
        }
    }

    private void listarLivros() {
        List<Livro> livros = repository.findAll();
        livros.forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = repository.buscarAutores();
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivosNoAno() {
        try {
            System.out.println("Digite o ano:");
            int ano = leitura.nextInt();
            leitura.nextLine();

            List<Autor> autores = repository.buscarAutoresVivosNoAno(Year.of(ano));
            autores.forEach(System.out::println);
        }catch (InputMismatchException e){
            System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            leitura.nextLine();
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

    private void quantidadeLivrosPorIdioma() {
        System.out.println("""
                Digite o idioma para busca
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
        String idioma = leitura.nextLine();
        Integer quantidadeIdioma = repository.countByIdioma(idioma);
        System.out.printf("O idioma %s tem %d livros cadastrado\n", idioma, quantidadeIdioma);
    }
}
