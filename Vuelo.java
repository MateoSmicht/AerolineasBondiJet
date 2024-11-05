package bondiJet;

public abstract class Vuelo {
	protected String identificacion;
    protected Aeropuerto aeropuertoSalida;
    protected Aeropuerto aeropuertoDestino;
    protected String horaSalida;
    protected String horaDestino;
    
    public Vuelo(String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String horaSalida, String horaDestino) {
    	this.identificacion = identificacion;
    	this.aeropuertoSalida = aeropuertoSalida;
    	this.aeropuertoDestino = aeropuertoDestino;
    	this.horaSalida = horaSalida;
    	this.horaDestino = horaDestino;
    }
}
