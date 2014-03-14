import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CheckForNewPlayer extends Thread {

	private int playerId;

	public CheckForNewPlayer(int playerId) {
		this.playerId = playerId;
	}

	@Override
	public void run() {
		while (true) {
			String code = Utils.GET("auction");
			if (!code.contains("BAD")) {
				String[] auction = (code.split(";"))[1].split(",");
//				if (lblPlayerForBid.getText() != auction[0] || width != imagePanel.getWidth()) {
//					width = imagePanel.getWidth();
//					lblPlayerForBid.setText(auction[0]);
//					lblCurrentBid.setText(String.format("Current Bid: $%s",
//							auction[2]));
//					lblPositions.setText(String.format("Positions: %s",
//							auction[5].replace('#', ',')));
//
//					// ImageIcon image = new ImageIcon(auction[3]);
//					ImageIcon image = null;
//					try {
//						image = createImageIcon(auction[3], "");
//					} catch (MalformedURLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					image = scaleImage(image,width,(image.getIconHeight()*width)/image.getIconWidth());
//					logo.setIcon(image);
//				}
			}

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// table.setValueAt(count++, 0, 0);
		}
	}

	/**
	 * Creates an ImageIcon if the path is valid.
	 * 
	 * @param String
	 *            - resource path
	 * @param String
	 *            - description of the file
	 * @throws MalformedURLException
	 */
	protected ImageIcon createImageIcon(String path, String description)
			throws MalformedURLException {
		URL imgURL = new URL(path);
		return new ImageIcon(imgURL, description);
	}

	private ImageIcon scaleImage(ImageIcon image, int imageW, int imageH) {

		Image img = image.getImage();
		BufferedImage bi = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		g.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null, null);
		return new ImageIcon(bi.getScaledInstance(imageW, imageH, BufferedImage.TYPE_INT_ARGB));
	}

}
