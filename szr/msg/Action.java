package szr.msg;

import szr.mainserv.*;
/**
 * Akció õsosztály (kliens -> szerver üzenet)
 * Creation date: (2002.02.05. 16:48:02)
 */
public abstract class Action extends Msg {
/**
 * Az akció callback függvénye (mi történjen az üzenet fogadásakor?)
 */
public abstract void doServer(ServerMsgHandler smh);
}
