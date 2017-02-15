package com.wuma.inheri;

/**
 * Created by wuma
 * on 2017/2/15 at 9:57
 */
public class People extends Animal implements Intelligence {
    public People(){
        //name="people";
        //name has private access in "wuma.inherit.Life"
        lifespan=100;
        //people life longer than animal
        System.out.println("this is a people");
    }
    public void breath(){
        System.out.println("have a breath every seconds");
    }
//    public void learn(){
//        System.out.println("high learn high mathmatics");
//    }
}
