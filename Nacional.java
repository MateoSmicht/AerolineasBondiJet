package bondiJet;

public class Nacional extends Publico {
	public Nacional(String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String horaSalida, String horaDestino, int cantidadAsientos, int cantidadTripulantes) {
        this.identificacion = identificacion;
        this.aeropuertoSalida = aeropuertoSalida;
        this.aeropuertoDestino = aeropuertoDestino;
        this.horaSalida = horaSalida;
        this.horaDestino = horaDestino;
        this.cantidadAsientos = cantidadAsientos;
        this.cantidadTripulantes = cantidadTripulantes;
        this.clientes = new LinckedList<>();
        this.pasajesConNumeroAsiento = new LinckedList<>();
        this.secciones = new LinckedList<>();
        this.estadoActivo = false;
        
        
    }
}
