package bondiJet;

public class Nacional extends Publico {
	private int refrigerio;
	public Nacional (int refrigerio, String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String horaSalida, String horaDestino,int cantidadAsientos, int cantidadTripulantes, int seccion) {
	super(horaDestino, aeropuertoDestino, aeropuertoDestino, horaDestino, horaDestino, cantidadAsientos, cantidadTripulantes, seccion);
	this.refrigerio= 1;
}
}