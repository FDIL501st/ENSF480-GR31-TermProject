package Interface;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TicketForm extends Form implements ActionListener{
    private static JFrame ticketFrame;
    private static JPanel panel;
    private static JButton payButton;
    private static JButton cancelButton;
    private static JButton homeButton;
    
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

         JPanel listTitle = new JPanel();
         listTitle.setBounds(510,40,200,40);
         listTitle.setLayout(new BorderLayout());
         JLabel movieLabel = new JLabel();
         movieLabel.setText("Available Movies");
         movieLabel.setFont(new Font("Calibri Black", Font.BOLD, 15));
         movieLabel.setVisible(true);
         listTitle.add(movieLabel);
         
         JPanel moviePanel = new JPanel();
         moviePanel.setBounds(450,80,200,400);
         moviePanel.setLayout(new FlowLayout());
        
         DefaultListModel<String> movies = HomePage.mc.getAllMovies();
         JList<String> movieList = new JList<>(movies);
         moviePanel.add(movieList);

        
        panel.add(homeButton);
        panel.add(payButton);
        
    
        ticketFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ticketFrame.add(moviePanel);
        ticketFrame.add(listTitle);
        ticketFrame.add(panel);
        ticketFrame.setVisible(true);
        


    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Home")){
            ticketFrame.dispose();
       }
    }
}
