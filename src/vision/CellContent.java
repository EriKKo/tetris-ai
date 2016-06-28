package vision;

import java.awt.*;

public enum CellContent {

    EMPTY(new Color(-15066598), "EMPTY", false),
    EMPTY2(new Color(43,43,43), "EMPTY", false),
    EMPTY3(new Color(47,47,47), "EMPTY", false),
    UNBREAKABLE(new Color(153,153,153), "STONE", true),
    J_MINO(new Color(-14597690), "J-MINO", true),
    Z_MINO(new Color(-2683081), "Z-MINO", true),
    L_MINO(new Color(-1877246), "L-MINO", true),
    S_MINO(new Color(-10899199), "S-MINO", true),
    T_MINO(new Color(-5297782), "T-MINO", true),
    O_MINO(new Color(-1859838), "O-MINO", true),
    I_MINO(new Color(-15754281), "I-MINO", true),
    ACTIVE_L_MINO(new Color(255, 126, 37), "L-MINO-A", false),
    ACTIVE_S_MINO(new Color(124,212,36), "S-MINO-A", false),
    ACTIVE_O_MINO(new Color(255,194,37), "O-MINO-A", false),
    ACTIVE_I_MINO(new Color(50,190,250), "I-MINO-A", false),
    ACTIVE_Z_MINO(new Color(250,50,90), "Z-MINO-A", false),
    ACTIVE_T_MINO(new Color(210,76,173), "T-MINO-A", false),
    ACTIVE_J_MINO(new Color(68,100,233), "J-MINO-A", false);

    final Color color;
    final String name;
    final boolean fixed;

    CellContent(Color color, String name, boolean fixed) {
        this.color = color;
        this.name = name;
        this.fixed = fixed;
    }

    public String toString() {
        return name;
    }

    public boolean isFixed() {
        return fixed;
    }

    public boolean isMino() {
        return !(this == EMPTY || this == EMPTY2 || this == EMPTY3 || this == UNBREAKABLE);
    }

    static CellContent getContentFromColor(Color color) {
        CellContent res = EMPTY;
        for (CellContent content : values()) {
            if (VisionUtil.colorDist(color, content.color) < VisionUtil.colorDist(color, res.color)) {
                res = content;
            }
        }
        return res;
    }
}
