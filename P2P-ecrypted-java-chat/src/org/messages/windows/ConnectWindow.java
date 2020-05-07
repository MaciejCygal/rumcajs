package org.messages.windows;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.messages.crypto.Cipher;
import org.messages.network.Client;
import org.messages.network.Server;
import org.messages.windows.style.Decoration;
import org.messages.windows.style.Palette;

public class ConnectWindow extends JFrame {
	
	// Class constants
	private final int WIDTH = 470;
	private final int HIGH = 500;
	
	private final String TITLE = "Chat settings";
	
	private final int DEFAULT_CLOP = JFrame.EXIT_ON_CLOSE;
		
	private final boolean REDIMENSIONABLE = false;

	//FE
	public ConnectWindow () {
		// We configure the JFrame
		setSize(WIDTH, HIGH);
		setTitle(TITLE);
		setDefaultCloseOperation(DEFAULT_CLOP);
		setResizable(REDIMENSIONABLE);
		
		// We added the frame
		frameworkConnect framework = new frameworkConnect();
		
		add(framework);
	}

	//FE
	// Show window
	public void showWindow() {
		setVisible(true);
	}

	//FE
	// Show the window if show equals true, if not, hide it
	public void showWindow(boolean display) {
		setVisible(display);
	}

	//FE
	// Close the window
	public void cerrarwindow() {
		dispose();
	}
	
}

class frameworkConnect extends JPanel {

	// Variables necessary to check that both things are started and go to chat
	private boolean clientActivated, serverActivated;

	// Framework objects that must be public to access and control their properties
	private JPanel pMain, pInitserver, pInitClient, pCrypto;
	private JButton btn_crypto, btn_InitServer, btn_InitClient;
	private JTextField Key_txt, SPort_txt, CPort_txt, CIP_txt;

	//FE
	// Class constructor
	public frameworkConnect() {
		
		// Panels are created
		pMain = new JPanel();
		pCrypto = new JPanel();
		pInitserver = new JPanel();
		pInitClient = new JPanel();
		
		// Margins are added with a CardLayout
		setLayout(new CardLayout(10,10));
		setBackground(Palette.COLOR_PRIMARY);
		
		// Main panel, where the other two panels will go, one with the server and one with the client
		pMain = new JPanel();
		pMain.setLayout(new BoxLayout(pMain, BoxLayout.PAGE_AXIS));
		pMain.setBackground(Palette.COLOR_PRIMARY);
		
		// Set the visual properties for both the client and server panels
		setCryptoPropierties();
		setInitserverVPropierties();
		setInitClientVPropierties();
		
		/* 
		*	Button list (To set the cryptographic key, start the server and the client),
        *   simply call other methods that perform the action (For a better understanding of the code)
		*/
		btn_crypto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCryptoKey();
			}
		});
		
		btn_InitServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initServer();
			}
		});
		
		btn_InitClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initClient();
			}
		});
		
		// Secondary panels are added to the main one
		pMain.add(pCrypto);
		pMain.add(pInitserver);
		pMain.add(pInitClient);
		
		// The main is added to the top with the edges added
		add(pMain);
	}

	//FE
	// Set the cryptographic key in case it hasn't been changed
	private void setCryptoKey() {
		new Cipher(Key_txt.getText());
	}

	//BE
	// Start the server, and if the client has already started, the dialog screen opens
	private void initServer() {
		String sport = SPort_txt.getText();
		int port;
		try {
			port = Integer.parseInt(sport);
			new Server(port);
			JOptionPane.showMessageDialog(null, "Server started successfully, the other user can now connect", "Server started", JOptionPane.INFORMATION_MESSAGE);
			SPort_txt.setEditable(false);
			btn_InitServer.setEnabled(false);
			serverActivated = true;
			if (clientActivated) {
				MainWindow window = new MainWindow();
				window.showWindow();
				// CLOSE THIS WINDOW MISSING
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Only numbers can be entered in the port", "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}

	//BE
	// Start the client, and if the server has already started, the dialog screen opens
	private void initClient() {
		String IP = CIP_txt.getText();
		String sport = CPort_txt.getText();
		
		int port;
		
		try {
			port = Integer.parseInt(sport);
			new Client(IP, port);
			JOptionPane.showMessageDialog(null, "Client connected successfully", "Client started", JOptionPane.INFORMATION_MESSAGE);
			CIP_txt.setEditable(false);
			CPort_txt.setEditable(false);
			btn_InitClient.setEnabled(false);
			if (serverActivated) {
				MainWindow window = new MainWindow();
				window.showWindow();
				// CLOSE THIS WINDOW MISSING
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "You can only enter numbers in the port", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	//FE
	// Sets the visual properties of the panel
	private void setCryptoPropierties() {
		pCrypto.setForeground(Color.WHITE);
		pCrypto.setBackground(Palette.COLOR_PRIMARY);
		pCrypto.setLayout(new GridBagLayout());
		pCrypto.setBorder(
			BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Palette.COLOR_SECONDARY), 
				"Encryption",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				null,
				Color.WHITE
			)
		);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 3, 3, 3);
		c.gridwidth = 1;
		
		// ROW 1
		JLabel lab_key = new JLabel("Encryption key");
		lab_key.setForeground(Color.WHITE);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		pCrypto.add(lab_key,c);
		
		// ROW 2
		Key_txt = new JTextField();
		Decoration.setBasicstyleTXT(Key_txt);
		c.gridy = 1;
		c.insets = new Insets(0,6,0,6);
		pCrypto.add(Key_txt, c);
		
		// ROW 3
		btn_crypto = new JButton("Set password");
		Decoration.setAceptBTN(btn_crypto);
		c.gridy = 2;
		pCrypto.add(btn_crypto, c);
	}

	//FE
	// Sets the visual properties of the panel
	private void setInitserverVPropierties() {
		pInitserver.setForeground(Color.WHITE);
		pInitserver.setBackground(Palette.COLOR_PRIMARY);
		pInitserver.setLayout(new GridBagLayout());
		pInitserver.setBorder(
			BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Palette.COLOR_SECONDARY), 
				"Configure server",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				null,
				Color.WHITE
			)
		);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 3, 3, 3);
		
		// ROW 1
		JLabel lab_Port = new JLabel("port");
		lab_Port.setForeground(Color.WHITE);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 2;
		c.gridy = 0;
		pInitserver.add(lab_Port, c);
		
		// ROW 2
		SPort_txt = new JTextField();
		Decoration.setBasicstyleTXT(SPort_txt);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.insets = new Insets(0,6,0,6);
		pInitserver.add(SPort_txt, c);
		
		// ROW 3
		btn_InitServer = new JButton("Start server");
		Decoration.setAceptBTN(btn_InitServer);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.insets = new Insets(6, 6, 6, 6);
		pInitserver.add(btn_InitServer, c);
	}

	//FE
	// Sets the visual properties of the panel
	private void setInitClientVPropierties() {
		pInitClient.setForeground(Color.WHITE);
		pInitClient.setBackground(Palette.COLOR_PRIMARY);
		pInitClient.setLayout(new GridBagLayout());
		pInitClient.setBorder(
			BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Palette.COLOR_SECONDARY), 
				"Configure client",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				null,
				Color.WHITE
			)
		);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 3, 3, 3);
		
		// ROW 1
		JLabel lab_IP = new JLabel("IP");
		lab_IP.setForeground(Color.WHITE);
		c.weightx = 2;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		pInitClient.add(lab_IP, c);
		
		JLabel lab_Port = new JLabel("port");
		lab_Port.setForeground(Color.WHITE);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 2;
		c.gridy = 0;
		pInitClient.add(lab_Port, c);
		
		// ROW 2
		CIP_txt = new JTextField();
		Decoration.setBasicstyleTXT(CIP_txt);
		c.weightx = 2;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0,6,0,6);
		pInitClient.add(CIP_txt, c);
		
		CPort_txt = new JTextField();
		Decoration.setBasicstyleTXT(CPort_txt);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(0,0,0,6);
		pInitClient.add(CPort_txt, c);
		
		// ROW 3
		btn_InitClient = new JButton("Start Client");
		Decoration.setAceptBTN(btn_InitClient);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.insets = new Insets(6, 6, 6, 6);
		pInitClient.add(btn_InitClient, c);
		
	}
	
}
