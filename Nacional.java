package bondiJet;

import java.util.HashMap;

public class Nacional extends Publico {
	private double refrigerio;

	public Nacional(double refrigerio, String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino,
			String fecha, int[] cantidadAsientos, int cantidadTripulantes, double[] precio) {
		super(identificacion, aeropuertoSalida, aeropuertoDestino, fecha, cantidadAsientos, cantidadTripulantes,
				precio);
		this.refrigerio = refrigerio;
	}


	@Override
	public double valorPasaje(int seccionAsiento) {
		double costo = (super.valorPasaje(seccionAsiento) + this.refrigerio) * 0.20; // agregamos el %20 de impuertos
		return costo;
	}

	@Override
	public boolean esUnVueloValido() {
		if ( this.aeropuertoDestino.equals(null)
				|| this.aeropuertoSalida.equals(null))
			return false;
		if (this.aeropuertoDestino.getPais().equals("Argentina")
				&& this.aeropuertoSalida.getPais().equals("Argentina")) {
			if (this.cantidadAsientos.length == 2 && this.precio.length == 2) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String verDatos() {
		return super.verDatos() + ", valor refrigerio:" + this.refrigerio;
	}

}