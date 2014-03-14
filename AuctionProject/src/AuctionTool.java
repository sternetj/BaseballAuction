import javax.imageio.ImageIO;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class AuctionTool extends JFrame {
	private JTextField txtSterneImage;
	private static JPanel content;
	protected JLabel lblTeamName;
	private static JPanel login;
	private static JSplitPane splitPane;
	private static int teamId;
	private JLabel lblCurrentBid;
	private JLabel lblPlayerForBid;
	private JPanel panel_1;
	private JPanel logoPane;
	private JLabel lblPositions;
	private JButton btnTrombone, btnCrickets, btnRimShot, btnBoo, btnFart, btnClap, btnPacman;
	private JPanel panel_3;
	private JLabel label;
	private JLabel label_1;
	private JTableX teamTable;
	private JLabel lblMaxBidAmount;
	private String projectDir = "";//System.getProperty("user.dir").replace("bin", "");
	MP3 rimshot = new MP3(projectDir + "sound\\rimshot.mp3");
	MP3 cricket = new MP3(projectDir + "sound\\cricket.mp3");
	MP3 trombone = new MP3(projectDir + "sound\\trombone.mp3");
	MP3 boo = new MP3(projectDir + "sound\\boo.mp3");
	MP3 fart = new MP3(projectDir + "sound\\fart.mp3");
	MP3 golfClap = new MP3(projectDir + "sound\\golfClap.mp3");
	MP3 pacman = new MP3(projectDir + "sound\\pacmanDies.mp3");
	private JLabel imageLabel;
	private static AuctionTool auctionFrame;
	
	public static void main (String[] args){		
		auctionFrame = new AuctionTool ();
		auctionFrame.setSize(1400,870);
		auctionFrame.setVisible(true);
		auctionFrame.getContentPane().add(content, BorderLayout.CENTER);
		auctionFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void windowClosing(WindowEvent e)
	{
	dispose();
	System.exit(0);
	}
	
	public AuctionTool(){
		content = new JPanel(new CardLayout());

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

						int index = 0;
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
					
					//JOptionPane.showMessageDialog(auctionFrame,"Welcome!");

					UpdateAuction t = new UpdateAuction(lblPlayerForBid,
							lblCurrentBid, lblPositions, imageLabel, logoPane);
					//t.start();
					
					GetPlayers s = new GetPlayers(teamTable,teamId);
					s.start();
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

		splitPane.setResizeWeight(1);

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
				g.drawImage(im, 0, 0, 1400, 100, null);
				super.paintComponent(g);
			}
		};
		lblTeamName.setFont(new Font("Impact", Font.BOLD, 60));
		panel.add(lblTeamName, BorderLayout.NORTH);

		teamTable = new JTableX();
		//teamTable.setEnabled(false);
		teamTable.setFillsViewportHeight(true);
		teamTable.setFont(new Font("Tahoma", Font.PLAIN, 25));
		DefaultTableModel dtm = new DefaultTableModel(new String[] {"Pos", "Player", "Cost"},21){
				public String[] prop_names =
				{
					"C","1B","2B","3B","CM","MI","OF","OF","OF","OF","Ut","Ut","P","P","P","P","P","P","P","P","P","P"
				};
				public Object getValueAt(int row,int col){
					if (col==0)
	                    return prop_names[row];
					//if (col==1)
						//return teamTable.getCellEditor(row, col).
					return super.getValueAt(row,col);
				}
	            public boolean isCellEditable(int row, int col)
	            {
	                if (col!=1)
	                    return false;
	                return true;
	            }
		};
		teamTable.setModel(dtm);
		teamTable.getColumnModel().getColumn(0).setMaxWidth(75);
		teamTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		teamTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		teamTable.getColumnModel().getColumn(2).setMaxWidth(100);
		teamTable.setRowHeight(30);
		panel.add(new JScrollPane(teamTable), BorderLayout.CENTER);
		//panel.add(teamTable, BorderLayout.CENTER);

		lblMaxBidAmount = new JLabel("Max Bid Amount: $2");
		lblMaxBidAmount.setFont(new Font("Tahoma", Font.PLAIN, 48));
		panel.add(lblMaxBidAmount, BorderLayout.SOUTH);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setMinimumSize(new Dimension(100,100));
		splitPane.setRightComponent(tabbedPane);

		JPanel auction = new JPanel();
		tabbedPane.addTab("Auction", auction);
		auction.setLayout(new BorderLayout(0, 0));

		lblCurrentBid = new JLabel("Current Bid: $10");
		lblCurrentBid.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblCurrentBid.setHorizontalAlignment(SwingConstants.CENTER);
		auction.add(lblCurrentBid, BorderLayout.SOUTH);

		lblPlayerForBid = new JLabel("Jon Jay");
		lblPlayerForBid.setFont(new Font("Tahoma", Font.BOLD, 36));
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
		Font buttonFont = new Font("Segoe UI", Font.PLAIN, 16);
		btnTrombone.setFont(buttonFont);
		btnTrombone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				trombone.play();
			}
		});
		sound.add(btnTrombone);

		btnCrickets = new JButton("Crickets");
		btnCrickets.setFont(buttonFont);
		btnCrickets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cricket.play();
			}
		});
		sound.add(btnCrickets);

		btnRimShot = new JButton("Rim Shot");
		btnRimShot.setFont(buttonFont);
		btnRimShot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				rimshot.play();
			}
		});
		sound.add(btnRimShot);
		
		btnBoo = new JButton("Boo");
		btnBoo.setFont(buttonFont);
		btnBoo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boo.play();
			}
		});
		sound.add(btnBoo);
		
		btnFart = new JButton("Fart");
		btnFart.setFont(buttonFont);
		btnFart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				fart.play();
			}
		});
		sound.add(btnFart);
		
		btnClap = new JButton("Golf Clap");
		btnClap.setFont(buttonFont);
		btnClap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				golfClap.play();
			}
		});
		sound.add(btnClap);
		
		btnPacman = new JButton("Pacman Dying");
		btnPacman.setFont(buttonFont);
		btnPacman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				pacman.play();
			}
		});
		sound.add(btnPacman);
	}
}
