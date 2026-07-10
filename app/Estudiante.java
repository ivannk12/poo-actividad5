/**
 * Esta clase denominada Estudiante modela un estudiante con un identificador, un
 * nombre, un programa academico y una edad. Ofrece metodos para convertir el
 * objeto a una linea de texto y para reconstruirlo desde una linea, lo que
 * permite almacenarlo y leerlo de un archivo.
 * @version 1.0/2026
 */
public class Estudiante {

    int id;          // Identificador unico del estudiante
    String nombre;   // Nombre del estudiante
    String programa; // Programa academico del estudiante
    int edad;        // Edad del estudiante

    /**
     * Constructor de la clase Estudiante
     * @param id Identificador del estudiante
     * @param nombre Nombre del estudiante
     * @param programa Programa academico del estudiante
     * @param edad Edad del estudiante
     */
    Estudiante(int id, String nombre, String programa, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.programa = programa;
        this.edad = edad;
    }

    /**
     * Convierte el estudiante a una linea de texto separada por comas para
     * guardarlo en el archivo.
     * @return Linea de texto con los datos del estudiante
     */
    String aLinea() {
        return id + "," + nombre + "," + programa + "," + edad;
    }

    /**
     * Crea un estudiante a partir de una linea de texto leida del archivo.
     * @param linea Linea de texto con los datos separados por comas
     * @return Objeto Estudiante reconstruido
     */
    static Estudiante desdeLinea(String linea) {
        String[] datos = linea.split(",");
        int id = Integer.parseInt(datos[0]);
        String nombre = datos[1];
        String programa = datos[2];
        int edad = Integer.parseInt(datos[3]);
        return new Estudiante(id, nombre, programa, edad);
    }
}
