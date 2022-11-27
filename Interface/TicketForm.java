package Interface;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TicketForm extends Form implements ActionListener{
    private static JFrame ticketFrame;
    private static JPanel panel;
    private static JButton payButton;
    private static JButton movieButton;
    private static JButton homeButton;
    private static JList<String> movieList;
    private static DefaultListModel<String> movies = new DefaultListModel<>();
    private static DefaultListModel<String> times = new DefaultListModel<>();
    private static JList<String> timeList;
    
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
        movieButton.setBounds(450,400,150,25);

         JPanel mListTitle = new JPanel();
         mListTitle.setBounds(510,40,200,40);
         mListTitle.setLayout(new BorderLayout());
         JLabel movieLabel = new JLabel();
         movieLabel.setText("Available Movies");
         movieLabel.setFont(new Font("Calibri Black", Font.BOLD, 15));
         movieLabel.setVisible(true);
         mListTitle.add(movieLabel);

         JPanel tListTitle = new JPanel();
         tListTitle.setBounds(160,40,200,40);
         tListTitle.setLayout(new BorderLayout());
         JLabel timeLabel = new JLabel();
         timeLabel.setText("Available Times");
         timeLabel.setFont(new Font("Calibri Black", Font.BOLD, 15));
         timeLabel.setVisible(true);
         tListTitle.add(timeLabel);
         
         JPanel moviePanel = new JPanel();
         moviePanel.setBounds(450,80,200,300);
         moviePanel.setLayout(new FlowLayout());
        
        movies = HomePage.mc.getAllMovies();
          movieList = new JList<>(movies);
         moviePanel.add(movieList);

         JPanel timePanel = new JPanel();
         timePanel.setBounds(100,80,200,300);
         timePanel.setLayout(new FlowLayout());
         
         timeList = new JList<>(times);
         timePanel.add(timeList);

        
        panel.add(homeButton);
        panel.add(payButton);
        panel.add(movieButton);
        
    
        ticketFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ticketFrame.add(moviePanel);
        ticketFrame.add(timePanel);
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
                        HomePage.mc.getAllShowTimes(movies.get(i),times);
                    
                    }
                }
            }
        }
    }
}
