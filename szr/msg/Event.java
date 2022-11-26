package szr.msg;

import szr.maincli.*;
/**
 * Esemény õsosztály (szerver -> kliens üzenet)
 * Creation date: (2002.02.05. 16:47:44)
 */
public abstract class Event extends Msg {
/**
 * Az esemény callback függvénye (mi történjen az üzenet fogadásakor?)
 */
public abstract void doClient(ClientMsgHandler cmh);
}
