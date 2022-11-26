package szr.mainserv;

import java.io.*;
import java.net.*;
import szr.shared.*;
/**
 * Val�s Sz�lr�zsaSzerver oszt�ly
 * Creation date: (2002.03.25. 20:11:26)
 */
public class RGameServer extends GameServer {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private int clientSlotCount;	// h�ny kliens csatlakozhat m�g?
private RGameServer(int iClientSlotCount, boolean iTestMode) {
	super();
	clientSlotCount = iClientSlotCount;
	setTestMode(iTestMode);
}
/**
 * JELSZ�KEZEL�S M�G NINCS!
 */
public boolean checkPassword(String name, String email, String password) {
	// !!! temporary:
	  // generate password
		String pass = name;
	  // check it
		if (password.equals(pass)) {
			szr.msg.Logger.log(new szr.msg.InfoLog("RGameServer: password check OK."), null, name);//$NON-NLS-1$
			return true;
		} else {
			szr.msg.Logger.log(new szr.msg.InfoLog("RGameServer: password check FAILED!"), null, name);//$NON-NLS-1$
			return false;
		}
}
private synchronized void connect(Socket sock) {
	RServerMsgHandler smh = new RServerMsgHandler (this, sock);
	if (clientSlotCount > 0) {
		clientSlotCount--;	// a removeMsgHandler n�veli
		smh.start();
	} else {
		szr.msg.Logger.log(new szr.msg.InfoLog("RGameServer: Too many clients, rejecting connect request."), null, smh.getPlayerStr());//$NON-NLS-1$
		smh.reject(resInfoTexts.getString("A_szerverhez_nem_lehet_kap")); //$NON-NLS-1$
	}
	try {
		Thread.sleep(500);	// ne lehessen egym�s ut�n t�l gyosran kapcsol�dni
	} catch (InterruptedException e) {}
}
public static void main(String[] args) {
  try {
	System.out.println("Sz�lr�zsaSzerver");//$NON-NLS-1$
	System.out.println("~~~~~~~~~~~~~~~~");//$NON-NLS-1$
	System.out.println("(C) by Moln�r Andr�s, 2002.");//$NON-NLS-1$
	System.out.println("");//$NON-NLS-1$

	int myTCPport, maxNrOfClients;

	// parse params
	if (args.length < 2 ) {
		System.out.println("Haszn�lat: java szr.mainserv.RGameServer TCPportSz�m MaxKliensSz�m [PakliM�d]");//$NON-NLS-1$
		System.out.println("	       PakliM�d: test | notest");//$NON-NLS-1$
		return;
	}
	try {
		myTCPport = Integer.parseInt(args[0]);	
		maxNrOfClients = Integer.parseInt(args[1]);
	} catch (NumberFormatException e) {
		System.out.println("Hib�s sz�mform�tum� a megadott param�ter!");//$NON-NLS-1$
		return;
	}

	boolean tesztPaklik;
	if (args.length == 2 || args[2].equals("notest")) {//$NON-NLS-1$
		tesztPaklik = false;
	} else if (args[2].equals("test")) {//$NON-NLS-1$
		tesztPaklik = true;
	} else {
		System.out.println("A PakliM�d csak \"test\" vagy \"notest\" lehet!");//$NON-NLS-1$
		return;
	}

	RGameServer theGameServer = new RGameServer(maxNrOfClients, tesztPaklik);
	ServerSocket serverSock = null;
	try {
		serverSock = new ServerSocket(myTCPport, maxNrOfClients);
		szr.msg.Logger.log(new szr.msg.InfoLog(SubstituteStr.doIt("RGameServer started. Listening on TCP port %p . . .", "%p", Integer.toString(serverSock.getLocalPort()))), null, null);//$NON-NLS-2$//$NON-NLS-1$
		while (true) {
			Socket sock = serverSock.accept();
			szr.msg.Logger.log(new szr.msg.InfoLog(SubstituteStr.doIt(SubstituteStr.doIt("RGameServer: Connect request from %addr:%port", "%addr", sock.getInetAddress().toString()), "%port", Integer.toString(sock.getPort()))), null, null);//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
			theGameServer.connect(sock);
		}
	} catch (ThreadDeath e) {
		szr.msg.Logger.log(new szr.msg.InfoLog("RGameServer stopped."), null, null);//$NON-NLS-1$
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			serverSock.close();
		} catch (Exception e) {
			szr.msg.Logger.log(new szr.msg.ErrStrLog("Can't close server socket!"), null, null);//$NON-NLS-1$
			szr.msg.Logger.log(new szr.msg.ErrExcLog(e), null, null);
		}
		theGameServer.disconnectAll();
	}
  } catch (Throwable exc) {
	  if (!(exc instanceof ThreadDeath)) {
		  szr.msg.Logger.log(new szr.msg.ErrStrLog("RGameServer: uncaught exception in main()."), null, null);//$NON-NLS-1$
		  szr.msg.Logger.log(new szr.msg.ErrExcLog(exc), null, null);
	  }
  }
}
public synchronized void removeMsgHandler(ServerMsgHandler smh) {
	super.removeMsgHandler(smh);
	clientSlotCount++;		// a cs�kkent�st a connect() v�gzi
}
/**
 * M�G NINCS JELSZ�KEZEL�S!
 */
public void sendPassword(String name, String email) {
	// !!! temporary:
	  // generate password
		String pass = name;
	  // send it
		szr.msg.Logger.log(new szr.msg.InfoLog(szr.shared.SubstituteStr.doIt("RGameServer: sendPassword() to %addr requested, FEATURE NOT YET IMPLEMENTED.", "%addr", email)), null, name);//$NON-NLS-2$//$NON-NLS-1$
}
}
