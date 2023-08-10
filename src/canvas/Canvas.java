package canvas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Dimension;
import java.awt.Rectangle;






public class Canvas implements MouseMotionListener{
    
    //Create the window frame:
    JFrame frame = new JFrame();

    //Create the things that will be on the window:
    JPanel canvasPanel, toolbar;
    JButton btn,backColor;
    //Gives the color and size of the brush
    JTextField color,size;
    String prev,background;

    // This creates the Canvas itself.
    Canvas(){
        canvasPanel = new JPanel();
        canvasPanel.addMouseMotionListener(this);

    }

    


    //Creates the General Set up of the Buttons:
    public void addButton(int x, int y, String col){
        this.btn = new JButton();
        btn.setBounds(x,y,50,50);
        btn.setBackground(Color.decode(col));
        //This gets the color at where we clicked
        btn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                color.setText(col);
                System.out.println("we are in action performed, the color now is" + color.getText());
            }
        });

        //This will add the button to the toolbar each time we create the button
        toolbar.add(btn);
    }

    //View
    public void CanvasView(){

        //Set Up the Painting Area Layout---------------------------------

        //Create the size of the Painting Area
        canvasPanel.setBounds(320,30,850,600);

        //Set the Background Color of the Painting Area
        canvasPanel.setBackground(Color.white);

        //Update the Painting Area
        canvasPanel.setLayout(null);

        //Add the Painting Area to the window
        frame.add(canvasPanel);

        //Add the Controller Part of the Canvas:---------------------------
        canvasPanel.addMouseListener(new MouseAdapter() {
            @Override
            //This is the function that will be called when the mouse is clicked on the canvas panel
            public void mouseClicked(MouseEvent e){
                //This will get the color and size of the brush
                String cl = color.getText();
                int sz = Integer.parseInt(size.getText());
                //This will create the brush
                Graphics graph = canvasPanel.getGraphics();
                //This will set the color of the brush
                graph.setColor(Color.decode(cl));
                //This will draw the brush on the canvas
                graph.fillRect(e.getX(), e.getY(), sz, sz);
            }
        });
        //Set Up the Tool bar Layout--------------------------------------
        toolbar = new JPanel();
        toolbar.setBounds(30, 30, 250, 600);
        toolbar.setBackground(Color.white);
        toolbar.setLayout(null);

        //Add the Tool Bar to the window
        frame.add(toolbar);
        
        //left: x closer to 0
        //right: x closer to 250
        //top: y closer to 0
        //bottom: y closer to 600
        //Add toolbar buttons: 
        addButton(32, 30, "#000000");
		addButton(105, 30, "#FFFFFF");
		addButton(177, 30, "#808080");
		addButton(32, 90, "#FF0000");
		addButton(105, 90, "#00FF00");
		addButton(177, 90, "#0000FF");
		addButton(32, 150, "#FFFF00");
		addButton(105, 150, "#FFA500");
		addButton(177, 150, "#A020F0");
		addButton(32, 210, "#FFC0CB");
		addButton(105, 210, "#964B00");
        addButton(177, 210, "#C32148");

        //Add a Custom Color Selector:
        JLabel colorLabel = new JLabel("Custom Color:");
        colorLabel.setBounds(30,260,100,50);
        toolbar.add(colorLabel);
        color = new JTextField();
        color.setBounds(30,310,190,30);
        color.setText("#000000");
        toolbar.add(color);
        
        //Set Background Color:
        background = "#FFFFFF";

        //Create Background Button
        backColor = new JButton("Set Background Color");
        backColor.setBounds(30, 350, 190, 30);
        toolbar.add(backColor);

        //This is the actions that will be performed when the button is clicked
        backColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                background = color.getText();
                canvasPanel.setBackground(Color.decode(background));
            }
        });
        //Create Zoom and Minimize Feature:
        JLabel sizeLabel = new JLabel("Size");
        sizeLabel.setBounds(30,380,100,50);
        toolbar.add(sizeLabel);

        //Add the decrease size button
        JButton subtract = new JButton("-");
        subtract.setBounds(30,415,50,30);
        toolbar.add(subtract);

        JButton add = new JButton("+");
        add.setBounds(170 ,415,50,30);
        toolbar.add(add);

        //Add the Size input text box
        size = new JTextField();
        size.setBounds(100,415,50,30);
        size.setText("5");
        toolbar.add(size);

        //Add the actions of the subtract button:
        subtract.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                if (Integer.parseInt(size.getText()) > 2){
                   size.setText(Integer.parseInt(size.getText())-1+"");  
                }else{
                    size.setText("1");
                }
                
            }
        });

        //Add the actions of the add button:
         add.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                size.setText(Integer.parseInt(size.getText())+1+""); 
            }
        });

        //Paint and Erase Feature:
        //Add the brush button
        JButton brush = new JButton("Brush");
        brush.setBounds(30,465,80,30);
        toolbar.add(brush);

        //Add the erase button
        JButton erase =  new JButton("Erase");
        erase.setBounds(140,465,80,30);
        toolbar.add(erase);
        
        prev = "#000000";

        //Add the actions of the brush button:
        brush.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                color.setText(prev);
            }
        });

        //Add the actions of the erase button:
        erase.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){


                //Save the previous color so when we switch back to brush, we can continue from the last color
                if(!color.getText().equals(background)){
                    prev = color.getText();
                }
                //Have the eraser be the same color of the background so it gives a eraser effect
                color.setText(background);
            }

        });
        //Clear Canvas Feature:

        //Add the clear Button
        JButton clear = new JButton("Clear");
        clear.setBounds(30,515,190,30);
        toolbar.add(clear);

        //Add the actions of the clear button:
        clear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                canvasPanel.removeAll();
                canvasPanel.repaint();
            }
        });

        //Save Canvas Feature:
        //Add the save button
        JButton save = new JButton("Save");
        save.setBounds(30, 555, 190, 30);
        toolbar.add(save);
        
        //Add the actions of the save button:
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String time = java.time.LocalTime.now().toString();
                time = time.substring(0,2) + time.substring(3,5) + time.substring(6,8) + time.substring(9,12) + ".png";
                try{
                    BufferedImage image = getImage(canvasPanel);
                    ImageIO.write(image,"png",new File(time));
                }catch(Exception ex){
                    ex.printStackTrace();
                }

            }

        });

        //Set Up Frame Properties:This is needed to display the window
        frame.setSize(1200,700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#001122"));
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    //Saving Image Functions
    public BufferedImage getImage(Component comp) throws AWTException {
	    Point p = comp.getLocationOnScreen();
	    Dimension dim = comp.getSize();
	    BufferedImage captureImage = new Robot().createScreenCapture(new Rectangle(p, dim));
	    return captureImage;
	}

    //Mouse Functions:
    //Needs to implement abstract functions of MouseMotionListener
        @Override
        public  void mouseDragged(MouseEvent e){
            String writeColor = color.getText();
            int writeSize = Integer.parseInt(size.getText());
            Graphics write = canvasPanel.getGraphics();
            write.setColor(Color.decode(writeColor));
            write.fillRect(e.getX(), e.getY(), writeSize, writeSize);
        }

        @Override
        public void mouseMoved(MouseEvent e){

        }

}
