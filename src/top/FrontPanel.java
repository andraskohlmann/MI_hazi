package top;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class FrontPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private LoadingPanel loading;

	public FrontPanel(Visualizer v) {
		JButton jb = new JButton("Start Experiment");
		jb.setActionCommand("start");
		jb.addActionListener(v);
		
		JPanel pan = new JPanel();
		pan.add(jb);
		
		setLayout(new GridLayout(0, 1));
		
		loading = new LoadingPanel();

		add(pan);
		add(loading);
	}
	
	public void setPercent(double p) {
		loading.setPercent(p);
		loading.repaint();
	}

}
