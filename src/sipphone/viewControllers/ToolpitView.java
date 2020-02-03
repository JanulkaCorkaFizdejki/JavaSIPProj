package sipphone.viewControllers;

import javafx.scene.control.Tooltip;

public class ToolpitView {

    public static Tooltip tooltip (String textToolpit) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(textToolpit);

        return tooltip;
    }
}
