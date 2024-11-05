package bondiJet;

import java.util.HashMap;
import java.util.Map;

public abstract class Publico extends Vuelo {
	protected int cantidadAsientos;
    protected int cantidadTripulantes;
    protected Map<Integer, Pasaje> pasajes;
    protected int seccion;
    
    public Publico(String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String horaSalida, String horaDestino,int cantidadAsientos, int cantidadTripulantes, int seccion) {
    	super(identificacion, aeropuertoSalida, aeropuertoDestino, horaSalida, horaDestino);
    	this.cantidadAsientos= cantidadAsientos;
    	this.cantidadTripulantes= cantidadTripulantes;
    	this.seccion= seccion;
    	this.pasajes = new HashMap<>();
    }
}
