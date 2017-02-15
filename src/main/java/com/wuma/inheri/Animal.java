package com.wuma.inheri;

/**
 * Created by wuma
 * on 2017/2/15 at 9:53
 */
public class Animal extends Life implements Alive,Breed,Emotion,Intelligence,Practisee{
    public Animal(){
        System.out.println("this is a animal.");
    }
    public void mate() {
        System.out.println("animal mate");
    }

    public void raisechild() {
        System.out.println("animal raise children");
    }

    public void sad() {
        System.out.println("animal is sad");
    }

    public void happy() {
        System.out.println("animal is happy");
    }

    public void peace() {
        System.out.println("animal is peace");
    }

    public void grieve() {
        System.out.println("animal is grieve");
    }

    public void think() {
        System.out.println("animal think");
    }

    public void look() {
        System.out.println("animal look");
    }

    public void earnALive() {
        System.out.println("animal earn a live");
    }

    public void learn() {
        System.out.println("animal learn sth");
    }
}
