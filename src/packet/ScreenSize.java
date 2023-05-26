package packet;

import java.awt.*;

//Metodo che ricava la grandezza dello schermo, utilizzato per posizionare i vari frame all'interno dello schermo
public class ScreenSize {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int width = (int) screenSize.getWidth();
    static int height = (int) screenSize.getHeight();

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}
