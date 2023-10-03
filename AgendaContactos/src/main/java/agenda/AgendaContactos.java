package agenda;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class AgendaContactos {
	private static final String ELIMINADO = "eliminado>000000000-000000000-000000";
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int index = 2;
		
		try {
			RandomAccessFile fichero = new RandomAccessFile("agenda.csv", "rw");

			while (index != 1)   {
				System.out.println("1.Salir");
                System.out.println("2.Buscar por código");
                System.out.println("3.Buscar por el comienzo del nombre");
                System.out.println("4.Mostrar agenda completa");
                System.out.println("5.Añadir un contacto");
                System.out.println("6.Borrar un contacto");
				index = sc.nextInt();
				sc.nextLine();
				
				switch (index) {
				case 1:
					return;
				case 2: 
					System.out.println(buscarPorCodigo(fichero,sc));
					break;
				case 3:
					System.out.println(buscarPorNombre(fichero,sc));
					break;
				case 4:
					System.out.println(mostrarAgendaCompleta(fichero));
					break;
				case 5:
					System.out.println(agregarContacto(fichero, sc));
					break;
				case 6:
					System.out.println(eliminarContacto(fichero,sc));
					break;
				}
			}
			
			fichero.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}
	
	public static String buscarPorCodigo(RandomAccessFile fichero, Scanner sc) throws IOException {
		if (fichero.length() > 0) {
			System.out.println("Dime código");
			String cod = sc.nextLine();
			String linea;
			fichero.seek(0); // nos situamos al principio
			while ((linea = fichero.readLine()) != null) {
				String[] campos = linea.split(",");
				if (campos[0].equals(cod)) {
					return linea;
				}

			}
			return "no hay ningun contacto con ese código";
		} else {
			return "no hay contactos";
		}
		
	}
	
	public static String buscarPorNombre(RandomAccessFile fichero, Scanner sc) throws IOException {
		if (fichero.length() > 0) {
			System.out.println("Nombre por el que quieres buscar");
			String nombre = sc.nextLine();
			String results = "";
			String linea;
			fichero.seek(0); // nos situamos al principio
			
			while ((linea = fichero.readLine()) != null) {
				String[] campos = linea.split(",");
				if (campos[1].startsWith(nombre) && !campos[0].equals(ELIMINADO)) {
					results += linea + "\n";
				}
			}

			if (results == "") {
				return "no hay ningun contacto con ese nombre";
			} else {
				return results;
			}
		} else {
			return "no hay contactos";
		}
	}
	
	public static String mostrarAgendaCompleta(RandomAccessFile fichero) throws IOException {
		if (fichero.length() > 0) {
			String linea;
			String contenidoCSV = "";
			fichero.seek(0); // nos situamos al principio
			while ((linea = fichero.readLine()) != null) {
				contenidoCSV += linea + "\n";
			}
			return contenidoCSV;
		} else {
			return "no hay contactos";
		}

	}
	
	public static String agregarContacto(RandomAccessFile fichero, Scanner sc) throws IOException {
		System.out.println("Cual es el nombre de tu alumno?");
		String nombre = sc.nextLine();
		System.out.println("Cual es la edad de tu alumno?");
		String edad = sc.nextLine();
		System.out.println("Cual es la teléfono de tu alumno?");
		String telefono = sc.nextLine();
		
		Contacto c1 = new Contacto(nombre, edad, telefono);
		fichero.seek(fichero.length());
		fichero.writeBytes(c1.toCSV()+ "\n");
		return "Contacto añadido: " + c1.toCSV();
	}
	
	public static String eliminarContacto(RandomAccessFile fichero, Scanner sc) throws IOException {
		if (fichero.length() > 0) {
			System.out.println("Código del alumno que quieres eliminar?");
			String codigo = sc.nextLine();
			String linea;
			fichero.seek(0);
			while ((linea = fichero.readLine()) != null) {
				String[] campos = linea.split(",");
				if (campos[0].equals(codigo)) {
					// Reemplaza el UUID con "eliminado>000000000-000000000-000000"
					campos[0] = ELIMINADO;
					linea = String.join(",", campos);
					fichero.seek(fichero.getFilePointer() - linea.length() - 1); // Retrocede al inicio de la línea
					fichero.writeBytes(linea); // Escribe la línea modificada
					return "Contacto eliminado";
				}
			}

			return "No se encontró ninguna persona con ese nombre.";
		} else {
			return "no hay contactos";
		}
	}

}
