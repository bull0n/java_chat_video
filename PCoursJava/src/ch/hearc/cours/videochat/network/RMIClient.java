
package ch.hearc.cours.videochat.network;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class RMIClient
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private RMIClient()
		{
		start();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public synchronized static RMIClient getInstance()
		{
		if (instance == null)
			{
			instance = new RMIClient();
			}

		return instance;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void start()
		{
		serverSide();
		clientSide();
		}

	private void clientSide()
		{
		RmiURL url = new RmiURL(Settings.ID_CLIENT, Settings.IP_CLIENT, Settings.PORT_CLIENT);
		Chat_I chatRemote;
		try
			{
			chatRemote = (Chat_I)RmiTools.connectionRemoteObjectBloquant(url, 1000, 100);
			work(chatRemote);
			}
		catch (RemoteException | MalformedURLException e)
			{
			System.err.println("[RMIClient] : clientSide : Remote error!");
			e.printStackTrace();
			}
		}

	private void work(Chat_I chatRemote)
		{
		try
			{
			System.out.println(chatRemote);
			ChatRemote.init(chatRemote);
			ChatRemote.getInstance();
			chatRemote.writeMessage(new Message("test"));
			}
		catch (RemoteException e)
			{
			e.printStackTrace();
			}
		}

	private void serverSide()
		{
		try
			{
			this.chat = Chat.getInstance();
			RmiTools.shareObject(this.chat, new RmiURL(Settings.ID_SERVER, Settings.PORT_SERVER));
			}
		catch (RemoteException | MalformedURLException e)
			{
			System.err.println(e);
			System.err.println("[RMIClient] : serverSide() : Impossible de partager l'objet Webcam : w");
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Tools
	private Chat_I chat;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static RMIClient instance = null;
	}