package Interface;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class PaymentForm extends Form implements ActionListener{
    private static JFrame paymentFrame;
    private static JPanel panel;
    private static JButton confirmButton;
    private static JButton homeButton;
    public void run(){
        paymentFrame = new JFrame();
        paymentFrame.setSize(750,500);
        paymentFrame.setTitle("Payment Form");
        panel = new JPanel();//panel 
        panel.setLayout(null);

        homeButton = new JButton("Home");
        homeButton.addActionListener(new PaymentForm());
        homeButton.setBounds(10,10,80,25);

        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new PaymentForm());
        confirmButton.setBounds(450,400,150,25);

        panel.add(homeButton);
        panel.add(confirmButton);
        
        
    
        paymentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        paymentFrame.add(panel);
        paymentFrame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Home")){
            paymentFrame.dispose();
       }
    }
}
