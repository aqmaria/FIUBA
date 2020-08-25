package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import view.eventos.BotonEntrarEventHandler;
import view.eventos.OpcionSalirEventHandler;
import view.utilities.ReproductorMusica;

public class ContenedorBienvenidos extends VBox {

    Stage stage;

    public ContenedorBienvenidos(Stage stage, Scene proximaEscena) {

        super();

        this.stage = stage;

        this.setAlignment(Pos.CENTER);
        this.setSpacing(122);
        this.setPadding(new Insets(111));
        
        Image imagen = new Image("file:src/view/images/fondo_principal.jpg");
        BackgroundImage imagenDeFondo = new BackgroundImage(imagen, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.setBackground(new Background(imagenDeFondo));

        Button botonEntrar = new Button();
        botonEntrar.setText("Jugar");
        
        Button botonSalir = new Button();
        botonSalir.setText("Salir");

        Label etiqueta = new Label();
        etiqueta.setFont(Font.font("Arial Black", FontWeight.BOLD,80));

        etiqueta.setText("AlgoPoly");
        etiqueta.setTextFill(Color.web("#66A7C5"));

        BotonEntrarEventHandler botonEntrarHandler = new BotonEntrarEventHandler(stage, proximaEscena);
        botonEntrar.setOnAction(botonEntrarHandler);
        
        
        
        botonSalir.setOnAction(new OpcionSalirEventHandler());
        this.getChildren().addAll(etiqueta, botonEntrar,botonSalir);
        
    }

}
