package szr.pakli;

import szr.card.*;
/**
 * Klasszikus\Román pakliosztály
 * Creation date: (2002.02.10. 16:30:30)
 */
public class RomanPakli extends NormalPakli {
protected void createACards() {
	byte[] cp = new byte[9];
	random.nextBytes(cp);
	insertCardAt(new Card(new AtlatszoSzin(), new AtlatszoKerdojel()), translateRandomByteToPos(cp[0]));
	insertCardAt(new Card(new AtlatszoSzin(), new AtlatszoKerdojel()), translateRandomByteToPos(cp[1]));

	insertCardAt(new Card(new AtlatszoSzin(), new CsereloJel()), translateRandomByteToPos(cp[2]));
	insertCardAt(new Card(new AtlatszoSzin(), new CsereloJel()), translateRandomByteToPos(cp[3]));

	insertCardAt(new Card(new AtlatszoSzin(), new LancSorozoJel()), translateRandomByteToPos(cp[4]));
	insertCardAt(new Card(new AtlatszoSzin(), new LancSorozoJel()), translateRandomByteToPos(cp[5]));

	insertCardAt(new Card(new AtlatszoSzin(), new UtSzorzoJel(UtSzorzoJel.PipaloID)), translateRandomByteToPos(cp[6]));
	insertCardAt(new Card(new AtlatszoSzin(), new UtSzorzoJel(UtSzorzoJel.FelPipaloID)), translateRandomByteToPos(cp[7]));
	insertCardAt(new Card(new AtlatszoSzin(), new UtSzorzoJel(UtSzorzoJel.DuplazoID)), translateRandomByteToPos(cp[8]));
}
protected void createFCards() {
	byte[] cp;
	cp = new byte[1];
	random.nextBytes(cp);
	insertCardAt(new Card(new SzinesSzin(SzinesSzin.Fmask), new AszJel()), translateRandomByteToPos(cp[0]));
}
protected void createKetszinuCards(int ketszinuMask) {
	byte[] cp;
	cp = new byte[RO_TuristaJel.ID_ARR.length];
	random.nextBytes(cp);
	for (int j = 0; j < RO_TuristaJel.ID_ARR.length; ++j) {
		insertCardAt(new Card(new SzinesSzin(ketszinuMask), new RO_TuristaJel(RO_TuristaJel.ID_ARR[j])), translateRandomByteToPos(cp[j]));
	}
}
protected void createLCards() {
	byte[] cp;
	
	for (int i = 0; i < 2; ++i) {
		cp = new byte[12];
		random.nextBytes(cp);
		insertCardAt(new Card(new Lila(), new SzinKeroJel(RO_TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
		insertCardAt(new Card(new Lila(), new SzinKeroJel(RO_TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[1]));
		insertCardAt(new Card(new Lila(), new UtJel(UtJel.NegyesHuzoID)), translateRandomByteToPos(cp[2]));
		insertCardAt(new Card(new Lila(), new UtJel(UtJel.NegyesHuzoID)), translateRandomByteToPos(cp[3]));
		insertCardAt(new Card(new Lila(), new KeveroJel()), translateRandomByteToPos(cp[4]));
		insertCardAt(new Card(new Lila(), new JelzesKeroJel(RO_TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[5]));
		insertCardAt(new Card(new Lila(), new RecyclJel()), translateRandomByteToPos(cp[6]));
		insertCardAt(new Card(new Lila(), new TovabbadoJel()), translateRandomByteToPos(cp[7]));
		insertCardAt(new Card(new Lila(), new KiosztoJel()), translateRandomByteToPos(cp[8]));
		insertCardAt(new Card(new Lila(), new CsereloJel()), translateRandomByteToPos(cp[9]));
		insertCardAt(new Card(new Lila(), new JelzeslapKerdojel()), translateRandomByteToPos(cp[10]));
		insertCardAt(new Card(new Lila(), new JelzeslapKerdojel()), translateRandomByteToPos(cp[11]));
	}
}
protected void createSzinesCards(int szinMask) {
	byte[] cp;
	
	for (int i = 0; i < 3; ++i) {
		cp = new byte[RO_TuristaJel.ID_ARR.length + 3];
		random.nextBytes(cp);
		insertCardAt(new Card(new SzinesSzin(szinMask), new ForditoJel(RO_TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new VarialoJel()), translateRandomByteToPos(cp[1]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new JelzeslapKerdojel()), translateRandomByteToPos(cp[2]));
		for (int j = 0; j < RO_TuristaJel.ID_ARR.length; ++j) {
			insertCardAt(new Card(new SzinesSzin(szinMask), new RO_TuristaJel(RO_TuristaJel.ID_ARR[j])), translateRandomByteToPos(cp[3 + j]));
		}
	}
	cp = new byte[4 + UtJel.ID_ARR.length];
	random.nextBytes(cp);
	insertCardAt(new Card(new SzinesSzin(szinMask), new JelzesKeroJel(RO_TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
	insertCardAt(new Card(new SzinesSzin(szinMask), new UtJelzesKeroJel()), translateRandomByteToPos(cp[1]));
	insertCardAt(new Card(new SzinesSzin(szinMask), new AszJel()), translateRandomByteToPos(cp[2]));
	insertCardAt(new Card(new SzinesSzin(szinMask), new AtlatszoKerdojel()), translateRandomByteToPos(cp[3]));
	for (int j = 0; j < UtJel.ID_ARR.length; ++j) {
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.ID_ARR[j])), translateRandomByteToPos(cp[4 + j]));
	}
	cp = new byte[UtSzorzoJel.ID_ARR.length];
	random.nextBytes(cp);
	for (int j = 0; j < UtSzorzoJel.ID_ARR.length; ++j) {
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtSzorzoJel(UtSzorzoJel.ID_ARR[j])), translateRandomByteToPos(cp[j]));
	}
}
public int getSzinMask() {
	return SzinesSzin.Kmask | SzinesSzin.Pmask | SzinesSzin.Smask;
}
}
