package org.messages.windows.estilo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextField;

public class Decoration {
	
	// Default decoration for the JTextField
	public static JTextField setBasicEstiloTXT(JTextField field) {
		
		field.setBackground(Paleta.COLOR_SECUNDARIO);
		field.setBorder(new RoundedBorder(8, Paleta.COLOR_CLARO));
		field.setForeground(Color.white);
		field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		field.setPreferredSize(new Dimension(0,32));
		
		return field;
	}
	
	// Default decoration for the JButton
	public static JButton setAceptBTN(JButton btn) {
		
		btn.setBackground(Paleta.ACEPTAR);
		btn.setBorder(new RoundedBorder(8, Paleta.ACEPTAR));
		btn.setForeground(Paleta.ACEPTAR_2);
		btn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		btn.setPreferredSize(new Dimension(0,40));
		
		return btn;
	}
	
}
