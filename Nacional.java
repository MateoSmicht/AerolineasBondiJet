package bondiJet;

public class Nacional extends Publico {
	private double refrigerio;
	public Nacional (double refrigerio, String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String fecha, int [] cantidadAsientos, int cantidadTripulantes, double[] precio) {
	super( identificacion,  aeropuertoSalida,  aeropuertoDestino,  fecha,  cantidadTripulantes,  cantidadAsientos, precio);
	this.refrigerio= refrigerio;
}
}