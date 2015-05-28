/*
 *                                                           ^ ^
 *                                                           O O
 *                                                         /    )
 *                                                        /  ,,
 *                                                       /
 *
 * Bubo Observer Library for Android
 *
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
 * Common tests for all {@link Registrar} implementations that guarantee ordering of observer callbacks.
 */
public abstract class OrdererdRegistrarTest extends RegistrarTest {


    // +----------------------------------------------------------------------+
    // | ORDERING TESTS
    // +----------------------------------------------------------------------+
    @Test
    public void testAddThreeDispatchOrder() {
        final Registrar<Object> testSubject = onCreateTestSubject();
        final Object testObserver0 = new Object();
        final Object testObserver1 = new Object();
        final Object testObserver2 = new Object();

        testSubject.addListener(testObserver0);
        testSubject.addListener(testObserver1);
        testSubject.addListener(testObserver2);

        assertOrderedDispatch(testSubject, testObserver0, testObserver1, testObserver2);
    }

    @Test
    public void testAddThreeDispatchReorder() {
        final Registrar<Object> testSubject = onCreateTestSubject();
        final Object testObserver0 = new Object();
        final Object testObserver1 = new Object();
        final Object testObserver2 = new Object();

        testSubject.addListener(testObserver0);
        testSubject.addListener(testObserver2);
        testSubject.addListener(testObserver1);
        testSubject.removeListener(testObserver2);
        testSubject.addListener(testObserver2);

        assertOrderedDispatch(testSubject, testObserver0, testObserver1, testObserver2);
    }

    // +----------------------------------------------------------------------+
    // | PRIVATE
    // +----------------------------------------------------------------------+
    private void assertOrderedDispatch(Registrar<?> testSubject, Object ... testObservers) {
        boolean[] foundSubject = new boolean[testObservers.length];
        int iterations = 0;

        for (Object listener : testSubject) {
            assertTrue(iterations < testObservers.length);
            int check = 0;
            while (check < iterations) {
                assertTrue(foundSubject[check++]);
            }
            while(check >= iterations && check < testObservers.length) {
                Object testObserver = testObservers[check];
                assertFalse(foundSubject[check]);
                if (testObserver == listener) {
                    foundSubject[check] = true;
                }
                check++;
            }

            ++iterations;
        }

        for (boolean found : foundSubject) {
            assertTrue(found);
        }
        assertEquals(iterations, testObservers.length);
    }

}