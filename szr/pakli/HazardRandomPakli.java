package szr.pakli;

import szr.card.*;
/**
 * Extrém\Hazárd_R pakliosztály
 * Creation date: (2002.03.27. 19:20:35)
 */
public class HazardRandomPakli extends RandomPakli {
public int getSzinMask() {
	return SzinesSzin.Pmask | SzinesSzin.Zmask;
}
protected OsJelzes nextCardJelzes() {
	byte[] cp = new byte[2];
	random.nextBytes(cp);
	int m = (cp[0] + 128) % 37;
	int j = cp[1] + 128;
	switch (m) {
		case 0:
			return new KeveroJel();
		case 1:
			return new KiosztoJel();
		case 2:
			return new RecyclJel();
		case 3:
			return new UtJelzesKeroJel();
		case 4:
			return new VarialoJel();
		case 5:
		case 6:
			return new AtlatszoKerdojel();
		case 7:
		case 8:
			return new JelzeslapKerdojel();
		case 9:
		case 10:
		case 11:
			return new ForditoJel(SK_TuristaJel.STYLE_ID);
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
			return new UtSzorzoJel(UtSzorzoJel.ID_ARR[j % UtSzorzoJel.ID_ARR.length]);

		default:	// 17..36
			return new UtJel(UtJel.ID_ARR[j % UtJel.ID_ARR.length]);
	}
}
protected OsSzin nextCardSzin() {
	byte[] cp = new byte[1];
	random.nextBytes(cp);
	int i = cp[0] + 128;
	if (i >= 35) {
		switch (i % 3) {
			case 0:  return new SzinesSzin(SzinesSzin.Pmask);
			case 1:  return new SzinesSzin(SzinesSzin.Zmask);
			case 2:  return new SzinesSzin(SzinesSzin.Fmask);
			default: return null;
		}
	} else if (i >= 15) {
		return new AtlatszoSzin();
	} else if (i >= 5) {
		return new Lila();
	} else {
		return new HalmozoSzinKeroSzin();
	}
}
}
