import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Client extends JFrame implements ActionListener{
Socket socket;
BufferedReader br;
PrintWriter out;
//declare components
private JLabel heading =new JLabel("client Area");
private JTextArea messageArea =new JTextArea();
private JTextField messageInput =new JTextField();
private Font font=new Font("Roboto",Font.PLAIN,15);
//constructor
public Client(){
 try{
 System.out.println("sending request to server");
 socket =new Socket("127.0.0.1",7777);
 System.out.println("connection done");
 br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
 out= new PrintWriter(socket.getOutputStream());
 createGUI();
 handleEvents();
 startReading();
 // startWriting(); 
   }
 catch(Exception e){
 //handle exception }}
 private void handleEvents(){
 messageInput.addKeyListener(new KeyListener(){
 @Override
 public void keyTyped(KeyEvent e) {

 }
 @Override
 public void keyPressed(KeyEvent e) {

 }
 @Override
 public void keyReleased(KeyEvent e) {

 // System.out.println("key released" + e.getKeyCode());
 if(e.getKeyCode()==10){
 String contentToSend=messageInput.getText();
 messageArea.append("Me:"+contentToSend+ "\n");
 out.println(contentToSend);
 out.flush();
 messageInput.setText("");
 messageInput.requestFocus();
 } } });

 }
 private void createGUI(){
 this.setTitle("Client Message[end]");
 this.setSize(600,600);
 this.setLocationRelativeTo(null);
 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 //coding for component
 heading.setFont(font);
 messageArea.setFont(font);
 messageInput.setFont(font);
 heading.setIcon(new ImageIcon("img2.jpeg"));
 heading.setHorizontalTextPosition(SwingConstants.CENTER);
heading.setVerticalTextPosition(SwingConstants.BOTTOM); 
   heading.setHorizontalAlignment(SwingConstants.CENTER);
heading.setBorder(BorderFactory.createEmptyBorder(01,01,01,01));
messageArea.setEditable(false);
this.setLayout(new BorderLayout());
// adding component to frame
this.add(heading,BorderLayout.NORTH);
JScrollPane jScrollPane=new JScrollPane(messageArea);
this.add(jScrollPane,BorderLayout.CENTER);
this.add(messageInput,BorderLayout.SOUTH);
 this.setVisible(true); }
public void startReading(){
 // thread for reading
 Runnable r1 =()->{
 System.out.println("reader start..");
 try{
 while(true){

 String mssg=br.readLine();
 if(mssg.equals("exit")){
 System.out.println("Server terminated the chat");
 // JOptionPane.showMessageDialog(this, "server terminated the chat");
 // messageInput.setEnabled(false);
 socket.close();
 break;
 }
 //System.out.println("server:"+ mssg);
 messageArea.append("Server:" +mssg+ "\n");
 }
 }catch(Exception e){
 e.printStackTrace();
 System.out.println("--------------connection is closed-----------------");
 }
 };

 new Thread(r1).start();
 }
   public void startWriting(){
 // thread - take data from user and send it to client
 Runnable r2 =()->{
 System.out.println("writer started..");
 try{
 while(!socket.isClosed()){



 BufferedReader br1 =new BufferedReader(new InputStreamReader(System.in));
 String content = br1.readLine();
 out.println(content);
 out.flush();

 if(content.equals("exit")){
 socket.close();
 break;
 }


 }

 }catch(Exception e){
 e.printStackTrace();
 System.out.println("--------------connection is closed-----------------");
 }

 };
 new Thread(r2).start();
 }

 public static void main(String args[]) {

 System.out.println("this is client....");
 new Client();
 }
 }
