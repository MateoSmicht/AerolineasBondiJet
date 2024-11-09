package bondiJet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aerolinea implements IAerolinea {
	public String nombreAerolinea;
	public String cuit;
	public HashMap<String,Aeropuerto> aeropuertos;
	private Map<Integer, Cliente> clientes;
	private Map<String, Nacional> vuelosPublicosNacional;
	private Map<String, Internacional> vuelosPublicosInternacionales;
	private Map<String, Privado> vuelosPrivados;
	private Map<String, Vuelo> vuelos; // Mapa para todos los vuelo
	private Map<Integer, Pasaje> pasajes;
	

	
	
	public Aerolinea(String nombreAerolinea, String cuit) {
		this.nombreAerolinea= nombreAerolinea;
		this.cuit= cuit;
		this.aeropuertos = new HashMap<>();
		this.clientes = new HashMap<>();
		this.vuelosPublicosNacional = new HashMap<>();
		this.vuelosPublicosInternacionales = new HashMap<>();
		this.vuelosPrivados = new HashMap<>();
		this.vuelos = new HashMap<>();
		this.pasajes = new HashMap<>();
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
		 	int cantidadDeVuelosNacionales= vuelos.size();
		 	// Crea una instancia temporal de Nacional para generar el código de vuelo Nacional 
		 	Nacional tempNacional = new Nacional(valorRefrigerio, "", aeropuertos.get(origen), aeropuertos.get(destino), fecha, cantAsientos, tripulantes, precios);
		 	String codigo = tempNacional.generarCodigoVuelo(cantidadDeVuelosNacionales);
		 	if(tempNacional.elVueloEsNacional(aeropuertos, origen, destino, cantAsientos, precios)==false) {
		 		throw new RuntimeException("El vuelo no es nacional" );
		 	}
				Nacional nuevoVueloPubNacional= new Nacional(valorRefrigerio,codigo,aeropuertos.get(origen),
						aeropuertos.get(destino), fecha, cantAsientos, tripulantes, precios );
				vuelosPublicosNacional.put(codigo, nuevoVueloPubNacional);
				vuelos.put(codigo, nuevoVueloPubNacional);
				return codigo;
			}	
	public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes, double valorRefrigerio, 
				int cantRefrigerios, double[] precios,  int[] cantAsientos,  String[] escalas) {
		int cantidadDeVuelosInternacionales= vuelos.size();
	 	// Crea una instancia temporal de Nacional para generar el código de vuelo Nacional 
	 	Internacional tempInternacional = new Internacional(cantRefrigerios, valorRefrigerio,  escalas, "",  aeropuertos.get(origen),  aeropuertos.get(destino),  fecha, cantAsientos,  tripulantes, precios);
	 	String codigo = tempInternacional.generarCodigoVuelo(cantidadDeVuelosInternacionales);
	 	if(tempInternacional.elVueloEsInternacional(aeropuertos, origen, destino, cantAsientos, precios)==false) {
	 		throw new RuntimeException("El vuelo no es internacional" );
	 	}
	 	Internacional nuevoVueloPubInternacional= new Internacional(cantRefrigerios, valorRefrigerio,  escalas, codigo,  aeropuertos.get(origen),  aeropuertos.get(destino),  fecha, cantAsientos,  tripulantes, precios);
	 	vuelosPublicosInternacionales.put(codigo, nuevoVueloPubInternacional);
	 	vuelos.put(codigo, nuevoVueloPubInternacional);
		return codigo;
	}
	public String VenderVueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio,  int dniComprador, int[] acompaniantes) {
		Privado tempPrivado = new Privado(dniComprador, acompaniantes, tripulantes, precio, 0, "", aeropuertos.get(origen), aeropuertos.get(destino), fecha);
		 boolean Posterior = Vuelo.esFechaPosterior(fecha);
	        if (!Posterior) {
	        	throw new RuntimeException("la fecha no es valida.");
	        } 
		int cantidadDeVuelosPrivados= vuelos.size();
	 	String codigoPriv = tempPrivado.generarCodigoVuelo(cantidadDeVuelosPrivados);
	 	int jets= tempPrivado.calcularJetsNecesarios(acompaniantes);
	 	double precioTotal = tempPrivado.calcularPrecioFinal(jets);
	 	Privado nuevoVueloPrivado= new Privado( dniComprador ,  acompaniantes,  tripulantes,  precioTotal,  jets,  codigoPriv,  aeropuertos.get(origen),  aeropuertos.get(destino),  fecha );
	 	vuelosPrivados.put(codigoPriv, nuevoVueloPrivado);
	 	vuelos.put(codigoPriv, nuevoVueloPrivado);
	 	return codigoPriv;
	} 
	public int venderPasaje(int dniCliente, String codVuelo, int nroAsiento, boolean aOcupar) {
	    // Verificar si el cliente está registrado
	    if (!clientes.containsKey(dniCliente)) {
	        throw new RuntimeException("El cliente no está registrado.");
	    }

	    // Buscar el vuelo en el mapa de vuelos públicos nacionales y internacionales
	    Publico vuelo = (Publico) vuelos.get(codVuelo);
	    if (vuelo == null) {
	        throw new RuntimeException("Vuelo no encontrado.");
	    }
	 

	    // Verificar si el asiento está disponible
	    if (vuelo.pasajes.containsKey(nroAsiento)) {
	        throw new RuntimeException("El asiento ya está ocupado.");
	    }
	    //seccion del vuelo
	    
	    // Registrar el pasaje
	    int codigoPasaje=pasajes.size()+1;
	    Pasaje nuevoPasaje = new Pasaje(nroAsiento, vuelo, clientes.get(dniCliente));
	    vuelo.pasajes.put(nroAsiento, nuevoPasaje);

	    // Generar y devolver un código único de pasaje (puede ser el número del asiento o un contador)
	    return codigoPasaje;
	}
	public Map<Integer, String> asientosDisponibles(String codVuelo) {
	    // Buscar el vuelo en el mapa de vuelos públicos nacionales
	    Publico vuelo = (Publico) vuelos.get(codVuelo);
	    if (vuelo == null) {
	        throw new RuntimeException("Vuelo no encontrado: " + codVuelo);
	    }

	    Map<Integer, String> asientosDisponibles = new HashMap<>();
	    int asientoNumero = 1;

	    // Añadir asientos de clase Turista
	    for (int i = 0; i < vuelo.cantidadAsientos[0]; i++) {
	        asientosDisponibles.put(asientoNumero++, "Turista");
	    }

	    // Añadir asientos de clase Ejecutiva
	    for (int i = 0; i < vuelo.cantidadAsientos[1]; i++) {
	        asientosDisponibles.put(asientoNumero++, "Ejecutiva");
	    }

	    return asientosDisponibles;
	}
	public List<String> consultarVuelosSimilares(String origen, String destino, String fecha) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    LocalDate fechaConsulta = LocalDate.parse(fecha, formatter);
	    LocalDate fechaMaxima = fechaConsulta.plusDays(7);

	    List<String> vuelosSimilares = new ArrayList<>();

	    // Recorrer todos los vuelos 
	    for (Vuelo vuelo : vuelos.values()) {
	        LocalDate fechaVuelo = LocalDate.parse(vuelo.getFecha(), formatter);

	        // Verificar que el origen y destino coinciden, y que la fecha está dentro del rango
	        if (vuelo.getAeropuertoSalida().getNombre().equals(origen) &&
	            vuelo.getAeropuertoDestino().getNombre().equals(destino) &&
	            (fechaVuelo.isEqual(fechaConsulta) || (fechaVuelo.isAfter(fechaConsulta) && fechaVuelo.isBefore(fechaMaxima)))) {

	            vuelosSimilares.add(vuelo.getIdentificacion());
	        }
	    }

	    return vuelosSimilares;
	}
	public List<String> cancelarVuelo(String codVuelo) {
	    List<String> registros = new ArrayList<>();

	    // Obtener el vuelo a cancelar
	    Publico vueloACancelar = vuelosPublicosNacional.get(codVuelo);
	    if (vueloACancelar == null) {
	        throw new RuntimeException("Vuelo no encontrado para cancelar: " + codVuelo);
	    }

	    // Encontrar un vuelo alternativo similar (misma ruta y fecha posterior)
	    Publico vueloAlternativo = null;
	    LocalDate fechaVueloACancelar = LocalDate.parse(vueloACancelar.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

	    for (Publico vuelo : vuelosPublicosNacional.values()) {
	        if (!vuelo.getIdentificacion().equals(codVuelo) &&
	            vuelo.getAeropuertoSalida().getNombre().equals(vueloACancelar.getAeropuertoSalida().getNombre()) &&
	            vuelo.getAeropuertoDestino().getNombre().equals(vueloACancelar.getAeropuertoDestino().getNombre())) {
	            
	            LocalDate fechaVueloAlternativo = LocalDate.parse(vuelo.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	            if (fechaVueloAlternativo.isAfter(fechaVueloACancelar)) {
	                vueloAlternativo = vuelo;
	                break;
	            }
	        }
	    }

	    if (vueloAlternativo == null) {
	        throw new RuntimeException("No se encontró un vuelo alternativo para reubicar a los pasajeros.");
	    }

	    // Reubicar pasajeros en el vuelo alternativo
	    for (Pasaje pasaje : vueloACancelar.pasajes.values()) {
	        Cliente cliente = pasaje.getCliente();
	        int asientoNuevo = buscarAsientoDisponible(vueloAlternativo, pasaje.getSeccionASiento());
	        
	        if (asientoNuevo != -1) {
	            // Registrar el nuevo pasaje para el cliente en el vuelo alternativo
	            Pasaje nuevoPasaje = new Pasaje(asientoNuevo, vueloAlternativo, cliente);
	            vueloAlternativo.pasajes.put(asientoNuevo, nuevoPasaje);

	            // Generar el registro de reubicación
	            String registro = String.format("%d - %s - %s - %s", cliente.getDni(), cliente.getNombre(), cliente.getTelefono(), vueloAlternativo.getIdentificacion());
	            registros.add(registro);
	        }
	    }

	    // Eliminar el vuelo original después de reubicar a los pasajeros
	    vuelosPublicosNacional.remove(codVuelo);

	    return registros;
	}
	
	private int buscarAsientoDisponible(Publico vuelo, int seccionOriginal) {
	    // Asumimos que clase Turista es 0 y Ejecutiva es 1 (con Ejecutiva siendo mejor)
	    for (int seccion = seccionOriginal; seccion < vuelo.cantidadAsientos.length; seccion++) {
	        for (int asiento = 1; asiento <= vuelo.cantidadAsientos[seccion]; asiento++) {
	            if (!vuelo.pasajes.containsKey(asiento)) {
	                return asiento;
	            }
	        }
	    }
	    return -1; // No se encontró asiento disponible
	}

	public double totalRecaudado(String destino) {
	    double totalRecaudacion = 0.0;

	    // Recaudación de vuelos privados
	    for (Privado vuelo : vuelosPrivados.values()) {
	        if (vuelo.getAeropuertoDestino().getNombre().equals(destino)) {
	            double precioPorJet = vuelo.getPrecio();
	            int cantidadJets = vuelo.getCantidadJets();
	            double recaudacionVuelo = cantidadJets * precioPorJet;
	            recaudacionVuelo += recaudacionVuelo * 0.30; // Aplicar 30% de impuestos
	            totalRecaudacion += recaudacionVuelo;
	        }
	    }

	    // Recaudación de vuelos internacionales
	    for (Internacional vuelo : vuelosPublicosInternacionales.values()) {
	        if (vuelo.getAeropuertoDestino().getNombre().equals(destino)) {
	            double recaudacionVuelo = 0.0;

	            // Sumar el costo de los asientos según la sección
	            for (int i = 0; i < vuelo.cantidadAsientos.length; i++) {
	                recaudacionVuelo += vuelo.cantidadAsientos[i] * vuelo.precio[i];
	            }

	            // Sumar el costo de los refrigerios para todos los pasajeros
	            int totalPasajeros = Arrays.stream(vuelo.cantidadAsientos).sum();
	            recaudacionVuelo += totalPasajeros * vuelo.getCantidadRefrigerios() * vuelo.getValorRefrigerio();

	            // Aplicar 20% de impuestos
	            recaudacionVuelo += recaudacionVuelo * 0.20;
	            totalRecaudacion += recaudacionVuelo;
	        }
	    }

	    return totalRecaudacion;
	}

	
	public String detalleDeVuelo(String codVuelo) {
	    StringBuilder detalle = new StringBuilder();
	    Publico vueloNacional = vuelosPublicosNacional.get(codVuelo);
	    if (vueloNacional != null) {
	        detalle.append(codVuelo).append(" - ")
	               .append(vueloNacional.getAeropuertoSalida().getNombre()).append(" - ")
	               .append(vueloNacional.getAeropuertoDestino().getNombre()).append(" - ")
	               .append(vueloNacional.getFecha()).append(" - NACIONAL");
	        return detalle.toString();
	    }
	    Publico vueloInternacional = vuelosPublicosInternacionales.get(codVuelo);
	    if (vueloInternacional != null) {
	        detalle.append(codVuelo).append(" - ").append(vueloInternacional.getAeropuertoSalida().getNombre()).append(" - ")
	       .append(vueloInternacional.getAeropuertoDestino().getNombre()).append(" - ")
	       	.append(vueloInternacional.getFecha()).append(" - INTERNACIONAL");
	        return detalle.toString();
	    }
	    Privado vueloPrivado = vuelosPrivados.get(codVuelo);
	    if (vueloPrivado != null) {
	        detalle.append(codVuelo).append(" - ")
	               .append(vueloPrivado.getAeropuertoSalida().getNombre()).append(" - ")
	               .append(vueloPrivado.getAeropuertoDestino().getNombre()).append(" - ")
	               .append(vueloPrivado.getFecha()).append(" - PRIVADO (")
	               .append(vueloPrivado.getCantidadJets()).append(")");
	        return detalle.toString();
	    }
	    throw new RuntimeException("Vuelo no encontrado para el código proporcionado: " + codVuelo);
	}

	
	public String toString() { 
		return "Aerolínea: " + this.nombreAerolinea + "\nCUIT: " + cuit; 
		}
	;

	    // Método para cancelar un pasaje
	    public void cancelarPasaje(int dni, String codVuelo, int nroAsiento) {
	        Cliente cliente = clientes.get(dni);
	        if (cliente == null) {
	            throw new RuntimeException("El cliente no está registrado.");
	        }

	        Vuelo v = vuelos.get(codVuelo);
	        if (v == null) {
	            throw new RuntimeException("El vuelo no existe.");
	        }
	            pasajes.remove(nroAsiento);
	    }

		@Override
		public void cancelarPasaje(int dni, int codPasaje) {
			// TODO Auto-generated method stub
			
		}
	
}//end



	 
