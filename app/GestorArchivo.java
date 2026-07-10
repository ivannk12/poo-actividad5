import java.io.*;
import java.util.ArrayList;

// Clase que maneja el archivo de estudiantes con las operaciones CRUD.
// Para escribir usa FileWriter y PrintWriter, y para leer FileReader con BufferedReader.
public class GestorArchivo {

    String nombreArchivo;

    GestorArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    // CREATE: agrega un estudiante al final del archivo
    void crear(Estudiante estudiante) throws IOException {
        FileWriter archivo = new FileWriter(nombreArchivo, true); // true = agrega al final
        PrintWriter impresor = new PrintWriter(archivo);
        impresor.println(estudiante.aLinea());
        impresor.close();
    }

    // READ: lee todos los estudiantes del archivo y los devuelve en una lista
    ArrayList<Estudiante> leer() throws IOException {
        ArrayList<Estudiante> lista = new ArrayList<Estudiante>();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            return lista; // si todavia no existe el archivo, la lista queda vacia
        }
        BufferedReader filtro = new BufferedReader(new FileReader(nombreArchivo));
        String linea = filtro.readLine();
        while (linea != null) {
            if (!linea.trim().equals("")) {
                lista.add(Estudiante.desdeLinea(linea));
            }
            linea = filtro.readLine();
        }
        filtro.close();
        return lista;
    }

    // UPDATE: busca por id, cambia los datos y reescribe el archivo
    boolean actualizar(Estudiante estudiante) throws IOException {
        ArrayList<Estudiante> lista = leer();
        boolean encontrado = false;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).id == estudiante.id) {
                lista.set(i, estudiante);
                encontrado = true;
            }
        }
        if (encontrado) {
            escribirTodos(lista);
        }
        return encontrado;
    }

    // DELETE: quita el estudiante con ese id y reescribe el archivo
    boolean eliminar(int id) throws IOException {
        ArrayList<Estudiante> lista = leer();
        ArrayList<Estudiante> nueva = new ArrayList<Estudiante>();
        boolean encontrado = false;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).id == id) {
                encontrado = true; // este no se copia a la nueva lista
            } else {
                nueva.add(lista.get(i));
            }
        }
        if (encontrado) {
            escribirTodos(nueva);
        }
        return encontrado;
    }

    // Reescribe todo el archivo con la lista que se le pase
    void escribirTodos(ArrayList<Estudiante> lista) throws IOException {
        FileWriter archivo = new FileWriter(nombreArchivo, false); // false = sobrescribe
        PrintWriter impresor = new PrintWriter(archivo);
        for (int i = 0; i < lista.size(); i++) {
            impresor.println(lista.get(i).aLinea());
        }
        impresor.close();
    }
}
