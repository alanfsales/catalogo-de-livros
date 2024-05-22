package br.com.catalogo.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

    private String idioma;

    private Double numeroDownload;

    public Livro() {}

    public Livro(LivroDTO livroDTO) {
        this.titulo = livroDTO.titulo();
        Autor autor = new Autor(livroDTO.authors().get(0));
        this.autor = autor;
        this.idioma = livroDTO.idioma().get(0);
        this.numeroDownload = livroDTO.numeroDownload();
    }

    public Livro(Long idApi, String titulo, Autor autor, String idioma, Double numeroDownload) {
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
        return  "------------------ LIVRO ------------------" +
                "\nTítulo:             " + titulo +
                "\nAutor:              " + autor.getAutor() +
                "\nIdioma:             " + idioma +
                "\nNúmero de Download: " + numeroDownload +
                "\n-------------------------------------------\n";
    }
}
