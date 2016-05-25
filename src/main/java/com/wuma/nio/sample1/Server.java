package com.wuma.nio.sample1;

/**
 * Created by liwujun
 * on 2016/2/23 at 15:39
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ConcurrentLinkedQueue<SelectionKey> m_conn = new ConcurrentLinkedQueue<SelectionKey>();
    private ConcurrentLinkedQueue<SelectionKey> m_req = new ConcurrentLinkedQueue<SelectionKey>();
    private final int m_processNum = 3;
    private final int m_worksNum = 3;
    private final int m_port = 3562;
    private ServerSocketChannel channel;
    private boolean connQuEpt = true;
    private boolean reqQuEpt = true;
    private Selector selector;//for connection
    private List<Selector> m_reqSelector = new ArrayList<Selector>();


    public void listen() throws IOException {
        selector = Selector.open();
        channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(m_port));
        channel.register(selector, SelectionKey.OP_ACCEPT);

        new Thread(new ConnectionHander()).start();

        //new Thread(new RequestManager()).start();
        creatRequestHanders();

        new Thread(new ProcessManager()).start();

    }

	/*class RequestManager implements Runnable {
        private ExecutorService m_reqPool;
		public RequestManager() {
			m_reqPool = Executors.newFixedThreadPool(m_processNum, new RequestThreadFactor());
		}
		public void run() {
			while (true) {

			}
		}
	}*/

    void creatRequestHanders() {
        try {
            for (int i = 0; i < m_processNum; ++i) {
                Selector slt = Selector.open();
                m_reqSelector.add(slt);
                RequestHander req = new RequestHander();
                req.setSelector(slt);
                new Thread(req).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ProcessManager implements Runnable {
        private ExecutorService m_workPool;

        public ProcessManager() {
            m_workPool = Executors.newFixedThreadPool(m_worksNum);
        }

        public void run() {
            SelectionKey key;
            while (true) {
                //̫����cpu//Ӧ��Ҫ��һ��wait������������������
                while ((key = m_req.poll()) != null) {
                    ProcessRequest preq = new ProcessRequest();
                    preq.setKey(key);
                    m_workPool.execute(preq);
                }
            }
        }
    }


	/*class RequestThread extends Thread {
        private Selector selector;
		public  RequestThread(Runnable r) {
			super(r);
			try {
			selector = Selector.open();
			}
			catch(IOException e) {
				e.printStackTrace();
				//todo
			}
		}
	}

	class RequestThreadFactor implements ThreadFactory {
		public Thread newThread(Runnable r) {
			return new RequestThread(r);
		}
	}*/


    class ConnectionHander implements Runnable {

        int idx = 0;


        public void run() {

            System.out.println("ConnectionHandler listenning to connection");
            while (true) {

                try {
                    selector.select();
                    Set<SelectionKey> selectKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectKeys.iterator();

                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();
                        System.out.println("m_conn add key " + key);
                        m_conn.add(key);
                        int num = m_reqSelector.size();
                        m_reqSelector.get(idx).wakeup();//��ֹ����request�Ľ��̶��ڶ�����
                        idx = (idx + 1) % num;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    class RequestHander implements Runnable {
        private Selector selector;

        public void setSelector(Selector slt) {
            selector = slt;
        }

        public void run() {
            try {
                SelectionKey key;
                System.out.println(Thread.currentThread() + "listenning to request");
                while (true) {
                    selector.select();
                    while ((key = m_conn.poll()) != null) {
                        System.out.println("key is not null ,key: " + key);
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        SocketChannel sc = ssc.accept();//����һ������
                        if (sc != null) {
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(Thread.currentThread() + " a connected line");
                        } else {
                            System.out.println("sc is null.key:" + key);
                        }
                    }

                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> it = keys.iterator();
                    while (it.hasNext()) {
                        SelectionKey keytmp = it.next();
                        it.remove();
                        if (keytmp.isReadable()) {
                            m_req.add(keytmp);
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    class ProcessRequest implements Runnable {
        SelectionKey key;

        public void setKey(SelectionKey key) {
            this.key = key;
        }

        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            SocketChannel sc = (SocketChannel) key.channel();
            String msg = null;
            try {
                int readBytes = 0;
                int ret;
                try {
                    while ((ret = sc.read(buffer)) > 0) {
                        readBytes += ret;
                    }
                } catch (IOException e) {

                } finally {
                    buffer.flip();
                }
                if (readBytes > 0) {
                    msg = Charset.forName("utf-8").decode(buffer).toString();
                    buffer = null;
                }
            } finally {
                if (buffer != null)
                    buffer.clear();
            }
            try {
                if (msg != null) {
                    System.out.println(Thread.currentThread() + " server received [ " + msg + "] from client address : " /**+ sc.getRemoteAddress()**/);
                }
                Thread.sleep(2000);
                if (msg != null) {
                    sc.write(ByteBuffer.wrap((msg + " server response ").getBytes(Charset.forName("utf-8"))));
                }
            } catch (Exception e) {

            }


        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Server server = new Server();
        try {
            server.listen();
        } catch (IOException e) {

        }
    }

}

