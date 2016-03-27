//4-2-15 ///fix spaces in decryption
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.BorderFactory;
import java.awt.*;
import java.awt.event.*;

public class AsciiIndex extends JPanel
{
   static boolean encryptMode = true;
   static JFrame frame;
   static String oldField = "Enter text or file name to encrypt", oldResult = "";
   public static void encryptDialog()
   {
      encryptMode = true;
      frame = new JFrame("Encryption: Ascii Index");
      frame.setSize(500, 275);
      frame.setLocation(500, 300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new AsciiIndex(oldField, oldResult));
      frame.setVisible(true);  
   }
   
   public static void decryptDialog()
   {
      encryptMode = false;
      frame = new JFrame("Decryption: Ascii Index");
      frame.setSize(500, 275);
      frame.setLocation(500, 300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new AsciiIndex(oldField, oldResult));
      frame.setVisible(true);  
   }
  
   JTextArea field, result;
   
   public AsciiIndex(String fieldText, String resultText)
   {
      setLayout(new FlowLayout());
      
      JPanel north = new JPanel();
      north.setLayout(new FlowLayout());
      add(north, BorderLayout.NORTH);
      
      if(encryptMode == true)
      {
         field = new JTextArea(fieldText, 5, 40);
         field.setBorder(BorderFactory.createLineBorder(new Color(237,149,85), 5));
      }
      else
      {
         if(fieldText.equals("Enter text or file name to encrypt"))
            field = new JTextArea("Enter text or file name to decrypt", 5, 40);
         else
            field = new JTextArea(fieldText, 5, 40);
         field.setBorder(BorderFactory.createLineBorder(new Color(85,173,237), 5));
      }
      field.setLineWrap(true);
      north.add(field);
         
      JPanel center = new JPanel();
      FlowLayout flow = new FlowLayout();
      flow.setHgap(0);
      flow.setVgap(0);
      center.setLayout(flow);
      add(center, BorderLayout.CENTER);
      
      if(encryptMode == true)
      {
         JButton en = new JButton("Encrypt text");
         en.setFocusable(true);
         en.addActionListener(new encryptHandler());
         en.setPreferredSize(new Dimension(110,40));
         en.setMargin(null);
         center.add(en);
      }
      else
      {
         JButton de = new JButton("Decrypt text");
         de.setFocusable(true);
         de.addActionListener(new decryptHandler());
         de.setPreferredSize(new Dimension(110,40));
         de.setMargin(null);
         center.add(de);
      }
      
      JButton file;
      if(encryptMode == true)
      {
         file = new JButton("Encrypt file");
      }
      else
      {
         file = new JButton("Decrypt file");
      }
      file.setFocusable(true);
      file.addActionListener(new fileHandler());
      file.setPreferredSize(new Dimension(110,40));
      file.setMargin(null);
      center.add(file);
      
      JButton clear = new JButton("Clear");
      clear.setFocusable(true);
      clear.addActionListener(new clearHandler());
      clear.setPreferredSize(new Dimension(60,40));
      clear.setMargin(null);
      center.add(clear);
      
      if(encryptMode == true)
      {
         JButton deSwitch = new JButton("Decryption");
         deSwitch.setFocusable(true);
         deSwitch.addActionListener(new deSwitchHandler());
         deSwitch.setPreferredSize(new Dimension(110,40));
         deSwitch.setMargin(null);
         center.add(deSwitch);
      }
      else
      {
         JButton enSwitch = new JButton("Encryption");
         enSwitch.setFocusable(true);
         enSwitch.addActionListener(new enSwitchHandler());
         enSwitch.setPreferredSize(new Dimension(110,40));
         enSwitch.setMargin(null);
         center.add(enSwitch);
      }
      
      JButton mode = new JButton("Change code");
      mode.setFocusable(true);
      mode.addActionListener(new modeHandler());
      mode.setPreferredSize(new Dimension(110,40));
      mode.setMargin(null);
      center.add(mode);
            
      JPanel south = new JPanel();
      south.setLayout(new FlowLayout());
      add(south, BorderLayout.SOUTH);      
            
      result = new JTextArea(resultText, 5, 40);
      result.setLineWrap(true);
      if(encryptMode == true)
         result.setBorder(BorderFactory.createLineBorder(new Color(85,173,237), 5));
      else
         result.setBorder(BorderFactory.createLineBorder(new Color(237,149,85), 5));
      south.add(result);
   }
   
   private class modeHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         frame.dispose();
         if(encryptMode == true)
            Encryptor2.encryptOp();
         else
            Encryptor2.decryptOp();  
      }
   }
   
   private class encryptHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if(field.getText().length() == 0)
            result.setText("Please enter text to encrypt.");
         else
            result.setText(encrypt(field.getText()));
         oldResult = result.getText();
         oldField = field.getText();
      }
   }
   
   private class fileHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         result.setText("");    
         try
         {
            String filename = field.getText();
            Scanner infile = new Scanner(new File(filename));
            String toUse = "";
            while(infile.hasNextLine())
               toUse += infile.nextLine() + " ";
            if(encryptMode == true)
               result.setText(result.getText() + encrypt(toUse));
            else
               result.setText(result.getText() + decrypt(toUse));
            oldResult = result.getText();
            Object[] options = {"Save to new file", "No", "Yes"};
            String word = "encrypted";
            String title = "Encrypter 2.0";
            if(encryptMode == false)
            {
               word = "decrypted";
               title = "Decryptor 2.0";
            }
            int n = JOptionPane.showOptionDialog(null, "Would you like to save the "+word+" message to "
               +filename+"?", title,
                  JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
            if(n == 0)
            {
               PrintStream p = new PrintStream(new FileOutputStream(new File(filename.substring(0, 
                  filename.length()-4)+"Output.txt")));
               p.print(result.getText());
            } 
            else if(n == 2)
            {
               Scanner addfile = new Scanner(new File(filename));
               ArrayList<Object> lines = new ArrayList<Object>();
               while(addfile.hasNextLine())
                  lines.add(addfile.nextLine());
               PrintStream p = new PrintStream(new FileOutputStream(new File(filename)));
               for(Object o: lines)
                  p.println(o);
               p.println(result.getText());
            }           
         }
         catch(FileNotFoundException x)
         {
            if(encryptMode == true)
               result.setText("Please enter a valid file name to encrypt.");
            else
               result.setText("Please enter a valid file name to decrypt.");
         }
      }
   }
   
   private class clearHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         result.setText("");
         field.setText("");
         oldField = "Enter text or file name to encrypt";
         oldResult = "";
      }
   }
   
   private class deSwitchHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         switchToDecryption();
      }
   }
   
   private class decryptHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if(field.getText().length() == 0)
            result.setText("Please enter text to decrypt.");
         else
            result.setText(decrypt(field.getText()));
         oldResult = result.getText();
         oldField = field.getText();
      }
   }
   
   private class enSwitchHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         switchToEncryption();
      }
   }
   
   public void switchToDecryption()
   {
      if(!oldField.equals("Enter text or file name to encrypt") && !oldResult.equals(""))
      {
         oldResult = field.getText();
         oldField = result.getText();
      }
      frame.dispose();
      decryptDialog();
   }
   
   public void switchToEncryption()
   {
      if(!oldField.equals("Enter text or file name to encrypt") && !oldResult.equals(""))
      {
         oldResult = field.getText();
         oldField = result.getText();
      }
      frame.dispose();
      encryptDialog();
   }
   
   public String encrypt(String word)
   {
      String encrypted = "";
      int[] asciis = new int[word.length()];
      for(int i = 0; i < asciis.length; i++)
      {
         if(word.charAt(i) != ' ')
         {
            asciis[i] = (int)(word.charAt(i)-i);
            if(asciis[i] < 33)
               asciis[i] = 126 - (33 - asciis[i]);
         }
         else
            asciis[i] = (int)(' ');
         encrypted += (char)(asciis[i]); 
      }
      return encrypted;
   }
   
   public static String decrypt(String word)
   {
      String decrypted = "";
      int[] asciis = new int[word.length()];
      for(int i = 0; i < asciis.length; i++)
      {
         if(word.charAt(i) != ' ')
         {
            asciis[i] = (int)(word.charAt(i)+i);
            if(asciis[i] > 126)
               asciis[i] = 33 + (asciis[i] - 126);
         }
         else
            asciis[i] = (int)(' ');
         decrypted += (char)(asciis[i]); 
      }
      return decrypted;
   }    
}