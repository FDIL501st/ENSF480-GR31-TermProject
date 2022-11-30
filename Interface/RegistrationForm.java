package Interface;
import java.awt.*;
import javax.swing.*;

import Controller.UserController;

import java.awt.event.*;
import java.util.regex.*;
public class RegistrationForm extends Form implements ActionListener{
    private static JFrame registerFrame;
    private static JPanel panel;

    private static JTextField emailText;
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
        
       JLabel emailLabel = new JLabel("Email"); //label for email
        emailLabel.setBounds(10,50,80,25);
       panel.add(emailLabel);
    
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

         emailText = new JTextField(); //text field for username
        emailText.setBounds(100,50,165,25);
        
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

        
       panel.add(emailText);
       panel.add(passwordText);
       panel.add(fNameText);
       panel.add(lNameText);
       panel.add(addressText);
       panel.add(cardText);

       panel.add(registerButton);
       panel.add(homeButton);
      
      

       
        registerFrame.add(panel);
        registerFrame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Home")){
            registerFrame.dispose();
       }
       String email = emailText.getText().trim();
       String password = passwordText.getText().trim();
       String fName = fNameText.getText().trim();
       String lName = lNameText.getText().trim();
       String address = addressText.getText().trim();
       String cardNum = cardText.getText().trim();
    
       
        if(e.getActionCommand().equals("Register")){
          
            if(!Pattern.matches("^(.+)@(.+)$",email) ){ //check for valid email 
                JOptionPane.showMessageDialog(null,"Email is invalid");
            }
            else if(!Pattern.matches("[a-zA-z ']*",fName)){ 
                JOptionPane.showMessageDialog(null,"First name must only contain characters");
            }
            else if(!Pattern.matches("[a-zA-z ']*",lName)){ 
                JOptionPane.showMessageDialog(null,"Last name must only contain characters");
            }
            else if(!Pattern.matches("\\d{1,5}\\s\\w*\\s?\\w*",address)){ 
                JOptionPane.showMessageDialog(null,"Address is invalid");
            }
            else if(!Pattern.matches("\\b[0-9]{4}\\s?[0-9]{4}\\s?[0-9]{4}\\s?[0-9]{4}\\s?\\b",cardNum)){
                JOptionPane.showMessageDialog(null,"Card number is invalid");
            }
            else if(email.length()==0){ 
                JOptionPane.showMessageDialog(null,"Email field is empty");
            }
            else if(password.length()==0){ 
                JOptionPane.showMessageDialog(null,"Password field is empty");
            }
            else if(fName.length()==0){ 
                JOptionPane.showMessageDialog(null,"First Name field is empty");
            }
            else if(lName.length()==0){ 
                JOptionPane.showMessageDialog(null,"Last Name field is empty");
            }
            else if(address.length()==0){ 
                JOptionPane.showMessageDialog(null,"Address field is empty");
            }
            else if(cardNum.length()==0){ 
                JOptionPane.showMessageDialog(null,"Card Number field is empty");
            }
            else if(UserController.register(email,password,fName,lName,address,cardNum)){
                JOptionPane.showMessageDialog(null,"Registration successful");
            }
            else{
                JOptionPane.showMessageDialog(null,"Email already registered");
            }
        }
     
    }
}
