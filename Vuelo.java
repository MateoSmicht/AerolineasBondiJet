package bondiJet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Vuelo {
	protected String identificacion;
	protected Aeropuerto aeropuertoSalida;
    protected Aeropuerto aeropuertoDestino;
    protected String fecha;
    
    
    public Vuelo(String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String fecha) {
    	this.identificacion = this.generarCodigoVuelo(identificacion);
    	this.aeropuertoSalida = aeropuertoSalida;
    	this.aeropuertoDestino = aeropuertoDestino;
    	this.fecha = fecha;
    }
    
    public boolean esUnVueloValido(){
    	if(this.aeropuertoDestino.equals(null) || this.aeropuertoSalida.equals(null)) {
    		return false;
    	}else {
    		return true;
    	}
    }
    public Vuelo crear() {
    	return new Vuelo (this.identificacion, this.aeropuertoSalida, this.aeropuertoDestino, this.fecha);
    }
 // Getters
    public String getIdentificacion() {
        return identificacion;
    }

    public Aeropuerto getAeropuertoSalida() {
        return aeropuertoSalida;
    }

    public Aeropuerto getAeropuertoDestino() {
        return aeropuertoDestino;
    }

    public String getFecha() {
        return fecha;
    }
    
    public boolean esFechaPosterior(String fecha) {
        try {
            // Define el formato de la fecha
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      
            // Convierte la cadena de fecha a un objeto LocalDate
            LocalDate fechaDada = LocalDate.parse(fecha, formatter);
            
            // Obtén la fecha actual
            LocalDate fechaActual = LocalDate.now();
            
            // Compara las fechas
            return fechaDada.isAfter(fechaActual);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido: " + fecha);
            return false;
        }
    }
    
    public String generarCodigoVuelo(String tamañoDeHashMapVuelos) {
    	StringBuilder codigoVuelo = new StringBuilder();
    	codigoVuelo.append("{").append(tamañoDeHashMapVuelos).append("}");
    	String resultado = codigoVuelo.toString();
    	return resultado;
    	}
    public String verDatos() {
    	return toString();
    }
    
    @Override
    public String toString() {
    	return "Id vuelo:" + this.identificacion + ", Origen:" + this.aeropuertoSalida + ", Destino:" + this.aeropuertoSalida + ", fecha:" + this.fecha;
    }

	
    
   
}
    

