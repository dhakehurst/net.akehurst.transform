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
 * 
 * This is based on the original works that contributed to
 * SiTra [http://www.cs.bham.ac.uk/~bxb/Sitra/index.html]
 *
 * Contributors:
 * 	Dr. David H. Akehurst
 *  Dr. Behzad Bordbar
 */
package net.akehurst.transform.simple;

import java.util.List;

public interface Transformer {
	
	<S, T> T transform(Class<? extends Relation> ruleType, S source) throws RelationNotFoundException;
	<S, T> List<? extends T> transformAll(Class<? extends Relation> ruleType, List<? extends S> element) throws RelationNotFoundException;
	
}
