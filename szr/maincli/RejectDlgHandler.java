package szr.maincli;

import java.awt.*;
import java.awt.event.*;
/**
 * A kliens figyelmezetõ üzenetet megjelenítõ ablakát kezelõ osztály
 * Creation date: (2002.02.07. 21:15:46)
 */
public class RejectDlgHandler implements ActionListener {
	private final Dialog rejectDialog;
	private final Button rejectOKButton;
	private final TextArea rejectInfoTextArea;
	private final Button rejectGameHelpButton;
	private InfoSystem parentInfoSystem = null;

	// kiegészítõ játékosállapot-konstansok (a figyelmeztetõ panelrõl hívható JátékSúgó miatt)
	public final static int PSR_AUTOPASSZ = -1;
	public final static int PSR_ERROR_FATAL = -2;
	public final static int PSR_MEGELOZOTT = -3;
	
	private int myPState = -1;
public RejectDlgHandler(Dialog iRejectDialog, TextArea iRejectInfoTextArea, Button iRejectOKButton, Button iRejectGameHelpButton) {
	super();
	rejectDialog = iRejectDialog;
	rejectInfoTextArea = iRejectInfoTextArea;
	rejectOKButton = iRejectOKButton;
	rejectGameHelpButton = iRejectGameHelpButton;
	rejectOKButton.addActionListener(this);
	rejectGameHelpButton.addActionListener(this);
	rejectDialog.addWindowListener(new java.awt.event.WindowAdapter() {
		public void windowClosing(java.awt.event.WindowEvent e) {
			rejectDialog.setVisible(false);
		};
	});
}
public void actionPerformed(ActionEvent e) {
	if (e.getSource() == rejectOKButton) {
		rejectDialog.setVisible(false);
		rejectInfoTextArea.setText("");//$NON-NLS-1$
	} else if (e.getSource() == rejectGameHelpButton) {
		synchronized (parentInfoSystem.getParentController()) {
			parentInfoSystem.displayJatekSugo(myPState);
		}
	}
}
public InfoSystem getParentInfoSystem() {
	return parentInfoSystem;
}
public void setParentInfoSystem(InfoSystem newParentInfoSystem) {
	parentInfoSystem = newParentInfoSystem;
}
public synchronized void showRejectDialog(String rejectInfoText, int pState) {
	myPState = pState;
	rejectInfoTextArea.append ("\n\n" + rejectInfoText);//$NON-NLS-1$

	// ha túl hosszú a szöveg, akkor az elejérõl törlünk:
	int l = rejectInfoTextArea.getText ().length ();
	if (l > 20000) {
		rejectInfoTextArea.replaceRange ("", 0, l - 10000);//$NON-NLS-1$
		//infoTextArea.append ("");
	}
	rejectDialog.show();
	rejectInfoTextArea.setCaretPosition(rejectInfoTextArea.getText().length());
}
}
