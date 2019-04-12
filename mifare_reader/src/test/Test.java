package test;

import javax.smartcardio.*;
import java.util.*;

public class Test {

    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        int a = 0;
        for (byte b : bytes) { // 使用除与取余进行转换
            if (b < 0) {
                a = 256 + b;
            } else {
                a = b;
            }
            //sb.append("0x");
            sb.append(HEX_CHAR[a / 16]);
            sb.append(HEX_CHAR[a % 16]);
            //sb.append(" ");
        }
        return sb.toString().toUpperCase();
    }


    public static void main(String[] args) {


        TerminalFactory factory = TerminalFactory.getDefault();//得到一个默认的读卡器工厂(迷。。)
        List<CardTerminal> terminals;//创建一个List用来放读卡器(谁没事会在电脑上插三四个读卡器。。)
        try {
            terminals = factory.terminals().list();//从工厂获得插在电脑上的读卡器列表
            //terminals.stream().forEach(s->System.out.println(s));//打印获取到的读卡器名称
            System.out.println(terminals.toString());

            terminals = factory.terminals().list();//get读卡器列表
            CardTerminal a = terminals.get(0);//使用第0个读卡器[暂且不考虑同时插N个读卡器的情况了]
            a.waitForCardPresent(0L);//等待放置卡片
            Card card = a.connect("T=1");//连接卡片，协议T=1 块读写(T=0貌似不支持，一用就报错)
            CardChannel channel = card.getBasicChannel();//打开通道
            CommandAPDU getUID = new CommandAPDU(0xFF,  0xCA, 0x00, 0x00,0x04);//中文API第12页
            ResponseAPDU r = channel.transmit(getUID);//发送getUID指令
            System.out.println("UID: " + bytesToHexString(r.getData()));

            byte[] pwd = {(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};//先用一个数组把密钥存起来
            //byte[] pwd = {(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};//先用一个数组把密钥存起来// ~
            CommandAPDU loadPWD = new CommandAPDU(0xFF, 0x82, 0x00, 0x00, pwd);//然后构造一个加载密钥APDU指令~
            ResponseAPDU r1 = channel.transmit(loadPWD);//发送loadPWD指令
            System.out.println("LOAD PWD result: " + bytesToHexString(r1.getBytes()));

            byte[] check = {(byte)0x01,(byte)0x00,(byte)0x05,(byte)0x60,(byte)0x00};//认证数据字节，包含了需要认证的区块号、密钥类型和密钥存储的地址(密钥号)
            CommandAPDU authPWD = new CommandAPDU(0xFF, 0x86, 0x00, 0x00, check);//加上指令头部，构造出完整的认证APDU指令.
            ResponseAPDU r2 = channel.transmit(authPWD);//发送认证指令
            System.out.println("CHECK PWD result: " + bytesToHexString(r2.getBytes()));//打印返回值

            CommandAPDU getData = new CommandAPDU(0xFF, 0xB0, 0x00, 0x39,0x01);//构造读区块APDU指令,读第八个区块(2扇区0区块)值
            ResponseAPDU r3 = channel.transmit(getData);//发送读区块指令
            System.out.println("READ data: " + bytesToHexString(r3.getBytes()));//打印返回值

            byte[] up = {(byte)0x1f,(byte)0x10,(byte)0xC5,(byte)0xE9,(byte)0x6B,(byte)0x0A,(byte)0x11,(byte)0x11,(byte)0xE9,(byte)0x6B,(byte)0x0A,(byte)0x11,(byte)0x11,(byte)0xE9,(byte)0x6B,(byte)0x0A};
            CommandAPDU updateData = new CommandAPDU(0xFF, 0xD6, 0x00, 0x39, up);
            ResponseAPDU r4 = channel.transmit(updateData);//发送写块指令
            System.out.println("WRITE response: " + bytesToHexString(r4.getBytes()));//打印返回值

            CommandAPDU getData1 = new CommandAPDU(0xFF, 0xB0, 0x00, 0x39,0x10);//构造读区块APDU指令,读第八个区块(2扇区0区块)值
            ResponseAPDU r5 = channel.transmit(getData1);//发送读区块指令
            System.out.println("READ data: " + bytesToHexString(r5.getBytes()));//打印返回值
            
//            byte[] com = {(byte)0xFF, (byte)0x00, (byte)0x40, (byte)0x9A, (byte)0x04, (byte)0x08, (byte)0x06, (byte)0x2, (byte)0x01};
//            CommandAPDU getData6 = new CommandAPDU(com);
//            ResponseAPDU r6 = channel.transmit(getData6);
//            System.out.println("READ data: " + bytesToHexString(r6.getBytes()));//打印返回值
            
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}