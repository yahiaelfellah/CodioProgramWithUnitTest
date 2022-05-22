package dt;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

public class TruckExceptionTest {

    private static Class<TruckException> testClass;
    private static Constructor<TruckException>[] testConstructors;
    private static Field[] testFields;
    private static Method[] testMethods;

    private static TruckException testInstance;

    private static String parameter = "brand";

    private static Class<?>[] constExpectedParams = { String.class };

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
        testClass = TruckException.class;
        testConstructors = (Constructor<TruckException>[]) testClass.getDeclaredConstructors();
        testFields = testClass.getDeclaredFields();
        testMethods = testClass.getDeclaredMethods();

        if (checkConstructor()) {
            testConstructors[0].setAccessible(true);
            testInstance =
                    (TruckException) testConstructors[0].newInstance(parameter);
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

        Class<?> ancestor = testClass.getSuperclass();
        assertNotNull(ancestor);
        assertEquals(VehicleException.class, ancestor);

        assertEquals("dt", testClass.getPackage().getName());
    }

    @Test
    public void testParameter() throws IllegalAccessException {
        // Check that field exists
        Field testField = getAccessibleField("parameter");
        String previousValue = (String)testField.get(testInstance);

        // Check that getter exists
        //assertMethod("getParameter", String.class, Modifier.PUBLIC, 0);

        // Change field value
        String testValue = "model";
        testField.set(testInstance, testValue);

        // Test getter
        assertEquals(testValue, testInstance.getParameter());

        // Reset field value
        testField.set(testInstance, previousValue);
    }

    @Test
    public void testConstructor() throws IllegalAccessException {
        assertTrue(checkConstructor());

        // Check value
        assertEquals(parameter, getAccessibleField("parameter").get(testInstance));
    }

}
