package test;

import javax.smartcardio.*;

public class Test1 implements Runnable{
	
	CardTerminal ct;
	int flag = 0;
	
	public Test1(CardTerminal ct){
		this.ct = ct;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true){
				if (ct.isCardPresent()){
					if (this.flag == 0){
						this.flag = 1;
						System.out.println("true");
					}
					else continue;
				}
				else if (!ct.isCardPresent()){
					if (this.flag == 1){
						this.flag = 0;
						System.out.println("false");
					}
					else continue;
				}
			}
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
