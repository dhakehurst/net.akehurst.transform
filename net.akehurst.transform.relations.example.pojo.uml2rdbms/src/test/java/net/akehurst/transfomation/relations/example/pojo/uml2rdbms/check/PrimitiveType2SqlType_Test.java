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
import java.util.List;

import net.akehurst.transfomation.relations.example.pojo.uml2rdbms.AbstractTransformTest;
import net.akehurst.transfomation.relations.example.uml2rdbms.Uml2Rdbms;
import net.akehurst.transfomation.relations.example.uml2rdbms.rule.PrimitiveType2SqlType;

import org.junit.Test;

public class PrimitiveType2SqlType_Test extends AbstractTransformTest {

	@Test
	public void check_uml__Integer_NUMBER_true() {

		String ut = "Integer";
		String rt = "NUMBER";
		
		List<? extends Object> uml = Arrays.asList(ut);
		List<? extends Object> rdbms = Arrays.asList(rt);

		this.testFindMatch(Uml2Rdbms.umlDomainId, PrimitiveType2SqlType.class, uml, rdbms, true);
		
	}
	
	@Test
	public void check_rdbms__Integer_NUMBER_true() {

		String ut = "Integer";
		String rt = "NUMBER";
		
		List<? extends Object> uml = Arrays.asList(ut);
		List<? extends Object> rdbms = Arrays.asList(rt);

		this.testFindMatch(Uml2Rdbms.rdbmsDomainId, PrimitiveType2SqlType.class, uml, rdbms, true);
		
	}
	
	@Test
	public void check_uml__String_VARCHAR_true() {

		String ut = "String";
		String rt = "VARCHAR";
		
		List<? extends Object> uml = Arrays.asList(ut);
		List<? extends Object> rdbms = Arrays.asList(rt);

		this.testFindMatch(Uml2Rdbms.umlDomainId, PrimitiveType2SqlType.class, uml, rdbms, true);
		
	}

	@Test
	public void check_rdbms__String_VARCHAR_true() {

		String ut = "String";
		String rt = "VARCHAR";
		
		List<? extends Object> uml = Arrays.asList(ut);
		List<? extends Object> rdbms = Arrays.asList(rt);

		this.testFindMatch(Uml2Rdbms.rdbmsDomainId, PrimitiveType2SqlType.class, uml, rdbms, true);
		
	}

	@Test
	public void check_uml__Boolean_BOOLEAN_true() {

		String ut = "Boolean";
		String rt = "BOOLEAN";
		
		List<? extends Object> uml = Arrays.asList(ut);
		List<? extends Object> rdbms = Arrays.asList(rt);

		this.testFindMatch(Uml2Rdbms.umlDomainId, PrimitiveType2SqlType.class, uml, rdbms, true);
		
	}

	@Test
	public void check_rdbms__Boolean_BOOLEAN_true() {

		String ut = "Boolean";
		String rt = "BOOLEAN";
		
		List<? extends Object> uml = Arrays.asList(ut);
		List<? extends Object> rdbms = Arrays.asList(rt);

		this.testFindMatch(Uml2Rdbms.rdbmsDomainId, PrimitiveType2SqlType.class, uml, rdbms, true);
		
	}

	@Test
	public void check_uml__Boolean_VARCHAR_false() {

		String ut = "Boolean";
		String rt = "VARCHAR";
		
		List<? extends Object> uml = Arrays.asList(ut);
		List<? extends Object> rdbms = Arrays.asList(rt);

		this.testFindMatch(Uml2Rdbms.umlDomainId, PrimitiveType2SqlType.class, uml, rdbms, false);
		
	}
	
	@Test
	public void check_rdbms__Boolean_VARCHAR_false() {

		String ut = "Boolean";
		String rt = "VARCHAR";
		
		List<? extends Object> uml = Arrays.asList(ut);
		List<? extends Object> rdbms = Arrays.asList(rt);

		this.testFindMatch(Uml2Rdbms.rdbmsDomainId, PrimitiveType2SqlType.class, uml, rdbms, false);
		
	}
	
	@Test
	public void check_uml__Boolean_FISH_false() {

		String ut = "Boolean";
		String rt = "FISH";
		
		List<? extends Object> uml = Arrays.asList(ut);
		List<? extends Object> rdbms = Arrays.asList(rt);

		this.testFindMatch(Uml2Rdbms.umlDomainId, PrimitiveType2SqlType.class, uml, rdbms, false);
		
	}
	
	@Test
	public void check_rdbms__Boolean_FISH_false() {

		String ut = "Boolean";
		String rt = "FISH";
		
		List<? extends Object> uml = Arrays.asList(ut);
		List<? extends Object> rdbms = Arrays.asList(rt);

		this.testFindMatch(Uml2Rdbms.rdbmsDomainId, PrimitiveType2SqlType.class, uml, rdbms, false);
		
	}
}
