package szr.pakli;

import java.util.*;
import szr.card.*;
/**
 * Mini\Mini_R pakliosztály
 * Creation date: (2002.02.10. 16:28:51)
 */
public class MiniRandomPakli extends RandomPakli {
protected OsJelzes nextCardJelzes() {
	byte[] cp = new byte[1];
	random.nextBytes(cp);
	int m = (cp[0] + 128) % 11;
	switch (m) {
		case 8:	 return new AtlatszoKerdojel();
		case 9:  return new ForditoJel(TuristaJel.STYLE_ID);
		case 10: return new JelzeslapKerdojel();
		default: // 0..7
				 return new TuristaJel(TuristaJel.ID_ARR[m]);
	}
}
protected OsSzin nextCardSzin() {
	byte[] cp = new byte[1];
	random.nextBytes(cp);
	int i = cp[0] + 128;
	if (i >= 50) {
		switch (i % 4) {
			case 0:  return new SzinesSzin(SzinesSzin.Kmask);
			case 1:  return new SzinesSzin(SzinesSzin.Pmask);
			case 2:  return new SzinesSzin(SzinesSzin.Zmask);
			case 3:  return new SzinesSzin(SzinesSzin.Smask);
			default: return null;
		}
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
