import frames.JourneyCost;
import main.Main;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.launcher.ApplicationLauncher;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.assertj.swing.core.Robot;

import javax.swing.*;

import static org.assertj.swing.finder.WindowFinder.findFrame;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CalculateJourneyTest {

    private final Robot robot =  BasicRobot.robotWithNewAwtHierarchy();
    private FrameFixture window;

    @Before
    public void setUp(){
        // change the path of the text file to test
        Main.path = "tests/test.txt";

        // start the application
        ApplicationLauncher.application(Main.class).start();

        // get the first frame
        window = findCurrentFrame("Menu");

    }

    @After
    public void tearDown(){
        // tears down the frames
        window.cleanUp();
    }



    @Test
    public void A_calculateNormalJourney(){

        System.out.println("Calculate Journey Tests : ");

        window.button("Calculate Journey Cost").click();

        window = findCurrentFrame("Journey Cost Calculator");

        window.comboBox().click().selectItem(5);

        window.textBox("numberOfMiles").setText("4000");

        window.textBox("fuelCost").setText("142.2");

        window.button("calculate").click();

        String resultLabel = window.label("result").text();


        // find the value of the label and find calculate the actual journey cost

        // calculating the journey cost
        double mpg = Double.parseDouble(window.textBox("mpgBox").text());
        double miles = Double.parseDouble(window.textBox("numberOfMiles").text());
        double fuelCost = Double.parseDouble(window.textBox("fuelCost").text());

        double expectedResult = calculateCostInPounds(mpg,miles,fuelCost);

        // find the result that that is given in the label
        String actualResultString = resultLabel.substring(28).trim();

        // parse the result String to a double
        double actualResultDouble = Double.parseDouble(actualResultString);

        if (actualResultDouble == expectedResult){
            Assert.assertEquals(actualResultDouble,expectedResult,0);
            System.out.println("Test 1 : Passed");
        }
        else
            Assert.fail();





    }

    @Test
    public void B_enterInvaidMiles(){

        window.button("Calculate Journey Cost").click();

        window = findCurrentFrame("Journey Cost Calculator");

        window.comboBox().click().selectItem(4);


        window.textBox("numberOfMiles").setText("14wert./w?ER<>tpsi000");

        window.textBox("fuelCost").setText("142.2");

        window.button("calculate").click();

        String expectedErrorString = "Invalid Double";
        String actualErrorString = window.optionPane().label("OptionPane.label").text();

        // close the JOPtion pane
        window.optionPane().click();

        if (expectedErrorString.equals(actualErrorString)){
            Assert.assertEquals(expectedErrorString, actualErrorString);
            System.out.println("Test 2 : Passed");
        }
        else
            Assert.fail();
    }

    @Test
    public void C_enterMilesZero(){
        window.button("Calculate Journey Cost").click();

        window = findCurrentFrame("Journey Cost Calculator");

        window.comboBox().click().selectItem(4);


        window.textBox("numberOfMiles").setText("0");

        window.textBox("fuelCost").setText("142.2");

        window.button("calculate").click();

        String expectedErrorString = "Inputs Cant be Zero!";
        String actualErrorString = window.optionPane().label("OptionPane.label").text();

        // close the JOPtion pane
        window.optionPane().click();

        if (expectedErrorString.equals(actualErrorString)){
            Assert.assertEquals(expectedErrorString, actualErrorString);
            System.out.println("Test 3 : Passed");
        }
        else
            Assert.fail();
    }

    @Test
    public void D_enterInvalidMpg(){
        window.button("Calculate Journey Cost").click();

        window = findCurrentFrame("Journey Cost Calculator");

        window.comboBox().click().selectItem(4);


        window.textBox("numberOfMiles").setText("1000");

        window.textBox("fuelCost").setText("a;sidf a.,/2/3 r.ljf dwufidj");

        window.button("calculate").click();

        String expectedErrorString = "Invalid Double";
        String actualErrorString = window.optionPane().label("OptionPane.label").text();

        // close the JOPtion pane
        window.optionPane().click();

        if (expectedErrorString.equals(actualErrorString)){
            Assert.assertEquals(expectedErrorString, actualErrorString);
            System.out.println("Test 4 : Passed");
        }
        else
            Assert.fail();
    }

    @Test
    public void E_enterMpgZero(){
        window.button("Calculate Journey Cost").click();

        window = findCurrentFrame("Journey Cost Calculator");

        window.comboBox().click().selectItem(4);


        window.textBox("numberOfMiles").setText("1000");

        window.textBox("fuelCost").setText("0");

        window.button("calculate").click();

        String expectedErrorString = "Inputs Cant be Zero!";
        String actualErrorString = window.optionPane().label("OptionPane.label").text();

        // close the JOPtion pane
        window.optionPane().click();

        if (expectedErrorString.equals(actualErrorString)){
            Assert.assertEquals(expectedErrorString, actualErrorString);
            System.out.println("Test 5 : Passed");
        }
        else
            Assert.fail();
    }

    @Test
    public void F_enterMpgZeroAndMilesZero(){
        window.button("Calculate Journey Cost").click();

        window = findCurrentFrame("Journey Cost Calculator");

        window.comboBox().click().selectItem(4);


        window.textBox("numberOfMiles").setText("0");

        window.textBox("fuelCost").setText("0");

        window.button("calculate").click();

        String expectedErrorString = "Inputs Cant be Zero!";
        String actualErrorString = window.optionPane().label("OptionPane.label").text();

        // close the JOPtion pane
        window.optionPane().click();

        if (expectedErrorString.equals(actualErrorString)){
            Assert.assertEquals(expectedErrorString, actualErrorString);
            System.out.println("Test 6 : Passed");
        }
        else
            Assert.fail();
    }


    public double calculateCostInPounds(double mpg, double miles, double fuelCost){
        // this just uses maths to calculate the cost of the journey with the selected car and the user inputs
        double gallonsOfFuelNeeded = miles/mpg;

        // this converts the gallon of fuel needed to litres
        // L = gal / 0.21997
        double litersOfFuelNeeded = gallonsOfFuelNeeded / 0.21997;
        double result = (litersOfFuelNeeded * fuelCost) / 100;

        return Math.round(result*100.0)/100.0;

        //returns the value in pounds as 2dp

    }


    private FrameFixture findCurrentFrame(String title) {
        return findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
            @Override
            protected boolean isMatching(JFrame frame) {
                return title.equals(frame.getTitle()) && frame.isShowing();
            }
        }).using(robot);
    }




}
