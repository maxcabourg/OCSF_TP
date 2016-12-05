// This file contains material supporting section 3.7 of the textbook:// "Object Oriented Software Engineering" and is issued under the open-source// license found at www.lloseng.com package client;import ocsf.client.*;import common.*;import java.io.*;import java.util.LinkedList;import java.util.List;/** * This class overrides some of the methods defined in the abstract superclass * in order to give more functionality to the client. * * @author Dr Timothy C. Lethbridge * @author Dr Robert Lagani&egrave; * @author Fran&ccedil;ois B&eacute;langer * @version July 2000 */public class ChatClient extends AbstractClient {	// Instance variables **********************************************	/**	 * The interface type variable. It allows the implementation of the display	 * method in the client.	 */	ChatIF clientUI;	private static String QUIT_COMMAND = "#quit";	private static String LOGOFF_COMMAND = "#logoff";	private static String SET_HOST_COMMAND = "#sethost";	private static String SET_PORT_COMMAND = "#setport";	private static String LOGIN_COMMAND = "#login";	private static String GETHOST_COMMAND = "#gethost";	private static String GETPORT_COMMAND = "#getport";	private String[] commandes = { QUIT_COMMAND, LOGOFF_COMMAND,			SET_HOST_COMMAND, SET_PORT_COMMAND, LOGIN_COMMAND,			GETHOST_COMMAND, GETPORT_COMMAND };	// Constructors ****************************************************	/**	 * Constructs an instance of the chat client.	 *	 * @param host	 *            The server to connect to.	 * @param port	 *            The port number to connect on.	 * @param clientUI	 *            The interface type variable.	 */	public ChatClient(String host, int port, ChatIF clientUI)			throws IOException {		super(host, port); // Call the superclass constructor		this.clientUI = clientUI;		openConnection();	}	// Instance methods ************************************************	/**	 * This method handles all data that comes in from the server.	 *	 * @param msg	 *            The message from the server.	 */	public void handleMessageFromServer(Object msg) {		clientUI.display(msg.toString());	}	/**	 * This method handles all data coming from the UI	 *	 * @param message	 *            The message from the UI.	 */	public void handleMessageFromClientUI(String message) {		try {			if(isCommand(message) != null){				if(message.equals(QUIT_COMMAND)){					sendToServer(message);					this.closeConnection();					System.exit(0);								}				else if(message.equals(LOGOFF_COMMAND)){					this.closeConnection();				}				else if(message.equals(GETHOST_COMMAND)){					clientUI.display("Host = "+this.getHost());				}				else if(message.equals(GETPORT_COMMAND)){					clientUI.display("Port = "+this.getPort());				}				else if(message.equals(LOGIN_COMMAND)){					if(!this.isConnected()){						this.openConnection();					}					else						System.out.println("Already connected");				}				else if(message.split(" ")[0].equals(SET_PORT_COMMAND)){					String port = message.split(" ")[1];					if(port.matches("[1-9]^4")){						this.setPort(Integer.parseInt(port));					}					else						System.out.println("Invalid command");								}				else if(message.split(" ")[0].equals(SET_HOST_COMMAND)){					if(!isConnected()){						String host = message.split(" ")[1];						this.setHost(host);					}					else						System.out.println("Invalid host");				}			}			else if(message.equals("42")){				System.out.println("   /\\_/\\  ");			    System.out.println("  / o o \\  ");			    System.out.println(" (   \"   ) ");			    System.out.println("  \\~(*)~/  ");			    System.out.println("   // \\\\  ");			}			else				sendToServer(message);		} catch (IOException e) {			clientUI.display("Could not send message to server.  Terminating client.");			quit();		}	}	/**	 * This method terminates the client.	 */	public void quit() {		try {			closeConnection();		} catch (IOException e) {		}		System.exit(0);	}	private String isCommand(String message) {		if (message.charAt(0) == '#') {			String command = message.split(" ")[0];			for (String element : commandes) {				if (command.equals(element))					return command;			}		}		return null;	}	protected void connectionClosed() {		System.out.println("Connection ended");	}	protected void connectionException(Exception exception) {		System.out.println("Exception thrown by the server");		connectionClosed();	}}// End of ChatClient class