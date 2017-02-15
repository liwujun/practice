package com.wuma.inheri;

/**
 * Created by wuma
 * on 2017/2/15 at 9:32
 */
public class Life implements Alive{
    private String name = "life";
    public int lifespan=10;

    public Life() {
        name = "life";
        System.out.println("this is a live is alive");
    }

    public void breath() {
        System.out.println("Life take a breath");
    }

    public void eat() {
        System.out.println("Life eat sth in hungry");
    }

    public void sleep() {
        System.out.println("Life take a sleep");
    }

    public void visible() {
        System.out.println("Life can been seen");
    }
}
