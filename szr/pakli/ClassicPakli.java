package szr.pakli;

import szr.card.*;
/**
 * Klasszikus\Magyar pakliosztály
 * Creation date: (2002.02.10. 16:30:30)
 */
public class ClassicPakli extends NormalPakli {
protected void createACards() {
	byte[] cp = new byte[5];
	random.nextBytes(cp);
	insertCardAt(new Card(new AtlatszoSzin(), new AtlatszoKerdojel()), translateRandomByteToPos(cp[0]));
	insertCardAt(new Card(new AtlatszoSzin(), new AtlatszoKerdojel()), translateRandomByteToPos(cp[1]));

	insertCardAt(new Card(new AtlatszoSzin(), new TovabbadoJel()), translateRandomByteToPos(cp[2]));
	insertCardAt(new Card(new AtlatszoSzin(), new TovabbadoJel()), translateRandomByteToPos(cp[3]));

	insertCardAt(new Card(new AtlatszoSzin(), new LancSorozoJel()), translateRandomByteToPos(cp[4]));
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
protected void createLCards() {
	byte[] cp;
	
	for (int i = 0; i < 2; ++i) {
		cp = new byte[10];
		random.nextBytes(cp);
		insertCardAt(new Card(new Lila(), new SzinKeroJel(TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
		insertCardAt(new Card(new Lila(), new SzinKeroJel(TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[1]));
		insertCardAt(new Card(new Lila(), new UtJel(UtJel.NegyesHuzoID)), translateRandomByteToPos(cp[2]));
		insertCardAt(new Card(new Lila(), new UtJel(UtJel.NegyesHuzoID)), translateRandomByteToPos(cp[3]));
		insertCardAt(new Card(new Lila(), new KeveroJel()), translateRandomByteToPos(cp[4]));
		insertCardAt(new Card(new Lila(), new KeveroJel()), translateRandomByteToPos(cp[5]));
		insertCardAt(new Card(new Lila(), new RecyclJel()), translateRandomByteToPos(cp[6]));
		insertCardAt(new Card(new Lila(), new TovabbadoJel()), translateRandomByteToPos(cp[7]));
		insertCardAt(new Card(new Lila(), new KiosztoJel()), translateRandomByteToPos(cp[8]));
		insertCardAt(new Card(new Lila(), new CsereloJel()), translateRandomByteToPos(cp[9]));
		
	}
}
protected void createSzinesCards(int szinMask) {
	byte[] cp;
	
	for (int i = 0; i < 2; ++i) {
		cp = new byte[TuristaJel.ID_ARR.length + 7];
		random.nextBytes(cp);
		insertCardAt(new Card(new SzinesSzin(szinMask), new ForditoJel(TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.KettesHuzoID)), translateRandomByteToPos(cp[1]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.IxID)), translateRandomByteToPos(cp[2]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.ElhuzoID)), translateRandomByteToPos(cp[3]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.PipaID)), translateRandomByteToPos(cp[4]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new VarialoJel()), translateRandomByteToPos(cp[5]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new JelzeslapKerdojel()), translateRandomByteToPos(cp[6]));
		for (int j = 0; j < TuristaJel.ID_ARR.length; ++j) {
			insertCardAt(new Card(new SzinesSzin(szinMask), new TuristaJel(TuristaJel.ID_ARR[j])), translateRandomByteToPos(cp[7 + j]));
		}
	}
	cp = new byte[2];
	random.nextBytes(cp);
	insertCardAt(new Card(new SzinesSzin(szinMask), new JelzesKeroJel(TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
	insertCardAt(new Card(new SzinesSzin(szinMask), new AszJel()), translateRandomByteToPos(cp[1]));
}
}
