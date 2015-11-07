package edu.uclm.esi.mongo.incrustados.persistencia;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Vector;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.uclm.esi.mongo.incrustados.dominio.Cliente;
import edu.uclm.esi.mongo.incrustados.dominio.Llamada;

public class CargadoraSintetica {

public static void main(String[] args) throws IOException {
	final int NUMERO_DE_CLIENTES = 90;
	MongoClient mongoClient=new MongoClient("localhost", 27017);
	
	MongoDatabase db = mongoClient.getDatabase("telefonosIncrustados");
	
	if (db.getCollection("clientes")==null)
		db.createCollection("clientes");

	MongoCollection<Document> clientes=db.getCollection("clientes"); 
	
	String nombresFileName="/Users/Maco/Google Drive/docencia/2015-2016/mongoDB/nombres.txt";
	String apellidosFileName="/Users/Maco/Google Drive/docencia/2015-2016/mongoDB/apellidos.txt";
	Vector<String> nombres=read(nombresFileName);
	Vector<String> apellidos=read(apellidosFileName);
	
	int cont=0;
	Random dado=new Random();
	for (int i=0; i<nombres.size()*apellidos.size(); i++) {
		int posNombre=dado.nextInt(nombres.size());
		int posApellido1=dado.nextInt(apellidos.size());
		int posApellido2=dado.nextInt(apellidos.size());
		int iDNI=5000000+dado.nextInt(5000000);
		int iTelefono=600000000+i;
		int tarifa=dado.nextInt(4);
		GregorianCalendar fechaDeNacimiento = fechaAlAzar();
		
		String nombre=nombres.get(posNombre);
		String apellido1=apellidos.get(posApellido1);
		String apellido2=apellidos.get(posApellido2);
		String dni=""+iDNI;
		String telefono=""+iTelefono;
		Cliente c=new Cliente((cont+1), nombre, apellido1, apellido2, dni, telefono, tarifa, fechaDeNacimiento);
		int favorito; String sFavorito;
		do {
			favorito=(600000000 + dado.nextInt(NUMERO_DE_CLIENTES));
			sFavorito="" + favorito;
		} while (sFavorito.equals(telefono));
		addLlamadas(c, sFavorito, NUMERO_DE_CLIENTES);
		Document d=c.toDocument();
		clientes.insertOne(d);
		cont++;
		if (cont==NUMERO_DE_CLIENTES)
			break;
	}
	mongoClient.close();
}
	
	private static Vector<String> read(String fileName) throws IOException {
		Vector<String> result=new Vector<>();
		FileInputStream fis = new FileInputStream(fileName);
		DataInputStream dis = new DataInputStream(fis);
		BufferedReader br = new BufferedReader(new InputStreamReader(dis));
		String linea;
		while ((linea = br.readLine()) != null) {
			result.add(linea);
		}
		fis.close();
		return result;
	}

	private static void addLlamadas(Cliente c, String favorito, int numClientes) {
		Random dado=new Random();
		for (int mes=5; mes<=10; mes++) {
			for (int dia=1; dia<=30; dia++) {
				int numLlamadas=dado.nextInt(4);
				for (int j=0; j<numLlamadas; j++) {
					int n=dado.nextInt(numClientes);
					String telefonoDestino="" + (600000000 + n);
					if (c.getTelefono().equals(telefonoDestino))
						continue;
					int duracion=dado.nextInt(600);
					int year=2015;
					int hora=dado.nextInt(24), minuto=dado.nextInt(60), segundo=dado.nextInt(60);
					if (dado.nextFloat()<0.3)
						telefonoDestino=favorito;
					Llamada llamada=new Llamada(c.getTelefono(), telefonoDestino, duracion, year, mes, dia, hora, minuto, segundo);
					c.addLlamada(llamada);
				}
			}
		}
	}
	
	// Tomada de: http://stackoverflow.com/questions/3985392/generate-random-date-of-birth
	private static GregorianCalendar fechaAlAzar() {
		GregorianCalendar result = new GregorianCalendar();
		int year = randBetween(1930, 2010);
		result.set(GregorianCalendar.YEAR, year);
		int dayOfYear = randBetween(1, result.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));
		result.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);
		return result;
	}
	
	private static int randBetween(int start, int end) {
		return start + (int)Math.round(Math.random() * (end - start));
	}
}
