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

/**
 * Interface common to all Bubo observer registrar objects.
 * TODO: document the Registrar contract.
 */
public interface Registrar<LISTENER_TYPE> extends Iterable<LISTENER_TYPE> {

    void addListener(LISTENER_TYPE listener);
    void removeListener(LISTENER_TYPE listener);
    void clear();

}
