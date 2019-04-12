package mifare;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import java.awt.GridLayout;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.BorderUIResource.CompoundBorderUIResource;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Event;

import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class Data_reader_ui extends JFrame{

	private JPanel contentPane;
	
	private JTextField keyTextField;
	
	private JTextField[] blockTextField;
	//private JTextField blockTextField[0];
	//private JTextField blockTextField[1];
	//private JTextField blockTextField[2];
	private JTextField keyATextField;
	private JTextField acTextField;
	private JTextField keyBTextField;
	private JTextField uidTextField;
	private JTextField noticeText;
	
	private JRadioButton rdbtnKeyA;
	private JRadioButton rdbtnKeyB;
	private JComboBox comboBox;
	
	private JCheckBox[] blockCheckBox;
	
//	private JCheckBox blockCheckBox[0];
//	private JCheckBox blockCheckBox[1];
//	private JCheckBox blockCheckBox[2];
	private JCheckBox block3CheckBox;
	
	private int sector;
	private int block;
	private char keyType;
	private String key = null;
	private String[] blockData;
	private String uid = null;
//	private String blockData[0] = null;
//	private String blockData[1] = null;
//	private String blockData[2] = null;
	private String block3KeyA = null;
	private String block3AC = null;
	private String block3KeyB = null;
	private String block3Data;
	private String[] sectorType;
	
	private Boolean isCard;
//	private CardTerminal card;
	private TerminalFactory factory;
	
	private MifareControl mc = new MifareControl();
	private CardTerminal ct;
	private Card card;
	private String ctName;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Data_reader_ui frame = new Data_reader_ui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws CardException 
	 */
	public Data_reader_ui(){
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 757, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
        
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u57FA\u672C\u64CD\u4F5C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(5, 5, 729, 321);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("\u9009\u62E9\u6247\u533A");
		label.setFont(new Font("等线", Font.PLAIN, 26));
		label.setBounds(54, 37, 109, 38);
		panel.add(label);
		
		sectorType = new String[16];
		for (int i = 0 ; i < 16; i++)
			sectorType[i] = String.valueOf(i);
		
		blockData = new String[3];
		for (int i = 0; i < 3; i++)
			blockData[i] = null;
		
		blockTextField = new JTextField[3];
		
		blockCheckBox = new JCheckBox[3];
		
		comboBox = new JComboBox(sectorType);
		comboBox.setBounds(165, 37, 82, 38);
		panel.add(comboBox);

		
		rdbtnKeyA = new JRadioButton("Key A",true);
		rdbtnKeyA.setSelected(true);
		rdbtnKeyA.setFont(new Font("等线", Font.PLAIN, 15));
		rdbtnKeyA.setBounds(302, 37, 75, 27);
		panel.add(rdbtnKeyA);
		
		rdbtnKeyB = new JRadioButton("Key B");
		rdbtnKeyB.setFont(new Font("等线", Font.PLAIN, 15));
		rdbtnKeyB.setBounds(302, 60, 75, 27);
		panel.add(rdbtnKeyB);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnKeyA);
		group.add(rdbtnKeyB);
		
		
		
		keyTextField = new JTextField();
		keyTextField.setBounds(413, 45, 266, 28);
		panel.add(keyTextField);
		keyTextField.setColumns(10);
		keyTextField.addKeyListener(new KeyLimitListener(keyTextField, 12));

		
		JLabel label_1 = new JLabel("\u5BC6\u7801");
		label_1.setFont(new Font("等线", Font.PLAIN, 24));
		label_1.setBounds(512, 16, 55, 27);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("\u57570");
		label_2.setFont(new Font("等线", Font.PLAIN, 24));
		label_2.setBounds(33, 111, 55, 27);
		panel.add(label_2);
		
		blockTextField[0] = new JTextField();
		blockTextField[0].setBounds(102, 111, 444, 27);
		panel.add(blockTextField[0]);
		blockTextField[0].setColumns(10);
		blockTextField[0].addKeyListener(new KeyLimitListener(blockTextField[0],32));

		
		blockTextField[1] = new JTextField();
		blockTextField[1].setColumns(10);
		blockTextField[1].setBounds(102, 151, 444, 27);
		panel.add(blockTextField[1]);
		blockTextField[1].addKeyListener(new KeyLimitListener(blockTextField[1],32));
		
		JLabel label_3 = new JLabel("\u57571");
		label_3.setFont(new Font("等线", Font.PLAIN, 24));
		label_3.setBounds(33, 151, 55, 27);
		panel.add(label_3);
		
		blockTextField[2] = new JTextField();
		blockTextField[2].setColumns(10);
		blockTextField[2].setBounds(102, 191, 444, 27);
		panel.add(blockTextField[2]);
		blockTextField[2].addKeyListener(new KeyLimitListener(blockTextField[2],32));
		
		JLabel label_4 = new JLabel("\u57572");
		label_4.setFont(new Font("等线", Font.PLAIN, 24));
		label_4.setBounds(33, 191, 55, 27);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("\u57573");
		label_5.setFont(new Font("等线", Font.PLAIN, 24));
		label_5.setBounds(33, 231, 55, 27);
		panel.add(label_5);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(102, 231, 444, 27);
		panel.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] {163, 117, 163, 0};
		gbl_panel_1.rowHeights = new int[] {27, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		keyATextField = new JTextField();
		GridBagConstraints gbc_keyATextField = new GridBagConstraints();
		gbc_keyATextField.fill = GridBagConstraints.BOTH;
		gbc_keyATextField.insets = new Insets(0, 0, 0, 5);
		gbc_keyATextField.gridx = 0;
		gbc_keyATextField.gridy = 0;
		panel_1.add(keyATextField, gbc_keyATextField);
		keyATextField.setColumns(10);
		keyATextField.addKeyListener(new KeyLimitListener(keyATextField, 12));

		
		acTextField = new JTextField();
		GridBagConstraints gbc_acTextField = new GridBagConstraints();
		gbc_acTextField.fill = GridBagConstraints.BOTH;
		gbc_acTextField.insets = new Insets(0, 0, 0, 5);
		gbc_acTextField.gridx = 1;
		gbc_acTextField.gridy = 0;
		panel_1.add(acTextField, gbc_acTextField);
		acTextField.setColumns(10);
		acTextField.addKeyListener(new KeyLimitListener(acTextField, 8));

		
		keyBTextField = new JTextField();
		GridBagConstraints gbc_keyBTextField = new GridBagConstraints();
		gbc_keyBTextField.fill = GridBagConstraints.BOTH;
		gbc_keyBTextField.gridx = 2;
		gbc_keyBTextField.gridy = 0;
		panel_1.add(keyBTextField, gbc_keyBTextField);
		keyBTextField.setColumns(10);
		keyBTextField.addKeyListener(new KeyLimitListener(keyBTextField, 12));

		
		blockCheckBox[0] = new JCheckBox("");
		blockCheckBox[0].setSelected(true);
		blockCheckBox[0].setBounds(556, 113, 25, 27);
		panel.add(blockCheckBox[0]);
		
		blockCheckBox[1] = new JCheckBox("");
		blockCheckBox[1].setSelected(true);
		blockCheckBox[1].setBounds(556, 151, 25, 27);
		panel.add(blockCheckBox[1]);
		
		blockCheckBox[2] = new JCheckBox("");
		blockCheckBox[2].setSelected(true);
		blockCheckBox[2].setBounds(556, 191, 25, 27);
		panel.add(blockCheckBox[2]);
		
		block3CheckBox = new JCheckBox("");
		block3CheckBox.setBounds(556, 233, 25, 27);
		panel.add(block3CheckBox);
		
		JLabel lblKeyA = new JLabel("Key A");
		lblKeyA.setFont(new Font("等线", Font.PLAIN, 20));
		lblKeyA.setBounds(144, 271, 55, 25);
		panel.add(lblKeyA);
		
		JLabel label_6 = new JLabel("\u63A7\u5236\u4F4D");
		label_6.setFont(new Font("等线", Font.PLAIN, 20));
		label_6.setBounds(288, 271, 60, 25);
		panel.add(label_6);
		
		JLabel lblKeyB = new JLabel("Key B");
		lblKeyB.setFont(new Font("等线", Font.PLAIN, 20));
		lblKeyB.setBounds(447, 271, 55, 25);
		panel.add(lblKeyB);
		
		JButton readDataButton = new JButton("\u8BFB\u5361");
		readDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		readDataButton.setBounds(591, 130, 113, 27);
		panel.add(readDataButton);
		readDataButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if (keyTextField.getText().toString().equals("")){
					noticeText.setText("请输入秘钥");
					return;
				}
				if (rdbtnKeyA.isSelected())
					keyType = 'A';
				else if(rdbtnKeyB.isSelected())
					keyType = 'B';
				
				sector =  Integer.parseInt(comboBox.getSelectedItem().toString());
				
				key = keyTextField.getText().toString();
				
				if ((ct = mc.terminalInitial()) == null){
					noticeText.setText("读卡器连接错误！");
					return;
				}
				else System.out.println("connect error!");
				
				card = mc.getCard(ct);
				
				
//				mc.pwdAuth(card, "FFFFFFFFFFFF", 8, 1, 'A');
//				String ss = mc.readData(card, "FFFFFFFFFFFF", 3, 2, 'A', 0x10);
//				System.out.println(ss);
				
				for (int i = 0; i < 3 ; i++){
					if ((blockData[0] = mc.readData(card, key, sector, i, keyType, 0x10)) != null){
						blockTextField[i].setText(blockData[0].substring(0, blockData[0].length()));
						noticeText.setText("数据获取成功");
					}
					else {
						noticeText.setText("数据获取失败");
						return;
					}
				}
				
				
				block3KeyA = mc.readData(card, key, sector, 3, keyType, 0x10).substring(0, 12);
				keyATextField.setText(block3KeyA);
				
				block3AC = mc.readData(card, key, sector, 3, keyType, 0x10).substring(12, 20);
				acTextField.setText(block3AC);
				
				block3KeyB = mc.readData(card, key, sector, 3, keyType, 0x10).substring(20, mc.readData(card, key, sector, 3, keyType, 0x10).length());
				keyBTextField.setText(block3KeyB); 
				
			}
		});
		
		JButton writeDataButton = new JButton("\u5199\u5361");
		writeDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int bLenth;
				
				
				
				if (keyTextField.getText().toString().equals("")){
					noticeText.setText("请输入秘钥");
					return;
				}
				
				if (rdbtnKeyA.isSelected())
					keyType = 'A';
				else if(rdbtnKeyB.isSelected())
					keyType = 'B';
				
				
				sector =  Integer.parseInt(comboBox.getSelectedItem().toString());
				
				key = keyTextField.getText().toString();
				
				
				if ((ct = mc.terminalInitial()) == null){
					noticeText.setText("读卡器连接错误！");
					return;
				}
				else System.out.println("connected!");
				
				card = mc.getCard(ct);
				
				for (int i = 0; i < 3; i++){
					if (blockCheckBox[i].isSelected()){
						if (blockTextField[i].getText().toString().length() != 32){
							noticeText.setText("0块数据长度错误");
							return;
						}
						else {
							blockData[i] = blockTextField[i].getText();
							if (mc.writeData(card, key, sector, i, keyType, blockData[i]))
								noticeText.setText("写入数据成功");
							else {
								noticeText.setText(i + "块写入数据失败");
								return;
							}
						}
					}
				}
				
				block3KeyA = keyATextField.getText().toString();
				block3KeyB = keyBTextField.getText().toString();
				block3AC = acTextField.getText().toString();
				if (block3KeyA.length() != 12){
					noticeText.setText("KeyA数据长度错误");
					return;
				}
				
				if (block3KeyB.length() != 12){
					noticeText.setText("KeyB数据长度错误");
					return;
				}
				
				if (block3AC.length() != 8){
					noticeText.setText("控制字节数据长度错误");
					return;
				}
				
				block3Data = block3KeyA + block3AC + block3KeyB;
				if (mc.writeData(card, key, sector, 3, keyType, block3AC))
					noticeText.setText("数据写入成功");
				else {
					noticeText.setText("块3数据写入失败");
					return;
				}
				
			}
		});
		writeDataButton.setBounds(591, 170, 113, 27);
		panel.add(writeDataButton);
		
		JButton btnUid = new JButton("uid\u53F7");
		btnUid.setBounds(591, 210, 113, 27);
		panel.add(btnUid);
		btnUid.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if ((ct = mc.terminalInitial()) == null){
					noticeText.setText("读卡器连接错误！");
					return;
				}
				else System.out.println("connect error!");

				card = mc.getCard(ct);
				uidTextField.setText(mc.getCardUID(card));
				 
			}
		});
		
		uidTextField = new JTextField();
		uidTextField.setEditable(false);
		uidTextField.setBounds(591, 254, 113, 27);
		panel.add(uidTextField);
		uidTextField.setColumns(10);
		
		noticeText = new JTextField();
		noticeText.setBackground(new Color(0, 0, 0));
		noticeText.setForeground(new Color(124, 252, 0));
		noticeText.setFont(new Font("等线", Font.PLAIN, 30));
		noticeText.setHorizontalAlignment(SwingConstants.CENTER);
		noticeText.setText("\u6B22\u8FCE\u4F7F\u7528");
		noticeText.setEditable(false);
		noticeText.setBounds(122, 339, 470, 49);
		contentPane.add(noticeText);
		noticeText.setColumns(10);
		
		JButton button_3 = new JButton("\u5C0F\u5DE5\u5177");
		button_3.setFont(new Font("等线", Font.PLAIN, 26));
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_3.setBounds(279, 401, 157, 37);
		contentPane.add(button_3);
	}



}
