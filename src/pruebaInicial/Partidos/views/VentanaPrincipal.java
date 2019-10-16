package pruebaInicial.Partidos.views;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import pruebaInicial.Partidos.logica.Logica;
import javafx.stage.Stage;
import pruebaInicial.Partidos.models.Partidos;

public class VentanaPrincipal extends Application {

    private ComboBox cbDivision;
    ObservableList<String> listadivision = FXCollections.observableArrayList();


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Partidos de Rugby");
        Logica.getINSTANCE().abreFichero();
        TableView tablaPartidos = new TableView(Logica.getINSTANCE().getoListaPartidos());
        tablaPartidos.setItems(Logica.getINSTANCE().getoListaPartidos());
        AnchorPane.setTopAnchor(tablaPartidos, 65d);
        AnchorPane.setBottomAnchor(tablaPartidos, 55d);
        AnchorPane.setLeftAnchor(tablaPartidos, 15d);
        AnchorPane.setRightAnchor(tablaPartidos, 15d);


        TableColumn<String, Partidos> column1 = new TableColumn<>("Equipo Local");
        column1.setCellValueFactory(new PropertyValueFactory<>("equipoLocal"));

        TableColumn<String, Partidos> column2 = new TableColumn<>("Equipo Visitante");
        column2.setCellValueFactory(new PropertyValueFactory<>("equipoVisitante"));

        TableColumn<String, Partidos> column3 = new TableColumn<>("División");
        column3.setCellValueFactory(new PropertyValueFactory<>("division"));

        TableColumn<String, Partidos> column4 = new TableColumn<>("Resultado");
        column4.setCellValueFactory(new PropertyValueFactory<>("resultado"));

        TableColumn<String, Partidos> column5 = new TableColumn<>("Fecha");
        column5.setCellValueFactory(new PropertyValueFactory<>("fechaPartidoFormateado"));

        tablaPartidos.getColumns().addAll(column1, column2, column3, column4, column5);



        Button btnAlta = new Button("Alta");
        btnAlta.setPrefSize(70d, 20d);
        AnchorPane.setBottomAnchor(btnAlta, 15d);
        AnchorPane.setLeftAnchor(btnAlta, 15d);

        Button btnBorrar = new Button("Borrar");
        btnBorrar.setPrefSize(70d, 20d);
        AnchorPane.setBottomAnchor(btnBorrar, 15d);
        AnchorPane.setLeftAnchor(btnBorrar, 175d);

        Button btnModificar = new Button("Modificar");
        btnModificar.setPrefSize(70d, 20d);
        AnchorPane.setBottomAnchor(btnModificar, 15d);
        AnchorPane.setLeftAnchor(btnModificar, 95d);

        Button btnSalir = new Button("Guardar y Salir");
        AnchorPane.setBottomAnchor(btnSalir, 15d);
        AnchorPane.setRightAnchor(btnSalir, 15d);

        btnAlta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DialogoPartido dialogoPartido = new DialogoPartido();
                dialogoPartido.show();
            }
        });

        btnBorrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int indiceBorrar = tablaPartidos.getSelectionModel().getSelectedIndex();
                if (indiceBorrar >= 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmación de borrado");
                    alert.setHeaderText("Se va a eliminar el partido seleccionado");
                    alert.setContentText("¿Seguro que desea eliminar el partido?");
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK)
                        Logica.getINSTANCE().borrarPartido(indiceBorrar);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error al borrar");
                    alert.setHeaderText("Seleccione el partido que desea eliminar");
                    alert.setContentText("No se ha seleccionado ningún partido para eliminar");
                    alert.show();
                }
            }
        });

        btnModificar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int indiceModificar = tablaPartidos.getSelectionModel().getSelectedIndex();
                if (indiceModificar >= 0) {
                    Partidos partidoSeleccionado = Logica.getINSTANCE().getoListaPartidos().get(indiceModificar);
                    DialogoPartido dialogoPartido = new DialogoPartido(partidoSeleccionado, indiceModificar);
                    dialogoPartido.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error al Modificar");
                    alert.setHeaderText("Seleccione el partido que desea modificar");
                    alert.setContentText("No se ha seleccionado ningún partido para modificar");
                    alert.show();
                }

            }
        });

        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Logica.getINSTANCE().guardarFichero();
                stage.close();
            }
        });

        listadivision.add("Todas las divisiones");
        listadivision.add("Primera");
        listadivision.add("Segunda");
        listadivision.add("Tercera");

        cbDivision = new ComboBox<String>(listadivision);
        AnchorPane.setTopAnchor(cbDivision, 15d);
        AnchorPane.setLeftAnchor(cbDivision, 200d);

        Label seleccion = new Label("Seleccione la división a filtrar");
        AnchorPane.setTopAnchor(seleccion, 15d);
        AnchorPane.setLeftAnchor(seleccion, 15d);

        cbDivision.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                FilteredList<Partidos> filteredList = new FilteredList<>(Logica.getINSTANCE().getoListaPartidos(), partidos -> true);
                SortedList<Partidos> sortedData = new SortedList<>(filteredList);
                sortedData.comparatorProperty().bind(tablaPartidos.comparatorProperty());
                tablaPartidos.setItems(sortedData);
                 filteredList.setPredicate(partidos -> {
                     if (cbDivision.getSelectionModel().getSelectedItem().toString().equals("Todas las divisiones")) return  true;
                    String divisionFiltrada = partidos.getDivision();
                    return divisionFiltrada.contains(cbDivision.getSelectionModel().getSelectedItem().toString());
                });

            }
        });


        AnchorPane panelAnclado = new AnchorPane(seleccion,cbDivision, tablaPartidos, btnAlta, btnBorrar, btnModificar, btnSalir);
        Scene scene = new Scene(panelAnclado, 500, 500);
        stage.setScene(scene);
        stage.show();


    }

}
