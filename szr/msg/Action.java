package szr.msg;

import szr.mainserv.*;
/**
 * Akci� �soszt�ly (kliens -> szerver �zenet)
 * Creation date: (2002.02.05. 16:48:02)
 */
public abstract class Action extends Msg {
/**
 * Az akci� callback f�ggv�nye (mi t�rt�njen az �zenet fogad�sakor?)
 */
public abstract void doServer(ServerMsgHandler smh);
}
