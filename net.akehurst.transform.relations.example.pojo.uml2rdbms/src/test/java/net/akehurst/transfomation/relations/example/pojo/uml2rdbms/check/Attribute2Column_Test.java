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
package net.akehurst.transfomation.relations.example.pojo.uml2rdbms.check;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.akehurst.transfomation.relations.example.pojo.uml2rdbms.AbstractTransformTest;
import net.akehurst.transfomation.relations.example.uml2rdbms.Uml2Rdbms;
import net.akehurst.transfomation.relations.example.uml2rdbms.rule.Attribute2Column;
import net.akehurst.transformation.relations.DomainModelItentifier;
import net.akehurst.transformation.relations.Relation;
import net.akehurst.transformation.relations.RelationNotApplicableException;
import net.akehurst.transformation.relations.RelationNotValidException;
import net.akehurst.transformation.relations.Transformer;

import org.junit.Assert;
import org.junit.Test;

import simpleRdbms.Column;
import simpleRdbms.SimpleRdbmsFactory;
import simpleUml.Attribute;
import simpleUml.PrimitiveDataType;
import simpleUml.SimpleUmlFactory;

public class Attribute2Column_Test extends AbstractTransformTest {

	protected void testFindMatch(DomainModelItentifier domainId, Class<? extends Relation> relationType, Attribute uml, Column rdbms, String prefix, boolean expected) {
		Map<DomainModelItentifier, Iterable<?>> domains = new HashMap<DomainModelItentifier, Iterable<?>>();
		domains.put(Uml2Rdbms.umlDomainId, Arrays.asList(uml));
		domains.put(Uml2Rdbms.rdbmsDomainId, Arrays.asList(rdbms));
		domains.put(Attribute2Column.prefixDomainId, Arrays.asList(prefix));

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
	
	@Test
	public void check_uml__name_name_true() {

		Attribute att = SimpleUmlFactory.createAttribute();
		PrimitiveDataType attType = SimpleUmlFactory.createPrimitiveDataType();
		attType.setName("String");
		att.setName("name");
		att.setType(attType);
		
		Column col = SimpleRdbmsFactory.createColumn();
		col.setName("name");
		col.setType("VARCHAR");
		
		String prefix = "";
		
		this.testFindMatch(Uml2Rdbms.umlDomainId, Attribute2Column.class,att, col, prefix, true);
		
	}

}
