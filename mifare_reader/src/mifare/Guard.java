package mifare;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Guard extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Guard frame = new Guard();
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
	public Guard() {
		setTitle("\u95E8\u7981\u6A21\u62DF\u7CFB\u7EDF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u8BF7\u9009\u62E9\u64CD\u4F5C\u7C7B\u578B", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(5, 5, 422, 248);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("\u53D1\u5361");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(148, 50, 113, 27);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u95E8\u7981\u7CFB\u7EDF");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(148, 178, 113, 27);
		panel.add(btnNewButton_1);
	}

}
