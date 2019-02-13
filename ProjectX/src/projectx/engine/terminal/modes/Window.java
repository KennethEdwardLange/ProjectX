package projectx.engine.terminal.modes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import projectx.engine.Handler;
import projectx.engine.terminal.Terminal;

import javax.swing.text.AbstractDocument;

public class Window extends Mode implements ActionListener {
	
	JFrame frame;

    // GUI stuff
    public JTextArea  enteredText = new JTextArea(15, 48);
    private JTextField typedText   = new JTextField(48);

    public Window(Terminal terminal, Handler handler) {
    	super(terminal, handler);
    }
    
    @Override
	public void init() {
    	frame = new JFrame();
    	
        frame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                	frame.setVisible(false);
                	frame.dispose();
                }
            }
        );
        
        Font font1 = new Font("Courier", Font.BOLD, 24);
         
        typedText.setFont(font1);
        enteredText.setFont(font1);
        
        DocumentFilter filter = new LowercaseDocumentFilter();
        ((AbstractDocument) enteredText.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) typedText.getDocument()).setDocumentFilter(filter);


        // create GUI stuff
        enteredText.setEditable(false);
        enteredText.setBackground(Color.WHITE);
        enteredText.setLineWrap(true);
        typedText.addActionListener(this);

        Container content = frame.getContentPane();
        content.add(new JScrollPane(enteredText), BorderLayout.CENTER);
        content.add(typedText, BorderLayout.SOUTH);


        // display the window, with focus on typing box
        frame.setTitle("Terminal");
        frame.pack();
        typedText.requestFocusInWindow();
	}
    
    public void run() {
    	frame.setVisible(true); //you can't see me!
    }
	
	public void stop() {
		terminal.setEnabled(false);
		frame.setVisible(false); //you can't see me!
		frame.dispose(); //Destroy the JFrame object
	}
	
	public void pause() {
		terminal.setEnabled(false);
		frame.setVisible(false); //you can't see me!
	}
	
	public void returnCommand() {}
	
	public void update() {}
	
	public void render(Graphics g) {}
	
	public void print(String s) {enteredText.append(s + "\n");frame.repaint();enteredText.repaint();}
	
	public String toString() {return "";}

    public void actionPerformed(ActionEvent e) {
    	String text = typedText.getText();
    	typedText.setText("");
    	enteredText.append(text + "\n");
    	terminal.runCommand(text);
    	typedText.selectAll();
        typedText.requestFocusInWindow();
        enteredText.setCaretPosition(enteredText.getDocument().getLength());
    }
    
    class LowercaseDocumentFilter extends DocumentFilter {
        public void insertString(DocumentFilter.FilterBypass fb, int offset,
                String text, AttributeSet attr) throws BadLocationException {

            fb.insertString(offset, text.toLowerCase(), attr);
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                String text, AttributeSet attrs) throws BadLocationException {

            fb.replace(offset, length, text.toLowerCase(), attrs);
        }
    }

	@Override
	public void render(projectx.engine.glgfx.Graphics g) {}

	@Override
	public boolean validate() {
		return true;
	}
}