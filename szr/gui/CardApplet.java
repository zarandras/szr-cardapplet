package szr.gui;

import java.net.*;
import java.io.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import szr.maincli.*;
/**
 * A kliens Kártyaasztala
 * Creation date: (21/01/02 14:40:08)
 */
public class CardApplet extends Applet {
	private static java.util.ResourceBundle resWidgetTexts = java.util.ResourceBundle.getBundle("szr.shared.WidgetTexts");  //$NON-NLS-1$
	private String parameterInfo[][] = {
	 	 {"BaseURL",		"url",		"root of resources"},//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
	 	 {"ServerHost",		"inetaddr",	"server host"},//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
	 	 {"ServerTCPport",	"int",		"server TCP port"}//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
	  };
	protected static final java.lang.String baseURL = // default root path, ha nincs paraméter
		"http://people.inf.elte.hu/mola/kartya/";//$NON-NLS-1$
	private Panel ivjPanel2 = null;
	private Label ivjInfoLabel = null;
	private TextArea ivjInfoTextArea = null;
	private Scrollbar ivjLerakottScrollbar = null;
	private Label ivjAkcioLabel = null;
	private Label ivjAktSzinJelLabel1 = null;
	private Label ivjAktSzinJelLabel2 = null;
	private Panel ivjAktSzinJelPanel = null;
	private Panel ivjAsztalPanel = null;
	private Label ivjAzazLabel = null;
	private Label ivjChatLabel = null;
	private TextArea ivjChatTextArea = null;
	private Label ivjCopyrightLabel = null;
	private Label ivjEmailLabel = null;
	private TextField ivjEmailTextField = null;
	private Label ivjIranyLabel = null;
	private Panel ivjJatekBeallPanel = null;
	private Button ivjJatekCsatlButton = null;
	private Button ivjJatekKiszallButton = null;
	private Label ivjJatekLabel = null;
	private Label ivjJatekosLabel = null;
	private List ivjJatekosList = null;
	private Panel ivjJatekosPanel = null;
	private Panel ivjJatekPanel = null;
	private TextField ivjJatekTextField = null;
	private Button ivjJatekUjButton = null;
	private Label ivjJellemzokLabel = null;
	private Button ivjJelszoKuldButton = null;
	private Label ivjJelszoLabel = null;
	private TextField ivjJelszoTextField = null;
	private Panel ivjJelzeslapPanel = null;
	private Choice ivjKertJelChoice = null;
	private Choice ivjKertSzinChoice = null;
	private Button ivjLerakButton = null;
	private Label ivjLerakottLabel = null;
	private Panel ivjLerakottPanel = null;
	private Label ivjMeghirdetettLabel = null;
	private List ivjMeghirdetettList = null;
	private Button ivjMondButton = null;
	private Label ivjNevLabel = null;
	private TextField ivjNevTextField = null;
	private Choice ivjPakliJellemzoChoice = null;
	private Label ivjPakliJellemzoLabel = null;
	private Label ivjPakliLabel = null;
	private Panel ivjPakliPanel = null;
	private Button ivjPasszButton = null;
	private Label ivjSajatLapLabel = null;
	private Panel ivjSajatLapPanel = null;
	private Panel ivjSajatLapPanel1 = null;
	private Panel ivjSajatLapPanel2 = null;
	private Panel ivjSajatLapPanel3 = null;
	private Panel ivjSajatLapPanel4 = null;
	private Panel ivjSajatLapPanel5 = null;
	private Panel ivjSajatLapPanel6 = null;
	private Panel ivjSajatLapPanel7 = null;
	private Panel ivjSajatLapPanel8 = null;
	private Scrollbar ivjSajatLapScrollbar = null;
	private Label ivjSmileyLabel = null;
	private TextField ivjSmileyTextField = null;
	private Label ivjUzenetLabel = null;
	private TextField ivjUzenetTextField = null;
	private ClientController clientController = null;
	private Button ivjCardHelpButton = null;
	private Button ivjGameHelpButton = null;
	private szr.gui.CardComponent[] ccSajatLapok = new CardComponent[8];
	private Label ivjHelpLabel1 = null;
	private Label ivjHelpLabel2 = null;
	private Panel ivjKertJelPanel = null;
	private Panel ivjKertSzinPanel = null;
	private Label ivjSajatSzinJelLabel = null;
	private Panel ivjSajatSzinJelPanel = null;
	private SmallImgComponent ccSajatSzinJel = null;
	private SmallImgComponent ccAktSzinJel = null;
	private SmallImgComponent ccJelzesSzinJel = null;
	private Panel ivjJelzesSzinJelPanel = null;
	private SmallImgComponent ccKertSzin = null;
	private SmallImgComponent ccKertJel = null;
	private CardComponent ccLerakottLap = null;
	private CardComponent ccPakli = null;
	private CardComponent ccJelzeslap = null;
	private CardComponent jelzeslap = null;
	private SmallImgComponent jelzesSzinJel = null;
	private Button ivjJatekosListaButton = null;
	private Panel ivjContentsPane = null;
	private MenuItem ivjJelzesSugoMenuItem = null;
	private Dialog ivjRejectDialog = null;
	private Button ivjRejectOKButton = null;
	private TextArea ivjRejectTextArea = null;
	private MenuItem ivjSzinSugoMenuItem = null;
	private Button ivjRejectGameHelpButton = null;
	private Label ivjRejectHelpLabel1 = null;
	private Label ivjRejectHelpLabel2 = null;
	private Label ivjRejectInfoLabel = null;
	private PopupMenu ivjCardHelpPopupMenu = null;
	private Label ivjAktSzinJelLabel3 = null;
	private Label ivjJelzeslapLabel = null;
	private Choice ivjKertIranyChoice = null;
	private Label ivjKertJelLabel1 = null;
	private Label ivjKertJelLabel2 = null;
	private Label ivjKertSzinLabel1 = null;
	private Label ivjKertSzinLabel2 = null;
	private Label ivjFelLeLabel = null;
	private MenuItem ivjMenuSeparator1 = null;
	private Choice ivjOsztasJellemzoChoice = null;
	private Label ivjOsztasJellemzoLabel = null;
	private MenuItem ivjResolveMenuItem = null;
	private java.lang.String myBaseURL = null;
	private Label ivjKertIranyLabel = null;
	private Button ivjPakliDescrButton = null;
	private MenuItem ivjAppletHelpMenuItem = null;
	private PopupMenu ivjGameHelpPopupMenu = null;
	private MenuItem ivjWhatToDoMenuItem = null;
public ClientMsgHandler createMyMsgHandler() {
	return new RClientMsgHandler(getParameter("ServerHost"), getParameter("ServerTCPport"));//$NON-NLS-2$//$NON-NLS-1$
}
/**
 * Cleans up whatever resources are being held. If the applet is active
 * it is stopped.
 * 
 * @see #init
 * @see #start
 * @see #stop
 */
public void destroy() {
	clientController.endProcess();
	super.destroy();
}
/**
 * Return the Label811121 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getAkcioLabel() {
	if (ivjAkcioLabel == null) {
		try {
			ivjAkcioLabel = new java.awt.Label();
			ivjAkcioLabel.setName("AkcioLabel");
			ivjAkcioLabel.setText(resWidgetTexts.getString("AkcioLabel_text"));
			ivjAkcioLabel.setBackground(new java.awt.Color(184,0,119));
			ivjAkcioLabel.setForeground(java.awt.Color.cyan);
			ivjAkcioLabel.setFont(new java.awt.Font("dialog", 1, 10));
			ivjAkcioLabel.setAlignment(java.awt.Label.LEFT);
			ivjAkcioLabel.setBounds(3, 1, 67, 18);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjAkcioLabel;
}
/**
 * Return the Label81 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getAktSzinJelLabel1() {
	if (ivjAktSzinJelLabel1 == null) {
		try {
			ivjAktSzinJelLabel1 = new java.awt.Label();
			ivjAktSzinJelLabel1.setName("AktSzinJelLabel1");
			ivjAktSzinJelLabel1.setText("? Aktuális");
			ivjAktSzinJelLabel1.setBackground(new java.awt.Color(0,90,0));
			ivjAktSzinJelLabel1.setForeground(java.awt.Color.yellow);
			ivjAktSzinJelLabel1.setFont(new java.awt.Font("dialog", 0, 10));
			ivjAktSzinJelLabel1.setAlignment(java.awt.Label.RIGHT);
			ivjAktSzinJelLabel1.setBounds(98, 127, 57, 15);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjAktSzinJelLabel1;
}
/**
 * Return the Label812 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getAktSzinJelLabel2() {
	if (ivjAktSzinJelLabel2 == null) {
		try {
			ivjAktSzinJelLabel2 = new java.awt.Label();
			ivjAktSzinJelLabel2.setName("AktSzinJelLabel2");
			ivjAktSzinJelLabel2.setText(resWidgetTexts.getString("AktSzinJelLabel2_text"));
			ivjAktSzinJelLabel2.setBackground(new java.awt.Color(0,90,0));
			ivjAktSzinJelLabel2.setForeground(java.awt.Color.yellow);
			ivjAktSzinJelLabel2.setFont(new java.awt.Font("dialog", 0, 10));
			ivjAktSzinJelLabel2.setAlignment(java.awt.Label.RIGHT);
			ivjAktSzinJelLabel2.setBounds(98, 140, 57, 15);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjAktSzinJelLabel2;
}
/**
 * Return the AktSzinJelLabel3 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getAktSzinJelLabel3() {
	if (ivjAktSzinJelLabel3 == null) {
		try {
			ivjAktSzinJelLabel3 = new java.awt.Label();
			ivjAktSzinJelLabel3.setName("AktSzinJelLabel3");
			ivjAktSzinJelLabel3.setText(resWidgetTexts.getString("AktSzinJelLabel3_text"));
			ivjAktSzinJelLabel3.setBackground(new java.awt.Color(0,90,0));
			ivjAktSzinJelLabel3.setForeground(java.awt.Color.yellow);
			ivjAktSzinJelLabel3.setFont(new java.awt.Font("dialog", 0, 10));
			ivjAktSzinJelLabel3.setAlignment(java.awt.Label.RIGHT);
			ivjAktSzinJelLabel3.setBounds(98, 154, 57, 15);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjAktSzinJelLabel3;
}
/**
 * Return the Panel6 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getAktSzinJelPanel() {
	if (ivjAktSzinJelPanel == null) {
		try {
			ivjAktSzinJelPanel = new java.awt.Panel();
			ivjAktSzinJelPanel.setName("AktSzinJelPanel");
			ivjAktSzinJelPanel.setLayout(null);
			ivjAktSzinJelPanel.setBackground(new java.awt.Color(198,255,220));
			ivjAktSzinJelPanel.setBounds(157, 131, 35, 35);
			ivjAktSzinJelPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			// user code begin {1}
			SmallImgComponent cc = new SmallImgComponent(this);
			getAktSzinJelPanel ().add (cc);
			cc.setBounds(0, 0, getAktSzinJelPanel ().getSize ().width, getAktSzinJelPanel ().getSize ().height);
			ccAktSzinJel = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjAktSzinJelPanel;
}
/**
 * Return the AppletHelpMenuItem property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.MenuItem getAppletHelpMenuItem() {
	if (ivjAppletHelpMenuItem == null) {
		try {
			ivjAppletHelpMenuItem = new java.awt.MenuItem();
			ivjAppletHelpMenuItem.setLabel("Az asztal használata");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjAppletHelpMenuItem;
}
/**
 * Returns information about this applet.
 * @return a string of information about this applet
 */
public String getAppletInfo() {
	return "CardApplet - Kártyaasztal\n" + //$NON-NLS-1$
		"\n" + //$NON-NLS-1$
		"Turistakártya kliens.\n" + //$NON-NLS-1$
		"@author: Molnár András\n" + //$NON-NLS-1$
		"";//$NON-NLS-1$
}
/**
 * Return the Panel5 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getAsztalPanel() {
	if (ivjAsztalPanel == null) {
		try {
			ivjAsztalPanel = new java.awt.Panel();
			ivjAsztalPanel.setName("AsztalPanel");
			ivjAsztalPanel.setLayout(null);
			ivjAsztalPanel.setBackground(new java.awt.Color(0,90,0));
			ivjAsztalPanel.setBounds(9, 39, 476, 233);
			getAsztalPanel().add(getKertJelLabel1(), getKertJelLabel1().getName());
			getAsztalPanel().add(getKertSzinPanel(), getKertSzinPanel().getName());
			getAsztalPanel().add(getKertJelPanel(), getKertJelPanel().getName());
			getAsztalPanel().add(getKertSzinChoice(), getKertSzinChoice().getName());
			getAsztalPanel().add(getKertJelChoice(), getKertJelChoice().getName());
			getAsztalPanel().add(getKertJelLabel2(), getKertJelLabel2().getName());
			getAsztalPanel().add(getKertSzinLabel1(), getKertSzinLabel1().getName());
			getAsztalPanel().add(getKertSzinLabel2(), getKertSzinLabel2().getName());
			getAsztalPanel().add(getPakliLabel(), getPakliLabel().getName());
			getAsztalPanel().add(getPakliPanel(), getPakliPanel().getName());
			getAsztalPanel().add(getJelzeslapPanel(), getJelzeslapPanel().getName());
			getAsztalPanel().add(getAzazLabel(), getAzazLabel().getName());
			getAsztalPanel().add(getJelzesSzinJelPanel(), getJelzesSzinJelPanel().getName());
			getAsztalPanel().add(getLerakottLabel(), getLerakottLabel().getName());
			getAsztalPanel().add(getLerakottScrollbar(), getLerakottScrollbar().getName());
			getAsztalPanel().add(getLerakottPanel(), getLerakottPanel().getName());
			getAsztalPanel().add(getJelzeslapLabel(), getJelzeslapLabel().getName());
			getAsztalPanel().add(getAktSzinJelPanel(), getAktSzinJelPanel().getName());
			getAsztalPanel().add(getAktSzinJelLabel3(), getAktSzinJelLabel3().getName());
			getAsztalPanel().add(getAktSzinJelLabel2(), getAktSzinJelLabel2().getName());
			getAsztalPanel().add(getAktSzinJelLabel1(), getAktSzinJelLabel1().getName());
			getAsztalPanel().add(getJatekosList(), getJatekosList().getName());
			getAsztalPanel().add(getJatekosLabel(), getJatekosLabel().getName());
			getAsztalPanel().add(getFelLeLabel(), getFelLeLabel().getName());
			getAsztalPanel().add(getSmileyLabel(), getSmileyLabel().getName());
			getAsztalPanel().add(getSmileyTextField(), getSmileyTextField().getName());
			getAsztalPanel().add(getCardHelpButton(), getCardHelpButton().getName());
			getAsztalPanel().add(getGameHelpButton(), getGameHelpButton().getName());
			getAsztalPanel().add(getHelpLabel1(), getHelpLabel1().getName());
			getAsztalPanel().add(getHelpLabel2(), getHelpLabel2().getName());
			getAsztalPanel().add(getKertIranyChoice(), getKertIranyChoice().getName());
			getAsztalPanel().add(getKertIranyLabel(), getKertIranyLabel().getName());
			getAsztalPanel().add(getIranyLabel(), getIranyLabel().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjAsztalPanel;
}
/**
 * Return the Label811 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getAzazLabel() {
	if (ivjAzazLabel == null) {
		try {
			ivjAzazLabel = new java.awt.Label();
			ivjAzazLabel.setName("AzazLabel");
			ivjAzazLabel.setText("? azaz:");
			ivjAzazLabel.setBackground(new java.awt.Color(0,90,0));
			ivjAzazLabel.setForeground(java.awt.Color.yellow);
			ivjAzazLabel.setFont(new java.awt.Font("dialog", 0, 10));
			ivjAzazLabel.setAlignment(java.awt.Label.CENTER);
			ivjAzazLabel.setBounds(0, 114, 47, 16);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjAzazLabel;
}
public java.lang.String getBaseURL() {
	if (myBaseURL == null) {
		String s = getParameter("BaseURL");//$NON-NLS-1$
		myBaseURL = ((s != null) ? s : baseURL);
	}
	return myBaseURL;
}
/**
 * Return the CardHelpButton property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Button getCardHelpButton() {
	if (ivjCardHelpButton == null) {
		try {
			ivjCardHelpButton = new java.awt.Button();
			ivjCardHelpButton.setName("CardHelpButton");
			ivjCardHelpButton.setFont(new java.awt.Font("dialog", 1, 10));
			ivjCardHelpButton.setBackground(new java.awt.Color(0,128,112));
			ivjCardHelpButton.setBounds(127, 4, 76, 23);
			ivjCardHelpButton.setForeground(java.awt.Color.white);
			ivjCardHelpButton.setLabel(resWidgetTexts.getString("CardHelpButton_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjCardHelpButton;
}
/**
 * Return the CardPopupMenu property value.
 * @return java.awt.PopupMenu
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.PopupMenu getCardHelpPopupMenu() {
	if (ivjCardHelpPopupMenu == null) {
		try {
			ivjCardHelpPopupMenu = new java.awt.PopupMenu();
			ivjCardHelpPopupMenu.setLabel(resWidgetTexts.getString("CardHelpPopupMenu_label"));
			ivjCardHelpPopupMenu.add(getSzinSugoMenuItem());
			ivjCardHelpPopupMenu.add(getJelzesSugoMenuItem());
			ivjCardHelpPopupMenu.add(getMenuSeparator1());
			ivjCardHelpPopupMenu.add(getResolveMenuItem());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjCardHelpPopupMenu;
}
/**
 * Return the Label1 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getChatLabel() {
	if (ivjChatLabel == null) {
		try {
			ivjChatLabel = new java.awt.Label();
			ivjChatLabel.setName("ChatLabel");
			ivjChatLabel.setFont(new java.awt.Font("dialog", 1, 10));
			ivjChatLabel.setText(resWidgetTexts.getString("ChatLabel_text"));
			ivjChatLabel.setBounds(4, 6, 65, 15);
			ivjChatLabel.setForeground(java.awt.Color.white);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjChatLabel;
}
/**
 * Return the TextArea1 property value.
 * @return java.awt.TextArea
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.TextArea getChatTextArea() {
	if (ivjChatTextArea == null) {
		try {
			ivjChatTextArea = new java.awt.TextArea();
			ivjChatTextArea.setName("ChatTextArea");
			ivjChatTextArea.setText("");
			ivjChatTextArea.setBackground(java.awt.Color.darkGray);
			ivjChatTextArea.setForeground(java.awt.Color.white);
			ivjChatTextArea.setFont(new java.awt.Font("dialog", 0, 10));
			ivjChatTextArea.setBounds(4, 25, 263, 137);
			ivjChatTextArea.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjChatTextArea;
}
protected szr.maincli.ClientController getClientController() {
	return clientController;
}
/**
 * Return the ContentsPane property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getContentsPane() {
	if (ivjContentsPane == null) {
		try {
			ivjContentsPane = new java.awt.Panel();
			ivjContentsPane.setName("ContentsPane");
			ivjContentsPane.setLayout(null);
			ivjContentsPane.setBounds(123, 16, 0, 0);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjContentsPane;
}
/**
 * Return the Label11 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getCopyrightLabel() {
	if (ivjCopyrightLabel == null) {
		try {
			ivjCopyrightLabel = new java.awt.Label();
			ivjCopyrightLabel.setName("CopyrightLabel");
			ivjCopyrightLabel.setFont(new java.awt.Font("dialog", 2, 10));
			ivjCopyrightLabel.setText("© Molnár András, 2002.");
			ivjCopyrightLabel.setBounds(9, 405, 223, 18);
			ivjCopyrightLabel.setForeground(java.awt.Color.yellow);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjCopyrightLabel;
}
/**
 * Return the Label311 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getEmailLabel() {
	if (ivjEmailLabel == null) {
		try {
			ivjEmailLabel = new java.awt.Label();
			ivjEmailLabel.setName("EmailLabel");
			ivjEmailLabel.setFont(new java.awt.Font("dialog", 1, 10));
			ivjEmailLabel.setText(resWidgetTexts.getString("EmailLabel_text"));
			ivjEmailLabel.setBounds(99, -1, 119, 17);
			ivjEmailLabel.setForeground(java.awt.Color.yellow);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjEmailLabel;
}
/**
 * Return the TextField5 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.TextField getEmailTextField() {
	if (ivjEmailTextField == null) {
		try {
			ivjEmailTextField = new java.awt.TextField();
			ivjEmailTextField.setName("EmailTextField");
			ivjEmailTextField.setFont(new java.awt.Font("dialog", 0, 12));
			ivjEmailTextField.setText("");
			ivjEmailTextField.setBackground(java.awt.Color.orange);
			ivjEmailTextField.setBounds(99, 15, 170, 27);
			ivjEmailTextField.setForeground(java.awt.Color.blue);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjEmailTextField;
}
/**
 * Return the LeFelLabel property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getFelLeLabel() {
	if (ivjFelLeLabel == null) {
		try {
			ivjFelLeLabel = new java.awt.Label();
			ivjFelLeLabel.setName("FelLeLabel");
			ivjFelLeLabel.setText(resWidgetTexts.getString("Le"));
			ivjFelLeLabel.setBackground(new java.awt.Color(0,90,0));
			ivjFelLeLabel.setForeground(java.awt.Color.white);
			ivjFelLeLabel.setFont(new java.awt.Font("dialog", 1, 14));
			ivjFelLeLabel.setAlignment(java.awt.Label.LEFT);
			ivjFelLeLabel.setBounds(311, 199, 32, 32);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjFelLeLabel;
}
/**
 * Return the GameHelpButton property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Button getGameHelpButton() {
	if (ivjGameHelpButton == null) {
		try {
			ivjGameHelpButton = new java.awt.Button();
			ivjGameHelpButton.setName("GameHelpButton");
			ivjGameHelpButton.setFont(new java.awt.Font("dialog", 1, 10));
			ivjGameHelpButton.setBackground(new java.awt.Color(0,128,112));
			ivjGameHelpButton.setBounds(45, 4, 76, 23);
			ivjGameHelpButton.setForeground(java.awt.Color.white);
			ivjGameHelpButton.setLabel(resWidgetTexts.getString("GameHelpButton_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjGameHelpButton;
}
/**
 * Return the GameHelpPopupMenu property value.
 * @return java.awt.PopupMenu
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.PopupMenu getGameHelpPopupMenu() {
	if (ivjGameHelpPopupMenu == null) {
		try {
			ivjGameHelpPopupMenu = new java.awt.PopupMenu();
			ivjGameHelpPopupMenu.setLabel(resWidgetTexts.getString("GameHelpPopupMenu_Label"));
			ivjGameHelpPopupMenu.add(getWhatToDoMenuItem());
			ivjGameHelpPopupMenu.add(getAppletHelpMenuItem());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjGameHelpPopupMenu;
}
/**
 * Return the HelpLabel1 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getHelpLabel1() {
	if (ivjHelpLabel1 == null) {
		try {
			ivjHelpLabel1 = new java.awt.Label();
			ivjHelpLabel1.setName("HelpLabel1");
			ivjHelpLabel1.setFont(new java.awt.Font("dialog", 1, 10));
			ivjHelpLabel1.setAlignment(java.awt.Label.RIGHT);
			ivjHelpLabel1.setText(resWidgetTexts.getString("HelpLabel1_text"));
			ivjHelpLabel1.setBounds(-2, -1, 45, 16);
			ivjHelpLabel1.setForeground(java.awt.Color.white);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjHelpLabel1;
}
/**
 * Return the Label8111211 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getHelpLabel2() {
	if (ivjHelpLabel2 == null) {
		try {
			ivjHelpLabel2 = new java.awt.Label();
			ivjHelpLabel2.setName("HelpLabel2");
			ivjHelpLabel2.setFont(new java.awt.Font("dialog", 1, 10));
			ivjHelpLabel2.setAlignment(java.awt.Label.RIGHT);
			ivjHelpLabel2.setText(resWidgetTexts.getString("HelpLabel2_text"));
			ivjHelpLabel2.setBounds(-2, 13, 45, 16);
			ivjHelpLabel2.setForeground(java.awt.Color.white);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjHelpLabel2;
}
/**
 * Return the Label6 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getInfoLabel() {
	if (ivjInfoLabel == null) {
		try {
			ivjInfoLabel = new java.awt.Label();
			ivjInfoLabel.setName("InfoLabel");
			ivjInfoLabel.setFont(new java.awt.Font("dialog", 1, 14));
			ivjInfoLabel.setAlignment(java.awt.Label.RIGHT);
			ivjInfoLabel.setText(resWidgetTexts.getString("InfoLabel_text"));
			ivjInfoLabel.setBounds(7, 4, 45, 31);
			ivjInfoLabel.setForeground(java.awt.Color.white);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjInfoLabel;
}
/**
 * Return the TextArea2 property value.
 * @return java.awt.TextArea
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
protected java.awt.TextArea getInfoTextArea() {
	if (ivjInfoTextArea == null) {
		try {
			ivjInfoTextArea = new java.awt.TextArea("", 0, 0, java.awt.TextArea.SCROLLBARS_VERTICAL_ONLY);
			ivjInfoTextArea.setName("InfoTextArea");
			ivjInfoTextArea.setText("");
			ivjInfoTextArea.setBackground(java.awt.Color.white);
			ivjInfoTextArea.setFont(new java.awt.Font("dialog", 1, 10));
			ivjInfoTextArea.setBounds(54, 3, 428, 31);
			ivjInfoTextArea.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjInfoTextArea;
}
/**
 * Return the Label10 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getIranyLabel() {
	if (ivjIranyLabel == null) {
		try {
			ivjIranyLabel = new java.awt.Label();
			ivjIranyLabel.setName("IranyLabel");
			ivjIranyLabel.setText(resWidgetTexts.getString("IranyLabel_text"));
			ivjIranyLabel.setBackground(new java.awt.Color(0,90,0));
			ivjIranyLabel.setForeground(java.awt.Color.white);
			ivjIranyLabel.setFont(new java.awt.Font("dialog", 0, 12));
			ivjIranyLabel.setAlignment(java.awt.Label.RIGHT);
			ivjIranyLabel.setBounds(257, 199, 47, 32);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjIranyLabel;
}
/**
 * Return the Panel11 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getJatekBeallPanel() {
	if (ivjJatekBeallPanel == null) {
		try {
			ivjJatekBeallPanel = new java.awt.Panel();
			ivjJatekBeallPanel.setName("JatekBeallPanel");
			ivjJatekBeallPanel.setLayout(null);
			ivjJatekBeallPanel.setBackground(new java.awt.Color(0,128,175));
			ivjJatekBeallPanel.setBounds(499, 87, 279, 122);
			getJatekBeallPanel().add(getPakliJellemzoChoice(), getPakliJellemzoChoice().getName());
			getJatekBeallPanel().add(getOsztasJellemzoChoice(), getOsztasJellemzoChoice().getName());
			getJatekBeallPanel().add(getMeghirdetettList(), getMeghirdetettList().getName());
			getJatekBeallPanel().add(getJatekTextField(), getJatekTextField().getName());
			getJatekBeallPanel().add(getJatekUjButton(), getJatekUjButton().getName());
			getJatekBeallPanel().add(getJatekCsatlButton(), getJatekCsatlButton().getName());
			getJatekBeallPanel().add(getJatekLabel(), getJatekLabel().getName());
			getJatekBeallPanel().add(getMeghirdetettLabel(), getMeghirdetettLabel().getName());
			getJatekBeallPanel().add(getJellemzokLabel(), getJellemzokLabel().getName());
			getJatekBeallPanel().add(getOsztasJellemzoLabel(), getOsztasJellemzoLabel().getName());
			getJatekBeallPanel().add(getPakliJellemzoLabel(), getPakliJellemzoLabel().getName());
			getJatekBeallPanel().add(getJatekKiszallButton(), getJatekKiszallButton().getName());
			getJatekBeallPanel().add(getJatekosListaButton(), getJatekosListaButton().getName());
			getJatekBeallPanel().add(getPakliDescrButton(), getPakliDescrButton().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJatekBeallPanel;
}
/**
 * Return the Button12 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Button getJatekCsatlButton() {
	if (ivjJatekCsatlButton == null) {
		try {
			ivjJatekCsatlButton = new java.awt.Button();
			ivjJatekCsatlButton.setName("JatekCsatlButton");
			ivjJatekCsatlButton.setFont(new java.awt.Font("dialog", 0, 10));
			ivjJatekCsatlButton.setBounds(4, 70, 86, 23);
			ivjJatekCsatlButton.setLabel(resWidgetTexts.getString("JatekCsatlButton_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJatekCsatlButton;
}
/**
 * Return the Button1211 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Button getJatekKiszallButton() {
	if (ivjJatekKiszallButton == null) {
		try {
			ivjJatekKiszallButton = new java.awt.Button();
			ivjJatekKiszallButton.setName("JatekKiszallButton");
			ivjJatekKiszallButton.setFont(new java.awt.Font("dialog", 0, 10));
			ivjJatekKiszallButton.setBounds(4, 93, 86, 23);
			ivjJatekKiszallButton.setLabel(resWidgetTexts.getString("JatekKiszallButton_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJatekKiszallButton;
}
/**
 * Return the Label3 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getJatekLabel() {
	if (ivjJatekLabel == null) {
		try {
			ivjJatekLabel = new java.awt.Label();
			ivjJatekLabel.setName("JatekLabel");
			ivjJatekLabel.setFont(new java.awt.Font("dialog", 1, 10));
			ivjJatekLabel.setText(resWidgetTexts.getString("JatekLabel_text"));
			ivjJatekLabel.setBounds(4, 1, 52, 19);
			ivjJatekLabel.setForeground(java.awt.Color.yellow);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJatekLabel;
}
/**
 * Return the Label7 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getJatekosLabel() {
	if (ivjJatekosLabel == null) {
		try {
			ivjJatekosLabel = new java.awt.Label();
			ivjJatekosLabel.setName("JatekosLabel");
			ivjJatekosLabel.setFont(new java.awt.Font("dialog", 1, 10));
			ivjJatekosLabel.setText(resWidgetTexts.getString("JatekosLabel_text"));
			ivjJatekosLabel.setBackground(new java.awt.Color(0,90,0));
			ivjJatekosLabel.setBounds(213, -1, 105, 21);
			ivjJatekosLabel.setForeground(java.awt.Color.white);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJatekosLabel;
}
/**
 * Return the List1 property value.
 * @return java.awt.List
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.List getJatekosList() {
	if (ivjJatekosList == null) {
		try {
			ivjJatekosList = new java.awt.List();
			ivjJatekosList.setName("JatekosList");
			ivjJatekosList.setBackground(new java.awt.Color(0,128,112));
			ivjJatekosList.setBounds(213, 19, 258, 172);
			ivjJatekosList.setForeground(java.awt.Color.white);
			ivjJatekosList.setEnabled(true);
			ivjJatekosList.setMultipleMode(true);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJatekosList;
}
/**
 * Return the JatekosListaButton property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Button getJatekosListaButton() {
	if (ivjJatekosListaButton == null) {
		try {
			ivjJatekosListaButton = new java.awt.Button();
			ivjJatekosListaButton.setName("JatekosListaButton");
			ivjJatekosListaButton.setFont(new java.awt.Font("dialog", 0, 10));
			ivjJatekosListaButton.setBounds(94, 100, 178, 17);
			ivjJatekosListaButton.setActionCommand(resWidgetTexts.getString("JatekosListaButton_label"));
			ivjJatekosListaButton.setLabel(resWidgetTexts.getString("JatekosListaButton_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJatekosListaButton;
}
/**
 * Return the Panel1 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
protected java.awt.Panel getJatekosPanel() {
	if (ivjJatekosPanel == null) {
		try {
			ivjJatekosPanel = new java.awt.Panel();
			ivjJatekosPanel.setName("JatekosPanel");
			ivjJatekosPanel.setLayout(null);
			ivjJatekosPanel.setBackground(new java.awt.Color(223,128,48));
			ivjJatekosPanel.setBounds(499, 4, 279, 82);
			getJatekosPanel().add(getNevTextField(), getNevTextField().getName());
			getJatekosPanel().add(getEmailTextField(), getEmailTextField().getName());
			getJatekosPanel().add(getNevLabel(), getNevLabel().getName());
			getJatekosPanel().add(getEmailLabel(), getEmailLabel().getName());
			getJatekosPanel().add(getJelszoKuldButton(), getJelszoKuldButton().getName());
			getJatekosPanel().add(getJelszoTextField(), getJelszoTextField().getName());
			getJatekosPanel().add(getJelszoLabel(), getJelszoLabel().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJatekosPanel;
}
/**
 * Return the Panel3 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getJatekPanel() {
	if (ivjJatekPanel == null) {
		try {
			ivjJatekPanel = new java.awt.Panel();
			ivjJatekPanel.setName("JatekPanel");
			ivjJatekPanel.setLayout(null);
			ivjJatekPanel.setBackground(new java.awt.Color(0,90,0));
			ivjJatekPanel.setBounds(5, 4, 489, 421);
			getJatekPanel().add(getSajatLapPanel(), getSajatLapPanel().getName());
			getJatekPanel().add(getAsztalPanel(), getAsztalPanel().getName());
			getJatekPanel().add(getCopyrightLabel(), getCopyrightLabel().getName());
			getJatekPanel().add(getInfoTextArea(), getInfoTextArea().getName());
			getJatekPanel().add(getInfoLabel(), getInfoLabel().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJatekPanel;
}
/**
 * Return the TextField4 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.TextField getJatekTextField() {
	if (ivjJatekTextField == null) {
		try {
			ivjJatekTextField = new java.awt.TextField();
			ivjJatekTextField.setName("JatekTextField");
			ivjJatekTextField.setFont(new java.awt.Font("dialog", 0, 12));
			ivjJatekTextField.setText("");
			ivjJatekTextField.setBackground(java.awt.Color.blue);
			ivjJatekTextField.setBounds(4, 20, 86, 27);
			ivjJatekTextField.setForeground(java.awt.Color.yellow);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJatekTextField;
}
/**
 * Return the Button1 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Button getJatekUjButton() {
	if (ivjJatekUjButton == null) {
		try {
			ivjJatekUjButton = new java.awt.Button();
			ivjJatekUjButton.setName("JatekUjButton");
			ivjJatekUjButton.setFont(new java.awt.Font("dialog", 0, 10));
			ivjJatekUjButton.setBounds(4, 47, 86, 23);
			ivjJatekUjButton.setLabel(resWidgetTexts.getString("JatekUjButton_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJatekUjButton;
}
/**
 * Return the Label5 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getJellemzokLabel() {
	if (ivjJellemzokLabel == null) {
		try {
			ivjJellemzokLabel = new java.awt.Label();
			ivjJellemzokLabel.setName("JellemzokLabel");
			ivjJellemzokLabel.setFont(new java.awt.Font("dialog", 1, 10));
			ivjJellemzokLabel.setText(resWidgetTexts.getString("JellemzokLabel_text"));
			ivjJellemzokLabel.setBounds(197, 1, 80, 18);
			ivjJellemzokLabel.setForeground(java.awt.Color.yellow);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJellemzokLabel;
}
/**
 * Return the Button2 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
protected java.awt.Button getJelszoKuldButton() {
	if (ivjJelszoKuldButton == null) {
		try {
			ivjJelszoKuldButton = new java.awt.Button();
			ivjJelszoKuldButton.setName("JelszoKuldButton");
			ivjJelszoKuldButton.setFont(new java.awt.Font("dialog", 1, 10));
			ivjJelszoKuldButton.setBounds(130, 56, 139, 21);
			ivjJelszoKuldButton.setLabel(resWidgetTexts.getString("JelszoKuldButton_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJelszoKuldButton;
}
/**
 * Return the Label312 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
protected java.awt.Label getJelszoLabel() {
	if (ivjJelszoLabel == null) {
		try {
			ivjJelszoLabel = new java.awt.Label();
			ivjJelszoLabel.setName("JelszoLabel");
			ivjJelszoLabel.setFont(new java.awt.Font("dialog", 1, 10));
			ivjJelszoLabel.setText(resWidgetTexts.getString("JelszoLabel_text"));
			ivjJelszoLabel.setBounds(3, 37, 95, 21);
			ivjJelszoLabel.setForeground(java.awt.Color.yellow);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJelszoLabel;
}
/**
 * Return the TextField21 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
protected java.awt.TextField getJelszoTextField() {
	if (ivjJelszoTextField == null) {
		try {
			ivjJelszoTextField = new java.awt.TextField();
			ivjJelszoTextField.setName("JelszoTextField");
			ivjJelszoTextField.setText("");
			ivjJelszoTextField.setBackground(java.awt.Color.orange);
			ivjJelszoTextField.setVisible(true);
			ivjJelszoTextField.setForeground(java.awt.Color.blue);
			ivjJelszoTextField.setFont(new java.awt.Font("dialog", 0, 12));
			ivjJelszoTextField.setBounds(3, 55, 122, 23);
			ivjJelszoTextField.setEnabled(true);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJelszoTextField;
}
/**
 * Return the JelzeslapLabel property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getJelzeslapLabel() {
	if (ivjJelzeslapLabel == null) {
		try {
			ivjJelzeslapLabel = new java.awt.Label();
			ivjJelzeslapLabel.setName("JelzeslapLabel");
			ivjJelzeslapLabel.setText(resWidgetTexts.getString("PakliJelzeslap_text2"));
			ivjJelzeslapLabel.setBackground(new java.awt.Color(0,90,0));
			ivjJelzeslapLabel.setForeground(java.awt.Color.yellow);
			ivjJelzeslapLabel.setFont(new java.awt.Font("dialog", 0, 10));
			ivjJelzeslapLabel.setAlignment(java.awt.Label.LEFT);
			ivjJelzeslapLabel.setBounds(25, 44, 86, 19);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJelzeslapLabel;
}
/**
 * Return the Panel61 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getJelzeslapPanel() {
	if (ivjJelzeslapPanel == null) {
		try {
			ivjJelzeslapPanel = new java.awt.Panel();
			ivjJelzeslapPanel.setName("JelzeslapPanel");
			ivjJelzeslapPanel.setLayout(null);
			ivjJelzeslapPanel.setBackground(new java.awt.Color(0,90,0));
			ivjJelzeslapPanel.setBounds(47, 89, 48, 78);
			// user code begin {1}
			CardComponent cc = new CardComponent(this);
			getJelzeslapPanel ().add (cc);
			cc.setBounds(0, 0, getJelzeslapPanel ().getSize ().width, getJelzeslapPanel ().getSize ().height);
			ccJelzeslap = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJelzeslapPanel;
}
/**
 * Return the JelzesSugoMenuItem property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.MenuItem getJelzesSugoMenuItem() {
	if (ivjJelzesSugoMenuItem == null) {
		try {
			ivjJelzesSugoMenuItem = new java.awt.MenuItem();
			ivjJelzesSugoMenuItem.setLabel(resWidgetTexts.getString("JelzesSugoMenuItem_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJelzesSugoMenuItem;
}
/**
 * Return the Panel62 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getJelzesSzinJelPanel() {
	if (ivjJelzesSzinJelPanel == null) {
		try {
			ivjJelzesSzinJelPanel = new java.awt.Panel();
			ivjJelzesSzinJelPanel.setName("JelzesSzinJelPanel");
			ivjJelzesSzinJelPanel.setLayout(null);
			ivjJelzesSzinJelPanel.setBackground(new java.awt.Color(198,255,220));
			ivjJelzesSzinJelPanel.setBounds(9, 133, 35, 35);
			ivjJelzesSzinJelPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			// user code begin {1}
			SmallImgComponent cc = new SmallImgComponent(this);
			getJelzesSzinJelPanel ().add (cc);
			cc.setBounds(0, 0, getJelzesSzinJelPanel ().getSize ().width, getJelzesSzinJelPanel ().getSize ().height);
			ccJelzesSzinJel = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJelzesSzinJelPanel;
}
/**
 * Return the KertIranyChoice property value.
 * @return java.awt.Choice
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Choice getKertIranyChoice() {
	if (ivjKertIranyChoice == null) {
		try {
			ivjKertIranyChoice = new java.awt.Choice();
			ivjKertIranyChoice.setName("KertIranyChoice");
			ivjKertIranyChoice.setFont(new java.awt.Font("dialog", 1, 10));
			ivjKertIranyChoice.setBackground(java.awt.Color.white);
			ivjKertIranyChoice.setBounds(194, 210, 47, 26);
			ivjKertIranyChoice.setEnabled(false);
			ivjKertIranyChoice.setForeground(new java.awt.Color(0,90,0));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjKertIranyChoice;
}
/**
 * Return the KertIranyLabel property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getKertIranyLabel() {
	if (ivjKertIranyLabel == null) {
		try {
			ivjKertIranyLabel = new java.awt.Label();
			ivjKertIranyLabel.setName("KertIranyLabel");
			ivjKertIranyLabel.setText(resWidgetTexts.getString("KertIranyLabel_text"));
			ivjKertIranyLabel.setBackground(new java.awt.Color(0,90,0));
			ivjKertIranyLabel.setForeground(java.awt.Color.yellow);
			ivjKertIranyLabel.setFont(new java.awt.Font("dialog", 0, 10));
			ivjKertIranyLabel.setAlignment(java.awt.Label.CENTER);
			ivjKertIranyLabel.setBounds(175, 193, 76, 18);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjKertIranyLabel;
}
/**
 * Return the Choice21 property value.
 * @return java.awt.Choice
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Choice getKertJelChoice() {
	if (ivjKertJelChoice == null) {
		try {
			ivjKertJelChoice = new java.awt.Choice();
			ivjKertJelChoice.setName("KertJelChoice");
			ivjKertJelChoice.setFont(new java.awt.Font("dialog", 1, 10));
			ivjKertJelChoice.setBackground(java.awt.Color.white);
			ivjKertJelChoice.setBounds(85, 210, 109, 26);
			ivjKertJelChoice.setEnabled(false);
			ivjKertJelChoice.setForeground(new java.awt.Color(0,90,0));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjKertJelChoice;
}
/**
 * Return the Label8111 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getKertJelLabel1() {
	if (ivjKertJelLabel1 == null) {
		try {
			ivjKertJelLabel1 = new java.awt.Label();
			ivjKertJelLabel1.setName("KertJelLabel1");
			ivjKertJelLabel1.setText("Kért");
			ivjKertJelLabel1.setBackground(new java.awt.Color(0,90,0));
			ivjKertJelLabel1.setForeground(java.awt.Color.yellow);
			ivjKertJelLabel1.setFont(new java.awt.Font("dialog", 0, 10));
			ivjKertJelLabel1.setAlignment(java.awt.Label.CENTER);
			ivjKertJelLabel1.setBounds(89, 176, 42, 18);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjKertJelLabel1;
}
/**
 * Return the KertJelLabel2 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getKertJelLabel2() {
	if (ivjKertJelLabel2 == null) {
		try {
			ivjKertJelLabel2 = new java.awt.Label();
			ivjKertJelLabel2.setName("KertJelLabel2");
			ivjKertJelLabel2.setText(resWidgetTexts.getString("KertJelLabel2_text"));
			ivjKertJelLabel2.setBackground(new java.awt.Color(0,90,0));
			ivjKertJelLabel2.setForeground(java.awt.Color.yellow);
			ivjKertJelLabel2.setFont(new java.awt.Font("dialog", 0, 10));
			ivjKertJelLabel2.setAlignment(java.awt.Label.CENTER);
			ivjKertJelLabel2.setBounds(89, 193, 46, 18);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjKertJelLabel2;
}
/**
 * Return the KertJelPanel property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getKertJelPanel() {
	if (ivjKertJelPanel == null) {
		try {
			ivjKertJelPanel = new java.awt.Panel();
			ivjKertJelPanel.setName("KertJelPanel");
			ivjKertJelPanel.setLayout(null);
			ivjKertJelPanel.setBackground(new java.awt.Color(198,255,220));
			ivjKertJelPanel.setBounds(139, 174, 35, 35);
			ivjKertJelPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			// user code begin {1}
			SmallImgComponent cc = new SmallImgComponent(this);
			getKertJelPanel ().add (cc);
			cc.setBounds(0, 0, getKertJelPanel ().getSize ().width, getKertJelPanel ().getSize ().height);
			ccKertJel = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjKertJelPanel;
}
/**
 * Return the Choice2 property value.
 * @return java.awt.Choice
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Choice getKertSzinChoice() {
	if (ivjKertSzinChoice == null) {
		try {
			ivjKertSzinChoice = new java.awt.Choice();
			ivjKertSzinChoice.setName("KertSzinChoice");
			ivjKertSzinChoice.setFont(new java.awt.Font("dialog", 1, 10));
			ivjKertSzinChoice.setBackground(java.awt.Color.white);
			ivjKertSzinChoice.setBounds(3, 210, 82, 26);
			ivjKertSzinChoice.setEnabled(false);
			ivjKertSzinChoice.setForeground(new java.awt.Color(0,90,0));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjKertSzinChoice;
}
/**
 * Return the KertSzinLabel1 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getKertSzinLabel1() {
	if (ivjKertSzinLabel1 == null) {
		try {
			ivjKertSzinLabel1 = new java.awt.Label();
			ivjKertSzinLabel1.setName("KertSzinLabel1");
			ivjKertSzinLabel1.setText("Kért");
			ivjKertSzinLabel1.setBackground(new java.awt.Color(0,90,0));
			ivjKertSzinLabel1.setForeground(java.awt.Color.yellow);
			ivjKertSzinLabel1.setFont(new java.awt.Font("dialog", 0, 10));
			ivjKertSzinLabel1.setAlignment(java.awt.Label.CENTER);
			ivjKertSzinLabel1.setBounds(6, 176, 36, 18);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjKertSzinLabel1;
}
/**
 * Return the KertSzinLabel2 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getKertSzinLabel2() {
	if (ivjKertSzinLabel2 == null) {
		try {
			ivjKertSzinLabel2 = new java.awt.Label();
			ivjKertSzinLabel2.setName("KertSzinLabel2");
			ivjKertSzinLabel2.setText(resWidgetTexts.getString("KertSzinLabel2_text"));
			ivjKertSzinLabel2.setBackground(new java.awt.Color(0,90,0));
			ivjKertSzinLabel2.setForeground(java.awt.Color.yellow);
			ivjKertSzinLabel2.setFont(new java.awt.Font("dialog", 0, 10));
			ivjKertSzinLabel2.setAlignment(java.awt.Label.CENTER);
			ivjKertSzinLabel2.setBounds(6, 193, 36, 18);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjKertSzinLabel2;
}
/**
 * Return the KertSzinPanel property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getKertSzinPanel() {
	if (ivjKertSzinPanel == null) {
		try {
			ivjKertSzinPanel = new java.awt.Panel();
			ivjKertSzinPanel.setName("KertSzinPanel");
			ivjKertSzinPanel.setLayout(null);
			ivjKertSzinPanel.setBackground(new java.awt.Color(198,255,220));
			ivjKertSzinPanel.setBounds(41, 174, 35, 35);
			ivjKertSzinPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			// user code begin {1}
			SmallImgComponent cc = new SmallImgComponent(this);
			getKertSzinPanel ().add (cc);
			cc.setBounds(0, 0, getKertSzinPanel ().getSize ().width, getKertSzinPanel ().getSize ().height);
			ccKertSzin = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjKertSzinPanel;
}
/**
 * Return the Button31 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Button getLerakButton() {
	if (ivjLerakButton == null) {
		try {
			ivjLerakButton = new java.awt.Button();
			ivjLerakButton.setName("LerakButton");
			ivjLerakButton.setFont(new java.awt.Font("dialog", 1, 12));
			ivjLerakButton.setBackground(new java.awt.Color(0,0,119));
			ivjLerakButton.setBounds(3, 20, 70, 23);
			ivjLerakButton.setForeground(java.awt.Color.cyan);
			ivjLerakButton.setLabel(resWidgetTexts.getString("LerakButton_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjLerakButton;
}
/**
 * Return the Label8 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getLerakottLabel() {
	if (ivjLerakottLabel == null) {
		try {
			ivjLerakottLabel = new java.awt.Label();
			ivjLerakottLabel.setName("LerakottLabel");
			ivjLerakottLabel.setText(resWidgetTexts.getString("LerakottLabel_text"));
			ivjLerakottLabel.setBackground(new java.awt.Color(0,90,0));
			ivjLerakottLabel.setForeground(java.awt.Color.yellow);
			ivjLerakottLabel.setFont(new java.awt.Font("dialog", 0, 10));
			ivjLerakottLabel.setAlignment(java.awt.Label.RIGHT);
			ivjLerakottLabel.setBounds(105, 28, 86, 17);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjLerakottLabel;
}
/**
 * Return the Panel611 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getLerakottPanel() {
	if (ivjLerakottPanel == null) {
		try {
			ivjLerakottPanel = new java.awt.Panel();
			ivjLerakottPanel.setName("LerakottPanel");
			ivjLerakottPanel.setLayout(null);
			ivjLerakottPanel.setBackground(new java.awt.Color(0,90,0));
			ivjLerakottPanel.setBounds(143, 47, 48, 78);
			// user code begin {1}
			CardComponent cc = new CardComponent(this);
			getLerakottPanel ().add (cc);
			cc.setBounds(0, 0, getLerakottPanel ().getSize ().width, getLerakottPanel ().getSize ().height);
			ccLerakottLap = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjLerakottPanel;
}
/**
 * Return the Scrollbar2 property value.
 * @return java.awt.Scrollbar
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Scrollbar getLerakottScrollbar() {
	if (ivjLerakottScrollbar == null) {
		try {
			ivjLerakottScrollbar = new java.awt.Scrollbar();
			ivjLerakottScrollbar.setName("LerakottScrollbar");
			ivjLerakottScrollbar.setBackground(new java.awt.Color(0,128,112));
			ivjLerakottScrollbar.setBlockIncrement(1);
			ivjLerakottScrollbar.setForeground(java.awt.Color.yellow);
			ivjLerakottScrollbar.setMaximum(0);
			ivjLerakottScrollbar.setBounds(131, 47, 12, 78);
			ivjLerakottScrollbar.setVisibleAmount(1);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjLerakottScrollbar;
}
/**
 * Return the Label4 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getMeghirdetettLabel() {
	if (ivjMeghirdetettLabel == null) {
		try {
			ivjMeghirdetettLabel = new java.awt.Label();
			ivjMeghirdetettLabel.setName("MeghirdetettLabel");
			ivjMeghirdetettLabel.setFont(new java.awt.Font("dialog", 1, 10));
			ivjMeghirdetettLabel.setText(resWidgetTexts.getString("MeghirdetettLabel_text"));
			ivjMeghirdetettLabel.setBounds(94, 1, 97, 18);
			ivjMeghirdetettLabel.setForeground(java.awt.Color.yellow);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMeghirdetettLabel;
}
/**
 * Return the List2 property value.
 * @return java.awt.List
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.List getMeghirdetettList() {
	if (ivjMeghirdetettList == null) {
		try {
			ivjMeghirdetettList = new java.awt.List();
			ivjMeghirdetettList.setName("MeghirdetettList");
			ivjMeghirdetettList.setFont(new java.awt.Font("dialog", 0, 10));
			ivjMeghirdetettList.setBackground(java.awt.Color.blue);
			ivjMeghirdetettList.setBounds(94, 21, 73, 76);
			ivjMeghirdetettList.setForeground(java.awt.Color.yellow);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMeghirdetettList;
}
/**
 * Return the MenuSeparator1 property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.MenuItem getMenuSeparator1() {
	if (ivjMenuSeparator1 == null) {
		try {
			ivjMenuSeparator1 = new java.awt.MenuItem();
			ivjMenuSeparator1.setLabel("-");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMenuSeparator1;
}
/**
 * Return the Button111121 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Button getMondButton() {
	if (ivjMondButton == null) {
		try {
			ivjMondButton = new java.awt.Button();
			ivjMondButton.setName("MondButton");
			ivjMondButton.setFont(new java.awt.Font("dialog", 0, 10));
			ivjMondButton.setBackground(java.awt.Color.black);
			ivjMondButton.setBounds(220, 181, 48, 30);
			ivjMondButton.setForeground(java.awt.Color.white);
			ivjMondButton.setLabel(resWidgetTexts.getString("MondButton_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMondButton;
}
/**
 * Return the Label31 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getNevLabel() {
	if (ivjNevLabel == null) {
		try {
			ivjNevLabel = new java.awt.Label();
			ivjNevLabel.setName("NevLabel");
			ivjNevLabel.setFont(new java.awt.Font("dialog", 1, 10));
			ivjNevLabel.setText(resWidgetTexts.getString("NevLabel_text"));
			ivjNevLabel.setBounds(3, -1, 93, 18);
			ivjNevLabel.setForeground(java.awt.Color.yellow);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjNevLabel;
}
/**
 * Return the TextField2 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.TextField getNevTextField() {
	if (ivjNevTextField == null) {
		try {
			ivjNevTextField = new java.awt.TextField();
			ivjNevTextField.setName("NevTextField");
			ivjNevTextField.setFont(new java.awt.Font("dialog", 0, 12));
			ivjNevTextField.setText("");
			ivjNevTextField.setBackground(java.awt.Color.orange);
			ivjNevTextField.setBounds(3, 15, 92, 27);
			ivjNevTextField.setForeground(java.awt.Color.blue);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjNevTextField;
}
/**
 * Return the Choice111 property value.
 * @return java.awt.Choice
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Choice getOsztasJellemzoChoice() {
	if (ivjOsztasJellemzoChoice == null) {
		try {
			ivjOsztasJellemzoChoice = new java.awt.Choice();
			ivjOsztasJellemzoChoice.setName("OsztasJellemzoChoice");
			ivjOsztasJellemzoChoice.setFont(new java.awt.Font("dialog", 0, 12));
			ivjOsztasJellemzoChoice.setBackground(java.awt.Color.blue);
			ivjOsztasJellemzoChoice.setBounds(173, 72, 98, 25);
			ivjOsztasJellemzoChoice.setForeground(java.awt.Color.yellow);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjOsztasJellemzoChoice;
}
/**
 * Return the Label51 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getOsztasJellemzoLabel() {
	if (ivjOsztasJellemzoLabel == null) {
		try {
			ivjOsztasJellemzoLabel = new java.awt.Label();
			ivjOsztasJellemzoLabel.setName("OsztasJellemzoLabel");
			ivjOsztasJellemzoLabel.setFont(new java.awt.Font("dialog", 2, 10));
			ivjOsztasJellemzoLabel.setText(resWidgetTexts.getString("OsztasJellemzoLabel_text"));
			ivjOsztasJellemzoLabel.setBounds(173, 60, 98, 14);
			ivjOsztasJellemzoLabel.setForeground(java.awt.Color.yellow);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjOsztasJellemzoLabel;
}
/**
 * Return the PakliDescrButton property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Button getPakliDescrButton() {
	if (ivjPakliDescrButton == null) {
		try {
			ivjPakliDescrButton = new java.awt.Button();
			ivjPakliDescrButton.setName("PakliDescrButton");
			ivjPakliDescrButton.setFont(new java.awt.Font("dialog", 0, 8));
			ivjPakliDescrButton.setBackground(java.awt.Color.yellow);
			ivjPakliDescrButton.setBounds(246, 19, 25, 13);
			ivjPakliDescrButton.setActionCommand(resWidgetTexts.getString("PakliDescrButtonText"));
			ivjPakliDescrButton.setLabel(resWidgetTexts.getString("PakliDescrButtonText"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPakliDescrButton;
}
/**
 * Return the Choice1 property value.
 * @return java.awt.Choice
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Choice getPakliJellemzoChoice() {
	if (ivjPakliJellemzoChoice == null) {
		try {
			ivjPakliJellemzoChoice = new java.awt.Choice();
			ivjPakliJellemzoChoice.setName("PakliJellemzoChoice");
			ivjPakliJellemzoChoice.setFont(new java.awt.Font("dialog", 0, 12));
			ivjPakliJellemzoChoice.setBackground(java.awt.Color.blue);
			ivjPakliJellemzoChoice.setBounds(173, 32, 98, 23);
			ivjPakliJellemzoChoice.setForeground(java.awt.Color.yellow);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPakliJellemzoChoice;
}
/**
 * Return the Label511 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getPakliJellemzoLabel() {
	if (ivjPakliJellemzoLabel == null) {
		try {
			ivjPakliJellemzoLabel = new java.awt.Label();
			ivjPakliJellemzoLabel.setName("PakliJellemzoLabel");
			ivjPakliJellemzoLabel.setFont(new java.awt.Font("dialog", 2, 10));
			ivjPakliJellemzoLabel.setText(resWidgetTexts.getString("PakliJellemzoLabel_text"));
			ivjPakliJellemzoLabel.setBounds(173, 18, 54, 14);
			ivjPakliJellemzoLabel.setForeground(java.awt.Color.yellow);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPakliJellemzoLabel;
}
/**
 * Return the Label82 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getPakliLabel() {
	if (ivjPakliLabel == null) {
		try {
			ivjPakliLabel = new java.awt.Label();
			ivjPakliLabel.setName("PakliLabel");
			ivjPakliLabel.setText(resWidgetTexts.getString("PakliJelzeslap_text1"));
			ivjPakliLabel.setBackground(new java.awt.Color(0,90,0));
			ivjPakliLabel.setForeground(java.awt.Color.yellow);
			ivjPakliLabel.setFont(new java.awt.Font("dialog", 0, 10));
			ivjPakliLabel.setAlignment(java.awt.Label.LEFT);
			ivjPakliLabel.setBounds(25, 28, 54, 19);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPakliLabel;
}
/**
 * Return the Panel612 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getPakliPanel() {
	if (ivjPakliPanel == null) {
		try {
			ivjPakliPanel = new java.awt.Panel();
			ivjPakliPanel.setName("PakliPanel");
			ivjPakliPanel.setLayout(null);
			ivjPakliPanel.setBackground(new java.awt.Color(0,90,0));
			ivjPakliPanel.setBounds(25, 59, 78, 48);
			// user code begin {1}
			CardComponent cc = new CardComponent(this);
			getPakliPanel ().add (cc);
			cc.setBounds(0, 0, getPakliPanel ().getSize ().width, getPakliPanel ().getSize ().height);
			ccPakli = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPakliPanel;
}
/**
 * Return the Panel2 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getPanel2() {
	if (ivjPanel2 == null) {
		try {
			ivjPanel2 = new java.awt.Panel();
			ivjPanel2.setName("Panel2");
			ivjPanel2.setLayout(null);
			ivjPanel2.setBackground(java.awt.Color.black);
			ivjPanel2.setBounds(499, 210, 279, 215);
			getPanel2().add(getChatTextArea(), getChatTextArea().getName());
			getPanel2().add(getUzenetTextField(), getUzenetTextField().getName());
			getPanel2().add(getMondButton(), getMondButton().getName());
			getPanel2().add(getChatLabel(), getChatLabel().getName());
			getPanel2().add(getUzenetLabel(), getUzenetLabel().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPanel2;
}
public String[][] getParameterInfo() {
	return parameterInfo;
}
/**
 * Return the Button311 property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Button getPasszButton() {
	if (ivjPasszButton == null) {
		try {
			ivjPasszButton = new java.awt.Button();
			ivjPasszButton.setName("PasszButton");
			ivjPasszButton.setBackground(new java.awt.Color(0,0,119));
			ivjPasszButton.setForeground(java.awt.Color.cyan);
			ivjPasszButton.setActionCommand(resWidgetTexts.getString("PasszButton_label"));
			ivjPasszButton.setLabel(resWidgetTexts.getString("PasszButton_label"));
			ivjPasszButton.setFont(new java.awt.Font("dialog", 1, 12));
			ivjPasszButton.setBounds(3, 47, 70, 23);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPasszButton;
}
/**
 * Return the RejectDialog property value.
 * @return java.awt.Dialog
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Dialog getRejectDialog() {
	if (ivjRejectDialog == null) {
		try {
			ivjRejectDialog = new java.awt.Dialog(new java.awt.Frame());
			ivjRejectDialog.setName("RejectDialog");
			ivjRejectDialog.setResizable(false);
			ivjRejectDialog.setLayout(null);
			ivjRejectDialog.setBackground(new java.awt.Color(0,90,0));
			ivjRejectDialog.setModal(false);
			ivjRejectDialog.setBounds(33, 59, 501, 170);
			ivjRejectDialog.setTitle(resWidgetTexts.getString("RejectDialog_title"));
			getRejectDialog().add(getContentsPane(), getContentsPane().getName());
			getRejectDialog().add(getRejectOKButton(), getRejectOKButton().getName());
			getRejectDialog().add(getRejectTextArea(), getRejectTextArea().getName());
			getRejectDialog().add(getRejectInfoLabel(), getRejectInfoLabel().getName());
			getRejectDialog().add(getRejectHelpLabel1(), getRejectHelpLabel1().getName());
			getRejectDialog().add(getRejectHelpLabel2(), getRejectHelpLabel2().getName());
			getRejectDialog().add(getRejectGameHelpButton(), getRejectGameHelpButton().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjRejectDialog;
}
/**
 * Return the RejectGameHelpButton property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Button getRejectGameHelpButton() {
	if (ivjRejectGameHelpButton == null) {
		try {
			ivjRejectGameHelpButton = new java.awt.Button();
			ivjRejectGameHelpButton.setName("RejectGameHelpButton");
			ivjRejectGameHelpButton.setFont(new java.awt.Font("dialog", 1, 10));
			ivjRejectGameHelpButton.setBounds(55, 104, 76, 23);
			ivjRejectGameHelpButton.setLabel(resWidgetTexts.getString("GameHelpButton_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjRejectGameHelpButton;
}
/**
 * Return the RejectHelpLabel1 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getRejectHelpLabel1() {
	if (ivjRejectHelpLabel1 == null) {
		try {
			ivjRejectHelpLabel1 = new java.awt.Label();
			ivjRejectHelpLabel1.setName("RejectHelpLabel1");
			ivjRejectHelpLabel1.setFont(new java.awt.Font("dialog", 1, 10));
			ivjRejectHelpLabel1.setAlignment(java.awt.Label.RIGHT);
			ivjRejectHelpLabel1.setText(resWidgetTexts.getString("HelpLabel1_text"));
			ivjRejectHelpLabel1.setBounds(8, 97, 45, 16);
			ivjRejectHelpLabel1.setForeground(java.awt.Color.white);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjRejectHelpLabel1;
}
/**
 * Return the RejectHelpLabel2 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getRejectHelpLabel2() {
	if (ivjRejectHelpLabel2 == null) {
		try {
			ivjRejectHelpLabel2 = new java.awt.Label();
			ivjRejectHelpLabel2.setName("RejectHelpLabel2");
			ivjRejectHelpLabel2.setFont(new java.awt.Font("dialog", 1, 10));
			ivjRejectHelpLabel2.setAlignment(java.awt.Label.RIGHT);
			ivjRejectHelpLabel2.setText(resWidgetTexts.getString("HelpLabel2_text"));
			ivjRejectHelpLabel2.setBounds(8, 111, 45, 16);
			ivjRejectHelpLabel2.setForeground(java.awt.Color.white);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjRejectHelpLabel2;
}
/**
 * Return the RejectInfoLabel property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getRejectInfoLabel() {
	if (ivjRejectInfoLabel == null) {
		try {
			ivjRejectInfoLabel = new java.awt.Label();
			ivjRejectInfoLabel.setName("RejectInfoLabel");
			ivjRejectInfoLabel.setFont(new java.awt.Font("dialog", 1, 14));
			ivjRejectInfoLabel.setAlignment(java.awt.Label.RIGHT);
			ivjRejectInfoLabel.setText(resWidgetTexts.getString("InfoLabel_text"));
			ivjRejectInfoLabel.setBounds(8, 66, 45, 31);
			ivjRejectInfoLabel.setForeground(java.awt.Color.white);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjRejectInfoLabel;
}
/**
 * Return the RejectOKButton property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Button getRejectOKButton() {
	if (ivjRejectOKButton == null) {
		try {
			ivjRejectOKButton = new java.awt.Button();
			ivjRejectOKButton.setName("RejectOKButton");
			ivjRejectOKButton.setFont(new java.awt.Font("dialog", 1, 12));
			ivjRejectOKButton.setBackground(java.awt.SystemColor.control);
			ivjRejectOKButton.setBounds(406, 104, 77, 23);
			ivjRejectOKButton.setForeground(java.awt.SystemColor.controlText);
			ivjRejectOKButton.setLabel(resWidgetTexts.getString("RejectOKButton_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjRejectOKButton;
}
/**
 * Return the RejectTextArea property value.
 * @return java.awt.TextArea
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.TextArea getRejectTextArea() {
	if (ivjRejectTextArea == null) {
		try {
			ivjRejectTextArea = new java.awt.TextArea("", 0, 0, java.awt.TextArea.SCROLLBARS_VERTICAL_ONLY);
			ivjRejectTextArea.setName("RejectTextArea");
			ivjRejectTextArea.setBackground(java.awt.Color.pink);
			ivjRejectTextArea.setForeground(java.awt.Color.black);
			ivjRejectTextArea.setFont(new java.awt.Font("dialog", 1, 10));
			ivjRejectTextArea.setBounds(55, 35, 428, 62);
			ivjRejectTextArea.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjRejectTextArea;
}
/**
 * Return the ResolveMenuItem property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.MenuItem getResolveMenuItem() {
	if (ivjResolveMenuItem == null) {
		try {
			ivjResolveMenuItem = new java.awt.MenuItem();
			ivjResolveMenuItem.setLabel(resWidgetTexts.getString("ResolveMenuItem_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjResolveMenuItem;
}
/**
 * Return the Label81112 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getSajatLapLabel() {
	if (ivjSajatLapLabel == null) {
		try {
			ivjSajatLapLabel = new java.awt.Label();
			ivjSajatLapLabel.setName("SajatLapLabel");
			ivjSajatLapLabel.setText(resWidgetTexts.getString("SajatLapLabel_text"));
			ivjSajatLapLabel.setBackground(new java.awt.Color(184,0,119));
			ivjSajatLapLabel.setForeground(java.awt.Color.cyan);
			ivjSajatLapLabel.setFont(new java.awt.Font("dialog", 1, 10));
			ivjSajatLapLabel.setAlignment(java.awt.Label.LEFT);
			ivjSajatLapLabel.setBounds(75, 1, 75, 17);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSajatLapLabel;
}
/**
 * Return the Panel4 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getSajatLapPanel() {
	if (ivjSajatLapPanel == null) {
		try {
			ivjSajatLapPanel = new java.awt.Panel();
			ivjSajatLapPanel.setName("SajatLapPanel");
			ivjSajatLapPanel.setLayout(null);
			ivjSajatLapPanel.setBackground(new java.awt.Color(184,0,119));
			ivjSajatLapPanel.setBounds(9, 276, 473, 129);
			getSajatLapPanel().add(getSajatLapPanel1(), getSajatLapPanel1().getName());
			getSajatLapPanel().add(getSajatLapPanel2(), getSajatLapPanel2().getName());
			getSajatLapPanel().add(getSajatLapPanel3(), getSajatLapPanel3().getName());
			getSajatLapPanel().add(getSajatLapPanel4(), getSajatLapPanel4().getName());
			getSajatLapPanel().add(getSajatLapPanel5(), getSajatLapPanel5().getName());
			getSajatLapPanel().add(getSajatLapPanel6(), getSajatLapPanel6().getName());
			getSajatLapPanel().add(getSajatLapPanel7(), getSajatLapPanel7().getName());
			getSajatLapPanel().add(getSajatLapPanel8(), getSajatLapPanel8().getName());
			getSajatLapPanel().add(getSajatLapScrollbar(), getSajatLapScrollbar().getName());
			getSajatLapPanel().add(getSajatSzinJelPanel(), getSajatSzinJelPanel().getName());
			getSajatLapPanel().add(getPasszButton(), getPasszButton().getName());
			getSajatLapPanel().add(getLerakButton(), getLerakButton().getName());
			getSajatLapPanel().add(getSajatLapLabel(), getSajatLapLabel().getName());
			getSajatLapPanel().add(getAkcioLabel(), getAkcioLabel().getName());
			getSajatLapPanel().add(getSajatSzinJelLabel(), getSajatSzinJelLabel().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSajatLapPanel;
}
/**
 * Return the Panel6111 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getSajatLapPanel1() {
	if (ivjSajatLapPanel1 == null) {
		try {
			ivjSajatLapPanel1 = new java.awt.Panel();
			ivjSajatLapPanel1.setName("SajatLapPanel1");
			ivjSajatLapPanel1.setLayout(null);
			ivjSajatLapPanel1.setBackground(new java.awt.Color(184,0,119));
			ivjSajatLapPanel1.setBounds(75, 20, 48, 78);
			ivjSajatLapPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			// user code begin {1}
			CardComponent cc = new CardComponent(this);
			getSajatLapPanel1 ().add (cc);
			cc.setBounds(0, 0, getSajatLapPanel1 ().getSize ().width, getSajatLapPanel1 ().getSize ().height);
			ccSajatLapok[0] = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSajatLapPanel1;
}
/**
 * Return the Panel61111 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getSajatLapPanel2() {
	if (ivjSajatLapPanel2 == null) {
		try {
			ivjSajatLapPanel2 = new java.awt.Panel();
			ivjSajatLapPanel2.setName("SajatLapPanel2");
			ivjSajatLapPanel2.setLayout(null);
			ivjSajatLapPanel2.setBackground(new java.awt.Color(184,0,119));
			ivjSajatLapPanel2.setBounds(124, 20, 48, 78);
			ivjSajatLapPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			// user code begin {1}
			CardComponent cc = new CardComponent(this);
			getSajatLapPanel2 ().add (cc);
			cc.setBounds(0, 0, getSajatLapPanel2 ().getSize ().width, getSajatLapPanel2 ().getSize ().height);
			ccSajatLapok[1] = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSajatLapPanel2;
}
/**
 * Return the Panel611111 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getSajatLapPanel3() {
	if (ivjSajatLapPanel3 == null) {
		try {
			ivjSajatLapPanel3 = new java.awt.Panel();
			ivjSajatLapPanel3.setName("SajatLapPanel3");
			ivjSajatLapPanel3.setLayout(null);
			ivjSajatLapPanel3.setBackground(new java.awt.Color(184,0,119));
			ivjSajatLapPanel3.setBounds(173, 20, 48, 78);
			ivjSajatLapPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			// user code begin {1}
			CardComponent cc = new CardComponent(this);
			getSajatLapPanel3 ().add (cc);
			cc.setBounds(0, 0, getSajatLapPanel3 ().getSize ().width, getSajatLapPanel3 ().getSize ().height);
			ccSajatLapok[2] = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSajatLapPanel3;
}
/**
 * Return the Panel6111111 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getSajatLapPanel4() {
	if (ivjSajatLapPanel4 == null) {
		try {
			ivjSajatLapPanel4 = new java.awt.Panel();
			ivjSajatLapPanel4.setName("SajatLapPanel4");
			ivjSajatLapPanel4.setLayout(null);
			ivjSajatLapPanel4.setBackground(new java.awt.Color(184,0,119));
			ivjSajatLapPanel4.setBounds(222, 20, 48, 78);
			ivjSajatLapPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			// user code begin {1}
			CardComponent cc = new CardComponent(this);
			getSajatLapPanel4 ().add (cc);
			cc.setBounds(0, 0, getSajatLapPanel4 ().getSize ().width, getSajatLapPanel4 ().getSize ().height);
			ccSajatLapok[3] = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSajatLapPanel4;
}
/**
 * Return the Panel61111111 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getSajatLapPanel5() {
	if (ivjSajatLapPanel5 == null) {
		try {
			ivjSajatLapPanel5 = new java.awt.Panel();
			ivjSajatLapPanel5.setName("SajatLapPanel5");
			ivjSajatLapPanel5.setLayout(null);
			ivjSajatLapPanel5.setBackground(new java.awt.Color(184,0,119));
			ivjSajatLapPanel5.setBounds(271, 20, 48, 78);
			ivjSajatLapPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			// user code begin {1}
			CardComponent cc = new CardComponent(this);
			getSajatLapPanel5 ().add (cc);
			cc.setBounds(0, 0, getSajatLapPanel5 ().getSize ().width, getSajatLapPanel5 ().getSize ().height);
			ccSajatLapok[4] = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSajatLapPanel5;
}
/**
 * Return the Panel611111111 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getSajatLapPanel6() {
	if (ivjSajatLapPanel6 == null) {
		try {
			ivjSajatLapPanel6 = new java.awt.Panel();
			ivjSajatLapPanel6.setName("SajatLapPanel6");
			ivjSajatLapPanel6.setLayout(null);
			ivjSajatLapPanel6.setBackground(new java.awt.Color(184,0,119));
			ivjSajatLapPanel6.setBounds(320, 20, 48, 78);
			ivjSajatLapPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			// user code begin {1}
			CardComponent cc = new CardComponent(this);
			getSajatLapPanel6 ().add (cc);
			cc.setBounds(0, 0, getSajatLapPanel6 ().getSize ().width, getSajatLapPanel6 ().getSize ().height);
			ccSajatLapok[5] = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSajatLapPanel6;
}
/**
 * Return the Panel6111111111 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getSajatLapPanel7() {
	if (ivjSajatLapPanel7 == null) {
		try {
			ivjSajatLapPanel7 = new java.awt.Panel();
			ivjSajatLapPanel7.setName("SajatLapPanel7");
			ivjSajatLapPanel7.setLayout(null);
			ivjSajatLapPanel7.setBackground(new java.awt.Color(184,0,119));
			ivjSajatLapPanel7.setBounds(369, 20, 48, 78);
			ivjSajatLapPanel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			// user code begin {1}
			CardComponent cc = new CardComponent(this);
			getSajatLapPanel7 ().add (cc);
			cc.setBounds(0, 0, getSajatLapPanel7 ().getSize ().width, getSajatLapPanel7 ().getSize ().height);
			ccSajatLapok[6] = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSajatLapPanel7;
}
/**
 * Return the Panel61111111111 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getSajatLapPanel8() {
	if (ivjSajatLapPanel8 == null) {
		try {
			ivjSajatLapPanel8 = new java.awt.Panel();
			ivjSajatLapPanel8.setName("SajatLapPanel8");
			ivjSajatLapPanel8.setLayout(null);
			ivjSajatLapPanel8.setBackground(new java.awt.Color(184,0,119));
			ivjSajatLapPanel8.setBounds(418, 20, 48, 78);
			ivjSajatLapPanel8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			// user code begin {1}
			CardComponent cc = new CardComponent(this);
			getSajatLapPanel8 ().add (cc);
			cc.setBounds(0, 0, getSajatLapPanel8 ().getSize ().width, getSajatLapPanel8 ().getSize ().height);
			ccSajatLapok[7] = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSajatLapPanel8;
}
/**
 * Return the Scrollbar1 property value.
 * @return java.awt.Scrollbar
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Scrollbar getSajatLapScrollbar() {
	if (ivjSajatLapScrollbar == null) {
		try {
			ivjSajatLapScrollbar = new java.awt.Scrollbar();
			ivjSajatLapScrollbar.setName("SajatLapScrollbar");
			ivjSajatLapScrollbar.setMaximum(0);
			ivjSajatLapScrollbar.setBounds(74, 99, 393, 24);
			ivjSajatLapScrollbar.setBlockIncrement(4);
			ivjSajatLapScrollbar.setVisibleAmount(8);
			ivjSajatLapScrollbar.setOrientation(java.awt.Scrollbar.HORIZONTAL);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSajatLapScrollbar;
}
/**
 * Return the SajatSzinJelLabel property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getSajatSzinJelLabel() {
	if (ivjSajatSzinJelLabel == null) {
		try {
			ivjSajatSzinJelLabel = new java.awt.Label();
			ivjSajatSzinJelLabel.setName("SajatSzinJelLabel");
			ivjSajatSzinJelLabel.setText("? Szín/jelzés:");
			ivjSajatSzinJelLabel.setBackground(new java.awt.Color(184,0,119));
			ivjSajatSzinJelLabel.setForeground(java.awt.Color.cyan);
			ivjSajatSzinJelLabel.setFont(new java.awt.Font("dialog", 1, 10));
			ivjSajatSzinJelLabel.setAlignment(java.awt.Label.CENTER);
			ivjSajatSzinJelLabel.setBounds(0, 71, 75, 18);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSajatSzinJelLabel;
}
/**
 * Return the SajatSzinJelPanel property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Panel getSajatSzinJelPanel() {
	if (ivjSajatSzinJelPanel == null) {
		try {
			ivjSajatSzinJelPanel = new java.awt.Panel();
			ivjSajatSzinJelPanel.setName("SajatSzinJelPanel");
			ivjSajatSzinJelPanel.setLayout(null);
			ivjSajatSzinJelPanel.setBackground(new java.awt.Color(247,216,241));
			ivjSajatSzinJelPanel.setBounds(19, 89, 35, 35);
			ivjSajatSzinJelPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			// user code begin {1}
			SmallImgComponent cc = new SmallImgComponent(this);
			getSajatSzinJelPanel ().add (cc);
			cc.setBounds(0, 0, getSajatSzinJelPanel ().getSize ().width, getSajatSzinJelPanel ().getSize ().height);
			ccSajatSzinJel = cc;
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSajatSzinJelPanel;
}
/**
 * Return the Label9 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getSmileyLabel() {
	if (ivjSmileyLabel == null) {
		try {
			ivjSmileyLabel = new java.awt.Label();
			ivjSmileyLabel.setName("SmileyLabel");
			ivjSmileyLabel.setText(resWidgetTexts.getString("SmileyLabel_text"));
			ivjSmileyLabel.setBackground(new java.awt.Color(0,90,0));
			ivjSmileyLabel.setForeground(java.awt.Color.white);
			ivjSmileyLabel.setFont(new java.awt.Font("dialog", 0, 12));
			ivjSmileyLabel.setAlignment(java.awt.Label.RIGHT);
			ivjSmileyLabel.setBounds(344, 199, 55, 32);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSmileyLabel;
}
/**
 * Return the TextField3 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.TextField getSmileyTextField() {
	if (ivjSmileyTextField == null) {
		try {
			ivjSmileyTextField = new java.awt.TextField();
			ivjSmileyTextField.setName("SmileyTextField");
			ivjSmileyTextField.setText(":-)");
			ivjSmileyTextField.setBackground(java.awt.Color.white);
			ivjSmileyTextField.setForeground(new java.awt.Color(0,90,0));
			ivjSmileyTextField.setColumns(0);
			ivjSmileyTextField.setFont(new java.awt.Font("dialog", 1, 14));
			ivjSmileyTextField.setBounds(403, 199, 68, 32);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSmileyTextField;
}
/**
 * Return the SzinSugoMenuItem property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.MenuItem getSzinSugoMenuItem() {
	if (ivjSzinSugoMenuItem == null) {
		try {
			ivjSzinSugoMenuItem = new java.awt.MenuItem();
			ivjSzinSugoMenuItem.setLabel(resWidgetTexts.getString("SzinSugoMenuItem_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSzinSugoMenuItem;
}
/**
 * Return the Label2 property value.
 * @return java.awt.Label
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.Label getUzenetLabel() {
	if (ivjUzenetLabel == null) {
		try {
			ivjUzenetLabel = new java.awt.Label();
			ivjUzenetLabel.setName("UzenetLabel");
			ivjUzenetLabel.setFont(new java.awt.Font("dialog", 1, 10));
			ivjUzenetLabel.setText(resWidgetTexts.getString("UzenetLabel_text"));
			ivjUzenetLabel.setBounds(4, 166, 61, 14);
			ivjUzenetLabel.setForeground(java.awt.Color.white);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjUzenetLabel;
}
/**
 * Return the TextField1 property value.
 * @return java.awt.TextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.TextField getUzenetTextField() {
	if (ivjUzenetTextField == null) {
		try {
			ivjUzenetTextField = new java.awt.TextField();
			ivjUzenetTextField.setName("UzenetTextField");
			ivjUzenetTextField.setFont(new java.awt.Font("dialog", 0, 14));
			ivjUzenetTextField.setText("");
			ivjUzenetTextField.setBackground(java.awt.Color.darkGray);
			ivjUzenetTextField.setBounds(4, 181, 215, 30);
			ivjUzenetTextField.setForeground(java.awt.Color.white);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjUzenetTextField;
}
/**
 * Return the WhatToDoMenuItem property value.
 * @return java.awt.MenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.MenuItem getWhatToDoMenuItem() {
	if (ivjWhatToDoMenuItem == null) {
		try {
			ivjWhatToDoMenuItem = new java.awt.MenuItem();
			ivjWhatToDoMenuItem.setLabel(resWidgetTexts.getString("WhatToDoMenuItem_label"));
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjWhatToDoMenuItem;
}
/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {
  try {
	szr.msg.Logger.log(new szr.msg.ErrStrLog("CardApplet: uncaught exception."), null, null);//$NON-NLS-1$
	szr.msg.Logger.log(new szr.msg.ErrExcLog(exception), null, null);
	if (exception instanceof ExceptionInInitializerError) {
		szr.msg.Logger.log(new szr.msg.ErrStrLog("CardApplet: Initializer error."), null, null);//$NON-NLS-1$
		szr.msg.Logger.log(new szr.msg.ErrExcLog(((ExceptionInInitializerError)exception).getException()), null, null);
	}
  } catch (Exception e) {
	getInfoTextArea().setText("FATAL ERROR: " + e.toString() + "\n" + e.getMessage());
  }
}
/**
 * Initializes the applet.
 */
public void init() {
	try {
		super.init();
		setName("MyCardApplet");
		setLayout(null);
		setBackground(java.awt.Color.cyan);
		setSize(784, 430);
		add(getJatekosPanel(), getJatekosPanel().getName());
		add(getPanel2(), getPanel2().getName());
		add(getJatekPanel(), getJatekPanel().getName());
		add(getJatekBeallPanel(), getJatekBeallPanel().getName());
		// user code begin {1}
		add(getCardHelpPopupMenu());
		add(getGameHelpPopupMenu());
		clientController = new ClientController (
				new CardsInHand(ccSajatLapok, getSajatLapScrollbar(), ccSajatSzinJel, getLerakButton(), getPasszButton()),
				new Chatter(getChatTextArea(), getUzenetTextField(), getMondButton()),
				createMyMsgHandler(),
				new GameControlPanel(getJatekTextField(), getJatekUjButton(), getJatekCsatlButton(), getJatekKiszallButton(), getMeghirdetettList(), getPakliJellemzoChoice(), getOsztasJellemzoChoice(), getJatekosListaButton(), getPakliDescrButton()),
				new GameTable(ccLerakottLap, getLerakottScrollbar(), ccAktSzinJel, ccPakli, ccJelzeslap, ccJelzesSzinJel, getKertSzinChoice(), ccKertSzin, getKertJelChoice(), ccKertJel, getKertIranyChoice()),
				new PlayerControlPanel(getNevTextField(), getEmailTextField(), getJelszoTextField(), getJelszoKuldButton()),
				new PlayerList(getJatekosList(), getFelLeLabel(), getSmileyTextField()),
				new InfoSystem(getInfoTextArea(), getGameHelpButton(), getCardHelpButton(),
							   getCardHelpPopupMenu(), getSzinSugoMenuItem(), getJelzesSugoMenuItem(), getResolveMenuItem(),
							   getGameHelpPopupMenu(), getWhatToDoMenuItem(), getAppletHelpMenuItem(),
							   new RejectDlgHandler (getRejectDialog(), getRejectTextArea(), getRejectOKButton(), getRejectGameHelpButton()),
							   this)
				);
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {2}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		Frame frame = new java.awt.Frame();
		CardApplet aMyCardApplet;
		Class iiCls = Class.forName("szr.gui.CardApplet");//$NON-NLS-1$
		ClassLoader iiClsLoader = iiCls.getClassLoader();
		aMyCardApplet = (CardApplet)java.beans.Beans.instantiate(iiClsLoader,"szr.gui.CardApplet");//$NON-NLS-1$
		frame.add("Center", aMyCardApplet);//$NON-NLS-1$
		frame.setSize(aMyCardApplet.getSize());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		frame.setVisible(true);
	} catch (Throwable exception) {
		szr.msg.Logger.log(new szr.msg.ErrStrLog("Exception occurred in main() of CardApplet"), null, null);//$NON-NLS-1$
		szr.msg.Logger.log(new szr.msg.ErrExcLog(exception), null, null);
	}
}
public void setBaseURL(String newBaseURL) {
	myBaseURL = newBaseURL;
}
protected void setClientController(szr.maincli.ClientController newClientController) {
	clientController = newClientController;
}
}
