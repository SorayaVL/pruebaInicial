package pruebaInicial.Partidos.views;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import pruebaInicial.Partidos.logica.Logica;
import javafx.stage.Stage;
import pruebaInicial.Partidos.models.Partidos;

public class VentanaPrincipal extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Partidos de Rugby");
        Logica.getINSTANCE().abreFichero();
        TableView tablaPartidos = new TableView(Logica.getINSTANCE().getoListaPartidos());
        tablaPartidos.setItems(Logica.getINSTANCE().getoListaPartidos());
        AnchorPane.setTopAnchor(tablaPartidos, 15d);
        AnchorPane.setBottomAnchor(tablaPartidos, 75d);
        AnchorPane.setLeftAnchor(tablaPartidos, 15d);
        AnchorPane.setRightAnchor(tablaPartidos, 15d);


        TableColumn <String, Partidos> column1 = new TableColumn<>("Equipo Local");
        column1.setCellValueFactory(new PropertyValueFactory<>("equipoLocal"));

        TableColumn <String, Partidos> column2 = new TableColumn<>("Equipo Visitante");
        column2.setCellValueFactory(new PropertyValueFactory<>("equipoVisitante"));

        TableColumn <String, Partidos> column3 = new TableColumn<>("Division");
        column3.setCellValueFactory(new PropertyValueFactory<>("division"));

        TableColumn <String, Partidos> column4 = new TableColumn<>("Resultado");
        column4.setCellValueFactory(new PropertyValueFactory<>("resultado"));

        TableColumn <String, Partidos> column5 = new TableColumn<>("Fecha");
        column5.setCellValueFactory(new PropertyValueFactory<>("fechaPartidoFormateado"));

        tablaPartidos.getColumns().addAll(column1, column2, column3, column4, column5);

        Button btnAlta = new Button("Alta");
        btnAlta.setPrefSize(70d,20d);
        AnchorPane.setBottomAnchor(btnAlta, 15d);
        AnchorPane.setLeftAnchor(btnAlta, 15d);

        Button btnBorrar = new Button("Borrar");
        btnBorrar.setPrefSize(70d,20d);
        AnchorPane.setBottomAnchor(btnBorrar, 15d);
        AnchorPane.setLeftAnchor(btnBorrar, 175d);

        Button btnModificar = new Button("Modificar");
        btnModificar.setPrefSize(70d,20d);
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
                if (indiceBorrar>=0)
                    Logica.getINSTANCE().borrarPartido(indiceBorrar);
            }
        });

       btnModificar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int indiceModificar = tablaPartidos.getSelectionModel().getSelectedIndex();
                if (indiceModificar>=0){
                    Partidos partidoSeleccionado = Logica.getINSTANCE().getoListaPartidos().get(indiceModificar);
                    DialogoPartido dialogoPartido = new DialogoPartido(partidoSeleccionado, indiceModificar);
                    dialogoPartido.show();
                }
                else System.out.println("Por favor, seleccione un partido de la tabla para modificarlo");

            }
        });

       btnSalir.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               Logica.getINSTANCE().guardarFichero();
               stage.close();
           }
       });



        AnchorPane panelAnclado = new AnchorPane(tablaPartidos, btnAlta, btnBorrar, btnModificar, btnSalir);
        Scene scene = new Scene(panelAnclado, 500, 500);
        stage.setScene(scene);
        stage.show();


          }


}
