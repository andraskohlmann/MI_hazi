package top;

import javax.swing.JButton;
import javax.swing.JPanel;

public class FrontPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public FrontPanel(Visualizer v) {
		JButton jb = new JButton("Start Experiment");
		jb.setActionCommand("start");
		jb.addActionListener(v);

		add(jb);
	}

}
