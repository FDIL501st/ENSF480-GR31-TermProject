package Interface;
import java.awt.*;
import javax.swing.*;

import Controller.MovieController;

import java.awt.event.*;
import java.text.ParseException;

import Model.Movie;
import Model.Payment;

import java.util.ArrayList;

public class HomePage implements ActionListener{
    protected Form form;
    private static JFrame homeFrame;
    private static JPanel mainPanel;
    private static JButton loginButton;
    private static JButton registerButton;
    private static JButton movieSelect;
    private static JButton annualPayment;
    private static JButton cancelButton;
    static JButton selectButton;
    static DefaultListModel<String> movies = new DefaultListModel<>();
    static ArrayList<Movie> movieList = new ArrayList<Movie>();
    static DefaultListModel<String> paidTickets = new DefaultListModel<>();
    private static JList<String> paidList;
   static JPanel pListTitle;
   private static Payment paymentSelected; //payment to be cancelled

   public static void main(String[] args) throws ParseException{ //main to test
    HomePage hp = new HomePage();
    hp.start();
   

}
    public void start() throws ParseException{
        homeFrame = new JFrame();
        homeFrame.setSize(750,500);
        homeFrame.setTitle("Home Page");

       JPanel title = new JPanel();
       title.setBounds(20,20,250,40);
       title.setLayout(new BorderLayout());
       JLabel label = new JLabel();
       label.setText("Movie Reservation App");
       label.setVisible(true);
       label.setFont(new Font("Calibri Black", Font.BOLD, 20));
       title.add(label);
        mainPanel = new JPanel();//panel 
        mainPanel.setLayout(null);
   

        loginButton = new JButton("Login");
       loginButton.addActionListener(new HomePage());
        loginButton.setBounds(580,10,80,25);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new HomePage());
        registerButton.setBounds(460,10,100,25);
        
        if(Form.loginStatus == true){
            annualPayment = new JButton("Annual Payment");
            annualPayment.addActionListener(new HomePage());
            annualPayment.setBounds(360, 10, 100, 25);
        }

        movieSelect = new JButton("View Movies");
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
        
        JPanel announcementPanel = new JPanel();
        announcementPanel.setBounds(450,80,200,400);
        announcementPanel.setLayout(new FlowLayout());
        
        
        movieList = MovieController.getAllMovies();
        for(int i=0;i<movieList.size();i++){
            if(movieList.get(i).regularAnnouncement()!= null){
                movies.addElement(movieList.get(i).regularAnnouncement());
            }
        }
        JList<String> announcements = new JList<>(movies);

        pListTitle = new JPanel(); 
        pListTitle.setBounds(100,120,150,40);
        pListTitle.setLayout(new BorderLayout());
        JLabel paidLabel = new JLabel(); //label to indicate to the user to enter a movie
        paidLabel.setText("Paid Tickets");
        paidLabel.setFont(new Font("Calibri Black", Font.BOLD, 15));
        paidLabel.setVisible(true);
        pListTitle.add(paidLabel);
        pListTitle.setVisible(false);

        JPanel ticketPanel = new JPanel(); //panel to contain payed ticket information
        ticketPanel.setBounds( 80,180,150,220);
        ticketPanel.setLayout(new FlowLayout());
        paidList = new JList<>(paidTickets); //list of payed tickets
        ticketPanel.add(paidList);

        selectButton = new JButton("Select Ticket"); //button to select the movie typed in the textbox
        selectButton.addActionListener(new HomePage());
       selectButton.setBounds(100,400,150,25);
       selectButton.setVisible(false); //set invisible to begin
        
       cancelButton = new JButton("Cancel Ticket"); //button to cancel the selected ticket 
       cancelButton.addActionListener(new HomePage());
       cancelButton.setBounds(350,400,150,25);
       cancelButton.setVisible(false); //set invisible to begin

        mainPanel.add(title);
        mainPanel.add(announcementTitle);
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);
        mainPanel.add(movieSelect);
        mainPanel.add(selectButton);
        mainPanel.add(cancelButton);
        announcementPanel.add(announcements);
  
        homeFrame.add(announcementPanel);
         homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         homeFrame.add(ticketPanel);
         homeFrame.add(pListTitle);
         homeFrame.add(mainPanel);
         homeFrame.setVisible(true);
    }
    public void setForm(Form f){
        form = f;
    }
    public void actionPerformed(ActionEvent e){
       
        if(e.getActionCommand().equals("Login")){
            this.setForm(new LoginForm());
            form.run();
        
        }
        if(e.getActionCommand().equals("Register")){
            this.setForm(new RegistrationForm());
            form.run(); 
            
        }
        if(e.getActionCommand().equals("View Movies")){
            this.setForm(new TicketForm());
            TicketForm.selectedTickets.clear();
            form.run();
        }
        
        if(e.getActionCommand().equals("Annual Payment")){
        }
        if(e.getActionCommand().equals("Select Ticket")){
            if(paidList.getSelectedIndex()!= -1){ //an item on the ticket list is selected
                for(int i=0;i<PaymentForm.payments.size();i++){ //check which ticket from the list is selected
                    if(paidList.getSelectedValue().equals("Ticket " + String.valueOf(PaymentForm.payments.get(i).getTicket().getID()))){
                        JLabel info = new JLabel();
                        info.setText("<html>Ticket ID: " + paidList.getSelectedValue() +
                        "<br>Movie Name: "+PaymentForm.payments.get(i).getTicket().getMovie().getMovieName() +
                        "<br>Time: "+ PaymentForm.payments.get(i).getTicket().getTime().toString() +
                        "<br>Seat Number: " + PaymentForm.payments.get(i).getTicket().getSeatNum() +
                        "<br>Room: " + PaymentForm.payments.get(i).getTicket().getRoomNum() + "</html>");
                        JOptionPane.showMessageDialog(null,info);
                        cancelButton.setVisible(true);
                        paymentSelected = PaymentForm.payments.get(i);
                    }   
                }
            }
        }
        if(e.getActionCommand().equals("Cancel Ticket")){
            if(!paymentSelected.cancelStatus()){
                JOptionPane.showMessageDialog(null,"Cannot cancel ticket within 3 days of show");
            }
            else{
                JOptionPane.showMessageDialog(null,"Cancellation successful");
                PaymentForm.payments.remove(paymentSelected);
                String ticket = "Ticket " + paymentSelected.getTicket().getID();
                for(int i=0;i<paidTickets.size();i++){
                
                    if(ticket.equals(paidTickets.get(i))){
                        paidTickets.remove(i);
                    }
                }
            }
        }
    }
}
