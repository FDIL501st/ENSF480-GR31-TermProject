package Interface;
import java.awt.*;
import javax.swing.*;

import Controller.MovieController;

import java.awt.event.*;

public class HomePage implements ActionListener{
    protected Form form;
    private static JFrame homeFrame;
   private static JPanel mainPanel;
   private static JButton loginButton;
   private static JButton registerButton;
   private static JButton movieSelect;
  static DefaultListModel<String> movies = new DefaultListModel<>();

   public static void main(String[] args){ //main to test
    HomePage hp = new HomePage();
    hp.start();
   

}
    public void start(){
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
        
        
     
        movies = MovieController.getNewMovies();
        JList<String> announcements = new JList<>(movies);
     
       
        
        mainPanel.add(title);
        mainPanel.add(announcementTitle);
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);
        mainPanel.add(movieSelect);
        announcementPanel.add(announcements);
  
        homeFrame.add(announcementPanel);
         homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            form.run();
        }
    }
}
