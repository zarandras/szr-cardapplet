package szr.maincli;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import szr.shared.*;
/**
 * A kliens Játékpanel osztálya
 * Creation date: (2002.01.27. 15:05:48)
 */
public class GameControlPanel implements ActionListener, TextListener, ItemListener {
	private static java.util.ResourceBundle resFileDirs = java.util.ResourceBundle.getBundle("szr.shared.FileDirs");  //$NON-NLS-1$
	private static java.util.ResourceBundle resWidgetTexts = java.util.ResourceBundle.getBundle("szr.shared.WidgetTexts");  //$NON-NLS-1$
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private ClientController parentController = null;
	private final TextField jatekTextField;
	private final Button ujButton;
	private final Button csatlButton;
	private final Button pakliDescrButton;
	private final Button kiszallButton;
	private final List jatekList;
	private final Choice pakliJellChoice;
	private final Choice osztasJellChoice;
	private final Button jatekosListaButton;

	// widget state konstansok:
	protected final static int WS_DISABLED = 0;
	protected final static int WS_VALIDNEWNAME = 1;
	protected final static int WS_PLAYING = 3;
	protected final static int WS_VALIDEXISTINGNAME = 2;
	protected final static int WS_VALIDPLAYINGNAME = 5;
	protected final static int WS_INVALIDNAME = 4;
	
	private final Vector jatekok;
	private int myMinOsztottLapok = 0;
	private java.util.Vector pakliRecs = null;
public GameControlPanel(TextField iJatekTextField,
						Button iUjButton, Button iCsatlButton, Button iKiszallButton,
						List iJatekList,
						Choice iPakliJellChoice, Choice iOsztasJellChoice,
						Button iJatekosListaButton, Button iPakliDescrButton) {
	super();
	jatekok = new Vector ();

	jatekTextField = iJatekTextField;
	ujButton = iUjButton;
	csatlButton = iCsatlButton;
	kiszallButton = iKiszallButton;
	jatekList = iJatekList;
	pakliJellChoice = iPakliJellChoice;
	osztasJellChoice = iOsztasJellChoice;
	jatekosListaButton = iJatekosListaButton;
	pakliDescrButton = iPakliDescrButton;

	jatekTextField.addTextListener(this);
	ujButton.addActionListener(this);
	csatlButton.addActionListener(this);
	kiszallButton.addActionListener(this);
	jatekList.addItemListener(this);
	jatekosListaButton.addActionListener(this);
	pakliDescrButton.addActionListener(this);

	pakliJellChoice.insert (resWidgetTexts.getString("(...)"), 0); //$NON-NLS-1$
	// fillPakliJellChoice fills it
	
	osztasJellChoice.insert (resWidgetTexts.getString("(...)"), 0); //$NON-NLS-1$
	// fillOsztasJellChoice fills it
	
	setWidgetState (WS_DISABLED);
}
public void actionPerformed(ActionEvent e) {
  synchronized (parentController) {
	Object srcWidget = e.getSource ();
	PlayerControlPanel pcp = getParentController().getPlayerControlPanel ();

	if (srcWidget == ujButton && ujButton.isEnabled()) {
		if (getParentController().getPlayerName().length() == 0) {
			getParentController().rejectedAction(resInfoTexts.getString("Add_meg_a_játékosnevedet_m")); //$NON-NLS-1$
		} else if (pakliJellChoice.getSelectedIndex() == 0 || osztasJellChoice.getSelectedIndex() == 0) {
			getParentController().rejectedAction(resInfoTexts.getString("Válassz_ki_egy_paklit_és_a")); //$NON-NLS-1$
		} else {
			setWidgetState(WS_DISABLED);
			getParentController().getMsgHandler().send(new szr.msg.SCAJatekotHirdet(
				new GameRec(jatekTextField.getText(), pakliJellChoice.getSelectedIndex() - 1, osztasJellChoice.getSelectedIndex() + myMinOsztottLapok - 1),
				new PlayerRec(pcp.getName(), pcp.getEmail(), getParentController().getPlayerList().getMySmiley()),
				pcp.getPassword()));
		}
	} else if (srcWidget == csatlButton && csatlButton.isEnabled()) {
		if (getParentController().getPlayerName().length() == 0) {
			getParentController().rejectedAction(resInfoTexts.getString("Add_meg_a_játékosnevedet_m1")); //$NON-NLS-1$
		} else if (jatekList.getSelectedItem().charAt(0) == '(') {
			getParentController().rejectedAction(resInfoTexts.getString("A_játék_már_elkezdõdött,_n")); //$NON-NLS-1$
		} else {
			setWidgetState(WS_DISABLED);
			getParentController().getMsgHandler().send(new szr.msg.SCACsatlakozik(
				jatekTextField.getText(),
				new PlayerRec(pcp.getName(), pcp.getEmail(), getParentController().getPlayerList().getMySmiley()),
				pcp.getPassword()));
		}
	} else if (srcWidget == kiszallButton && kiszallButton.isEnabled()) {
		setWidgetState(WS_DISABLED);
		getParentController().getMsgHandler().send(new szr.msg.SCAKiszall());
	} else if (srcWidget == jatekosListaButton && jatekosListaButton.isEnabled()) {
		getParentController().getMsgHandler().send(new szr.msg.SCAJatekosLista(jatekTextField.getText()));
	} else if (srcWidget == pakliDescrButton) {
		displayPakliDescrHTML();
	}
  }
}
/**
 * A beírt játéknév alapján a widget-ek beállítása
 */
private int checkBeirtNev() {
	String beirtNev = jatekTextField.getText ();
	if (beirtNev.length() == 0 || beirtNev.indexOf (' ') != -1 || beirtNev.indexOf ('(') != -1 || beirtNev.indexOf (')') != -1) {
		jatekList.deselect (jatekList.getSelectedIndex ());
		return WS_INVALIDNAME;
	} else {
		int i = jatekKeres (beirtNev);
		if (i >= jatekok.size ()) { /* not found */
			jatekList.deselect (jatekList.getSelectedIndex ());
			return WS_VALIDNEWNAME;
		} else {
			jatekList.select (i);
			GameRec jr = (GameRec) jatekok.elementAt (i);
			pakliJellChoice.select (jr.pakliIdx + 1);
			osztasJellChoice.select (jr.kezdetiLapSzam - myMinOsztottLapok + 1);
			if (jr.started) {
				return WS_VALIDPLAYINGNAME;
			} else {
				return WS_VALIDEXISTINGNAME;
			}
		}
	}
}
public void disable() {
	setWidgetState(WS_DISABLED);
}
/**
 * Insert the method's description here.
 * Creation date: (2002.05.18. 14:30:30)
 */
private void displayPakliDescrHTML() {
	String descrDir = resFileDirs.getString("paklik/"); //$NON-NLS-1$
	String descrName;
	if (pakliRecs == null || pakliJellChoice.getSelectedIndex() == 0) {
		descrName = "index.html";//$NON-NLS-1$
	} else {
		descrName = ((PakliRec)pakliRecs.elementAt(pakliJellChoice.getSelectedIndex() - 1)).pakliDocHTML;
		if (descrName == null)
			descrName = "index.html";//$NON-NLS-1$
	}
	getParentController().getInfoSystem().displayDocHTML(descrDir + descrName);
}
public void fillJatekList(Vector jatekVect) {
	for (int i = 0; i < jatekVect.size (); ++i) {
		ujJatek ((GameRec) jatekVect.elementAt (i), false);
	}
	setWidgetState (checkBeirtNev ());
}
public void fillOsztasJellChoice(int minOsztottLapok, int maxOsztottLapok) {
	myMinOsztottLapok = minOsztottLapok;
	for (int i = 0; i < maxOsztottLapok - minOsztottLapok + 1; ++i) {
		osztasJellChoice.insert (Integer.toString(i + minOsztottLapok), i + 1);
	}
}
public void fillPakliJellChoice(Vector pakliVect) {
	pakliRecs = pakliVect;
	for (int i = 0; i < pakliVect.size (); ++i) {
		pakliJellChoice.insert(((PakliRec)pakliVect.elementAt(i)).toString(), i + 1);
	}
}
public String getJatekNev() {
	return jatekTextField.getText();
}
public ClientController getParentController() {
	return parentController;
}
public void itemStateChanged(ItemEvent itemEvent) {
  synchronized (parentController) {
	if (itemEvent.getItemSelectable () == jatekList && itemEvent.getStateChange () == ItemEvent.SELECTED
		&& jatekList.isEnabled()) {
		try {
			String s = new String (jatekList.getSelectedItem ());
			jatekTextField.setText((s.charAt(0) == '(') ? s.substring(1, s.length()-1) : s);
			resetWidgetState();
		} catch (Exception e) {
			szr.msg.Logger.log(new szr.msg.ErrExcLog(e), parentController.getGameName(), parentController.getPlayerName());
			jatekTextField.setText("");//$NON-NLS-1$
			resetWidgetState();
		}
	}
  }
}
private int jatekKeres(String jatekNev) {
	int i = 0;
	while (i < jatekok.size ()) {
		if (((GameRec) jatekok.elementAt (i)).name.equals (jatekNev))
			break;
		i++;
	}
	return i;
}
public void jatekKezdodik(String jatekNev) {
	int i = jatekKeres(jatekNev);
	if (i < jatekok.size()) {
		jatekok.setElementAt(((GameRec)jatekok.elementAt(i)).started(), i);
		jatekList.replaceItem(((GameRec)jatekok.elementAt(i)).toString(), i);
		if (!parentController.isJoined())
			setWidgetState(checkBeirtNev());
	}
}
public void jatekTorolve(String jatekNev) {
	int i = jatekKeres (jatekNev);
	if (i < jatekok.size ()) {
		jatekList.remove (i);
		jatekok.removeElementAt (i);
		if (!parentController.isJoined ())
			setWidgetState (checkBeirtNev ());
	}
}
public void resetWidgetState() {
	if (parentController.getPlayState() == ClientController.PS_DISCONNECTED)
		disable();
	else if (!parentController.isJoined ())
		setWidgetState (checkBeirtNev ());
	else
		setWidgetState (WS_PLAYING);
}
public void setParentController(ClientController newParentController) {
	parentController = newParentController;
}
private void setWidgetState(int state) {
	switch (state) {
		
		case WS_DISABLED:
			jatekTextField.setEnabled (false);
			ujButton.setEnabled (false);
			csatlButton.setEnabled (false);
			kiszallButton.setEnabled (false);
			jatekList.setEnabled (false);
			pakliJellChoice.setEnabled (false);
			osztasJellChoice.setEnabled (false);
			jatekosListaButton.setEnabled (false);
			break;
					 
		case WS_INVALIDNAME:
			jatekTextField.setEnabled (true);
			ujButton.setEnabled (false);
			csatlButton.setEnabled (false);
			kiszallButton.setEnabled (false);
			jatekList.setEnabled (true);
			pakliJellChoice.setEnabled (false);
			osztasJellChoice.setEnabled (false);
			jatekosListaButton.setEnabled (false);
			break;

		case WS_VALIDNEWNAME:
			jatekTextField.setEnabled (true);
			ujButton.setEnabled (true);
			csatlButton.setEnabled (false);
			kiszallButton.setEnabled (false);
			jatekList.setEnabled (true);
			pakliJellChoice.setEnabled (true);
			osztasJellChoice.setEnabled (true);
			jatekosListaButton.setEnabled (false);
			break;

		case WS_VALIDEXISTINGNAME:
			jatekTextField.setEnabled (true);
			ujButton.setEnabled (false);
			csatlButton.setEnabled (true);
			kiszallButton.setEnabled (false);
			jatekList.setEnabled (true);
			pakliJellChoice.setEnabled (false);
			osztasJellChoice.setEnabled (false);
			jatekosListaButton.setEnabled (true);
			break;

		case WS_PLAYING:
			jatekTextField.setEnabled (false);
			ujButton.setEnabled (false);
			csatlButton.setEnabled (false);
			kiszallButton.setEnabled (true);
			jatekList.setEnabled (false);
			pakliJellChoice.setEnabled (false);
			osztasJellChoice.setEnabled (false);
			jatekosListaButton.setEnabled (false);
			break;

		case WS_VALIDPLAYINGNAME:
			jatekTextField.setEnabled (true);
			ujButton.setEnabled (false);
			csatlButton.setEnabled (/*false*/true);
			kiszallButton.setEnabled (false);
			jatekList.setEnabled (true);
			pakliJellChoice.setEnabled (false);
			osztasJellChoice.setEnabled (false);
			jatekosListaButton.setEnabled (true);
			break;

		default:
			szr.msg.Logger.log(new szr.msg.ErrStrLog(SubstituteStr.doIt("GameControlPanel error: invalid WS constant %wsc at setWidgetState", "%wsc", Integer.toString(state))), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
			break;
	}
}
public void textValueChanged(TextEvent e) {
	if (parentController != null) {
	  synchronized (parentController) {
		try {
			if (e.getSource () == jatekTextField && jatekTextField.isEnabled()) {
				setWidgetState (checkBeirtNev ());
					if (!getParentController().getPlayerList().isEmpty())
						getParentController().getPlayerList().delAllPlayers();
			}
		} catch (NullPointerException ex) {}
	  }
	}
}
public void ujJatek(GameRec jr, boolean updateWidgetState) {
	jatekok.addElement (jr);
	jatekList.add (jr.toString ());
	if (updateWidgetState && !parentController.isJoined ())
		setWidgetState (checkBeirtNev ());
}
}
