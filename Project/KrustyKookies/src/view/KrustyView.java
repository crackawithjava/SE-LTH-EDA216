package view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import se.datadosen.component.RiverLayout;

public class KrustyView extends JFrame {

	private static final long serialVersionUID = -7322546617726420651L;
	private JFrame content;
	private JTextField inputText, fromDate, toDate;
	private JButton searchButton, blockedTrue, blockedFalse;
	private JTextArea searchOutput, productionOutput;
	private String[] searchDescription = { "Search for pallet",
			"Block pallets", "Search quantity"};
	private JComboBox searchCombo = new JComboBox();

	public static final int SEARCH_FOR_PALLET = 0;
	public static final int BLOCK_PALLET = 1;
	public static final int SEARCH_QUANTITY = 2;

	public KrustyView() {
		content = new JFrame();
		content.setLayout(new RiverLayout());
		JLabel searchTerm = new JLabel("Search: ");
		content.add(searchTerm);
		this.inputText = new JTextField("", 20);
		content.add("left", inputText);
		JLabel fromDateLabel = new JLabel("From date: ");
		content.add(fromDateLabel);
		this.fromDate = new JTextField("", 6);
		content.add(fromDate);

		JLabel toDateLabel = new JLabel("To date: ");
		content.add(toDateLabel);
		this.toDate = new JTextField("", 6);
		content.add(toDate);

		for (String s : searchDescription) {
			searchCombo.addItem(s);
		}
		content.add("tab", searchCombo);
		this.searchButton = new JButton("Search");
		content.add("tab", searchButton);
		
		GridLayout outputLayout = new GridLayout(0, 2);
		outputLayout.setHgap(10);
		JPanel outputPanel = new JPanel(outputLayout);
		content.add("p hfill vfill", outputPanel);

		searchOutput = new JTextArea();
		searchOutput.setEditable(false);
		TitledBorder searchTitle;
		searchTitle = BorderFactory.createTitledBorder("Search results");
		searchTitle.setTitleJustification(TitledBorder.RIGHT);
		searchOutput.setBorder(searchTitle);
		searchOutput.setLineWrap(true);
		outputPanel.add(new JScrollPane(searchOutput));
		productionOutput = new JTextArea();
		productionOutput.setEditable(false);
		TitledBorder productionTitle;
		productionTitle = BorderFactory.createTitledBorder("Production status");
		productionTitle.setTitleJustification(TitledBorder.RIGHT);
		productionOutput.setBorder(productionTitle);
		productionOutput.setLineWrap(true);
		outputPanel.add(new JScrollPane(productionOutput));

		this.blockedTrue = new JButton("Blocked");
		content.add("p", blockedTrue);
		this.blockedFalse = new JButton("Not blocked");
		content.add("", blockedFalse);
		
		content.setSize(1024, 768);
		content.setTitle("Krusty Kookies");
		content.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		content.setVisible(true);
		content.setResizable(false);
	}

	public void updateSearchBox(String result) {
		SwingUtilities.invokeLater(new SearchResultJob(result));
	}

	public void insertToProductionBox(String toInsert) {
		SwingUtilities.invokeLater(new LogJob(toInsert));
		productionOutput.setCaretPosition(productionOutput.getDocument().getLength());
	}

	public String getSearchText() {
		return inputText.getText().trim();
	}

	public void addSearchListener(ActionListener actionListener) {
		searchButton.addActionListener(actionListener);
	}
	
	public void addBlockedButtonListener(ActionListener actionListener) {
		blockedTrue.addActionListener(actionListener);
	}
	
	public void addNotBlockedButtonListener(ActionListener actionListener) {
		blockedFalse.addActionListener(actionListener);
	}

	public void addComboBoxActionListener(ActionListener actionListener) {
		searchCombo.addActionListener(actionListener);
	}

	public int getSelectedAction() {
		return searchCombo.getSelectedIndex();
	}

	public String getFromDate() {
		return fromDate.getText().trim();
	}

	public String getToDate() {
		return toDate.getText().trim();
	}

	public void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(content, message, "Error",
				JOptionPane.ERROR_MESSAGE);
	}
	class LogJob implements Runnable {
		private String toLog;
		public LogJob(String toLog) {
			this.toLog = toLog;
		}
		
		@Override
		public void run() {
			productionOutput.append(toLog + '\n');
		}
		
	}
	
	class SearchResultJob implements Runnable {
		private String toLog;
		public SearchResultJob(String toLog) {
			this.toLog = toLog;
		}
		
		@Override
		public void run() {
			searchOutput.setText(toLog);
		}
	}
}
