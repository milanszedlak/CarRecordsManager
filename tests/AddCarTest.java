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
public class AddCarTest {

    
    // Uses a test Txt file
    // creates a basic robot for automated testing
    Robot robot = BasicRobot.robotWithNewAwtHierarchy();
    private FrameFixture window;


    // this is excecuted before every test
    @Before
    public void setUp() {
    	
    	
    	

        // this sets the test up from the main method and sets the frame Fixture to window
        // chages the textFile to a test one with the same data in it
        Main.path = "tests/test.txt";
        ApplicationLauncher.application(Main.class).start();
        window = findCurrentFrame("Menu");
    }

    @After
    public void tearDown() {
        // this closes the window after every test
        window.cleanUp();
    }

    @Test
    // adds a new car that doesn't exist in the text file already
    public void addCarNewCar() {

        System.out.println("Add Car Tests : ");

        // finds the "View Records" button and clicks it
        window.button("View Records").click();

        // new Frame is opened, so we need to find it and set it to window
        window = findCurrentFrame("Car Records");

        // finds the button "Add Car" and clicks it
        window.button("addButton").click();

        // new Frame is opened, so we need to find it and set it to window
        window = findCurrentFrame("Modify Car");

        window.textBox("idBox").setText("17");
        window.textBox("makeBox").setText("Seat");
        window.textBox("modelBox").setText("Leon");
        window.textBox("yearBox").setText("2017");
        window.textBox("engineBox").setText("1.4");
        window.textBox("mpgBox").setText("50");
        window.textBox("accBox").setText("10.5");


        window.button("add").click();

        // check if the car has been added by checking if the car is in the text file
        // if it is then the test passes


        List<Car> cars = ReadWriteHandler.txtFileToList(Main.path);
        for (Car value : cars) {
            if (value.getId().equals(17L)){


                Car car = new Car(17L, "Seat", "Leon", "2017", 1.4f, 50.0, 10.5);

                Assert.assertEquals(car.getId(), value.getId());
                Assert.assertEquals(car.getMake(), value.getMake());
                Assert.assertEquals(car.getModel(), value.getModel());
                Assert.assertEquals(car.getYear(), value.getYear());
                Assert.assertEquals(car.getEngineSize(), value.getEngineSize(), 0.0);
                Assert.assertEquals(car.getMpg(), value.getMpg(), 0.0);
                Assert.assertEquals(car.getAcceleration(), value.getAcceleration(), 0.0);

                System.out.println("Test 1 : Passed");


            }


        }

    }

    @Test
    // tries to add a car that already exist in the text file
    public void addExistingCar(){
        // finds the "View Records" button and clicks it
        window.button("View Records").click();

        // new Frame is opened, so we need to find it and set it to window
        window = findCurrentFrame("Car Records");

        // finds the button "Add Car" and clicks it
        window.button("addButton").click();

        // new Frame is opened, so we need to find it and set it to window
        window = findCurrentFrame("Modify Car");

        window.textBox("idBox").setText("17");
        window.textBox("makeBox").setText("Seat");
        window.textBox("modelBox").setText("Leon");
        window.textBox("yearBox").setText("2017");
        window.textBox("engineBox").setText("1.4");
        window.textBox("mpgBox").setText("50");
        window.textBox("accBox").setText("10.5");


        window.button("add").click();

        Assert.assertEquals("Car with id already exists!", window.optionPane().label("OptionPane.label").text());

        // this will only print if the test has passed
        System.out.println("Test 5 : Passed");
        window.optionPane().button().click();



    }

    @Test
    // tries to add a car with inputs
    public void addEmptyCar(){
        // finds the "View Records" button and clicks it
        window.button("View Records").click();

        // new Frame is opened, so we need to find it and set it to window
        window = findCurrentFrame("Car Records");

        // finds the button "Add Car" and clicks it
        window.button("addButton").click();

        // new Frame is opened, so we need to find it and set it to window
        window = findCurrentFrame("Modify Car");

        window.textBox("makeBox").setText("");
        window.textBox("modelBox").setText("");
        window.textBox("yearBox").setText("");
        window.textBox("engineBox").setText("");
        window.textBox("mpgBox").setText("");
        window.textBox("accBox").setText("");


        window.button("add").click();



        Assert.assertEquals("Can't leave inputs empty!", window.optionPane().label("OptionPane.label").text());

        window.optionPane().button().click();

        // this will only print if the test has passed
        System.out.println("Test 4 : Passed");

    }

    @Test
    // tries to add a car with no make
    public void addCarWithNoMake(){
        // finds the "View Records" button and clicks it
        window.button("View Records").click();

        // new Frame is opened, so we need to find it and set it to window
        window = findCurrentFrame("Car Records");

        // finds the button "Add Car" and clicks it
        window.button("addButton").click();

        // new Frame is opened, so we need to find it and set it to window
        window = findCurrentFrame("Modify Car");

        window.textBox("makeBox").setText("");
        window.textBox("modelBox").setText("Leon");
        window.textBox("yearBox").setText("2017");
        window.textBox("engineBox").setText("1.4");
        window.textBox("mpgBox").setText("50");
        window.textBox("accBox").setText("10.5");


        window.button("add").click();



        Assert.assertEquals("Can't leave inputs empty!", window.optionPane().label("OptionPane.label").text());

        window.optionPane().button().click();

        // this will only print if the test has passed
        System.out.println("Test 3 : Passed");
    }

    @Test
    // adds valid car with invalid year must be between 1886 and the current year
    public void addCarWithInvalidYear(){

        // finds the "View Records" button and clicks it
        window.button("View Records").click();

        // new Frame is opened, so we need to find it and set it to window
        window = findCurrentFrame("Car Records");

        // finds the button "Add Car" and clicks it
        window.button("addButton").click();

        // new Frame is opened, so we need to find it and set it to window
        window = findCurrentFrame("Modify Car");

        window.textBox("makeBox").setText("Seat");
        window.textBox("modelBox").setText("Leon");
        window.textBox("yearBox").setText("2099");
        window.textBox("engineBox").setText("1.4");
        window.textBox("mpgBox").setText("50");
        window.textBox("accBox").setText("10.5");


        window.button("add").click();



        Assert.assertEquals("Year must be between 1886 and 2023", window.optionPane().label("OptionPane.label").text());

        window.optionPane().button().click();

        // this will only print if the test has passed
        System.out.println("Test 2 : Passed");


    }



    // this finds the current frame that is opened
    private FrameFixture findCurrentFrame(String title) {
        return findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
            @Override
            protected boolean isMatching(JFrame frame) {
                return title.equals(frame.getTitle()) && frame.isShowing();
            }
        }).using(robot);
    }









}
