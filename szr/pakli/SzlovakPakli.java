package szr.pakli;

import szr.card.*;
/**
 * Klasszikus\Szlovák pakliosztály
 * Creation date: (2002.02.10. 16:30:30)
 */
public class SzlovakPakli extends NormalPakli {
protected void createACards() {
	byte[] cp = new byte[7];
	random.nextBytes(cp);
	insertCardAt(new Card(new AtlatszoSzin(), new AtlatszoKerdojel()), translateRandomByteToPos(cp[0]));
	insertCardAt(new Card(new AtlatszoSzin(), new AtlatszoKerdojel()), translateRandomByteToPos(cp[1]));

	insertCardAt(new Card(new AtlatszoSzin(), new KiosztoJel()), translateRandomByteToPos(cp[2]));
	insertCardAt(new Card(new AtlatszoSzin(), new KiosztoJel()), translateRandomByteToPos(cp[3]));

	insertCardAt(new Card(new AtlatszoSzin(), new LancSorozoJel()), translateRandomByteToPos(cp[4]));

	insertCardAt(new Card(new AtlatszoSzin(), new UtSzorzoJel(UtSzorzoJel.PipaloID)), translateRandomByteToPos(cp[5]));
	insertCardAt(new Card(new AtlatszoSzin(), new UtSzorzoJel(UtSzorzoJel.DuplazoID)), translateRandomByteToPos(cp[6]));
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
	insertCardAt(new Card(new SzinesSzin(ketszinuMask), new SK_TuristaJel(SK_TuristaJel.ID_ARR[0])), translateRandomByteToPos(cp[0]));
}
protected void createLCards() {
	byte[] cp;
	
	for (int i = 0; i < 2; ++i) {
		cp = new byte[10];
		random.nextBytes(cp);
		insertCardAt(new Card(new Lila(), new SzinKeroJel(SK_TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
		insertCardAt(new Card(new Lila(), new SzinKeroJel(SK_TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[1]));
		insertCardAt(new Card(new Lila(), new UtJel(UtJel.NegyesHuzoID)), translateRandomByteToPos(cp[2]));
		insertCardAt(new Card(new Lila(), new UtJel(UtJel.NegyesHuzoID)), translateRandomByteToPos(cp[3]));
		insertCardAt(new Card(new Lila(), new KeveroJel()), translateRandomByteToPos(cp[4]));
		insertCardAt(new Card(new Lila(), new AtlatszoKerdojel()), translateRandomByteToPos(cp[5]));
		insertCardAt(new Card(new Lila(), new RecyclJel()), translateRandomByteToPos(cp[6]));
		insertCardAt(new Card(new Lila(), new TovabbadoJel()), translateRandomByteToPos(cp[7]));
		insertCardAt(new Card(new Lila(), new KiosztoJel()), translateRandomByteToPos(cp[8]));
		insertCardAt(new Card(new Lila(), new CsereloJel()), translateRandomByteToPos(cp[9]));
		
	}
}
protected void createSzinesCards(int szinMask) {
	byte[] cp;
	
	for (int i = 0; i < 2; ++i) {
		cp = new byte[SK_TuristaJel.ID_ARR.length + 9];
		random.nextBytes(cp);
		insertCardAt(new Card(new SzinesSzin(szinMask), new ForditoJel(SK_TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.HuzoID)), translateRandomByteToPos(cp[1]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.IxID)), translateRandomByteToPos(cp[2]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.KettesElhuzoID)), translateRandomByteToPos(cp[3]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.AtadoID)), translateRandomByteToPos(cp[4]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtSzorzoJel(UtSzorzoJel.FelPipaloID)), translateRandomByteToPos(cp[5]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new VarialoJel()), translateRandomByteToPos(cp[6]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new JelzeslapKerdojel()), translateRandomByteToPos(cp[7]));
		if (szinMask == SzinesSzin.Zmask) {
			insertCardAt(new Card(new SzinesSzin(szinMask), new NCHTuristaJel()), translateRandomByteToPos(cp[8]));
		}
		for (int j = 0; j < SK_TuristaJel.ID_ARR.length; ++j) {
			insertCardAt(new Card(new SzinesSzin(szinMask), new SK_TuristaJel(SK_TuristaJel.ID_ARR[j])), translateRandomByteToPos(cp[9 + j]));
		}
	}
	cp = new byte[2];
	random.nextBytes(cp);
	insertCardAt(new Card(new SzinesSzin(szinMask), new JelzesKeroJel(SK_TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
	insertCardAt(new Card(new SzinesSzin(szinMask), new AszJel()), translateRandomByteToPos(cp[1]));
}
}
