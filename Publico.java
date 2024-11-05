package bondiJet;

import java.util.HashMap;
import java.util.Map;

public abstract class Publico extends Vuelo {
	protected int[] cantidadAsientos;
    protected int  cantidadTripulantes;
    protected Map<Integer, Pasaje> pasajes;
    protected double [] precio;
    
    public Publico(String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String fecha, int []cantidadAsientos, int cantidadTripulantes, double[] precio) {
    	super(identificacion, aeropuertoSalida, aeropuertoDestino, fecha);
    	this.cantidadAsientos= cantidadAsientos;
    	this.cantidadTripulantes= cantidadTripulantes;
    	this.precio= precio;
    	this.pasajes = new HashMap<>();
    }
 
    public String generarCodigoVuelo(int tamañoDeHashMapVuelos) {
    	String numeroString = Integer.toString(tamañoDeHashMapVuelos+1);
    	StringBuilder codigoVuelo = new StringBuilder();
    	codigoVuelo.append("{").append(numeroString).append("}").append("-PUB");
    	String resultado = codigoVuelo.toString();
    	return resultado;
    	}
   
    }

