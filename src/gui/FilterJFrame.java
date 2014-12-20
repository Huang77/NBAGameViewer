package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import viewmodel.TimeView;

public class FilterJFrame extends JFrame implements ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int selectedNum = 5;
	static int checkboxNum = 21;
	
	TimeView timeView;
	
	JPanel labelPanel;
	JLabel label;
	JPanel bottonPanel;
	JButton button;
	
	// checkbox
	JPanel checkBoxPanel;
	
	
	JCheckBox[] checkbox;
	
	ArrayList<String> selectedAttr = new ArrayList<String>();
	
	public FilterJFrame (TimeView timeView) {
	
		this.timeView = timeView;
		this.setTitle("Filter");
		this.setSize(360, 350);
		this.setResizable(false);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		initComponents();
		setActionListener();
		this.setVisible(true);
	}
	
	public void initComponents () {
		labelPanel = new JPanel(new GridLayout());
		label = new JLabel("Select attributes:");
		labelPanel.add(label);
		this.add(labelPanel);
		
		initCheckBox();
		this.add(checkBoxPanel);
		
		bottonPanel = new JPanel();
		button = new JButton("Display");
		bottonPanel.add(button);
		this.add(bottonPanel);
		

	}
	
	public void initCheckBox () {
		checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new GridLayout(7, 3, 5, 10));
		
		checkbox = new JCheckBox[checkboxNum];

		checkbox[0] = new JCheckBox("Field Goal", false);
		checkbox[0].addItemListener(this);
		checkBoxPanel.add(checkbox[0]);
		checkbox[1] = new JCheckBox("FGA", false);
		checkbox[1].setToolTipText("Field Goal Attempts");
		checkbox[1].addItemListener(this);
		checkBoxPanel.add(checkbox[1]);
		checkbox[2] = new JCheckBox("FGP%", false);
		checkbox[2].addItemListener(this);
		checkBoxPanel.add(checkbox[2]);
		
		checkbox[3] = new JCheckBox("2-Points", false);
		checkbox[3].addItemListener(this);
		checkBoxPanel.add(checkbox[3]);
		checkbox[4] = new JCheckBox("2PA", false);
		checkbox[4].addItemListener(this);
		checkBoxPanel.add(checkbox[4]);
		checkbox[5] = new JCheckBox("2PP%", false);
		checkbox[5].addItemListener(this);
		checkBoxPanel.add(checkbox[5]);
		
		checkbox[6] = new JCheckBox("3-Points", false);
		checkbox[6].addItemListener(this);
		checkBoxPanel.add(checkbox[6]);
		checkbox[7] = new JCheckBox("3PA", false);
		checkbox[7].addItemListener(this);
		checkBoxPanel.add(checkbox[7]);
		checkbox[8] = new JCheckBox("3PP%", false);
		checkbox[8].addItemListener(this);
		checkBoxPanel.add(checkbox[8]);
		
		checkbox[9] = new JCheckBox("Free Throw", false);
		checkbox[9].addItemListener(this);
		checkBoxPanel.add(checkbox[9]);
		checkbox[10] = new JCheckBox("FTA", false);
		checkbox[10].addItemListener(this);
		checkBoxPanel.add(checkbox[10]);
		checkbox[11] = new JCheckBox("FTP%", false);
		checkbox[11].addItemListener(this);
		checkBoxPanel.add(checkbox[11]);
		
		checkbox[12] = new JCheckBox("Rebound", false);
		checkbox[12].addItemListener(this);
		checkBoxPanel.add(checkbox[12]);
		checkbox[13] = new JCheckBox("Offense RB", false);
		checkbox[13].addItemListener(this);
		checkBoxPanel.add(checkbox[13]);
		checkbox[14] = new JCheckBox("Defense RB", false);
		checkbox[14].addItemListener(this);
		checkBoxPanel.add(checkbox[14]);
		
		
		checkbox[15] = new JCheckBox("Points", false);
		checkbox[15].addItemListener(this);
		checkBoxPanel.add(checkbox[15]);
	
		checkbox[16] = new JCheckBox("Assist", false);
		checkbox[16].addItemListener(this);
		checkBoxPanel.add(checkbox[16]);
		checkbox[17] = new JCheckBox("Steal", false);
		checkbox[17].addItemListener(this);
		checkBoxPanel.add(checkbox[17]);
		checkbox[18] = new JCheckBox("Block", false);
		checkbox[18].addItemListener(this);
		checkBoxPanel.add(checkbox[18]);
		checkbox[19] = new JCheckBox("Turnover", false);
		checkbox[19].addItemListener(this);
		checkBoxPanel.add(checkbox[19]);
		checkbox[20] = new JCheckBox("Personal Foul", false);
		checkbox[20].addItemListener(this);
		checkBoxPanel.add(checkbox[20]);
	
	}
	public void setActionListener () {
		
		button.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				timeView.selectedAttr.clear();
				for (int i = 0;  i < selectedAttr.size(); i++) {
					timeView.selectedAttr.add(selectedAttr.get(i));
				}
				timeView.resetAttrDisplay();
			}
			
		});
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == e.SELECTED) {
			for (int i = 0; i < this.checkboxNum; i++) {
				if (e.getSource() == checkbox[i]) {
					selectedAttr.add(checkbox[i].getText());
					break;
				}
			}
		} else if (e.getStateChange() == e.DESELECTED) {
			for (int i = 0; i < this.checkboxNum; i++) {
				if (e.getSource() == checkbox[i]) {
					selectedAttr.remove(checkbox[i].getText());
					break;
				}
			}
		}
	}
}
