package szr.shared;

import java.io.Serializable ;
/**
 * Egy játék jellemzõit leíró rekord (a GameControlPanel és a GameController számára)
 * Creation date: (2002.02.05. 20:26:33)
 */
public class GameRec implements Serializable {
	public final String name;
	public final boolean started;
	public final int pakliIdx;
	public final int kezdetiLapSzam;

public GameRec (String iName, int iPakliIdx, int iLapSzam) {
	name = iName;
	pakliIdx = iPakliIdx;
	kezdetiLapSzam = iLapSzam;
	started = false;
}
protected GameRec (String iNev, int iPakliIdx, int iKezdetiLapSzam, boolean iStarted) {
	name = iNev;
	pakliIdx = iPakliIdx;
	kezdetiLapSzam = iKezdetiLapSzam;
	started = iStarted;
}
public GameRec notStarted() {
	return new GameRec(name, pakliIdx, kezdetiLapSzam, false);
}
/**
 * A játék elkezdve
 */
public GameRec started() {
	return new GameRec(name, pakliIdx, kezdetiLapSzam, true);
}
public String toString () {
	return ((started) ? "(" : "") + name + ((started) ? ")" : "");//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
}
