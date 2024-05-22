package br.com.catalogo.modelo;

import jakarta.persistence.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String autor;

    private Year anoNascimento;

    private Year anoFalecimento;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    List<Livro> livros = new ArrayList<>();

    public Autor() {}

    public Autor(AutorDTO autorDTO) {
        this.autor = autorDTO.autor();
        this.anoNascimento = Year.of(autorDTO.anoNascimento());
        this.anoFalecimento = Year.of(autorDTO.anoFalecimento());
    }

    public Autor(String autor, Year anoNascimento, Year anoFalecimento) {
        this.autor = autor;
        this.anoNascimento = anoNascimento;
        this.anoFalecimento = anoFalecimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Year getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Year anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Year getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Year anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        return  "------------------- Autor -----------------" +
                "\nAutor:           " + autor +
                "\nAno Nascimento:  " + anoNascimento +
                "\nAno Falecimento: " + anoFalecimento +
                "\nLivros:          " + livros.stream().map(l -> l.getTitulo()).collect(Collectors.toList())+
                "\n-------------------------------------------\n";
    }
}
