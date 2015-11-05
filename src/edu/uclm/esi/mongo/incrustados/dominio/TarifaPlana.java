package edu.uclm.esi.mongo.incrustados.dominio;


//Cuota fija: 250 euros; establec. llamada: 0,15
public class TarifaPlana extends Tarifa {



	@Override
	public double getCuotaFija() {
		return 250;
	}

	@Override
	public double getEstablecimiento() {
		return 0.15;
	}

	@Override
	public double getImportePorSegundo() {
		return 0;
	}


}
