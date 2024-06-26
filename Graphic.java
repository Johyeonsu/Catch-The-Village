package TeamProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;

public class Graphic extends JFrame {

	Village village;

	Container contentPane;
	JLabel villagePeopleLabel = new JLabel("");
	JLabel villageHappyLabel = new JLabel("");
	JLabel villagePlumLabel = new JLabel("");
	JLabel setJobLabel = new JLabel("");
	JLabel setJob2Label = new JLabel("");

	ImageIcon endingbg = null;

	Product[] market;
	Product[] product;
	Person[] person;

	int marketindex;
	int jobindex;
	int locationY;

	public void getVillage(Village village) {
		this.village = village;

	}

	public void change(String panel) {
		contentPane.removeAll();
		switch (panel) {
		case ("play"):
			contentPane.add(new PlayPanel(contentPane));
			break;
		case ("market"):
			contentPane.add(new MarketPanel());
			break;
		case ("choosejob"):
			contentPane.add(new ChooseJobPanel());
			break;
		case ("intro1"):
			contentPane.add(new IntroPanel1());
			break;
		case ("intro2"):
			contentPane.add(new IntroPanel2());
			break;
		case ("intro3"):
			contentPane.add(new IntroPanel3());
			break;
		case ("marketwarning"):
			contentPane.add(new MarketWarningPanel());
			break;
		case ("notice"):
			contentPane.add(new NoticePanel());
			break;
		case ("build"):
			contentPane.add(new BuildPanel(marketindex));
			break;
		case ("ending"):
			contentPane.add(new EndingPanel());
			break;
		case ("purchase"):
			contentPane.add(new PurchasePanel(market[marketindex].price));
			break;
		}
		revalidate();
		repaint();
	}

	public int makeHash(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		int hash;
		if (b.getY() == 245) {
			hash = b.getX(); // choosejob 해쉬코드
			switch (hash) {
			case (220):
				hash = 0;
				break;
			case (327):
				hash = 1;
				break;
			case (434):
				hash = 2;
				break;
			case (541):
				hash = 3;
				break;
			case (648):
				hash = 4;
				break;
			}
		} else {
			hash = b.getX() * b.getY() % 100; // 마켓 해쉬코드
			switch (hash) {
			case (31):
				hash = 0;
				break;
			case (90):
				hash = 1;
				break;
			case (49):
				hash = 2;
				break;
			case (8):
				hash = 3;
				break;
			case (92):
				hash = 4;
				break;
			case (80):
				hash = 5;
				break;
			case (68):
				hash = 6;
				break;
			case (56):
				hash = 7;
				break;
			}
		}
		return hash;
	}

	public void setLabelLSA(JLabel label, int x, int y, int width, int height) {
		label.setLocation(x, y);
		label.setSize(width, height);
		label.setFont(new Font("배달의민족 도현", Font.PLAIN, 20));
		this.add(label);
	}

	public void setButtonLSA(JButton b, int x, int y, int width, int height) {
		b.setLocation(x, y);
		b.setSize(width, height);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		this.add(b);
	}

	public void setTextLSA(JTextField tf, int x, int y, int width, int height) {
		tf.setLocation(x, y);
		tf.setSize(width, height);
		tf.setFont(new Font("배달의민족 도현", Font.PLAIN, 20));
		tf.setOpaque(false);
		tf.setBorder(null);
		this.add(tf);
	}
	
	public void buildProduct(Product product[]) {
		Arrays.sort(product);
		Product.setProduct(product);
		for (int i = 0; i < product.length; i++) {
			if (product[i].productTF == true) {
				JLabel label = new JLabel(product[i].icon);
				setLabelLSA(label, product[i].locationX, product[i].locationY, product[i].icon.getIconWidth(),
						product[i].icon.getIconHeight());
			}
		}
	}

	class OpenChooseJob implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			change("choosejob");
		}
	}

	class OpenNotice implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			change("notice");
		}
	}

	class OpenMarket implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			change("market");
		}
	}

	class Close implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			change("play");
		}
	}

	class OpenWarningOrBuild implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Village.villagePlum < market[marketindex].price) {
				change("marketwarning");
			} else {
				Village.villagePlum -= market[marketindex].price;
				change("build");
			}
		}
	}

	class OpenPurchasePopup implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			marketindex = makeHash(e);
			change("purchase");
		}
	}

	class SetJob implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			person = Person.getPerson();

			if (Village.joblessMember > 0) {
				jobindex = makeHash(e);
				int i;
				for (i = 0; i < person.length; i++) {
					if (person[i].jobName == "Jobless") {
						person[i] = person[i].changeJob(person[i], jobindex);
						setJobLabel.setText("마을 주민" + i + "의 직업은 " + person[i].jobName + "입니다!");
						Village.joblessMember -= 1;
						setJob2Label.setText("우리 마을의 백수는 총 " + Village.joblessMember + "명입니다.");
						break;
					}
				}
				if (Village.joblessMember <= 0) {
					change("notice");
				}
			}
		}
	}

	class ChangeContentMouse implements MouseListener, MouseMotionListener {
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {
			villageHappyLabel.setText("" + Village.villageHappy);
			villagePlumLabel.setText("" + Village.villagePlum);
			villagePeopleLabel.setText("" + Village.villagePopulation);
			if (Village.playType == "sadending") {
				endingbg = new ImageIcon("img/sadending.png");
			} else if (Village.playType == "happyending") {
				endingbg = new ImageIcon("img/happyending.png");
			}
			if (endingbg != null) {
				change("ending");
			}
		}
		public void mouseExited(MouseEvent e) {}
		public void mouseDragged(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) {
			villageHappyLabel.setText("" + Village.villageHappy + "");
			villagePlumLabel.setText("" + Village.villagePlum + "");
			villagePeopleLabel.setText("" + Village.villagePopulation + "");
			if (Village.playType == "sadending") {
				endingbg = new ImageIcon("img/sadending.png");
			} else if (Village.playType == "happyending") {
				endingbg = new ImageIcon("img/happyending.png");
			}
			if (endingbg != null) {
				change("ending");
			}
		}

	}

	class IntroPanel extends JPanel {
		ImageIcon introbg = new ImageIcon("img/intro.png");

		public void paintComponent(Graphics g) {
			g.drawImage(introbg.getImage(), 0, 0, getWidth(), getHeight(), this);
		}

		IntroPanel() {
			JLabel introinfo = new JLabel("클릭하면 게임이 시작됩니다.");
			setLabelLSA(introinfo, 370, 300, 400, 50);
			this.addMouseListener(new MouseListener(){
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {
					change("intro1");
				}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}

	}

	class IntroPanel1 extends JPanel {
		ImageIcon intro1 = new ImageIcon("img/intro1.png");

		public void paintComponent(Graphics g) {
			g.drawImage(intro1.getImage(), 0, 0, getWidth(), getHeight(), this);
		}

		IntroPanel1() {
			this.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {
					change("intro2");
				}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}

		
	}

	class IntroPanel2 extends JPanel {
		ImageIcon intro2 = new ImageIcon("img/intro2setname.png");

		public void paintComponent(Graphics g) {
			g.drawImage(intro2.getImage(), 0, 0, getWidth(), getHeight(), this);
		}

		IntroPanel2() {
			JTextField inputVillageNameField = new JTextField("");
			setTextLSA(inputVillageNameField, 330, 327, 320, 50);
			this.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {
					Village.villageName = inputVillageNameField.getText();
					change("intro3");
				}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
			inputVillageNameField.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {}
				public void keyReleased(KeyEvent e) {}
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == 10) {
						Village.villageName = inputVillageNameField.getText();
						change("intro3");
					}
				}
			});
			
		}
	}

	class IntroPanel3 extends JPanel {
		ImageIcon intro3 = new ImageIcon("img/intro3.png");

		public void paintComponent(Graphics g) {
			g.drawImage(intro3.getImage(), 0, 0, getWidth(), getHeight(), this);
		}

		IntroPanel3() {
			this.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {
					change("play");
				}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}

		
	}

	class PlayPanel extends JPanel {

		ImageIcon bg = new ImageIcon("img/background.jpg");

		public void paintComponent(Graphics g) {
			g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
		}

		public PlayPanel(Container contentPane) {
			product = Product.getProduct();

			buildProduct(product);

			JLabel villageNameLabel = new JLabel(Village.villageName);
			setLabelLSA(villageNameLabel, 10, 10, 200, 38);

			villagePeopleLabel = new JLabel("" + Village.villagePopulation);
			setLabelLSA(villagePeopleLabel, 500, 12, 167, 38);
			ImageIcon villagePeople = new ImageIcon("img/people.png");
			JLabel villagePeopleimg = new JLabel(villagePeople);
			setLabelLSA(villagePeopleimg, 450, 10, 167, 38);

			villageHappyLabel = new JLabel("" + Village.villageHappy);
			setLabelLSA(villageHappyLabel, 675, 12, 167, 38);
			ImageIcon villageHappy = new ImageIcon("img/happyave.png");
			JLabel villageHappyimg = new JLabel(villageHappy);
			setLabelLSA(villageHappyimg, 625, 10, 167, 38);

			villagePlumLabel = new JLabel("" + Village.villagePlum);
			setLabelLSA(villagePlumLabel, 850, 12, 167, 38);
			ImageIcon villagePlum = new ImageIcon("img/plum.png");
			JLabel villagePlumimg = new JLabel(villagePlum);
			setLabelLSA(villagePlumimg, 800, 10, 167, 38);

			JButton messageIconButton = new JButton("");
			setButtonLSA(messageIconButton, 839, 390, 60, 54);
			if (Village.joblessMember > 0) {
				messageIconButton.addActionListener(new OpenChooseJob());
			} else {
				messageIconButton.addActionListener(new OpenNotice());
			}
			ImageIcon messageIcon = new ImageIcon("img/messageicon.png");
			JLabel messageIconLabel = new JLabel(messageIcon);
			setLabelLSA(messageIconLabel, 839, 390, 60, 54);

			JButton marketIconButton = new JButton("");
			setButtonLSA(marketIconButton, 907, 390, 60, 54);
			marketIconButton.addActionListener(new OpenMarket());
			ImageIcon marketIcon = new ImageIcon("img/marketicon.png");
			JLabel marketIconLabel = new JLabel(marketIcon);
			setLabelLSA(marketIconLabel, 907, 390, 60, 54);

			this.addMouseListener(new ChangeContentMouse());
			this.addMouseMotionListener(new ChangeContentMouse());

		}

	}

	class MarketPanel extends JPanel {
		ImageIcon marketbg = new ImageIcon("img/market.png");

		public void paintComponent(Graphics g) {
			g.drawImage(marketbg.getImage(), 0, 0, getWidth(), getHeight(), this);
		}

		MarketPanel() {
			market = Product.getMarket();

			JButton house1PurButton = new JButton("");
			setButtonLSA(house1PurButton, 157, 83, 165, 165);
			house1PurButton.addActionListener(new OpenPurchasePopup());

			JButton house2PurButton = new JButton("");
			setButtonLSA(house2PurButton, 330, 83, 165, 165);
			house2PurButton.addActionListener(new OpenPurchasePopup());

			JButton house4PurButton = new JButton("");
			setButtonLSA(house4PurButton, 503, 83, 165, 165);
			house4PurButton.addActionListener(new OpenPurchasePopup());

			JButton house8PurButton = new JButton("");
			setButtonLSA(house8PurButton, 676, 83, 165, 165);
			house8PurButton.addActionListener(new OpenPurchasePopup());

			JButton flowerPurButton = new JButton("");
			setButtonLSA(flowerPurButton, 157, 256, 165, 165);
			flowerPurButton.addActionListener(new OpenPurchasePopup());

			JButton treePurButton = new JButton("");
			setButtonLSA(treePurButton, 330, 256, 165, 165);
			treePurButton.addActionListener(new OpenPurchasePopup());

			JButton benchPurButton = new JButton("");
			setButtonLSA(benchPurButton, 503, 256, 165, 165);
			benchPurButton.addActionListener(new OpenPurchasePopup());

			JButton fountainPurButton = new JButton("");
			setButtonLSA(fountainPurButton, 676, 256, 165, 165);
			fountainPurButton.addActionListener(new OpenPurchasePopup());

			setLabelLSA(villagePlumLabel, 700, 30, 167, 38);

			JButton exitMarketButton = new JButton("");
			setButtonLSA(exitMarketButton, 790, 28, 45, 40);
			exitMarketButton.addActionListener(new Close());

			this.addMouseListener(new ChangeContentMouse());
			this.addMouseMotionListener(new ChangeContentMouse());
		}
	}

	class PurchasePanel extends JPanel {
		ImageIcon purchbg = new ImageIcon("img/purchase.png");

		public void paintComponent(Graphics g) {
			g.drawImage(purchbg.getImage(), 0, 0, getWidth(), getHeight(), this);
		}

		PurchasePanel(int price) {
			setLabelLSA(villagePlumLabel, 700, 30, 167, 38);

			JLabel purchaseInfoLabel = new JLabel("" + price + "개의 플럼이 차감됩니다.");
			setLabelLSA(purchaseInfoLabel, 370, 210, 300, 40);
			JLabel purchaseInfoLabel2 = new JLabel("구매하시겠습니까?");
			setLabelLSA(purchaseInfoLabel2, 410, 235, 300, 40);

			JButton exitPurchaseButton = new JButton("");
			setButtonLSA(exitPurchaseButton, 750, 140, 35, 35);
			exitPurchaseButton.addActionListener(new OpenMarket());

			JButton okPurchaseButton = new JButton("");
			setButtonLSA(okPurchaseButton, 370, 290, 122, 33);
			okPurchaseButton.addActionListener(new OpenWarningOrBuild());

			JButton cancelPurchaseButton = new JButton("");
			setButtonLSA(cancelPurchaseButton, 496, 290, 122, 33);
			cancelPurchaseButton.addActionListener(new OpenMarket());

			this.addMouseListener(new ChangeContentMouse());
			this.addMouseMotionListener(new ChangeContentMouse());

		}
	}

	class MarketWarningPanel extends JPanel {
		ImageIcon marketwarningbg = new ImageIcon("img/marketwarning.png");

		public void paintComponent(Graphics g) {
			g.drawImage(marketwarningbg.getImage(), 0, 0, getWidth(), getHeight(), this);
		}

		MarketWarningPanel() {
			setLabelLSA(villagePlumLabel, 700, 30, 167, 38);

			JLabel marketWarningInfoLabel = new JLabel("보유한 플럼보다 더 많은 양의 플럼이 필요해요!");
			setLabelLSA(marketWarningInfoLabel, 290, 230, 450, 40);

			JButton exitmarketWarningButton = new JButton("");
			setButtonLSA(exitmarketWarningButton, 750, 140, 35, 35);
			exitmarketWarningButton.addActionListener(new OpenMarket());

			JButton okmarketWarningButton = new JButton("");
			setButtonLSA(okmarketWarningButton, 370, 290, 122, 33);
			okmarketWarningButton.addActionListener(new OpenMarket());

			JButton cancelmarketWarningButton = new JButton("");
			setButtonLSA(cancelmarketWarningButton, 496, 290, 122, 33);
			cancelmarketWarningButton.addActionListener(new OpenMarket());

			this.addMouseListener(new ChangeContentMouse());
			this.addMouseMotionListener(new ChangeContentMouse());
		}
	}

	class BuildPanel extends JPanel {
		ImageIcon buildbg = new ImageIcon("img/build.png");

		public void paintComponent(Graphics g) {
			g.drawImage(buildbg.getImage(), 0, 0, getWidth(), getHeight(), this);
		}

		BuildPanel(int index) {
			product = Product.getProduct();
			buildProduct(product);

			this.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {
					int i = 0;
					for (i = 0; i < product.length; i++) {
						if (product[i].productTF == false) {
							break;
						}
					}
					product[i] = product[i].makeItem(product[i], index);
					product[i].locationX = e.getX() - (product[i].icon.getIconWidth() / 2);
					product[i].locationY = e.getY() - product[i].icon.getIconHeight();
				
					change("play");
				}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
	}

	class ChooseJobPanel extends JPanel {
		ImageIcon choosejobbg = new ImageIcon("img/choosejob.png");

		public void paintComponent(Graphics g) {
			g.drawImage(choosejobbg.getImage(), 0, 0, getWidth(), getHeight(), this);
		}

		ChooseJobPanel() {
			person = Person.getPerson();

			JButton exitJobButton = new JButton("나가기");
			setButtonLSA(exitJobButton, 800, 105, 45, 40);
			exitJobButton.addActionListener(new Close());

			setJobLabel = new JLabel("마을 주민에게 직업을 정해 주세요");
			setLabelLSA(setJobLabel, 400, 180, 300, 40);

			setJob2Label = new JLabel("우리 마을의 백수는 총 " + Village.joblessMember + "명입니다.");
			setLabelLSA(setJob2Label, 400, 215, 300, 20);

			JButton setJob1Button = new JButton("");
			setButtonLSA(setJob1Button, 220, 245, 104, 40);
			setJob1Button.addActionListener(new SetJob());

			JButton setJob2Button = new JButton("");
			setButtonLSA(setJob2Button, 327, 245, 104, 40);
			setJob2Button.addActionListener(new SetJob());

			JButton setJob3Button = new JButton("");
			setButtonLSA(setJob3Button, 434, 245, 104, 40);
			setJob3Button.addActionListener(new SetJob());

			JButton setJob4Button = new JButton("");
			setButtonLSA(setJob4Button, 541, 245, 104, 40);
			setJob4Button.addActionListener(new SetJob());

			JButton setJob5Button = new JButton("");
			setButtonLSA(setJob5Button, 648, 245, 104, 40);
			setJob5Button.addActionListener(new SetJob());
		}
	}

	class NoticePanel extends JPanel {
		ImageIcon noticebg = new ImageIcon("img/notice.png");

		public void paintComponent(Graphics g) {
			g.drawImage(noticebg.getImage(), 0, 0, getWidth(), getHeight(), this);
		}

		NoticePanel() {
			JLabel noticeLabel = new JLabel("");
			int x = 0;
			if (Village.joblessMember <= 0 && Village.homelessMember <= 0) {
				noticeLabel.setText("살기 좋은 행복한 마을이에요.");
				x = 360;
			} else if (Village.homelessMember > 0) {
				noticeLabel.setText("노숙자가 있어요! 빨리 집을 지어주세요.");
				x = 310;
			} else {
				noticeLabel.setText("마을 주민들을 잘 돌봐주세용");
				x = 360;
			}
			setLabelLSA(noticeLabel, x, 200, 500, 40);

			JButton okNoticeButton = new JButton("");
			setButtonLSA(okNoticeButton, 360, 262, 120, 33);
			okNoticeButton.addActionListener(new Close());

			JButton cancelNoticeButton = new JButton("");
			setButtonLSA(cancelNoticeButton, 484, 262, 120, 33);
			cancelNoticeButton.addActionListener(new Close());

			JButton exitNoticeButton = new JButton("");
			setButtonLSA(exitNoticeButton, 736, 110, 35, 35);
			exitNoticeButton.addActionListener(new Close());
		}

	}

	class EndingPanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.drawImage(endingbg.getImage(), 0, 0, getWidth(), getHeight(), this);
		}

		EndingPanel() {
			product = Product.getProduct();
			JButton endingButton = new JButton("");
			setButtonLSA(endingButton, 435, 330, 120, 33);

			endingButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
		}
	}

	Graphic() { // 생성자
		contentPane = this.getContentPane();
		contentPane.removeAll();
		contentPane.add(new IntroPanel());

		super.setTitle("Catch The Village");
		this.setSize(1000, 500);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
