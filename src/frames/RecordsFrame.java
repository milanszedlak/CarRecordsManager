package frames;

import handlers.ReadWriteHandler;
import main.Main;
import model.Car;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class RecordsFrame{

    public JFrame recordFrame = new JFrame();

    private Car selectedCar;
    public RecordsFrame(JFrame frame) {

        // Creates all the buttons for the frame
        final JButton deleteButton = new JButton("Delete Car");
        final JButton editButton = new JButton("Edit Car");
        final JButton addButton = new JButton("Add Car");
        final JButton backButton = new JButton("Back");
        // creates the car which will be used to store the selected car



        // initialises all the settings for the frame

        recordFrame.setSize(frame.getSize());
        recordFrame.setLocation(frame.getX(), frame.getY());
        recordFrame.setLayout(null);
        recordFrame.setBounds(frame.getBounds());
        recordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        recordFrame.setResizable(false);
        recordFrame.setTitle("Car Records");
        recordFrame.setVisible(true);

        // gets all the cars from the txt file
        List<Car> cars;
        cars = ReadWriteHandler.txtFileToList(Main.path);

        // Create a JTable
        JTable table = createJTable(recordFrame, cars);
        DefaultTableModel model = (DefaultTableModel) table.getModel();


        // set all the bounds for the components and add them to the frame
        setBoundsForComponents(recordFrame,addButton, deleteButton, editButton, backButton);






        // mouse listener for the table, so it can enable the edit and delete buttons when the user selects a row

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                // this only accounts for left click of the button
                if (e.getButton() == MouseEvent.BUTTON1){
                    // set to delete and edit buttons
                    deleteButton.setEnabled(true);
                    editButton.setEnabled(true);

                    String selectedRow= model.getDataVector().elementAt(table.convertRowIndexToModel(table.getSelectedRow())).toString();
                    // need to convert the string to a car

                    // needed to remove the "[" and the "]" from the string, so I can add it to a Car
                    selectedRow = selectedRow.replaceAll("[\\[\\]]", "");
                    String[] selectedRowData = selectedRow.split(",");

                    float engineSize = Float.parseFloat(selectedRowData[4]);

                    selectedCar = new Car(Long.parseLong(selectedRowData[0]), selectedRowData[1],selectedRowData[2], selectedRowData[3], engineSize, Double.parseDouble(selectedRowData[5]), Double.parseDouble(selectedRowData[6]));
                }
            }
        });



        // addButton event listener
        addButton.addActionListener(e -> {



            new ModifyCars(recordFrame, selectedCar, 2);

            recordFrame.dispose();

        });

        // editButton event Listener
        editButton.addActionListener(e -> {
            new ModifyCars(recordFrame, selectedCar, 1);
            recordFrame.dispose();

        });

        // deleteButton event listener
        deleteButton.addActionListener(e -> {

            new ModifyCars(recordFrame, selectedCar, 3);

            recordFrame.dispose();


        });

        // deleteButton event listener
        backButton.addActionListener(e -> {
            // this will just return to the main menu

            new MainMenu(recordFrame.getLocation());
            recordFrame.dispose();


        });



    }
    public static void centerValuesInTable(JTable table, int align){
        // this just centers all the cells in the table, so it looks good
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(align);

        TableModel newModel = table.getModel();

        for (int i = 0; i < newModel.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

    }

    public JTable createJTable(JFrame frame, List<Car> cars){
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


        // populate the table from the cars list
        for (Car car : cars) {

            String id = String.valueOf(car.getId());
            String make = car.getMake();
            String carModel = car.getModel();
            String year = car.getYear();
            String engineSize = String.valueOf(car.getEngineSize());
            String mpg = String.valueOf(car.getMpg());
            String acc = String.valueOf(car.getAcceleration());

            model.addRow(new Object[]{id, make, carModel, year, engineSize, mpg, acc});

        }


        // This disables the user to be able to edit the cells in the table
        table.setDefaultEditor(Object.class, null);
        // Adds the table to the frame
        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBounds(0,100,600,250);
        scrollPane.setVisible(true);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoscrolls(false);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frame.add(scrollPane);
        // center cells in table
        centerValuesInTable(table, SwingConstants.CENTER);


        return table;
    }

    public void setBoundsForComponents(JFrame recordFrame, JButton addButton,
                                       JButton deleteButton, JButton editButton, JButton backButton){
        addButton.setBounds(200, 350, 200, 50);
        deleteButton.setBounds(200, 450, 200, 50);
        editButton.setBounds(200, 400, 200, 50);

        backButton.setBounds(20,20,100,50);
        // the Delete and Edit buttons should be disabled for now until the user selects a record
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        recordFrame.add(addButton);
        recordFrame.add(deleteButton);
        recordFrame.add(editButton);
        recordFrame.add(backButton);

        // set the name for every component
        addButton.setName("addButton");
        deleteButton.setName("deleteButton");
        editButton.setName("editButton");
        backButton.setName("backButton");

    }



}
