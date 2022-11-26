package szr.gui;

import java.awt.*;
import java.awt.event.*;
import szr.maincli.*;
import java.applet.*;
/**
 * Virtuális kliens (Gyakorlóasztal)
 * Creation date: (2002.03.20. 10:22:23)
 */
public class VCardApplet extends CardApplet implements ActionListener {
	private static java.util.ResourceBundle resWidgetTexts = java.util.ResourceBundle.getBundle("szr.shared.WidgetTexts");  //$NON-NLS-1$
	private String parameterInfo[][] = {
	 	 {"BaseURL",		"url",	"root of resources"},//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
	// 	 {"TestMode",		"",		"test mode"},	<- hidden parameter
	  };
	private szr.mainserv.VGameServer myServer = null;	// a virtuális szerver
	private java.awt.Button ujAppletButton = null;
	private java.applet.AppletContext customAppletContext = null;	// a külön nyitott ablak ebbe kapja az eredeti applet contextjét
	private static int nextChildID = 1;	// a következõ [ÚJ ABLAK] indexe
public void actionPerformed (ActionEvent event) {
	Object srcWidget = event.getSource ();
	if (srcWidget == ujAppletButton) {
		newApplet();
	}
}
public ClientMsgHandler createMyMsgHandler() {
	return new VClientMsgHandler(null);
}
public AppletContext getAppletContext() {
	if (customAppletContext == null)
		return super.getAppletContext();
	else
		return customAppletContext;
}
/**
 * Gets the applet information.
 * @return java.lang.String
 */
public java.lang.String getAppletInfo() {
	return "VCardApplet - Gyakorlóasztal\n" + //$NON-NLS-1$
		"\n" + //$NON-NLS-1$
		"Virtuális Turistakártya kliens.\n" + //$NON-NLS-1$
		"@author: Molnár András\n" + //$NON-NLS-1$
		"";//$NON-NLS-1$
}
public szr.mainserv.VGameServer getMyServer() {
	return myServer;
}
public String[][] getParameterInfo() {
	return parameterInfo;
}
/**
 * Return the UjAppletButton property value.
 * @return java.awt.Button
 */
private java.awt.Button getUjAppletButton() {
	if (ujAppletButton == null) {
		try {
			Rectangle r1 = getJelszoTextField().getBounds();
			Rectangle r2 = getJelszoKuldButton().getBounds();
			ujAppletButton = new java.awt.Button();
			ujAppletButton.setName("UjAppletButton");//$NON-NLS-1$
			ujAppletButton.setBackground(java.awt.Color.red);
			ujAppletButton.setBounds(r1.x, r2.y, r2.x + r2.width - r1.x, r1.height);
			ujAppletButton.setLabel(resWidgetTexts.getString("ÚJ_ABLAK")); //$NON-NLS-1$
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ujAppletButton;
}
/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {
  try {
	szr.msg.Logger.log(new szr.msg.ErrStrLog("VCardApplet: Uncaught exception."), null, null);//$NON-NLS-1$
	szr.msg.Logger.log(new szr.msg.ErrExcLog(exception), null, null);
	if (exception instanceof ExceptionInInitializerError) {
		szr.msg.Logger.log(new szr.msg.ErrStrLog("VCardApplet: Initializer error."), null, null);//$NON-NLS-1$
		szr.msg.Logger.log(new szr.msg.ErrExcLog(((ExceptionInInitializerError)exception).getException()), null, null);
	}
  } catch (Exception e) {
	getInfoTextArea().setText("FATAL ERROR: " + e.toString() + "\n" + e.getMessage());
  }
}
/**
 * Handle the Applet init method.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void init() {
	try {
		super.init();
		setName("VCardApplet");
		setSize(784, 430);
		// user code begin {1}
		getJelszoKuldButton().setVisible(false);
		getJelszoLabel().setVisible(false);
		getJelszoTextField().setVisible(false);
		getJatekosPanel().add(getUjAppletButton(), getUjAppletButton().getName());
		getUjAppletButton().addActionListener (this);
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {2}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * Egyszer hívódik csak meg; a newApplet-bõl
 */
protected void initSystem() {
	setMyServer(new szr.mainserv.VGameServer (getParameter("TestMode") != null));//$NON-NLS-1$
	szr.msg.Logger.setClientController(getClientController());
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		Frame frame = new java.awt.Frame();
		VCardApplet aVCardApplet;
		Class iiCls = Class.forName("szr.gui.VCardApplet");//$NON-NLS-1$
		ClassLoader iiClsLoader = iiCls.getClassLoader();
		aVCardApplet = (VCardApplet)java.beans.Beans.instantiate(iiClsLoader,"szr.gui.VCardApplet");//$NON-NLS-1$
		frame.add("Center", aVCardApplet);//$NON-NLS-1$
		frame.setSize(aVCardApplet.getSize());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		frame.setVisible(true);
	} catch (Throwable exception) {
		szr.msg.Logger.log(new szr.msg.ErrStrLog("Exception occurred in main() of VCardApplet"), null, null);//$NON-NLS-1$
		szr.msg.Logger.log(new szr.msg.ErrExcLog(exception), null, null);
	}
}
protected void newApplet() {
	try {
		Frame frame = new java.awt.Frame();
		VCardApplet childVCardApplet;
		Class iiCls = Class.forName("szr.gui.VCardApplet");//$NON-NLS-1$
		ClassLoader iiClsLoader = iiCls.getClassLoader();
		childVCardApplet = (VCardApplet)java.beans.Beans.instantiate(iiClsLoader,"szr.gui.VCardApplet");//$NON-NLS-1$
		frame.add("Center", childVCardApplet);//$NON-NLS-1$
		frame.setSize(new Dimension(childVCardApplet.getSize().width + 10, childVCardApplet.getSize().height + 50));
		frame.setTitle(szr.shared.SubstituteStr.doIt(resWidgetTexts.getString("Child_VCardApplet_#%n"), "%n", Integer.toString(nextChildID)));//$NON-NLS-2$ //$NON-NLS-1$
		nextChildID++;
		frame.addWindowListener(new VCardWindowAdapter(frame, childVCardApplet));
		childVCardApplet.setBaseURL(getBaseURL());
		childVCardApplet.setCustomAppletContext(getAppletContext());
		if (getMyServer() == null) {
			initSystem();
		}
		frame.setVisible(true);
		childVCardApplet.setMyServer (getMyServer ());
	} catch (Exception ex) {
		szr.msg.Logger.log(new szr.msg.ErrStrLog("VCardApplet: error launching new VCardApplet instance."), null, null);//$NON-NLS-1$
		szr.msg.Logger.log(new szr.msg.ErrExcLog(ex), null, null);
	}
}
protected void setCustomAppletContext(java.applet.AppletContext newCustomAppletContext) {
	customAppletContext = newCustomAppletContext;
}
/**
 * Virtuális szerver megadása
 */
public void setMyServer(szr.mainserv.VGameServer newMyServer) {
	if (myServer == null) {
		myServer = newMyServer;
		((VClientMsgHandler)getClientController().getMsgHandler()).setMyServer(myServer);	// resumes ClientController
	}
}
}
