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
