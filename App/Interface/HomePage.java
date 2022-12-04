package Interface;
import java.awt.*;
import javax.swing.*;

import Controller.MovieController;
import Controller.TicketController;
import Controller.UserController;

import java.awt.event.*;
import java.text.ParseException;

import Model.Movie;
import Model.Payment;
import Model.RegisteredUser;
import Model.Ticket;
import Model.User;

import java.util.ArrayList;

public class HomePage implements ActionListener{
    protected Form form;
    static User currentUser = new User(); //current user that is logged in
    private static JFrame homeFrame;
    private static JPanel mainPanel;
    private static JButton loginButton;
    static JButton logoutButton;
    private static JButton registerButton;
    private static JButton movieSelect;
    static JButton annualPayment;
    static JButton cancelButton;
    static JButton cancelAllButton;
    static JButton selectButton;
    static DefaultListModel<String> movies = new DefaultListModel<>(); //list of movie names to announce
    public static ArrayList<Movie> movieList = MovieController.getAllMovies(); //list of all movies
   
    static DefaultListModel<String> paidTickets = new DefaultListModel<>(); //list of tickets that are paid for
    private static JList<String> paidList; //display for list of tickets that are paid for
    public static ArrayList<Ticket> allTickets = new ArrayList<Ticket>();
   static JPanel pListTitle;
   JList<String> announcements;
   private static Payment paymentSelected; //payment to be cancelled
    public void start() throws ParseException{
        homeFrame = new JFrame();
        homeFrame.setSize(850,700);
        homeFrame.setTitle("Home Page");

       JPanel title = new JPanel(); //panel for home page title
       title.setBounds(20,20,250,40);
       title.setLayout(new BorderLayout());
       JLabel label = new JLabel();
       label.setText("Movie Reservation App");
       label.setVisible(true);
       label.setFont(new Font("Calibri Black", Font.BOLD, 20));
       title.add(label);
        mainPanel = new JPanel();//panel 
        mainPanel.setLayout(null);
   

        loginButton = new JButton("Login"); //button to open login form
       loginButton.addActionListener(new HomePage());
        loginButton.setBounds(520,10,80,25);

        registerButton = new JButton("Register"); //button to open register form
        registerButton.addActionListener(new HomePage());
        registerButton.setBounds(420,10,100,25);

        logoutButton = new JButton("Logout"); //button to logout
        logoutButton.addActionListener(new HomePage());
        logoutButton.setBounds(600, 10, 80, 25);
        
        annualPayment = new JButton("Annual Payment"); //button to open payment form for annual payment
        annualPayment.addActionListener(new HomePage());
        annualPayment.setBounds(280, 10, 140, 25);

        movieSelect = new JButton("Purchase Ticket"); //button to purchase/view tickets
        movieSelect.addActionListener(new HomePage());
        movieSelect.setBounds(20,90,150,25);
        
        JPanel announcementTitle = new JPanel(); 
        announcementTitle.setBounds(510,40,100,40);
        announcementTitle.setLayout(new BorderLayout());
        JLabel announcementLabel = new JLabel();
        announcementLabel.setText("New Movies");
        announcementLabel.setFont(new Font("Calibri Black", Font.BOLD, 15));
        announcementLabel.setVisible(true);
        announcementTitle.add(announcementLabel);
        
        JPanel announcementPanel = new JPanel(); //panel for movie announcements
        announcementPanel.setBounds(425,80,250,400);
        announcementPanel.setLayout(new FlowLayout());
        
        for(int i=0;i<movieList.size();i++){ //check which movies should be announced
            if(movieList.get(i).regularAnnouncement()!= null){
                movies.addElement(movieList.get(i).regularAnnouncement()); //add movies to be announced to list
            }
        }
        announcements = new JList<>(movies); //display list of movies to be announced

        pListTitle = new JPanel(); 
        pListTitle.setBounds(100,120,150,40);
        pListTitle.setLayout(new BorderLayout());
        JLabel paidLabel = new JLabel(); 
        paidLabel.setText("Paid Tickets");
        paidLabel.setFont(new Font("Calibri Black", Font.BOLD, 15));
        paidLabel.setVisible(true);
        pListTitle.add(paidLabel);
        pListTitle.setVisible(false);

        JPanel ticketPanel = new JPanel(); //panel to contain paid ticket information
        ticketPanel.setBounds( 80,150,150,400);
        ticketPanel.setLayout(new FlowLayout());
        paidList = new JList<>(paidTickets); //list of paid tickets
        paidList.setFont(new Font("Arial",Font.PLAIN,8));
        ticketPanel.add(paidList);

        selectButton = new JButton("Select Ticket"); //button to select a ticket from paid tickets list
        selectButton.addActionListener(new HomePage());
       selectButton.setBounds(100,600,150,25);
       selectButton.setVisible(false); //set invisible to begin
        
       cancelButton = new JButton("Cancel Ticket"); //button to cancel the selected ticket 
       cancelButton.addActionListener(new HomePage());
       cancelButton.setBounds(350,600,150,25);
       cancelButton.setVisible(false); //set invisible to begin

       cancelAllButton = new JButton("Cancel All Tickets"); //button to cancel all tickets
       cancelAllButton.addActionListener(new HomePage());
       cancelAllButton.setBounds(550,600,150,25);
       cancelAllButton.setVisible(false); //set invisible to begin

        mainPanel.add(title);
        mainPanel.add(announcementTitle);
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);
        mainPanel.add(logoutButton);
        mainPanel.add(movieSelect);
        mainPanel.add(selectButton);
        mainPanel.add(cancelButton);
        mainPanel.add(cancelAllButton);
        mainPanel.add(annualPayment);
        announcementPanel.add(announcements);

        if (currentUser.getRegistrationStatus()== false) { //if user is not logged in then annual payments button will be invisible
            annualPayment.setVisible(false);
            logoutButton.setVisible(false);
        }
  
        homeFrame.add(announcementPanel);
         homeFrame.add(ticketPanel);
         homeFrame.add(pListTitle);
         homeFrame.add(mainPanel);
         homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         homeFrame.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                TicketController.setAllTickets(HomePage.allTickets);
            }
         });
         homeFrame.setVisible(true);
         homeFrame.setLocationRelativeTo(null); //center the page
         
    }
    public void setForm(Form f){ //set which form will be opened
        form = f;
    }
    public void actionPerformed(ActionEvent e){
       
        if(e.getActionCommand().equals("Login")){ 
                this.setForm(new LoginForm()); //set form as login form
                form.run(); //run form
        }
        if(e.getActionCommand().equals("Register")){
                this.setForm(new RegistrationForm()); //set form as register form
                form.run();  //run form
        }
        if (e.getActionCommand().equals("Logout")) {
            if (currentUser.getRegistrationStatus() == true) { //make sure user is logged in
                JOptionPane.showMessageDialog(null, "Successfully Logged Out");
                currentUser = new User(); //indicate user is logged out
                movies.clear(); //clear announced movies
                movieList.clear(); 
                movieList = MovieController.getAllMovies(); //get a list of all movies
             
                for(int i=0;i<movieList.size();i++){ //get all movies to be announced
                    if(movieList.get(i).regularAnnouncement()!= null){
                        movies.addElement(movieList.get(i).regularAnnouncement());
                    }       
                }
                announcements = new JList<>(movies);
                annualPayment.setVisible(false); //set annualpayment button to be invisible
                paidTickets.clear(); //clear paid tickets
                //paid tickets select/cancel buttons set to invisible
                pListTitle.setVisible(false); 
                selectButton.setVisible(false);
                cancelButton.setVisible(false);
                cancelAllButton.setVisible(false);
                logoutButton.setVisible(false);
            }
            else { //user is already logged in
                JOptionPane.showMessageDialog(null, "Unable to logout\nNo user is logged in");
            }
        }
        if(e.getActionCommand().equals("Purchase Ticket")){
            if(!Form.ticketFormOpened ){
                TicketForm.selectedTickets.clear(); //clear any previously selected tickets
                this.setForm(new TicketForm());
                form.run();
            }
            else{
                JOptionPane.showMessageDialog(null, "Ticket Form already opened");
            }
        }
        
        if(e.getActionCommand().equals("Annual Payment")){
                this.setForm(new PaymentForm());
                form.run();          
        }
        if(e.getActionCommand().equals("Select Ticket")){
            if(paidList.getSelectedIndex()!= -1){ //an item on the ticket list is selected
                for(int i=0;i<PaymentForm.payments.size();i++){ //check which ticket from the list is selected
                    if(paidList.getSelectedValue().equals("Ticket " + String.valueOf(PaymentForm.payments.get(i).getTicket().getID()))){
                        JLabel info = new JLabel(); //display info when ticket is selected
                        info.setText("<html>Ticket ID: " + paidList.getSelectedValue() +
                        "<br>Movie Name: "+PaymentForm.payments.get(i).getTicket().getMovie().getMovieName() +
                        "<br>Time: "+ PaymentForm.payments.get(i).getTicket().getTime().toString() +
                        "<br>Seat Number: " + PaymentForm.payments.get(i).getTicket().getSeatNum() +
                        "<br>Room: " + PaymentForm.payments.get(i).getTicket().getRoomNum() + "</html>");
                        JOptionPane.showMessageDialog(null,info);
                        cancelButton.setVisible(true);
                        cancelAllButton.setVisible(true);
                        paymentSelected = PaymentForm.payments.get(i);
                    }   
                }
            }
        }
        if(e.getActionCommand().equals("Cancel Ticket")){ //cancel ticket selected by user
            if(!paymentSelected.cancelStatus()){ //check if ticket is within 3 days of the show
                JOptionPane.showMessageDialog(null,"Cannot cancel ticket within 3 days of show");
            }
            else{
                JOptionPane.showMessageDialog(null,"Cancellation successful for Ticket " + String.valueOf(paymentSelected.getTicket().getID()));
                
                HomePage.allTickets.remove(paymentSelected.getTicket());
                Payment p = paymentSelected;
                TicketController.updateSeat(p.getTicket().getMovie().getMovieName(),p.getTicket().getTime(),p.getTicket().getSeatNum(),1);
                PaymentForm.payments.remove(paymentSelected); //remove from payments list
                HomePage.currentUser.updatePayments(PaymentForm.payments); 
                String ticket = "Ticket " + paymentSelected.getTicket().getID();
                for(int i=0;i<paidTickets.size();i++){
                
                    if(ticket.equals(paidTickets.get(i))){
                    
                        paidTickets.remove(i); //remove from paidTickets display
                    }
                }
                if(!currentUser.getRegistrationStatus()){
                    JOptionPane.showMessageDialog(null,"Cancel successful\nCode to apply on next purchase is: " + String.valueOf(TicketController.makeNewCode(100)));
                }
            }
        }
        if(e.getActionCommand().equals("Cancel All Tickets")){ //cancel all tickets
            boolean cancel = true;
            int cancelled = 0; //number of cancelled tickets
            ArrayList<Payment> removed = new ArrayList<Payment>();
            for(int i=0;i<PaymentForm.payments.size();i++){
                if(!PaymentForm.payments.get(i).cancelStatus()){ //check if ticket is within 3 days of the show
                    cancel = false;
                }
                else{
                    cancelled++;
                    JOptionPane.showMessageDialog(null,"Cancel Successful for Ticket "+ String.valueOf(PaymentForm.payments.get(i).getTicket().getID()));

                    HomePage.allTickets.remove(PaymentForm.payments.get(i).getTicket());
                    Payment p = PaymentForm.payments.get(i);
                    TicketController.updateSeat(p.getTicket().getMovie().getMovieName(),p.getTicket().getTime(),p.getTicket().getSeatNum(),1);
                    removed.add(PaymentForm.payments.get(i));
                    HomePage.currentUser.updatePayments(PaymentForm.payments); 
                    String ticket = "Ticket " + PaymentForm.payments.get(i).getTicket().getID();
                    for(int j=0;j<paidTickets.size();j++){
                    
                        if(ticket.equals(paidTickets.get(j))){
                        
                            paidTickets.remove(j); //remove from paidTickets display
                        }
                    }
                }
            }
            for(Payment t: removed){
                PaymentForm.payments.remove(t); //remove from payments list
            }
            if(cancel == false){
                JOptionPane.showMessageDialog(null,"One or more tickets could not be cancelled");
            }
            if(!currentUser.getRegistrationStatus()&& cancelled >0){
                JOptionPane.showMessageDialog(null,"Code to apply on next purchase is: " + String.valueOf(TicketController.makeNewCode(cancelled*100)));
            }
        }
    }
 
}