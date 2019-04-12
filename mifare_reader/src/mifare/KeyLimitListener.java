package mifare;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class KeyLimitListener implements KeyListener {
	
	JTextField jtf;
	int limit;
	
	public KeyLimitListener(JTextField jtf,int limit){
		this.jtf = jtf;
		this.limit = limit;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		String s = this.jtf.getText();  
	    if(s.length() >= this.limit) e.consume(); 
	    
	    char keyCh = e.getKeyChar();  
	    if ((keyCh >= 'a') && (keyCh <= 'f')) 
            e.setKeyChar((char)(keyCh - 32));
          

	    
	    else if (!(((keyCh >= '0')&&(keyCh <= '9')) || ((keyCh >='A') && (keyCh <='F')))) {  
            //if (keyCh != '') // »Ø³µ×Ö·û  
                e.setKeyChar('\0');
	    }

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
