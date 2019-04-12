package test;

import javax.smartcardio.Card;
import javax.smartcardio.CardTerminal;

import mifare.MifareControl;

public class MifareControl_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MifareControl mc = new MifareControl();
		CardTerminal ct;
		Card card;
		String ctName, uid;
		
		if ((ct = mc.terminalInitial()) != null)
			System.out.println(ct.toString());
		else System.out.println("connect error!");
	
		ctName = mc.getTerminalName(ct);
		System.out.println(ctName);
		
		card = mc.getCard(ct);
		uid = mc.getCardUID(card);
		System.out.println(uid);
		
		mc.pwdAuth(card, "FFFFFFFFFFFF", 8, 1, 'A');
		String ss = mc.readData(card, "FFFFFFFFFFFF", 3, 2, 'A', 4);
		System.out.println(ss);
		
		Boolean b = mc.writeData(card, "FFFFFFFFFFFF", 8, 1, 'A', "00000000000000000000000000000000");
		mc.writeData(card, "FFFFFFFFFFFF", 8, 2, 'A', "12275F6E0CFF‬FF078069‭12275F6E0C");
		System.out.println(b);

	}

}
