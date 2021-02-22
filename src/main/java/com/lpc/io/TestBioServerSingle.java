package com.lpc.io;



import jodd.util.concurrent.ThreadFactoryBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * BIO 基于线程驱动 （Blocking I/O）
 * 一个线程，连接一个客户端
 * 改善的话，是基于线程池，但是也是一个线程，一个连接
 * 核心组件： ServerSocket, inputStream, outputStream, byte-buffer, socket
 */
public class TestBioServerSingle {

    public static final int OneKB = 1024;

    private static volatile LongAdder seq = new LongAdder();

    public static void bioServer() {
        ServerSocket serverSocket = null;

        Socket socket = null; //客户端

        try {
            serverSocket = new ServerSocket(8088);
            while (true) {
                socket = serverSocket.accept(); // 阻塞，3次握手
                BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

                String line = null;
                while (true) {
                    line = bf.readLine();
                    if (Objects.isNull(line)) {
                        break;
                    }
                    System.out.println("收到消息:"+ line);
                    pw.println("ok");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(
            2,
            2,
            60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2), new ThreadFactoryBuilder().setNameFormat("socket_server-%d").get(),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    System.out.println("超过最大连接数量，拒绝");
                }
            });

    public static void advanceBioServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8088);
            while (true) {
                Socket socket = serverSocket.accept(); // 阻塞，3次握手
                seq.increment();
                System.out.println(String.format("第%d个连接...", seq.intValue()));
                pool.execute(new SocketHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        //bioServer();
        advanceBioServer();
    }

}

class SocketHandler implements Runnable {

    private Socket socket = null;

    SocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            byte[] buffer = new byte[TestBioServerSingle.OneKB]; //问题： 超过1024 会怎样
            while (in.read(buffer) > 0) {
                System.out.println(new String(buffer));
                out.write("成功".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
