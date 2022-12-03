package Interface;
import java.awt.*;
import javax.swing.*;

import Controller.TicketController;
import Model.Payment;
import Model.RegisteredUser;
import java.awt.event.*;
import java.util.Date;
import java.util.ArrayList;

public class PaymentForm extends Form implements ActionListener{
    private static JFrame paymentFrame;
    private static JPanel panel;
    private static JButton confirmButton;
    private static JButton homeButton;
    private static JButton selectButton;
    private static JTextField summText;
    private static JButton codeButton;
    private static JTextField codeText;
    static DefaultListModel<String> tickets = new DefaultListModel<>();
    private static JList<String> ticketList;
    static ArrayList<Payment> payments = new ArrayList<Payment>();
    static TicketController tc = new TicketController();

    
    public void run(){
        paymentFrame = new JFrame();
        paymentFrame.setSize(850,700);
        paymentFrame.setTitle("Payment Form");
        panel = new JPanel();//panel 
        panel.setLayout(null);

        homeButton = new JButton("Home"); 
        homeButton.addActionListener(new PaymentForm());
        homeButton.setBounds(10,10,80,25);

        confirmButton = new JButton("Confirm Payment");
        confirmButton.addActionListener(new PaymentForm());
        confirmButton.setBounds(450,600,150,25);

        selectButton = new JButton("Select Ticket"); //button to select the movie typed in the textbox
        selectButton.addActionListener(new PaymentForm());
       selectButton.setBounds(100,600,150,25);

       codeButton = new JButton("Apply Code"); //button to apply code
       codeButton.addActionListener(new PaymentForm());
        codeButton.setBounds(650,600,150,25);
        if(HomePage.currentUser.getRegistrationStatus()){
            codeButton.setVisible(false);
        }

        JPanel tListTitle = new JPanel(); 
        tListTitle.setBounds(100,40,150,40);
        tListTitle.setLayout(new BorderLayout());
        JLabel ticketLabel = new JLabel();
        ticketLabel.setText("Tickets");
        ticketLabel.setFont(new Font("Calibri Black", Font.BOLD, 15));
        ticketLabel.setVisible(true);
        tListTitle.add(ticketLabel);

        JPanel ticketPanel = new JPanel(); //panel to contain ticket information
        ticketPanel.setBounds( 80,80,150,400);
        ticketPanel.setLayout(new FlowLayout());
        for(int i=0;i<TicketForm.selectedTickets.size();i++){
            if(!tickets.contains("Ticket " + TicketForm.selectedTickets.get(i).getID())){ //no duplicate tickets
                tickets.addElement("Ticket " + String.valueOf(TicketForm.selectedTickets.get(i).getID()));
            }
        }
        ticketList = new JList<>(tickets); //list of tickets
        ticketList.setFont(new Font("Arial",Font.PLAIN,8));
        ticketPanel.add(ticketList); //display list of tickets

        JPanel summTitle = new JPanel(); 
        summTitle.setBounds(300,40,150,40);
        summTitle.setLayout(new BorderLayout());
        JLabel summLabel = new JLabel();
        summLabel.setText("Receipt");
        summLabel.setFont(new Font("Calibri Black", Font.BOLD, 15));
        summLabel.setVisible(true);
        summTitle.add(summLabel);

         summText = new JTextField(); //show the price of the tickets
        summText.setBounds(300, 80, 200, 40);
        if (tickets.size() == 0 ) {
            RegisteredUser ru = (RegisteredUser) (HomePage.currentUser);
            if(ru.checkAnnualFee()){
                summText.setText("Annual Payment x1: $20.00");
            }
        }
        else {
            summText.setText("Tickets x" + tickets.size() + ": $" + 100*tickets.size() + ".00");
        }
        summText.setEditable(false); 
        if(!HomePage.currentUser.getRegistrationStatus()){
            JLabel codeLabel = new JLabel("Code"); //label for email
            codeLabel.setBounds(600,40,80,25);
            codeLabel.setFont(new Font("Arial",Font.BOLD,14));
            panel.add(codeLabel);
            codeText = new JTextField();
            codeText.setBounds(600,80,165,25);
            paymentFrame.add(codeText);
        }
        panel.add(homeButton);
        panel.add(confirmButton);
        panel.add(selectButton);
        panel.add(codeButton);
        
        paymentFrame.add(tListTitle);
        paymentFrame.add(ticketPanel);
        paymentFrame.add(summTitle);
        paymentFrame.add(summText);
        paymentFrame.add(panel);
        paymentFrame.setVisible(true);
        paymentFrame.setLocationRelativeTo(null); //center the page
        
    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Home")){
            TicketForm.selectedTickets.clear(); //clear all tickets
            tickets.clear(); 
            TicketForm.seatsArr.clear(); //clear any selected seats
            paymentFrame.dispose(); 
            ticketFormOpened = false; //indicate that ticket form can be reopened
       }
       if(e.getActionCommand().equals("Select Ticket")){ //select ticket (show ticket information)
            if (tickets.size() == 0) {
                JOptionPane.showMessageDialog(null, "Unable to select tickets.\nThis page is being used for annual payments.");
            }
            if(ticketList.getSelectedIndex()!= -1){ //an item on the ticket list is selected
                for(int i=0;i<TicketForm.selectedTickets.size();i++){ //check which ticket from the list is selected
                    if(ticketList.getSelectedValue().equals("Ticket " + String.valueOf(TicketForm.selectedTickets.get(i).getID()))){
                        JLabel info = new JLabel(); //display info for tickets
                        info.setText("<html>Ticket ID: " + ticketList.getSelectedValue() +
                        "<br>Movie Name: "+TicketForm.selectedTickets.get(i).getMovie().getMovieName() +
                        "<br>Time: "+ TicketForm.selectedTickets.get(i).getTime().toString() +
                        "<br>Seat Number: " + TicketForm.selectedTickets.get(i).getSeatNum() +
                        "<br>Room: " + TicketForm.selectedTickets.get(i).getRoomNum() + "</html>");
                        JOptionPane.showMessageDialog(null,info);
                    }   
                }
            }
        }
        if(e.getActionCommand().equals("Apply Code")){
            if(codeText.getText().trim().length()>0){
                try{
                    double value = TicketController.getCodeValue(Integer.parseInt(codeText.getText().trim()));
                    if(value != 0){
                        JOptionPane.showMessageDialog(null,"Code is applied");
                        if((100*tickets.size()-value)>0){
                            summText.setText("Tickets x" + tickets.size() + ": $" + String.valueOf(100*tickets.size()-value));
                        }
                        else{
                            summText.setText("Tickets x" + tickets.size() + ": $" + 0 + ".00");
                        }
                    
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Code is invalid");
                    }
                }
                catch(Exception e1){
                    JOptionPane.showMessageDialog(null,"Code is invalid");
                }
            }
        }
        if(e.getActionCommand().equals("Confirm Payment")){
            if(HomePage.currentUser.getRegistrationStatus()){ //registered user does not need to add information
                if (tickets.size() == 0) {
                    JOptionPane.showMessageDialog(null,"Annual payment has been completed\nReceipt has been sent to your email");
                }
                else {
                    JOptionPane.showMessageDialog(null,"Ticket payment has been completed\nReceipt and tickets have been sent to your email");
                }
                for(int i=0;i<TicketForm.selectedTickets.size();i++){ //create payment objects
                    Payment p = new Payment(TicketForm.selectedTickets.get(i));
                    payments.add(p);
                    HomePage.paidTickets.addElement("Ticket " + String.valueOf(TicketForm.selectedTickets.get(i).getID()));
                    tc.add(p.getTicket());
                }
                HomePage.currentUser.updatePayments(payments); //add paid tickets to user 
                HomePage.selectButton.setVisible(true); //homepage ticket select button is visible
                HomePage.pListTitle.setVisible(true);
                TicketForm.selectedTickets.clear(); //clear tickets
                TicketForm.seatsArr.clear();
                tickets.clear();
                summText.setVisible(false); //price display is cleared
                paymentFrame.dispose();
                ticketFormOpened =false;
                if(tickets.size()==0){
                    RegisteredUser ru = (RegisteredUser) (HomePage.currentUser);
                    ru.setDateLastPayed(new Date());
                    HomePage.annualPayment.setVisible(false);
                }
            }
            else{ //need to get user additional information
                Form f = new RegistrationForm();
                paymentFrame.dispose();
                f.run();     
            }
        }
    }
}
