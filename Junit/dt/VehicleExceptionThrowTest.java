package dt;

import org.junit.Test;

import static org.junit.Assert.*;

public class VehicleExceptionThrowTest {

    // Truck
    private static String[] materials = { "traffic signs", "hats", "chairs" };
    private static long permit = 4321_8765L;
    private static String company = "DDC";

    // SUV
    private static byte capacity = 6;
    private static byte childSeatCount = 3;
    private static byte airbagCount = 5;

    // Vehicle
    private static String ownerName = "Denis Paquette";
    private static String ownerAddress = "Blackpool";
    private static String brand = "Renault";
    private static String model = "708";
    private static String licencePlate = "DPB 119";
    private static float value = 39900.f;

    @Test(expected = VehicleException.class)
    public void testTruckError1() throws VehicleException {
        try {
            Truck test = new Truck(
                    materials, permit, company,
                    ownerName, ownerAddress,
                    brand, "",
                    licencePlate, value);
        } catch(VehicleException ve) {
            if (!ve.getParameter().equals("model")) {
                fail("VehicleException thrown with wrong parameter");
            }
            throw ve;
        }
    }

    @Test(expected = VehicleException.class)
    public void testTruckError2() throws VehicleException {
        try {
            Truck test = new Truck(
                    materials, permit, "",
                    ownerName, ownerAddress,
                    brand, model,
                    licencePlate, value);
        } catch(VehicleException ve) {
            if (!ve.getParameter().equals("company")) {
                fail("VehicleException thrown with wrong parameter");
            }
            throw ve;
        }
    }

    @Test(expected = VehicleException.class)
    public void testSuvError1() throws VehicleException {
        try {
            Suv test = new Suv(
                    capacity, childSeatCount, airbagCount,
                    ownerName, ownerAddress,
                    "", model,
                    licencePlate, value);
        } catch(VehicleException ve) {
            if (!ve.getParameter().equals("brand")) {
                fail("VehicleException thrown with wrong parameter");
            }
            throw ve;
        }
    }

    @Test(expected = VehicleException.class)
    public void testSuvError2() throws VehicleException {
        try {
            Suv test = new Suv(
                    capacity, childSeatCount, airbagCount,
                    "", ownerAddress,
                    brand, model,
                    licencePlate, value);
        } catch(VehicleException ve) {
            if (!ve.getParameter().equals("ownerName")) {
                fail("VehicleException thrown with wrong parameter");
            }
            throw ve;
        }
    }

    @Test
    public void testTruckValid() throws VehicleException {
        Truck test = new Truck(
                materials, permit, company,
                ownerName, ownerAddress,
                brand, model,
                licencePlate, value);
    }

    @Test
    public void testSuvValid() throws VehicleException {
        Suv test = new Suv(
                capacity, childSeatCount, airbagCount,
                ownerName, ownerAddress,
                brand, model,
                licencePlate, value);
    }

}
