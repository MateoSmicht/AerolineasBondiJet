package bondiJet;

import java.util.HashMap;
import java.util.Map;

public class Publico extends Vuelo {
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
  
    public double valorPasaje(int seccionAsiento) {
    	double precioSeccion = precio[seccionAsiento]; // (ej. 0 para Turista, 1 para Ejecutiva)
    	return precioSeccion;
    }
    @Override
    public String generarCodigoVuelo(int tamañoDeHashMapVuelos) {
    	StringBuilder codigoVuelo = new StringBuilder();
    	codigoVuelo.append(super.generarCodigoVuelo(tamañoDeHashMapVuelos)).append("-PUB");
    	String resultado = codigoVuelo.toString();
    	return resultado;
    	}
    
    @Override
    public String verDatos() {
    	return super.verDatos() + toString();
    }
    @Override
    public String toString() {
    	return "Cantidad Asientos: "+ this.cantidadAsientos + ", Cantidad tripulantes:" + this.cantidadTripulantes + ", precio:" +  this.precio;
    }
    }

