package app;

import static spark.Spark.*;


public class Aplicacao {
	
private static CarroService carroService = new CarroService();
	
    public static void main(String[] args) {
        /* port(nao conectado a nenhum banco de dados); */

        post("/carro", (request, response) -> carroService.add(request, response));

        get("/carro/:id", (request, response) -> carroService.get(request, response));

        get("/carro/update/:id", (request, response) -> carroService.update(request, response));

        get("/carro/delete/:id", (request, response) -> carroService.remove(request, response));

        get("/carro", (request, response) -> carroService.getAll(request, response));
               
    }
}
