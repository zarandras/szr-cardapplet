package szr.pakli;

import java.util.*;
import szr.card.*;
/**
 * Tesztpaklik\P_AzHat_R pakliosztály
 * Creation date: (2002.02.10. 16:28:51)
 */
public class ProbaAzHatRandomPakli extends RandomPakli {
protected OsJelzes nextCardJelzes() {
	byte[] cp = new byte[1];
	random.nextBytes(cp);
	int m = (cp[0] + 128) % 24;
	switch (m) {
		case 23: return new AtlatszoKerdojel();
		case 22: return new JelzeslapKerdojel();
		case 21: return new ForditoJel(SK_TuristaJel.STYLE_ID);
		case 20: return new CsereloJel();
		case 19: return new FelkialtoJel();
		case 18: return new KeveroJel();
		case 17: return new KiosztoJel();
		case 16: return new RecyclJel();
		case 15: return new TovabbadoJel();
		case 14: return new JelzesKeroJel(SK_TuristaJel.STYLE_ID);
		case 13: return new VarialoJel();
		case 12: return new SzinKeroJel(SK_TuristaJel.STYLE_ID);
		case 11: return new AszJel();
		case 10: return new LancSorozoJel();
		case 9:  return new NCHTuristaJel();
		default:	// 0..8
				 return new SK_TuristaJel(SK_TuristaJel.ID_ARR[m]);
	}
}
protected OsSzin nextCardSzin() {
	byte[] cp = new byte[1];
	random.nextBytes(cp);
	int i = cp[0] + 128;
	if (i >= 80) {
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
