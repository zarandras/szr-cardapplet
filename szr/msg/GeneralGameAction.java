package szr.msg;

/**
 * Általános játék-akciók õsosztálya (ténylegesen a játékot befolyásoló akciók)
 * Creation date: (2002.03.02. 11:42:08)
 */
public abstract class GeneralGameAction extends Action {
public String toString() {
	return "---> " + super.toString() + " <---";//$NON-NLS-2$//$NON-NLS-1$
}
}
