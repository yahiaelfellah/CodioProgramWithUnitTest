package dt;

import org.junit.Test;

import static org.junit.Assert.*;

public class TruckExceptionThrowTest {

    // Truck
    private static String[] materials = { "traffic signs", "hats", "chairs" };
    private static long permit = 4321_8765L;
    private static String company = "DDC";

    // Vehicle
    private static String ownerName = "Denis Paquette";
    private static String ownerAddress = "Blackpool";
    private static String brand = "Renault";
    private static String model = "708";
    private static String licencePlate = "DPB 119";
    private static float value = 39900.f;

    @Test(expected = TruckException.class)
    public void testTruckErrorEmpty() throws VehicleException, TruckException {
        try {
            Truck test = new Truck(
                    new String[] {}, permit, company,
                    ownerName, ownerAddress,
                    brand, model,
                    licencePlate, value);
        } catch(TruckException te) {
            if (!te.getParameter().equals("materials")) {
                fail("TruckException thrown with wrong parameter");
            }
            throw te;
        }
    }

    @Test(expected = TruckException.class)
    public void testTruckErrorNull() throws VehicleException, TruckException {
        try {
            Truck test = new Truck(
                    null, permit, company,
                    ownerName, ownerAddress,
                    brand, model,
                    licencePlate, value);
        } catch(TruckException te) {
            if (!te.getParameter().equals("materials")) {
                fail("TruckException thrown with wrong parameter");
            }
            throw te;
        }
    }

    @Test(expected = TruckException.class)
    public void testTruckErrorLarge1() throws VehicleException, TruckException {
        try {
            Truck test = new Truck(
                    new String[] { "doors", "planks", "paint", "screws", "nails", "tools" }, permit, company,
                    ownerName, ownerAddress,
                    brand, model,
                    licencePlate, value);
        } catch(TruckException te) {
            if (!te.getParameter().equals("materials")) {
                fail("TruckException thrown with wrong parameter");
            }
            throw te;
        }
    }

    @Test(expected = TruckException.class)
    public void testTruckErrorLarge2() throws VehicleException, TruckException {
        try {
            Truck test = new Truck(
                    new String[] { "doors", "planks", "paint", "screws", "nails", "tools", "drill", "cable" }, permit, company,
                    ownerName, ownerAddress,
                    brand, model,
                    licencePlate, value);
        } catch(TruckException te) {
            if (!te.getParameter().equals("materials")) {
                fail("TruckException thrown with wrong parameter");
            }
            throw te;
        }
    }

    @Test
    public void testTruckValid1() throws VehicleException, TruckException {
        Truck test = new Truck(
                materials, permit, company,
                ownerName, ownerAddress,
                brand, model,
                licencePlate, value);
    }
    /*
    @Test
    public void testDriverTestTruck() throws VehicleException {
      try {
        Driver.testTruck();
      } catch(TruckException te) {
        fail("Exception should be caught in testTruck");
      }
    }
    */
    @Test
    public void testTruckValid2() throws VehicleException, TruckException {
        Truck test = new Truck(
                new String[] { "planks", "paint", "screws", "nails", "tools" }, permit, company,
                ownerName, ownerAddress,
                brand, model,
                licencePlate, value);
    }

}
