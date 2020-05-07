package org.messages.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.messages.crypto.Cipher;
import org.messages.network.Client;
import org.messages.network.GetCallback;
import org.messages.network.SentCallback;
import org.messages.network.Server;
import org.messages.parser.ParseMSG;
import org.messages.windows.style.Palette;
//----------------FE------------------------------------------------------------------
// Class where the window goes
public class MainWindow extends JFrame {
	
	// Class constants
	private final int WIDTH = 500;
	private final int HIGH = 600;
	
	private final String TITLE = "Messages";
	
	private final int DEFAULT_CLOP = JFrame.EXIT_ON_CLOSE;
	
	private final boolean REDIMENSIONABLE = true;
	
	// JFrame components
	private JPanel panel;
	
	// CLASS PARAMETERS window constructor
	public MainWindow() {
		// We configure the JFrame
		setSize(WIDTH, HIGH);
		setTitle(TITLE);
		setDefaultCloseOperation(DEFAULT_CLOP);
		setResizable(REDIMENSIONABLE);
		
		// We start the components
		panel = new frameworkMainWindow();
		// We added the components
		add(panel);
	}
	
	// Show window
	public void showWindow() {
		setVisible(true);
	}
	
	// Show the window if show equals true, if not, hide it
	public void showWindow(boolean display) {
		setVisible(display);
	}
	
}

class frameworkMainWindow extends JPanel {

	// Class variables
	private JEditorPane conversacion;
	private JTextField entrada;
	
	// Message stack
	private String messages;
	
	// Constructor of the class we initiate, configure and show
	public frameworkMainWindow() {
		super(new BorderLayout());
		
		conversacion = new JEditorPane();
		entrada = new JTextField();
		
		messages = "";
		
		// We configure the panel where the conversation will take place
		conversacion.setContentType("text/html");
		conversacion.setEditable(false);
		conversacion.setBackground(Palette.COLOR_PRIMARY);
		
		conversacion.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		conversacion.setForeground(Color.WHITE);
		conversacion.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		conversacion.setText("");
		
		// We configure the text input
		entrada.setBackground(Palette.COLOR_SECONDARY);
		entrada.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Palette.COLOR_CLARO));
		entrada.setForeground(Color.white);
		entrada.setPreferredSize(new Dimension(0,30));
		
		entrada.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enviarMensaje(entrada.getText());
			}
		});
		
		// We added the elements to the panel
		add(conversacion, BorderLayout.CENTER);
		add(entrada, BorderLayout.SOUTH);
		
		// We accept messages on the server
		Server.aceptarmessages(new GetCallback() {
			
			@Override
			public String send() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void get(String msg) {
				recivirMensaje(msg);
			}
		});
		
	}
	//--------------------FE-------------------------------------------------------------------------

	//BE------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Method to send message to the other user
	private void enviarMensaje(String msg) {
		
		// If there is a space at the beginning, delete it
		if (msg.charAt(0) == ' ') {
			msg = msg.substring(1, msg.length());
		}
		
		// Delete the text from the input
		entrada.setText("");
		
		System.out.println(msg);
		messages += ParseMSG.parseMensajeEnv(msg);
		conversacion.setText(messages);
		
		// Send the message and set a callback for when the other user has successfully received the message
		Client.enviarMensaje(Cipher.cifrarMSG(msg), null);
	}

	//BE----------------------------------------------------------------------------------------------------------------
	private void recivirMensaje(String msg) {
		messages += ParseMSG.parseMensajeRecv(Cipher.descifrarMSG(msg));
		
		conversacion.setText(messages);
	}
	
	
}
