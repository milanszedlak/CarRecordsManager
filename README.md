# Car Records Manager
Car Records Manager for University Assignment

This is my assignment for university where I had to make a records appication where it reads/writes to an text file,
It uses java.swing for the main GUI of the application and uses google.gson to create JSON objects directly from my custom classes

# Adding the Jars to the build path

For eclipse:

1. Right click on the project in the "project explorer" -> click on "build path" -> "configure build path"
2. Click on the "libraries" tab -> "class path" -> "add external jars" 
3. Select all the jars that are inluded in the project
4. click okay and apply and close


# Testing run configurations
1. go to the project and right click on "tests" package -> "run as" -> "run configurations"
2. go to JUnit -> "CarManagementSystem" -> "arguements" in the "vm arguements" paste:
3. --add-opens java.base/java.util=ALL-UNNAMED
4. click okay and apply and close
5. run the tests package with the configuration

(p.s - make sure to delete last car added after the testcases have ran, so when running it again it runs properly)

