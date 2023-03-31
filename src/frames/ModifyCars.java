package frames;


import handlers.ReadWriteHandler;
import model.Car;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;

// Modify cars frame
public class ModifyCars{

    // the id is auto added in the ReadWriteHandler class

    // create all the textFields needed for the frame
    private JTextField makeBox; private  JTextField engineBox;
    private  JTextField mpgBox; private  JTextField accBox;
    private  JTextField modelBox; private  JTextField yearBox;
    private JTextField idBox;

    // create all the buttons needed for the frame
    private JButton addButton;  private JButton deleteButton;
    private JButton backButton; private JButton editButton;



    // create all the labels for the frame
    private JLabel makeLBl; private JLabel engineLBl;
    private JLabel mpgLbl;  private JLabel modelLBl;
    private JLabel yearLBl; private JLabel accLbl;

    private JLabel idLbl;


    // create the frame and the selected car class to be able to modify the cars
    private final JFrame ModifyFrame = new JFrame("Modify Car");
    private final Car selectedCar;

    public ModifyCars(JFrame previousFrame, Car car, int frameChoice){
        // intialise the frame
        ModifyFrame.setLayout(null);
        ModifyFrame.setSize(previousFrame.getSize());
        ModifyFrame.setLocation(previousFrame.getX(), previousFrame.getY());

        ModifyFrame.setBounds(previousFrame.getBounds());
        ModifyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ModifyFrame.setResizable(false);

        // sets the inputted car to the selectCar instantition
        this.selectedCar = car;

        // initialise all the components for the frame
        initComponents();

        // set the bounds for all the components
        setBoundsForComponents();

        // add all the components to the frame
        addComponents();


        // sets the mode depending on what button was pressed in the previous frame
        switch (frameChoice){
            case 1:
                editMode();
                break;
            case 2:
                addMode();
                break;
            case 3:
                deleteMode();
                break;
        }



        // Adding an actionListener to the add Button
        addButton.addActionListener(e -> {

            if (checkTextFieldsEmpty() && checkValidDoubleFormat() && checkYearBoxFormat()){
                // add the car to the end of the text file
                // increments to id by 1
                // make a new car from the text fields
                boolean idEmpty = false;
                if (idBox.getText().isEmpty())
                    idEmpty = true;

                ReadWriteHandler.addCarInTxt(getSelectedAddCar(idEmpty));

                // opens the records frame once the new car is added
                openPreviousFrame();
            }

        });

        // Adding the action listener to the edit button
        editButton.addActionListener(e ->{

            if(checkYearBoxFormat() && checkTextFieldsEmpty() && checkValidDoubleFormat()){
                // get the selected car from the previous screen and changes made from the user
                ReadWriteHandler.updateCarInTxt(getSelectedCarDeleteOrEdit());

                // opens the records frame once the car is edited
                openPreviousFrame();
            }
        });

        // Adding the actio listener to the delete button
        deleteButton.addActionListener(e -> {

            // this doesnt need error handling as the values cannot be modified
            // gets the selected car from previous screen and deletes it from the records
            // finds the id being deleted and decrements all that are greater by 1
            ReadWriteHandler.deleteCarInTxt(getSelectedCarDeleteOrEdit());

            // opens the records frame once the car is deleted
            openPreviousFrame();
        });

        // add the action listener to the back button
        backButton.addActionListener(e ->{
               openPreviousFrame();
        });

        // visible true
        ModifyFrame.setVisible(true);


        // Year Text Listener
        yearBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyPressed = e.getKeyChar();

                if (!((keyPressed >= '0') && keyPressed <= '9') ){
                    e.consume();
                }
            }
        });

        // mpg Text Listener
        mpgBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyPressed = e.getKeyChar();

                if (!((keyPressed >= '0') && keyPressed <= '9' || keyPressed == KeyEvent.VK_PERIOD) ){
                    e.consume();
                }
            }
        });

        // acceleration Text Listener
        accBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyPressed = e.getKeyChar();

                if (!((keyPressed >= '0') && keyPressed <= '9' || keyPressed == KeyEvent.VK_PERIOD) ){
                    e.consume();
                }
            }
        });

        // engineSize Text Listener
        engineBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyPressed = e.getKeyChar();

                if (!((keyPressed >= '0') && keyPressed <= '9' || keyPressed == KeyEvent.VK_PERIOD) ){
                    e.consume();
                }
            }
        });

        // Make text listener
        makeBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyPressed = e.getKeyChar();

                // should only be able to enter text no numbers
                if (((keyPressed >= '0') && keyPressed <= '9' || keyPressed== KeyEvent.VK_SPACE) ){
                    e.consume();
                }
            }
        });

        // model text listner

        modelBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyPressed = e.getKeyChar();

                // should only be able to enter text no numbers
                if (keyPressed== KeyEvent.VK_SPACE){
                    e.consume();
                }
            }
        });


    }




    // set the components for the frame
    private void setBoundsForComponents(){

        // sets the bounds for all the components in the frame
        idBox.setBounds(100, 25, 400, 40);
        makeBox.setBounds(100, 100, 400, 40);
        modelBox.setBounds(100, 175, 400, 40);
        yearBox.setBounds(100, 250, 400, 40);
        engineBox.setBounds(100, 325, 400, 40);
        mpgBox.setBounds(100, 400, 400, 40);
        accBox.setBounds(100,475,400,40);

        addButton.setBounds(250, 515, 100, 50);
        editButton.setBounds(250, 515, 100, 50);

        deleteButton.setBounds(250,515,100,50);

        backButton.setBounds(10,10,65,45);
        // set the bounds for the labels

        makeLBl.setBounds(102, 70, 400, 40);

        modelLBl.setBounds(102, 145, 400, 40);

        yearLBl.setBounds(102, 220, 400, 40);

        engineLBl.setBounds(102, 295, 400, 40);

        mpgLbl.setBounds(102,370,400,40);

        accLbl.setBounds(102,445, 400, 40);

        idLbl.setBounds(102, 0, 400, 40);
    }
    private void addComponents(){
        // adds all the components to the frame
        ModifyFrame.add(makeBox);  ModifyFrame.add(makeLBl);
        ModifyFrame.add(modelBox); ModifyFrame.add(modelLBl);
        ModifyFrame.add(yearBox); ModifyFrame.add(yearLBl);
        ModifyFrame.add(engineBox); ModifyFrame.add(engineLBl);
        ModifyFrame.add(addButton); ModifyFrame.add(editButton);
        ModifyFrame.add(backButton); ModifyFrame.add(deleteButton);
        ModifyFrame.add(mpgBox); ModifyFrame.add(mpgLbl);
        ModifyFrame.add(accLbl); ModifyFrame.add(accBox);
        ModifyFrame.add(idBox); ModifyFrame.add(idLbl);

        // sets the name for all the JTextFields
        makeBox.setName("makeBox");
        modelBox.setName("modelBox");
        yearBox.setName("yearBox");
        engineBox.setName("engineBox");
        mpgBox.setName("mpgBox");
        accBox.setName("accBox");
        idBox.setName("idBox");

        addButton.setName("add");
        deleteButton.setName("delete");
        editButton.setName("edit");

    }

    private void initComponents(){

        // initialise the textFields
        makeBox = new JFormattedTextField();
        modelBox = new JFormattedTextField();
        yearBox = new JFormattedTextField();
        engineBox = new JFormattedTextField();
        mpgBox = new JFormattedTextField();
        accBox = new JFormattedTextField();
        idBox = new JFormattedTextField();

        // intiialise the Buttons
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        backButton = new JButton("Back");

        // intialise all the labels
        makeLBl = new JLabel("Make :");
        modelLBl = new JLabel("Model :");
        yearLBl = new JLabel("Year :");
        engineLBl = new JLabel("Engine Size :");
        mpgLbl = new JLabel("MPG :");
        accLbl = new JLabel("Acceleration (m/s^2)");
        idLbl = new JLabel("ID :");

    }

    // frame modes
    private void deleteMode(){
        //delete choice
        editButton.setVisible(false);
        editButton.setEnabled(false);
        addButton.setVisible(false);
        addButton.setEnabled(false);

        idBox.setEnabled(false);
        idBox.setVisible(false);
        idLbl.setVisible(false);

        makeBox.setText(selectedCar.getMake());
        modelBox.setText(selectedCar.getModel());
        yearBox.setText(selectedCar.getYear());
        engineBox.setText(String.valueOf(selectedCar.getEngineSize()));
        mpgBox.setText(String.valueOf(selectedCar.getMpg()));
        accBox.setText(String.valueOf(selectedCar.getAcceleration()));
        makeBox.setEditable(false);
        modelBox.setEditable(false);
        yearBox.setEditable(false);
        engineBox.setEditable(false);
        mpgBox.setEditable(false);
        accBox.setEditable(false);
    }
    private void editMode(){
        // edit choice
        addButton.setVisible(false);
        addButton.setEnabled(false);
        // also prefill the text boxes with the current row selected from the previous screen
        String make = selectedCar.getMake().trim();
        String model = selectedCar.getModel().trim();
        String year = selectedCar.getYear().trim();

        makeBox.setText(make);
        modelBox.setText(model);
        yearBox.setText(year);
        engineBox.setText(String.valueOf(selectedCar.getEngineSize()));
        mpgBox.setText(String.valueOf(selectedCar.getMpg()));
        accBox.setText(String.valueOf(selectedCar.getAcceleration()));

        // disable the delete button
        deleteButton.setVisible(false);
        deleteButton.setEnabled(false);

        idBox.setEnabled(false);
        idBox.setVisible(false);
        idLbl.setVisible(false);

    }
    private void addMode(){
        // add choice
        // disable the edit button
        editButton.setVisible(false);
        editButton.setEnabled(false);

        // disable the delete button
        deleteButton.setEnabled(false);
        deleteButton.setVisible(false);
    }
    private void openPreviousFrame(){
        new RecordsFrame(ModifyFrame);
        ModifyFrame.dispose();
    }

    // get the car that was clicked on the previous frame
    private Car getSelectedAddCar(boolean idNull){

        if (idNull)
            return new Car(null, makeBox.getText(), modelBox.getText(), yearBox.getText(), Float.parseFloat(engineBox.getText()),Double.parseDouble(mpgBox.getText()), Double.parseDouble(accBox.getText()));
        else{
            return new Car(Long.parseLong(idBox.getText()), makeBox.getText(), modelBox.getText(), yearBox.getText(), Float.parseFloat(engineBox.getText()),Double.parseDouble(mpgBox.getText()), Double.parseDouble(accBox.getText()));
        }

    }

    private Car getSelectedCarDeleteOrEdit(){
        return new Car(selectedCar.getId(), makeBox.getText(), modelBox.getText(), yearBox.getText(), Float.parseFloat(engineBox.getText()),Double.parseDouble(mpgBox.getText()), Double.parseDouble(accBox.getText()));
    }


    // checks if any of the inputs are empty
    private boolean checkTextFieldsEmpty(){


        if (makeBox.getText().trim().isEmpty() || modelBox.getText().trim().isEmpty() || yearBox.getText().trim().isEmpty() || accBox.getText().trim().isEmpty() || mpgBox.getText().trim().isEmpty() || engineBox.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(ModifyFrame, "Can't leave inputs empty!", "Empty Inputs", JOptionPane.ERROR_MESSAGE, null);
            return false;
        }
        return true;
    }

    // checks the valid inputs for the year
    private boolean checkYearBoxFormat(){

        try{
            int year = Integer.parseInt(yearBox.getText());
            // this ensures that the year is 4 characters long and it is between 1886 which is when the first car was made
            // and the current year
            if (yearBox.getText().length() == 4 && year >= 1886 && year <= LocalDateTime.now().getYear()){
                return true;
            }


            JOptionPane.showMessageDialog(ModifyFrame, "Year must be between 1886 and " + LocalDateTime.now().getYear(), "Invalid Date", JOptionPane.ERROR_MESSAGE, null);

        }
        catch (Exception e){
            JOptionPane.showMessageDialog(ModifyFrame, "Year must be between 1886 and " + LocalDateTime.now().getYear(), "Invalid Date", JOptionPane.ERROR_MESSAGE, null);
        }

        return false;

    }

    // checks the valid inputs for the acceleration , mpg and engineSize
    private boolean checkValidDoubleFormat(){

        try{
            // parses all the boxes where double can be entered
            double parse = Double.parseDouble(mpgBox.getText());
            double parse2 = Double.parseDouble(accBox.getText());
            double parse3 = Double.parseDouble(engineBox.getText());

            // checks if any of the inputs are zero
            if(parse <= 0 || parse2 <= 0 || parse3 <= 0){
                JOptionPane.showMessageDialog(ModifyFrame, "Values cant be Zero!", "Invalid Inputs!", JOptionPane.ERROR_MESSAGE, null);

                return false;

            }

            return true;
        }
        catch (Exception e){
            // catches any error when trying to parse string to a double
            JOptionPane.showMessageDialog(ModifyFrame, "Invalid Double !", "Invalid Double", JOptionPane.ERROR_MESSAGE, null);

            return false;

        }
    }











}
