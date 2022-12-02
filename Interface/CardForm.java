package Interface;

import java.awt.*;
import javax.swing.*;
import java.util.Date;
import Controller.UserController;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.*;
import java.util.regex.*;
import java.util.concurrent.TimeUnit;
import Model.Payment;
/*Payment form for unregistered users who require to enter additional information */
public class CardForm extends Form implements ActionListener{
    private static JFrame cardFrame;
    private static JPanel panel;

    private static JTextField emailText;
    private static JTextField fNameText;
    private static JTextField lNameText;
    private static JTextField addressText;
    private static JTextField cardText;
    private static JTextField expiryText;
    private static JTextField cvvText;
    private static JButton payButton;
    private static JButton homeButton;
    public void run() {
        
        cardFrame = new JFrame();
        cardFrame.setSize(500,350);
        cardFrame.setTitle("Card Form");

       
        panel = new JPanel();//main panel 
        panel.setLayout(null);
        
       JLabel emailLabel = new JLabel("Email"); //label for email
        emailLabel.setBounds(10,50,80,20);
       panel.add(emailLabel);


       JLabel fNameLabel = new JLabel(" First Name"); //label for first name
       fNameLabel.setBounds(10,90,80,20);
      panel.add(fNameLabel);

      JLabel lNameLabel = new JLabel("Last Name"); //label for last name
       lNameLabel.setBounds(10,130,80,20);
      panel.add(lNameLabel);

      JLabel addressLabel = new JLabel("Address"); //label for address
      addressLabel.setBounds(10,170,80,20);
     panel.add(addressLabel);
        
     JLabel cardLabel = new JLabel("Card Number"); //label for cardNumber
     cardLabel.setBounds(10,210,80,20);
    panel.add(cardLabel);

    JLabel expiryLabel = new JLabel("Expiry Date"); //label for expiry date
    expiryLabel.setBounds(10,250,80,20);
   panel.add(expiryLabel);

   JLabel cvvLabel = new JLabel("cvv"); //label for cvv
   cvvLabel.setBounds(10,290,80,20);
  panel.add(cvvLabel);

         emailText = new JTextField(); //text field for email
        emailText.setBounds(100,50,165,20);
        

       fNameText = new JTextField(); //text field for first name
       fNameText.setBounds(100,90,165,20);

       lNameText = new JTextField(); //text field for last name
       lNameText.setBounds(100,130,165,20);

       addressText = new JTextField(); //text field for address
       addressText.setBounds(100,170,165,20);
      
       cardText = new JTextField(); //text field for card
       cardText.setBounds(100,210,165,20);

       expiryText = new JTextField(); //text field for expiry
       expiryText.setBounds(100,250,165,20);

       cvvText = new JTextField(); //text field for cvv
       cvvText.setBounds(100,290,165,20);

        payButton = new JButton("Confirm"); //button used to confirm payment
        payButton.addActionListener(new CardForm());
       payButton.setBounds(280,280,80,25);

       homeButton = new JButton("Home"); //button to return to previously opened form
       homeButton.addActionListener(new CardForm());
      homeButton.setBounds(10,10,80,25);

        
       panel.add(emailText);
       panel.add(fNameText);
       panel.add(lNameText);
       panel.add(addressText);
       panel.add(cardText);
       panel.add(expiryText);
       panel.add(cvvText);

       panel.add(payButton);
       panel.add(homeButton);
      
      

       
        cardFrame.add(panel);
        cardFrame.setVisible(true);
        cardFrame.setLocationRelativeTo(null); //center the page
    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Home")){ //dispose form and return to home page
            cardFrame.dispose();
            TicketForm.seatsArr.clear();
            PaymentForm.tickets.clear();
            ticketFormOpened =false; //indicate that ticketform is closed and a new form can be reopened
       }
       //trim all user inputs
       String email = emailText.getText().trim();
       String fName = fNameText.getText().trim();
       String lName = lNameText.getText().trim();
       String address = addressText.getText().trim();
       String cardNum = cardText.getText().trim();
       String expiry = expiryText.getText().trim();
       String cvv = cvvText.getText().trim();
       
        if(e.getActionCommand().equals("Confirm")){
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
            else if(!Pattern.matches("[a-zA-z ']*",fName)){ //needs to contain characters without spaces
                JOptionPane.showMessageDialog(null,"First name must only contain characters");
            }
            else if(!Pattern.matches("[a-zA-z ']*",lName)){ //needs to contain characters without spaces
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
            /* Check to make sure all entries are filled */
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
            else if(expiry.length()==0){ 
                JOptionPane.showMessageDialog(null,"Expiry Date field is empty");
            }
            else if(cvv.length()==0){ 
                JOptionPane.showMessageDialog(null,"cvv field is empty");
            }
            else{
                //payment successful
                JOptionPane.showMessageDialog(null, "Payment has been completed.\nReceipt has been sent to: " + email);
                for(int i=0;i<TicketForm.selectedTickets.size();i++){ 
                    PaymentForm.payments.add(new Payment(TicketForm.selectedTickets.get(i))); //create payment objects
                    HomePage.paidTickets.addElement("Ticket " + String.valueOf(PaymentForm.payments.get(i).getTicket().getID())); //add paidTickets to home page
                }
                HomePage.selectButton.setVisible(true); //homepage ticket select button is visible
                HomePage.pListTitle.setVisible(true); //homepage paid ticket title is visible
                TicketForm.selectedTickets.clear(); //clear tickets
                TicketForm.seatsArr.clear(); //reset seats on ticketform 
                 PaymentForm.tickets.clear(); //clear tickets on paymentform
                 ticketFormOpened =false;
                 cardFrame.dispose();
            }
        }
     
    }
}
