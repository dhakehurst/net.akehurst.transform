/*
 * Copyright (c) 2015 D. David H. Akehurst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.akehurst.transformation.relations;

import java.util.Map;

public interface Relation {

	Transformer getTransformer();

	Class<?> getDomainType(DomainModelItentifier domainId);

	Object getDomainVariable(DomainModelItentifier domainId) throws RelationNotValidException;

	void setDomainVariable(DomainModelItentifier domainId, Object value) throws RelationNotValidException;

	boolean when(DomainModelItentifier targetDomain) throws RelationNotValidException;

	boolean check(DomainModelItentifier targetDomain) throws RelationNotValidException;

	boolean checkWhere(DomainModelItentifier targetDomain) throws RelationNotValidException;
	
	void enforce(DomainModelItentifier targetDomain) throws RelationNotValidException;
	
	void enforceWhere(DomainModelItentifier targetDomain) throws RelationNotValidException;
}
