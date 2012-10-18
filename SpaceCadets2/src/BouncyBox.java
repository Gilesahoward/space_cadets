package uk.ac.soton.ecs.gah2g11.space.cadets.bouncy;
   import java.awt.event.*;
   import java.applet.Applet;
   import java.awt.*;
   
	
   public class BouncyBox extends java.applet.Applet
   {
      static int textX = 135;
      static int textY = 40;
   
     // The object we will use to write with instead of the standard screen graphics 
      Graphics bufferGraphics; 
     // The image that will contain everything that has been drawn on 
     // bufferGraphics. 
      Image offscreen; 
      Thread updater;
   
      public void init() {
         resize(650,500);
         offscreen = createImage(650,500); 
         Thread updater = new incrThread(20, textX, textY, 2, this);
         updater.start();
         bufferGraphics = offscreen.getGraphics();
      }
      public void paint(Graphics g) {
         bufferGraphics.clearRect(0,0,650,500); 
         bufferGraphics.drawString("Text that moves both horizontally and vertically", textX, textY);        
         g.drawImage(offscreen,0,0,this);
      }
         
      
     
         
      public void update(Graphics g)
      {
         paint(g);
      }
      
      public static void setTextPos(int newX, int newY, BouncyBox s)
      {
         textY = newY;
         textX = newX;
         s.repaint();	
      }
   
      public static int getTextX()
      {
         return textX;
      }
      public static int getTextY()
      {
         return textX;
      }
      
   }
   class incrThread extends Thread {
      int interval;
      int xPos;
      int yPos;
      int incr;
      BouncyBox applet;
      private boolean finished = false;
      private boolean goLeft = false;
      private boolean goRight = true;
      private boolean goUp = false;
      private boolean goDown = true;
      incrThread(int wait, int x, int y, int i, BouncyBox s)
      {
         interval = wait;
         xPos = x;
         yPos = y;
         incr = i;
         applet = s;
      }
   
      public void run() {
         try
         {
            while(!finished)
            {
               if(goDown && yPos >= 498)
               {
                  goDown = false;
                  goUp = true;
               }
               if(goRight && xPos >= 350)
               {
                  goRight = false;
                  goLeft = true;
               }
               if(goLeft && xPos <= 0)
               {
                  goLeft = false;
                  goRight = true;
               }
               if(goUp && yPos <= 8)
               {
                  goUp = false;
                  goDown = true;
               }
               
               
               if(goDown && goRight)
               
               {
                  this.sleep(interval);
                  xPos += incr;
                  yPos += incr;
                  applet.setTextPos(xPos, yPos, applet);
                 
               
               } 
               else if(goLeft && goUp)
               {
                  this.sleep(interval);
                  xPos -= incr;
                  yPos -= incr;
                  applet.setTextPos(xPos, yPos, applet);
               }
               else if(goLeft && goDown)
               {
                  this.sleep(interval);
                  xPos -= incr;
                  yPos += incr;
                  applet.setTextPos(xPos, yPos, applet);
               }
               else if(goRight && goUp)
               {
                  this.sleep(interval);
                  xPos += incr;
                  yPos -= incr;
                  applet.setTextPos(xPos, yPos, applet);
               }
            }
         }
            catch(InterruptedException io)
            {
               System.out.println("INTERRUPTED EXCEPTION CAUGHT, CLOSING.");
               System.exit(0);
            }
      
      }
   }