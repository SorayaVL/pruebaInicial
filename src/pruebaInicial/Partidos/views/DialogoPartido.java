package pruebaInicial.Partidos.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pruebaInicial.Partidos.logica.Logica;
import pruebaInicial.Partidos.models.Partidos;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DialogoPartido extends Stage {

    private TextField tfEquipoLocal;
    private TextField tfEquipoVisitante;
    private String division;
    private TextField tfResultado;
    private Button btnAceptar;
    private ComboBox cbDivision;
    private DatePicker dpFecha;
    private String resultado;
    private String equipoLocal;
    private String equipoVisitante;
    private LocalDate fechaPartido;



    public DialogoPartido(){
        inicializaVista();
        btnAceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                addPartido();
            }
        });

    }

    public DialogoPartido(Partidos partidoSeleccionado, int indiceModificar) {

        inicializaVista();
        tfEquipoLocal.setText(partidoSeleccionado.getEquipoLocal());
        tfEquipoVisitante.setText(partidoSeleccionado.getEquipoVisitante());
        cbDivision.getSelectionModel().select(partidoSeleccionado.getDivision());
        tfResultado.setText(partidoSeleccionado.getResultado());
        dpFecha.setValue(partidoSeleccionado.getFechaPartido());
        btnAceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (datosCorrectos()){
                    Partidos partidoModificado = new Partidos(tfEquipoLocal.getText(), tfEquipoVisitante.getText(), division, resultado, dpFecha.getValue());
                    Logica.getINSTANCE().modificarPartido(partidoModificado,indiceModificar);
                    close();
                }

            }
        });


    }


    private void inicializaVista() {

        initModality(Modality.APPLICATION_MODAL);
        setTitle("Alta Partido");
        // Creación de la observable list para los valores del combobox división
        ObservableList<String> listadivision = FXCollections.observableArrayList();
        listadivision.add("Primera");
        listadivision.add("Segunda");
        listadivision.add("Tercera");

        Label lLocal = new Label("Equipo Local: ");
        tfEquipoLocal = new TextField();
        Label eVisitante = new Label("Equipo Visitante: ");
        tfEquipoVisitante = new TextField();
        Label lDivision = new Label("Division: ");
        cbDivision = new ComboBox<String>(listadivision);
        Label lresultado = new Label("Resultado");
        tfResultado = new TextField();
        Label lFechaPartido = new Label("Fecha Partido: ");
        dpFecha = new DatePicker();
        btnAceptar = new Button("Aceptar");
        ImageView imagenRugby = new ImageView(getClass().getResource("res/rugby.png").toExternalForm());
        imagenRugby.setPreserveRatio(true);
        imagenRugby.setFitHeight(50);


        GridPane gridPane = new GridPane();
        gridPane.add(lDivision, 0, 0, 1, 1);
        gridPane.add(cbDivision, 1,0, 1, 1);
        gridPane.add(lFechaPartido, 2, 0,2 ,1);
        gridPane.add(dpFecha, 3,0,1,1);
        gridPane.add(lLocal, 0, 1 , 1 , 1);
        gridPane.add(tfEquipoLocal,1 , 1 , 1,1);
        gridPane.add(eVisitante, 2, 1, 1, 1);
        gridPane.add(tfEquipoVisitante, 3, 1, 1, 1);
        gridPane.add(lresultado, 0, 2, 1, 1);
        gridPane.add(tfResultado, 1,2,1,1);
        gridPane.add(imagenRugby,2, 2, 1, 1 );
        gridPane.setVgap(10);
        gridPane.setHgap(10);



        Scene scene = new Scene( new VBox(gridPane, btnAceptar),600, 200);
        setScene(scene);

    }


        private void addPartido(){

            if (datosCorrectos()) {
                Partidos partido = new Partidos(tfEquipoLocal.getText(), tfEquipoVisitante.getText(), division, resultado, dpFecha.getValue());
                Logica.getINSTANCE().aniadirPartidos(partido);
                close();
            }

        }

        public boolean datosCorrectos(){
        boolean datosCorrectos = false;

        if (tfEquipoLocal.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No se puede añadir el partido");
            alert.setHeaderText("Todos los campos son necesarios");
            alert.setContentText("Por favor, rellene el Equipo Local");
            alert.show();
        }

        else equipoLocal = tfEquipoLocal.getText();

        if (tfEquipoVisitante.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No se puede añadir el partido");
            alert.setHeaderText("Todos los campos son necesarios");
            alert.setContentText("Por favor, rellene el campo Equipo Visitante");
            alert.show();
        }
            else equipoVisitante = tfEquipoVisitante.getText();

        if (cbDivision.getSelectionModel().getSelectedItem()!=null) division = cbDivision.getSelectionModel().getSelectedItem().toString();
            else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No se puede añadir el partido");
            alert.setHeaderText("Todos los campos son necesarios");
            alert.setContentText("Por favor, rellene la división");
            alert.show();
        };

        if (dpFecha.getValue()!=null) fechaPartido=dpFecha.getValue();
            else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No se puede añadir el partido");
            alert.setHeaderText("Todos los campos son necesarios");
            alert.setContentText("Por favor, rellene la fecha del partido");
            alert.show();
        };

        comprobarResultado();

            datosCorrectos = equipoLocal != null && equipoVisitante != null && division != null && resultado != null && fechaPartido != null;

        return datosCorrectos;
        }

        private void comprobarResultado(){
            if (tfResultado.getText()!=null){
                String regex = "[0-9]\\-[0-9]";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(tfResultado.getText());
                boolean correcto = matcher.find();

                if (correcto){
                    resultado=tfResultado.getText();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("No se puede añadir el partido");
                    alert.setHeaderText("Formato incorrecto");
                    alert.setContentText("Por favor, rellene el resultado siguiendo el patron: num-num");
                    alert.show();
                }
            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No se puede añadir el partido");
                alert.setHeaderText("Todos los campos son necesarios");
                alert.setContentText("Por favor, rellene el resultado siguiendo el patron: num-num");
                alert.show();
            }


    }

}

