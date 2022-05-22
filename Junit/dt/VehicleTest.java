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

public class VehicleTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

//  private static final float EPSILON = 1e-8f;

    private static Class<Vehicle> testClass;
    private static Constructor<Vehicle>[] testConstructors;
    private static Constructor<Vehicle> validConst;
    private static Field[] testFields;
    private static Method[] testMethods;

    private static Vehicle testInstance;

    private static String ownerName = "Bob Loblaw";
    private static String ownerAddress = "Newport Beach";
    private static String brand = "Chevrolet";
    private static String model = "Corvette";
    private static String licencePlate = "BLL 707";
    private static float value = 59495.f;

    private static Class<?>[] constExpectedParams = {
            String.class, String.class,
            String.class, String.class,
            String.class, float.class
    };

    /**
     * Tests that one constructor matches the requirments
     */
    private static boolean softCheckConstructor() {
        boolean res = false;

        for (Constructor<Vehicle> curConst : testConstructors) {
            Class<?>[] constParams = curConst.getParameterTypes();
            if (constExpectedParams.length == constParams.length) {
                boolean validParamTypes = true; // All the parameter types are in order
                for (int i = 0; i < constParams.length; ++i) {
                    if (!(constParams[i].equals(constExpectedParams[i]))) {
                        validParamTypes = false;
                        break;
                    }
                }
                if (validParamTypes) {
                    res = true;
                    validConst = curConst;
                    break;
                }
            }
        }

        return res;
    }

    /**
     * Also checks that the valid constructor is the only one
     * and that it is protected
     */
    private static boolean hardCheckConstructor() {
        return softCheckConstructor() && testConstructors.length == 1
                && validConst.getModifiers() == Modifier.PROTECTED;
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testClass = Vehicle.class;
        testConstructors = (Constructor<Vehicle>[]) testClass.getDeclaredConstructors();
        testFields = testClass.getDeclaredFields();
        testMethods = testClass.getDeclaredMethods();

        if (softCheckConstructor()) {
            validConst.setAccessible(true);
            testInstance = (Vehicle) validConst.newInstance(
                    ownerName, ownerAddress,
                    brand, model,
                    licencePlate, value);
        }
    }

    private static Field getAccessibleField(String fieldName) {
        Field field = null;
        for(Field curField : testFields) {
            if (curField.getName().equals(fieldName)) {
                field = curField;
                field.setAccessible(true);
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
    public void testOwnerName() throws IllegalAccessException {
        // Check that field exists
        Field testField = assertField(
                "ownerName", String.class, Modifier.PRIVATE);

        // Check that getter exists
        assertMethod("getOwnerName", String.class, Modifier.PUBLIC, 0);

        // Change field value
//    testField.setAccessible(true);
        String testValue = "Jane Doe";
        testField.set(testInstance, testValue);

        // Test getter
        assertEquals(testValue, testInstance.getOwnerName());

        // Reset field value
        testField.set(testInstance, ownerName);
    }

    @Test
    public void testOwnerAddress() throws IllegalAccessException {
        // Check that field exists
        Field testField = assertField(
                "ownerAddress", String.class, Modifier.PRIVATE);

        // Check that getter exists
        assertMethod("getOwnerAddress", String.class, Modifier.PUBLIC, 0);

        // Change field value
//    testField.setAccessible(true);
        String testValue = "123 Abingdon";
        testField.set(testInstance, testValue);

        // Test getter
        assertEquals(testValue, testInstance.getOwnerAddress());

        // Reset field value
        testField.set(testInstance, ownerAddress);
    }

    @Test
    public void testBrand() {
        // Check that field exists
        assertField("brand", String.class, Modifier.PROTECTED);
    }

    @Test
    public void testModel() {
        // Check that field exists
        assertField("model", String.class, Modifier.PROTECTED);
    }

    @Test
    public void testLicencePlate() {
        // Check that field exists
        assertField("licencePlate", String.class, Modifier.PROTECTED);
    }

    @Test
    public void testConstructor1() {
        assertTrue(hardCheckConstructor());
    }

    @Test
    public void testConstructor2() throws IllegalArgumentException, IllegalAccessException {
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
                value;
        assertEquals(expected, testInstance.toString());
    }

    @Test
    public void testSetOwnerName() {
        String testValue = "Jane Doe";
        testInstance.setOwnerName(testValue);
        assertEquals(testValue, testInstance.getOwnerName());

        // Reset field value
        testInstance.setOwnerName(ownerName);
    }

    @Test
    public void testSetOwnerAddress() {
        String testValue = "123 Abingdon";
        testInstance.setOwnerAddress(testValue);
        assertEquals(testValue, testInstance.getOwnerAddress());

        // Reset field value
        testInstance.setOwnerName(ownerAddress);
    }

}
