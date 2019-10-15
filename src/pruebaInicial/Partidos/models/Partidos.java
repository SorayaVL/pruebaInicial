package pruebaInicial.Partidos.models;
/*Vamos a realizar una aplicación que se encargue de gestionar partidos de rugby. La
aplicación no tendrá interfaz gráfico y funcionará en consola. Debes programar y
estructurar la aplicación de la mejor manera que sepas, siempre adaptándose a la
funcionalidad que se indica a continuación.
• De cada partido de rugby se almacenará la siguiente información.
◦ Equipo local (cadena de texto)
◦ Equipo visitante (cadena de texto)
◦ División. A seleccionar entre primera, segunda o tercera.
◦ Resultado (formato número-número, por ejemplo, 23-34).
◦ Fecha (en formato 30-10-2017).
• Se permitirán las siguientes operaciones:
◦ Alta de un nuevo partido
◦ Mostrar un listado de partidos
◦ Borrado de un partido
◦ Mostrar los partidos ordenados. Se permitirá ordenar por fecha y el orden
podrá ser ascendente o descendente según decida el usuario.
◦ Mostrar todos los partidos de una división seleccionada (si se selecciona la
primera división solo se mostrarán los partidos de ésta).
• Persistencia de datos
◦ Al salir de la aplicación los partidos se guardarán en un fichero. Si no existe
se creará.
◦ Al entrar de nuevo en la aplicación se cargarán los partidos que existan en
el fichero predefinido.*/

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Partidos implements Serializable{
    private static final long serialVersionUID = -2134457787282706088L;
    private String equipoLocal;
    private String equipoVisitante;
    private String division;
    private String resultado;
    private LocalDate fechaPartido = LocalDate.now();


    public String getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(String equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public String getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(String equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public LocalDate getFechaPartido() {
        return fechaPartido;
    }

    public String getFechaPartidoFormateado() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd'-'MM'-'yyyy");
        String fechaPartidoFormateado =getFechaPartido().format(f);
        return fechaPartidoFormateado;
    }
    public void setFechaPartido(LocalDate fechaPartido) {

        this.fechaPartido = fechaPartido;
    }


    public Partidos(String equipoLocal, String equipoVisitante, String division, String resultado, LocalDate fechaPartido) {

        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.division = division;
        this.resultado = resultado;
        this.fechaPartido = fechaPartido;


    }

    @Override
    public String toString() {
        return "Partidos{" +
                "equipoLocal='" + equipoLocal + '\'' +
                ", equipoVisitante='" + equipoVisitante + '\'' +
                ", division='" + division + '\'' +
                ", resultado='" + resultado + '\'' +
                ", fechaPartido=" + fechaPartido +
                "" +'}';
    }

}



