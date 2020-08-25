package view;

import java.util.Hashtable;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.command.Comando;
import model.command.ComprarPropiedadComando;
import model.command.EdificarComando;
import model.command.PagarFianzaComando;
import model.command.TerminarTurnoComando;
import model.command.TirarDadosYAvanzarComando;
import model.command.VenderPropiedadComando;
import model.juego.Juego;
import model.juego.Jugador;
import view.VistaTablero;
import view.eventos.Alerta;
import view.eventos.Boton2JugadoresEventHandler;
import view.eventos.Boton3JugadoresEventHandler;
import view.eventos.BotonComprarPropiedadEventHandler;
import view.eventos.BotonEdificarEventHandler;
import view.eventos.BotonPagarFianzaEventHandler;
import view.eventos.BotonTerminarTurnoEventHandler;
import view.eventos.BotonTirarDadosEventHandler;
import view.eventos.BotonVenderEventHandler;

public class ContenedorPrincipal extends BorderPane {

	
    VBox botonera;
    VBox panelDineroJugadores;
    VBox botoneraInicio;
    GridPane contenedorTablero;
    VistaTablero vistaTablero;
    VBox panelGanador;
    Juego juego;
    GridPane contenedorBotonesDados;
	private HBox contenedorDados;

    public ContenedorPrincipal(Stage stage, Juego juego) {
    	this.juego = juego;
        this.setBotoneraElegirNumJugadores();
        Image imagen = new Image("file:src/view/images/fondo_principal.jpg");
        BackgroundImage imagenDeFondo = new BackgroundImage(imagen, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.setBackground(new Background(imagenDeFondo));
    }
    
    private void setBotoneraElegirNumJugadores() {
    
        
    	Button botonElegir2Jugadores = new Button();
    	botonElegir2Jugadores.setText("2 Jugadores");
		Boton2JugadoresEventHandler Jugadores2Handler = new Boton2JugadoresEventHandler(this.juego, this);
		botonElegir2Jugadores.setOnAction(Jugadores2Handler);
    	
    	Button botonElegir3Jugadores = new Button();
    	botonElegir3Jugadores.setText("3 Jugadores");
		Boton3JugadoresEventHandler Jugadores3Handler = new Boton3JugadoresEventHandler(this.juego, this);
		botonElegir3Jugadores.setOnAction(Jugadores3Handler);
    	
    	Label etiqueta = new Label();
        etiqueta.setFont(Font.font("Arial Black", FontWeight.BOLD,30));
        etiqueta.setText("Elija cantidad de jugadores");
        etiqueta.setTextFill(Color.web("#66A7C5"));
		
    	
        this.botoneraInicio = new VBox(etiqueta,botonElegir2Jugadores,botonElegir3Jugadores);
        this.botoneraInicio.setSpacing(30);

        this.botoneraInicio.setAlignment(Pos.CENTER);
        
        
        
        this.setCenter(this.botoneraInicio);
        

    }
    
    

    
    private void setDerBotoneraConDados() {
        
    	Jugador jugadorActual = juego.getJugadorActual();
    	Hashtable<String, Comando> accionesPosibles = jugadorActual.getAccionesPosibles();
    	if(accionesPosibles.size() == 0) this.actualizar();
    	
    	boolean puedeComprar = false;
    	String nombreComprar = "";
    	boolean puedeVender = false;
    	boolean puedeEdificar = false;
        for(String nombreAccion : accionesPosibles.keySet()) {
        	if(nombreAccion.startsWith(ComprarPropiedadComando.prefijoAccion)) {
        		puedeComprar = true;
        		nombreComprar = nombreAccion;
        	}
        	else if(nombreAccion.startsWith(VenderPropiedadComando.prefijoAccion)) puedeVender = true;
        	else if(nombreAccion.startsWith(EdificarComando.prefijoAccion)) puedeEdificar = true;
        }
    	
        Button botonTirarDados = new Button();
        botonTirarDados.setText("Tirar Dados");
        if(!accionesPosibles.containsKey(TirarDadosYAvanzarComando.nombreAccion) || !jugadorActual.puedeMoverse())
        	botonTirarDados.setDisable(true);
		BotonTirarDadosEventHandler tirarDadosHandler = new BotonTirarDadosEventHandler(this.juego, this);
		botonTirarDados.setOnAction(tirarDadosHandler);
        
        Button botonTerminarTurno = new Button();
        botonTerminarTurno.setText("Terminar Turno");
        if(!accionesPosibles.containsKey(TerminarTurnoComando.nombreAccion) && jugadorActual.puedeMoverse())
        	botonTerminarTurno.setDisable(true);
		BotonTerminarTurnoEventHandler terminarTurnoHandler = new BotonTerminarTurnoEventHandler(this.juego, this);
		botonTerminarTurno.setOnAction(terminarTurnoHandler);
        
		Button botonComprarPropiedad = new Button();
		botonComprarPropiedad.managedProperty().bind(botonComprarPropiedad.visibleProperty());
        if(!puedeComprar)
        	botonComprarPropiedad.setVisible(false);
        else
        	botonComprarPropiedad.setText(nombreComprar);
        BotonComprarPropiedadEventHandler comprarPropiedadHandler = new BotonComprarPropiedadEventHandler(jugadorActual, nombreComprar,this);
        botonComprarPropiedad.setOnAction(comprarPropiedadHandler);
        
		
		Button botonVenderPropiedad = new Button();
		botonVenderPropiedad.managedProperty().bind(botonVenderPropiedad.visibleProperty());
        botonVenderPropiedad.setText("Vender Propiedad");
        if(!puedeVender)
        	botonVenderPropiedad.setVisible(false);
        BotonVenderEventHandler venderHandler = new BotonVenderEventHandler(this.juego,this);
        botonVenderPropiedad.setOnAction(venderHandler);
        
        
        Button botonEdificar = new Button();
        botonEdificar.managedProperty().bind(botonEdificar.visibleProperty());
        botonEdificar.setText("Edificar");
        if(!puedeEdificar || !accionesPosibles.containsKey(TirarDadosYAvanzarComando.nombreAccion))
        	botonEdificar.setVisible(false);
        BotonEdificarEventHandler edificarHandler = new BotonEdificarEventHandler(this.juego, this);
        botonEdificar.setOnAction(edificarHandler);
        
        Button botonPagarFianza = new Button();
        botonPagarFianza.managedProperty().bind(botonPagarFianza.visibleProperty());
        botonPagarFianza.setText("Pagar Fianza");
        if(!accionesPosibles.containsKey(PagarFianzaComando.nombreAccion))
        	botonPagarFianza.setVisible(false);
        BotonPagarFianzaEventHandler pagarFianzaHandler = new BotonPagarFianzaEventHandler(this.juego,this);
        botonPagarFianza.setOnAction(pagarFianzaHandler);
        
        this.botonera = new VBox(botonTirarDados,botonTerminarTurno,botonComprarPropiedad,botonVenderPropiedad,botonEdificar,botonPagarFianza);
        
        this.botonera.setSpacing(30);
        this.botonera.setMinWidth(350.0);
            
        /*
    	Image imagen = new Image("file:src/view/images/fondo_principal.jpg");
        BackgroundImage imagenDeFondo = new BackgroundImage(imagen, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.botonera.setBackground(new Background(imagenDeFondo));
        */
        
        this.contenedorBotonesDados = new GridPane();
        this.contenedorBotonesDados.setMinWidth(350.0);
        RowConstraints fila1 = new RowConstraints();
        fila1.setPercentHeight(80);
        RowConstraints fila2 = new RowConstraints();
        fila2.setPercentHeight(20);
        contenedorBotonesDados.getRowConstraints().addAll(fila1, fila2);
        contenedorBotonesDados.add(botonera, 0, 0);
        
        this.contenedorDados = new HBox();
        
        this.contenedorDados.setSpacing(30);
        
        /*
    	imagen = new Image("file:src/view/images/fondo_principal.jpg");
        BackgroundImage imagenDeFondoDados = new BackgroundImage(imagen, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.contenedorDados.setBackground(new Background(imagenDeFondo));
        */
        
        GridPane.setValignment(contenedorDados, VPos.valueOf("BOTTOM"));
		this.contenedorBotonesDados.add(contenedorDados, 0, 1);
		
		
        contenedorBotonesDados.setPadding(new Insets(10));
        this.setRight(this.contenedorBotonesDados);
        
    }
    

    private void setIzqTablero() {
    	
		contenedorTablero = new GridPane();
		vistaTablero = new VistaTablero( juego, this, contenedorTablero);
		this.setCenter(contenedorTablero);

    }
    
	public void mostrarDado(int valor) {
		if(valor==0) return;
		Image image = new Image("file:src/view/images/dado1.png");
        switch (valor) {
        
		    case 2 :
		    	image = new Image("file:src/view/images/dado2.png");
		        break;
		        
		    case 3 :
		    	image = new Image("file:src/view/images/dado3.png");
		        break;	
		        
		    case 4 :
		    	image = new Image("file:src/view/images/dado4.png");
		        break;		
		
		    case 5 :
		    	image = new Image("file:src/view/images/dado5.png");
		        break;		
		
		    case 6 :
		    	image = new Image("file:src/view/images/dado6.png");
		        break;		

		}
		
		ImageView dado = new ImageView();
		dado.setImage(image);
		dado.setFitWidth(40);
		dado.setFitHeight(50);
		
		this.contenedorDados.getChildren().add(dado);
	}
    
    

    private void setConsola() {
    	
    	
        String msjEnConsola = "Total Efectivo\n";
		for (Jugador jugador : this.juego.getJugadores()) {
			msjEnConsola+= "Jugador "+jugador.getNombre()+" $ " + String.valueOf(jugador.getEfectivo()) + "\n" ;
		}

		Label etiqueta = new Label();
        etiqueta.setText(msjEnConsola);
        etiqueta.setFont(Font.font("courier new", FontWeight.SEMI_BOLD, 14));
        etiqueta.setTextFill(Color.WHITE);
        
        String msjEnConsolaJugActual = "Jugador actual:  \n\n";
		msjEnConsolaJugActual+= juego.getJugadorActual().getNombre() + "\n" ;

		Label etiquetaJugActual = new Label();
        etiquetaJugActual.setText(msjEnConsolaJugActual);
        etiquetaJugActual.setFont(Font.font("courier new", FontWeight.BOLD, 18));
        etiquetaJugActual.setTextFill(Color.WHITE);
        
        //GridPane.setHalignment(etiquetaJugActual, HPos.valueOf("RIGHT"));
        
        GridPane contenedorConsola = new GridPane();
        ColumnConstraints columna1 = new ColumnConstraints();
        columna1.setPercentWidth(70);
        ColumnConstraints columna2 = new ColumnConstraints();
        columna2.setPercentWidth(20);
        contenedorConsola.getColumnConstraints().addAll(columna1, columna2);
        contenedorConsola.add(etiqueta, 0, 0);
        contenedorConsola.add(etiquetaJugActual, 1, 0);
        
        contenedorConsola.setMaxWidth(this.getWidth());
        
        //contenedorConsola.setPadding(new Insets(40));
        contenedorConsola.setStyle("-fx-background-color: black;");
        this.setBottom(contenedorConsola);
    }
    
    public void inhabilitarBotones() {
        Button botonTirarDados = new Button();
        botonTirarDados.setText("Tirar Dados");
        botonTirarDados.setDisable(true);

        Button botonTerminarTurno = new Button();
        botonTerminarTurno.setText("Terminar Turno");
        botonTerminarTurno.setDisable(true);

        
		Button botonVenderPropiedad = new Button();
        botonVenderPropiedad.setText("Vender Propiedad");
        botonVenderPropiedad.setDisable(true);
        
        Button botonEdificar = new Button();
        botonEdificar.setText("Edificar");
        botonEdificar.setDisable(true);
        
        Button botonPagarFianza = new Button();
        botonPagarFianza.setText("Pagar Fianza");
        botonPagarFianza.setDisable(true);
        
        this.botonera = new VBox(botonTirarDados,botonTerminarTurno,botonVenderPropiedad,botonEdificar,botonPagarFianza);
        
        this.botonera.setSpacing(30);
             
    	Image imagen = new Image("file:src/view/images/fondo_principal.jpg");
        BackgroundImage imagenDeFondo = new BackgroundImage(imagen, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.botonera.setBackground(new Background(imagenDeFondo));
        this.setRight(this.botonera);
    }


    
    public void actualizar() {
    	if(this.juego.estaTerminado()) {
    		this.inhabilitarBotones();
    		return;
    	}
    	this.setDerBotoneraConDados();
    	this.setIzqTablero();
    	this.setConsola();
    }

}
