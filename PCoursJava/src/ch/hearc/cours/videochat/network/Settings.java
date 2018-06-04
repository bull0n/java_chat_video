
package ch.hearc.cours.videochat.network;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Map;

import com.bilat.tools.reseau.rmi.NetworkTools;
import com.bilat.tools.reseau.rmi.RmiAddress;
import com.bilat.tools.reseau.rmi.RmiTools;

public class Settings
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private Settings()
		{
		localAddress = RmiAddress.createLocal().getLocal();
		System.setProperty("java.rmi.server.hostname", localAddress.getHostAddress()); // Patch linux: ip of localhost
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void patchLocal(String address)
		{
		System.out.println("[Settings] : patchLocal : patch local address fix for linux");
		localAddress = getByName(address);
		System.setProperty("java.rmi.server.hostname", address); // Patch linux: ip of localhost
		}

	public static void initRemote(String address)
		{
		remoteAddress = getByName(address);
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public InetAddress getLocal()
		{
		return localAddress;
		}

	public InetAddress getRemote()
		{
		return remoteAddress;
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static Settings getInstance()
		{
		if (instance == null)
			{
			instance = new Settings();
			}
		return instance;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private static RmiAddress settings(String remote)
		{
		try
			{
			// RmiAddress contient 2 adresse, la locale et la remote
			// La locale peut etre fausse si il y a plusieurs NetworkInterface, fausse dans
			// le sens ou elle n'utilise peutetre pas le bon networkinterface
			// Ci-dessous quelques exemples:

			// 2 jvm, 2 pc differents:
			String remoteAdress = "157.26.100.94";
			RmiAddress rmiAdress = RmiAddress.create(remoteAdress); // si failed voir networkinterfaceChoice pour
																	// specifier le networkinterface
			if (rmiAdress == null)
				{
				rmiAdress = networkinterfaceChoice(remoteAdress);
				}

			// 2 jvm, meme pc
			// RmiAddress rmiAdress = RmiAddress.createLocal();
			System.out.println(rmiAdress);

			// Settings.init(rmiAdress);

			return rmiAdress;

			// RmiTools.setTimeout(5000);// experimentale
			}
		catch (Exception e)
			{
			e.printStackTrace();
			// ServiceGUI.getInstance().getServices().showError("Failed to launch
			// application!");
			}
		return null;
		}

	/**
	 * La locale adress peut poser problème! Attention a la spécifier lors du share
	 * !
	 *
	 * Attention au network interface. Pluseiurs possibilités: - Demander à
	 * l'utilisateur de le choisir - utiliser NetworkTools.localhostEth() - utiliser
	 * NetworkTools.localhost(filtreSymble) // example filtreSymble="eth"
	 *
	 * @param remoteAdress
	 */
	private static RmiAddress networkinterfaceChoice(String remote)
		{
		try
			{
			Map<NetworkInterface, InetAddress> mapNetworkAdress = NetworkTools.localhost();
			System.out.println(mapNetworkAdress); // then ask user to choose

			// ou

			// local : localhost
			// InetAddress localAdress = InetAddress.getLocalHost();
			// InetAddress remoteAdress = localAdress;

			// local : true ip
			// InetAddress localAdress = InetAddress.getByName("157.26.103.80");
			// InetAddress remoteAdress = localAdress;

			// distant : true ip
			// String localHost="157.26.100.94";
			//
			// InetAddress localAdress = InetAddress.getByName("157.26.100.94");
			// InetAddress remoteAdress = InetAddress.getByName("157.26.103.80");
			// RmiAddress rmiAdress = new RmiAddress(localAdress, remoteAdress);
			//
			// System.setProperty("java.rmi.server.hostname", localHost); // Patch linux: ip
			// of localhost
			//

			// distant : true ip auto
			InetAddress remoteAdress = InetAddress.getByName(remote);

			InetAddress localAdress = NetworkTools.localhostEth().get(0);
			RmiAddress rmiAdress = new RmiAddress(localAdress, remoteAdress);

			System.setProperty("java.rmi.server.hostname", localAdress.getHostAddress()); // Patch linux: ip of
																							// localhost

			System.out.println(rmiAdress);
			System.out.println(mapNetworkAdress);

			return rmiAdress;

			}
		catch (Exception e)
			{
			e.printStackTrace();
			// ServiceGUI.getInstance().getServices().showError("Failed to launch application!");
			}
		return null;
		}

	public static InetAddress getByName(String host)
		{
		try
			{
			return InetAddress.getByName(host);
			}
		catch (UnknownHostException e)
			{
			e.printStackTrace();
			}
		return null;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Output
	private static RmiAddress rmiAddress;
	private static InetAddress localAddress;
	private static InetAddress remoteAddress;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static Settings instance;

	public static final int PORT_CHAT = RmiTools.PORT_RMI_DEFAUT;
	public static final String ID_CHAT = "CHAT";

	}
