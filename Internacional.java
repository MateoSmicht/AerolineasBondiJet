package bondiJet;

public class Internacional extends Publico {
	private boolean vueloTieneEscala;
    private List<Aeropuerto> listaEscala;
    private int cantidadRefrigerios;

    public Internacional(String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String horaSalida, String horaDestino, boolean vueloTieneEscala, int cantidadAsientos, int cantidadTripulantes, int cantidadRefrigerios) {
        this.identificacion = identificacion;
        this.aeropuertoSalida = aeropuertoSalida;
        this.aeropuertoDestino = aeropuertoDestino;
        this.horaSalida = horaSalida;
        this.horaDestino = horaDestino;
        this.vueloTieneEscala = vueloTieneEscala;
        this.cantidadAsientos = cantidadAsientos;
        this.cantidadTripulantes = cantidadTripulantes;
        this.cantidadRefrigerios = cantidadRefrigerios;
        this.clientes = new ArrayList<>();
        this.pasajesConNumeroAsiento = new ArrayList<>();
        this.secciones = new ArrayList<>();
        this.listaEscala = new ArrayList<>();
        this.estadoActivo = true;
    }
}
