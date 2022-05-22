import dt.Suv;
import dt.Truck;
import dt.Vehicle;
import dt.VehicleException;
import org.junit.Test;

import static org.junit.Assert.*;
import dt.*;
public class DriverTest {

    private static final float EPSILON = 1e-8f;

    private static void runTest(float expected, Vehicle[] vehicles) {
        assertEquals(expected, Driver.sumValues(vehicles), EPSILON);
    }

    @Test
    public void testSumValuesSuvs() throws VehicleException {
        Vehicle[] vehicles = {
                new Suv(
                        (byte)7, (byte)2, (byte)2,
                        "Pierre Maisonneuve", "Unknown",
                        "Honda", "Odyssey",
                        "FHG 789", 35980.f)
        };
        runTest(35980.f, vehicles);
    }

    @Test
    public void testSumValuesTrucks() throws VehicleException {
        Vehicle[] vehicles = {
                new Truck(
                        new String[] { "sand", "traffic cones" }, 1001L, "Ville de Montreal",
                        "Valerie Plante", "City Hall",
                        "Chevrolet", "Silverado",
                        "MTL 001", 43695.95f),
                new Truck(
                        new String[] { "sand", "traffic cones", "concrete", "trees", "flowers" }, 2001L, "Westmount",
                        "Michelle Desjardins", "75 Belvedere",
                        "Ford", "XLT",
                        "WMT 100", 21789.9f),
                new Truck(
                        new String[] { "traffic signs", "hats", "chairs" }, 4321_8765L, "DDC",
                        "Bob Loblaw", "Newport Beach",
                        "Chevrolet", "Corvette",
                        "BLL 707", 59495.f),
                new Truck(
                        new String[] { "planks", "paint", "screws", "nails", "tools" }, 7856_1289_4523L, "UQAM",
                        "Jeanne Tremblay", "1100 Notre-Dame",
                        "Fiat", "500X",
                        "ABC 123", 24890.5f),
                new Truck(
                        new String[] { "green screen", "projectors", "cameras" }, 200_6954L, "Rodeo FX",
                        "Justin Levesque", "1400 McGill",
                        "BMW", "X6",
                        "Unknown", 74500.28f)
        };
        runTest(224371.63f, vehicles);
    }

    @Test
    public void testSumValuesMixed() throws VehicleException {
        Vehicle[] vehicles = {
                new Truck(
                        new String[] { "sand", "traffic cones" }, 1001, "Ville de Montreal",
                        "Valerie Plante", "City Hall",
                        "Chevrolet", "Silverado",
                        "MTL 001", 39490.f),
                new Truck(
                        new String[] { "sand", "traffic cones", "concrete", "trees", "flowers" }, 2001, "Westmount",
                        "Michelle Desjardins", "75 Belvedere",
                        "Ford", "XLT",
                        "WMT 100", 27000.f),
                new Suv(
                        (byte)12, (byte)4, (byte)2,
                        "Jeanne Tremblay", "1100 Notre-Dame",
                        "Fiat", "500X",
                        "ABC 123", 24500.f)
        };
        runTest(90990.f, vehicles);
    }

    @Test
    public void testSumValuesEmpty() throws VehicleException {
        runTest(0.f, new Vehicle[] {});
    }

    @Test
    public void testSumValuesNull() throws VehicleException {
        runTest(0.f, null);
    }

}
