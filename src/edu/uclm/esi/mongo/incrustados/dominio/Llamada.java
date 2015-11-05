package edu.uclm.esi.mongo.incrustados.dominio;

import java.util.Calendar;
import java.util.Random;

import org.bson.BsonDateTime;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Llamada {
	private String origen, destino;
	private int duracion;
	private Calendar fecha;
	
	public Llamada(int ultimoNumero) {
		Random r=new Random();
		int rango=ultimoNumero-600000000;
		int iOrigen=600000000+r.nextInt(rango);
		this.origen="" + iOrigen;
		int iDestino=600000000 + r.nextInt(99999999);
		this.destino="" + iDestino;
		double auxi=r.nextDouble();
		if (auxi<0.4) {
			this.duracion=r.nextInt(200);
		} else if (auxi<0.6) {
			this.duracion=r.nextInt(800);
		} else if (auxi<0.8) {
			this.duracion=r.nextInt(1800);
		} else {
			this.duracion=r.nextInt(3000);
		}
		//this.fecha=this.fechaAleatoriaCalendar(2012, 10);
		this.fecha=Calendar.getInstance();
	}
	
	public Llamada(String llamadaEnJSON) throws ParseException {
		JSONParser parser=new JSONParser();
		JSONObject jso=(JSONObject) parser.parse(llamadaEnJSON);
		this.origen=jso.get("origen").toString();
		this.destino=jso.get("destino").toString();
		this.duracion=Integer.parseInt(jso.get("duracion").toString());
	}

	public Llamada(String origen, String destino, int duracion, int year, int mes, int dia, int hora, int minuto, int segundo) {
		this.origen=origen;
		this.destino=destino;
		this.duracion=duracion;
		this.fecha=Calendar.getInstance();
		this.fecha.set(year, mes, dia, hora, minuto, segundo);
	}

	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}

	public int getDuracion() {
		return duracion;
	}

	public Calendar getFecha() {
		return fecha;
	}
	
	@Override
	public String toString() {
		String fechaYHora = this.fecha.get(Calendar.DATE) + "/" + (1+this.fecha.get(Calendar.MONTH)) + "/" + this.fecha.get(Calendar.YEAR) + "; " + 
			this.fecha.get(Calendar.HOUR) + ":" + this.fecha.get(Calendar.MINUTE) + ":" + this.fecha.get(Calendar.SECOND);
		return "De " + this.origen + " a " + this.destino + ", a las " + fechaYHora + "; duración: " + this.duracion + " sg.";
	}

	public BsonDocument toBsonDocument() {
		BsonDocument r=new BsonDocument();
		r.append("origen", new BsonString(origen));
		r.append("destino", new BsonString(destino));
		r.append("duracion", new BsonInt32(duracion));
		r.append("fecha", new BsonDateTime(fecha.getTime().getTime()));
		return r;
	}

	public Document toDocument() {
		Document r=new Document();
		r.put("origen", origen);
		r.put("destino", destino);
		r.put("duracion", duracion);
		r.put("fecha", fecha.getTime());
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() {
		JSONObject r=new JSONObject();
		r.put("origen", origen);
		r.put("destino", destino);
		r.put("duracion", duracion);
		r.put("fecha", fecha.getTime());
		return r;
	}
	
	public static void main(String[] args) {
		Llamada llamada=new Llamada(666666666);
		BsonDocument d=llamada.toBsonDocument();
		System.out.println(d.toJson());
		System.out.println(System.currentTimeMillis());
	}

}
