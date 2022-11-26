package szr.pakli;

import szr.card.*;
/**
 * Teszpaklik\P_Egylapos pakliosztály
 * Creation date: (2002.03.05. 20:21:18)
 */
public class ProbaEgylaposPakli extends NormalPakli {
protected void createACards() {}
protected void createFCards() {
	pushCard(new Card(new SzinesSzin(SzinesSzin.Fmask), new TuristaJel(TuristaJel.ID_SAV)));
}
protected void createKetszinuCards(int ketszinuMask) {}
protected void createLCards() {}
protected void createSzinesCards(int szinMask) {}
public int getSzinMask() {
	return Szin.EmptyMask;
}
}
