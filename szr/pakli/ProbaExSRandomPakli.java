package szr.pakli;

import java.util.*;
import szr.card.*;
/**
 * Tesztpaklik\P_ExS_R pakliosztály
 * Creation date: (2002.02.10. 16:28:51)
 */
public class ProbaExSRandomPakli extends RandomPakli {
protected OsJelzes nextCardJelzes() {
	byte[] cp = new byte[1];
	random.nextBytes(cp);
	int m = (cp[0] + 128) % 18;
	switch (m) {
		case 17: return new TuristaJel(TuristaJel.ID_ARR[0]);	// hogy több legyen belõle
		case 16: return new AtlatszoKerdojel();
		case 15: return new JelzeslapKerdojel();
		case 14: return new ForditoJel(TuristaJel.STYLE_ID);
		case 13: return new UtJel(UtJel.NegyesHuzoID);
		case 12: return new JelzesKeroJel(TuristaJel.STYLE_ID);
		case 11: return new SzinKeroJel(TuristaJel.STYLE_ID);
		case 10: return new ComboJel_HU();
		case 9:  return new AszJel();
		case 8:  return new LancSorozoJel();
		default: // 0..7
				 return new TuristaJel(TuristaJel.ID_ARR[m]);
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
