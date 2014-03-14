import javax.imageio.ImageIO;
import javax.print.attribute.standard.JobMessageFromOperator;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JApplet;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.sun.org.apache.xml.internal.security.Init;

import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class AuctionTool extends JApplet {
	private JTextField txtSterneImage;
	private JPanel content;
	protected JLabel lblTeamName;
	private static JPanel login;
	private static JSplitPane splitPane;
	private static int teamId;
	private JLabel lblCurrentBid;
	private JLabel lblPlayerForBid;
	private JPanel panel_1;
	private JPanel logoPane;
	private JLabel lblPositions;
	private JButton btnTrombone;
	private JButton btnCrickets;
	private JButton btnNewButton;
	private JPanel panel_3;
	private JLabel label;
	private JLabel label_1;
	private JTable teamTable;
	private JLabel lblMaxBidAmount;
	private String projectDir = "";//System.getProperty("user.dir").replace("bin", "");
	MP3 rimshot = new MP3(projectDir + "sound\\rimshot.mp3");
	MP3 cricket = new MP3(projectDir + "sound\\cricket.mp3");
	MP3 trombone = new MP3(projectDir + "sound\\trombone.mp3");
	private JLabel imageLabel;

	/**
	 * Create the applet.
	 */
	public AuctionTool() {
		this.init();
	}
	
	public void init(){
		content = new JPanel(new CardLayout());
		getContentPane().add(content, BorderLayout.CENTER);

		// Login Stuff
		login = new JPanel();
		login.setName("login");
		login.setBackground(new Color(135, 206, 250));
		content.add(login, "login");
		login.setLayout(new BorderLayout(0, 0));

		panel_3 = new JPanel();
		panel_3.setBackground(new Color(176, 224, 230));
		panel_3.setAlignmentY(CENTER_ALIGNMENT);
		// panel_3.setHorizontalAlignment(SwingConstants.CENTER);
		login.add(panel_3, BorderLayout.CENTER);

		label = new JLabel("");

		label_1 = new JLabel("");

		JLabel lblTeamName_1 = new JLabel("Team Name:");
		lblTeamName_1.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblTeamName_1.setHorizontalAlignment(SwingConstants.CENTER);

		txtSterneImage = new JTextField();
		txtSterneImage.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtSterneImage.setText("Sterne Image");
		txtSterneImage.setColumns(10);

		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 28));
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_3.add(label);
		panel_3.add(label_1);
		panel_3.add(lblTeamName_1);
		panel_3.add(txtSterneImage);
		panel_3.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//System.out.println(Db.query());
				
				String code = Utils.GET(String.format("login?username=%s",
						txtSterneImage.getText()));

				if (code.contains("OK")) {
					CardLayout cl = (CardLayout) (content.getLayout());
					cl.show(content, "splitPane");

					teamId = Integer.parseInt(code.split(";")[1]);
					lblTeamName.setText(txtSterneImage.getText());

					code = Utils.GET(String.format("team?teamid=%s", teamId));

					String[] team = code.split(";");

					for (String s : team) {
						if (s == "")
							continue;
						String[] p = s.split(",");

						int index = 1;
						while (teamTable.getValueAt(index, 0) != p[2]
								&& teamTable.getValueAt(index, 1) != null) {
							index++;
						}

						teamTable.setValueAt(p[2], index, 0);
						teamTable.setValueAt(p[0], index, 1);
						teamTable.setValueAt(p[1], index, 2);

						if (p[3].contains("2")) {
							teamTable
									.setValueAt(
											"<html><span style=\"color: blue;\">"
													+ p[0] + "</span></html>",
											index, 1);
						} else if (p[3].contains("3")) {
							teamTable.setValueAt(
									"<html><span style=\"color: red;\">" + p[0]
											+ "</span></html>", index, 1);
						}
					}

					UpdateAuction t = new UpdateAuction(lblPlayerForBid,
							lblCurrentBid, lblPositions, imageLabel, logoPane);
					t.start();
				} else {
					JOptionPane.showMessageDialog(null,
							"Incorrect Team Name.\nPlease try again",
							"Login Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// User Stuff

		splitPane = new JSplitPane();
		splitPane.setName("splitPane");
		content.add(splitPane, "splitPane");

		splitPane.setResizeWeight(0.75);

		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));

		lblTeamName = new JLabel("Team Name") {
			public void paintComponent(Graphics g) {
				BufferedImage im = null;
				try {
					im = ImageIO
							.read(new URL(
									"http://mlb.greensports.org/wp-content/themes/twentyeleven/images/headers/mlb-banner.jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				g.drawImage(im, 0, 0, 1500, 100, null);
				super.paintComponent(g);
			}
		};
		lblTeamName.setFont(new Font("Impact", Font.BOLD, 60));
		panel.add(lblTeamName, BorderLayout.NORTH);

		teamTable = new JTable();
		teamTable.setEnabled(false);
		teamTable.setFillsViewportHeight(true);
		teamTable.setFont(new Font("Tahoma", Font.PLAIN, 30));
		teamTable.setModel(new DefaultTableModel(new Object[][] {
				{ "Pos", "Player", "Cost" }, { "C", null, null },
				{ "1B", null, null }, { "2B", null, null },
				{ "3B", null, null }, { "CM", null, null },
				{ "MI", null, null }, { "OF", null, null },
				{ "OF", null, null }, { "OF", null, null },
				{ "OF", null, null }, { "Ut", null, null },
				{ "Ut", null, null }, { "P", null, null }, { "P", null, null },
				{ "P", null, null }, { "P", null, null }, { "P", null, null },
				{ "P", null, null }, { "P", null, null }, { "P", null, null },
				{ "P", null, null }, { "P", null, null }, }, new String[] {
				"Pos", "Player", "Cost" }));
		teamTable.setRowHeight(34);
		panel.add(teamTable, BorderLayout.CENTER);

		lblMaxBidAmount = new JLabel("Max Bid Amount: $2");
		lblMaxBidAmount.setFont(new Font("Tahoma", Font.PLAIN, 99));
		panel.add(lblMaxBidAmount, BorderLayout.SOUTH);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);

		JPanel auction = new JPanel();
		tabbedPane.addTab("Auction", auction);
		auction.setLayout(new BorderLayout(0, 0));

		lblCurrentBid = new JLabel("Current Bid: $10");
		lblCurrentBid.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblCurrentBid.setHorizontalAlignment(SwingConstants.CENTER);
		auction.add(lblCurrentBid, BorderLayout.SOUTH);

		lblPlayerForBid = new JLabel("Jon Jay");
		lblPlayerForBid.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblPlayerForBid.setHorizontalAlignment(SwingConstants.CENTER);
		auction.add(lblPlayerForBid, BorderLayout.NORTH);

		panel_1 = new JPanel();
		auction.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		logoPane = new JPanel();
		logoPane.setBackground(new Color(220, 20, 60));
		panel_1.add(logoPane, BorderLayout.CENTER);
		logoPane.setLayout(new BorderLayout(0, 0));
		
		imageLabel = new JLabel("");
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		logoPane.add(imageLabel, BorderLayout.CENTER);

		lblPositions = new JLabel("Positions: OF, CM");
		lblPositions.setFont(new Font("Tahoma", Font.PLAIN, 32));
		panel_1.add(lblPositions, BorderLayout.SOUTH);

		JPanel sound = new JPanel();
		tabbedPane.addTab("Sound Board", sound);
		sound.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnTrombone = new JButton("Sad Trombone");
		btnTrombone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				trombone.play();
			}
		});
		sound.add(btnTrombone);

		btnCrickets = new JButton("Crickets");
		btnCrickets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cricket.play();
			}
		});
		sound.add(btnCrickets);

		btnNewButton = new JButton("Rim Shot");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				rimshot.play();
			}
		});
		sound.add(btnNewButton);
	}
}
