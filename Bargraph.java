import java.awt.*;
import BreezyGUI.*;

public class Bargraph extends GBFrame
{
   //Establish the placement of the fields for user entry
   Label LabelA = addLabel("A", 1,1,1,1);
   IntegerField FieldA = addIntegerField(0,1,2,1,1);
   Label LabelB = addLabel("B", 1,3,1,1);
   IntegerField FieldB = addIntegerField(0,1,4,1,1);
   Label LabelC = addLabel("C", 1,5,1,1);
   IntegerField FieldC = addIntegerField(0,1,6,1,1);
   Label LabelD = addLabel("D", 1,7,1,1);
   IntegerField FieldD = addIntegerField(0,1,8,1,1);
   Label LabelF = addLabel("F", 1,9,1,1);
   IntegerField FieldF = addIntegerField(0,1,10,1,1);

   //Establish a drop down menu for the drawing
   //Right now we only have one choice -- Bar 
   MenuItem barChoice = addMenuItem ("Graph", "Bar");

   //Establish variables for this program
   int numberOfScores = 5;
   int Xleft = 100;
   int Xright = 300;
   int Ytop = 100;
   int Ybottom = 250; // y value entries can be up to 150
   int BarWidth = 10;

   int totalX , totalY;
   int[ ] scores;
   char graphChoice;

   //Constructor to establish the initial values in the program
   public Bargraph()
   {
      scores = new int[numberOfScores];
      for (int i = 0; i < scores.length; i++)
         scores[ i ] = 0;
      totalX = Xright - Xleft + 1;
      totalY = Ybottom - Ytop + 1;
      graphChoice = 'B';
   }

   //Allow for the menu choices
   //Remember, we only have one choice right now
   public void menuItemSelected (MenuItem item)
   {
      if (item == barChoice)
           graphChoice = 'B';

      repaint();
   }

   public void paint (Graphics g)
   {
      getInputData();
      g.setColor (Color.red); //set color of the graph
      g.drawString("Java Grades",170,290);  //title
      drawBarGraph(g);
   }

   //Get input from the screen
   public void getInputData()
   {
      scores[0] = FieldA.getNumber();
      scores[1] = FieldB.getNumber();
      scores[2] = FieldC.getNumber();
      scores[3] = FieldD.getNumber();
      scores[4] = FieldF.getNumber();
   }

   //Draw the bar graph
   public void drawBarGraph(Graphics g)
   {
      drawAxes(g);
      int i, x, y, height, largestNumber, xIncrement, yIncrement;

   //Compute the x and y increments
   largestNumber = findLargest (scores);
   xIncrement = totalX /numberOfScores;
   if (largestNumber ==0)
      yIncrement = 0;
   else
      yIncrement = totalY / largestNumber;

     //Draw the bars
     for(i=0; i < numberOfScores; i++)
     {
        x = getXCoordinate(i+1, xIncrement);
        y = getYCoordinate(scores[i], yIncrement);
        x = x - BarWidth / 2;
        height = Ybottom - y + 1;
        g.fillRect(x, y, BarWidth, height);
     }

     //Label x - axes with grade choices
     String [ ] label = {"A", "B", "C", "D", "F"};
     for(i=1; i<= numberOfScores; i++)
        g.drawString(label[i-1], 100+ i*xIncrement, 270);

     //Label y - axes with quantity of each grade
    int topy;
     if(largestNumber%10==0)
        topy=largestNumber;
     else
         topy=(largestNumber/10+1)*10;

     //i=i+5 controls y value label -- adjust for size of data
     for (i=0; i<=topy; i=i+5)
     {
        g.drawString(String.valueOf(i), 70, Ybottom-i*yIncrement+5);
     }
  }

  //Draw the axes for the graph
  public void drawAxes (Graphics g)
  {
     g.drawLine(Xleft, Ytop, Xleft, Ybottom);
     g.drawLine(Xleft, Ybottom, Xright, Ybottom);
  }
  //Determining x coordinate
  public int getXCoordinate(int i, int xIncrement)
  {
     return Xleft + xIncrement *i;
  }
  //Determining y coordinate
  public int getYCoordinate(int numStudents, int yIncrement)
  {
     return Ybottom - yIncrement * numStudents;
  }
  //Finding the largest value in the array
  public int findLargest(int [ ] a)
  {
     int i;
     int loc = 0;
     for(i=1; i<a.length; i++)
         if(a[i]>a[loc])
            loc = i;
     return a[loc];
   }
  
  //Main
  public static void main(String[ ] args)
  {
     Frame frm = new bargraph();
     frm.setSize (400, 300);
     frm.setVisible (true);
  }
}