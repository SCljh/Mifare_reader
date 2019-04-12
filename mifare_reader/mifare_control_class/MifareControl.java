package mifare;

import javax.smartcardio.*;
import java.util.*;

public class MifareControl {
	
	Card card;
	
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
            sb.append(HEX_CHAR[a / 16]);
            sb.append(HEX_CHAR[a % 16]);
        }
        return sb.toString().toUpperCase();
    }
    
    public static byte[] toByteArray(String hexString) {
   	 
  	  hexString = hexString.toLowerCase();
  	  final byte[] byteArray = new byte[hexString.length() / 2];
  	  int k = 0;
  	  for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
  	   byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
  	   byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
  	   byteArray[i] = (byte) (high << 4 | low);
  	   k += 2;
  	  }
  	  return byteArray;
  	 }
    
	/**
	 *读卡器初始化
	 *成功返回CardTerminal
	 *失败返回null
	 */
    public CardTerminal terminalInitial(){
    	try {
			TerminalFactory factory = TerminalFactory.getDefault();//获取默认的读卡器工厂
			List<CardTerminal> terminals;//创建一个List用来放读卡器
			terminals = factory.terminals().list();//从工厂获得插在电脑上的读卡器列表
			CardTerminal a = terminals.get(0);
			return a;
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
	/**
	 *获取读卡器名称
	 *成功返回读卡器名称（String）
	 *失败返回null
	 */
    public String getTerminalName(CardTerminal ct){
    	if (ct != null)
    		return ct.toString();
    	else return null;
    }
    
	/**
	 *获得卡片
	 *参数terminal：需要执行操作的terminal对象
	 *成功则返回Card类型
	 *失败返回null
	 */
    public Card getCard(CardTerminal terminal){
    	try {
			terminal.waitForCardPresent(0L);//等待放置卡片
			card = terminal.connect("T=1");//连接卡片
			return card;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
	/**
	 *获取mifare卡片uid值
	 *参数card：需要执行操作的card对象
	 *成功返回uid（String）
	 *失败返回null
	 */
    public String getCardUID(Card card){
		try {
			CardChannel channel = card.getBasicChannel();//打开通道
			CommandAPDU getUID = new CommandAPDU(0xFF,  0xCA, 0x00, 0x00,0x04);//中文API第12页
			ResponseAPDU r = channel.transmit(getUID);//发送getUID指令
//			System.out.println("UID: " + bytesToHexString(r.getData()));
			return bytesToHexString(r.getData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
	/**
	 *针对指定扇区认证mifare卡片密钥
	 *card：需要执行操作的card对象
	 *pwd：string类型表示的16进制密钥（String下12个字符）
	 *sector：需要认证的扇区
	 *block：需要认证的的块（该扇区对应的0,1,2,3块）
	 *keyType：密钥类型（'A' OR 'B'）
	 *返回值：认证成功返回true，失败返回false
	 */
    public Boolean pwdAuth(Card card, String pwd, int sector, int block, char keyType){
    	byte[] pwdArray = toByteArray(pwd);
    	int kType = 0;
    	
    	//byte[] pwdArray = {(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
    	
    	if (keyType == 'A')
    		kType = 0x60;
    	else if (keyType == 'B')
    		kType = 0x61;
    	
        try {
        	CardChannel channel = card.getBasicChannel();
        	CommandAPDU loadPWD = new CommandAPDU(0xFF, 0x82, 0x00, 0x00, pwdArray);//然后构造一个加载密钥APDU指令~
			ResponseAPDU r1 = channel.transmit(loadPWD);//发送loadPWD指令
			String s = bytesToHexString(r1.getBytes());
			System.out.println(s);
			if (bytesToHexString(r1.getBytes()).equals("6300")){
				System.out.println("秘钥加载错误");
				return false;
			}
			else if (bytesToHexString(r1.getBytes()).equals( "9000"))
				System.out.println("秘钥加载成功");
				
			System.out.println("LOAD PWD result: " + bytesToHexString(r1.getBytes()));
		
        
        int local = sector * 4 + block;

        byte[] check = {(byte)0x01,(byte)0x00, (byte)local, (byte)kType, (byte)0x00};//认证数据字节，包含了需要认证的区块号、密钥类型和密钥存储的地址(密钥号)
        CommandAPDU authPWD = new CommandAPDU(0xFF, 0x86, 0x00, 0x00, check);//加上指令头部，构造出完整的认证APDU指令.
        ResponseAPDU r2 = channel.transmit(authPWD);//发送认证指令
        System.out.println("CHECK PWD result: " + bytesToHexString(r2.getBytes()));//打印返回值
        if (bytesToHexString(r2.getBytes()).equals( "6300")){
        	System.out.println("秘钥认证错误");
        	return false;
        }
        else if (bytesToHexString(r2.getBytes()).equals("9000")){
        	System.out.println("秘钥认证成功");
        	return true;
        }
        
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
        
    }

	/**
	 *读取mifare卡内指定块的数据
	 *card：需要执行操作的card对象
	 *pwd：string类型表示的16进制密钥（String下12个字符）
	 *sector：需要进行操作的扇区
	 *block：需要进行操作的的块（该扇区对应的0,1,2,3块）
	 *keyType：密钥类型（'A' OR 'B'）
	 *lenth：需要读取的数据的字节数（不得超过16）
	 *返回值：成功返回String类型数据，失败返回null
	 */
    public String readData(Card card, String pwd, int sector, int block, char keyType, int lenth){
    	
    	if (!pwdAuth(card, pwd, sector, block, keyType)){
    		System.out.print("秘钥认证失败");
    		return null;
    	}
    	int local = sector * 4 + block;
    	
    	try {
			CardChannel channel = card.getBasicChannel();
			CommandAPDU getData = new CommandAPDU(0xFF, 0xB0, 0x00,local,lenth);//构造读区块APDU指令,读区块值
			ResponseAPDU r3 = channel.transmit(getData);//发送读区块指令
			System.out.println("READ data: " + bytesToHexString(r3.getBytes()));//打印返回值
			
			return bytesToHexString(r3.getBytes()).substring(0, bytesToHexString(r3.getBytes()).length()-4);
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	
    }
    
	/**
	 *向mifare卡内指定块写入指定数据
	 *card：需要执行操作的card对象
	 *pwd：string类型表示的16进制密钥（String下12个字符）
	 *sector：需要进行操作的扇区
	 *block：需要进行操作的的块（该扇区对应的0,1,2,3块）
	 *keyType：密钥类型（'A' OR 'B'）
	 *data：以String类型表示的需要写入的数据（16进制下16字节）
	 */
    public Boolean writeData(Card card, String pwd, int sector, int block, char keyType, String data){
    	
    	if (!pwdAuth(card, pwd, sector, block, keyType)){
    		System.out.print("秘钥认证失败");
    		return false;
    	}
    	
    	int local = sector * 4 + block; 
    	try {
			CardChannel channel= card.getBasicChannel();
			byte[] byteData = toByteArray(data);
			CommandAPDU updateData = new CommandAPDU(0xFF, 0xD6, 0x00, local, byteData);
			//CommandAPDU updateData = new CommandAPDU(0xFF, 0xD6, 0x00, 0x39, byteData);
			ResponseAPDU r4 = channel.transmit(updateData);//发送写块指令
			System.out.println("WRITE response: " + bytesToHexString(r4.getBytes()));//打印返回值
			readData(card, pwd, sector, block, keyType, 16);
			if (bytesToHexString(r4.getBytes()).equals("9000"))
				return true;
			else 
				return false;
			} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    	
    }

    public void dataError(){
    	
    }
    
    public void dataCorrect(){
    	
    }
    
}
