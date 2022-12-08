import java.awt.Paint;
import java.awt.Shape;

import org.piccolo2d.nodes.PPath;
import org.piccolo2d.nodes.PText;
import org.piccolo2d.util.PBounds;

public class TextBoxNode extends PPath.Float {
    private static final long serialVersionUID = 1L;

    private PText nodeLabel;

    public TextBoxNode(Shape shape, String newText) {
        super(shape);

        nodeLabel = new PText(newText);
        nodeLabel.setPickable(false);

        this.addChild(nodeLabel);

        centerText();
    }

    public Paint getTextPaint() {
        return nodeLabel.getTextPaint();
    }

    public void setTextPaint(Paint textPaint) {
        nodeLabel.setTextPaint(textPaint);
    }

    public String getText() {
        return nodeLabel.getText();
    }

    public void setText(String newText) {
        nodeLabel.setText(newText);
        centerText();
    }

    private void centerText() {
        PBounds ourBounds = this.getBounds();
        nodeLabel.centerBoundsOnPoint(ourBounds.getCenterX(), ourBounds.getCenterY());
    }

}
