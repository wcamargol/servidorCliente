package model;

/*
 * REDES DE COMPUTADORES
 * PRAT-4
 * Walter Luiz de Camargo - 434094
 * Itapevi
 * 
 * Programa Cliente de Chat
 * Solicita conexao a um Chat do programa Servidor, envia e recebe mensagens
 */

import model.Serializador;
import model.Mensagem;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class Cliente extends JFrame{
	
	//esecuta programa cliente
	public static void main(String[] args) {
		Cliente cliente = new Cliente();// cria o cliente
		cliente.setVisible(true);//torna visivel
		cliente.esperaMsg();//aguarda multicast do servidor
	}
	
	//declaracao de variaveis
	private static final long serialVersionUID = 1L;
	private final JTextArea areaChat; //exibe as mensagens
	private JTextArea entradaMsg; //gera a saida das mensagens
	private final JButton btConectar; //botao para conectar
	private final JButton btDesconectar; //botao para desconectar
	private final JButton btEnviar; //envia mensagens
	private String nomeUsuario; //nomeUsuario para adicionar a mensagens enviadas
	private DatagramSocket socket; //socket para conectar ao servidor
	//mensagem contem um campo String para mensagem e uma flag desconectar
	//para avisar ao servidor da desconexao
	private Mensagem mensagem = new Mensagem();
	private boolean conectado = true;
        private Serializador sD;
	
	//Construtor Cliente
	public Cliente(){
            				
		super( "CHAT RC" );
		this.sD = new Serializador();
		try {
			//conecta-se ao servidor
			socket = new DatagramSocket();
		}//fim do try
		catch (SocketException e) {
			mostraMsg(e.toString() + "\n");
			e.printStackTrace();
		}//fim do catch
		
		setBounds(100, 100, 400, 460);
		
		//cria btConectar
		btConectar = new JButton( "Conectar");
		
		//cria Conectar para botoes de conexao
		ActionListener Conectar = new Conectar();
		btConectar.addActionListener(Conectar);
		
		//cria btDesconectar
		btDesconectar = new JButton( "Desconectar");
		
		//desativa o botao de desconexao
		btDesconectar.setEnabled(false);
		
		//cria o objeto interno Desconectar para botoes de desconexao
		
		//adiciona JButtons de conetar e desconectar ao buttonPanel
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(btConectar);
		buttonPanel.add(btDesconectar);
		
		areaChat = new JTextArea(); //exibe as mensagens
		areaChat.setEditable( false ); //desativa a edicao
		areaChat.setWrapStyleWord( true ); //configura o estilo de quebra de linha
		areaChat.setLineWrap( true ); //ativa a quebra de linha
		
		//coloca a areaChat no JScrollPane para permitir rolagem
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout( new BorderLayout( 10, 10 ) );
		messagePanel.add( new JScrollPane( areaChat ),
				BorderLayout.CENTER );
		
		entradaMsg = new JTextArea( 4, 20 ); //para inserir novas mensagens
		entradaMsg.setWrapStyleWord( true ); //configura o estilo de quebra de linha
		entradaMsg.setLineWrap( true ); //ativa a quebra de linha
		entradaMsg.setEditable( false ); //desativa a edicao
		
		btEnviar = new JButton( "Enviar"); //criar o botao de enviar
		btEnviar.setEnabled( false ); //desativa o botao de enviar
		btEnviar.addActionListener((ActionEvent event) -> {
                    Mensagem msg =  new Mensagem();
                    msg.setMsg(entradaMsg.getText());
                    enviarMsg(msg);
                    entradaMsg.setText(""); //limpa a area entradaMsg
                } //fim do metodo actionPerformed
                //envia uma nova mensagem quando usuario ativa o btEnviar
                //fim da classe interna actionlistener
                ); //fim da chamada para addActionListener
		
		Box box = new Box(BoxLayout.X_AXIS); //cria uma nova caixa para layout
		box.add(new JScrollPane( entradaMsg )); //adiciona a area de entrada a caixa
		box.add(btEnviar); //adiciona o botao Enviar a caixa
		messagePanel.add(box, BorderLayout.SOUTH); //adiciona a caixa ao painel
		getContentPane().add(buttonPanel, BorderLayout.NORTH); //adiciona o painel de botoes
		getContentPane().add(messagePanel, BorderLayout.CENTER); //adiciona o painel de mensagens
		
		//adiciona WindowListener para desconectar quando o usuario sair
		addWindowListener (
				new WindowAdapter (){
					//desconecta-se do servidor e encerra o aplicativo
                                        @Override
					public void windowClosing (WindowEvent event){
						if (conectado){
							System.exit(0);
							socket.close();
						}						
					} //fim do metodo windowClosing
				} //fim da classe interna windowAdapter
		); //fim da chamada a addWindowListener
	} //fim do construtor Cliente
	
	//Conectar ouve solicitacoes do usuario para conectar ao servidor
	private class Conectar implements ActionListener{
				
		//conecta-se ao servidor e ativa/desativa componentes GUI
                @Override
		public void actionPerformed(ActionEvent event){
						
			
				Mensagem msg =  new Mensagem();				
				//envia mensagem de conexao				
				msg.setMsg("SL01L2");
				enviarMsg(msg);
				conectado = true;
				//limpa a areaChat
				areaChat.setText("");
				//desativa conectar
				btConectar.setEnabled( false );
				//ativa desconectar
				btDesconectar.setEnabled( true );
				//ativa o botao Enviar
				btEnviar.setEnabled( true );
				//ativa a edicao para a area de entrada
				entradaMsg.setEditable( true ); 
				//configura foco para a area de entrada
				entradaMsg.requestFocus(); //configura foco para a area de entrada
			
		} //fim do metodo actionPerformed
	} //fim da classe interna Conectar
	
	//Envia mensgens para o servidor
	private void enviarMsg(Mensagem msg){
		
		
		byte[] data = new byte[1024];
		try {
			//serializa o objeto mensagem
			data = this.sD.serialize(msg);
		}//fim do try
		catch (IOException e) {
			mostraMsg(e.toString() + "\n");
			e.printStackTrace();
		}//fim do catch
		
		//cria o datagrama para enviar
		try {
			DatagramPacket sendPacket = new DatagramPacket( data,
			        data.length, InetAddress.getLocalHost(), 5000 );
			//envia a mensagem efetivamente
			socket.send(sendPacket);			
		}//fim do try
		catch (UnknownHostException e) {
			mostraMsg(e.toString() + "\n");
			e.printStackTrace();
		}//fim do catch
		catch (IOException e) {
			mostraMsg(e.toString() + "\n");
			e.printStackTrace();
		}//fim do catch	
	}//fim do metodo enviarMsg
	
	//Espera por mensagens vindas do servidor		
	public void esperaMsg(){
		while (true){
			// recebe a mensagem e exibe o conteudo
			try{
				byte data[] = new byte[1024]; // configura o pacote
				DatagramPacket receivePacket = new DatagramPacket(
						data, data.length );
				
				if (socket != null){
					//recebe a mensegem do servidor
					socket.receive(receivePacket); // espera o pacote
				}//fim do if
				
				try {
					//deserializa o array de bytes para o objeto mensagem
					mensagem = (Mensagem)this.sD.deserialize(receivePacket.getData());
				}//fim do try
				catch (ClassNotFoundException e) {
					mostraMsg(e.toString() + "\n");
					e.printStackTrace();
				}//fim do catch
				
				//exibe o conteudo da mensagem
				mostraMsg(mensagem.getMsg());
			}// fim do try
		    catch ( IOException ioe ){
		    	mostraMsg( ioe.toString() + "\n" );
		    	ioe.printStackTrace();
		    }//fim do catch
		}// fim do while
	} // fim do metodo esperaMsg
	
	
	
	//Exibe nova mensagem acrescentando a mensagem a JTextArea.  
	private void mostraMsg(final String str){
		SwingUtilities.invokeLater(() -> {
                    //acrescenta nova mensagem
                    areaChat.append("\n" + str);
                } //fim do metodo run
                //fim da classe interna runnable
                );//fim da chamada para SwingUtilities.invokeLater	
	}//fim do metodo mostraMsg	
}//fim da classe Cliente