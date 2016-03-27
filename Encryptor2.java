//4-2-15
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class Encryptor2
{
   public static void main(String[] args) throws Exception
   {
      Object[] options = {"Encrypt", "Decrypt"};     
      int n = JOptionPane.showOptionDialog(null, "Select encrypt or decrypt.", "Encryptor 2.0",
         JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
      if(n == 1)
         decryptOp();
      else
         encryptOp();
   }
   public static void decryptOp()
   {
      Object[] encryptMethods = {"Morse Code", "Ascii Index", "Binary Tree"};      
         int x = JOptionPane.showOptionDialog(null, "Select method of encryption", "Encryptor 2.0",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, encryptMethods, null);
         switch(x)
         {
            case 0: MorseCode.decryptDialog();
               break;
            case 1: AsciiIndex.decryptDialog();
               break;
            case 2: BinaryTree.decryptDialog();
               break;
         }
   }
   public static void encryptOp()
   {
      Object[] encryptMethods = {"Morse Code", "Ascii Index", "Binary Tree"};      
         int x = JOptionPane.showOptionDialog(null, "Select method of encryption", "Encryptor 2.0",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, encryptMethods, null);
         switch(x)
         {
            case 0: MorseCode.encryptDialog();
               break;
            case 1: AsciiIndex.encryptDialog();
               break;
            case 2: BinaryTree.encryptDialog();
               break;
         }
   }
}