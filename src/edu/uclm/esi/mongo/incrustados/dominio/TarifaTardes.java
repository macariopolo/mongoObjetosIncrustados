package edu.uclm.esi.mongo.incrustados.dominio;



//Cuota fija: 25 euros; Gratis de 16 a 24; otras, establecimiento: 0,30 + 0,008 euros/segundo
public class TarifaTardes extends Tarifa {

	@Override
	public double getCuotaFija() {
		return 25;
	}

	@Override
	public double getEstablecimiento() {
		return 0.30;
	}

	@Override
	public double getImportePorSegundo() {
		return 0.08;
	}

}
