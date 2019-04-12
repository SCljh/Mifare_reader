package mifare;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.xml.internal.ws.util.StringUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.activation.ActivationGroupDesc;

import javax.swing.JTextField;
import javax.smartcardio.Card;
import javax.smartcardio.CardTerminal;
import javax.swing.DropMode;
import javax.swing.JButton;

public class Guard_issuer extends JFrame {

	private JPanel contentPane;
	private JTextField userNametextField;
	private JTextField addressTextField;
	private JTextField plateTextField;
	
	private String userName = null;
	private String uid = null;
	private String address = null;
	private String plate = null;
	
	private StringBuilder userNameUTF8 = null;
	private StringBuilder addressUTF8 = null;
	private StringBuilder plateUTF8 = null;
	
	private String userNameData = null;
	private String addressData = null;
	private String plateData = null;
	
	private JLabel uidTitleLabel;
	private JLabel uidLabel;
	
	private MifareControl mc = new MifareControl();
	private CardTerminal ct;
	private Card card;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Guard_issuer frame = new Guard_issuer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Guard_issuer() {
		
		userNameUTF8 = new StringBuilder();
		addressUTF8 = new StringBuilder();
		plateUTF8 = new StringBuilder();
		
		setTitle("\u95E8\u7981\u53D1\u5361\u7CFB\u7EDF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 613, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		uidTitleLabel = new JLabel("\u5361\u7247UID\uFF1A");
		uidTitleLabel.setFont(new Font("等线", Font.PLAIN, 22));
		uidTitleLabel.setBounds(14, 23, 102, 31);
		contentPane.add(uidTitleLabel);
		
		uidLabel = new JLabel("\u7A7A");
		uidLabel.setFont(new Font("等线", Font.PLAIN, 22));
		uidLabel.setBounds(118, 23, 215, 31);
		contentPane.add(uidLabel);
		
		userNametextField = new JTextField();
		userNametextField.setFont(new Font("等线", Font.PLAIN, 22));
		userNametextField.setToolTipText("\u6237\u4E3B\u540D");
		userNametextField.setBounds(266, 89, 195, 41);
		contentPane.add(userNametextField);
		userNametextField.setColumns(10);
		
		JLabel label = new JLabel("\u6237\u4E3B\u540D");
		label.setFont(new Font("等线", Font.PLAIN, 22));
		label.setBounds(144, 89, 85, 41);
		contentPane.add(label);
		
		addressTextField = new JTextField();
		addressTextField.setToolTipText("\u6237\u4E3B\u540D");
		addressTextField.setFont(new Font("等线", Font.PLAIN, 22));
		addressTextField.setColumns(10);
		addressTextField.setBounds(266, 161, 195, 41);
		contentPane.add(addressTextField);
		
		JLabel label_1 = new JLabel("\u4F4F\u5740");
		label_1.setFont(new Font("等线", Font.PLAIN, 22));
		label_1.setBounds(144, 161, 85, 41);
		contentPane.add(label_1);
		
		plateTextField = new JTextField();
		plateTextField.setToolTipText("\u6237\u4E3B\u540D");
		plateTextField.setFont(new Font("等线", Font.PLAIN, 22));
		plateTextField.setColumns(10);
		plateTextField.setBounds(266, 238, 195, 41);
		contentPane.add(plateTextField);
		
		JLabel label_2 = new JLabel("\u8F66\u724C");
		label_2.setFont(new Font("等线", Font.PLAIN, 22));
		label_2.setBounds(144, 238, 85, 41);
		contentPane.add(label_2);
		
		JButton button = new JButton("\u786E\u8BA4\u6DFB\u52A0\u6B64\u95E8\u7981\u5361");
		button.setFont(new Font("等线", Font.PLAIN, 20));
		button.setBounds(144, 314, 317, 41);
		contentPane.add(button);
		
		JButton getUIDButton = new JButton("\u83B7\u53D6uid");
		getUIDButton.setBounds(14, 67, 113, 27);
		contentPane.add(getUIDButton);
		getUIDButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if ((ct = mc.terminalInitial()) == null){
					JOptionPane.showMessageDialog(null, "读卡器连接错误");  
					//noticeText.setText("读卡器连接错误！");
					return;
				}
				else System.out.println("connect error!");

				card = mc.getCard(ct);
				JOptionPane.showMessageDialog(null,mc.getCardUID(card));  
				uid = mc.getCardUID(card);
				
				System.out.println(uid);
				
				uidLabel.setText(uid);
			}
		});
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if (uid == null){
					JOptionPane.showMessageDialog(null, "请先获取门禁卡uid");
					return;
				}
				
				if ((userName = userNametextField.getText().toString()).equals("")){
					JOptionPane.showMessageDialog(null,"请输入户主姓名");
					return;
				}
				if ((address = addressTextField.getText().toString()).equals("")){
					JOptionPane.showMessageDialog(null,"请输入户主住址");
					return;
				}
				plate = plateTextField.getText().toString();
				
				System.out.println(userName);
				System.out.println(Convert_tool.convertStringToUTF8(userName));
				userNameUTF8.append(Convert_tool.convertStringToUTF8(userName));
				
				addressUTF8.append(Convert_tool.convertStringToUTF8(address));
				
				plateUTF8.append(plate);
				
				String keydata = "12275F6E0CFFFF07806912275F6E0CFE";
				
				if (!mc.writeData(card, "FFFFFFFFFFFF", 14, 3, 'A', keydata)){
					System.out.println("卡片初始化失败");
					JOptionPane.showMessageDialog(null, "卡片初始化失败");
					return;
				}else System.out.println("卡片初始化成功");
				
				System.out.println("usernameutf8.lenth():" + userNameUTF8.length());
				System.out.println("usernameutf8:"+ userNameUTF8);
				
				for (;userNameUTF8.length() < 32;){
					userNameUTF8.append('0');
				}
				System.out.println("userNameUTF8.lenth():" + userNameUTF8.length());
				userNameData = userNameUTF8.toString();
				
				for (; addressUTF8.length() < 32;){
					addressUTF8.append('0');
				}
				System.out.println("addressUTF8.lenth():" + addressUTF8.length());
				addressData = addressUTF8.toString();
				System.out.println("addressData.lenth():"+ addressData.length());
				
				for (;plateUTF8.length()<32;)
					plateUTF8.append('0');
				plateData = plateUTF8.toString();
				
				System.out.println("username.lenth():" + userNameData.length());
				
				
				
				if(!mc.writeData(card, "12275F6E0CFF", 14, 0, 'A', userNameData)){
					System.out.println("用户名写入失败");
					JOptionPane.showMessageDialog(null, "用户名数据写入错误");
					return;
				}else System.out.println("用户名写入成功");
				
				if(!mc.writeData(card, "12275F6E0CFF", 14, 1, 'A', addressData)){
					System.out.println("住址数据写入失败");
					JOptionPane.showMessageDialog(null, "住址数据写入错误");
					return;
				}else System.out.println("住址数据写入成功");
				
				if(!mc.writeData(card, "12275F6E0CFF", 14, 2, 'A', plateData)){
					System.out.println("车牌数据写入失败");
					JOptionPane.showMessageDialog(null, "车牌数据写入错误");
					return;
				}else System.out.println("车牌数据写入成功");
				
				Sql_tool st = new Sql_tool();
				
				if (st.hasUid(uid, "db_uid")){
					System.out.println("数据库中已存在此卡");
					JOptionPane.showMessageDialog(null, "此卡已存在");
					return;
				}
				
				else if (st.insert(uid, "db_uid") == 1){
					System.out.println("发卡成功");
					JOptionPane.showMessageDialog(null, "发卡成功");
					return;
				}
				
				
			}
		});
	}
}
