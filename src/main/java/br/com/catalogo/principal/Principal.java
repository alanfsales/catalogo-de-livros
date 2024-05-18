package br.com.catalogo.principal;

import br.com.catalogo.service.ConsultaAPI;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Principal {

    public void exibeMenu(){
        String menu = """
                1 - Buscar livros por título
                2 - Listar livros registrados
                
                """;
        ConsultaAPI consultaAPI = new ConsultaAPI();
        String json = consultaAPI.buscar("https://gutendex.com/books/?search=dom+casmurro");

        System.out.println(json);

        ObjectMapper mapper = new ObjectMapper();
        mapper.
    }
}
