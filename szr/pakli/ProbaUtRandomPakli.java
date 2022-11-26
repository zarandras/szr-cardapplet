package szr.pakli;

import java.util.*;
import szr.card.*;
/**
 * Tesztpaklik\P_Ut_R pakliosztály
 * Creation date: (2002.02.10. 16:28:51)
 */
public class ProbaUtRandomPakli extends RandomPakli {
public int getSzinMask() {
	return SzinesSzin.Kmask | SzinesSzin.Pmask | SzinesSzin.Smask;
}
protected OsJelzes nextCardJelzes() {
	byte[] cp = new byte[2];
	random.nextBytes(cp);
	int m = (cp[0] + 128) % 4;
	int j = cp[1] + 128;
	switch (m) {
		case 0:
			switch (j % 8) {
				case 7: return new AtlatszoKerdojel();
				case 6: return new JelzeslapKerdojel();
				case 5: return new ForditoJel(RO_TuristaJel.STYLE_ID);
				case 4: return new JelzesKeroJel(RO_TuristaJel.STYLE_ID);
				case 3: return new UtJelzesKeroJel();
				case 2: return new SzinKeroJel(RO_TuristaJel.STYLE_ID);
				case 1: return new AszJel();
				default: /*case 0:*/
						return new LancSorozoJel();
			}
		case 1: return new UtJel(UtJel.ID_ARR[j % UtJel.ID_ARR.length]);
		case 2: return new UtSzorzoJel(UtSzorzoJel.ID_ARR[j % UtSzorzoJel.ID_ARR.length]);
		default:/*case 3:*/
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
		} else {
			return new JelzeslapSzin();
		}
	} else if (i >= 80) {
		switch (i % 3) {
			case 0:  return new SzinesSzin(SzinesSzin.Kmask);
			case 1:  return new SzinesSzin(SzinesSzin.Pmask);
			case 2:  return new SzinesSzin(SzinesSzin.Smask);
			default: return null;
		}
	} else if (i >= 60) {
		return new Lila();
	} else if (i >= 40) {
		return new AtlatszoSzin();
	} else if (i >= 36) {
		return new SzinesSzin(SzinesSzin.Fmask);
	} else {
		switch (i % 3) {
			case 0:  return new SzinesSzin(SzinesSzin.Kmask | SzinesSzin.Pmask);
			case 1:  return new SzinesSzin(SzinesSzin.Kmask | SzinesSzin.Smask);
			case 2:  return new SzinesSzin(SzinesSzin.Pmask | SzinesSzin.Smask);
			default: return null;
		}
	}
}
}
