package bondiJet;

public class Cliente {
	private int dni;
	private String nombre;
	private String telefeno;
	
	public Cliente(int dni, String nombre, String telefono) {
		this.dni= dni;
		this.nombre= nombre;
		this.telefeno= telefono;
	}
	
	public int getDni() {
		return this.dni;
	}
	

	    public String getNombre() {
	        return this.nombre;
	    }

	    public String getTelefono() {
	        return this.telefeno;
	    }

	   
	    public String verDatos() {
	        StringBuilder datos = new StringBuilder();
	        datos.append("Cliente:\n");
	        datos.append("DNI: ").append(dni).append("\n");
	        datos.append("Nombre: ").append(nombre).append("\n");
	        datos.append("Teléfono: ").append(this.telefeno).append("\n");
	        return datos.toString();
	    }
	}


