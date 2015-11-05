package edu.uclm.esi.mongo.incrustados.dominio;



//Cuota fija: 15 euros; 50 minutos gratis pgando 0,10 de establecimiento; luego, establecimiento + 0,01 euros/segundo
public class Tarifa50Minutos extends Tarifa {

	
	@Override
	public double getCuotaFija() {
		return 15;
	}

	@Override
	public double getEstablecimiento() {
		return 0.15;
	}

	@Override
	public double getImportePorSegundo() {
		return 0.01;
	}


}
