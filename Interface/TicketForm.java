package Interface;
import java.awt.*;
import javax.swing.*;


import Controller.TicketController;
import Model.Ticket;
import Model.Movie;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

public class TicketForm extends Form implements ActionListener{
    private static JFrame ticketFrame; 
    private static JPanel panel;
    private static JButton payButton; 
    private static JButton movieButton;
    private static JButton timeselect;
    private static JButton homeButton;
    private static ArrayList<Date> dates; //stores a list of show times for the movie selected
    private static DefaultListModel<String> times = new DefaultListModel<>(); //stores a list of show times for the movie selected
    private static JList<String> timeList; 
    private static Movie movieSelected; //current movie selected
    private static Date timeSelected; //current time selected
    private static int roomSelected; //current room selected
    private static JPanel seatPanel; 
    static ArrayList<JButton> seatsArr = new ArrayList<JButton>(100); //array of all seats for a given movie and time
    static ArrayList<Ticket> selectedTickets = new ArrayList<Ticket>(); //list of all tickets selected
    private static JTextField movieText;
    private static  JPanel sListTitle;
    private static  JPanel tListTitle;

   
    
    public void run(){
        ticketFrame = new JFrame(); //create frame
        ticketFrame.setSize(750,500);
        ticketFrame.setTitle("Ticket Form");
        panel = new JPanel();//create new panel 
        panel.setLayout(null);

        payButton = new JButton("Pay"); //button to start payment
        payButton.addActionListener(new TicketForm());
        payButton.setBounds(100,10,80,25);
 
         homeButton = new JButton("Home"); //button to return to homepage
         homeButton.addActionListener(new TicketForm());
         homeButton.setBounds(10,10,80,25);

        movieButton = new JButton("Select Movie"); //button to select the movie typed in the textbox
         movieButton.addActionListener(new TicketForm());
        movieButton.setBounds(100,400,150,25);

        timeselect = new JButton("Select Time"); //button to select time
        timeselect.addActionListener(new TicketForm());
        timeselect.setBounds( 480,400,150,25);
        timeselect.setVisible(false); //set invisible until movie is selected

         JPanel mListTitle = new JPanel(); 
         mListTitle.setBounds(100,40,150,40);
         mListTitle.setLayout(new BorderLayout());
         JLabel movieLabel = new JLabel(); //label to indicate to the user to enter a movie
         movieLabel.setText("Enter Movie");
         movieLabel.setFont(new Font("Calibri Black", Font.BOLD, 15));
         movieLabel.setVisible(true);
         mListTitle.add(movieLabel);

         tListTitle = new JPanel(); //panel for time list title
         tListTitle.setBounds(510,40,150,40);
         tListTitle.setLayout(new BorderLayout());
         JLabel timeLabel = new JLabel(); //label for time list title
         timeLabel.setText("Available Times");
         timeLabel.setFont(new Font("Calibri Black", Font.BOLD, 15));
         timeLabel.setVisible(true);
         tListTitle.add(timeLabel);
         tListTitle.setVisible(false); //set panel to invisible until a movie is selected

         sListTitle = new JPanel(); //panel for seat display title
         sListTitle.setBounds(300,40,150,40);
         sListTitle.setLayout(new BorderLayout());
         sListTitle.setVisible(false); //set panel to invisible until a movie and time is selected
         JLabel seatLabel = new JLabel(); //label for seat display title
         seatLabel.setText("Available Seats");
         seatLabel.setFont(new Font("Calibri Black", Font.BOLD, 15));
         seatLabel.setVisible(true);
         sListTitle.add(seatLabel);

         movieText = new JTextField(); //text box to enter movie name
         movieText.setBounds(100,80,100,25);
        
         JPanel timePanel = new JPanel(); //panel to contain time information
         timePanel.setBounds( 450,80,150,300);
         timePanel.setLayout(new FlowLayout());
         
         timeList = new JList<>(times); //list of times for a selected movie
         timeList.setFont(new Font("Arial",Font.PLAIN,8));
         timePanel.add(timeList);

         seatPanel = new JPanel(); //panel for seat display
         seatPanel.setLayout(new GridLayout(10,10,3,3)); //panel set to 10x10 grid layout
         seatPanel.setBounds(300,80,150,300);
         for(int i=0;i<100;i++){ //create buttons to represent seats in an array of buttons
           seatsArr.add(new JButton(String.valueOf(i)));
           seatsArr.get(i).setFont(new Font("Arial",Font.PLAIN,6));
           seatsArr.get(i).setHorizontalAlignment(SwingConstants.LEFT);
           seatsArr.get(i).addActionListener(new TicketForm());
           seatsArr.get(i).setVisible(false); //set all buttons to be invisible before any selection is made

            seatPanel.add(seatsArr.get(i)); //add all buttons to seat panel
        }
      

        //add buttons and textfield to main panel
        panel.add(movieText);
        panel.add(homeButton);
        panel.add(payButton);
        panel.add(movieButton);
        panel.add(timeselect);
        
    
        
       //add all panels to frame and set frame to visible
        ticketFrame.add(timePanel);
        ticketFrame.add(seatPanel);
        ticketFrame.add(mListTitle);
        ticketFrame.add(tListTitle);
        ticketFrame.add(sListTitle);
        ticketFrame.add(panel);
        ticketFrame.setVisible(true); 
        
        


    }
    public void actionPerformed(ActionEvent e){
        String movieName = movieText.getText().trim(); //movie name entered by user in textbox

        if(e.getActionCommand().equals("Home")){ //close ticket form if home button pressed
            seatsArr.clear();
            selectedTickets.clear();
            times.clear();
            ticketFrame.dispose();
       }
       if(e.getActionCommand().equals("Select Movie")){ //select movie button pressed
                tListTitle.setVisible(false); //set seat display title and time list title to invisible
                sListTitle.setVisible(false);
                for(int i=0;i<seatsArr.size();i++){ //remove any previous seat displays
                    seatsArr.get(i).setVisible(false);
                }
                times.clear(); //clear previous time list
                boolean found = false;//set to true if user entered a value movie name
                for(int i=0;i<HomePage.movieList.size();i++){
                    if(HomePage.movieList.get(i).getMovieName().equalsIgnoreCase(movieName)){
                        found = true;
                        dates = HomePage.movieList.get(i).getAvailableTimes();
                        for(int j=0;j<dates.size();j++){
                            times.addElement(dates.get(j).toString()); //convert Date to string
                        }
                        
                        timeselect.setVisible(true); //set timeselect button to visible
                        tListTitle.setVisible(true);    //set time list title to be visible
                        TicketForm.movieSelected = HomePage.movieList.get(i); //set the selected movie to the name entered by user
                        break;
                    }
                }
                if(!found){
                    JOptionPane.showMessageDialog(null,movieName + " is an invalid movie name");
                }   
        }
        if(e.getActionCommand().equals("Select Time")){ //select time button is pressed
            ArrayList<Integer> seats = new ArrayList<Integer>(); 
            if(timeList.getSelectedIndex()!= -1){ //an item on the time list is selected
                for(int i=0;i<times.size();i++){ //check which time from the list is selected
                    if(timeList.getSelectedValue().equals(times.get(i))){
                        seats = TicketController.getSeats(TicketForm.movieSelected.getMovieName(),dates.get(i)); //get array of seats
                        timeSelected = dates.get(i);
                    }
                }
            }
            for(int i=1;i<seats.size();i++){ //index 0 is the room #
                if(seats.get(i)==1){  //1 indicates that a seat is available so set the display button to be visible
                    seatsArr.get(i).setVisible(true); 
                }
            }
            roomSelected = seats.get(0); //set room selected
            sListTitle.setVisible(true); //set seat title to be visible
        }
        if(e.getActionCommand().equals("Pay")){
            if (selectedTickets.size() == 0) {
                JOptionPane.showMessageDialog(null, "No tickets selected");
            }
            else {
                PaymentForm pf = new PaymentForm();
                times.clear();
                pf.run();
            }
        }
        for(int i=0;i<100;i++){
            if(e.getActionCommand().equals(String.valueOf(i))){
                boolean selected = false;
                for(int j=0;j<selectedTickets.size();j++){
                    if(selectedTickets.get(j).getMovie().getMovieName()==movieSelected.getMovieName() &&selectedTickets.get(j).getTime().equals(timeSelected) && selectedTickets.get(j).getSeatNum()==i){
                        JOptionPane.showMessageDialog(null,"Seat " +String.valueOf(i) + " unselected");
                        selectedTickets.remove(j);
                        selected = true;
                        break;
                    }
                }
                if(!selected) {
                    JOptionPane.showMessageDialog(null,"Seat " +String.valueOf(i) + " selected");
                    
                        selectedTickets.add(new Ticket(movieSelected,timeSelected,i,roomSelected));
                  
                }
            }
        }
    }
  
}
