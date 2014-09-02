package org.jglue.totorom;


/**
 * @author Bryn Cooke (http://jglue.org)
 */

public class Knows extends FramedEdge {

    public void setYears(int years) {
        setProperty("years", years);
    }

    public int getYears() {
        return getProperty("years");
    }
}
