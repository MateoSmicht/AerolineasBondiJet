package bondiJet;

public abstract class Vuelo {
	protected String identificacion;
    protected Aeropuerto aeropuertoSalida;
    protected Aeropuerto aeropuertoDestino;
    protected String fecha;
    
    public Vuelo(String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String fecha) {
    	this.identificacion = identificacion;
    	this.aeropuertoSalida = aeropuertoSalida;
    	this.aeropuertoDestino = aeropuertoDestino;
    	this.fecha = fecha;
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
    
   
}
    

