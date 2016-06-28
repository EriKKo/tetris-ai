package vision;

import java.awt.*;

public class VisionUtil {

    static int colorDist(Color c1, Color c2) {
        int dr = c1.getRed() - c2.getRed();
        int dg = c1.getGreen() - c2.getGreen();
        int db = c1.getBlue() - c2.getBlue();
        return dr*dr + dg*dg + db*db;
    }
}
