package canvas;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;


public interface CanvasViewInt extends ActionListener, MouseListener{
    void setCanvasController(CanvasControllerInt canvasController);

    void updateSize(int size);

}
