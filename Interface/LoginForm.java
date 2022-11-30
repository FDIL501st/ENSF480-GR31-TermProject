package Interface;

import javax.swing.*;

import Controller.UserController;

import java.awt.event.*;
public class LoginForm extends Form implements ActionListener {
   private static JFrame loginFrame;
   private static JPanel panel;
   
   private static JTextField emailText;
   private static JPasswordField passwordText;
   private static JButton loginButton;
   private static JButton homeButton;
  


 
    public void run() {
        loginFrame = new JFrame();
        loginFrame.setSize(500,350);
        loginFrame.setTitle("Login Form");

       
        panel = new JPanel();//panel 
        panel.setLayout(null);
        
       JLabel emailLabel = new JLabel("Email"); //label for email
        emailLabel.setBounds(10,80,80,25);
       panel.add(emailLabel);
    
        JLabel passwordLabel = new JLabel("Password"); //label for password
        passwordLabel.setBounds(10,150,80,25);
       panel.add(passwordLabel);
        
         emailText = new JTextField(); //text field for username
        emailText.setBounds(100,80,165,25);
        
      passwordText = new JPasswordField(); //text field for password
       passwordText.setBounds(100,150,165,25);
      
       loginButton = new JButton("Sign in");
       loginButton.addActionListener(new LoginForm());
        loginButton.setBounds(280,220,80,25);

        homeButton = new JButton("Home");
        homeButton.addActionListener(new LoginForm());
        homeButton.setBounds(10,10,80,25);
     

        
       panel.add(emailLabel);
       panel.add(emailText);
       panel.add(passwordLabel);
       panel.add(passwordText);
       panel.add(loginButton);
      panel.add(homeButton);
      
      

    
        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Home")){
            loginFrame.dispose();
       }
       String email = emailText.getText();
       String password = passwordText.getText();

       if(e.getActionCommand().equals("Sign in")){
           
            if(UserController.validate(email,password)!= null){
                JOptionPane.showMessageDialog(null,"Login successful");
                for(int i=0;i<HomePage.movieList.size();i++){
                    if(HomePage.movieList.get(i).RUAnnouncement() != null){
                        HomePage.movies.addElement(HomePage.movieList.get(i).RUAnnouncement());
                    }
                }
                TicketForm.selectedTickets.clear(); //clear previously selected tickets
                PaymentForm.payments.clear(); //clear previous payments
                HomePage.paidTickets.clear();
                HomePage.pListTitle.setVisible(false); //homepage payment invisible
                HomePage.selectButton.setVisible(false); 
                Form.loginStatus = true;
                loginFrame.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null,"Incorrect username or password");
            }
        }

    }
    
}