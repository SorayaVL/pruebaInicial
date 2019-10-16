package pruebaInicial.Partidos.logica;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pruebaInicial.Partidos.models.Partidos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Logica implements Serializable {
    private static final long serialVersionUID = -2134457787282706088L;
    private static Logica INSTANCE = null;

    private ObservableList<Partidos> oListaPartidos = FXCollections.observableArrayList();
    private List<Partidos> partidosList = new ArrayList<Partidos>();


    public void setoListaPartidos(ObservableList<Partidos> oListaPartidos) {
        this.oListaPartidos = oListaPartidos;
    }

    private Logica() {
    }

    public static Logica getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public static void setINSTANCE(Logica INSTANCE) {
        Logica.INSTANCE = INSTANCE;
    }

    public ObservableList<Partidos> getoListaPartidos() {
        return oListaPartidos;
    }


    public void aniadirPartidos(Partidos partido) {
        oListaPartidos.add(partido);

    }

    public void verListaPartidos() {
        for (Partidos p : oListaPartidos) {
            System.out.println(p);
        }

    }

    public void borrarPartido(int borrar) {

        oListaPartidos.remove(borrar);
      /*  Iterator<Partidos> it = listaPartidos.iterator();
        while (it.hasNext()){
            int id = it.next().getIdPartido();
            if (borrar== id)
                it.remove();*/
    }

    public void modificarPartido(Partidos partidoM, int indice) {
        oListaPartidos.set(indice, partidoM);

    }

    public void guardarFichero() {
        partidosList=new ArrayList<Partidos>(oListaPartidos);

        try {
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream("./partidos.txt"));
            fichero.writeObject(partidosList);
            fichero.close();
        } catch (IOException e) {

        }

    }

    public void abreFichero() {
        try {
            ObjectInputStream cargaFichero = new ObjectInputStream(new FileInputStream("./partidos.txt"));
             partidosList = (ArrayList) cargaFichero.readObject();
            cargaFichero.close();
            for (Partidos p : partidosList){
                aniadirPartidos(p);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}



