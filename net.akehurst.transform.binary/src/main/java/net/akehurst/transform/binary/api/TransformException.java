/**
 * Copyright (C) 2015 Dr. David H. Akehurst (http://dr.david.h.akehurst.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.akehurst.transform.binary.api;

/**
 * Thrown by Rules that fail to perform the transformation.
 *
 * @author Dr. David H. Akehurst
 *
 */
public class TransformException extends RuntimeException {

    private static final long serialVersionUID = -1349802693285606128L;

    public TransformException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
