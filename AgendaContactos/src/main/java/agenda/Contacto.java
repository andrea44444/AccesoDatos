package agenda;

import java.util.UUID;

public class Contacto {
	
	private String nombre;
    private String edad;
    private String telefono;
    private UUID id;
    
	public Contacto(String nombre, String edad, String telefono) {
		this.nombre = nombre;
		this.edad = edad;
		this.telefono = telefono;
		this.id = UUID.randomUUID();
	}

	public UUID getId() {
        return id;
    }
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String toCSV() {
        return id+","+nombre + "," + edad + "," + telefono;
    }
}
