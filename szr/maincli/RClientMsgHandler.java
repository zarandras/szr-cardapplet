package szr.maincli;

import java.io.*;
import java.net.*;
import szr.msg.*;
/**
 * Valós kliens (Kártyaasztal) üzenetkezelõ osztálya
 * Creation date: (2002.03.24. 17:17:49)
 */
public class RClientMsgHandler extends ClientMsgHandler {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private ObjectOutputStream oStream = null;
	private ObjectInputStream iStream = null;
	private java.net.Socket clientSock = null;
	private final String serverHost;
	private final String serverTcpPort;	// csak késõbb parse-olja, mert nem lehetne még hibaüzenetet adni a konstruktorhíváskor
public RClientMsgHandler(String iServerHost, String iServerTcpPort) {
	super();
	serverHost = iServerHost;
	serverTcpPort = iServerTcpPort;
}
protected void closeConnection() {
  if (clientSock != null && iStream != null && oStream != null) {
	try {
		iStream.close();
		oStream.close();
		clientSock.close();
	} catch (IOException e) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(e), getGameStr(), getPlayerStr());
	} finally {
		clientSock = null;
		iStream = null;
		oStream = null;
	}
  } else {
	szr.msg.Logger.log(new szr.msg.ErrStrLog("RClientMsgHandler: closeConnection() called but connection already closed."), getGameStr(), getPlayerStr());//$NON-NLS-1$
  }
}
protected boolean openConnection() {
  if (clientSock == null && iStream == null && oStream == null) {	// clientSock should NOT be initialized before
	try {
		clientSock = new Socket(serverHost, Integer.parseInt(serverTcpPort));
		oStream = new ObjectOutputStream(clientSock.getOutputStream());
		iStream = new ObjectInputStream(clientSock.getInputStream());
		return true;
	} catch (IOException e) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(e), getGameStr(), getPlayerStr());
		return false;
	}
  } else {
	szr.msg.Logger.log(new szr.msg.ErrStrLog("RClientMsgHandler: openConnection() called but connection already opened."), getGameStr(), getPlayerStr());//$NON-NLS-1$
	return false;
  }
}
protected szr.msg.Event readNextEvent() {
	if (clientSock != null && iStream != null) {
		try {
			Event e = (Event)iStream.readObject();
			return e;
		} catch (Exception ex) {
			szr.msg.Logger.log(new szr.msg.ErrExcLog(ex), getGameStr(), getPlayerStr());
			return null;
		}
	} else {
		szr.msg.Logger.log(new szr.msg.ErrStrLog("RClientMsgHandler: readNextEvent() called but connection not opened."), getGameStr(), getPlayerStr());//$NON-NLS-1$
		return null;
	}
}
public void send(szr.msg.Action action) {
	if (clientSock != null && oStream != null) {
		try {
			oStream.writeObject(action);
			oStream.flush();
		} catch (IOException ex) {
			szr.msg.Logger.log(new szr.msg.ErrExcLog(ex), getGameStr(), getPlayerStr());
			getParentController().rejectedAction(resInfoTexts.getString("Baj_van_a_hálózati_kapcsol1"), szr.maincli.RejectDlgHandler.PSR_ERROR_FATAL); //$NON-NLS-1$
		}
	} else {
		szr.msg.Logger.log(new szr.msg.InfoLog("RClientMsgHandler: send() called but connection not opened."), getGameStr(), getPlayerStr());//$NON-NLS-1$
	}
}
}
