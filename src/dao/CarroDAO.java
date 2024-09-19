package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import model.Carro;

public class CarroDAO {

	private List<Carro> produtos;
	private int maxId = 0;

	private File file;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	public int getMaxId() {
		return maxId;
	}

	public CarroDAO(String filename) throws IOException {
		file = new File(filename);
		produtos = new ArrayList<Carro>();
		if (file.exists()) {
			readFromFile();
		}

	}

	public void add(Carro carro) {
		try {
			produtos.add(carro);
			this.maxId = (carro.getId() > this.maxId) ? carro.getId() : this.maxId;
			this.saveToFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o produto '" + carro.getDescricao() + "' no disco!");
		}
	}

	public Carro get(int id) {
		for (Carro carro : carros) {
			if (id == carro.getId()) {
				return carro;
			}
		}
		return null;
	}

	public void update(Carro c) {
		int index = produtos.indexOf(c);
		if (index != -1) {
			produtos.set(index, c);
			this.saveToFile();
		}
	}

	public void remove(Carro c) {
		int index = produtos.indexOf(c);
		if (index != -1) {
			produtos.remove(index);
			this.saveToFile();
		}
	}

	public List<Carro> getAll() {
		return carros;
	}

	private List<Carro> readFromFile() {
		produtos.clear();
		Carro carroo = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {

			while (fis.available() > 0) {
				carro = (Carro) inputFile.readObject();
				carros.add(carro);
				maxId = (carro.getId() > maxId) ? carro.getId() : maxId;
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar produto no disco!");
			e.printStackTrace();
		}
		return produtos;
	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Carro carro : carros) {
				outputFile.writeObject(carro);
			}
			outputFile.flush();
			this.close();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar produto no disco!");
			e.printStackTrace();
		}
	}

	private void close() throws IOException {
		outputFile.close();
		fos.close();
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			this.saveToFile();
			this.close();
		} catch (Exception e) {
			System.out.println("ERRO ao salvar a base de dados no disco!");
			e.printStackTrace();
		}
	}
}
