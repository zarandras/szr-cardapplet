package szr.msg;

/**
 * Hálózati kapcsolat bontása eseményosztály
 * Creation date: (2002.02.06. 22:58:09)
 */
public class PEBontas extends PersonalEvent {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	
public PEBontas() {
	super();
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().rejectedAction(resInfoTexts.getString("A_szerver_felmondta_a_kapc"), szr.maincli.RejectDlgHandler.PSR_ERROR_FATAL); //$NON-NLS-1$
//	cmh.disconnect();	<- a ClientMsgHandler hívja meg
}
public String toString() {
	return super.toString();
}
}
