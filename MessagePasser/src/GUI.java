import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JPanel implements ActionListener {
	protected JTextField nameField;
	protected JTextField typeField;
	protected JTextField dataField;
	protected JTextArea textArea;
	protected JButton sendButton;
	private final static String newline = "\n";
	protected static final String name = "NAME";
	protected static final String type = "TYPE";
	protected static final String data = "DATA";
	protected JLabel actionLabel;

	public GUI() {
		super(new GridBagLayout());

		nameField = new JTextField(20);
		nameField.setActionCommand(name);
		nameField.addActionListener(this);
		JLabel nameFieldLabel = new JLabel(name + ": ");
		nameFieldLabel.setLabelFor(nameField);

		typeField = new JTextField(20);
		typeField.addActionListener(this);
		JLabel typeFieldLabel = new JLabel(type + ": ");
		typeFieldLabel.setLabelFor(typeField);

		dataField = new JTextField(20);
		dataField.addActionListener(this);
		JLabel dataFieldLabel = new JLabel(data + ": ");
		dataFieldLabel.setLabelFor(dataField);
		
		//Create a label to put messages during an action event.
        actionLabel = new JLabel("Type text in a field and press Enter.");
        actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		
		sendButton = new JButton("SEND");
		sendButton.setVerticalTextPosition(AbstractButton.CENTER);
		sendButton.setHorizontalTextPosition(AbstractButton.LEADING); 
																		
		sendButton.setMnemonic(KeyEvent.VK_D);
		sendButton.setActionCommand("disable");
		sendButton.addActionListener(this);

		textArea = new JTextArea(5, 20);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);

		JPanel textControlsPane = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();

		// Add Components to this panel.
		GridBagConstraints c = new GridBagConstraints();
		textControlsPane.setLayout(gridbag);

		JLabel[] labels = { nameFieldLabel, typeFieldLabel, dataFieldLabel };
		JTextField[] textFields = { nameField, typeField, dataField };
		addLabelTextRows(labels, textFields, gridbag, textControlsPane);

		c.gridwidth = GridBagConstraints.REMAINDER;

		/*c.fill = GridBagConstraints.HORIZONTAL;
		add(nameField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		add(typeField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		add(dataField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		add(sendButton, c);*/

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		textControlsPane.add(actionLabel, c);
        textControlsPane.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Text Fields"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
		add(scrollPane, c);
	}

	public void actionPerformed(ActionEvent evt) {
		String text = nameField.getText();
		textArea.append(text + newline);
		nameField.selectAll();

		// Make sure the new text is visible, even if there
		// was a selection in the text area.
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("TextDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add contents to the window.
		frame.add(new GUI());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private void addLabelTextRows(JLabel[] labels, JTextField[] textFields,
			GridBagLayout gridbag, Container container) {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		int numLabels = labels.length;

		for (int i = 0; i < numLabels; i++) {
			c.gridwidth = GridBagConstraints.RELATIVE; // next-to-last
			c.fill = GridBagConstraints.NONE; // reset to default
			c.weightx = 0.0; // reset to default
			container.add(labels[i], c);

			c.gridwidth = GridBagConstraints.REMAINDER; // end row
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			container.add(textFields[i], c);
		}
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}