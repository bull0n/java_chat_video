
package ch.hearc.cours.videochat.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import ch.hearc.cours.tools.gui.MagasinImage;
import ch.hearc.cours.videochat.data.UserData;

public class JMessageInput extends Box
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JMessageInput()
		{
		super(BoxLayout.X_AXIS);

		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public String getInput()
		{
		return this.jTextFieldMessageInput.getText();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		jTextFieldMessageInput = new JTextField();
		jButtonSend = new JButton("Envoyer");

		this.add(jTextFieldMessageInput);
		this.add(Box.createHorizontalGlue());
		this.add(jButtonSend);
		}

	private void control()
		{
		ActionListener actionListener = new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent event)
				{
				String nickname = UserData.getInstance().getNickname();
				ServiceGUI.getInstance().sendMessage(nickname + ": " + jTextFieldMessageInput.getText() + END_LINE);
				jTextFieldMessageInput.setText(EMPTY);
				}
			};

		jButtonSend.addActionListener(actionListener);
		jTextFieldMessageInput.addActionListener(actionListener);
		}

	private void appearance()
		{

		this.jButtonSend.setIcon(MagasinImage.SEND);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JTextField jTextFieldMessageInput;
	private JButton jButtonSend;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final int SPACE_WIDTH = 30;
	private static final String END_LINE = "\n";
	private static final String EMPTY = "";
	}
