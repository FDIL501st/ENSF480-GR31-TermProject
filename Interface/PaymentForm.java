package Interface;
import java.awt.*;
import javax.swing.*;

import Model.Payment;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class PaymentForm extends Form implements ActionListener{
    private static JFrame paymentFrame;
    private static JPanel panel;
    private static JButton confirmButton;
    private static JButton homeButton;
    private static JButton selectButton;
    static DefaultListModel<String> tickets = new DefaultListModel<>();
    private static JList<String> ticketList;
    static ArrayList<Payment> payments = new ArrayList<Payment>();

    
    public void run(){
        paymentFrame = new JFrame();
        paymentFrame.setSize(750,500);
        paymentFrame.setTitle("Payment Form");
        panel = new JPanel();//panel 
        panel.setLayout(null);

        homeButton = new JButton("Home");
        homeButton.addActionListener(new PaymentForm());
        homeButton.setBounds(10,10,80,25);

        confirmButton = new JButton("Confirm Payment");
        confirmButton.addActionListener(new PaymentForm());
        confirmButton.setBounds(450,400,150,25);

        selectButton = new JButton("Select Ticket"); //button to select the movie typed in the textbox
        selectButton.addActionListener(new PaymentForm());
       selectButton.setBounds(100,400,150,25);


        JPanel tListTitle = new JPanel(); 
        tListTitle.setBounds(100,40,150,40);
        tListTitle.setLayout(new BorderLayout());
        JLabel ticketLabel = new JLabel();
        ticketLabel.setText("Tickets");
        ticketLabel.setFont(new Font("Calibri Black", Font.BOLD, 15));
        ticketLabel.setVisible(true);
        tListTitle.add(ticketLabel);

        JPanel ticketPanel = new JPanel(); //panel to contain ticket information
        ticketPanel.setBounds( 80,80,150,300);
        ticketPanel.setLayout(new FlowLayout());
        for(int i=0;i<TicketForm.selectedTickets.size();i++){
            if(!tickets.contains("Ticket " + TicketForm.selectedTickets.get(i).getID())){ //no duplicate tickets
                tickets.addElement("Ticket " + String.valueOf(TicketForm.selectedTickets.get(i).getID()));
            }
        }
        ticketList = new JList<>(tickets); //list of tickets
        ticketPanel.add(ticketList);

        panel.add(homeButton);
        panel.add(confirmButton);
        panel.add(selectButton);
        
        paymentFrame.add(tListTitle);
        paymentFrame.add(ticketPanel);
        paymentFrame.add(panel);
        paymentFrame.setVisible(true);
        
    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Home")){
            paymentFrame.dispose();
       }
       if(e.getActionCommand().equals("Select Ticket")){ //select ticket (show ticket information)
            if(ticketList.getSelectedIndex()!= -1){ //an item on the ticket list is selected
                for(int i=0;i<TicketForm.selectedTickets.size();i++){ //check which ticket from the list is selected
                    if(ticketList.getSelectedValue().equals("Ticket " + String.valueOf(TicketForm.selectedTickets.get(i).getID()))){
                        JLabel info = new JLabel();
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
        if(e.getActionCommand().equals("Confirm Payment")){
            if(Form.loginStatus){ //registered user does not need to add information
                JOptionPane.showMessageDialog(null,"Payment has been completed");
                for(int i=0;i<TicketForm.selectedTickets.size();i++){ //create payment objects
                    payments.add(new Payment(TicketForm.selectedTickets.get(i)));
                    HomePage.paidTickets.addElement("Ticket " + String.valueOf(PaymentForm.payments.get(i).getTicket().getID()));
                }
                HomePage.selectButton.setVisible(true); //homepage ticket select button is visible
                HomePage.pListTitle.setVisible(true);
                TicketForm.selectedTickets.clear(); //clear tickets
                TicketForm.seatsArr.clear();
                tickets.clear();
            }
            else{ //need to get user credit card information
                String cardNum = JOptionPane.showInputDialog("Please Enter Credit Card Information");
                cardNum = cardNum.trim();
                if(!Pattern.matches("\\b[0-9]{4}\\s?[0-9]{4}\\s?[0-9]{4}\\s?[0-9]{4}\\s?\\b",cardNum)){
                    JOptionPane.showMessageDialog(null,"Card number is invalid");
                }
                else{
                    String email = JOptionPane.showInputDialog("Please Enter your email");
                    email = email.trim();
                    if(!Pattern.matches("^(.+)@(.+)$",email) ){
                        JOptionPane.showMessageDialog(null,"Email entered is invalid");
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Payment has been completed");
                        for(int i=0;i<TicketForm.selectedTickets.size();i++){ //create payment objects
                            payments.add(new Payment(TicketForm.selectedTickets.get(i)));
                            HomePage.paidTickets.addElement("Ticket " + String.valueOf(PaymentForm.payments.get(i).getTicket().getID()));
                        }
                        HomePage.selectButton.setVisible(true); //homepage ticket select button is visible
                        HomePage.pListTitle.setVisible(true);
                        TicketForm.selectedTickets.clear(); //clear tickets
                        TicketForm.seatsArr.clear();
                        tickets.clear();
                    }
                }
            }
        }
    }
}
