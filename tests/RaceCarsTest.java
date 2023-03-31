import main.Main;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.launcher.ApplicationLauncher;
import org.junit.*;
import org.junit.runners.MethodSorters;

import javax.swing.*;

import static org.assertj.swing.finder.WindowFinder.findFrame;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RaceCarsTest {

    private final Robot robot = BasicRobot.robotWithNewAwtHierarchy();
    private FrameFixture window;

    @Before
    public void setUp(){
        // set the path of Text File
        Main.path = "tests/test.txt";

        // start the application
        ApplicationLauncher.application(Main.class).start();

        // find the frame for the Frame Fixture
        window = findCurrentFrame("Menu");
    }

    @After
    public void tearDown(){
        window.cleanUp();
    }

    @Test
    public void A_enterValidInputs(){

        System.out.println("Race Cars Tests : ");


        window.button("Race Cars").click();
        window = findCurrentFrame("Race Cars");
        window.comboBox("combo1").selectItem(2);
        window.comboBox("combo2").selectItem(3);

        window.textBox("input").setText("4000");

        window.button("race").click();

        // this race should be won by the value at index 3
        String expectedText = "Winner is : Ram 1500 2023";
        String actualText = window.optionPane().label("OptionPane.label").text();

        window.optionPane().click();

        if (expectedText.equals(actualText)){
            Assert.assertEquals(expectedText, actualText);
            System.out.println("Test 1 : Passed");
        }
        else
            Assert.fail();



    } // Car 1 wins

    @Test
    public void B_raceSameCar(){
        window.button("Race Cars").click();
        window = findCurrentFrame("Race Cars");
        window.comboBox("combo1").selectItem(3);
        window.comboBox("combo2").selectItem(3);

        window.textBox("input").setText("4000");

        window.button("race").click();

        // this race should be won by the value at index 3
        String expectedText = "Can't race the same car !";
        String actualText = window.optionPane().label("OptionPane.label").text();

        window.optionPane().click();

        if (expectedText.equals(actualText)){
            Assert.assertEquals(expectedText, actualText);
            System.out.println("Test 2 : Passed");
        }
        else
            Assert.fail();
    } // throws JOptionPane

    @Test
    public void C_invalidMetres(){
        window.button("Race Cars").click();
        window = findCurrentFrame("Race Cars");
        window.comboBox("combo1").selectItem(3);
        window.comboBox("combo2").selectItem(2);

        window.textBox("input").setText("40000");

        window.button("race").click();

        // this race should be won by the value at index 3
        String expectedText = "Distance must be between 1 and 1000 metres";
        String actualText = window.optionPane().label("OptionPane.label").text();

        window.optionPane().click();

        if (actualText.equals(expectedText)){
            Assert.assertEquals(expectedText, actualText);
            System.out.println("Test 3 : Passed");
        }
        else
            Assert.fail();
    } // throws JOptionPane

    @Test
    public void D_raceCarsWithSameAcceleration(){
        window.button("Race Cars").click();
        window = findCurrentFrame("Race Cars");
        window.comboBox("combo1").selectItem("Ford - Mondeo - 2023 - 6.2");
        window.comboBox("combo2").selectItem("Chevrolet - Silverado - 2023 - 6.2");

        window.textBox("input").setText("4000");

        window.button("race").click();

        // this race should be won by the value at index 3
        String expectedText = "Winner is : Draw!";
        String actualText = window.optionPane().label("OptionPane.label").text();

        window.optionPane().click();

        if (actualText.equals(expectedText)){
            Assert.assertEquals(expectedText, actualText);
            System.out.println("Test 4 : Passed");
        }
        else
            Assert.fail();
    } // Cars Draw

    @Test
    public void E_enterMetresZero(){
        window.button("Race Cars").click();
        window = findCurrentFrame("Race Cars");
        window.comboBox("combo1").selectItem(3);
        window.comboBox("combo2").selectItem(2);

        window.textBox("input").setText("0");

        window.button("race").click();

        // this race should be won by the value at index 3
        String expectedText = "Distance must be between 1 and 1000 metres";
        String actualText = window.optionPane().label("OptionPane.label").text();

        window.optionPane().click();

        if (actualText.equals(expectedText)){
            Assert.assertEquals(expectedText, actualText);
            System.out.println("Test 5 : Passed");
        }
        else
            Assert.fail();
    } // throws JOptionPane






    private FrameFixture findCurrentFrame(String title) {
        return findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
            @Override
            protected boolean isMatching(JFrame frame) {
                return title.equals(frame.getTitle()) && frame.isShowing();
            }
        }).using(robot);
    }

}
