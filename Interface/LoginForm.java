package Interface;
import java.awt.*;
import javax.swing.*;

import Model.LoginServer;

import java.awt.event.*;
public class LoginForm extends Form implements ActionListener {
   private static JFrame loginFrame;
   private static JPanel panel;
   
   private static JTextField userText;
   private static JPasswordField passwordText;
   private static JButton loginButton;
   private static JButton homeButton;


 
    public void run() {
        loginFrame = new JFrame();
        loginFrame.setSize(500,350);
        loginFrame.setTitle("Login Form");

       
        panel = new JPanel();//panel 
        panel.setLayout(null);
        
       JLabel userLabel = new JLabel("Email"); //label for username
        userLabel.setBounds(10,80,80,25);
       panel.add(userLabel);
    
        JLabel passwordLabel = new JLabel("Password"); //label for password
        passwordLabel.setBounds(10,150,80,25);
       panel.add(passwordLabel);
        
         userText = new JTextField(); //text field for username
        userText.setBounds(100,80,165,25);
        
      passwordText = new JPasswordField(); //text field for password
       passwordText.setBounds(100,150,165,25);
      
       loginButton = new JButton("Sign in");
       loginButton.addActionListener(new LoginForm());
        loginButton.setBounds(280,220,80,25);

        homeButton = new JButton("Home");
        homeButton.addActionListener(new LoginForm());
        homeButton.setBounds(10,10,80,25);
     

        
       panel.add(userLabel);
       panel.add(userText);
       panel.add(passwordLabel);
       panel.add(passwordText);
       panel.add(loginButton);
      panel.add(homeButton);
      
      

        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Home")){
            loginFrame.dispose();
       }
       String username = userText.getText();
       String password = passwordText.getText();
       LoginServer l = LoginServer.getInstance();
       JFrame popup = new JFrame();

       if(e.getActionCommand().equals("Sign in")){
            popup.setVisible(true);
            popup.toFront();
            popup.setAlwaysOnTop(true);
            if(l.checkDuplicate(username,password)!=null){
                JOptionPane.showMessageDialog(null,"Login successful");
            }
            else{
                JOptionPane.showMessageDialog(null,"Incorrect username or password");
            }
        }
        popup.setVisible(false);
    }
    
}