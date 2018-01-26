package net.soeur.qqbot.listen;

import net.soeur.qqbot.model.Model;

import java.util.Map;

public interface Listen {

     Model getModel();

     void onEvent(Events events, Map args);

}
