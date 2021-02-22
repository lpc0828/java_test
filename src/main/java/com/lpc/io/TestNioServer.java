package com.lpc.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * NIO 基于事件驱动 (Non Blocking I/O)
 * 可接受：接收，读，写，连接，断开
 */
public class TestNioServer {


    static void startServer(int port) {
        ConcurrentHashMap<Integer, SocketChannel> chm = new ConcurrentHashMap<Integer, SocketChannel>();

        try {
            Selector selector = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress(port);
            ssc.bind(address);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server start, begin to listen on port:" + port);
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();

                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    String msg = new String();
                    if (key.isAcceptable()) {
                        ServerSocketChannel sscNew = (ServerSocketChannel) key.channel();
                        SocketChannel sc = sscNew.accept();
                        sc.configureBlocking(false);
                        // Add the new connection to the selector
                        sc.register(selector, SelectionKey.OP_READ);
                        // Add the socket channel to the list
                        chm.put(sc.hashCode(), sc);
                        it.remove();
                    } else if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer echoBuffer = ByteBuffer.allocate(4);
                        int code = 0;
                        while ((code = sc.read(echoBuffer)) > 0) {
                            byte b[] = new byte[echoBuffer.position()];
                            echoBuffer.flip();
                            echoBuffer.get(b);
                            msg+=new String(b, "UTF-8");
                        }
                        if (code == -1 || msg.toUpperCase().indexOf("BYE") > -1) {
                            chm.remove(sc.hashCode());
                        } else {
                            //code=0，消息读完或者echoBuffer空间不够时，部分消息内容下一次select后收到
                            echoBuffer.clear();
                        }
                        it.remove();
                        System.out.println("msg: " + msg  + " from: " + sc + "code:  " + code );
                        //注册可写通知
                        sc.register(selector,SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        String sendText = "Message from Server";
                        ByteBuffer sendBuffer = ByteBuffer.allocate(256);
                        sendBuffer.put(sendText.getBytes());
                        sendBuffer.flip();
                        int code = 0;
                        //如果sendBuffer内容一次没有写完，会在下一次事件中处理吗？
                        while ((code = client.write(sendBuffer)) != 0) {
                        }
                        if (code == -1) {
                            chm.remove(client.hashCode());
                            client.close();
                        } else {
                            //code=0，消息写完
                            sendBuffer.clear();
                        }
                        it.remove();
                        System.out.println("Send message to client.");
                        //在读通知里面注册为写事件，所以这里还需要注册为读，否则不在接受客户端消息
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (key.isConnectable()) {
                        System.out.println("----------isConnectable----------");
                    } else if (key.isValid()) {
                        System.out.println("----------isValid----------");
                    }
                }
                TimeUnit.MILLISECONDS.sleep(500);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        startServer(8088);
    }
}
