package szr.maincli;

import java.awt.*;
import java.awt.event.*;
import szr.card.*;
import szr.mainserv.*;
import szr.gui.*;
import szr.shared.*;
/**
 * A kliens InfóBoxot, Súgót, RejectDialogot és CardHelpPopupMenüt kezelõ osztálya
 * Creation date: (2002.02.16. 10:03:29)
 */
public class InfoSystem implements ActionListener {
	private static java.util.ResourceBundle resFileDirs = java.util.ResourceBundle.getBundle("szr.shared.FileDirs");  //$NON-NLS-1$
	private static java.util.ResourceBundle resWidgetTexts = java.util.ResourceBundle.getBundle("szr.shared.WidgetTexts");  //$NON-NLS-1$
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final TextArea infoTextArea;
	private final CardApplet myApplet;
	private final Button gameHelpButton;
	private final Button cardHelpButton;
	private final RejectDlgHandler rejectDlgHandler;
	private final PopupMenu cardHelpPopupMenu;
	private final PopupMenu gameHelpPopupMenu;
	private final MenuItem szinSugoMenuItem;
	private final MenuItem resolveMenuItem;
	private final MenuItem jelzesSugoMenuItem;
	private final MenuItem whatToDoMenuItem;
	private final MenuItem appletHelpMenuItem;
	private OsSzin helpSzin = null;		// errõl kell SzínSúgót adni
	private OsJelzes helpJelzes = null;	// errõl kell JelzésSúgót adni
	private ClientController parentController = null;
	private szr.shared.UtSzam myUtSzam = null;	// az InfóBoxba ki kell írni a kötelezõ húzást
	private szr.shared.ExSType myExS = null;	// az InfóBoxba ki kell írni az extra sorozási lehetõséget
	private SmallImgComponent currentSmallImg = null;	// a SmallImg, melyen a menüt kinyitottuk
	public final static java.awt.Color ACTIVE_COLOR = Color.green;
	public final static java.awt.Color INACTIVE_COLOR = Color.lightGray;
	public final static java.awt.Color DEFAULT_COLOR = Color.white;
	private final static java.lang.String HELP_PATH = resFileDirs.getString("help/"); //$NON-NLS-1$
public InfoSystem(TextArea iInfoTextArea, Button iGameHelpButton, Button iCardHelpButton,
				PopupMenu iCardHelpPopupMenu, MenuItem iSzinSugoMenuItem, MenuItem iJelzesSugoMenuItem, MenuItem iResolveMenuItem,
				PopupMenu iGameHelpPopupMenu, MenuItem iWhatToDoMenuItem, MenuItem iAppletHelpMenuItem,
				RejectDlgHandler iRejectDlgHandler, CardApplet iMyApplet) {
	super();
	infoTextArea = iInfoTextArea;
	cardHelpButton = iCardHelpButton;
	gameHelpButton = iGameHelpButton;
	cardHelpPopupMenu = iCardHelpPopupMenu;
	gameHelpPopupMenu = iGameHelpPopupMenu;
	szinSugoMenuItem = iSzinSugoMenuItem;
	jelzesSugoMenuItem = iJelzesSugoMenuItem;
	resolveMenuItem = iResolveMenuItem;
	whatToDoMenuItem = iWhatToDoMenuItem;
	appletHelpMenuItem = iAppletHelpMenuItem;
	rejectDlgHandler = iRejectDlgHandler;
	myApplet = iMyApplet;

	rejectDlgHandler.setParentInfoSystem(this);

	cardHelpButton.addActionListener(this);
	gameHelpButton.addActionListener(this);
	szinSugoMenuItem.addActionListener(this);
	jelzesSugoMenuItem.addActionListener(this);
	resolveMenuItem.addActionListener(this);
	whatToDoMenuItem.addActionListener(this);
	appletHelpMenuItem.addActionListener(this);

	szinSugoMenuItem.setEnabled(false);
	jelzesSugoMenuItem.setEnabled(false);
	resolveMenuItem.setEnabled(false);
	whatToDoMenuItem.setEnabled(true);
	appletHelpMenuItem.setEnabled(true);
}
public void actionPerformed(ActionEvent e) {
  synchronized (parentController) {
	Object srcWidget = e.getSource();
	if (srcWidget == gameHelpButton) {
		gameHelpPopupMenu.show(gameHelpButton, 20, 10);
	} else if (srcWidget == whatToDoMenuItem) {
		displayJatekSugo();
	} else if (srcWidget == appletHelpMenuItem) {
		displayAppletSugo();
	} else if (srcWidget == cardHelpButton) {
		try {
			Card c = getParentController().getGameTable().getDisplayedCardObj();
			setHelpSzin(c.getSzin());
			setHelpJelzes(c.getJelzes());
			setCurrentSmallImg(null);
		} catch (NullPointerException ex) {
			setHelpSzin(null);
			setHelpJelzes(null);
			setCurrentSmallImg(null);
		}
		szinSugoMenuItem.setEnabled(true);		// ha valamelyik null, akkor a tartalomjegyzéket adjuk,
		jelzesSugoMenuItem.setEnabled(true);	// ezért mindenképp enablálunk
		cardHelpPopupMenu.show(cardHelpButton, 20, 10);
	} else if (srcWidget == szinSugoMenuItem && szinSugoMenuItem.isEnabled()) {
		displaySzinSugo();
	} else if (srcWidget == jelzesSugoMenuItem && jelzesSugoMenuItem.isEnabled()) {
		displayJelzesSugo();
	} else if (srcWidget == resolveMenuItem && resolveMenuItem.isEnabled()) {
		displayResolvedSmallImg();
	}
  }
}
public void displayAppletSugo() {
	String appletHelpURLStr = getHelpRootURL();
	appletHelpURLStr += "applet.html";//$NON-NLS-1$
	try {
		szr.msg.Logger.log(new szr.msg.InfoLog(szr.shared.SubstituteStr.doIt("InfoSystem: Displaying game help %hfile", "%hfile", appletHelpURLStr)), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
		java.net.URL appletHelpURL = new java.net.URL(appletHelpURLStr);
		myApplet.getAppletContext().showDocument(appletHelpURL, "_blank");//$NON-NLS-1$
	} catch (java.net.MalformedURLException exc) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(exc), parentController.getGameName(), parentController.getPlayerName());
	}
}
public void displayDocHTML(String path) {
	String docURLStr = myApplet.getBaseURL() + path;
	try {
		szr.msg.Logger.log(new szr.msg.InfoLog(szr.shared.SubstituteStr.doIt("InfoSystem: Displaying doc HTML %hfile", "%hfile", docURLStr)), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
		java.net.URL docURL = new java.net.URL(docURLStr);
		myApplet.getAppletContext().showDocument(docURL, "_blank");//$NON-NLS-1$
	} catch (java.net.MalformedURLException exc) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(exc), parentController.getGameName(), parentController.getPlayerName());
	}
}
public void displayJatekSugo() {
	displayJatekSugo(parentController.getPlayState());
}
public void displayJatekSugo(int myPState) {
	String gameHelpURLStr = getHelpRootURL();
	switch (myPState) {

		// PS_...:
		
		case ClientController.PS_NOTJOINED:
				gameHelpURLStr += "ps_notjoined.html";//$NON-NLS-1$
			break;
		case ClientController.PS_JOINED:
				gameHelpURLStr += "ps_joined.html";//$NON-NLS-1$
			break;
		case ClientController.PS_JOINED_FIRST:
				gameHelpURLStr += "ps_joined_first.html";//$NON-NLS-1$
			break;
		case ClientController.PS_INACTIVE:
				gameHelpURLStr += "ps_inactive.html";//$NON-NLS-1$
			break;
		case ClientController.PS_INACTIVE_BESZURHAT:
				gameHelpURLStr += "ps_inactive_beszurhat.html";//$NON-NLS-1$
			break;
		case ClientController.PS_INACTIVE_VETOZHAT:
				gameHelpURLStr += "ps_inactive_vetozhat.html";//$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE:
				gameHelpURLStr += "ps_active.html";//$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_SOROZHAT:
				gameHelpURLStr += "ps_active_sorozhat.html";//$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_VETOBLOCKED:
				gameHelpURLStr += "ps_active_vetoblocked.html";//$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_LILA:
				gameHelpURLStr += "ps_active_lila.html";//$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_SZINKERO:
				gameHelpURLStr += "ps_active_szinkero.html";//$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_JELZESKERO:
				gameHelpURLStr += "ps_active_jelzeskero.html";//$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_KEZDOLAP:
				gameHelpURLStr += "ps_active_kezdolap.html";//$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_IRANYKERO:
				gameHelpURLStr += "ps_active_iranykero.html";//$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_LAPKERO:
				gameHelpURLStr += "ps_active_lapkero.html";//$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_GYENGE_LAPKERO:
				gameHelpURLStr += "ps_active_gyenge_lapkero.html";//$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_UT:
				gameHelpURLStr += "ps_active_ut.html";//$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_EXS:
				gameHelpURLStr += "ps_active_exs.html";//$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_EXS_SOROZHAT:
				gameHelpURLStr += "ps_active_exs_sorozhat.html";//$NON-NLS-1$
			break;
		case ClientController.PS_VESZTETT:
				gameHelpURLStr += "ps_vesztett.html";//$NON-NLS-1$
			break;
		case ClientController.PS_NYERT:
				gameHelpURLStr += "ps_nyert.html";//$NON-NLS-1$
			break;
		case ClientController.PS_DISCONNECTED:
				gameHelpURLStr += "ps_disconnected.html";//$NON-NLS-1$
			break;
		case ClientController.PS_EGYEDULMARADT:
				gameHelpURLStr += "ps_egyedulmaradt.html";//$NON-NLS-1$
			break;
			
		// PSR_...:
		
		case RejectDlgHandler.PSR_AUTOPASSZ:
				gameHelpURLStr += "psr_autopassz.html";//$NON-NLS-1$
			break;
		case RejectDlgHandler.PSR_ERROR_FATAL:
				gameHelpURLStr += "psr_error.html";//$NON-NLS-1$
			break;
		case RejectDlgHandler.PSR_MEGELOZOTT:
				gameHelpURLStr += "psr_megelozott.html";//$NON-NLS-1$
			break;


		default:
				szr.msg.Logger.log(new szr.msg.ErrStrLog(szr.shared.SubstituteStr.doIt("InfoSystem error: invalid playState constant %ps at displayGameHelp()", "%ps", Integer.toString(myPState))), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
				gameHelpURLStr += "ps_index.html";//$NON-NLS-1$
			break;
	}

	try {
		szr.msg.Logger.log(new szr.msg.InfoLog(szr.shared.SubstituteStr.doIt("InfoSystem: Displaying game help %hfile", "%hfile", gameHelpURLStr)), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
		java.net.URL gameHelpURL = new java.net.URL(gameHelpURLStr);
		myApplet.getAppletContext().showDocument(gameHelpURL, "_blank");//$NON-NLS-1$
	} catch (java.net.MalformedURLException exc) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(exc), parentController.getGameName(), parentController.getPlayerName());
	}
}
public void displayJelzesSugo() {
	String sugoURLStr = getHelpRootURL();
	if (helpJelzes == null) {
		// index
		sugoURLStr += "j_index.html";//$NON-NLS-1$
	} else {
		sugoURLStr += helpJelzes.getJelzesSugoFileName();
	}

	try {
		szr.msg.Logger.log(new szr.msg.InfoLog(szr.shared.SubstituteStr.doIt("InfoSystem: Displaying jelzesSugo %hfile", "%hfile", sugoURLStr)), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
		java.net.URL sugoURL = new java.net.URL(sugoURLStr);
		myApplet.getAppletContext ().showDocument (sugoURL, "_blank");//$NON-NLS-1$
	} catch (java.net.MalformedURLException exc) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(exc), parentController.getGameName(), parentController.getPlayerName());
	}
}
/**
 * A 'Mivé válna?' menüpont kiválasztásakor hívódik meg
 */
public void displayResolvedSmallImg() {
	if (currentSmallImg != null) {
		try {
			currentSmallImg.setSzin(helpSzin.getResolvedSzin(parentController.getGameTable().getTableState()));
			currentSmallImg.setJelzes(helpJelzes.getResolvedJelzes(parentController.getGameTable().getTableState()));
		} catch (NullPointerException e) {
			currentSmallImg.clearSzin();
			currentSmallImg.clearJelzes();
		}
	}
}
public void displaySzinSugo() {
	String sugoURLStr = getHelpRootURL();
	if (helpSzin == null) {
		// index
		sugoURLStr += "c_index.html";//$NON-NLS-1$
	} else {
		sugoURLStr += helpSzin.getSzinSugoFileName();
	}

	try {
		szr.msg.Logger.log(new szr.msg.InfoLog(szr.shared.SubstituteStr.doIt("InfoSystem: Displaying szinSugo %hfile", "%hfile", sugoURLStr)), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
		java.net.URL sugoURL = new java.net.URL(sugoURLStr);
		myApplet.getAppletContext ().showDocument (sugoURL, "_blank");//$NON-NLS-1$
	} catch (java.net.MalformedURLException exc) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(exc), parentController.getGameName(), parentController.getPlayerName());
	}
}
public PopupMenu getCardHelpPopupMenu() {
	return cardHelpPopupMenu;
}
private String getHelpRootURL() {
	return myApplet.getBaseURL() + HELP_PATH;
}
public ClientController getParentController() {
	return parentController;
}
/**
 * Figyelmeztetõ üzenet
 */
public void openRejectDialog(String rejectInfoText, int pState) {
	rejectDlgHandler.showRejectDialog(rejectInfoText, pState);
}
public void setCurrentSmallImg(SmallImgComponent newSmallImg) {
	currentSmallImg = newSmallImg;
	resolveMenuItem.setEnabled(currentSmallImg != null && helpSzin != null && helpJelzes != null);
}
public void setExS(szr.shared.ExSType exS) {
	myExS = exS;
	setPSInfoText(parentController.getPlayState());	// update info text
}
public void setHelpJelzes(szr.card.OsJelzes newHelpJelzes) {
	helpJelzes = newHelpJelzes;
	if (helpJelzes != null) {
		jelzesSugoMenuItem.setLabel(szr.shared.SubstituteStr.doIt(resWidgetTexts.getString("JelzesSugoMenuItem_label%j"), "%j", helpJelzes.toString()));//$NON-NLS-2$ //$NON-NLS-1$
		jelzesSugoMenuItem.setEnabled (true);
		resolveMenuItem.setEnabled (helpSzin != null);
	} else {
		jelzesSugoMenuItem.setLabel(resWidgetTexts.getString("JelzesSugoMenuItem_label")); //$NON-NLS-1$
		jelzesSugoMenuItem.setEnabled (false);
		resolveMenuItem.setEnabled (false);
	}
}
public void setHelpSzin(OsSzin newHelpSzin) {
	helpSzin = newHelpSzin;
	if (helpSzin != null) {
		szinSugoMenuItem.setLabel(szr.shared.SubstituteStr.doIt(resWidgetTexts.getString("SzinSugoMenuItem_label%c"), "%c", helpSzin.toString()));//$NON-NLS-2$ //$NON-NLS-1$
		szinSugoMenuItem.setEnabled (true);
		resolveMenuItem.setEnabled (helpJelzes != null);
	} else {
		szinSugoMenuItem.setLabel(resWidgetTexts.getString("SzinSugoMenuItem_label")); //$NON-NLS-1$
		szinSugoMenuItem.setEnabled (false);
		resolveMenuItem.setEnabled (false);
	}
}
public void setInfoText(String newText) {
	setInfoText(newText, DEFAULT_COLOR);
}
public void setInfoText(String newText, Color newColor) {
	infoTextArea.setText(newText);
	infoTextArea.setBackground(newColor);
	szr.msg.Logger.log(new szr.msg.InfoLog(szr.shared.SubstituteStr.doIt("InfoSystem: %itxt", "%itxt", newText.replace('\n',' '))), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
}
public void setParentController(ClientController newParentController) {
	parentController = newParentController;
}
/**
 * InfóBox szövegének beállítása játékosállapot-kód alapján
 */
public void setPSInfoText(int state) {
	switch (state) {
		case ClientController.PS_ACTIVE:
			setInfoText(resInfoTexts.getString("Te_következel._nHa_most_pa"), ACTIVE_COLOR); //$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_IRANYKERO:
			setInfoText(resInfoTexts.getString("Válassz_egy_irányt_a_kérõl"), ACTIVE_COLOR); //$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_JELZESKERO:
			setInfoText(resInfoTexts.getString("Válassz_egy_jelzést_a_kérõ"), ACTIVE_COLOR); //$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_KEZDOLAP:
			setInfoText(resInfoTexts.getString("Nyomd_meg_a_'Passz'_gombot"), ACTIVE_COLOR); //$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_LAPKERO:
			setInfoText(SubstituteStr.doIt(SubstituteStr.doIt(resInfoTexts.getString("Válassz_egy_kártyát!_%mire"), //$NON-NLS-1$
					"%mire", (myUtSzam == null) ? "" : ((myUtSzam.im < 0) ? resInfoTexts.getString("(átadásra)") : resInfoTexts.getString("(eldobásra)"))), //$NON-NLS-4$ //$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
					"%ut", (myUtSzam == null) ? "" : myUtSzam.toLongString()),//$NON-NLS-2$//$NON-NLS-1$
				ACTIVE_COLOR);
			break;
		case ClientController.PS_ACTIVE_GYENGE_LAPKERO:
			setInfoText(resInfoTexts.getString("Válassz_egy_kártyát_vagy_'"), ACTIVE_COLOR); //$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_SOROZHAT:
			setInfoText(SubstituteStr.doIt(resInfoTexts.getString("Azonos_jelzésû_lapokat_még"), //$NON-NLS-1$
					"%(ut)", (myUtSzam == null) ? "" : "(" + myUtSzam.toLongString() + ")"),//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
				ACTIVE_COLOR);
			break;
		case ClientController.PS_ACTIVE_LILA:
			setInfoText(SubstituteStr.doIt(SubstituteStr.doIt(resInfoTexts.getString("Több_lapot_már_nem_rakhats"), //$NON-NLS-1$
					"%(ut)", (myUtSzam == null) ? "" : "(" + myUtSzam.toLongString() + ")"),//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
					"%(exs)", (myExS == null) ? "" : "(" + myExS.toString() + ")"),//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
				ACTIVE_COLOR);
			break;
		case ClientController.PS_ACTIVE_SZINKERO:
			setInfoText(resInfoTexts.getString("Válassz_egy_színt_a_kérõli"), ACTIVE_COLOR); //$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_VETOBLOCKED:
			setInfoText(resInfoTexts.getString("Várj..._most_a_többiek_vét"), ACTIVE_COLOR); //$NON-NLS-1$
			break;
		case ClientController.PS_ACTIVE_UT:
			setInfoText(SubstituteStr.doIt(resInfoTexts.getString("Te_következel._Kötelezõ_pa"), //$NON-NLS-1$
					"%ut", (myUtSzam == null) ? resInfoTexts.getString("Nem_tudni_mi,_mert_program") : myUtSzam.toLongString()), //$NON-NLS-2$//$NON-NLS-1$
				ACTIVE_COLOR);
			break;
		case ClientController.PS_ACTIVE_EXS:
			setInfoText(SubstituteStr.doIt(resInfoTexts.getString("Te_következel,_extra_soroz"), //$NON-NLS-1$
					"%exs", (myExS == null) ? resInfoTexts.getString("Nem_tudni_mi,_mert_program") : myExS.toString()), //$NON-NLS-2$//$NON-NLS-1$
				ACTIVE_COLOR);
			break;
		case ClientController.PS_ACTIVE_EXS_SOROZHAT:
			setInfoText(SubstituteStr.doIt(resInfoTexts.getString("Extra_sorozási_lehetõséged"), //$NON-NLS-1$
					"%exs", (myExS == null) ? resInfoTexts.getString("Nem_tudni_mi,_mert_program") : myExS.toString()), //$NON-NLS-2$//$NON-NLS-1$
				ACTIVE_COLOR);
			break;
		case ClientController.PS_DISCONNECTED:
			setInfoText(resInfoTexts.getString("A_hálózati_kapcsolat_megsz")); //$NON-NLS-1$
			break;
		case ClientController.PS_INACTIVE:
			setInfoText(SubstituteStr.doIt(SubstituteStr.doIt(resInfoTexts.getString("Most_nem_te_következel._n%"), //$NON-NLS-1$
					"%(ut)", (myUtSzam == null) ? "" : "(" + myUtSzam.toLongString() + ")"),//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
					"%(exs)", (myExS == null) ? "" : "(" + myExS.toString() + ")"),//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
				INACTIVE_COLOR);
			break;
		case ClientController.PS_INACTIVE_BESZURHAT:
			setInfoText(SubstituteStr.doIt(SubstituteStr.doIt(resInfoTexts.getString("Most_nem_te_jössz,_de_eset"), //$NON-NLS-1$
					"%(ut)", (myUtSzam == null) ? "" : "(" + myUtSzam.toLongString() + ")"),//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
					"%(exs)", (myExS == null) ? "" : "(" + myExS.toString() + ")"),//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
				INACTIVE_COLOR);
			break;
		case ClientController.PS_INACTIVE_VETOZHAT:
			setInfoText(SubstituteStr.doIt(SubstituteStr.doIt(resInfoTexts.getString("Most_nem_te_jössz,_de_vétó"), //$NON-NLS-1$
					"%(ut)", (myUtSzam == null) ? "" : "(" + myUtSzam.toLongString() + ")"),//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
					"%(exs)", (myExS == null) ? "" : "(" + myExS.toString() + ")"),//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
				INACTIVE_COLOR);
			break;
		case ClientController.PS_JOINED:
			setInfoText(resInfoTexts.getString("Csatlakoztál_a_játékhoz._n"), INACTIVE_COLOR); //$NON-NLS-1$
			break;
		case ClientController.PS_JOINED_FIRST:
			setInfoText(resInfoTexts.getString("Te_vagy_az_osztó_játékos._"), ACTIVE_COLOR); //$NON-NLS-1$
			break;
		case ClientController.PS_NOTJOINED:
			setInfoText(resInfoTexts.getString("Csatlakozz_egy_játékhoz_va")); //$NON-NLS-1$
			break;
		case ClientController.PS_NYERT:
			setInfoText(resInfoTexts.getString("Elfogytak_a_kártyáid._nMeg"), ACTIVE_COLOR); //$NON-NLS-1$
			break;
		case ClientController.PS_VESZTETT:
			setInfoText(resInfoTexts.getString("Sajnos_nem_te_nyertél._n"), INACTIVE_COLOR); //$NON-NLS-1$
			break;
		case ClientController.PS_EGYEDULMARADT:
			setInfoText(resInfoTexts.getString("Vége_a_játéknak,_mert_rajt"), ACTIVE_COLOR); //$NON-NLS-1$
			break;
		default:
			szr.msg.Logger.log(new szr.msg.ErrStrLog(szr.shared.SubstituteStr.doIt("InfoSystem error: invalid playState constant %ps at setPSInfoText()", "%ps", Integer.toString(state))), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
			break;
	}
}
public void setUtSzam(szr.shared.UtSzam ur) {
	myUtSzam = ur;
	setPSInfoText(parentController.getPlayState());	// update info text
}
}
