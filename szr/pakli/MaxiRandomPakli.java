package szr.pakli;

import java.util.*;
import szr.card.*;
/**
 * Maxi\Maxi_R pakliosztály
 * Creation date: (2002.02.10. 16:28:51)
 */
public class MaxiRandomPakli extends RandomPakli {
protected OsJelzes nextCardJelzes() {
	byte[] cp = new byte[2];
	random.nextBytes(cp);
	int m = (cp[0] + 128) % 7;
	int j = cp[1] + 128;
	switch (m) {
		case 0:
			switch (j % 11) {
				case 10: return new AtlatszoKerdojel();
				case 9:  return new CsereloJel();
				case 8:  return new KeveroJel();
				case 7:  return new KiosztoJel();
				case 6:  return new RecyclJel();
				case 5:  return new TovabbadoJel();
				case 4:  return new ComboJel_HU();
				case 3:  return new UtSzorzoJel(UtSzorzoJel.DuplazoID);
				case 2:  return new UtSzorzoJel(UtSzorzoJel.PipaloID);
				case 1:  return new UtSzorzoJel(UtSzorzoJel.FelPipaloID);
				default: /*case 0:*/
						 return new LancSorozoJel();
			}

		case 1:
			switch (j % 4) {
				case 3:  return new JelzesKeroJel(TuristaJel.STYLE_ID);
				case 2:  return new UtJelzesKeroJel();
				case 1:  return new SzinKeroJel(TuristaJel.STYLE_ID);
				default: /*case 0:*/
						 return new AszJel();
			}
		
		case 2:
		case 3:
			switch (j % 3) {
				case 2: return new JelzeslapKerdojel();
				case 1: return new VarialoJel();
				default: /*case 0:*/
						return new ForditoJel(TuristaJel.STYLE_ID);
			}
		
		case 4: return new UtJel(UtJel.ID_ARR[j % UtJel.ID_ARR.length]);
		
		default: /* case 5, 6 */
				return new TuristaJel(TuristaJel.ID_ARR[j % TuristaJel.ID_ARR.length]);
	}
}
protected OsSzin nextCardSzin() {
	byte[] cp = new byte[1];
	random.nextBytes(cp);
	int i = cp[0] + 128;
	if (i >= 240) {
		return new JelzeslapSzin();
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
