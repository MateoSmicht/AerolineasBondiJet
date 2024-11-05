package bondiJet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Aerolinea {
	public String nombreAerolinea;
	public String cuit;
	public Map<String,Aeropuerto> aeropuertos;
	private Aeropuerto aeropuerto;
	private Publico publico;
	private Nacional nacional;
	private Map<Integer, Cliente> clientes;
	private Map<String, Nacional> vuelosPublicosNacional;
	
	public Aerolinea(String nombreAerolinea, String cuit) {
		this.nombreAerolinea= nombreAerolinea;
		this.cuit= cuit;
		this.aeropuertos = new HashMap<>();
		this.clientes = new HashMap<>();
		this.vuelosPublicosNacional = new HashMap<>();
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
		 	 	//GENERAMOS EL CODIGO DEL VUELO PUBLICO
				String numeroString = Integer.toString(cantidadDeVuelosNacionales+1);
		    	StringBuilder codigoVuelo = new StringBuilder();
		    	codigoVuelo.append("{").append(numeroString).append("}").append("-PUB");
		    	String codigo = codigoVuelo.toString();
		    	if (cantAsientos.length != 2 && precios.length != 2 ) {
		    		throw new RuntimeException("Los vuelos publicos nacionales tiene 2 secciones" );
		    	}
		    	if (aeropuertos.get(destino).getPais().equals("Argentina") && aeropuertos.get(origen).getPais().equals("Argentina")) {
		    		
		    	if (vuelosPublicosNacional.containsKey(codigo)) {
		    		throw new RuntimeException("El vuelo ya esta registrado" ); //Si el vuelo ya existe sale una excepcion
				 }else {
				Nacional nuevoVueloPubNacional= new Nacional(valorRefrigerio,codigo,aeropuertos.get(origen),
						aeropuertos.get(destino), fecha, cantAsientos, tripulantes, precios );
				vuelosPublicosNacional.put(codigo, nuevoVueloPubNacional);
				return codigo;
			}	
		    	} else {
		    		throw new RuntimeException("El vuelo no es nacional" ); 
		    	}
	 }
}
	 
