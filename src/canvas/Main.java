package canvas;

public class Main{
    public static void main(String[] args){
        CanvasViewInt canvas = new CanvasView();
        CanvasModelInt model = new CanvasModel();
        CanvasControllerInt controller = new CanvasController(model,canvas);

        canvas.setCanvasController(controller);
        // canvas.CanvasAreaView();
    }


}

