package model;

public class Car {


    private Long id;
    private String make;
    private String model;
    private String year;
    private float engineSize;
    private double mpg;
    private double acceleration;



    public Car(Long id, String make, String model, String year, float engineSize, double mpg, double acceleration) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.engineSize = engineSize;
        this.mpg = mpg;
        this.acceleration = acceleration;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public float getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(float engineSize) {
        this.engineSize = engineSize;
    }

    public double getMpg() {
        return mpg;
    }

    public void setMpg(double mpg) {
        this.mpg = mpg;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }
}
