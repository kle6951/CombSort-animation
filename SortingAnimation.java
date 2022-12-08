import java.awt.Color;
import java.util.Random;

import org.piccolo2d.activities.PActivity;
import org.piccolo2d.nodes.PText;

/***
 *
 * @author Khanh Le
 * @author Ben Cacic
 * @author Mohammed Arab
 */

public class SortingAnimation extends AnimationScreen {
    private static final long serialVersionUID = 1L;

    private static final int totalWidth = 1000;
    private static final int totalHeight = 600;
    private static final int arrSize = 12;

    private static PText header;
    private static TextBoxNode[] myTextBoxes = new TextBoxNode[12];
    static Random random = new Random();

    @SuppressWarnings("unused")
    private static PText indexText;
    private static PText gapText;
    private static PText gap;
    private static PText nextGap;
    private static PText allSortText;
    static int point;
    private static int x_scale = 10;
    private static int animationSpeed = 1000;
    private static double lowerYPos = 150;
    private static double higherYPos = 60;

    private final static int[] unsortedArr = random.ints(12, -1000, 1000).toArray();

    @Override
    public void addInitialNodes() {
        this.setBounds(0, 0, totalWidth, totalHeight);

        // add background box
        addColouredBox(0, 0, totalWidth, totalHeight, Color.PINK);

        // add header text
        header = addText(0, 0, "CombSort Animation!");
        header.setTextPaint(Color.BLACK);
        x_scale = 10;

        for (int i = 0; i < arrSize; i++) {

            Integer elArr = unsortedArr[i];
            String text = elArr.toString();
            myTextBoxes[i] = addTextBox(0, 0, 50, 50, text);
            myTextBoxes[i].animateToPositionScaleRotation(getXPos(i), higherYPos, 1.0, 0, 2000);

            myTextBoxes[i].setPaint(Color.white);
            myTextBoxes[i].setTextPaint(Color.black);

        }

        x_scale = 50;
        for (int i = 0; i < arrSize; i++) {
            Integer index = i;
            String numIndex = index.toString();
            indexText = addText(x_scale, 35, numIndex);
            point = x_scale;
            x_scale += 75;
        }

        gapText = addText(-50, 200, "Gap Value: ");
        CombSort sort = new CombSort();
        Integer gapInt = sort.getNextGap(arrSize);
        String gapString = gapInt.toString();
        gap = addText(-50, 200, gapString);

        nextGap = addText(-50, 300, "");

        allSortText = addText(-200, 450, "THE ARRAY IS ALL SORTED !!!!");

    }

    private static void swap(int startingIndex, int endingIndex) {

        waitForActivity(myTextBoxes[startingIndex].animateToPositionScaleRotation(getXPos(startingIndex), lowerYPos,
                1.0, 0, animationSpeed));
        waitForActivity(myTextBoxes[endingIndex].animateToPositionScaleRotation(getXPos(endingIndex), lowerYPos, 1.0, 0,
                animationSpeed));

        waitForActivity(myTextBoxes[startingIndex].animateToPositionScaleRotation(getXPos(endingIndex), higherYPos, 1.0,
                0, animationSpeed));
        myTextBoxes[startingIndex].setPaint(Color.blue);
        myTextBoxes[startingIndex].setTextPaint(Color.WHITE);

        waitForActivity(myTextBoxes[endingIndex].animateToPositionScaleRotation(getXPos(startingIndex), higherYPos, 1.0,
                0, animationSpeed));
        myTextBoxes[endingIndex].setPaint(Color.blue);
        myTextBoxes[endingIndex].setTextPaint(Color.WHITE);

        int temp = unsortedArr[startingIndex];
        unsortedArr[startingIndex] = unsortedArr[endingIndex];
        unsortedArr[endingIndex] = temp;

        TextBoxNode tempBox = myTextBoxes[startingIndex];
        myTextBoxes[startingIndex] = myTextBoxes[endingIndex];
        myTextBoxes[endingIndex] = tempBox;
    }

    private static void changingLayout(int startingIndex, int endingIndex) {
        waitForActivity(myTextBoxes[startingIndex].animateToPositionScaleRotation(getXPos(startingIndex), lowerYPos,
                1.0, 0, animationSpeed));
        myTextBoxes[startingIndex].setPaint(Color.black);
        myTextBoxes[startingIndex].setTextPaint(Color.WHITE);
        waitForActivity(myTextBoxes[endingIndex].animateToPositionScaleRotation(getXPos(endingIndex), lowerYPos, 1.0, 0,
                animationSpeed));
        myTextBoxes[endingIndex].setPaint(Color.black);
        myTextBoxes[endingIndex].setTextPaint(Color.WHITE);
        waitForActivity(myTextBoxes[startingIndex].animateToPositionScaleRotation(getXPos(startingIndex), higherYPos,
                1.0, 0, animationSpeed));
        myTextBoxes[startingIndex].setPaint(Color.WHITE);
        myTextBoxes[startingIndex].setTextPaint(Color.black);
        waitForActivity(myTextBoxes[endingIndex].animateToPositionScaleRotation(getXPos(endingIndex), higherYPos, 1.0,
                0, animationSpeed));
        myTextBoxes[endingIndex].setPaint(Color.WHITE);
        myTextBoxes[endingIndex].setTextPaint(Color.black);

    }

    public static void main(String[] args) {
        SortingAnimation screen = new SortingAnimation();
//        int gapValue = arrSize;
        int gapValue = arrSize;
        CombSort sort = new CombSort();
        boolean swapped = true;

        // wait for initialization before animating
        screen.waitForInitialization();
        header.animateToPositionScaleRotation(0, 0, 2.0, 0, 1500);
        gapText.animateToPositionScaleRotation(50, 300, 2.0, 0, 1500);
        gap.animateToPositionScaleRotation(250, 300, 2.0, 0, 1500);

//        gapValue = sort.getNextGap(gapValue);

        while (gapValue != 1 || swapped == true) {
            // Find next gap
            gapValue = sort.getNextGap(gapValue);

            // Initialize swapped as false so that we can
            // check if swap happened or not
            swapped = false;

            // Compare all elements with current gap
            for (int i = 0; i < arrSize - gapValue; i++) {
                if (unsortedArr[i] <= unsortedArr[i + gapValue]) {
                    changingLayout(i, i + gapValue);
                }
                if (unsortedArr[i] > unsortedArr[i + gapValue]) {
                    // Swap arr[i] and arr[i+gap]
                    swap(i, i + gapValue);
                    // Set swapped
                    swapped = true;
                }
            }
            gap.animateToPositionScaleRotation(gap.getX(), -1000, 1.0, 0, animationSpeed);
            Integer gapInt = sort.getNextGap(gapValue);
            String gapString = gapInt.toString();
            nextGap.setText(gapString);
            waitForActivity(nextGap.animateToPositionScaleRotation(250, 300, 2.0, 0, 1500));
        }
        for (int i = 0; i < arrSize; i++) {
            myTextBoxes[i].setPaint(Color.BLUE);
            myTextBoxes[i].setTextPaint(Color.WHITE);
        }
        waitForActivity(allSortText.animateToPositionScaleRotation(50, 450, 3.0, 0, animationSpeed));
    }

    private static double getXPos(int index) {
        return (75 * index) + 25;
    }

    private static void waitForActivity(PActivity activity) {
        long endTime = activity.getStartTime() + activity.getDuration();
        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // Whatever, I do what I want.
            }
        }
    }
}