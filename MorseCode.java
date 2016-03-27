//4-2-15
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.BorderFactory;
import java.awt.*;
import java.awt.event.*;

public class MorseCode extends JPanel
{
   static Map<String, String> map;
   static boolean encryptMode = true, symbolError = false;
   static JFrame frame;
   static String oldField = "Enter text or file name to encrypt", oldResult = "";
   public static void encryptDialog()
   {
      map = makeMap();
      encryptMode = true;
      frame = new JFrame("Encryption: Morse Code");
      frame.setSize(500, 275);
      frame.setLocation(500, 300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new MorseCode(oldField, oldResult));
      frame.setVisible(true);  
   }
   
   public static void decryptDialog()
   {
      map = makeMap();
      encryptMode = false;
      frame = new JFrame("Decryption: Morse Code");
      frame.setSize(500, 275);
      frame.setLocation(500, 300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new MorseCode(oldField, oldResult));
      frame.setVisible(true);  
   }
  
   JTextArea field, result;
   
   public MorseCode(String fieldText, String resultText)
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
   
   private class fileHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         result.setText("");    
         try
         {
            String filename = field.getText();
            Scanner infile = new Scanner(new File(filename));
            while(infile.hasNext() && symbolError == false)
            {
               if(encryptMode == true)
                  result.setText(result.getText() + encrypt(infile.next()));
               else
                  result.setText(result.getText() + decrypt(infile.next()));
            }
            oldResult = result.getText();
            if(symbolError == false)
            {
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
            else
               symbolError = false;          
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
      String[] splits = word.split(" ");
      String encrypted = "";
      for(int j = 0; j < splits.length; j++)
      {
         for(int i = 0; i < splits[j].length(); i++)
         {
            String s = splits[j].substring(i, i+1);
            boolean upper = false;
            if(Character.isUpperCase(s.charAt(0)))
            {
               upper = true;
               encrypted += map.get(s.toLowerCase());
            }
            else
               encrypted += map.get(s);
            upper = false;
            encrypted += "|";
         }
         encrypted = encrypted.substring(0, encrypted.length()-1);
         encrypted += "  ";
      }
      return encrypted;
   }
   
   public static String decrypt(String word)
   {
      boolean error = false;
      for(int i = 0; i < word.length(); i++)
      {
         if(word.charAt(i) != '.' && word.charAt(i) != '-'
                && word.charAt(i) != ' ' && word.charAt(i) != '|')
         {
            symbolError = true;
            error = true;
         }
      }
      if(error == true)
         return "Morse Code must consist of only ., -, and |.";
      else
      {
         String decrypted = "";
         if(word.contains("|"))
         {
            String sub = "";
            int oldPos = 0;
         //String[] splits = word.split("|");
            for(int i = 0; i < word.length(); i++)
            {
               if(word.charAt(i) == '|' || word.charAt(i) == ' ')
               {
                  sub = word.substring(oldPos, i);
                  oldPos = i+1;
                  decrypted += morseDecodeKey(sub); 
               }
            }
            sub = word.substring(oldPos, word.length());
            decrypted += morseDecodeKey(sub) + " ";
         }
         else
            decrypted += morseDecodeKey(word) + " ";
         return decrypted;
      }
   }
   
   public static Map<String, String> makeMap()
   {
      Map<String, String> map = new HashMap<String, String>();
      map.put("a", ".-");
      map.put("b", "-...");
      map.put("c", "-.-.");
      map.put("d", "-..");
      map.put("e", ".");
      map.put("f", "..-.");
      map.put("g", "--.");
      map.put("h", "....");
      map.put("i", "..");
      map.put("j", ".---");
      map.put("k", "-.-");
      map.put("l", ".-..");
      map.put("m", "--");
      map.put("n", "-.");
      map.put("o", "---");
      map.put("p", ".--.");
      map.put("q", "--.-");
      map.put("r", ".-.");
      map.put("s", "...");
      map.put("t", "-");
      map.put("u", "..-");
      map.put("v", "...-");
      map.put("w", ".--");
      map.put("x", "-..-");
      map.put("y", "-.--");
      map.put("z", "--..");
      map.put("1", ".----");
      map.put("2", "..---");
      map.put("3", "...--");
      map.put("4", "....-");
      map.put("5", ".....");
      map.put("6", "-....");
      map.put("7", "--...");
      map.put("8", "---..");
      map.put("9", "----.");
      map.put("0", "-----");
      map.put(".", ".-.-.-");
      map.put(",", "--..--");
      map.put("?", "..--..");
      map.put("'", ".----.");
      map.put("!", "-.-.--");
      map.put("/", "-..-.");
      map.put("(", "-.--.");
      map.put(")", "-.--.-");
      map.put("&", ".-...");
      map.put(":", "---...");
      map.put(";", "-.-.-.");
      map.put("=", "-...-");
      map.put("+", ".-.-.");
      map.put("-", "-....-");
      map.put("_", "..--.-");
      map.put("$", "...-..-");
      map.put("@", ".--.-.");
      map.put("\"", ".-..-.");
      map.put(" ", " ");
      return map;
   }

   public static String morseDecodeKey(String morse)
   {
      for(String s: map.keySet())
      {
         if(map.get(s).equals(morse))
            return s;
      }
      return " ";
   }

}