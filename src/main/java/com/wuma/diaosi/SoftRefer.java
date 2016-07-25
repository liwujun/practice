package com.wuma.diaosi;

import com.wuma.bean.User;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * Created by liwujun
 * on 2016/1/4 at 14:06
 */
public class SoftRefer {

    static ReferenceQueue<User> softQueue = null;

    public static class CheckRefQueue extends Thread {
        public void run() {
            while (true) {
                if (softQueue != null) {
                    UserSoftReference obj = null;
                    try {
                        obj = (UserSoftReference) softQueue.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (obj != null)
                        System.out.println("user id:" + obj.uid + " is deleted.");
                }
            }
        }
    }

    public static class UserSoftReference extends SoftReference<User> {
        String uid;

        public UserSoftReference(User referent, ReferenceQueue<? super User> q) {
            super(referent, q);
            uid = referent.getId();
        }
    }

    public static void main(String[] args) {
        User u = new User("1", "zhangsan");
        SoftReference<User> userSoftReference = new SoftReference<User>(u);
        u = null;
        System.out.println(userSoftReference.get());
        System.gc();
        System.out.println("After GC:");
        System.out.println(userSoftReference.get());
        byte[] b = new byte[1024 * 925 * 7];
        System.gc();
        System.out.println(userSoftReference.get());
    }

    public static void softTest() {
        Thread t = new CheckRefQueue();
        t.setDaemon(true);
        t.start();
        User u = new User("2", "mmm");
        softQueue = new ReferenceQueue<User>();
        UserSoftReference userSoftReference = new UserSoftReference(u, softQueue);
        u = null;

    }
}
