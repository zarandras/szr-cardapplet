package szr.pakli;

import szr.card.*;
/**
 * Klasszikus\Százas pakliosztály
 * Creation date: (2002.02.10. 16:30:30)
 */
public class UnoPakli extends NormalPakli {
protected void createACards() {}
protected void createFCards() {}
protected void createKetszinuCards(int ketszinuMask) {}
protected void createLCards() {
	byte[] cp;
	
	for (int i = 0; i < 4; ++i) {
		cp = new byte[2];
		random.nextBytes(cp);
		insertCardAt(new Card(new Lila(), new SzinKeroJel(TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
		insertCardAt(new Card(new Lila(), new UtJel(UtJel.NegyesHuzoID)), translateRandomByteToPos(cp[1]));
	}
}
protected void createSzinesCards(int szinMask) {
	byte[] cp;
	
	for (int i = 0; i < 2; ++i) {
		cp = new byte[TuristaJel.ID_ARR.length + 3];
		random.nextBytes(cp);
		insertCardAt(new Card(new SzinesSzin(szinMask), new ForditoJel(TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.KettesHuzoID)), translateRandomByteToPos(cp[1]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.IxID)), translateRandomByteToPos(cp[2]));
		for (int j = 0; j < TuristaJel.ID_ARR.length; ++j) {
			insertCardAt(new Card(new SzinesSzin(szinMask), new TuristaJel(TuristaJel.ID_ARR[j])), translateRandomByteToPos(cp[3 + j]));
		}
	}
	cp = new byte[1];
	random.nextBytes(cp);
	insertCardAt(new Card(new SzinesSzin(szinMask), new AszJel()), translateRandomByteToPos(cp[0]));
}
}
