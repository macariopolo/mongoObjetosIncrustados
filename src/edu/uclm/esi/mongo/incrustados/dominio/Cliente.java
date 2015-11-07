package edu.uclm.esi.mongo.incrustados.dominio;

import java.util.GregorianCalendar;
import java.util.Vector;

import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.Document;

public class Cliente {
	private int id;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String dni;
	private String telefono;
	private GregorianCalendar fechaDeNacimiento;
	private Tarifa tarifa;
	private Vector<Llamada> llamadas;

	public Cliente(int id, String nombre, String apellido1, String apellido2, String dni, String telefono, int tarifa, GregorianCalendar fechaDeNacimiento) {
		this.id=id;
		this.nombre=nombre;
		this.apellido1=apellido1;
		this.apellido2=apellido2;
		this.dni=dni;
		this.telefono=telefono;
		this.fechaDeNacimiento=fechaDeNacimiento;
		this.tarifa=buildTarifa(tarifa);
		this.llamadas=new Vector<>();
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido1() {
		return apellido1;
	}
	
	public String getApellido2() {
		return apellido2;
	}

	public String getDni() {
		return dni;
	}

	public String getTelefono() {
		return telefono;
	}
	
	public GregorianCalendar getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}

	public Tarifa buildTarifa(int tarifa) {
		switch (tarifa) {
		case Constantes.PLANA :
			return new TarifaPlana();
		case Constantes.CINCUENTA_MINUTOS :
			return new Tarifa50Minutos();
		case Constantes.FIN_DE_SEMANA :
			return new TarifaFinDeSemana();
		case Constantes.TARDES :
			return new TarifaTardes();
		default:
			return null;
		}
	}

	public Tarifa getTarifa() {
		return this.tarifa;
	}

	public Document toDocument() {
		Document r=new Document();
		r.append("id", id);
		r.append("nombre", nombre);
		r.append("apellido1", apellido1);
		r.append("apellido2", apellido2);
		r.append("dni", dni);
		r.append("telefono", telefono);
		r.append("fechaDeNacimiento", fechaDeNacimiento.getTime());
		Vector<Document> dLlamadas=new Vector<>();
		for (Llamada llamada : llamadas)
			dLlamadas.add(llamada.toDocument());
		r.append("llamadas", dLlamadas);
		r.append("tarifa", tarifa.toDocument());
		return r;
	}
	
	public BsonDocument toBsonDocument() {
		BsonDocument r=new BsonDocument();
		r.append("id", new BsonInt32(id));
		r.append("nombre", new BsonString(nombre));
		r.append("apellido1", new BsonString(apellido1));
		r.append("apellido2", new BsonString(apellido2));
		r.append("dni", new BsonString(dni));
		r.append("telefono", new BsonString(telefono));
		BsonArray llamadas=new BsonArray();
		for (Llamada llamada : this.llamadas)
			llamadas.add(llamada.toBsonDocument());
		
		r.append("llamadas", llamadas);
		r.append("tarifa", tarifa.toBsonDocument());
		return r;
	}

	public void addLlamada(Llamada llamada) {
		this.llamadas.add(llamada);
	}

	public void setTarifa(Tarifa t) {
		this.tarifa=t;
	}

	public Vector<Llamada> getLlamadas() {
		return llamadas;
	}
}
