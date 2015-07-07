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
package net.akehurst.transfomation.relations.example.uml2rdbms;

import java.util.List;
import java.util.Vector;

import net.akehurst.transfomation.relations.example.uml2rdbms.rule.Class2Table;
import net.akehurst.transfomation.relations.example.uml2rdbms.rule.Package2Schema;
import net.akehurst.transformation.relations.AbstractTransformer;
import net.akehurst.transformation.relations.DomainModelItentifier;
import net.akehurst.transformation.relations.Relation;

/*
 * transformation umlToRdbms(uml:SimpleUML, rdbms:SimpleRDBMS)
 {
 key Table (name, schema);
 key Column (name, owner);   // owner:Table opposite column:Column
 key Key (name, owner); // key of class ëKeyí;
 // owner:Table opposite key:Key
 */

public class Uml2Rdbms extends AbstractTransformer {

	public static final DomainModelItentifier umlDomainId = new DomainModelItentifier(simpleUml.SimpleUmlFactory.class);
	public static final DomainModelItentifier rdbmsDomainId = new DomainModelItentifier(simpleRdbms.SimpleRdbmsFactory.class);
	
	public Uml2Rdbms() {

	}

	@Override
	public List<Class<? extends Relation>> getTopRelationType() {
		List<Class<? extends Relation>> result = new Vector<Class<? extends Relation>>();

		result.add(Package2Schema.class);
		result.add(Class2Table.class);

		return result;
	}

}
