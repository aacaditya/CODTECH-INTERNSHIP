/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatappserver;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javazoom.jl.player.Player;

/**
 *
 * @author Aditya
 */
public class Server 
{
    private JFrame serverframe; 
    private JTextArea ta ;
    private JScrollPane scrollpane ;
    private JTextField tf;
    
    private ServerSocket serversocket;
    
    private InetAddress inet_address;
    
    private DataInputStream dis;
    private DataOutputStream dos;
//    ------------------------------------------------Thread creation-------------------------------------------------------------------------
     Thread thread = new Thread(){
        public void run()
        {
            while(true)
            {
                readMessage();
            }
        }
    };
//    ----------------------------------------------------------------------------------------------------------------------------------------------
   
    Socket socket;
    Server()
    {
      serverframe  = new JFrame("Server"); 
      serverframe.setSize( 500, 500);
      serverframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      ta = new JTextArea();
      ta.setEditable(false);
       Font font = new Font("Arial", 1, 16);
       ta.setFont(font);
      scrollpane = new JScrollPane(ta);
      serverframe.add(scrollpane);
      
      tf = new JTextField();
      tf.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
              sendMessage(tf.getText());
              ta.append(tf.getText()+"\n");
              tf.setText("");
          }
      });
      tf.setEditable(false);
      serverframe.add(tf,BorderLayout.SOUTH);
      
      serverframe.setLocationRelativeTo(null);
      serverframe.setVisible(true);
    }
    
    public void waitingForClient()
    {
        try 
        {
            String ipaddress =getIpAddress();
            serversocket = new ServerSocket(1111);
            ta.setText("To connect with server please provide Ip Address : "+ipaddress);
            socket =serversocket.accept();
            ta.setText("Client connected \n");
            ta.append("-----------------------------------------------\n");
            tf.setEditable(true);
        }
        catch(Exception e)
        {
            System.out.println("e");
        }
        
    }
    public String getIpAddress()
    {
        String ip_address ="";
        try 
        {
            inet_address =InetAddress.getLocalHost();
            ip_address =inet_address.getHostAddress();
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return ip_address;
        
    }
 void setIOStream()
{
    
    try
    {
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
     thread.start();
}
   
 public void sendMessage(String message)
{
    try
    {
        dos.writeUTF(message);
        dos.flush();
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
    
}
 
 public void readMessage()
{
    try
    {
        String message = dis.readUTF();
        ShowMessage("Client : "+message);
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
}
 
public void ShowMessage(String message)
{
    ta.append(message+"\n");
   chatSound();
}

public void chatSound()
{
    try
    {
        FileInputStream fis = new FileInputStream("E:\\Neatbeans java\\ChatApplication\\src\\sound\\chat_sound.mp3");
        Player p = new Player(fis);
        p.play();
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
}
}
