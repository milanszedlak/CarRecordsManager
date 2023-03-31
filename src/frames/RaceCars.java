package frames;

import handlers.ReadWriteHandler;
import main.Main;
import model.Car;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Objects;


// DISCLAIMER! the race cars function assumes that the acceleration through-out the race is constant and also that both
// cars have the same mass, so it is not very accurate it just demonstrates my coding ability
public class RaceCars {
    private final JFrame raceFrame = new JFrame();
    private final JButton backButton = new JButton("Back");
    private final JButton raceButton = new JButton("Race Cars !");

    private final String[] comboBoxList = initComboBox();
    private final JComboBox<String> car1Combo = new JComboBox<>(comboBoxList);
    private final JComboBox<String> car2Combo = new JComboBox<>(comboBoxList);
    private final JLabel car1Label = new JLabel("Car 1 : ");
    private final JLabel car2Label = new JLabel("Car 2 : ");

    private final JTextField distanceText = new JFormattedTextField();
    private final JLabel distanceLabel = new JLabel("Enter distance in metres : ");

    private final JPanel car2Panel = new JPanel();
    private final JPanel car1Panel = new JPanel();

    public RaceCars(JFrame previousFrame) {

        // initialise the frame size and layout from the previous frame
        raceFrame.setSize(previousFrame.getSize());
        raceFrame.setLocation(previousFrame.getX(), previousFrame.getY());
        raceFrame.setLayout(null);
        raceFrame.setBounds(previousFrame.getBounds());
        raceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        raceFrame.setResizable(false);
        raceFrame.setTitle("Race Cars");


        // set the frame to visible
        raceFrame.setVisible(true);

        // set the bounds for all the components
        setBoundsForComponents();

        // add all the components to the frame
        addComponentsToFrame();

        // add cars and the background to the frame
        addCarsAndBackGround();


        // distance entered is validated for huge numbers also must be between 1 and 10000 metres
        raceButton.addActionListener(e ->{

            // this also needs to check if the cars selected are the same
            // as you shouldn't be able to race the car against itself

            // this only allows the input to be between 1 and 10000
            try{
                if (Integer.parseInt(distanceText.getText()) <= 10000 && Integer.parseInt(distanceText.getText()) > 0){
                    if (Objects.equals(car1Combo.getSelectedItem(), car2Combo.getSelectedItem())){
                        // throw a JOption pane
                        JOptionPane.showMessageDialog(raceFrame,"Can't race the same car !", "Car selection error", JOptionPane.WARNING_MESSAGE, null);
                    }
                    else{

                        // race the cars

                        // get the acceleration from both of the cars and find the time that they take to reach the inputted distance
                        String[] comboValues1 = Objects.requireNonNull(car1Combo.getSelectedItem()).toString().split(" - ");
                        String[] comboValues2 = Objects.requireNonNull(car2Combo.getSelectedItem()).toString().split(" - ");

                        String car1Racer = comboValues1[0] + " "  + comboValues1[1] + " " + comboValues1[2];
                        String car2Racer = comboValues2[0] + " "  + comboValues2[1] + " " + comboValues2[2];

                        // this gets the acceleration for both of the cars
                        String acceleration1 = comboValues1[3].trim();
                        String acceleration2 = comboValues2[3].trim();

                        double doubleAcc = Double.parseDouble(acceleration1);
                        double doubleAcc2 = Double.parseDouble(acceleration2);

                        double time1 = Math.sqrt((2*Integer.parseInt(distanceText.getText()))/doubleAcc);
                        double time2 = Math.sqrt((2*Integer.parseInt(distanceText.getText()))/doubleAcc2);

                        moveCars(car1Panel,car2Panel,time1,time2, car1Racer,car2Racer);
                    }
                }
                else
                    JOptionPane.showMessageDialog(raceFrame, "Distance must be between 1 and 1000 metres", "Invalid distance", JOptionPane.ERROR_MESSAGE, null);

            }catch (Exception e1){
                JOptionPane.showMessageDialog(raceFrame, "Distance must be between 1 and 1000 metres", "Invalid distance", JOptionPane.ERROR_MESSAGE, null);
            }


        });

        backButton.addActionListener(e ->{
            new MainMenu(raceFrame.getLocation());
            raceFrame.dispose();

        });

        // adding a keyListener to the input for the distance
        // this validates the input for the distance to always be integer
        distanceText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char characterEntered = e.getKeyChar();
                if (!((characterEntered >= '0') && characterEntered <= '9')){
                    e.consume();
                }
            }
        });

    }


    // initialises the comboBoxes by reading from the text file
    private String[] initComboBox(){

        // reads cars from txt
        List<Car> cars = ReadWriteHandler.txtFileToList(Main.path);

        // adds the car names to a String[] along
        String[] carMakes = new String[cars.size()];
        for (int i = 0; i < cars.size(); i++) {
            carMakes[i] = cars.get(i).getMake() + " - " + cars.get(i).getModel() + " - " + cars.get(i).getYear() + " - " + cars.get(i).getAcceleration();
        }
        return carMakes;

    }
    // moves the cars and determines the winner based on the time that is less
    private void moveCars(JPanel car1Panel, JPanel car2Panel, double time1, double time2, String car1, String car2)   {

        // Move the panel across the screen using a Timer

        boolean finalCar1Win = time1 < time2;
        Timer timer = new Timer(10, new ActionListener() {
            int x1 = 120;
            int x2 = 120;
            @Override
            public void actionPerformed(ActionEvent e) {
                String winningCar;
                if (time1 == time2){

                    // this accounts for draws
                    x1++;
                    x2++;
                    winningCar = "Draw!";
                }
                else{
                    if (finalCar1Win){
                        x1+=2;
                        x2++;
                        winningCar = car1;
                    }
                    else {
                        x1++;
                        x2+=2;
                        winningCar = car2;
                    }
                }
                // 400 gets across the entire screen
                if (x1 > 400 || x2 > 400) {
                    ((Timer) e.getSource()).stop();

                    JOptionPane.showMessageDialog(raceFrame, "Winner is : " + winningCar, "Winner!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    car1Panel.setBounds(x1, 385, 100, 100); // Set the new x-coordinate of the panel
                    car2Panel.setBounds(x2,485,100,100);
                }
            }
        });
        timer.start();


    }
    private void addCarsAndBackGround(){
        JLabel logo = new JLabel(new ImageIcon("resources/raceTrack1.png"));
        logo.setBounds(100,365,400,200);



        // car 1 panel

        car1Panel.setBounds(120,385,100,100);
        raceFrame.add(car1Panel);
        JLabel car1 = new JLabel(new ImageIcon("resources/car1.png"));
        car1Panel.setOpaque(false);
        car1.setBounds(120,385,50,100);


        // car 2 panel

        car2Panel.setBounds(120,485,100,100);
        raceFrame.add(car2Panel);
        JLabel car2 = new JLabel(new ImageIcon("resources/car2.png"));
        car2Panel.setOpaque(false);
        car2.setBounds(120,385,50,100);


        car1Panel.add(car1);
        car2Panel.add(car2);


        raceFrame.add(logo);
    }
    private void setBoundsForComponents(){
        // set the bounds for the combo Boxes and there labels
        car1Combo.setBounds(100, 100, 400, 40);
        car2Combo.setBounds(100, 175, 400, 40);

        car1Label.setBounds(102,70,400,40);
        car2Label.setBounds(102,145,400,40);

        // set the bounds for the distance box and label
        distanceText.setBounds(100,250,400,40);
        distanceLabel.setBounds(102,220,400,40);

        // set the bounds for the buttons
        backButton.setBounds(20, 20, 100, 50);
        raceButton.setBounds(240,300,120,50);
    }
    private void addComponentsToFrame(){
        raceFrame.add(backButton);
        raceFrame.add(car1Combo);
        raceFrame.add(car2Combo);
        raceFrame.add(car1Label);
        raceFrame.add(car2Label);
        raceFrame.add(distanceText);
        raceFrame.add(distanceLabel);
        raceFrame.add(raceButton);

        car1Combo.setName("combo1");
        car2Combo.setName("combo2");
        raceButton.setName("race");
        distanceText.setName("input");

    }
}
