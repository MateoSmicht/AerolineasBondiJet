package bondiJet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Aerolinea {
	public String nombreAerolinea;
	public String cuit;
	public HashMap<String,Aeropuerto> aeropuertos;
	private Map<Integer, Cliente> clientes;
	private Map<String, Nacional> vuelosPublicosNacional;
	private Map<String, Internacional> vuelosPublicosInternacionales;
	private Map<String, Privado> vuelosPrivados;
	private Privado privado;

	
	public Aerolinea(String nombreAerolinea, String cuit) {
		this.nombreAerolinea= nombreAerolinea;
		this.cuit= cuit;
		this.aeropuertos = new HashMap<>();
		this.clientes = new HashMap<>();
		this.vuelosPublicosNacional = new HashMap<>();
		this.vuelosPublicosInternacionales = new HashMap<>();
		this.vuelosPrivados = new HashMap<>();
	}
	
	public void registrarAeropuerto(String nombreAeropuerto, String pais, String provincia, String direccion) {
		if (aeropuertos.containsKey(nombreAeropuerto)){
			 throw new RuntimeException("El aeropuerto ya existe en el sistema " );
		 }else {
			 Aeropuerto aeropuertoNuevo= new Aeropuerto(nombreAeropuerto, pais,provincia,direccion);
			 aeropuertos.put(nombreAeropuerto, aeropuertoNuevo);
		 }
	}
	 public void registrarCliente(int dni, String nombre,String telefono) {
		 
		 if (clientes.containsKey(dni)){
			 throw new RuntimeException("El cliente ya esta cargado " );
		 }else {
			 Cliente clienteNuevo = new Cliente(dni, nombre, telefono);
			 clientes.put(dni, clienteNuevo);
		 }
}
	 public String registrarVueloPublicoNacional(String origen, String destino, String fecha, int tripulantes, double valorRefrigerio, double[] precios, int[] cantAsientos) {
		 	int cantidadDeVuelosNacionales= vuelosPublicosNacional.size();
		 	// Crea una instancia temporal de Nacional para generar el código de vuelo Nacional 
		 	Nacional tempNacional = new Nacional(valorRefrigerio, "", aeropuertos.get(origen), aeropuertos.get(destino), fecha, cantAsientos, tripulantes, precios);
		 	String codigo = tempNacional.generarCodigoVuelo(cantidadDeVuelosNacionales);
		 	if(tempNacional.elVueloEsNacional(aeropuertos, origen, destino, cantAsientos, precios)==false) {
		 		throw new RuntimeException("El vuelo no es nacional" );
		 	}
				Nacional nuevoVueloPubNacional= new Nacional(valorRefrigerio,codigo,aeropuertos.get(origen),
						aeropuertos.get(destino), fecha, cantAsientos, tripulantes, precios );
				vuelosPublicosNacional.put(codigo, nuevoVueloPubNacional);
				return codigo;
			}	
	public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes, double valorRefrigerio, 
				int cantRefrigerios, double[] precios,  int[] cantAsientos,  String[] escalas) {
		int cantidadDeVuelosInternacionales= vuelosPublicosInternacionales.size();
	 	// Crea una instancia temporal de Nacional para generar el código de vuelo Nacional 
	 	Internacional tempInternacional = new Internacional(cantRefrigerios, valorRefrigerio,  escalas, "",  aeropuertos.get(origen),  aeropuertos.get(destino),  fecha, cantAsientos,  tripulantes, precios);
	 	String codigo = tempInternacional.generarCodigoVuelo(cantidadDeVuelosInternacionales);
	 	if(tempInternacional.elVueloEsInternacional(aeropuertos, origen, destino, cantAsientos, precios)==false) {
	 		throw new RuntimeException("El vuelo no es internacional" );
	 	}
	 	Internacional nuevoVueloPubInternacional= new Internacional(cantRefrigerios, valorRefrigerio,  escalas, codigo,  aeropuertos.get(origen),  aeropuertos.get(destino),  fecha, cantAsientos,  tripulantes, precios);
		vuelosPublicosInternacionales.put(codigo, nuevoVueloPubInternacional);
		return codigo;
	}
	public String VenderVueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio,  int dniComprador, int[] acompaniantes) {
		Privado tempPrivado = new Privado(dniComprador, acompaniantes, tripulantes, precio, 0, "", aeropuertos.get(origen), aeropuertos.get(destino), fecha);
		int cantidadDeVuelosPrivados= vuelosPrivados.size();
	 	String codigoPriv = tempPrivado.generarCodigoVueloPriv(cantidadDeVuelosPrivados);
	 	int jets= tempPrivado.calcularJetsNecesarios(acompaniantes);
	 	double precioTotal = tempPrivado.calcularPrecioFinal(jets);
	 	Privado nuevoVueloPrivado= new Privado( dniComprador ,  acompaniantes,  tripulantes,  precioTotal,  jets,  codigoPriv,  aeropuertos.get(origen),  aeropuertos.get(destino),  fecha );
	 	vuelosPrivados.put(codigoPriv, nuevoVueloPrivado);
	 	return codigoPriv;
	}
	
}//end
	


	 
