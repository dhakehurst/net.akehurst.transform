package net.akehurst.transformation.relations;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import net.akehurst.transformation.relations.annotations.Check;
import net.akehurst.transformation.relations.annotations.Domain;
import net.akehurst.transformation.relations.annotations.Enforce;
import net.akehurst.transformation.relations.annotations.EnforceWhere;
import net.akehurst.transformation.relations.annotations.When;
import net.akehurst.transformation.relations.annotations.CheckWhere;

public abstract class AbstractRelation implements Relation {

	public AbstractRelation(Transformer transformer) {
		this.transformer = transformer;
	}

	Transformer transformer;

	public Transformer getTransformer() {
		return this.transformer;
	}

	Class<?> getDomainValue(Annotation annotation) {
		try {
			return (Class<?>) annotation.getClass().getMethod("value").invoke(annotation);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	Method getDomainMethod(String startsWith, DomainModelItentifier domainId, Class<? extends Annotation> annotationType) {
		List<Method> s = Arrays.asList(this.getClass().getMethods());
		Method res = null;
		for (Method m : s) {
			if (m.getName().startsWith(startsWith)) {
				List<? extends Annotation> ans = Arrays.asList(m.getAnnotation(annotationType));
				for (Annotation a : ans) {
					if (null==a) {
						break;
					}
					if (domainId.equals(getDomainValue(a))) {
						res = m;
						break;
					}
					if (null != res)
						break;
				}
			}
		}
		return res;
	}

	Object invoke(String startsWith, DomainModelItentifier domainId, Class<? extends Annotation> annotation)
			throws RelationNotValidException {
		Method m = getDomainMethod(startsWith, domainId, annotation);
		if (null == m) {
			throw new RelationNotValidException("Annotation " + annotation.getName() + " not found for domain "
					+ domainId + " in Relation class " + this.getClass().getName());
		}
		try {
			return m.invoke(this);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object getDomainVariable(DomainModelItentifier domainId) throws RelationNotValidException {
		return this.invoke("get", domainId, Domain.class);
	}

	public void setDomainVariable(DomainModelItentifier domainId, Object value) throws RelationNotValidException {
		Method m = this.getDomainMethod("set", domainId, Domain.class);
		try {
			m.invoke(this, new Object[] { value });
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Class<?> getDomainType(DomainModelItentifier domainId) {
		Method m = this.getDomainMethod("get", domainId, Domain.class);
		return m.getReturnType();
	}

	@Override
	public boolean when(DomainModelItentifier domainId) throws RelationNotValidException {
		return (Boolean) this.invoke("", domainId, When.class);
	}
	
	@Override
	public boolean check(DomainModelItentifier domainId) throws RelationNotValidException {
		return (Boolean) this.invoke("", domainId, Check.class);
	}

	@Override
	public boolean checkWhere(DomainModelItentifier domainId) throws RelationNotValidException {
		return (Boolean) this.invoke("", domainId, CheckWhere.class);
	}
	
	@Override
	public void enforce(DomainModelItentifier domainId) throws RelationNotValidException {
		this.invoke("", domainId, Enforce.class);
	}
	
	@Override
	public void enforceWhere(DomainModelItentifier domainId) throws RelationNotValidException {
		this.invoke("", domainId, EnforceWhere.class);
	}
}
