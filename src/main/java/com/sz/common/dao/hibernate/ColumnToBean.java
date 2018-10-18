package com.sz.common.dao.hibernate;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.ResultTransformer;

public class ColumnToBean implements ResultTransformer 
{
	private static final long serialVersionUID = 1L;
	private Class resultClass;
	private Setter[] setters;
	private PropertyAccessor propertyAccessor;

	public ColumnToBean(Class resultClass) {
		if (resultClass == null)
			throw new IllegalArgumentException("resultClass cannot be null");
		this.resultClass = resultClass;
		propertyAccessor = new ChainedPropertyAccessor(new PropertyAccessor[] {
				PropertyAccessorFactory.getPropertyAccessor(resultClass, null),
				PropertyAccessorFactory.getPropertyAccessor("field") });
	}

	// 结果转换时，HIBERNATE调用此方法
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;
		
		try {
				result = resultClass.newInstance();
				for (int i = 0; i < aliases.length; i++) {
					String alias = aliases[i];
					if (alias != null) {
						// 我的逻辑主要是在getSetterByColumnName方法里面，其它都是HIBERNATE的另一个类中COPY的
						// 这里填充所需要的SETTER方法
						try
						{
							if("int".equals(resultClass.getDeclaredField(aliases[i]).getType().getName().toLowerCase()) && "double".equals(tuple[i].getClass().getSimpleName().toLowerCase()))
							{
								 tuple[i]=((Double)Double.parseDouble(tuple[i]+"")).intValue();
							}
						}
						catch (Exception e) 
						{
							e.printStackTrace();
						}
						BeanUtils.setProperty(result, aliases[i], tuple[i]);
					}
				}
		} catch (InstantiationException e) {
			throw new HibernateException("Could not instantiate resultclass: "
					+ resultClass.getName());
		} catch (IllegalAccessException e) {
			throw new HibernateException("Could not instantiate resultclass: "
					+ resultClass.getName());
		}
		catch (Exception e) {
			throw new HibernateException("Could not instantiate resultclass: "
					+ resultClass.getName());
		}
		return result;
	}

	// 根据数据库字段名在POJO查找JAVA属性名，参数就是数据库字段名，如：USER_ID
	private Setter getSetterByColumnName(String alias) {
		// 取得POJO所有属性名
		Field[] fields = resultClass.getDeclaredFields();
		if (fields == null || fields.length == 0) {
			throw new RuntimeException("实体" + resultClass.getName() + "不含任何属性");
		}
		// 把字段名中所有的下杠去除
		String proName = alias.replaceAll("_", "").toLowerCase();
		for (Field field : fields) {
			if (field.getName().toLowerCase().equals(proName)) {// 去除下杠的字段名如果和属性名对得上，就取这个SETTER方法
				return propertyAccessor.getSetter(resultClass, field.getName());
			}
		}
		throw new RuntimeException(
				"找不到数据库字段 ："
						+ alias
						+ " 对应的POJO属性或其getter方法，比如数据库字段为USER_ID或USERID，那么JAVA属性应为userId");
	}

	@SuppressWarnings("unchecked")
	public List transformList(List collection) {
		return collection;
	}
}
