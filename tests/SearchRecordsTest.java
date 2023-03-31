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
public class SearchRecordsTest {

    private final Robot robot = BasicRobot.robotWithNewAwtHierarchy();
    private FrameFixture window;

    @Before
    public void setUp(){
        // this sets up the right frame and starts the application for the tests
        // and changes the text file to the test one

        Main.path = "tests/test.txt";
        ApplicationLauncher.application(Main.class).start();

        window = findCurrentFrame("Menu");

    }

    @After
    public void tearDown(){
        window.cleanUp();
    }

    @Test
    public void A_searchByMake(){

        System.out.println("Search Cars Tests : ");

        // this searches the text file for a record that contains the make that has been entered

        window.button("Search Records").click();

        window = findCurrentFrame("Search Records");

        window.comboBox().click().selectItem("Make");


        window.textBox("userInput").setText("Ford");

        window.button("search").click();

        JTable table = window.table().target();


        boolean correctValues = false;

        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getModel().getValueAt(i,1).equals("Ford")){
                Assert.assertEquals("Ford", table.getModel().getValueAt(i,1));
                correctValues = true;
            }
            else{
                Assert.fail();
            }
        }

        if (correctValues){
            System.out.println("Test 1: Passed");
        }









    }
    @Test
    public void B_SearchByModel(){
        window.button("Search Records").click();

        window = findCurrentFrame("Search Records");

        window.comboBox().click().selectItem("Model");


        window.textBox("userInput").setText("A5");

        window.button("search").click();

        JTable table = window.table().target();


        boolean correctValues = false;




        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getModel().getValueAt(i,2).equals("A5")){
                Assert.assertEquals("A5", table.getModel().getValueAt(i,2));
                correctValues = true;
            }
            else{
                Assert.fail();
            }
        }

        if (correctValues){
            System.out.println("Test 2: Passed");
        }
    }
    @Test
    public void C_SearchByYear(){
        window.button("Search Records").click();

        window = findCurrentFrame("Search Records");

        window.comboBox().click().selectItem("Year");


        window.textBox("userInput").setText("2022");

        window.button("search").click();

        JTable table = window.table().target();


        boolean correctValues = false;




        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getModel().getValueAt(i,3).equals("2022")){
                Assert.assertEquals("2022", table.getModel().getValueAt(i,3));
                correctValues = true;
            }
            else{
                Assert.fail();
            }
        }

        if (correctValues){
            System.out.println("Test 3: Passed");
        }
    }
    @Test
    public void D_SearchByEngineSize(){
        window.button("Search Records").click();

        window = findCurrentFrame("Search Records");

        window.comboBox().click().selectItem("Engine Size");


        window.textBox("userInput").setText("6.2");

        window.button("search").click();

        JTable table = window.table().target();


        boolean correctValues = false;




        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getModel().getValueAt(i,4).equals(String.valueOf(window.textBox("userInput").text()))){
                Assert.assertEquals("6.2", table.getModel().getValueAt(i,4));
                correctValues = true;
            }
            else{
                Assert.fail();
            }
        }

        if (correctValues){
            System.out.println("Test 4: Passed");
        }
    }
    @Test
    public void E_SearchByMPG(){
        window.button("Search Records").click();

        window = findCurrentFrame("Search Records");

        window.comboBox().click().selectItem("MPG");


        window.textBox("userInput").setText("40.0");

        window.button("search").click();

        JTable table = window.table().target();


        boolean correctValues = false;




        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getModel().getValueAt(i,5).equals(String.valueOf(window.textBox("userInput").text()))){
                Assert.assertEquals("40.0", table.getModel().getValueAt(i,5));
                correctValues = true;
            }
            else{
                Assert.fail();
            }
        }

        if (correctValues){
            System.out.println("Test 5: Passed");
        }
    }
    @Test
    public void F_SearchByAcceleration(){
        window.button("Search Records").click();

        window = findCurrentFrame("Search Records");

        window.comboBox().click().selectItem("Acceleration");


        window.textBox("userInput").setText("6.2");

        window.button("search").click();

        JTable table = window.table().target();


        boolean correctValues = false;




        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getModel().getValueAt(i,6).equals(String.valueOf(window.textBox("userInput").text()))){
                Assert.assertEquals("6.2", table.getModel().getValueAt(i,6));
                correctValues = true;
            }
            else{
                Assert.fail();
            }
        }

        if (correctValues){
            System.out.println("Test 6: Passed");
        }
    }
    // these tests are testing invalid inputs
    @Test
    public void G_SearchByMakeButEmpty(){

        window.button("Search Records").click();

        window = findCurrentFrame("Search Records");

        window.comboBox().click().selectItem("Make");


        window.textBox("userInput").setText("");

        window.button("search").click();


        String actualValue = window.optionPane().label("OptionPane.label").text();
        String expectValue = "Must Enter a Value !";

        window.optionPane().click();

        if (expectValue.equals(actualValue)){
            Assert.assertEquals(expectValue,actualValue);
            System.out.println("Test 7 : Passed");

        }
        else
            Assert.fail();








    }

    // I can do this test only to acceleration and don't have to test the mpg, engine size as it uses the same text box
    @Test
    public void H_SearchByAccelerationButInvalidDouble(){
        window.button("Search Records").click();

        window = findCurrentFrame("Search Records");

        window.comboBox().click().selectItem("Acceleration");


        window.textBox("userInput").setText("23.45.234.5");

        window.button("search").click();


        String actualValue = window.optionPane().label("OptionPane.label").text();
        String expectValue = "Invalid Double";

        window.optionPane().click();

        if (expectValue.equals(actualValue)){
            Assert.assertEquals(expectValue,actualValue);
            System.out.println("Test 8 : Passed");

        }
        else
            Assert.fail();
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
