package Interface;

import javax.swing.*;

import Model.RegisteredUser;

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

       
        panel = new JPanel();//main panel 
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
        loginFrame.setLocationRelativeTo(null); //center the page
    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Home")){ //close form and return to home page
            loginFrame.dispose();
       }
       String email = emailText.getText();
       String password = passwordText.getText();

       if(e.getActionCommand().equals("Sign in")){
        LoginServer lg = LoginServer.getInstance();
            if(lg.validate(email,password)!= null){ //check if email and password are in database
                JOptionPane.showMessageDialog(null,"Login successful");
                HomePage.movies.clear();
                for(int i=0;i<HomePage.movieList.size();i++){
                    if(HomePage.movieList.get(i).RUAnnouncement() != null){
                        HomePage.movies.addElement(HomePage.movieList.get(i).RUAnnouncement()); //add registered user announcements
                    }
                }
                TicketForm.selectedTickets.clear(); //clear previously selected tickets
                PaymentForm.payments.clear(); //clear previous payments
                HomePage.paidTickets.clear();
                HomePage.pListTitle.setVisible(false); //homepage payment invisible
                HomePage.selectButton.setVisible(false); 
                PaymentForm.tickets.clear(); //clear any unpaid tickets in paymentform
               
                HomePage.currentUser = lg.validate(email,password); //set current user
                RegisteredUser ru = (RegisteredUser) (HomePage.currentUser);
                if(ru.checkAnnualFee()){ //check if annual fee has been paid or not
                    HomePage.annualPayment.setVisible(true); //annual payment button on home page is visible
                }
                if(HomePage.currentUser.getPaidTickets().size()>0){ //check for any paid tickets on users account
                    for(int i=0;i<HomePage.currentUser.getPaidTickets().size();i++){
                        PaymentForm.payments.add(HomePage.currentUser.getPaidTickets().get(i));
                        HomePage.paidTickets.addElement("Ticket " + String.valueOf(PaymentForm.payments.get(i).getTicket().getID()));
                    }
                    HomePage.selectButton.setVisible(true); //homepage ticket select button is visible
                    HomePage.cancelButton.setVisible(false);
                    HomePage.pListTitle.setVisible(true);
                }
                HomePage.logoutButton.setVisible(true); //logout button on home page is visible
                loginFrame.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null,"Incorrect username or password");
            }
        }

    }
    
}