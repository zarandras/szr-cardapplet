package szr.shared;

import java.io.Serializable;
/**
 * Játék- ill. kért irányt reprezentáló osztály
 * Creation date: (2002.03.07. 21:00:22)
 */
public class Irany implements Serializable {
	private static java.util.ResourceBundle resWidgetTexts = java.util.ResourceBundle.getBundle("szr.shared.WidgetTexts");  //$NON-NLS-1$
	private final boolean lefele;
	public final static Irany Lefele = new Irany(true);
	public final static Irany Felfele = new Irany(false);
public Irany(Irany merre) {
	try {
		lefele = merre.isLefele();
	} catch (NullPointerException e) {
		lefele = true;
	}
}
protected Irany(boolean iLefele) {
	super();
	lefele = iLefele;
}
public boolean isFelfele() {
	return !lefele;
}
public boolean isLefele() {
	return lefele;
}
public boolean megegyezik(Irany ir) {
	try {
		return ir.isLefele() == lefele;
	} catch (NullPointerException e) {
		return false;
	}
}
public String toString() {
	return (lefele) ? resWidgetTexts.getString("Le") : resWidgetTexts.getString("Fel"); //$NON-NLS-2$ //$NON-NLS-1$
}
public Irany visszafele() {
	return new Irany(!lefele);
}
}
