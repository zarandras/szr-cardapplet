package szr.pakli;

import szr.card.*;
/**
 * Mini\Mini pakliosztály
 * Creation date: (2002.02.10. 16:30:30)
 */
public class MiniPakli extends NormalPakli {
protected void createACards() {
	byte[] cp = new byte[2];
	random.nextBytes(cp);
	insertCardAt(new Card(new AtlatszoSzin(), new AtlatszoKerdojel()), translateRandomByteToPos(cp[0]));
	insertCardAt(new Card(new AtlatszoSzin(), new AtlatszoKerdojel()), translateRandomByteToPos(cp[1]));
}
protected void createFCards() {
	byte[] cp;
	cp = new byte[1];
	random.nextBytes(cp);
	insertCardAt(new Card(new SzinesSzin(SzinesSzin.Fmask), new AszJel()), translateRandomByteToPos(cp[0]));
}
protected void createKetszinuCards(int ketszinuMask) {
	byte[] cp;
	cp = new byte[1];
	random.nextBytes(cp);
	insertCardAt(new Card(new SzinesSzin(ketszinuMask), new TuristaJel(TuristaJel.ID_ARR[0])), translateRandomByteToPos(cp[0]));
}
protected void createLCards() {}
protected void createSzinesCards(int szinMask) {
	byte[] cp;
	
	for (int i = 0; i < 2; ++i) {
		cp = new byte[TuristaJel.ID_ARR.length + 2];
		random.nextBytes(cp);
		insertCardAt(new Card(new SzinesSzin(szinMask), new ForditoJel(TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new JelzeslapKerdojel()), translateRandomByteToPos(cp[1]));
		for (int j = 0; j < TuristaJel.ID_ARR.length; ++j) {
			insertCardAt(new Card(new SzinesSzin(szinMask), new TuristaJel(TuristaJel.ID_ARR[j])), translateRandomByteToPos(cp[2 + j]));
		}
	}
	cp = new byte[1];
	random.nextBytes(cp);
	insertCardAt(new Card(new SzinesSzin(szinMask), new AszJel()), translateRandomByteToPos(cp[0]));
}
}
