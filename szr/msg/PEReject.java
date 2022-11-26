package szr.msg;

import szr.maincli.*;
/**
 * Akció elutasítása / Figyelmeztetõ üzenet eseményosztály
 * Creation date: (2002.02.06. 13:49:29)
 */
public class PEReject extends PersonalEvent {
	private final java.lang.String rejectInfoText;
	private final int helpPState;
public PEReject(String iRejectInfoText) {
	super();
	rejectInfoText = iRejectInfoText;
	helpPState = szr.maincli.ClientController.PS_NONE;
}
public PEReject(String iRejectInfoText, int iHelpPState) {
	super();
	rejectInfoText = iRejectInfoText;
	helpPState = iHelpPState;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	szr.maincli.ClientController cc = cmh.getParentController();
	if (cc.isActionSentFlag())
		cc.clearActionSentFlag();
	if (helpPState != szr.maincli.ClientController.PS_NONE)
		cmh.getParentController ().rejectedAction(rejectInfoText, helpPState);
	else 
		cmh.getParentController ().rejectedAction(rejectInfoText);
}
public String toString() {
	return super.toString() + " PS: " + Integer.toString(helpPState) + " MSG:" + rejectInfoText.replace('\n',' ');//$NON-NLS-2$//$NON-NLS-1$
}
}
