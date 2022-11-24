package Interface;
import java.awt.*;
import javax.swing.*;

import Model.LoginServer;

import java.awt.event.*;
import java.util.regex.*;
public class RegistrationForm extends Form implements ActionListener{
    private static JFrame registerFrame;
    private static JPanel panel;

    private static JTextField userText;
    private static JPasswordField passwordText;
    private static JTextField fNameText;
    private static JTextField lNameText;
    private static JTextField addressText;
    private static JTextField cardText;
    private static JButton registerButton;
    private static JButton homeButton;
    public void run() {
        registerFrame = new JFrame();
        registerFrame.setSize(500,350);
        registerFrame.setTitle("Registration Form");

       
        panel = new JPanel();//panel 
        panel.setLayout(null);
        
       JLabel userLabel = new JLabel("Username"); //label for username
        userLabel.setBounds(10,50,80,25);
       panel.add(userLabel);
    
       JLabel passwordLabel = new JLabel("Password"); //label for password
        passwordLabel.setBounds(10,90,80,25);
       panel.add(passwordLabel);

       JLabel fNameLabel = new JLabel(" First Name"); //label for first name
       fNameLabel.setBounds(10,130,80,25);
      panel.add(fNameLabel);

      JLabel lNameLabel = new JLabel("Last Name"); //label for last name
       lNameLabel.setBounds(10,170,80,25);
      panel.add(lNameLabel);

      JLabel addressLabel = new JLabel("Address"); //label for address
      addressLabel.setBounds(10,210,80,25);
     panel.add(addressLabel);
        
     JLabel cardLabel = new JLabel("Card Number"); //label for cardNumber
     cardLabel.setBounds(10,250,80,25);
    panel.add(cardLabel);

         userText = new JTextField(); //text field for username
        userText.setBounds(100,50,165,25);
        
      passwordText = new JPasswordField(); //text field for password
       passwordText.setBounds(100,90,165,25);

       fNameText = new JTextField(); //text field for first name
       fNameText.setBounds(100,130,165,25);

       lNameText = new JTextField(); //text field for last name
       lNameText.setBounds(100,170,165,25);

       addressText = new JTextField(); //text field for name
       addressText.setBounds(100,210,165,25);
      
       cardText = new JTextField(); //text field for name
       cardText.setBounds(100,250,165,25);
 
        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegistrationForm());
       registerButton.setBounds(280,280,80,25);

       homeButton = new JButton("Home");
       homeButton.addActionListener(new RegistrationForm());
      homeButton.setBounds(10,10,80,25);

        
       panel.add(userText);
       panel.add(passwordText);
       panel.add(fNameText);
       panel.add(lNameText);
       panel.add(addressText);
       panel.add(cardText);

       panel.add(registerButton);
       panel.add(homeButton);
      
      

        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.add(panel);
        registerFrame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Home")){
            registerFrame.dispose();
       }
       String username = userText.getText().trim();
       String password = passwordText.getText().trim();
       String fName = fNameText.getText().trim();
       String lName = lNameText.getText().trim();
       String address = addressText.getText().trim();
       String cardNum = cardText.getText().trim();
    
       LoginServer l = LoginServer.getInstance();
       JFrame popup = new JFrame();
       
        if(e.getActionCommand().equals("Register")){
            popup.setVisible(true);
            popup.toFront();
            popup.setAlwaysOnTop(true);
            if(!Pattern.matches("^\\S*$",username) ){ //don't allow spaces
                JOptionPane.showMessageDialog(null,"Username must not have spaces");
            }
            if(!Pattern.matches("[a-zA-z ']*",fName)){ 
                JOptionPane.showMessageDialog(null,"First name must only contain characters");
            }
            if(!Pattern.matches("[a-zA-z ']*",lName)){ 
                JOptionPane.showMessageDialog(null,"Last name must only contain characters");
            }
            if(!Pattern.matches("\\d{1,5}\\s\\w*\\s?\\w*",address)){ 
                JOptionPane.showMessageDialog(null,"Address is invalid");
            }
            if(!Pattern.matches("\\b[0-9]{4}\\s?[0-9]{4}\\s?[0-9]{4}\\s?[0-9]{4}\\s?\\b",cardNum)){
                JOptionPane.showMessageDialog(null,"Card number is invalid");
            }
            if(username.length()==0){ 
                JOptionPane.showMessageDialog(null,"Username field is empty");
            }
            if(password.length()==0){ 
                JOptionPane.showMessageDialog(null,"Password field is empty");
            }
            if(fName.length()==0){ 
                JOptionPane.showMessageDialog(null,"First Name field is empty");
            }
            if(lName.length()==0){ 
                JOptionPane.showMessageDialog(null,"Last Name field is empty");
            }
            if(address.length()==0){ 
                JOptionPane.showMessageDialog(null,"Address field is empty");
            }
            if(cardNum.length()==0){ 
                JOptionPane.showMessageDialog(null,"Card Number field is empty");
            }
            //int cardNum = Integer.parseInt(cardText.getText());
            else if(l.register(username,password,fName,lName,address,cardText.getText())){
                JOptionPane.showMessageDialog(null,"Registration successful");
            }
            else{
                JOptionPane.showMessageDialog(null,"Username already registered");
            }
        }
        popup.setVisible(false);
    }
}
