package szr.msg;

/**
 * GameAction-feldolgozás befejezõdése eseményosztály
 * Creation date: (2002.02.06. 23:46:39)
 */
public class GEActionFinished extends GameEvent {
	private final int nextCount;
public GEActionFinished(int iNextCount) {
	super();
	nextCount = iNextCount;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().setCurrGACount(nextCount);
}
public String toString() {
	return super.toString() + " next#: " + Integer.toString(nextCount);//$NON-NLS-1$
}
}
