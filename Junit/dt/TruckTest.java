package dt;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

public class TruckTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    private static Class<Truck> testClass;
    private static Constructor<Truck>[] testConstructors;
    private static Field[] testFields;
    private static Method[] testMethods;

    private static Truck testInstance;

    private static String[] materials = { "traffic signs", "hats", "chairs" };
    private static long permit = 4321_8765L;
    private static String company = "DDC";
    private static String ownerName = "Denis Paquette";
    private static String ownerAddress = "Blackpool";
    private static String brand = "Renault";
    private static String model = "708";
    private static String licencePlate = "DPB 119";
    private static float value = 39900.f;

    private static Class<?>[] constExpectedParams = {
            String[].class, long.class, String.class,
            String.class, String.class,
            String.class, String.class,
            String.class, float.class
    };

    /**
     * @return Empty String if no error
     */
    private static String checkConstructor() {
        String res = "";

        if (testConstructors.length != 1 ||
                testConstructors[0].getModifiers() != Modifier.PUBLIC) {
            res += "Wrong modifier";
        } else {
            Class<?>[] constParams = testConstructors[0].getParameterTypes();
            if (constExpectedParams.length != constParams.length) {
                res += "Invalid number of parameters";
            } else {
                for (int i = 0; i < constParams.length; ++i) {
                    if (!(constParams[i].equals(constExpectedParams[i]))) {
                        res += "Invalid parameter type at position " + i;
                        res += ": expected " + constExpectedParams[i].getSimpleName()
                                + " but found " + constParams[i].getSimpleName();
                        break;
                    }
                }
            }
        }

        if (!res.isEmpty()) {
            res = "Truck constructor is not valid\n" + res;
        }

        return res;
    }

    private static void assertConstructor() {
        String res = checkConstructor();
        if (res != "") {
            fail(res);
        }
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testClass = Truck.class;
        testConstructors = (Constructor<Truck>[]) testClass.getDeclaredConstructors();
        testFields = testClass.getDeclaredFields();
        testMethods = testClass.getDeclaredMethods();

        assertConstructor();

        testConstructors[0].setAccessible(true);
        testInstance = (Truck) testConstructors[0].newInstance(
                materials, permit, company,
                ownerName, ownerAddress,
                brand, model,
                licencePlate, value);
    }

    private static Field getAccessibleField(String fieldName) {
        Field field = null;

        for(Field curField : testFields) {
            if (curField.getName().equals(fieldName)) {
                field = curField;
                field.setAccessible(true);
            }
        }

        if (field == null) {
            Field[] superFields = testClass.getSuperclass().getDeclaredFields();
            for(Field curField : superFields) {
                if (curField.getName().equals(fieldName)) {
                    field = curField;
                    field.setAccessible(true);
                }
            }
        }

        return field;
    }

    private static Field assertField(
            String fieldName,
            Class<?> type,
            int modifier) {
        boolean foundField = false;
        Field resField = getAccessibleField(fieldName);
        if (resField != null) {
            // Test that it is the right type
            assertEquals(type, resField.getType());

            // Test that it is private
            assertEquals("Wrong modifier for field '" + fieldName + "'",
                    modifier, resField.getModifiers());

            foundField = true;
        }

        // Test that we found it
        assertTrue("Field '" + fieldName + "' was not found", foundField);
        return resField;
    }

    private static void assertMethod(
            String methodName,
            Class<?> returnType,
            int modifier,
            int paramCount) {
        boolean foundMethod = false;
        for(Method curMethod : testMethods) {
            // Check the signature
            if (curMethod.getName().equals(methodName) && curMethod.getParameterTypes().length == paramCount) {
                // Test that it is the right return type
                assertEquals(returnType, curMethod.getReturnType());

                // Test that it is public
                assertEquals("Wrong modifier for method '" + methodName + "'",
                        modifier, curMethod.getModifiers());

                foundMethod = true;
                break;
            }
        }

        // Test that we found it
        assertTrue("Method '" + methodName + "' was not found", foundMethod);
    }

    @Test
    public void testClass() {
        assertEquals(Modifier.PUBLIC, testClass.getModifiers() & Modifier.PUBLIC);
        assertEquals(Modifier.FINAL, testClass.getModifiers() & Modifier.FINAL);

        Class<?> ancestor = testClass.getSuperclass();
        assertNotNull(ancestor);
        assertEquals(Vehicle.class, ancestor);
    }

    @Test
    public void testMaterials() throws IllegalAccessException {
        // Check that field exists
        Field testField = assertField(
                "materials", String[].class, Modifier.PRIVATE);

        // Check values
        String[] testMaterials = (String[])(testField.get(testInstance));
        assertEquals(5, testMaterials.length);
        for (int i = 0; i < testMaterials.length; ++i) {
            String testMaterial = testMaterials[i];
            if (i < materials.length) {
                assertEquals(materials[i], testMaterial);
            } else {
                assertNull(testMaterial);
            }
        }

        // Check that getter exists
        assertMethod("getMaterials", String[].class, Modifier.PUBLIC, 0);

        // Test getter
        String[] gottenMaterials = testInstance.getMaterials();
        assertEquals(5, gottenMaterials.length);
        for (int i = 0; i < gottenMaterials.length; ++i) {
            String gottenMaterial = gottenMaterials[i];
            if (i < materials.length) {
                assertEquals(materials[i], gottenMaterial);
            } else {
                assertNull(gottenMaterial);
            }
        }
    }

    @Test
    public void testPermit() throws IllegalAccessException {
        // Check that field exists
        Field testField = assertField(
                "permit", long.class, Modifier.PRIVATE);
        long previousValue = (long)testField.get(testInstance);

        // Check that getter exists
        assertMethod("getPermit", long.class, Modifier.PUBLIC, 0);

        // Change field value
        long testValue = 11_22_99_88L;
        testField.set(testInstance, testValue);

        // Test getter
        assertEquals(testValue, testInstance.getPermit());

        // Reset field value
        testField.set(testInstance, previousValue);
    }

    @Test
    public void testCompany() throws IllegalAccessException {
        // Check that field exists
        Field testField = assertField(
                "company", String.class, Modifier.PRIVATE);
        String previousValue = (String)testField.get(testInstance);

        // Check that getter exists
        assertMethod("getCompany", String.class, Modifier.PUBLIC, 0);

        // Change field value
        String testValue = "ETS";
        testField.set(testInstance, testValue);

        // Test getter
        assertEquals(testValue, testInstance.getCompany());

        // Reset field value
        testField.set(testInstance, previousValue);
    }

    @Test
    public void testConstructor() throws IllegalAccessException {
        assertConstructor();

        // Check values

        assertEquals(permit, testInstance.getPermit());
        assertEquals(company, testInstance.getCompany());
        assertEquals(ownerName, testInstance.getOwnerName());
        assertEquals(ownerAddress, testInstance.getOwnerAddress());
        assertEquals(model, getAccessibleField("model").get(testInstance));
        assertEquals(brand, getAccessibleField("brand").get(testInstance));
        assertEquals(licencePlate, getAccessibleField("licencePlate").get(testInstance));
        assertEquals(value, getAccessibleField("value").get(testInstance));
    }

    @Test
    public void testToString() {
        String expected = ownerName + ", " +
                ownerAddress + ", " +
                brand + ", " +
                model + ", " +
                licencePlate + ", " +
                value + ", ";

        expected += "[";
        for (String curMat : materials) {
            expected += "/" + curMat + "/";
        }
        expected += "], " +
                permit + ", " +
                company;

        assertEquals(expected, testInstance.toString());
    }

    @Test
    public void testSetMaterials1() throws IllegalAccessException {
        // Smaller
        String[] expectedMaterials = { "potatoes" };

        Field testField = getAccessibleField("materials");
        String[] previousMaterials = (String[])(testField.get(testInstance));

        try {
            testInstance.setMaterials(expectedMaterials);
        } catch(ArrayIndexOutOfBoundsException aioobe) {
            fail(aioobe.getMessage());
        }

        String[] testMaterials = (String[])(testField.get(testInstance));
        assertEquals(5, testMaterials.length);
        for (int i = 0; i < testMaterials.length; ++i) {
            String testMaterial = testMaterials[i];
            if (i < expectedMaterials.length) {
                assertEquals(expectedMaterials[i], testMaterial);
            } else {
                assertNull(testMaterial);
            }
        }

        // Reset field value
        testField.set(testInstance, previousMaterials);
    }

    @Test
    public void testSetMaterials2() throws IllegalAccessException {
        // Larger
        String[] expectedMaterials = { "tent", "wood", "beer",
                "sleeping bags", "clothes", "food",
                "swimsuits", "towels" };

        Field testField = getAccessibleField("materials");
        String[] previousMaterials = (String[])(testField.get(testInstance));

        try {
            testInstance.setMaterials(expectedMaterials);
        } catch(ArrayIndexOutOfBoundsException aioobe) {
            fail(aioobe.getMessage());
        }

        String[] testMaterials = (String[])(testField.get(testInstance));
        assertEquals(5, testMaterials.length);
        for (int i = 0; i < testMaterials.length; ++i) {
            String testMaterial = testMaterials[i];
            assertEquals(expectedMaterials[i], testMaterial);
        }

        // Reset field value
        testField.set(testInstance, previousMaterials);
    }

    @Test
    public void testSetPermit() throws IllegalAccessException {
        // Save current value
        Field testField = getAccessibleField("permit");
        long previousValue = (long)testField.get(testInstance);

        long expected = 102938_666L;
        testInstance.setPermit(expected);
        long testPermit = (long)(getAccessibleField("permit").get(testInstance));
        assertEquals(expected, testPermit);

        // Reset field value
        testField.set(testInstance, previousValue);
    }

    @Test
    public void testSetCompany() throws IllegalAccessException {
        // Save current value
        Field testField = getAccessibleField("company");
        String previousValue = (String)testField.get(testInstance);

        String expected = "National Parks";
        testInstance.setCompany(expected);
        String testCompany = (String)(getAccessibleField("company").get(testInstance));
        assertEquals(expected, testCompany);

        // Reset field value
        testField.set(testInstance, previousValue);
    }

}
