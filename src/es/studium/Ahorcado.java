package es.studium;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

public class Ahorcado implements ActionListener, WindowListener
{
	Frame ventana = new Frame("Ahorcado");
	Dialog mensaje = new Dialog(ventana, "Fin de la Partida", true);
	Label lblMensaje = new Label("Has ganaddo");
	Button btnFin = new Button("Fin");
	Button btnRein = new Button("Reiniciar");

	String palabras[] =
	{ "AYUNTAMIENTO", "CALEFACCIÓN", "ÁRBOL", "MURCIÉLAGO", "MÉTODO", "LÓGICA", "CANCIÓN", "AVIÓN", "CÉLULA", "CLASE",
			"TELÉFONO", "PÁGINA" };
	String secreta = "";
	String secretaGuiones = "";
	int intentos;

	Random aleatorio = new Random();

	Panel panel1 = new Panel();
	Panel panel2 = new Panel();
	Panel panel3 = new Panel();

	Label lblScrt = new Label("Adivina la palabra secreta:");
	Label lblTries = new Label("Intentos restantes: 10 de 10.");
	Label lblLtra = new Label("Letras probadas:");

	TextField txtScrt = new TextField(15);
	TextField txtLtra = new TextField(2);
	TextField txtTries = new TextField(30);

	Button btnLtra = new Button("Probar con la letra...");

	Font comic = new Font("Serif", Font.BOLD, 12);

	int colorAle1 = aleatorio.nextInt(255) + 1;
	int colorAle2 = aleatorio.nextInt(255) + 1;
	int colorAle3 = aleatorio.nextInt(255) + 1;

	public Ahorcado()
	{

		ventana.setLayout(new FlowLayout());
		mensaje.setLayout(new FlowLayout());
		
		ventana.setFont(comic);
		mensaje.setSize(150, 100);
		ventana.setSize(500, 180);

		btnLtra.addActionListener(this);
		btnFin.addActionListener(this);
		btnRein.addActionListener(this);
		ventana.addWindowListener(this);
		mensaje.addWindowListener(this);
		
		panel1.add(lblScrt);
		txtScrt.setEnabled(false);
		panel1.add(txtScrt);
		
		panel2.add(btnLtra);
		panel2.add(txtLtra);
		panel2.add(lblTries);
		
		panel3.add(lblLtra);
		txtTries.setEnabled(false);
		panel3.add(txtTries);

		ventana.add(panel1);
		ventana.add(panel2);
		ventana.add(panel3);

		mensaje.add(lblMensaje);
		mensaje.add(btnRein);
		mensaje.add(btnFin);
		
		mensaje.setBackground(Color.GREEN);
		mensaje.setLocationRelativeTo(null);
		mensaje.setResizable(false);
		
		ventana.setBackground(new Color(colorAle1, colorAle2, colorAle3));
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		reiniciar();
		ventana.setVisible(true);
	}

	public static void main(String[] args)
	{
		new Ahorcado();
	}

	public void reiniciar()
	{
		intentos = 10;
		secreta = palabras[aleatorio.nextInt(palabras.length)];
		secretaGuiones = "";
		for (int i = 0; i < secreta.length(); i++)
		{
			secretaGuiones = secretaGuiones + "_ ";
		}
		txtScrt.setText(secretaGuiones);
		txtTries.setText("");
		lblTries.setText("Intentos restantes: " + intentos + " de 10.");
		mensaje.setBackground(null);
		lblMensaje.setBackground(null);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(btnLtra))
		{
			if (!txtLtra.getText().isEmpty())
			{
				char letra = txtLtra.getText().toUpperCase().charAt(0);
				txtLtra.setText("");
				txtLtra.requestFocus();
				String nuevaGuiones = "";
				boolean cambios = false;
				for (int i = 0; i < secreta.length(); i++)
				{

					if (letra == secreta.charAt(i))
					{
						nuevaGuiones = nuevaGuiones + letra + " ";
						cambios = true;
					} else
					{
						nuevaGuiones = nuevaGuiones + secretaGuiones.charAt(2 * i) + " ";
					}

				}
				if (!cambios)
				{
					txtTries.setText(txtTries.getText() + " " + letra);
					intentos--;
					lblTries.setText("Intentos restantes: " + intentos + " de 10.");
				}
				txtScrt.setText("");
				secretaGuiones = nuevaGuiones;
				txtScrt.setText(secretaGuiones);
			}
			comprobarFin();
		}
		else if(e.getSource().equals(btnFin)) {
			System.exit(0);
		}
		else if(e.getSource().equals(btnRein)) {
			mensaje.setVisible(false);
			reiniciar();			
		}
	}

	private void comprobarFin()
	{
		if (intentos == 0)
		{
			lblMensaje.setText("Perdiste: " + secreta);
			mensaje.setBackground(Color.RED);
			lblMensaje.setBackground(Color.RED);
			mensaje.setVisible(true);
		
			
		
		}
		else if (secretaGuiones.indexOf('_') == -1)
		{
			lblMensaje.setText("¡GANASTE!");
			mensaje.setBackground(Color.GREEN);
			lblMensaje.setBackground(Color.GREEN);
			mensaje.setVisible(true);
			
		}
	}

	@Override
	public void windowOpened(WindowEvent e){}

	@Override
	public void windowClosing(WindowEvent e)
	{
		if(mensaje.isActive()) {
			mensaje.setVisible(false);
			btnLtra.setEnabled(false);
			txtLtra.setEnabled(false);
		}
		else {
			System.exit(0);
		}
		
	}

	@Override
	public void windowClosed(WindowEvent e){}

	@Override
	public void windowIconified(WindowEvent e){}

	@Override
	public void windowDeiconified(WindowEvent e){}

	@Override
	public void windowActivated(WindowEvent e){}

	@Override
	public void windowDeactivated(WindowEvent e){}
}
