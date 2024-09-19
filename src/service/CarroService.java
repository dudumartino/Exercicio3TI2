package service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import dao.CarroDAO;
import model.Carroo;
import spark.Request;
import spark.Response;

public class CarroService {

	private CarroDAO carroDAO;

	public CarrooService() {
		try {
			carroDAO = new CarroDAO("produto.dat");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public Object add(Request request, Response response) {
		String descricao = request.queryParams("descricao");
		float preco = Float.parseFloat(request.queryParams("preco"));
		int quantidade = Integer.parseInt(request.queryParams("quantidade"));
		LocalDateTime dataFabricacao = LocalDateTime.parse(request.queryParams("dataFabricacao"));

		int id = carroDAO.getMaxId() + 1;

		Carro carro = new carro(id, descricao, preco, quantidade, dataFabricacao);

		carroDAO.add(carro);

		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		Carro carro = (carro) carroDAO.get(id);
		
		if (carro != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<produto>\n" + 
            		"\t<id>" + carro.getId() + "</id>\n" +
            		"\t<descricao>" + carro.getDescricao() + "</descricao>\n" +
            		"\t<preco>" + carro.getPreco() + "</preco>\n" +
            		"\t<quantidade>" + carro.getQuant() + "</quantidade>\n" +
            		"\t<fabricacao>" + carro.getDataFabricacao() + "</fabricacao>\n" +
            		"</produto>\n";
        } else {
            response.status(404); // 404 Not found
            return "Carro " + id + " não encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
		Carro carro = (carro) carroDAO.get(id);

        if (carro != null) {
        	carro.setDescricao(request.queryParams("descricao"));
        	carro.setPreco(Float.parseFloat(request.queryParams("preco")));
        	carro.setQuant(Integer.parseInt(request.queryParams("quantidade")));
        	carro.setDataFabricacao(LocalDateTime.parse(request.queryParams("dataFabricacao")));

        	carroDAO.update(carro);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "carro não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Carro carro = (carro) carroDAO.get(id);

        if (carro != null) {

        	carroDAO.remove(carro);

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "carro não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<produtos type=\"array\">");
		for (Carro carro : carroDAO.getAll()) {
			returnValue.append("\n<carro>\n" + 
            		"\t<id>" + carro.getId() + "</id>\n" +
            		"\t<descricao>" + carro.getDescricao() + "</descricao>\n" +
            		"\t<preco>" + carro.getPreco() + "</preco>\n" +
            		"\t<quantidade>" + carro.getQuant() + "</quantidade>\n" +
            		"\t<fabricacao>" + carro.getDataFabricacao() + "</fabricacao>\n" +    		
            		"</carro>\n");
		}
		returnValue.append("</carros>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}
