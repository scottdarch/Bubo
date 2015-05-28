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
package com.fiftythree.bubo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Marks a Registrar implementation that discards observer registration ordering to gain other advantages. This
 * means that the registrar's iterator may return each observer using an arbitrary or even unstable (i.e. between
 * iterations) ordering. It does <em>not</em> allow the registrar's iterator to skip observers or return the
 * same observer twice.
 */
@Target({ElementType.TYPE})
public @interface Unordered {
}
