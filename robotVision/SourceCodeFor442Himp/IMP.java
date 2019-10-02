/*
 *Hunter Lloyd
 * Copyrite.......I wrote, ask permission if you want to use it outside of class. 
 */

/*Josh Cullings and Brendan Blanchard's lab*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.awt.image.PixelGrabber;
import java.awt.image.MemoryImageSource;
import java.util.Arrays;
import java.util.prefs.Preferences;

class IMP implements MouseListener{
   JFrame frame;
   JPanel mp;
   JButton start;
   JScrollPane scroll;
   JMenuItem openItem, exitItem, resetItem;
   Toolkit toolkit;
   File pic;
   ImageIcon img;
   int colorX, colorY;
   int [] pixels;
   int [] results;
   //Instance Fields you will be using below
   int orientation = 0;
   //This will be your height and width of your 2d array
   int height=0, width=0;
   //your 2D array of pixels
    int picture[][];

    /* 
     * In the Constructor I set up the GUI, the frame the menus. The open pulldown 
     * menu is how you will open an image to manipulate. 
     */
   IMP()
   {
      toolkit = Toolkit.getDefaultToolkit();
      frame = new JFrame("Image Processing Software by Hunter");
      JMenuBar bar = new JMenuBar();
      JMenu file = new JMenu("File");
      JMenu functions = getFunctions();
      frame.addWindowListener(new WindowAdapter(){
            @Override
              public void windowClosing(WindowEvent ev){quit();}
            });
      openItem = new JMenuItem("Open");
      openItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ handleOpen(); }
           });
      resetItem = new JMenuItem("Reset");
      resetItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ reset(); }
           });     
      exitItem = new JMenuItem("Exit");
      exitItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ quit(); }
           });
      file.add(openItem);
      file.add(resetItem);
      file.add(exitItem);
      bar.add(file);
      bar.add(functions);
      frame.setSize(600, 600);
      mp = new JPanel();
      mp.setBackground(new Color(0, 0, 0));
      scroll = new JScrollPane(mp);
      frame.getContentPane().add(scroll, BorderLayout.CENTER);
      JPanel butPanel = new JPanel();
      butPanel.setBackground(Color.black);
      start = new JButton("start");
      start.setEnabled(false);
      start.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ fun1(); }
           });
      butPanel.add(start);
      frame.getContentPane().add(butPanel, BorderLayout.SOUTH);
      frame.setJMenuBar(bar);
      frame.setVisible(true);      
   }
   
   /* 
    * This method creates the pulldown menu and sets up listeners to selection of the menu choices. If the listeners are activated they call the methods 
    * for handling the choice, fun1, fun2, fun3, fun4, etc. etc. 
    */
   
  private JMenu getFunctions()
  {
     JMenu fun = new JMenu("Functions");
     
     JMenuItem firstItem = new JMenuItem("MyExample - fun1 method");
     JMenuItem secondItem = new JMenuItem("Gray Scale method");
     JMenuItem thirdItem = new JMenuItem("Rotate Method");
     JMenuItem fourthItem = new JMenuItem("Edge Method");
     JMenuItem fifthItem = new JMenuItem("Blur Method");
     JMenuItem sixthItem = new JMenuItem("Histogram");
     JMenuItem seventhItem = new JMenuItem("Equalize");

     firstItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){fun1();}
           });
   
      fun.add(firstItem);
      
      //adding the grayscale method to the menu
      secondItem.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent evt){grayScale();}
         });
 
      fun.add(secondItem);
      
      //adding the rotate method to the menu
      thirdItem.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent evt){rotate();}
         });
 
      fun.add(thirdItem);
      

       //adding the edge method to the menu
      fourthItem.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent evt){edge();}
      });
      
      fun.add(fourthItem);


      //adding the blur method to the menu
      fifthItem.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent evt){blur();}
         });
 
      fun.add(fifthItem);
      
      //adding the blur method to the menu
      sixthItem.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent evt){histogram();}
         });
 
      fun.add(sixthItem);

      //adding the blur method to the menu
      seventhItem.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent evt){equalize();}
         });
 
      fun.add(seventhItem);
      return fun;   
  }
  
  /*
   * This method handles opening an image file, breaking down the picture to a one-dimensional array and then drawing the image on the frame. 
   * You don't need to worry about this method. 
   */
    private void handleOpen()
  {  
     img = new ImageIcon();
     JFileChooser chooser = new JFileChooser();
      Preferences pref = Preferences.userNodeForPackage(IMP.class);
      String path = pref.get("DEFAULT_PATH", "");

      chooser.setCurrentDirectory(new File(path));
     int option = chooser.showOpenDialog(frame);
     
     if(option == JFileChooser.APPROVE_OPTION) {
        pic = chooser.getSelectedFile();
        pref.put("DEFAULT_PATH", pic.getAbsolutePath());
       img = new ImageIcon(pic.getPath());
      }
     width = img.getIconWidth();
     height = img.getIconHeight(); 
     
     JLabel label = new JLabel(img);
     label.addMouseListener(this);
     pixels = new int[width*height];
     
     results = new int[width*height];
  
          
     Image image = img.getImage();
        
     PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width );
     try{
         pg.grabPixels();
     }catch(InterruptedException e)
       {
          System.err.println("Interrupted waiting for pixels");
          return;
       }
     for(int i = 0; i<width*height; i++)
        results[i] = pixels[i];  
     turnTwoDimensional();
     mp.removeAll();
     mp.add(label);
     mp.revalidate();
  }
  
  /*
   * The libraries in Java give a one dimensional array of RGB values for an image, I thought a 2-Dimensional array would be more usefull to you
   * So this method changes the one dimensional array to a two-dimensional. 
   */
  private void turnTwoDimensional()
  {
     picture = new int[height][width];
     for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
          picture[i][j] = pixels[i*width+j];
  }
  /*
   *  This method takes the picture back to the original picture
   */
  private void reset()
  {
	  orientation = 0;
	  for(int i = 0; i<width*height; i++)
	  		pixels[i] = results[i];
	  turnTwoDimensional();
      Image img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width));
      JLabel label2 = new JLabel(new ImageIcon(img2));    
      mp.removeAll();
      mp.add(label2);
      mp.revalidate(); 
      mp.repaint();
    }
  /*
   * This method is called to redraw the screen with the new image. 
   */
  private void resetPicture()
  {
	  	Image img2;
      	switch (orientation){
      	
      	//image has been rotated 90 degrees to the right
      	case 1:
      		for(int i=0; i<height; i++)
      			for(int j=width-1; j>=0; j--)
      				pixels[j*height+i] = picture[i][j];
      		img2 = toolkit.createImage(new MemoryImageSource(height, width, pixels, 0, height));
      		break;
      	//image has been rotated 180 degrees to the right i.e. upside down
      	case 2:
      		for(int i=height-1; i>=0; i--)
      			for(int j=width-1; j>=0; j--)
      				pixels[i*width+j] = picture[i][j];
      		img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width)); 
      		break;
      	
      	//image has been rotated 270 degrees to the right
      	case 3:
      		for(int i=height-1; i>=0; i--)
      			for(int j=0; j<width; j++)
      				pixels[j*height+i] = picture[i][j];
      		img2 = toolkit.createImage(new MemoryImageSource(height, width, pixels, 0, height));
      		break;
      	//image is in its original orientation
      	default:
      		for(int i=0; i<height; i++)
      			for(int j=0; j<width; j++)
      				pixels[i*width+j] = picture[i][j];
      		img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width));
      		break;
      	}
      JLabel label2 = new JLabel(new ImageIcon(img2));    
       mp.removeAll();
       mp.add(label2);
       mp.revalidate();
       mp.repaint();
   
    }
    /*
     * This method takes a single integer value and breaks it down doing bit manipulation to 4 individual int values for A, R, G, and B values
     */
  private int [] getPixelArray(int pixel)
  {
      int temp[] = new int[4];
      temp[0] = (pixel >> 24) & 0xff;
      temp[1]   = (pixel >> 16) & 0xff;
      temp[2] = (pixel >>  8) & 0xff;
      temp[3]  = (pixel      ) & 0xff;
      return temp;
      
    }
    /*
     * This method takes an array of size 4 and combines the first 8 bits of each to create one integer. 
     */
  private int getPixels(int rgb[])
  {
         int alpha = 0;
         int rgba = (rgb[0] << 24) | (rgb[1] <<16) | (rgb[2] << 8) | rgb[3];
        return rgba;
  }
  
  public void getValue()
  {
      int pix = picture[colorY][colorX];
      int temp[] = getPixelArray(pix);
      System.out.println("Color value " + temp[0] + " " + temp[1] + " "+ temp[2] + " " + temp[3]);
    }
  
  /**************************************************************************************************
   * This is where you will put your methods. Every method below is called when the corresponding pulldown menu is 
   * used. As long as you have a picture open first the when your fun1, fun2, fun....etc method is called you will 
   * have a 2D array called picture that is holding each pixel from your picture. 
   *************************************************************************************************/
   /*
    * Example function that just removes all red values from the picture. 
    * Each pixel value in picture[i][j] holds an integer value. You need to send that pixel to getPixelArray the method which will return a 4 element array 
    * that holds A,R,G,B values. Ignore [0], that's the Alpha channel which is transparency, we won't be using that, but you can on your own.
    * getPixelArray will breaks down your single int to 4 ints so you can manipulate the values for each level of R, G, B. 
    * After you make changes and do your calculations to your pixel values the getPixels method will put the 4 values in your ARGB array back into a single
    * integer value so you can give it back to the program and display the new picture. 
    */
  private void fun1()
  {
     
    for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
       {   
          int rgbArray[] = new int[4];
         
          //get three ints for R, G and B
          rgbArray = getPixelArray(picture[i][j]);
         
        
           rgbArray[1] = 0;
           //take three ints for R, G, B and put them back into a single int
           picture[i][j] = getPixels(rgbArray);
        } 
     resetPicture();
  }
  
  private void grayScale()
  {
    for(int i=0; i<height; i++){
        for(int j=0; j<width; j++)
        {   
           int rgbArray[] = new int[4];
           int lumosity = 0;
          
           // get three ints for R, G and B
           rgbArray = getPixelArray(picture[i][j]);
           
           // calculates the lumosity for current pixel
           lumosity = (int) Math.round(0.21 * rgbArray[1] + 0.72 * rgbArray[2] + 0.07 * rgbArray[3]);
            
           // Sets the R, G, and B values to the lumosity
           rgbArray[1] = lumosity;
           rgbArray[2] = lumosity;
           rgbArray[3] = lumosity;
            
           //sets the pixel equal to the new values for R, G, and B
           picture[i][j] = getPixels(rgbArray);
        }
  }
	resetPicture();
}

  private void rotate(){
	  //increments the orientation
	  orientation++;
	  orientation = orientation%4;
	  
	  resetPicture();
  }  
  

  private void edge()
  {
	  //turns the image grayscale
      grayScale();
      int[][] mask ={
          {1,	1,	1,	1,	1},
          {1,	0,	0,	0,	1},
          {1,	0,	-24,	0,	1},
          {1,	0,	0,	0,	1},
          {1,	1,	1,	1,	1}   
      };
      
      for (int y = 0;y<height-1;y++){
          for(int x = 0;x<width-1;x++){
              
              int rgbArray[] = new int[4];
              rgbArray = getPixelArray(picture[y][x]);

              int[][] local = new int[5][5];
              for(int i=-2;i<=2;i++){
                  for(int j=-2;j<=2;j++){
                	  //double checks to make sure the current pixel isn't outside of the array
                	  try{
                		  local[i][j]= picture[(x+i)][(y+j)];
                	  }catch(Exception e){
                		  continue;
                	  }
                  }
              }
              
              for(int i=0;i<5;i++){
                  for(int j=0;j<5;j++){
                      local[i][j]=local[i][j]*mask[i][j];
                      picture[i][j] = local[i][j];
                  }
              }
          }    
      }
      resetPicture();
  }
  
  private void histogram(){
	  //This is in my histogram function in IMP
		  
	  //first count all pixel values in R and G and B array
		  
	  // Then pass those arrays to MyPanel constructor
		  
	  //Then when button is pushed call drawHistogram in MyPanel.....you write DrawHistogram
		
	  //Don't forget to call repaint();

	  int[] red = new int[256];
	  int[] green = new int[256];
	  int[] blue = new int [256];
	  Arrays.fill(red, 0);
	  Arrays.fill(green, 0);
	  Arrays.fill(blue, 0);
	  int[] rgbArray = new int[4];
	  for(int i=0; i<height; i++){
	        for(int j=0; j<width; j++){ 
	        	rgbArray = getPixelArray(picture[i][j]);
	        	red[rgbArray[1]]++;
	        	green[rgbArray[2]]++;
	        	blue[rgbArray[3]]++;
	        }
	  }
	  JFrame redFrame = new JFrame("Red");
	  redFrame.setSize(305, 600);
	  redFrame.setLocation(500, 0);
	  JFrame greenFrame = new JFrame("Green");
	  greenFrame.setSize(305, 600);
	  greenFrame.setLocation(800, 0);
	  JFrame blueFrame = new JFrame("blue");
	  blueFrame.setSize(305, 600);
	  blueFrame.setLocation(1100, 0);
	  MyPanel redPanel = new MyPanel(red);
	  MyPanel greenPanel = new MyPanel(green);
	  MyPanel bluePanel = new MyPanel(blue);
	  redFrame.getContentPane().add(redPanel, BorderLayout.CENTER);
	  redFrame.setVisible(true);
	  greenFrame.getContentPane().add(greenPanel, BorderLayout.CENTER);
	  greenFrame.setVisible(true);
	  blueFrame.getContentPane().add(bluePanel, BorderLayout.CENTER);
	  blueFrame.setVisible(true);
	  start.setEnabled(true);
  }
  
  private void equalize(){
	  
  }
        
  private void blur(){
	  //initializing new array to store blurred pixels
	  int blurredPic[][] = new int[height][width];
	  //increments through each pixel of picture
	  for(int i=0; i<height; i++){
		  for(int j=0; j<width; j++){
			  int rgb[] = new int[4];
			  rgb = getPixelArray(picture[i][j]);
			  int redAvg = 0;
			  int greenAvg = 0;
			  int blueAvg = 0;
			  //Increments through the the surrounding pixels, including the current pixel.
			  //If they are inside the array it will get the rgb values and add them to the average.
			  for(int x=-1; x<=1; x++){
				  for(int y=-1; y<=1; y++){
			          int rgbArray[] = new int[4];
			          if((i+x)>=0&&(i+x)<height&&(j+y)>=0&&(j+y)<width){
			          		rgbArray = getPixelArray(picture[i+x][j+y]);
			          		redAvg+=rgbArray[1];
			          		greenAvg+=rgbArray[2];
			          		blueAvg+=rgbArray[3];
			          }
				  }
			  }
			  //takes the sum of the rgb values divides them by 
			  rgb[1] = redAvg/9;
			  rgb[2] = greenAvg/9;
			  rgb[3] = blueAvg/9;
			  //sets the current pixels rgb values to the average of the surrounding rgb values.
			  blurredPic[i][j] = getPixels(rgb);
		  }
	  }
	  //sets the picture equal to the blurred image
	  picture = blurredPic;
	  resetPicture();
  }
  
  private void quit()
  {  
     System.exit(0);
  }

    @Override
   public void mouseEntered(MouseEvent m){}
    @Override
   public void mouseExited(MouseEvent m){}
    @Override
   public void mouseClicked(MouseEvent m){
        colorX = m.getX();
        colorY = m.getY();
        System.out.println(colorX + "  " + colorY);
        getValue();
        start.setEnabled(true);
    }
    @Override
   public void mousePressed(MouseEvent m){}
    @Override
   public void mouseReleased(MouseEvent m){}
   
   public static void main(String [] args)
   {
      IMP imp = new IMP();
   }
}