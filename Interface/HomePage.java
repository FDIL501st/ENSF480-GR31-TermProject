package Interface;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class HomePage implements ActionListener{
    protected Form form;
    private static JFrame homeFrame;
   private static JPanel mainPanel;
   private static JPanel announcementPanel;
   private static JButton loginButton;
   private static JButton registerButton;
   private static JButton movieSelect;

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
        loginButton.setBounds(550,10,80,25);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new HomePage());
        registerButton.setBounds(460,10,80,25);
     
        mainPanel.add(title);
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);
       
       
 
         homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         homeFrame.add(mainPanel);
         homeFrame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
       
        if(e.getActionCommand().equals("Login")){
            form = new LoginForm();
            form.run();
        }
        if(e.getActionCommand().equals("Register")){
            form= new RegistrationForm();
            form.run();
        }
    }
}
