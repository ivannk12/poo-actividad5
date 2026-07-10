import java.io.*;
import java.util.ArrayList;

/**
 * Esta clase denominada GestorArchivo administra las operaciones CRUD (Create,
 * Read, Update, Delete) de estudiantes sobre un archivo de texto. Utiliza las
 * clases FileWriter y PrintWriter para escribir, y FileReader con BufferedReader
 * para leer.
 * @version 1.0/2026
 */
public class GestorArchivo {

    String nombreArchivo; // Ruta del archivo donde se guardan los estudiantes

    /**
     * Constructor de la clase GestorArchivo
     * @param nombreArchivo Ruta del archivo de texto
     */
    GestorArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * CREATE: agrega un estudiante al final del archivo.
     * @param estudiante Estudiante a agregar
     * @throws IOException Si no se puede escribir en el archivo
     */
    void crear(Estudiante estudiante) throws IOException {
        FileWriter archivo = new FileWriter(nombreArchivo, true); // modo agregar
        PrintWriter impresor = new PrintWriter(archivo);
        impresor.println(estudiante.aLinea());
        impresor.close();
    }

    /**
     * READ: lee todos los estudiantes almacenados en el archivo.
     * @return Lista con los estudiantes leidos
     * @throws IOException Si no se puede leer el archivo
     */
    ArrayList<Estudiante> leer() throws IOException {
        ArrayList<Estudiante> lista = new ArrayList<Estudiante>();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            return lista; // Si el archivo no existe, la lista queda vacia
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

    /**
     * UPDATE: actualiza los datos de un estudiante identificado por su id.
     * @param estudiante Estudiante con los nuevos datos
     * @return true si se encontro y actualizo el estudiante
     * @throws IOException Si no se puede leer o escribir el archivo
     */
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

    /**
     * DELETE: elimina el estudiante identificado por su id.
     * @param id Identificador del estudiante a eliminar
     * @return true si se encontro y elimino el estudiante
     * @throws IOException Si no se puede leer o escribir el archivo
     */
    boolean eliminar(int id) throws IOException {
        ArrayList<Estudiante> lista = leer();
        ArrayList<Estudiante> nueva = new ArrayList<Estudiante>();
        boolean encontrado = false;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).id == id) {
                encontrado = true; // No se agrega a la nueva lista
            } else {
                nueva.add(lista.get(i));
            }
        }
        if (encontrado) {
            escribirTodos(nueva);
        }
        return encontrado;
    }

    /**
     * Reescribe todo el archivo con la lista de estudiantes indicada.
     * @param lista Lista de estudiantes a guardar
     * @throws IOException Si no se puede escribir el archivo
     */
    void escribirTodos(ArrayList<Estudiante> lista) throws IOException {
        FileWriter archivo = new FileWriter(nombreArchivo, false); // sobrescribe
        PrintWriter impresor = new PrintWriter(archivo);
        for (int i = 0; i < lista.size(); i++) {
            impresor.println(lista.get(i).aLinea());
        }
        impresor.close();
    }
}
