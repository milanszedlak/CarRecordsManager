package frames;

import handlers.ReadWriteHandler;
import main.Main;
import model.Car;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SearchFrame {

    // create the frame
    private final JFrame searchFrame = new JFrame();

    // create labels
    private final JLabel searchByLabel = new JLabel("Search by:");
    private final JLabel userInputLabel = new JLabel("Enter Make : ");

    // create buttons for the frame
    private final JButton searchButton = new JButton("Search");
    private final JButton backButton = new JButton("Back");

    // create combo box needed for the frame
    private final JComboBox<String> carAttributeCombo;

    // create the text field needed for the frame
    private final JTextField userInput = new JFormattedTextField();


    public SearchFrame(JFrame frame) {

        // initialises all the settings for the frame
        searchFrame.setSize(frame.getSize());
        searchFrame.setLocation(frame.getX(), frame.getY());
        searchFrame.setLayout(null);
        searchFrame.setBounds(frame.getBounds());
        searchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchFrame.setResizable(false);
        searchFrame.setTitle("Search Records");
        userInput.setName("userInput");
        searchButton.setName("search");



        // initialise the drop-down box with all the cars
        String[] dropBoxOptions = {"Make", "Model", "Year", "Engine Size", "MPG", "Acceleration"};
        carAttributeCombo = new JComboBox<>(dropBoxOptions);

        // set the bounds for all the components
        setBoundsForComponents();

        // add all components to the frame
        addComponentsToFrame();

        // create a table to store the values of the search function
        JTable resultTable = createJTable(searchFrame);
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();

        // as the first value in the combo box is make
        // set the default keyListener to make
        userInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyPressed = e.getKeyChar();

                // should only be able to enter text no numbers
                if (((keyPressed >= '0') && keyPressed <= '9' || keyPressed== KeyEvent.VK_SPACE) ){
                    e.consume();
                }
            }
        });


        // adds action listener to the searchButton
        searchButton.addActionListener(e -> {

           if (!userInput.getText().isEmpty()){
               // validates the user inputs
               if (validateInput(userInput.getText())){
                   // remove any values from a previous search
                   model.setRowCount(0);

                   // this will have get all the cars in the txt and depending on what the user has searched by filter them
                   // and return a list of them cars
                   List<Car> carList;
                   carList = ReadWriteHandler.txtFileToList(Main.path);

                   // need to create a List for the cars that match the search term
                   List<Car> carsThatMatch;
                   carsThatMatch = findSelectedCarsByTerm(userInput.getText(), carList, Objects.requireNonNull(carAttributeCombo.getSelectedItem()).toString());

                   for (Car car : carsThatMatch) {

                       String id = String.valueOf(car.getId());
                       String make = car.getMake();
                       String carModel = car.getModel();
                       String year = car.getYear();
                       String engineSize = String.valueOf(car.getEngineSize());
                       String mpg = String.valueOf(car.getMpg());
                       String acc = String.valueOf(car.getAcceleration());

                       model.addRow(new Object[]{id, make, carModel, year, engineSize, mpg, acc});

                   }
               }
           }
           else{
               JOptionPane.showOptionDialog(searchFrame, "Must Enter a Value !", null, JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);

           }


        });

        // updates the user input label dependent on what the user selects in the combo box
        // also sets all the keyboard listeners for the different selections the user can do
        carAttributeCombo.addActionListener(e ->{
            userInput.setText("");
            switch (Objects.requireNonNull(carAttributeCombo.getSelectedItem()).toString()){
                case "Make":
                    userInputLabel.setText("Enter Make :");
                    // don't allow numbers to be entered
                    removeKeyListenersForUserInput();
                    userInput.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char keyPressed = e.getKeyChar();

                            // should only be able to enter text no numbers
                            if (((keyPressed >= '0') && keyPressed <= '9' || keyPressed== KeyEvent.VK_SPACE) ){
                                e.consume();
                            }
                        }
                    });
                    break;
                case "Model":
                    userInputLabel.setText("Enter Model :");
                    // allow everything apart from spaces
                    removeKeyListenersForUserInput();
                    userInput.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char keyPressed = e.getKeyChar();

                            // should only be able to enter text no numbers
                            if (keyPressed== KeyEvent.VK_SPACE){
                                e.consume();
                            }
                        }
                    });

                    break;
                case "MPG":
                    userInputLabel.setText("Enter MPG :");
                    removeKeyListenersForUserInput();
                    userInput.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char keyPressed = e.getKeyChar();

                            if (!((keyPressed >= '0') && keyPressed <= '9' || keyPressed == KeyEvent.VK_PERIOD) ){
                                e.consume();
                            }
                        }
                    });
                    break;
                case "Engine Size":
                    userInputLabel.setText("Enter Engine Size :");
                    removeKeyListenersForUserInput();
                    userInput.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char keyPressed = e.getKeyChar();

                            if (!((keyPressed >= '0') && keyPressed <= '9' || keyPressed == KeyEvent.VK_PERIOD) ){
                                e.consume();
                            }
                        }
                    });
                    break;
                case "Year":
                    userInputLabel.setText("Enter Year :");
                    removeKeyListenersForUserInput();
                    userInput.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char keyPressed = e.getKeyChar();

                            if (!((keyPressed >= '0') && keyPressed <= '9') ){
                                e.consume();
                            }
                        }
                    });
                    break;
                case "Acceleration":
                    userInputLabel.setText("Enter Acceleration");
                    removeKeyListenersForUserInput();
                    userInput.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char keyPressed = e.getKeyChar();

                            if (!((keyPressed >= '0') && keyPressed <= '9' || keyPressed == KeyEvent.VK_PERIOD) ){
                                e.consume();
                            }
                        }
                    });
                    break;

            }
        });

        // adds an action listener to the backButton
        backButton.addActionListener(e -> {
            // this will close the current frame and open the main menu
            new MainMenu(searchFrame.getLocation());
            searchFrame.dispose();
        });

        // sets the frame to visible
        searchFrame.setVisible(true);
    }

    private List<Car> findSelectedCarsByTerm(String searchTerm, List<Car> cars, String comboBoxSelection) {

        // allow values that are lowercase and uppercase
        searchTerm = searchTerm.toLowerCase();
        String lowerValue = "";
        List<Car> returnCars = new ArrayList<>();
        for (Car car : cars) {
            switch (comboBoxSelection){
                case "Make":
                    // find all the Car for the make
                    lowerValue = car.getMake().toLowerCase();
                    if (lowerValue.equals(searchTerm))
                        returnCars.add(car);
                    break;
                case "Model":
                    // find all the Car for the model
                    lowerValue = car.getModel().toLowerCase();
                    if (lowerValue.equals(searchTerm))
                        returnCars.add(car);
                    break;
                case "Year":
                    // find all the Cars with the same year
                    if (car.getYear().equals(searchTerm))
                        returnCars.add(car);
                    break;
                case "MPG":
                    // find all the Cars with the same MPG
                    if (car.getMpg() == Double.parseDouble(searchTerm))
                        returnCars.add(car);
                    break;
                case "Engine Size":
                    // find all the Cars with the same Engine Size
                    if (car.getEngineSize() == Float.parseFloat(searchTerm))
                        returnCars.add(car);
                    break;
                case "Acceleration":
                    // find all the Cars with the same Acceleration
                    if (car.getAcceleration() == Double.parseDouble(searchTerm))
                        returnCars.add(car);
                    break;

            }
        }

        if (returnCars.size() == 0){
            JOptionPane.showOptionDialog(searchFrame, "No cars found!", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

        }
        return returnCars;
    }
    private JTable createJTable(JFrame frame){
        JTable table = new JTable();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(model);
        table.setRowSorter(tableRowSorter);


        model.addColumn("ID");
        model.addColumn("Make");
        model.addColumn("Model");
        model.addColumn("Year");
        model.addColumn("Engine Size");
        model.addColumn("MPG");
        model.addColumn("Acceleration");


        //center the table
        RecordsFrame.centerValuesInTable(table, SwingConstants.CENTER);


        // This disables the user to be able to edit the cells in the table
        table.setDefaultEditor(Object.class, null);
        // Adds the table to the frame
        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBounds(0,300,600,250);
        scrollPane.setVisible(true);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoscrolls(false);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frame.add(scrollPane);
        // center cells in table



        return table;
    }
    private void setBoundsForComponents(){
        carAttributeCombo.setBounds(100, 100, 400, 40);
        searchByLabel.setBounds(100, 70, 400, 40);
        userInput.setBounds(100, 175, 400, 40);
        userInputLabel.setBounds(100, 145, 400, 40);
        searchButton.setBounds(200, 225, 200, 50);
        backButton.setBounds(20, 20, 100, 50);
    }

    private void addComponentsToFrame(){
        searchFrame.add(carAttributeCombo);
        searchFrame.add(userInput);
        searchFrame.add(backButton);
        searchFrame.add(userInputLabel);
        searchFrame.add(searchByLabel);
        searchFrame.add(searchButton);

    }

    // validate the inputs for different choices
    private boolean validateInput(String searchTerm){

        boolean validInputs = false;

        switch (Objects.requireNonNull(carAttributeCombo.getSelectedItem()).toString()){

            case "MPG":
            case "Acceleration":
            case "Engine Size":
                validInputs = parseDoubleInput(searchTerm);
                break;


            case "Year":
                validInputs = checkYearBoxFormat(searchTerm);
                break;
            case "Make":
            case "Model":
                validInputs = true;
                break;


        }
        return validInputs;
    }



    // checks if the input is a double or a year
    private boolean parseDoubleInput(String input){

        boolean isValidDouble = false;

        try{
            // if it cant be parsed that the catch will catch the exception and print it to the screen
            // with a custom error message
            double parseCheck = Double.parseDouble(input);
            isValidDouble = true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(searchFrame, "Invalid Double", "Invalid Double", JOptionPane.ERROR_MESSAGE, null);
        }


        return isValidDouble;
    }

    private boolean checkYearBoxFormat(String input){

       try{
           int year = Integer.parseInt(input);
           // this ensures that the year is 4 characters long, and it is between 1886 which is when the first car was made
           // and the current year
           if (input.length() == 4 && year >= 1886 && year <= LocalDateTime.now().getYear()){
               return true;
           }

           JOptionPane.showMessageDialog(searchFrame, "Year must be between 1886 and " + LocalDateTime.now().getYear(), "Invalid Date", JOptionPane.ERROR_MESSAGE, null);


       }
       catch (Exception e){
           JOptionPane.showMessageDialog(searchFrame, "Year must be between 1886 and " + LocalDateTime.now().getYear(), "Invalid Date", JOptionPane.ERROR_MESSAGE, null);
       }

       return false;

    }

    private void removeKeyListenersForUserInput(){

        if (userInput.getKeyListeners().length > 0){
            // if it does have keyListeners remove them
            for (int i = 0; i < userInput.getKeyListeners().length; i++) {
                userInput.removeKeyListener(userInput.getKeyListeners()[i]);
            }
        }
    }


}
