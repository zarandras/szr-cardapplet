package szr.maincli;

import java.awt.*;
import java.awt.event.*;
/**
 * A kliens Csevegõ osztálya
 * Creation date: (2002.01.26. 15:31:34)
 */
public class Chatter implements ActionListener {
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	private ClientController parentController = null;
	private final java.awt.TextArea chatTextArea;
	private final java.awt.TextField uzenetTextField;
	private final java.awt.Button mondButton;
public Chatter(TextArea iChatTextArea, TextField iUzenetTextField, Button iMondButton) {
	super();
	chatTextArea = iChatTextArea;
	uzenetTextField = iUzenetTextField;
	mondButton = iMondButton;
	mondButton.addActionListener(this);
	mondButton.setEnabled(false);
}
public void actionPerformed(ActionEvent e) {
  synchronized (parentController) {
	if (e.getSource() == mondButton && mondButton.isEnabled()) {
		String s = uzenetTextField.getText();
		if (s.length() != 0) {
			send(s);
			uzenetTextField.setText("");//$NON-NLS-1$
		}
	}
  }
}
public void enable() {
	mondButton.setEnabled(true);
}
public ClientController getParentController() {
	return parentController;
}
/**
 * Játék kezdetén default szöveg kiírása
 */
public void printJoinMessage(boolean firstPlayer) {
	receive(szr.shared.SubstituteStr.doIt(resChatTexts.getString("JoinMessage1"), "%g", parentController.getGameName()));//$NON-NLS-2$ //$NON-NLS-1$
	receive(resChatTexts.getString("JoinMessage2")); //$NON-NLS-1$
	if (!firstPlayer)
		receive(resChatTexts.getString("[...]")); //$NON-NLS-1$
}
public void receive(String msgText) {
	chatTextArea.append(msgText + "\n");//$NON-NLS-1$

	// ha túl sok szöveg gyûlt össze, az elejét töröljük:
	int l = chatTextArea.getText().length();
	if (l > 20000) {
		chatTextArea.replaceRange("", 0, l - 10000);//$NON-NLS-1$
		chatTextArea.append("");//$NON-NLS-1$
	}
}
public void resetAndDisable() {
	chatTextArea.setText("");//$NON-NLS-1$
	uzenetTextField.setText("");//$NON-NLS-1$
	mondButton.setEnabled(false);
}
private void send(String msgText) {
	getParentController().getMsgHandler().send(new szr.msg.MGAChat(msgText));
}
/**
  * A ClientController hívja, ha létrejött
  */
public void setParentController(ClientController newParentController) {
	parentController = newParentController;
}
}
