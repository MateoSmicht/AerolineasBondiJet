package bondiJet;

import java.util.HashMap;
import java.util.Map;

public abstract class Publico extends Vuelo {
	protected int cantidadAsientos;
    protected int [] cantidadTripulantes;
    protected Map<Integer, Pasaje> pasajes;
    protected double [] precio;
    
    public Publico(String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String fecha, int cantidadAsientos, int[] cantidadTripulantes, double[] precio) {
    	super(identificacion, aeropuertoSalida, aeropuertoDestino, fecha);
    	this.cantidadAsientos= cantidadAsientos;
    	this.cantidadTripulantes= cantidadTripulantes;
    	this.precio= precio;
    	this.pasajes = new HashMap<>();
    }
    @Override
    public String GenerarCodigoVuelo(int tamañoDeHashMapVuelos, String publicoOprivado) {
    	StringBuilder codigoVuelo = new StringBuilder();
    	codigoVuelo.append(tamañoDeHashMapVuelos).append("-PUB");
    	String resultado = codigoVuelo.toString();
    	return resultado;
    	}
    }

