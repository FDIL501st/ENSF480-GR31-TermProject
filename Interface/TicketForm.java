package Interface;
import java.awt.*;
import javax.swing.*;

import Controller.MovieController;
import Controller.TicketController;

import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;

public class TicketForm extends Form implements ActionListener{
    private static JFrame ticketFrame;
    private static JPanel panel;
    private static JButton payButton;
    private static JButton movieButton;
    private static JButton timeselect;
    private static JButton homeButton;
    private static JList<String> movieList;
    private static DefaultListModel<String> movies = new DefaultListModel<>();
    private static DefaultListModel<String> times = new DefaultListModel<>();
    private static JList<String> timeList;
    private String movieSelected;
    private static JPanel seatPanel;
    private static ArrayList<JButton> seatsArr = new ArrayList<JButton>(100);
    private ArrayList<Integer> selectedSeats = new ArrayList<Integer>();
    
   
    
    public void run(){
        ticketFrame = new JFrame();
        ticketFrame.setSize(750,500);
        ticketFrame.setTitle("Ticket Form");
        panel = new JPanel();//panel 
        panel.setLayout(null);

        payButton = new JButton("Pay");
        payButton.addActionListener(new TicketForm());
        payButton.setBounds(100,10,80,25);
 
         homeButton = new JButton("Home");
         homeButton.addActionListener(new TicketForm());
         homeButton.setBounds(10,10,80,25);

        movieButton = new JButton("Select Movie");
         movieButton.addActionListener(new TicketForm());
        movieButton.setBounds(480,400,150,25);

        timeselect = new JButton("Select Time");
        timeselect.addActionListener(new TicketForm());
        timeselect.setBounds(100,400,150,25);
        timeselect.setVisible(false); //set invisible until movie is selected

         JPanel mListTitle = new JPanel();
         mListTitle.setBounds(510,40,200,40);
         mListTitle.setLayout(new BorderLayout());
         JLabel movieLabel = new JLabel();
         movieLabel.setText("Available Movies");
         movieLabel.setFont(new Font("Calibri Black", Font.BOLD, 15));
         movieLabel.setVisible(true);
         mListTitle.add(movieLabel);

         JPanel tListTitle = new JPanel();
         tListTitle.setBounds(140,40,200,40);
         tListTitle.setLayout(new BorderLayout());
         JLabel timeLabel = new JLabel();
         timeLabel.setText("Available Times");
         timeLabel.setFont(new Font("Calibri Black", Font.BOLD, 15));
         timeLabel.setVisible(true);
         tListTitle.add(timeLabel);
         
         JPanel moviePanel = new JPanel();
         moviePanel.setBounds(450,80,200,300);
         moviePanel.setLayout(new FlowLayout());
        
        movies = MovieController.getAllMovies();
          movieList = new JList<>(movies);
          movieList.setFont(new Font("Arial",Font.PLAIN,8));
         moviePanel.add(movieList);

         JPanel timePanel = new JPanel();
         timePanel.setBounds(80,80,200,300);
         timePanel.setLayout(new FlowLayout());
         
         timeList = new JList<>(times);
         timeList.setFont(new Font("Arial",Font.PLAIN,8));
         timePanel.add(timeList);

         seatPanel = new JPanel();
         seatPanel.setLayout(new GridLayout(10,10,3,3));
         seatPanel.setBounds(300,80,150,300);
         for(int i=0;i<100;i++){
           seatsArr.add(new JButton(String.valueOf(i)));
           seatsArr.get(i).setFont(new Font("Arial",Font.PLAIN,6));
           seatsArr.get(i).setHorizontalAlignment(SwingConstants.LEFT);
           seatsArr.get(i).addActionListener(new TicketForm());
           seatsArr.get(i).setVisible(false);

            seatPanel.add(seatsArr.get(i));
        }
      


        
        panel.add(homeButton);
        panel.add(payButton);
        panel.add(movieButton);
        panel.add(timeselect);
        
    
        ticketFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ticketFrame.add(moviePanel);
        ticketFrame.add(timePanel);
        ticketFrame.add(seatPanel);
        ticketFrame.add(mListTitle);
        ticketFrame.add(tListTitle);
        ticketFrame.add(panel);
        ticketFrame.setVisible(true);
        


    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Home")){
            ticketFrame.dispose();
       }
       if(e.getActionCommand().equals("Select Movie")){
    
            if(movieList.getSelectedIndex()!= -1){
                for(int i=0;i<movies.size();i++){
                    if(movieList.getSelectedValue().equals(movies.get(i))){
                        TicketController.getAllShowTimes(movies.get(i),times);
                        timeselect.setVisible(true);
                        movieSelected = movies.get(i);
                    }
                }
            }
        }
        if(e.getActionCommand().equals("Select Time")){
            ArrayList<Integer> seats = new ArrayList<Integer>();
            if(timeList.getSelectedIndex()!= -1){
                for(int i=0;i<times.size();i++){
                    if(timeList.getSelectedValue().equals(times.get(i))){
                    try {
                        seats = TicketController.getSeats(movieSelected,times.get(i));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    }
                }
            }
            for(int i=0;i<seats.size();i++){
                seatsArr.get(seats.get(i)).setVisible(true);
            }
        }
        if(e.getActionCommand().equals("Pay")){
            PaymentForm pf = new PaymentForm();
            pf.run();
        }
        for(int i=0;i<100;i++){
            if(e.getActionCommand().equals(String.valueOf(i))){
                if (selectedSeats.contains(i)) {
                    JOptionPane.showMessageDialog(null,"Seat " +String.valueOf(i) + " unselected");
                    selectedSeats.remove(i);
                }
                else {
                    JOptionPane.showMessageDialog(null,"Seat " +String.valueOf(i) + " selected");
                    selectedSeats.add(i);
                }
            }
        }
    }
}
