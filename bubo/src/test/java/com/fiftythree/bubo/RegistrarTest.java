/*
 *
 *                                ^ ^
 *                                O O
 *                              /    )
 *                             /  ,,
 * Bubo – Observable Contracts and Specialized Implementations.
 *                              |/
 * Copyright 2015 FiftyThree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fiftythree.bubo;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Common tests for all {@link Registrar} implementations.
 */
public abstract class RegistrarTest extends TestCase {

    // +----------------------------------------------------------------------+
    // | TEST TEMPLATE METHODS
    // +----------------------------------------------------------------------+
    protected abstract Registrar<Object> onCreateTestSubject();

    // +----------------------------------------------------------------------+
    // | NULL ARGUMENT TESTS
    // +----------------------------------------------------------------------+
    @Test
    public void testIAEForAddListener() {
        try {
            final Registrar<Object> testSubject = onCreateTestSubject();
            testSubject.addListener(null);
            fail("IAE was not thrown");
        } catch (IllegalArgumentException e) {
            // okay
        }
    }

    @Test
    public void testIAEForRemoveListener() {
        try {
            final Registrar<Object> testSubject = onCreateTestSubject();
            testSubject.removeListener(null);
            fail("IAE was not thrown");
        } catch (IllegalArgumentException e) {
            // okay
        }
    }

    // +----------------------------------------------------------------------+
    // | ADD ONE TESTS
    // +----------------------------------------------------------------------+
    @Test
    public void testAddIterateRemove() {
        final Registrar<Object> testSubject = onCreateTestSubject();
        final Object testObserver = new Object();

        testSubject.addListener(testObserver);

        int iterations = 0;
        boolean foundSubject = false;
        for (Object listener : testSubject) {
            if (listener == testObserver) {
                foundSubject = true;
            }
            ++iterations;
        }
        assertTrue(foundSubject);
        assertEquals(1, iterations);
    }

    @Test
    public void testAddRemoveIterate() {
        final Registrar<Object> testSubject = onCreateTestSubject();
        final Object testObserver = new Object();

        testSubject.addListener(testObserver);
        testSubject.removeListener(testObserver);
        boolean foundSubject = false;

        int iterations = 0;
        for (Object listener : testSubject) {
            if (listener == testObserver) {
                foundSubject = true;
            }
            ++iterations;
        }
        assertFalse(foundSubject);
        assertEquals(0, iterations);
    }

    @Test
    public void testAddTwiceIterate() {
        final Registrar<Object> testSubject = onCreateTestSubject();
        final Object testObserver = new Object();

        testSubject.addListener(testObserver);
        testSubject.addListener(testObserver);
        boolean foundSubject = false;

        int iterations = 0;
        for (Object listener : testSubject) {
            if (listener == testObserver) {
                foundSubject = true;
            }
            ++iterations;
        }
        assertTrue(foundSubject);
        assertEquals(1, iterations);
    }

    @Test
    public void testAddTwiceRemoveOnceIterate() {
        final Registrar<Object> testSubject = onCreateTestSubject();
        final Object testObserver = new Object();

        testSubject.addListener(testObserver);
        testSubject.addListener(testObserver);
        testSubject.removeListener(testObserver);
        boolean foundSubject = false;

        int iterations = 0;
        for (Object listener : testSubject) {
            if (listener == testObserver) {
                foundSubject = true;
            }
            ++iterations;
        }
        assertFalse(foundSubject);
        assertEquals(0, iterations);
    }

    @Test
    public void testAddOnceClear() {
        final Registrar<Object> testSubject = onCreateTestSubject();
        final Object testObserver = new Object();

        testSubject.addListener(testObserver);
        testSubject.clear();
        boolean foundSubject = false;

        int iterations = 0;
        for (Object listener : testSubject) {
            if (listener == testObserver) {
                foundSubject = true;
            }
            ++iterations;
        }
        assertFalse(foundSubject);
        assertEquals(0, iterations);
    }

    @Test
    public void testAddOneRemoveWhileDispatching() {
        final Registrar<Object> testSubject = onCreateTestSubject();
        final Object testObserver = new Object();

        testSubject.addListener(testObserver);
        boolean foundSubject = false;

        int iterations = 0;
        for (Object listener : testSubject) {
            if (listener == testObserver) {
                foundSubject = true;
                testSubject.removeListener(testSubject);
            }
            ++iterations;
        }
        assertTrue(foundSubject);
        assertEquals(1, iterations);
    }

    // +----------------------------------------------------------------------+
    // | ADD TWO TESTS
    // +----------------------------------------------------------------------+
    @Test
    public void testAddTwoIterate() {
        final Registrar<Object> testSubject = onCreateTestSubject();
        final Object testObserver0 = new Object();
        final Object testObserver1 = new Object();

        testSubject.addListener(testObserver0);
        testSubject.addListener(testObserver1);

        boolean foundSubject0 = false;
        boolean foundSubject1 = false;

        int iterations = 0;
        for (Object listener : testSubject) {
            ++iterations;
            if (listener == testObserver0) {
                foundSubject0 = true;
            } else if (listener == testObserver1) {
                foundSubject1 = true;
            }
        }
        assertTrue(foundSubject0);
        assertTrue(foundSubject1);
        assertEquals(2, iterations);
    }

    @Test
    public void testAddTwoRemoveOneTwice() {
        final Registrar<Object> testSubject = onCreateTestSubject();
        final Object testObserver0 = new Object();
        final Object testObserver1 = new Object();

        testSubject.addListener(testObserver0);
        testSubject.addListener(testObserver1);
        testSubject.removeListener(testObserver0);
        testSubject.removeListener(testObserver0);

        boolean foundSubject0 = false;
        boolean foundSubject1 = false;

        int iterations = 0;
        for (Object listener : testSubject) {
            ++iterations;
            if (listener == testObserver0) {
                foundSubject0 = true;
            } else if (listener == testObserver1) {
                foundSubject1 = true;
            }
        }
        assertFalse(foundSubject0);
        assertTrue(foundSubject1);
        assertEquals(1, iterations);
    }

    @Test
    public void testAddTwoRemoveWhileDispatching() {
        final Registrar<Object> testSubject = onCreateTestSubject();
        final Object testObserver0 = new Object();
        final Object testObserver1 = new Object();

        testSubject.addListener(testObserver0);
        testSubject.addListener(testObserver1);

        boolean foundSubject0 = false;
        boolean foundSubject1 = false;

        int iterations = 0;
        for (Object listener : testSubject) {
            ++iterations;
            if (listener == testObserver0) {
                foundSubject0 = true;
                testSubject.removeListener(testObserver0);
            } else if (listener == testObserver1) {
                foundSubject1 = true;
            }
        }

        assertTrue(foundSubject0);
        assertTrue(foundSubject1);
        assertEquals(2, iterations);

        // reset and try again
        foundSubject0 = false;
        foundSubject1 = false;

        iterations = 0;
        for (Object listener : testSubject) {
            ++iterations;
            if (listener == testObserver0) {
                foundSubject0 = true;
                testSubject.removeListener(testSubject);
            } else if (listener == testObserver1) {
                foundSubject1 = true;
            }
        }

        assertFalse(foundSubject0);
        assertTrue(foundSubject1);
        assertEquals(1, iterations);
    }

    @Test
    public void testAddTwoRemoveSecondWhileDispatching() {
        final Registrar<Object> testSubject = onCreateTestSubject();
        final Object testObserver0 = new Object();
        final Object testObserver1 = new Object();

        testSubject.addListener(testObserver0);
        testSubject.addListener(testObserver1);

        boolean foundSubject0 = false;
        boolean foundSubject1 = false;

        int iterations = 0;
        for (Object listener : testSubject) {
            ++iterations;
            if (listener == testObserver0) {
                foundSubject0 = true;
                testSubject.removeListener(testObserver1);
            } else if (listener == testObserver1) {
                foundSubject1 = true;
            }
        }

        assertTrue(foundSubject0);
        assertTrue(foundSubject1);
        assertEquals(2, iterations);
    }

}