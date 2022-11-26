package szr.caut;

/**
 * Automata végállapot
 * Creation date: (2002.02.14. 20:25:06)
 */
public class LASNyert extends LAState {
public LASNyert() {
	super();
}
public int getActivePlayerState() {
	return szr.maincli.ClientController.PS_NYERT;
}
public int getInactivePlayerState() {
	return szr.maincli.ClientController.PS_VESZTETT;
}
public szr.card.Jelzes getJelzes() {
	return null;
}
public szr.card.Szin getSzin() {
	return null;
}
public boolean isAutoPasszState() {
	return false;
}
protected LAState nextStateBeszurCJ(int transPhase, int invokeCount) {
	return null;
}
protected LAState nextStateLerakCJ(int transPhase, int invokeCount) {
	return null;
}
protected LAState nextStateNyer(int transPhase, int invokeCount) {
	return null;
}
protected LAState nextStatePassz(int transPhase, int invokeCount) {
	return null;
}
public String toString() {
	return "((LASNyert))";//$NON-NLS-1$
}
}
