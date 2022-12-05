package Interface;
import javax.swing.*;

import Controller.TicketController;
import Model.Payment;

import java.awt.event.*;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
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
    private static JTextField expiryText;
    private static JTextField cvvText;
    private static JButton registerButton;
    private static JButton homeButton;
    public void run() {
        registerFrame = new JFrame();
        registerFrame.setSize(600,500);
        registerFrame.setTitle("Registration Form");

       
        panel = new JPanel();//panel 
        panel.setLayout(null);
        
        emailText = new JTextField(); //text field for username
        emailText.setBounds(100,50,165,25);
        fNameText = new JTextField(); //text field for first name
        lNameText = new JTextField(); //text field for last name
        addressText = new JTextField(); //text field for name
        cardText = new JTextField(); //text field for name
        expiryText = new JTextField(); //text field for expiry
        cvvText = new JTextField(); //text field for cvv

       JLabel emailLabel = new JLabel("Email"); //label for email
        emailLabel.setBounds(10,50,80,25);
       panel.add(emailLabel);
    if(PaymentForm.tickets.size()==0){
       JLabel passwordLabel = new JLabel("Password"); //label for password
        passwordLabel.setBounds(10,90,80,25);
       panel.add(passwordLabel);

       passwordText = new JPasswordField(); //text field for password
       passwordText.setBounds(100,90,165,25);
       panel.add(passwordText);

       fNameText.setBounds(100,130,165,25);
       lNameText.setBounds(100,170,165,25);
       addressText.setBounds(100,210,165,25);
       cardText.setBounds(100,250,165,25);
       expiryText.setBounds(100,290,165,20);
       cvvText.setBounds(100,330,165,20);

       registerButton = new JButton("Register");
       registerButton.addActionListener(new RegistrationForm());
      registerButton.setBounds(340,340,100,25);
    }
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

    JLabel expiryLabel = new JLabel("Expiry Date"); //label for expiry date
    expiryLabel.setBounds(10,290,80,25);
   panel.add(expiryLabel);

   JLabel cvvLabel = new JLabel("cvv"); //label for cvv
   cvvLabel.setBounds(10,330,80,25);
  panel.add(cvvLabel);

  if(PaymentForm.tickets.size()>0){
    registerFrame.setTitle("User Payment Form");
    fNameLabel.setBounds(10,90,80,25);
    lNameLabel.setBounds(10,130,80,25);
    addressLabel.setBounds(10,170,80,25);
    cardLabel.setBounds(10,210,80,25);
    expiryLabel.setBounds(10,250,80,25);
    cvvLabel.setBounds(10,290,80,25);

    fNameText.setBounds(100,90,165,25);
    lNameText.setBounds(100,130,165,25);
    addressText.setBounds(100,170,165,25);
    cardText.setBounds(100,210,165,25);
    expiryText.setBounds(100,250,165,20);
    cvvText.setBounds(100,290,165,20);
  
    registerButton = new JButton("Confirm");
    registerButton.addActionListener(new RegistrationForm());
   registerButton.setBounds(280,310,80,25);
  }
  

       homeButton = new JButton("Home");
       homeButton.addActionListener(new RegistrationForm());
      homeButton.setBounds(10,10,80,25);

        
       panel.add(emailText);
       panel.add(fNameText);
       panel.add(lNameText);
       panel.add(addressText);
       panel.add(cardText);
       panel.add(expiryText);
       panel.add(cvvText);

       panel.add(registerButton);
       panel.add(homeButton);
      
        registerFrame.add(panel);
        registerFrame.setVisible(true);
        registerFrame.setLocationRelativeTo(null); //center the page
    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Home")){
            if(PaymentForm.tickets.size()>0){
                registerFrame.dispose();
                TicketForm.seatsArr.clear();
                PaymentForm.tickets.clear();
                ticketFormOpened =false; //indicate that ticketform is closed and a new form can be reopened
            }
            else{
                registerFrame.dispose();
            }
       }
    
        if(e.getActionCommand().equals("Register")|| e.getActionCommand().equals("Confirm")){
            String email = emailText.getText().trim();
            String fName = fNameText.getText().trim();
            String lName = lNameText.getText().trim();
            String address = addressText.getText().trim();
            String cardNum = cardText.getText().trim();
            String expiry = expiryText.getText().trim();
            String cvv = cvvText.getText().trim();
            boolean expired = false; 
            try { //check if expiry date is in correct format and that it is not expired
                SimpleDateFormat formatter = new SimpleDateFormat("MM/yy",Locale.ENGLISH);
                Date d= formatter.parse(expiry);
                if(TimeUnit.DAYS.convert( d.getTime() - new Date().getTime(),TimeUnit.MILLISECONDS)<0 ){ //past expiry date
                    expired = true;
                }

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null,"Expiry Date is invalid");
            }
            if(expired){ //card expired
                JOptionPane.showMessageDialog(null,"Card is expired");
            }
            else if(!Pattern.matches("^(.+)@(.+)$",email) ){ //check for valid email that doesn't contain . or + before "@"" and then "." or "+" after @
                JOptionPane.showMessageDialog(null,"Email is invalid");
            }
            else if(!Pattern.matches("[a-zA-z ']*",fName)){  //valid first name with alphabetic characters or ' only
                JOptionPane.showMessageDialog(null,"First name must only contain characters");
            }
            else if(!Pattern.matches("[a-zA-z ']*",lName)){ //valid last name with alphabetic characters or '
                JOptionPane.showMessageDialog(null,"Last name must only contain characters");
            }
            else if(!Pattern.matches("\\d{1,5}\\s\\w*\\s?\\w*",address)){  
                 //requires 1-5 digits followed by a space and then however many characters followed or not followed by a space then any number of characters
                JOptionPane.showMessageDialog(null,"Address is invalid");
            }
            else if(!Pattern.matches("\\b[0-9]{4}\\s?[0-9]{4}\\s?[0-9]{4}\\s?[0-9]{4}\\s?\\b",cardNum)){
                 //requires 4 sets of 4 digits, with or without a space between each set
                JOptionPane.showMessageDialog(null,"Card number is invalid");
            }
            else if(!Pattern.matches("\\d{3}",cvv)){ 
                //requires 3 digits
                JOptionPane.showMessageDialog(null,"Cvv is invalid");
            }
            //make sure no fields are empty
            else if(email.length()==0){ 
                JOptionPane.showMessageDialog(null,"Email field is empty");
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
            else if(cvv.length()==0){ 
                JOptionPane.showMessageDialog(null,"cvv field is empty");
            }
            else if(e.getActionCommand().equals("Register")){
                String password = passwordText.getText().trim();
                LoginServer lg = LoginServer.getInstance();
                if(password.length()==0){ 
                    JOptionPane.showMessageDialog(null,"Password field is empty");
                }
                else if(lg.register(email,password,fName,lName,address,cardNum)){ //register user 
                    JOptionPane.showMessageDialog(null,"Registration successful");
                    registerFrame.dispose();
                }
                else{ //cannot register using the same email
                    JOptionPane.showMessageDialog(null,"Email already registered");
                }
            }
            else if(e.getActionCommand().equals("Confirm")){
                 //payment successful
                 JOptionPane.showMessageDialog(null,"Payment has been completed.\nReceipt has been sent to: "+email);
                 for(int i=0;i<TicketForm.selectedTickets.size();i++){ 
                     Payment p = new Payment(TicketForm.selectedTickets.get(i));
                     PaymentForm.payments.add(p); //create payment objects
                     HomePage.paidTickets.addElement("Ticket " + String.valueOf(TicketForm.selectedTickets.get(i).getID())); //add paidTickets to home page
                     HomePage.allTickets.add(p.getTicket());
                     TicketController.updateSeat(p.getTicket().getMovie().getMovieName(),p.getTicket().getTime(),p.getTicket().getSeatNum(),0);
                 }
                 HomePage.selectButton.setVisible(true); //homepage ticket select button is visible
                 HomePage.pListTitle.setVisible(true); //homepage paid ticket title is visible
                 TicketForm.selectedTickets.clear(); //clear tickets
                 TicketForm.seatsArr.clear(); //reset seats on ticketform 
                  PaymentForm.tickets.clear(); //clear tickets on paymentform
                  ticketFormOpened =false;
                  registerFrame.dispose();
            }
        }
     
    }
}
