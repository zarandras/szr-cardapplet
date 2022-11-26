package szr.pakli;

import szr.card.*;
/**
 * Maxi\Maxi pakliosztály
 * Creation date: (2002.02.10. 16:30:30)
 */
public class MaxiPakli extends NormalPakli {
protected void createACards() {
	byte[] cp = new byte[10];
	random.nextBytes(cp);
	insertCardAt(new Card(new AtlatszoSzin(), new AtlatszoKerdojel()), translateRandomByteToPos(cp[0]));
	insertCardAt(new Card(new AtlatszoSzin(), new AtlatszoKerdojel()), translateRandomByteToPos(cp[1]));

	insertCardAt(new Card(new AtlatszoSzin(), new TovabbadoJel()), translateRandomByteToPos(cp[2]));
	insertCardAt(new Card(new AtlatszoSzin(), new TovabbadoJel()), translateRandomByteToPos(cp[3]));

	insertCardAt(new Card(new AtlatszoSzin(), new LancSorozoJel()), translateRandomByteToPos(cp[4]));
	insertCardAt(new Card(new AtlatszoSzin(), new ComboJel_HU()), translateRandomByteToPos(cp[5]));

	insertCardAt(new Card(new AtlatszoSzin(), new UtSzorzoJel(UtSzorzoJel.PipaloID)), translateRandomByteToPos(cp[6]));
	insertCardAt(new Card(new AtlatszoSzin(), new UtSzorzoJel(UtSzorzoJel.FelPipaloID)), translateRandomByteToPos(cp[7]));
	insertCardAt(new Card(new AtlatszoSzin(), new UtSzorzoJel(UtSzorzoJel.DuplazoID)), translateRandomByteToPos(cp[8]));
	insertCardAt(new Card(new AtlatszoSzin(), new UtSzorzoJel(UtSzorzoJel.DuplazoID)), translateRandomByteToPos(cp[9]));
}
protected void createFCards() {
	byte[] cp;
	cp = new byte[2];
	random.nextBytes(cp);
	insertCardAt(new Card(new SzinesSzin(SzinesSzin.Fmask), new AszJel()), translateRandomByteToPos(cp[0]));
	insertCardAt(new Card(new SzinesSzin(SzinesSzin.Fmask), new ForditoJel(TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[1]));
}
/**
 * FöldSzínû lapok létrehozása és beillesztése
 */
protected void createJCards() {
	byte[] cp = new byte[TuristaJel.ID_ARR.length];
	random.nextBytes(cp);
	for (int j = 0; j < TuristaJel.ID_ARR.length; ++j) {
		insertCardAt(new Card(new JelzeslapSzin(), new TuristaJel(TuristaJel.ID_ARR[j])), translateRandomByteToPos(cp[j]));
	}
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
		cp = new byte[13];
		random.nextBytes(cp);
		insertCardAt(new Card(new Lila(), new SzinKeroJel(TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
		insertCardAt(new Card(new Lila(), new SzinKeroJel(TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[1]));
		insertCardAt(new Card(new Lila(), new UtJel(UtJel.NegyesHuzoID)), translateRandomByteToPos(cp[2]));
		insertCardAt(new Card(new Lila(), new UtJel(UtJel.NegyesHuzoID)), translateRandomByteToPos(cp[3]));
		insertCardAt(new Card(new Lila(), new UtJel(UtJel.KettesPipaID)), translateRandomByteToPos(cp[4]));
		insertCardAt(new Card(new Lila(), new UtJel(UtJel.KettesElhuzoID)), translateRandomByteToPos(cp[5]));
		insertCardAt(new Card(new Lila(), new UtJel(UtJel.KettesAtadoID)), translateRandomByteToPos(cp[6]));
		insertCardAt(new Card(new Lila(), new KeveroJel()), translateRandomByteToPos(cp[7]));
		insertCardAt(new Card(new Lila(), new KeveroJel()), translateRandomByteToPos(cp[8]));
		insertCardAt(new Card(new Lila(), new RecyclJel()), translateRandomByteToPos(cp[9]));
		insertCardAt(new Card(new Lila(), new TovabbadoJel()), translateRandomByteToPos(cp[10]));
		insertCardAt(new Card(new Lila(), new KiosztoJel()), translateRandomByteToPos(cp[11]));
		insertCardAt(new Card(new Lila(), new CsereloJel()), translateRandomByteToPos(cp[12]));
	}
}
protected void createSzinesCards(int szinMask) {
	byte[] cp;
	
	for (int i = 0; i < 2; ++i) {
		cp = new byte[TuristaJel.ID_ARR.length + 9];
		random.nextBytes(cp);
		insertCardAt(new Card(new SzinesSzin(szinMask), new ForditoJel(TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.HuzoID)), translateRandomByteToPos(cp[1]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.KettesHuzoID)), translateRandomByteToPos(cp[2]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.IxID)), translateRandomByteToPos(cp[3]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.ElhuzoID)), translateRandomByteToPos(cp[4]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.PipaID)), translateRandomByteToPos(cp[5]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new UtJel(UtJel.AtadoID)), translateRandomByteToPos(cp[6]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new VarialoJel()), translateRandomByteToPos(cp[7]));
		insertCardAt(new Card(new SzinesSzin(szinMask), new JelzeslapKerdojel()), translateRandomByteToPos(cp[8]));
		for (int j = 0; j < TuristaJel.ID_ARR.length; ++j) {
			insertCardAt(new Card(new SzinesSzin(szinMask), new TuristaJel(TuristaJel.ID_ARR[j])), translateRandomByteToPos(cp[9 + j]));
		}
	}
	cp = new byte[3];
	random.nextBytes(cp);
	insertCardAt(new Card(new SzinesSzin(szinMask), new JelzesKeroJel(TuristaJel.STYLE_ID)), translateRandomByteToPos(cp[0]));
	insertCardAt(new Card(new SzinesSzin(szinMask), new UtJelzesKeroJel()), translateRandomByteToPos(cp[1]));
	insertCardAt(new Card(new SzinesSzin(szinMask), new AszJel()), translateRandomByteToPos(cp[2]));
}
public void fillPakli() {
	super.fillPakli();
	createJCards();
}
}
