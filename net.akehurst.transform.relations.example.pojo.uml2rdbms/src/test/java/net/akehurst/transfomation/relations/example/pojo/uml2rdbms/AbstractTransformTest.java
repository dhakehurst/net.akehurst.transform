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
package net.akehurst.transfomation.relations.example.pojo.uml2rdbms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.akehurst.transfomation.relations.example.uml2rdbms.Uml2Rdbms;
import net.akehurst.transformation.relations.DomainModelItentifier;
import net.akehurst.transformation.relations.Relation;
import net.akehurst.transformation.relations.RelationNotApplicableException;
import net.akehurst.transformation.relations.RelationNotValidException;
import net.akehurst.transformation.relations.Transformer;

import org.junit.Assert;

public class AbstractTransformTest { 

	protected void testFindMatch(DomainModelItentifier domainId, Class<? extends Relation> relationType, List<?> uml, List<?> rdbms, boolean expected) {
		Map<DomainModelItentifier, Iterable<?>> domains = new HashMap<DomainModelItentifier, Iterable<?>>();
		domains.put(Uml2Rdbms.umlDomainId, uml);
		domains.put(Uml2Rdbms.rdbmsDomainId, rdbms);

		Transformer transformation = new Uml2Rdbms();
		try {
			
			Relation relation = transformation.findMatch(domainId, relationType, domains);
			
			Assert.assertNotNull(relation);
			
		} catch (RelationNotValidException e) {
			if (true==expected) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		} catch (RelationNotApplicableException e) {
			if (true==expected) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}
	}
	
	protected void testCheck(DomainModelItentifier domainId, List<?> uml, List<?> rdbms, boolean expected) {
		Map<DomainModelItentifier, Iterable<?>> domains = new HashMap<DomainModelItentifier, Iterable<?>>();
		domains.put(Uml2Rdbms.umlDomainId, uml);
		domains.put(Uml2Rdbms.rdbmsDomainId, rdbms);

		Transformer transformation = new Uml2Rdbms();
		try {
			
			boolean match = transformation.check(domainId, domains);
			
			Assert.assertEquals(expected, match);
			
		} catch (RelationNotValidException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	protected void testEnforce(DomainModelItentifier targetDomainId, List<?> uml, List<?> rdbms, boolean before, boolean after) {
		Map<DomainModelItentifier, Iterable<?>> domains = new HashMap<DomainModelItentifier, Iterable<?>>();
		domains.put(Uml2Rdbms.umlDomainId, uml);
		domains.put(Uml2Rdbms.rdbmsDomainId, rdbms);

		Transformer transformation = new Uml2Rdbms();
		try {
			boolean match = transformation.check(targetDomainId, domains);
			Assert.assertEquals(before,match);
			
			Map<DomainModelItentifier, Iterable<?>> newDomains = transformation.enforce( targetDomainId, domains);

			match = transformation.check(targetDomainId, newDomains);
			Assert.assertEquals(after,match);

		} catch (RelationNotValidException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
}
