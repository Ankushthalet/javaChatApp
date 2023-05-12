import java.net.*;
import java.io.*;
class Server
{
ServerSocket server;
Socket socket;
BufferedReader br;
PrintWriter out;
//constructor
public Server(){
 try{
 server =new ServerSocket(7777);
 System.out.println("Server is ready to accept connection");
 System.out.println("waiting .....");
 socket=server.accept();
 br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
 out= new PrintWriter(socket.getOutputStream());
 startReading();
 startWriting(); }
 catch(Exception e){ 
    e.printStackTrace(); } }
 public void startReading(){
 // thread for reading
 Runnable r1 =()->{
 System.out.println("reader start..");
 try{
 while(true){
 String mssg=br.readLine();
 if(mssg.equals("exit")){
 System.out.println("client terminate the chat");
 socket.close();
 break;
 }
 System.out.println("client:"+ mssg);
 } }
 catch(Exception e){
 e.printStackTrace();
 System.out.println("--------------connection is closed-----------------"); } };
 new Thread(r1).start(); }
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
 break; }}}
 catch(Exception e){
 e.getStackTrace();
 System.out.println("--------------connection is closed-----------------"); } };
 new Thread(r2).start(); }
 public static void main(String args[]){
 System.out.println("this is server going to start server");
 new Server(); } }
