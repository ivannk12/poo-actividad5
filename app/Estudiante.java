// Clase Estudiante: guarda los datos de un estudiante (id, nombre, programa y edad).
// Tambien pasa el objeto a texto para el archivo y lo vuelve a armar desde el archivo.
public class Estudiante {

    int id;
    String nombre;
    String programa;
    int edad;

    Estudiante(int id, String nombre, String programa, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.programa = programa;
        this.edad = edad;
    }

    // Pasa el estudiante a una linea separada por comas para guardarlo en el archivo
    String aLinea() {
        return id + "," + nombre + "," + programa + "," + edad;
    }

    // Arma un estudiante a partir de una linea leida del archivo
    static Estudiante desdeLinea(String linea) {
        String[] datos = linea.split(",");
        int id = Integer.parseInt(datos[0]);
        String nombre = datos[1];
        String programa = datos[2];
        int edad = Integer.parseInt(datos[3]);
        return new Estudiante(id, nombre, programa, edad);
    }
}
