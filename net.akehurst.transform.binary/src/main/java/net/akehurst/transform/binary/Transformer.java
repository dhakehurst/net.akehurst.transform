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
package net.akehurst.transform.binary;

import java.util.List;

public interface Transformer {
	
	<L, R> R transformLeft2Right(Class<? extends Relation<L,R>> ruleClass, L left)throws RelationNotFoundException;
	<L, R> List<? extends R> transformAllLeft2Right(Class<? extends Relation<L,R>> ruleClass, List<? extends L> leftObjects) throws RelationNotFoundException;
	
	<L, R> L transformRight2Left(Class<? extends Relation<L,R>> ruleClass, R right) throws RelationNotFoundException;
	<L, R> List<? extends L> transformAllRight2Left(Class<? extends Relation<L,R>> ruleClass, List<? extends R> rightObjects) throws RelationNotFoundException;

	void clear();
	List<Class<? extends Relation<?,?>>> getRuleTypes();
	void registerRule(Class<? extends Relation<?,?>> ruleType);

}
