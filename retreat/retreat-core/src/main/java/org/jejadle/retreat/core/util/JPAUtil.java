package org.jejadle.retreat.core.util;

import java.io.StringWriter;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class JPAUtil {
	
	public static String convert(VelocityContext context, String vm){
		StringWriter swOut = new StringWriter();
		context.put("StringUtils", StringUtils.class);
		Velocity.evaluate( context, swOut, "jpa query", vm);
		return swOut.toString();
	}
	
	/**
	 * mapping xml 에 있는 Query을 가져온다.
	 * @param em
	 * @param queryName
	 * @return
	 */
	
	public static String getNativeQuery(EntityManager em, String queryName){
		return em.createNamedQuery(queryName).unwrap(org.hibernate.Query.class).getQueryString();
	}
	
	/**
	 * Velocity template 과 조합되어 있는 Query에서 완성된 Query를 만든다.
	 * jpql, sql 동시 사용한다. 
	 * 
	 * @param em
	 * @param context
	 * @param queryName
	 * @return
	 */
	
	public static String makeSQL(EntityManager em, VelocityContext context, String queryName){
		
		String nativeQuery = getNativeQuery(em, queryName);
		
		return convert(context,nativeQuery);
	}

}
