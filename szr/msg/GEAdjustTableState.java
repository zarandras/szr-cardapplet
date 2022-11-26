package szr.msg;

import szr.shared.*;
/**
 * JátékTér állapotának változása eseményosztály
 * Creation date: (2002.03.02. 17:07:16)
 */
public class GEAdjustTableState extends GameEvent {
	private final TableStateRec tableState;
public GEAdjustTableState(TableStateRec iTableState) {
	super();
	tableState = iTableState;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().getGameTable().setTableState(tableState);
}
public String toString() {
	return super.toString() + " " + tableState.toString();//$NON-NLS-1$
}
}
