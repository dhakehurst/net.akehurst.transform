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
package net.akehurst.transfomation.relations.example.uml2rdbms.rule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.akehurst.transfomation.relations.example.uml2rdbms.Uml2Rdbms;
import net.akehurst.transformation.relations.AbstractRelation;
import net.akehurst.transformation.relations.DomainModelItentifier;
import net.akehurst.transformation.relations.Relation;
import net.akehurst.transformation.relations.RelationNotApplicableException;
import net.akehurst.transformation.relations.RelationNotValidException;
import net.akehurst.transformation.relations.Transformer;

public class AbstractUml2RdbmsRelation extends AbstractRelation {

	public AbstractUml2RdbmsRelation(Transformer transformer) {
		super(transformer);
	}

	public Relation findRelation(DomainModelItentifier targetDomainId, Class<? extends Relation> relationType, Object uml, Object rdbms) throws RelationNotValidException, RelationNotApplicableException {
		Map<DomainModelItentifier, Object> domainArgs = new HashMap<DomainModelItentifier, Object>();
		domainArgs.put(Uml2Rdbms.umlDomainId, uml);
		domainArgs.put(Uml2Rdbms.rdbmsDomainId, rdbms);
		Relation relation = this.getTransformer().findRelation(targetDomainId, relationType, domainArgs);
		return relation;
	}
	
	public <T extends Relation> T findMatch(DomainModelItentifier targetDomainId, Class<T> relationType, Iterable<?> uml, Iterable<?> rdbms) throws RelationNotValidException, RelationNotApplicableException {
		Map<DomainModelItentifier, Iterable<?>> domains = new HashMap<DomainModelItentifier, Iterable<?>>();
		domains.put(Uml2Rdbms.umlDomainId, uml);
		domains.put(Uml2Rdbms.rdbmsDomainId, rdbms);
		Relation relation = this.getTransformer().findMatch(targetDomainId, relationType, domains);
		return (T)relation;
	}
	
	public boolean checkMatch(DomainModelItentifier targetDomainId, Class<? extends Relation> relationType, Object uml, Object rdbms) throws RelationNotValidException {
	
		try {
			
			Relation r = this.findMatch(targetDomainId, relationType, Arrays.asList(uml), Arrays.asList(rdbms));
			if (null!=r) {
				return true;
			}
		} catch (RelationNotApplicableException e) {
			
		}
		
		return false;
		
	}
	
	
}
