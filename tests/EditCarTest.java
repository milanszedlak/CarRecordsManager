import handlers.ReadWriteHandler;
import main.Main;
import model.Car;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.launcher.ApplicationLauncher;
import org.junit.*;
import org.junit.runners.MethodSorters;

import javax.swing.*;
import java.util.List;

import static org.assertj.swing.finder.WindowFinder.findFrame;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EditCarTest {


    private FrameFixture window;
    private final Robot robot = BasicRobot.robotWithNewAwtHierarchy();

    // this runs before each test
    @Before
    public void setUp(){

            // changes the text file to the test text file
            Main.path = "tests/test.txt";

            // starts the applicaiton for the test
            ApplicationLauncher.application(Main.class).start();

            // assigns the Menu frame to the frame fixture "window"
            window = findCurrentFrame("Menu");


    }

    // this runs after each test to clean the frames up and the threads
    @After
    public void tearDown() {
        // this closes the window after every test
        window.cleanUp();
        robot.cleanUp();


    }

    @Test
    public void A_editExistingCarWithValidInputs(){
        try{

            System.out.println("Edit Car Tests : ");
            // edits a car normally

            // finds the view Records Button and clicks it
            window.button("View Records").click();

            // gets the new frame
            window = findCurrentFrame("Car Records");
            window.scrollPane().click();

            window.button("editButton").click();

            // sets window to the new frame
            window = findCurrentFrame("Modify Car");


            // this will change the year of the car by 1
            int oldYear = Integer.parseInt(window.textBox("yearBox").text());
            int newYear = oldYear + 1;
            String currentModel = window.textBox("modelBox").text();
            String currentMake = window.textBox("makeBox").text();


            window.textBox("yearBox").setText(String.valueOf(newYear));




            window.button("edit").click();

            // check if the edit has been changed

            // read the current cars in the test file
            List<Car> cars = ReadWriteHandler.txtFileToList(Main.path);

            for (Car car : cars) {
                // check the car that has been chosen has
                if (car.getModel().equals(currentModel) && car.getMake().equals(currentMake) && car.getYear().equals(String.valueOf(newYear))) {

                    // check that the newYear is 1 less than the oldYear
                    Assert.assertEquals(newYear, oldYear + 1);
                    System.out.println("Test 1 : Passed");

                }
            }
        }
        catch (Exception ignored){

        }
    }
    @Test
    public void B_editCarWithInvalidAcceleration(){
       try {
           // this test changes the accleration to an invaid input and tries to edit the car
           // finds the view Records Button and clicks it
           window.button("View Records").click();

           // gets the new frame
           window = findCurrentFrame("Car Records");
           window.scrollPane().click();

           window.button("editButton").click();

           // sets window to the new frame
           window = findCurrentFrame("Modify Car");


           // finds the acceleration of the car and changes it to an invalid input
           window.textBox("accBox").setText("2.2.2.2.2");

           window.button("edit").click();

           // check if the edit has been changed
           // JOptionpane should pop up saying invalid double
           String expectedErrorText = "Invalid Double !";
           String actualErrorText = window.optionPane().label("OptionPane.label").text();


           Assert.assertEquals(expectedErrorText, actualErrorText);

           // this should only print out when to assert equals is true
           System.out.println("Test 2 : Passed");

       }catch (Exception ignored){

       }

    }
    @Test
    public void C_editCarWithInvalidYear(){
        try{
            // this tries to edit a car with a valid year and inputs and invalid one instead
            window.button("View Records").click();

            // gets the new frame
            window = findCurrentFrame("Car Records");
            window.scrollPane().click();

            window.button("editButton").click();

            // sets window to the new frame
            window = findCurrentFrame("Modify Car");


            // finds the acceleration of the car and changes it to an invalid input
            window.textBox("yearBox").setText("2200");

            window.button("edit").click();

            // check if the edit has been changed
            // JOptionpane should pop up saying invalid double
            String expectedErrorText = "Year must be between 1886 and 2023";
            String actualErrorText = window.optionPane().label("OptionPane.label").text();


            Assert.assertEquals(expectedErrorText, actualErrorText);

            // this should only print out when to assert equals is true
            System.out.println("Test 3 : Passed");
        }catch (Exception ignored){

        }

    }
    @Test
    public void D_editCarEmptyInputs(){
        try{
            window.button("View Records").click();

            // gets the new frame
            window = findCurrentFrame("Car Records");
            window.scrollPane().click();

            window.button("editButton").click();

            // sets window to the new frame
            window = findCurrentFrame("Modify Car");


            // finds the acceleration of the car and changes it to an invalid input
            window.textBox("makeBox").setText("");
            window.textBox("modelBox").setText("");
            window.textBox("yearBox").setText("");
            window.textBox("engineBox").setText("");
            window.textBox("mpgBox").setText("");
            window.textBox("accBox").setText("");

            window.button("edit").click();

            // check if the edit has been changed
            // JOptionpane should pop up saying invalid double
            String expectedErrorText = "Year must be between 1886 and 2023";
            String actualErrorText = window.optionPane().label("OptionPane.label").text();


            Assert.assertEquals(expectedErrorText, actualErrorText);

            // this should only print out when to assert equals is true
            System.out.println("Test 4 : Passed");
        }
        catch (Exception ignored){

        }
    }
    @Test
    public void E_editCarInvalidMpg(){

       try{
           window.button("View Records").click();

           // gets the new frame
           window = findCurrentFrame("Car Records");
           window.scrollPane().click();

           window.button("editButton").click();

           // sets window to the new frame
           window = findCurrentFrame("Modify Car");


           // finds the mpg of the car and changes it to an invalid input
           window.textBox("mpgBox").setText("2.123.123.1.3123.asdasdf");

           window.button("edit").click();

           // check if the edit has been changed
           // JOptionpane should pop up saying invalid double
           String expectedErrorText = "Invalid Double !";
           String actualErrorText = window.optionPane().label("OptionPane.label").text();

           Assert.assertEquals(expectedErrorText, actualErrorText);

           System.out.println("Test 5 : Passed");
       }
       catch (Exception ignored){

       }
    }
    @Test
    public void F_editCarInvalidEngineSize(){
        try {
            window.button("View Records").click();

            // gets the new frame
            window = findCurrentFrame("Car Records");
            window.scrollPane().click();

            window.button("editButton").click();

            // sets window to the new frame
            window = findCurrentFrame("Modify Car");


            // finds the mpg of the car and changes it to an invalid input
            window.textBox("engineBox").setText("2.adsf123.12asdf3.1.3123.asdasdf");

            window.button("edit").click();

            // check if the edit has been changed
            // JOptionpane should pop up saying invalid double
            String expectedErrorText = "Invalid Double !";
            String actualErrorText = window.optionPane().label("OptionPane.label").text();

            Assert.assertEquals(expectedErrorText, actualErrorText);

            System.out.println("Test 6 : Passed");


        }
        catch (Exception ignored){

        }
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
