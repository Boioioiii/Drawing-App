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


public final class CanvasView extends JFrame implements MouseMotionListener, CanvasViewInt{
    
    private CanvasControllerInt controller;
    private JButton subtract,add,brush,erase,clear,save;
    private JTextField color,size;
    private int JFrameSize;
    //Create the window frame:
   // JFrame frame = new JFrame();

    JButton btn,backColor;
    //Create the things that will be on the window:
    JPanel canvasPanel, toolbar;
    //Gives the color and size of the brush
    //JTextField color,size;
    String prev,background;

   

    @Override
    public void setCanvasController(CanvasControllerInt controller){
    this.controller = controller;
    }

    //Creates the General Set up of the Buttons:
    public void addButton(int x, int y, String col){
        this.btn = new JButton();
        this.btn.setBounds(x,y,50,50);
        this.btn.setBackground(Color.decode(col));
        //this.btn.addActionListener(this);

        //Not adding this with the other addActionLister/actionPerformed because of the need for the col variable
        // This gets the color at where we clicked
        this.btn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                color.setText(col);
            }
        });

        // This will add the button to the toolbar each time we create the button
        toolbar.add(this.btn);
    }

    //View
    public CanvasView(){

        this.canvasPanel = new JPanel();
        this.canvasPanel.addMouseMotionListener(this);

        //Set Up the Painting Area Layout---------------------------------

        //Create the size of the Painting Area
        this.canvasPanel.setBounds(320,30,850,600);

        //Set the Background Color of the Painting Area
        this.canvasPanel.setBackground(Color.white);

        //Update the Painting Area
        this.canvasPanel.setLayout(null);

        //Add the Painting Area to the window
        this.add(this.canvasPanel);

    //     //Add the Controller Part of the Canvas:---------------------------
        canvasPanel.addMouseListener(this);
        //Set Up the Tool bar Layout--------------------------------------
        toolbar = new JPanel();
        toolbar.setBounds(30, 30, 250, 600);
        toolbar.setBackground(Color.white);
        toolbar.setLayout(null);

        //Add the Tool Bar to the window
        this.add(toolbar);
        
    //     //left: x closer to 0
    //     //right: x closer to 250
    //     //top: y closer to 0
    //     //bottom: y closer to 600
    //     //Add toolbar buttons: 
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

    //     //Add a Custom Color Selector:
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

        //Create Zoom and Minimize Feature:
        JLabel sizeLabel = new JLabel("Size");
        sizeLabel.setBounds(30,380,100,50);
        toolbar.add(sizeLabel);

        //Add the decrease size button
        //JButton subtract = new JButton("-");
        this.subtract = new JButton("-");
        this.subtract.setBounds(30,415,50,30);
        toolbar.add(this.subtract);

        this.add = new JButton("+");
        this.add.setBounds(170 ,415,50,30);
        toolbar.add(this.add);

        //Add the Size input text box
        this.size = new JTextField();
        this.size.setBounds(100,415,50,30);
        size.setText("5");
        toolbar.add(this.size);

        //Add the actions of the decrease size button:
        this.subtract.addActionListener(this);

        //Add the actions of the add button:
        this.add.addActionListener(this);

        //Paint and Erase Feature:
        //Add the brush button
        this.brush = new JButton("Brush");
        this.brush.setBounds(30,465,80,30);
        toolbar.add(this.brush);

        //Add the erase button
        this.erase =  new JButton("Erase");
        this.erase.setBounds(140,465,80,30);
        toolbar.add(this.erase);
        
        prev = "#000000";

        //Add the actions of the brush button:
        this.brush.addActionListener(this);

        //Add the actions of the erase button:
        this.erase.addActionListener(this);

        //Clear Canvas Feature:
        //Add the clear Button
        this.clear = new JButton("Clear");
        this.clear.setBounds(30,515,190,30);
        toolbar.add(this.clear);

    //     //Add the actions of the clear button:
        this.clear.addActionListener(this);
   
        //Save Canvas Feature:
        //Add the save button
        this.save = new JButton("Save");
        this.save.setBounds(30, 555, 190, 30);
        toolbar.add(this.save);
        
    //     //Add the actions of the save button:
        this.save.addActionListener(this);
    

        //Set Up Frame Properties:This is needed to display the window
        this.setSize(1200,700);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.decode("#001122"));
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent event){
        Object source = event.getSource();
        if(source == this.subtract){
            this.JFrameSize = Integer.parseInt(size.getText());
            this.controller.processSubtract();
        }else if(source == this.add){
            this.JFrameSize = Integer.parseInt(size.getText());
            this.controller.processAdd();;
            
        }else if (source == this.brush){
            color.setText(prev);
        }else if (source == this.erase){
            if(!color.getText().equals(background)){
                prev = color.getText();
            }
            //Have the eraser be the same color of the background so it gives a eraser effect
            color.setText(background);
        }else if (source == this.clear){
            canvasPanel.removeAll();
            canvasPanel.repaint();
        }else if (source == this.save){
            String time = java.time.LocalTime.now().toString();
            time = time.substring(0,2) + time.substring(3,5) + time.substring(6,8) + time.substring(9,12) + ".png";
            try{
                BufferedImage image = getImage(canvasPanel);
                ImageIO.write(image,"png",new File(time));
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

    }

    @Override
    public void updateSize(int s){
            this.size.setText(s+"");
    }
   
    //This will add dots to where the brush touches on the canvas. So when user clicks and not drags,then a dot is left behind
    @Override
    public void mouseClicked(MouseEvent event){
        //This will get the color and size of the brush
        String cl = color.getText();
        int sz = Integer.parseInt(size.getText());
        //This will create the brush
        Graphics graph = canvasPanel.getGraphics();
        //This will set the color of the brush
        graph.setColor(Color.decode(cl));
        //This will draw the brush on the canvas
        graph.fillRect(event.getX(), event.getY(), sz, sz);
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

        //Saving Image Functions
        public BufferedImage getImage(Component comp) throws AWTException {
            Point p = comp.getLocationOnScreen();
            Dimension dim = comp.getSize();
            BufferedImage captureImage = new Robot().createScreenCapture(new Rectangle(p, dim));
            return captureImage;
	    }


        @Override
        public void mouseMoved(MouseEvent e){

        }

        @Override
        public void mousePressed(MouseEvent e){

        }

        @Override
        public void mouseReleased(MouseEvent e){

        }

        @Override
        public void mouseEntered(MouseEvent e){

        }

        @Override
        public void mouseExited(MouseEvent e){

        }




}
