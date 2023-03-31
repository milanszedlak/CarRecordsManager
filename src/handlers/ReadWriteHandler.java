package handlers;


import main.Main;
import model.Car;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReadWriteHandler {

    // this will convert the text file with the stored json data to a List of Cars (List<Car>)
    private static BufferedWriter writer;

    // Reads all the cars from the text file
    public static List<Car> txtFileToList(String path) {

        List<Car> cars = new ArrayList<>();
        // Reads the whole file to a String
        String allCarsString = "";
        try{
             allCarsString = new String(Files.readAllBytes(Paths.get(path)));
        }
        catch (IOException e){
            e.printStackTrace();
        }

        // Split the string up into array using the delimiter @
        String[] carsStringArray = allCarsString.split("@");

        // Convert the array of Strings to List of Cars
        for (String s : carsStringArray) {
            cars.add((Car) JsonConverter.fromJson(s, Car.class));
        }

        return cars;

    }
    // Deletes everything in the txt file and adds the new cars again

    public static void ListToTxtFile(List<Car> cars){

        try {
            // clear the text file
            writer = new BufferedWriter(new FileWriter(Main.path));
            writer.write("");

            // append is set to true to add the new car to the end of the file
            for (Car car : cars) {
                String jsonCar = JsonConverter.toJson(car) + "@";


                writer = new BufferedWriter(new FileWriter(Main.path, true));
                writer.append(jsonCar);

                writer.close();


            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // writes a single car to the end of the text file, it is used in the add method
    public static void writeACarToEndOfTxt(Car car){

        // Convert the car to a json string
        String jsonCar = JsonConverter.toJson(car);

        // adds the delimiter @ to the front of the string



        try {
            // append is set to true to add the new car to the end of the file
            writer = new BufferedWriter(new FileWriter(Main.path,true));
            writer.append(jsonCar).append("@");

            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void updateCarInTxt(Car car){

        // this will need to read all the text file, loop through it update the car and write it to the file
        List<Car> cars  = txtFileToList(Main.path);



        // this iterates through the cars and updates the car with the same id
        for (Car a : cars) {

            // finds the car with the same id
            if (Objects.equals(a.getId(), car.getId())){
                a.setMake(car.getMake());
                a.setModel(car.getModel());
                a.setYear(car.getYear());
                a.setEngineSize(car.getEngineSize());
                a.setMpg(car.getMpg());
                a.setAcceleration(car.getAcceleration());

                a.setMake(a.getMake().trim());
                a.setModel(a.getModel().trim());
                a.setYear(a.getYear().trim());



            }
        }



        // now we need to delete everything from the txt file and re-write it
        ListToTxtFile(cars);



    }

    public static void addCarInTxt(Car car){


        List<Car> currentCars = txtFileToList(Main.path);


        // get the last id of the car
        Long lastId = currentCars.get(currentCars.size() - 1).getId();
        if (car.getId() == null){
            car.setId(lastId + 1);
        }


        // if no id is entered then the id will be the last id + 1


        boolean carExists = lastId >= car.getId();

        if (!carExists){
            writeACarToEndOfTxt(car);
        }
        else {
            JOptionPane.showOptionDialog(null, "Car with id already exists!", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
        }





    }

    public static void deleteCarInTxt(Car car){

        // this will delete the selected car from the txt file
        // this will also change all the id's for every car so it nice and ordered

        List<Car> cars = txtFileToList(Main.path);


        Long deletedId = 0L;
        int index = 0;

        // this iterates through the cars and updates the car with the same id
        for (Car a : cars) {

            // finds the car with the same id
            if (Objects.equals(a.getId(), car.getId())){

                // this will delete the car from the list
                deletedId = a.getId();
                index = cars.indexOf(a);



            }


        }
        cars.remove(index);


        // now we want to decrement the id's which are bigger than the deleteId
        // is decrement a word? I don't know ill check on Google later

        for (Car b : cars ) {
            if (b.getId() > deletedId){
                b.setId(b.getId() - 1);
            }
        }


        ListToTxtFile(cars);







    }

}
