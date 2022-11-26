package szr.pakli;

import szr.card.*;
/**
 * Tesztpaklik\P_Lim pakliosztály
 * Creation date: (2002.03.05. 20:21:18)
 */
public class ProbaLimPakli extends NormalPakli {
protected void createACards() {
	byte[] cp = new byte[2];
	random.nextBytes(cp);
	insertCardAt(new Card(new AtlatszoSzin(), new AtlatszoKerdojel()), translateRandomByteToPos(cp[0]));
	insertCardAt(new Card(new AtlatszoSzin(), new JelzeslapKerdojel()), translateRandomByteToPos(cp[1]));
}
protected void createFCards() {}
/**
 * FöldSzínû lapok létrehozása és beillesztése
 */
protected void createJCards() {
	byte[] cp = new byte[2];
	random.nextBytes(cp);
	insertCardAt(new Card(new JelzeslapSzin(), new AtlatszoKerdojel()), translateRandomByteToPos(cp[0]));
	insertCardAt(new Card(new JelzeslapSzin(), new JelzeslapKerdojel()), translateRandomByteToPos(cp[1]));
}
protected void createKetszinuCards(int ketszinuMask) {}
protected void createLCards() {}
protected void createSzinesCards(int szinMask) {
	byte[] cp = new byte[1];
	random.nextBytes(cp);
	insertCardAt(new Card(new SzinesSzin(szinMask), new VarialoJel()), translateRandomByteToPos(cp[0]));
}
public void fillPakli() {
	super.fillPakli();
	createJCards();
}
public int getSzinMask() {
	return SzinesSzin.Kmask;
}
}
