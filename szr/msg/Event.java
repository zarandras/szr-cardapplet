package szr.msg;

import szr.maincli.*;
/**
 * Esem�ny �soszt�ly (szerver -> kliens �zenet)
 * Creation date: (2002.02.05. 16:47:44)
 */
public abstract class Event extends Msg {
/**
 * Az esem�ny callback f�ggv�nye (mi t�rt�njen az �zenet fogad�sakor?)
 */
public abstract void doClient(ClientMsgHandler cmh);
}
