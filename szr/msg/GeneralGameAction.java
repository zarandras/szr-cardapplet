package szr.msg;

/**
 * �ltal�nos j�t�k-akci�k �soszt�lya (t�nylegesen a j�t�kot befoly�sol� akci�k)
 * Creation date: (2002.03.02. 11:42:08)
 */
public abstract class GeneralGameAction extends Action {
public String toString() {
	return "---> " + super.toString() + " <---";//$NON-NLS-2$//$NON-NLS-1$
}
}
