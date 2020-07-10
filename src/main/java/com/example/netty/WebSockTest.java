//package com.example.netty;
//
//import com.google.gson.Gson;
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
//import io.netty.handler.logging.LogLevel;
//import io.netty.handler.logging.LoggingHandler;
//import org.springframework.util.StringUtils;
//
//import java.io.Serializable;
//import java.net.InetSocketAddress;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Scanner;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.LinkedBlockingQueue;
//
//public class WebSockTest {
//    static Map<String, SocketChannel> socketChannelMap = new ConcurrentHashMap<>();
//    static LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
//
//    public static void main(String[] args) throws InterruptedException {
//        Object o = new Object();
//        Scanner scanner = new Scanner(System.in);
//        startCollocation();
//        doTask();
//        while (true) {
//            synchronized (o) {
//                String str = scanner.nextLine();
//                if (!StringUtils.isEmpty(str))
//                    blockingQueue.add(str);
//                else
//                    o.wait(1000);
//            }
//        }
//    }
//
//    private static void doTask() {
//        Gson gson = new Gson();
//        new Thread(() -> {
//            while (true) {
//                String str = null;
//                try {
//                    str = blockingQueue.take();
//                    System.out.println("输出： " + str);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if (!StringUtils.isEmpty(str) && socketChannelMap.size() > 0) {
//                    Iterator<String> it = socketChannelMap.keySet().iterator();
//                    while (it.hasNext()) {
//                        System.out.println("执行 ： " + str);
//                        String key = it.next();
//                        SocketChannel obj = socketChannelMap.get(key);
//                        System.out.println("channel id is: " + key);
//                        System.out.println("channel: " + obj.isActive());
//                        obj.writeAndFlush(new TextWebSocketFrame(gson.toJson(new Car())));
//                    }
//                }
//            }
//        }).start();
//    }
//
//    private static void startCollocation() {
//        new Thread(() -> {
//            EventLoopGroup bossGroup = new NioEventLoopGroup();
//            EventLoopGroup workerGroup = new NioEventLoopGroup();
//            try {
//                ServerBootstrap serverBootstrap = new ServerBootstrap();
//                serverBootstrap.group(bossGroup, workerGroup).
//                        channel(NioServerSocketChannel.class).
//                        handler(new LoggingHandler(LogLevel.INFO)).
//                        childHandler(new WebSocketChannelInitializer());
//                ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8899)).sync();
//                channelFuture.channel().closeFuture().sync();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                bossGroup.shutdownGracefully();
//                workerGroup.shutdownGracefully();
//            }
//        }).start();
//    }
//}
//
//class Car implements Serializable {
//
//    /**
//     * 车主姓名，车五项之一
//     */
//    private String ownerName;
//
//    /**
//     * 车牌号码，车五项之一
//     */
//    private String plateNumber;
//
//    /**
//     * 车辆识别代号/车架号，车五项之一
//     */
//    private String vin;
//
//    /**
//     * 发动机号，车五项之一
//     */
//    private String engineNo;
//
//    /**
//     * 初登日期，车五项之一
//     */
//    private String registerDate;
//
//    /**
//     * 交管所车辆类型：轿车，小型普通客车，等，参考安联系统
//     */
//    private String vehicleStyle;
//
//    public Car() {
//        this.ownerName = "朱慧";
//        this.plateNumber = "苏AC29N8";
//        this.vin = "LE4GG3HB6FL398799";
//        this.engineNo = "10163505";
//        this.registerDate = "2015-08-13";
//        this.vehicleStyle = "K33 轿车";
//    }
//
//    public String getOwnerName() {
//        return ownerName;
//    }
//
//    public void setOwnerName(String ownerName) {
//        this.ownerName = ownerName;
//    }
//
//    public String getPlateNumber() {
//        return plateNumber;
//    }
//
//    public void setPlateNumber(String plateNumber) {
//        this.plateNumber = plateNumber;
//    }
//
//    public String getVin() {
//        return vin;
//    }
//
//    public void setVin(String vin) {
//        this.vin = vin;
//    }
//
//    public String getEngineNo() {
//        return engineNo;
//    }
//
//    public void setEngineNo(String engineNo) {
//        this.engineNo = engineNo;
//    }
//
//    public String getRegisterDate() {
//        return registerDate;
//    }
//
//    public void setRegisterDate(String registerDate) {
//        this.registerDate = registerDate;
//    }
//
//    public String getVehicleStyle() {
//        return vehicleStyle;
//    }
//
//    public void setVehicleStyle(String vehicleStyle) {
//        this.vehicleStyle = vehicleStyle;
//    }
//}