package frames;

import handlers.ReadWriteHandler;
import main.Main;
import model.Car;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class JourneyCost {



    private final List<Car> carMPGList = new ArrayList<>();
    public JourneyCost(JFrame frame){
        initFrame(frame);
    }

    public void initFrame(JFrame frame){

        // creates all the buttons
        final JButton calculateButton = new JButton("Calculate");
        final JButton backButton = new JButton("Back");

        // creates all the textFields and the combo Box
        final JTextField numOfMilesText = new JFormattedTextField();
        final JTextField fuelCostText = new JFormattedTextField();
        final JTextField mpgText = new JFormattedTextField();
        JComboBox<String> makeComboBox;

        // Creates all the Labels
        final JLabel resultLabel = new JLabel("Enter values and click calculate to find the cost!",
                SwingConstants.CENTER);

        final JLabel makeLbl = new JLabel("Make :");
        final JLabel mpgLbl = new JLabel("MPG :");
        final JLabel numOfMilesLbl = new JLabel("Number of Miles :");
        final JLabel fuelCostLbl = new JLabel("Fuel Cost (Pence/Litre) :");

        numOfMilesText.setName("numberOfMiles");
        fuelCostText.setName("fuelCost");
        calculateButton.setName("calculate");
        resultLabel.setName("result");
        mpgText.setName("mpgBox");

        // initialises the frame using the values from the previous frame
        JFrame journeyFrame = new JFrame();
        journeyFrame.setSize(frame.getSize());
        journeyFrame.setLocation(frame.getX(), frame.getY());
        journeyFrame.setLayout(null);
        journeyFrame.setBounds(frame.getBounds());
        journeyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        journeyFrame.setResizable(false);
        journeyFrame.setTitle("Journey Cost Calculator");

        // adds all the values from txt file to combo box
        String[] carMakes = initComboBox();
        makeComboBox = new JComboBox<>(carMakes);

        // set the bounds for text boxes and the comboBox
        setBoundsForTextBox(numOfMilesText, fuelCostText, mpgText, makeComboBox);

        // set the bounds for the buttons
        setBoundsForButtons(calculateButton, backButton);

        // set the bounds for the labels
        setBoundsForLabels(makeLbl, mpgLbl, numOfMilesLbl, fuelCostLbl, resultLabel);

        // adds everything to the frame
        addEveryComponentToFrame(journeyFrame, calculateButton, backButton, numOfMilesText, fuelCostText,
                mpgText, makeComboBox, makeLbl, mpgLbl, numOfMilesLbl, fuelCostLbl, resultLabel);


        // this is so when to form opens it automatically selects a drop-down and assigns it mpg
        addingSelectedMpg(makeComboBox, mpgText);


        //set the frame to visible
        journeyFrame.setVisible(true);

        // adding listener to the combo box to autofill the mpg
        makeComboBox.addActionListener(e -> {
            addingSelectedMpg(makeComboBox, mpgText);

        });

        // adding the listener to calculate button
        calculateButton.addActionListener(e -> {

            double result;

            // calculate the cost for the journey
            if (checkInputsEmpty(numOfMilesText, fuelCostText, journeyFrame) && validateInputs(numOfMilesText, fuelCostText, journeyFrame)){
                result = calculateCostInPounds(Double.parseDouble(mpgText.getText()),
                        Double.parseDouble(numOfMilesText.getText()),
                        Double.parseDouble(fuelCostText.getText()));

                // show the result of the cost in the label and the bottom of the screen
                resultLabel.setText("Price of your Journey is : £" + result);
            }

        });

        // back button action listener to get back to the main frame
        backButton.addActionListener(e -> {
            new MainMenu(journeyFrame.getLocation());
            journeyFrame.dispose();
        });


        // adding keylistener to the numOfMilesText
        numOfMilesText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyPressed = e.getKeyChar();

                if (!((keyPressed >= '0') && keyPressed <= '9' || keyPressed == KeyEvent.VK_PERIOD) ){
                    e.consume();
                }
            }
        });

        //adding keylistener to the fuelCostText
        fuelCostText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyPressed = e.getKeyChar();

                if (!((keyPressed >= '0') && keyPressed <= '9' || keyPressed == KeyEvent.VK_PERIOD) ){
                    e.consume();
                }
            }
        });



    }

    // gets all the cars from the txt file and then adds them to a String[]
    public String[] initComboBox(){


        // reads cars from txt
        List<Car> cars = ReadWriteHandler.txtFileToList(Main.path);

        // adds the car names to a String[] along
        String[] carMakes = new String[cars.size()];
        for (int i = 0; i < cars.size(); i++) {
            carMakes[i] = cars.get(i).getMake() + " - " + cars.get(i).getModel() + " - " + cars.get(i).getYear();

            // adds the cars to this list so no more reading of the txt file is necessary
            carMPGList.add(cars.get(i));
        }

        return carMakes;

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

    // validate the inputs for the number of miles and the cost in pence

    private boolean validateInputs(JTextField miles, JTextField cost, JFrame currentFrame){

        try{

            // try to parse the values to a double
            double milesDouble = Double.parseDouble(miles.getText());
            double costDouble = Double.parseDouble(cost.getText());

            if (milesDouble <= 0 || costDouble <=0){
                JOptionPane.showMessageDialog(currentFrame, "Inputs Cant be Zero!", "Invalid Input!", JOptionPane.ERROR_MESSAGE);

                return false;
            }


            return true;


        }catch (Exception e){
            JOptionPane.showMessageDialog(currentFrame, "Invalid Double", "Invalid Double", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    private boolean checkInputsEmpty(JTextField miles, JTextField cost, JFrame currentFrame){

        if (miles.getText().isEmpty() && cost.getText().isEmpty()){
            JOptionPane.showMessageDialog(currentFrame, "Inputs cant be empty!", "Empty Inputs!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

















    // Boilerplate code for setting the bounds of the components and adding them to the frame
    public void setBoundsForButtons(JButton calculateButton, JButton backButton){
        calculateButton.setBounds(250, 400, 100, 50);
        backButton.setBounds(20,20,100,50);
    }
    public void setBoundsForTextBox(JTextField numOfMilesText, JTextField fuelCostText, JTextField mpgText,
                                    JComboBox<String> makeComboBox){
        makeComboBox.setBounds(100, 100, 400, 40);
        mpgText.setBounds(100,175,400,40);
        numOfMilesText.setBounds(100, 250, 400, 40);
        fuelCostText.setBounds(100, 325, 400, 40);

        // set the mpg text box to not editable
        mpgText.setEditable(false);
    }
    public void setBoundsForLabels(JLabel makeLbl, JLabel mpgLbl, JLabel numOfMilesLbl, JLabel fuelCostLbl,
                                   JLabel resultLabel){
        makeLbl.setBounds(100,-110,400,400);
        mpgLbl.setBounds(100,-35,400,400);
        numOfMilesLbl.setBounds(100,40,400,400);
        fuelCostLbl.setBounds(100,115,400,400);
        resultLabel.setBounds(100,450,400,75);
    }

    public void addEveryComponentToFrame(JFrame journeyFrame, JButton calculateButton, JButton backButton,
                                         JTextField numOfMilesText,
                                         JTextField fuelCostText, JTextField mpgText,
                                         JComboBox<String> makeComboBox, JLabel makeLbl,
                                         JLabel mpgLbl, JLabel numOfMilesLbl, JLabel fuelCostLbl, JLabel resultLabel
                                         ){

        journeyFrame.add(calculateButton);
        journeyFrame.add(backButton);
        journeyFrame.add(numOfMilesText);
        journeyFrame.add(fuelCostText);
        journeyFrame.add(mpgText);
        journeyFrame.add(makeComboBox);
        journeyFrame.add(makeLbl);
        journeyFrame.add(mpgLbl);
        journeyFrame.add(numOfMilesLbl);
        journeyFrame.add(fuelCostLbl);
        journeyFrame.add(resultLabel);
    }

    public void addingSelectedMpg(JComboBox<String> makeComboBox, JTextField mpgText) {
        // iterates through the list of cars and finds the one that matches the selected car
        for (Car car : carMPGList) {
            if (Objects.requireNonNull(makeComboBox.getSelectedItem()).toString().contains(car.getModel()))
                mpgText.setText(String.valueOf(car.getMpg()));
        }
    }
}

