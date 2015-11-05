package edu.uclm.esi.mongo.incrustados.dominio;

import org.bson.BsonDocument;
import org.bson.BsonDouble;
import org.bson.BsonString;
import org.bson.Document;


public abstract class Tarifa {
	public abstract double getCuotaFija();
	public abstract double getEstablecimiento();
	public abstract double getImportePorSegundo();
	
	public Document toDocument() {
		Document r=new Document();
		r.append("tipo", this.getClass().getSimpleName());
		r.append("cuotaFija", this.getCuotaFija());
		r.append("establecimiento", this.getEstablecimiento());
		r.append("importePorSegundo", this.getImportePorSegundo());
		return r;
	}
	
	public BsonDocument toBsonDocument() {
		BsonDocument r=new BsonDocument();
		r.append("tipo", new BsonString(this.getClass().getSimpleName()));
		r.append("cuotaFija", new BsonDouble(this.getCuotaFija()));
		r.append("establecimiento", new BsonDouble(this.getEstablecimiento()));
		r.append("importePorSegundo", new BsonDouble(this.getImportePorSegundo()));
		return r;
	}
}
