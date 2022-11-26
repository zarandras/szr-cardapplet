package szr.pakli;

import java.util.*;
import szr.card.*;
/**
 * Tesztpaklik\P_MindentBele_R pakliosztály
 * Creation date: (2002.02.10. 16:28:51)
 */
public class ProbaRandomPakli extends RandomPakli {
protected OsJelzes nextCardJelzes() {
	byte[] cp = new byte[2];
	random.nextBytes(cp);
	int m = (cp[0] + 128) % 26;
	int j = cp[1] + 128;
	switch (m) {
		case 25: return new AtlatszoKerdojel();
		case 24: return new JelzeslapKerdojel();
		case 23: return new CsereloJel();
		case 22: return new FelkialtoJel();
		case 21: return new KeveroJel();
		case 20: return new KiosztoJel();
		case 19: return new RecyclJel();
		case 18: return new TovabbadoJel();
		case 17: return new ComboJel_HU();
		case 16: return new VarialoJel();
		case 15:
			switch (j % 3) {
				case 0:  return new ForditoJel(RO_TuristaJel.STYLE_ID);
				case 1:	 return new ForditoJel(SK_TuristaJel.STYLE_ID);
				default: return new ForditoJel(TuristaJel.STYLE_ID);
			}
		case 14: return new UtJel(UtJel.ID_ARR[j % UtJel.ID_ARR.length]);
		case 13: return new UtSzorzoJel(UtSzorzoJel.ID_ARR[j % UtSzorzoJel.ID_ARR.length]);
		case 12:
			switch (j % 3) {
				case 0:  return new JelzesKeroJel(RO_TuristaJel.STYLE_ID);
				case 1:	 return new JelzesKeroJel(SK_TuristaJel.STYLE_ID);
				default: return new JelzesKeroJel(TuristaJel.STYLE_ID);
			}
		case 11: return new UtJelzesKeroJel();
		case 10:
			switch (j % 3) {
				case 0:  return new SzinKeroJel(RO_TuristaJel.STYLE_ID);
				case 1:	 return new SzinKeroJel(SK_TuristaJel.STYLE_ID);
				default: return new SzinKeroJel(TuristaJel.STYLE_ID);
			}
		case 9: return new AszJel();
		case 8: return new LancSorozoJel();
		
		case 7:
		case 6:
		case 5:
				return new TuristaJel(TuristaJel.ID_ARR[j % TuristaJel.ID_ARR.length]);

		case 4:
		case 3:
				return new SK_TuristaJel(SK_TuristaJel.ID_ARR[j % SK_TuristaJel.ID_ARR.length]);
		case 2: return new NCHTuristaJel();

		default:	// 0..1
				return new RO_TuristaJel(RO_TuristaJel.ID_ARR[j % RO_TuristaJel.ID_ARR.length]);
	}
}
protected OsSzin nextCardSzin() {
	byte[] cp = new byte[1];
	random.nextBytes(cp);
	int i = cp[0] + 128;
	if (i >= 200) {
		if (i >= 230) {
			return new HalmozoSzinKeroSzin();
		} else if (i >= 215) {
			return new HalmozoJelzesKeroSzin_HU();
		} else {
			return new JelzeslapSzin();
		}
	} else if (i >= 80) {
		switch (i % 4) {
			case 0:  return new SzinesSzin(SzinesSzin.Kmask);
			case 1:  return new SzinesSzin(SzinesSzin.Pmask);
			case 2:  return new SzinesSzin(SzinesSzin.Zmask);
			case 3:  return new SzinesSzin(SzinesSzin.Smask);
			default: return null;
		}
	} else if (i >= 60) {
		return new Lila();
	} else if (i >= 40) {
		return new AtlatszoSzin();
	} else if (i >= 36) {
		return new SzinesSzin(SzinesSzin.Fmask);
	} else {
		switch (i % 6) {
			case 0:  return new SzinesSzin(SzinesSzin.Kmask | SzinesSzin.Pmask);
			case 1:	 return new SzinesSzin(SzinesSzin.Kmask | SzinesSzin.Zmask);
			case 2:  return new SzinesSzin(SzinesSzin.Kmask | SzinesSzin.Smask);
			case 3:  return new SzinesSzin(SzinesSzin.Pmask | SzinesSzin.Zmask);
			case 4:  return new SzinesSzin(SzinesSzin.Pmask | SzinesSzin.Smask);
			case 5:  return new SzinesSzin(SzinesSzin.Zmask | SzinesSzin.Smask);
			default: return null;
		}
	}
}
}
