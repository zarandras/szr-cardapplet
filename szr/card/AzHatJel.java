package szr.card;

/**
 * 'Azonnali hat�ly�' jelz�sek �soszt�lya
 * Creation date: (2002.03.05. 7:31:27)
 */
public abstract class AzHatJel extends Jelzes implements SingleVetoJel {
public boolean getDoublePicFlag() {
	return false;
}
public boolean hasKetszinuImg() {
	return false;
}
}
