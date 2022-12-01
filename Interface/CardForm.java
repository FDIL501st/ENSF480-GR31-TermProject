package Interface;

import java.awt.*;
import javax.swing.*;

import Controller.UserController;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.*;
import java.util.regex.*;
import Model.Payment;
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

       
        panel = new JPanel();//panel 
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

   JLabel cvvLabel = new JLabel("cvv");
   cvvLabel.setBounds(10,290,80,20);
  panel.add(cvvLabel);

         emailText = new JTextField(); //text field for username
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

       cvvText = new JTextField(); 
       cvvText.setBounds(100,290,165,20);

        payButton = new JButton("Confirm");
        payButton.addActionListener(new CardForm());
       payButton.setBounds(280,280,80,25);

       homeButton = new JButton("Home");
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
    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Home")){
            cardFrame.dispose();
       }
       String email = emailText.getText().trim();
       String fName = fNameText.getText().trim();
       String lName = lNameText.getText().trim();
       String address = addressText.getText().trim();
       String cardNum = cardText.getText().trim();
       String expiry = expiryText.getText().trim();
       String cvv = cvvText.getText().trim();
       
        if(e.getActionCommand().equals("Confirm")){
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("MM/yy",Locale.ENGLISH);
                formatter.parse(expiry);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null,"Expiry Date is invalid");
            }
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
            else if(!Pattern.matches("\\d{3}",cvv)){ 
                JOptionPane.showMessageDialog(null,"Cvv is invalid");
            }
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
                
                JOptionPane.showMessageDialog(null,"Payment has been completed");
                for(int i=0;i<TicketForm.selectedTickets.size();i++){ //create payment objects
                    PaymentForm.payments.add(new Payment(TicketForm.selectedTickets.get(i)));
                    HomePage.paidTickets.addElement("Ticket " + String.valueOf(PaymentForm.payments.get(i).getTicket().getID()));
                }
                HomePage.selectButton.setVisible(true); //homepage ticket select button is visible
                HomePage.pListTitle.setVisible(true);
                TicketForm.selectedTickets.clear(); //clear tickets
                TicketForm.seatsArr.clear();
                 PaymentForm.tickets.clear();
            }
        }
     
    }
}
