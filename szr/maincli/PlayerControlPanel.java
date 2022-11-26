package szr.maincli;

import java.awt.*;
import java.awt.event.*;
/**
 * A kliens Játékospanel osztálya
 * Creation date: (2002.01.27. 15:06:13)
 */
public class PlayerControlPanel implements ActionListener, TextListener {
	private ClientController parentController = null;
	private final TextField nevTextField;
	private final TextField emailTextField;
	private final TextField jelszoTextField;
	private final Button jelszoKuldButton;
	private boolean enabled = false;
public PlayerControlPanel(TextField iNevTextField, TextField iEmailTextField, TextField iJelszoTextField, Button iJelszoKuldButton) {
	super ();

	nevTextField = iNevTextField;
	emailTextField = iEmailTextField;
	jelszoTextField = iJelszoTextField;
	jelszoKuldButton = iJelszoKuldButton;

	jelszoKuldButton.addActionListener (this);
	jelszoTextField.setEchoChar('*');
	nevTextField.addTextListener (this);
	emailTextField.addTextListener (this);

	setEnabled (false);
}
public void actionPerformed(java.awt.event.ActionEvent e) {
  synchronized (parentController) {
	if (e.getSource () == jelszoKuldButton && jelszoKuldButton.isEnabled()) {
		jelszoKuldButton.setEnabled(false);	// nem enabláljuk, csak ha módosul valami valamelyik mezõben
											// (hogy ne lehessen egyszerre sokszor megnyomni)
		getParentController().getMsgHandler().send(new szr.msg.SCAJelszoKuldes(nevTextField.getText(), emailTextField.getText()));
	}
  }
}
/**
 * A jelszóküldés funkció engedélyezése, ha érvényesnek tûnõ email-cím van megadva
 */
private void adjustJelszoKuldState() {
	String nev = nevTextField.getText();
	String email = emailTextField.getText();
	int atPos = email.indexOf('@');
	jelszoKuldButton.setEnabled (nev.length() != 0 && email.length() > atPos + 1 && atPos > 0);
}
public String getEmail() {
	return emailTextField.getText();
}
public String getName() {
	return nevTextField.getText();
}
public ClientController getParentController() {
	return parentController;
}
public String getPassword() {
	return jelszoTextField.getText();
}
/**
 * Állapotbeállítás az aktuális játékosállapot alapján
 */
public void resetWidgetState() {
	setEnabled (!parentController.isJoined () && parentController.getPlayState() != ClientController.PS_DISCONNECTED);
}
public void setEnabled(boolean newEnabled) {
	enabled = newEnabled;
	nevTextField.setEnabled(enabled);
	emailTextField.setEnabled(enabled);
	jelszoTextField.setEnabled(enabled);
	if (enabled) {
		adjustJelszoKuldState();
	} else {
		jelszoKuldButton.setEnabled(false);
	}
}
public void setParentController(ClientController newParentController) {
	parentController = newParentController;
}
public void textValueChanged(java.awt.event.TextEvent e) {
  synchronized (parentController) {
	if (enabled && (e.getSource () == nevTextField || e.getSource () == emailTextField)) {
		adjustJelszoKuldState();
	}
  }
}
}
