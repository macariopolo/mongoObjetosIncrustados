package edu.uclm.esi.mongo.incrustados.dominio;



//Cuota fija: 25 euros; gratis en fin de semana; resto, establecimiento: 0,35 + 0,01 euros/segundo
public class TarifaFinDeSemana extends Tarifa {

	@Override
	public double getCuotaFija() {
		return 25;
	}

	@Override
	public double getEstablecimiento() {
		return 0.35;
	}

	@Override
	public double getImportePorSegundo() {
		return 0.01;
	}

}
