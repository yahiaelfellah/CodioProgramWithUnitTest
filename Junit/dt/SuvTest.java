package dt;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class SuvTest {

    private static Class<Suv> testClass;
    private static Constructor<Suv>[] testConstructors;
    private static Field[] testFields;
    private static Method[] testMethods;

    private static Suv testInstance;

    private static byte capacity = 6;
    private static byte childSeatCount = 3;
    private static byte airbagCount = 5;
    private static String ownerName = "Pierre Poulain";
    private static String ownerAddress = "Sherbrooke";
    private static String brand = "Honda";
    private static String model = "Civic";
    private static String licencePlate = "SHE 861";
    private static float value = 35990.f;

    private static Class<?>[] constExpectedParams = {
            byte.class, byte.class, byte.class,
            String.class, String.class,
            String.class, String.class,
            String.class, float.class
    };

    private static boolean checkConstructor() {
        boolean res = true;

        if (testConstructors.length != 1 ||
                testConstructors[0].getModifiers() != Modifier.PUBLIC) {
            res = false;
        } else {
            Class<?>[] constParams = testConstructors[0].getParameterTypes();
            if (constExpectedParams.length != constParams.length) {
                res = false;
            } else {
                for (int i = 0; i < constParams.length; ++i) {
                    if (!(constParams[i].equals(constExpectedParams[i]))) {
                        res = false;
                        break;
                    }
                }
            }
        }

        return res;
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testClass = Suv.class;
        testConstructors = (Constructor<Suv>[]) testClass.getDeclaredConstructors();
        testFields = testClass.getDeclaredFields();
        testMethods = testClass.getDeclaredMethods();

        if (checkConstructor()) {
            testConstructors[0].setAccessible(true);
            testInstance = (Suv) testConstructors[0].newInstance(
                    capacity, childSeatCount, airbagCount,
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
    public void testCapacity() throws IllegalAccessException {
        // Check that field exists
        Field testField = assertField(
                "capacity", byte.class, Modifier.PRIVATE);
        byte previousValue = (byte)testField.get(testInstance);

        // Check that getter exists
        assertMethod("getCapacity", byte.class, Modifier.PUBLIC, 0);

        // Change field value
        byte testValue = 9;
        testField.set(testInstance, testValue);

        // Test getter
        assertEquals(testValue, testInstance.getCapacity());

        // Reset field value
        testField.set(testInstance, previousValue);
    }

    @Test
    public void testChildSeatCount() throws IllegalAccessException {
        // Check that field exists
        Field testField = assertField(
                "childSeatCount", byte.class, Modifier.PRIVATE);
        byte previousValue = (byte)testField.get(testInstance);

        // Check that getter exists
        assertMethod("getChildSeatCount", byte.class, Modifier.PUBLIC, 0);

        // Change field value
        byte testValue = 9;
        testField.set(testInstance, testValue);

        // Test getter
        assertEquals(testValue, testInstance.getChildSeatCount());

        // Reset field value
        testField.set(testInstance, previousValue);
    }

    @Test
    public void testAirbagCount() throws IllegalAccessException {
        // Check that field exists
        Field testField = assertField(
                "airbagCount", byte.class, Modifier.PRIVATE);
        byte previousValue = (byte)testField.get(testInstance);

        // Check that getter exists
        assertMethod("getAirbagCount", byte.class, Modifier.PUBLIC, 0);

        // Change field value
        byte testValue = 9;
        testField.set(testInstance, testValue);

        // Test getter
        assertEquals(testValue, testInstance.getAirbagCount());

        // Reset field value
        testField.set(testInstance, previousValue);
    }

    @Test
    public void testConstructor() throws IllegalAccessException {
        assertTrue(checkConstructor());

        // Check values

        assertEquals(capacity, testInstance.getCapacity());
        assertEquals(childSeatCount, testInstance.getChildSeatCount());
        assertEquals(airbagCount, testInstance.getAirbagCount());
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

        expected += capacity + ", " +
                childSeatCount + ", " +
                airbagCount;

        assertEquals(expected, testInstance.toString());
    }

    @Test
    public void testSetCapacity() throws IllegalAccessException {
        // Save current value
        Field testField = getAccessibleField("capacity");
        byte previousValue = (byte)testField.get(testInstance);

        byte expected = 4;
        testInstance.setCapacity(expected);
        byte testCapacity = (byte)(getAccessibleField("capacity").get(testInstance));
        assertEquals(expected, testCapacity);

        // Reset field value
        testField.set(testInstance, previousValue);
    }

    @Test
    public void testSetChildSeatCount() throws IllegalAccessException {
        // Save current value
        Field testField = getAccessibleField("childSeatCount");
        byte previousValue = (byte)testField.get(testInstance);

        byte expected = 1;
        testInstance.setChildSeatCount(expected);
        byte testChildSeatCount = (byte)(getAccessibleField("childSeatCount").get(testInstance));
        assertEquals(expected, testChildSeatCount);

        // Reset field value
        testField.set(testInstance, previousValue);
    }

    @Test
    public void testSetAirbagCount() throws IllegalAccessException {
        // Save current value
        Field testField = getAccessibleField("airbagCount");
        byte previousValue = (byte)testField.get(testInstance);

        byte expected = 3;
        testInstance.setAirbagCount(expected);
        byte testAirbagCount = (byte)(getAccessibleField("airbagCount").get(testInstance));
        assertEquals(expected, testAirbagCount);

        // Reset field value
        testField.set(testInstance, previousValue);
    }

}
