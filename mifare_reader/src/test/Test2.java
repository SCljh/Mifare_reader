package test;

import java.util.List;

import javax.smartcardio.*;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TerminalFactory factory = TerminalFactory.getDefault();//得到一个默认的读卡器工厂(迷。。)
        List<CardTerminal> terminals;//创建一个List用来放读卡器(谁没事会在电脑上插三四个读卡器。。)
        
        try {
			terminals = factory.terminals().list();//从工厂获得插在电脑上的读卡器列表
			//terminals.stream().forEach(s->System.out.println(s));//打印获取到的读卡器名称
			System.out.println(terminals.toString());

			terminals = factory.terminals().list();//get读卡器列表
			CardTerminal a = terminals.get(0);//使用第0个读卡器[暂且不考虑同时插N个读卡器的情况了]
			if (a.isCardPresent()){
				System.out.println("card present");
			}
			else if (!a.isCardPresent())
				System.out.println("card absent");
			System.out.println(a.isCardPresent());
			System.out.println();
			
			
			Thread t = new Thread(new Test1(a));
			t.start();
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
