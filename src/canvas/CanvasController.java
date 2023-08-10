package canvas;

public final class CanvasController implements CanvasControllerInt {
    
    private CanvasModelInt model;
    private CanvasViewInt view;



    private static void UpdateViewToMatchModel(CanvasModelInt model, CanvasViewInt view){
        // int size = model.size();
        view.updateSize(model.size());
        
    }   

    @Override
    public void processSubtract(){
        System.out.println("We are in processSubtract()");
        
        int size = this.model.size();
        // // Need Size
        if (size > 2){
            this.model.setSize(size - 1);
        }else{
            this.model.setSize(1);
        }
        UpdateViewToMatchModel(this.model, this.view);
    }

    public void processAdd(){
        System.out.println("We are in processAdd()");
        int size = this.model.size();
        this.model.setSize(size + 1);    
        UpdateViewToMatchModel(this.model, this.view);
    }



    public CanvasController(CanvasModelInt model, CanvasViewInt view){
        this.model = model;
        this.view = view;
        UpdateViewToMatchModel(this.model, this.view);
    }

}


