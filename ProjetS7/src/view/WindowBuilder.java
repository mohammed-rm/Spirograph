package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.BottomItems;
import controller.Draw;
import controller.IconsResize;
import java.awt.Font;

public class WindowBuilder implements ChangeListener {

	private final JFrame frame = new JFrame();
	private final static int FRAME_WIDTH = 1200;
	private final static int FRAME_HEIGHT = 800;
	private Panel panel;
	private JLabel labClose;
	private JLabel labMin;
	private int yMouse;
	private int xMouse;
	private Bottom bottom;
	private BottomItems itemRights;
	private Top top;
	private DataComponents data;
	private JSlider sliderR;
	private JSlider sliderSmallR;
	private JSlider sliderPen;

	private final Draw draw = new Draw();
	private JLabel nameR;
	private JLabel nameSmallR;
	private JLabel namePen;

	public WindowBuilder() {
	
		initFrame();
		initComponent();
		iconsConfig();
		addComponents();
		frameListener();
		toMoveFrame();

		draw.setXscale(-3, +3);
		draw.setYscale(-2, +2);
		draw.enableDoubleBuffering();
		JLabel canvas = draw.getJLabel();

		frame.getContentPane().add(canvas, BorderLayout.WEST);
		frame.getContentPane().add(panel);
		
		nameR = new JLabel("Fixed Circle Radius");
		nameR.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
		nameR.setHorizontalAlignment(SwingConstants.LEFT);
		nameR.setBounds(2, 40, 140, 17);
		panel.add(nameR);
		
		nameSmallR = new JLabel("Moving Circle Radius");
		nameSmallR.setHorizontalAlignment(SwingConstants.LEFT);
		nameSmallR.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
		nameSmallR.setBounds(2, 290, 150, 17);
		panel.add(nameSmallR);
		
		namePen = new JLabel("Pen Length");
		namePen.setHorizontalAlignment(SwingConstants.LEFT);
		namePen.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
		namePen.setBounds(2, 540, 130, 17);
		panel.add(namePen);
		
		test();
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		stateChanged(null);
		frame.setVisible(true);
	}

	public void initFrame() {
		frame.setUndecorated(true);
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setPreferredSize(new Dimension(getFrameWidth(), FRAME_HEIGHT));
		frame.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screenSize.width / 2 - frame.getPreferredSize().width / 2,
				screenSize.height / 2 - frame.getPreferredSize().height / 2);
	}

	public void initComponent() {
		panel = new Panel();
		bottom = new Bottom();
		itemRights = new BottomItems();
		top = new Top();
		data = new DataComponents();
		sliderR = data.fixedSl();
		sliderSmallR = data.movingSl();
		sliderPen = data.penSl();
		sliderR.addChangeListener(this);
		sliderSmallR.addChangeListener(this);
		sliderPen.addChangeListener(this);
	}

	public void addComponents() {
		bottom.add(itemRights.createLabName());
		bottom.add(itemRights.createTime());
		frame.getContentPane().add(bottom);
		frame.getContentPane().add(top);

		frame.getContentPane().add(sliderR);
		frame.getContentPane().add(sliderSmallR);
		frame.getContentPane().add(sliderPen);
		frame.getContentPane().add(data.getFixedValue());
		frame.getContentPane().add(data.getMovingValue());
		frame.getContentPane().add(data.getPenValue());
	}

	/**
	 * Close and minimize icons
	 */
	public void iconsConfig() {
		labClose = new JLabel();
		labClose.setHorizontalAlignment(SwingConstants.CENTER);
		labClose.setIcon(IconsResize
				.getScaledImage(new ImageIcon(WindowBuilder.class.getResource("/icons/close_in.png")), 18, 18));
		labClose.setBounds(frame.getPreferredSize().width - 30, 5, 20, 20);
		labClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				labClose.setIcon(IconsResize
						.getScaledImage(new ImageIcon(WindowBuilder.class.getResource("/icons/close.png")), 18, 18));
				labClose.setBounds(frame.getPreferredSize().width - 30, 5, 20, 20);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				labClose.setIcon(IconsResize
						.getScaledImage(new ImageIcon(WindowBuilder.class.getResource("/icons/close_in.png")), 18, 18));
				labClose.setBounds(frame.getPreferredSize().width - 30, 5, 20, 20);
			}
		});
		frame.getContentPane().add(labClose);

		labMin = new JLabel();
		labMin.setHorizontalAlignment(SwingConstants.CENTER);
		labMin.setIcon(IconsResize.getScaledImage(new ImageIcon(WindowBuilder.class.getResource("/icons/minimize.png")),
				18, 18));
		labMin.setBounds(frame.getPreferredSize().width - 60, 5, 20, 20);
		labMin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				labMin.setIcon(IconsResize.getScaledImage(
						new ImageIcon(WindowBuilder.class.getResource("/icons/minimize_in.png")), 18, 18));
				labMin.setBounds(frame.getPreferredSize().width - 60, 5, 20, 20);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				labMin.setIcon(IconsResize
						.getScaledImage(new ImageIcon(WindowBuilder.class.getResource("/icons/minimize.png")), 18, 18));
				labMin.setBounds(frame.getPreferredSize().width - 60, 5, 20, 20);
			}
		});
		frame.getContentPane().add(labMin);
	}

	/**
	 * Add listeners to the frame to change back the minimize icon to its original
	 * state
	 */
	public void frameListener() {
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				labMin.setIcon(IconsResize
						.getScaledImage(new ImageIcon(WindowBuilder.class.getResource("/icons/minimize.png")), 18, 18));
				labMin.setBounds(frame.getPreferredSize().width - 60, 5, 20, 20);
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});
	}

	/**
	 * Add listeners to move the undecorated frame when the mouse is dragged
	 */
	public void toMoveFrame() {
		frame.addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				frame.setLocation(frame.getLocation().x + e.getX() - xMouse, frame.getLocation().y + e.getY() - yMouse);
			}
		});
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xMouse = e.getX();
				yMouse = e.getY();
			}
		});
	}

	/**
	 * @return the frameWidth
	 */
	public static int getFrameWidth() {
		return FRAME_WIDTH;
	}

	/**
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	@Override
	public void stateChanged(ChangeEvent evt) {

		double R = Math.toRadians(sliderR.getValue());
		double r = Math.toRadians(sliderSmallR.getValue());
		double d = Math.toRadians(sliderPen.getValue());

		data.getFixedValue().setText("Value R : " + sliderR.getValue());
		data.getMovingValue().setText("Value r : " + sliderSmallR.getValue());
		data.getPenValue().setText("Value d : " + sliderPen.getValue());

		draw.clear(Color.BLACK);
		draw.show();
		for (double t = 0.0; t < 300; t += 0.002) {
			// Outside movement
			// double x = (R + r) * Math.cos(t) - (r + a) * Math.cos(((R + r) / r) * t);
			// double y = (R + r) * Math.sin(t) - (r + a) * Math.sin(((R + r) / r) * t);

			// Inside movement
			double x = (R - r) * Math.cos(t) + d * Math.cos(((R - r) / r) * t);
			double y = (R - r) * Math.sin(t) - d * Math.sin(((R - r) / r) * t);
			draw.setPenColor(Color.getHSBColor((float) (t / Math.PI), 1.0f, 1.0f));
			draw.point(x, y);
			frame.repaint();
		}
		draw.show();
	}
	
	public void test() {
		double R = 100;
		double r = 69;
		double d = 80;
		
		ActionListener updateSpiro = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double t = 0;
				double x = (R - r) * Math.cos(t) + d * Math.cos(((R - r) / r) * t);
				double y = (R - r) * Math.sin(t) - d * Math.sin(((R - r) / r) * t);
				draw.clear(Color.WHITE);
				draw.setPenColor(Color.getHSBColor((float) (t / Math.PI), 1.0f, 1.0f));
				draw.point(50, 40);
				frame.repaint();
				
				t++;
			
			}
		};
		Timer tm = new Timer(10000, updateSpiro);
		tm.start();
	}
}