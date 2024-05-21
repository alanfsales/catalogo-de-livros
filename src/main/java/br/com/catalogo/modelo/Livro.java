package br.com.catalogo.modelo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long idApi;

    private String titulo;

    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

    private String idioma;

    private Double numeroDownload;

    public Livro() {}

    public Livro(LivroDTO livroDTO) {
        this.idApi = livroDTO.id();
        this.titulo = livroDTO.titulo();
        Autor autor = new Autor(livroDTO.authors().get(0));
        this.autor = autor;
        this.idioma = livroDTO.idioma().get(0);
        this.numeroDownload = livroDTO.numeroDownload();
    }

    public Livro(Long idApi, String titulo, Autor autor, String idioma, Double numeroDownload) {
        this.idApi = idApi;
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDownload = numeroDownload;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdApi() {
        return idApi;
    }

    public void setIdApi(Long idApi) {
        this.idApi = idApi;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idioma;
    }

    public void setIdiomas(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDownload() {
        return numeroDownload;
    }

    public void setNumeroDownload(Double numeroDownload) {
        this.numeroDownload = numeroDownload;
    }

    @Override
    public String toString() {
        return "----- LIVRO -----" +
                "\nTítulo: " + titulo +
                "\nAutor: " + autor.getAutor() +
                "\nIdioma: " + idioma +
                "\nNúmero de Download: " + numeroDownload +
                "\n-----------------\n";
    }
}
