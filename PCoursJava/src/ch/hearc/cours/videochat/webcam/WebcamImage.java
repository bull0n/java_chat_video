
package ch.hearc.cours.videochat.webcam;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class WebcamImage implements Serializable, Webcam_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public WebcamImage(BufferedImage bufferedImage)
		{
		this.webcamImage = bufferedImage;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/


	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setImage(BufferedImage image)
		{
		this.webcamImage = image;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@Override
	public BufferedImage getImage()
		{
		return this.webcamImage;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*		Serialisation			*|
	\*------------------------------*/

	private void writeObject(ObjectOutputStream oos) throws IOException
		{
		ImageIO.write(this.webcamImage, "jpeg", oos);
		}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
		{
		this.webcamImage = ImageIO.read(ois);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private BufferedImage webcamImage;

	}
