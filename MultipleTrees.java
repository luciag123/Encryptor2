//4-2-15
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.BorderFactory;
import java.awt.*;
import java.awt.event.*;

public class MultipleTrees extends JPanel
{
   static TreeNode tree;
   static boolean encryptMode = true;
   static JFrame frame;
   String pathFinal = "";
   static String oldField = "Enter text or file name to encrypt", oldResult = "";
   public static void main(String[] args)
   {
      tree = makeTree();
      encryptMode = true;
      frame = new JFrame("Encryption: Multiple Binary Trees");
      frame.setSize(500, 275);
      frame.setLocation(500, 300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new MultipleTrees(oldField, oldResult));
      frame.setVisible(true);
   }
   public static void decryptDialog()
   {
      tree = makeTree();
      encryptMode = false;
      frame = new JFrame("Decryption: Multiple Binary Trees");
      frame.setSize(500, 275);
      frame.setLocation(500, 300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new MultipleTrees(oldField, oldResult));
      frame.setVisible(true);
   }
   
   JTextArea field, result;
   
   public MultipleTrees(String fieldText, String resultText)
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
         {
            String bad = "0123456789.,?!-_|/#$%^&*()+=:;";
            boolean error = false;
            for(int i = 0; i < field.getText().length(); i++)
            {
               if(bad.indexOf(field.getText().charAt(i)) != -1)
                  error = true;
            }
            if(error == true)
               result.setText("Multiple Binary Trees text can't contain numbers or symbols.");
            else
               result.setText(encrypt(field.getText()));
         }
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
         {
            boolean error = false;
            for(int i = 0; i < field.getText().length(); i++)
            {
               if(field.getText().charAt(i) != '0' && field.getText().charAt(i) != '1'
                && field.getText().charAt(i) != ' ')
                  error = true;
            }
            if(error == true)
               result.setText("Encoded Multiple Binary Trees must consist of only 0s and 1s.");
            else
               result.setText(decrypt(field.getText()));
         }
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
      //encryptDialog();
   }
   
   public String encrypt(String word)
   {
      String encrypted = "";
      for(int i = 0; i < word.length(); i++)
      {
         encrypted += encryptHelper(word.substring(i, i+1));       
      }
      return encrypted;
   }
   
   public String encryptHelper(String word)
   {
      boolean found = false;
      int pathNum = 0;
      String path = "00000";
      TreeNode t = tree;
      if(word.equals(" "))
         return " ";
      while(found != true)
      {
         for(int i = 0; i < path.length(); i++)
         {
            if(path.substring(i, i+1).equals("0"))
               t = t.getLeft();
            else
               t = t.getRight();
            if(t.getValue().toString().equalsIgnoreCase(word))
            {
               found = true;
               return path;
            }
         } 
         if(found == false)
         {
            pathNum++;
            path = Integer.toBinaryString(pathNum);
            int length = path.length();
            for(int j = 0; j < (5-length); j++)
               path = "0" + path;
            t = tree;
         }
      }
      return "null";
   }
   
   public static String decrypt(String word)
   {
      TreeNode t = tree;
      String decrypted = "";
      String[] words = word.split(" ");
      Queue<String> letterQueue = new LinkedList<String>();
      for(int k = 0; k < words.length; k++)
      {
         //String[] letters = new String[words[k].length()/5];
         int fives = 0;
         while(fives < words[k].length()/5)
         {
            letterQueue.add(words[k].substring(5*fives, 5*(fives+1)));
            fives++;
         }
         int queuelength = letterQueue.size();
         for(int i = 0; i < queuelength; i++)
         {
            String currentLetter = letterQueue.remove();
            for(int j = 0; j < 5; j++)
            {     
               if(currentLetter.substring(j, j+1).equals("0"))
                  t = t.getLeft();
               else
                  t = t.getRight();
            }
            decrypted += t.getValue();
            t = tree;
         }
         letterQueue = new LinkedList<String>();
         decrypted += " ";
      }
      return decrypted.toLowerCase();
   }    
   
   public static TreeNode makeTree()
   {
      String s = "x ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ACEGIKMOQSUWYBDFHJLNPRTVXZ^^^^^^";
      TreeNode root = new TreeNode("" + s.charAt(1), null, null);
      for(int pos = 2; pos < s.length(); pos++)
         insert(root, "" + s.charAt(pos), pos, 
               (int)(1 + Math.log(pos) / Math.log(2)));
      return root;
   }
   public static void insert(TreeNode t, String s, int pos, int level)
   {
      TreeNode p = t;
      for(int k = level - 2; k > 0; k--)
         if((pos & (1 << k)) == 0)
            p = p.getLeft();
         else
            p = p.getRight();
      if((pos & 1) == 0)
         p.setLeft(new TreeNode(s, null, null));
      else
         p.setRight(new TreeNode(s, null, null));
   }
}

class TreeNode
{
   private Object value; 
   private TreeNode left, right;
   
   public TreeNode(Object initValue)
   { 
      value = initValue; 
      left = null; 
      right = null; 
   }
   
   public TreeNode(Object initValue, TreeNode initLeft, TreeNode initRight)
   { 
      value = initValue; 
      left = initLeft; 
      right = initRight; 
   }
   
   public Object getValue()
   { 
      return value; 
   }
   
   public TreeNode getLeft() 
   { 
      return left; 
   }
   
   public TreeNode getRight() 
   { 
      return right; 
   }
   
   public void setValue(Object theNewValue) 
   { 
      value = theNewValue; 
   }
   
   public void setLeft(TreeNode theNewLeft) 
   { 
      left = theNewLeft;
   }
   
   public void setRight(TreeNode theNewRight)
   { 
      right = theNewRight;
   }
}