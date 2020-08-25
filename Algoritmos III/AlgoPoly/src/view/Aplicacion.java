package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.juego.Juego;
import view.ContenedorBienvenidos;
import view.ContenedorPrincipal;


public class Aplicacion extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {

        stage.setTitle("Algopoly");
        
        
        
        Juego juego = new Juego();
        ContenedorPrincipal contenedorPrincipal = new ContenedorPrincipal( stage, juego );
        Scene escenaJuego = new Scene(contenedorPrincipal,1350, 650);


        ContenedorBienvenidos contenedorBienvenidos = new ContenedorBienvenidos(stage, escenaJuego);
        Scene escenaBienvenidos = new Scene(contenedorBienvenidos,1350, 650);

        stage.setScene(escenaBienvenidos);

		stage.show();

    }

}
