package canvas;

public final class CanvasModel implements CanvasModelInt{
    
    private int size;


    //Sets the size for the Brush Size
    @Override
    public void setSize(int size){
            System.out.println("We are in setSize");
            this.size = size;
    }

    public CanvasModel(){
        System.out.println("We are in CanvasModel");
        this.size = 5;

    }

    @Override
    public int size(){
        System.out.println("We are in size ");

      
        
        return this.size;
    }

}

