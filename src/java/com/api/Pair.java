/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api;

/**
 *
 * @author ecolak
 */
public class Pair<First, Second> {
    private First first;
    private Second second;

    public Pair(){}

    public Pair(First first, Second second){
        this.first = first;
        this.second = second;
    }

    /**
     * @return the first
     */
    public First getFirst() {
        return first;
    }

    /**
     * @param first the first to set
     */
    public void setFirst(First first) {
        this.first = first;
    }

    /**
     * @return the second
     */
    public Second getSecond() {
        return second;
    }

    /**
     * @param second the second to set
     */
    public void setSecond(Second second) {
        this.second = second;
    }
}
