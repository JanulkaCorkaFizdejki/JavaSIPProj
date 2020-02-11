package sipphone.viewControllers;

import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import sipphone.settings.AppColors;

public class ToolpitView {

    public static Tooltip tooltip (String textToolpit) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(textToolpit);
        tooltip.setShowDelay(Duration.millis(200));

        return tooltip;
    }

    public static Tooltip tooltipErr (String textToolpit) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(textToolpit);
        tooltip.setShowDelay(Duration.millis(0));
        tooltip.setShowDuration(Duration.seconds(60));
        tooltip.setStyle("-fx-background-color: "+ AppColors.getColor(AppColors.Colors.red) +"");
        return tooltip;
    }
}
