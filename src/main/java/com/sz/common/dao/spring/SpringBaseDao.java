package com.sz.common.dao.spring;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.sz.common.util.PageModel;
import com.sz.common.util.PageUtils;

@Repository
public class SpringBaseDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}
	
	private static final String SQL_INSERT = "insert";
    private static final String SQL_UPDATE = "update";
    
    public <T> int insert(T entity) throws Exception{
    	String sql=this.makeSql(SQL_INSERT, entity);
    	Object[] args=this.getArgs(SQL_INSERT, entity);
    	return jdbcTemplate.update(sql, args);
    }
    
    public <T> int update(T entity) throws Exception {
        String sql = this.makeSql(SQL_UPDATE,entity);
        Object[] args = this.getArgs(SQL_UPDATE,entity);
        return jdbcTemplate.update(sql, args);
    }
    
    public int deleteById(Class<?> clazz,Object id){
    	Field[] fields = clazz.getDeclaredFields();
    	String tableName=this.getTableName(clazz);
    	String columnId=this.getColumnIdName(fields);
        String sql=" DELETE FROM " + tableName + " WHERE "+columnId+"=?";
        return jdbcTemplate.update(sql, id);
    }
    
    public <T> T findById(Class<T> clazz,Object id){
    	Field[] fields = clazz.getDeclaredFields();
    	String tableName=this.getTableName(clazz);
    	String columnId=this.getColumnIdName(fields);
    	String sql="select * from "+tableName+" where "+columnId+" =? ";
    	return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(clazz),id);
    }
    
    
    public <T> List<T> queryBeanList(String sql,Class<T> clazz,Object... args){
    	return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(clazz), args);
    }
    
    public <T> T queryBeanObject(String sql,Class<T> clazz,Object... args){
    	return jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(clazz));
    }
    
    public <T> PageModel queryPage(String sql,Class<T> clazz,int pageNo,int pageSize,Object... args){
    	String limit=" limit "+(pageNo-1)*pageSize+","+pageSize;
    	String querySql=sql+limit;
    	String countSql="select count(*) from ("+sql+") t";
    	int totalCount=jdbcTemplate.queryForObject(countSql, Integer.class, args);
    	List<T> list=jdbcTemplate.query(querySql, BeanPropertyRowMapper.newInstance(clazz), args);
    	PageModel page=PageUtils.doPage(pageNo, pageSize, totalCount, list);
    	return page;
    }
    
    public <T> List<T> queryBeanList(String sql,Class<T> clazz,Map<String,?> paramMap){
    	return namedParameterJdbcTemplate.query(sql, paramMap,BeanPropertyRowMapper.newInstance(clazz));
    }
    
    public <T> T queryBeanObject(String sql,Class<T> clazz,Map<String,?> paramMap){
    	return namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(clazz));
    }
    
    public <T> PageModel queryPage(String sql,Class<T> clazz,int pageNo,int pageSize,Map<String,?> paramMap){
    	String limit=" limit "+(pageNo-1)*pageSize+","+pageSize;
    	String querySql=sql+limit;
    	String countSql="select count(*) from ("+sql+") t";
    	int totalCount=namedParameterJdbcTemplate.queryForObject(countSql, paramMap, Integer.class);
    	List<T> list=namedParameterJdbcTemplate.query(querySql, paramMap, BeanPropertyRowMapper.newInstance(clazz));
    	PageModel page=PageUtils.doPage(pageNo, pageSize, totalCount, list);
    	return page;
    }
    
    public <T> List<T> queryBeanList(String sql,Class<T> clazz,T t){
    	SqlParameterSource paramSource = new BeanPropertySqlParameterSource(t);
    	return namedParameterJdbcTemplate.query(sql, paramSource, BeanPropertyRowMapper.newInstance(clazz));
    }
    
    public <T> T queryBeanObject(String sql,Class<T> clazz,T t){
    	SqlParameterSource paramSource = new BeanPropertySqlParameterSource(t);
    	return namedParameterJdbcTemplate.queryForObject(sql, paramSource, BeanPropertyRowMapper.newInstance(clazz));
    }
	
    /**
     * 拼接sql语句
     * @param sqlFlag
     * @param entity
     * @return
     * @throws Exception
     */
	public <T> String makeSql(String sqlFlag,T entity) throws Exception {
        StringBuffer sql = new StringBuffer();
        Field[] fields = entity.getClass().getDeclaredFields();
        String tableName=this.getTableName(entity);
        if (sqlFlag.equals(SQL_INSERT)) {
            sql.append(" INSERT INTO " + tableName);
            sql.append("(");
            for (int i = 0; fields != null && i < fields.length; i++) {
            	if(fields[i].getAnnotation(Transient.class)==null) {
            		fields[i].setAccessible(true);
                	Object value=fields[i].get(entity);
                	if(value!=null){
    	                String columnName = this.getColumnName(fields[i]);
    	                sql.append(columnName).append(",");
                	}
            	}
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(") VALUES (");
            for (int i = 0; fields != null && i < fields.length; i++) {
            	if(fields[i].getAnnotation(Transient.class)==null) {
            		Object value=fields[i].get(entity);
                	if(value!=null){
                		sql.append("?,");
                	}
            	}
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        } else if (sqlFlag.equals(SQL_UPDATE)) {
        	String columnId="id";
            sql.append(" UPDATE " + tableName + " SET ");
            for (int i = 0; fields != null && i < fields.length; i++) {
            	if(fields[i].getAnnotation(Transient.class)==null) {
            		fields[i].setAccessible(true);
                    String columnName = this.getColumnName(fields[i]);
                    Id id=fields[i].getAnnotation(Id.class);
                    if(id!=null){
                    	columnId=columnName;
                    	continue;
                    }
                    sql.append(columnName).append("=").append("?,");
            	}
                
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE "+columnId+" = ?");
        }
        return sql.toString();
    }
	
	/**
	 * 拼接参数
	 * @param sqlFlag
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public <T> Object[] getArgs(String sqlFlag,T entity) throws Exception {
        Field[] fields = entity.getClass().getDeclaredFields();
        if (sqlFlag.equals(SQL_INSERT)) {
        	List<Object> list=new ArrayList<Object>();
            for (int i = 0; fields != null && i < fields.length; i++) {
            	if(fields[i].getAnnotation(Transient.class)==null) {
            		fields[i].setAccessible(true);
                    Object value=fields[i].get(entity);
                    if(value!=null){
                    	list.add(value);
                    }
            	}
            }
            return list.toArray();
        } else if (sqlFlag.equals(SQL_UPDATE)) {
        	List<Object> list=new ArrayList<Object>();
        	Object idValue="";
            for (int i = 0; fields != null && i < fields.length; i++) {
            	if(fields[i].getAnnotation(Transient.class)==null) {
            		fields[i].setAccessible(true);
                    Id id=fields[i].getAnnotation(Id.class);
                    if(id!=null){
                    	idValue=fields[i].get(entity);
                    	continue;
                    }
                    list.add(fields[i].get(entity));
            	}
            }
            list.add(idValue);
            return list.toArray();
        }
        return null;
    }
	
	
	
	
	/**
	 * 获取列名
	 * @param field
	 * @return
	 */
	private String getColumnName(Field field){
		String columnName = field.getName();
        Column column=field.getAnnotation(Column.class);
        if(column!=null && !StringUtils.isEmpty(column.name())){
        	columnName=column.name();
        }
        return columnName;
	}
	
	/**
	 * 获取主键列名
	 * @param fields
	 * @return
	 */
	private String getColumnIdName(Field[] fields){
		String columnId="id";
    	for (int i = 0; fields != null && i < fields.length; i++) {
            fields[i].setAccessible(true);
            String columnName = this.getColumnName(fields[i]);
            Id idAnn=fields[i].getAnnotation(Id.class);
            if(idAnn!=null){
            	columnId=columnName;
            	break;
            }
    	}
    	return columnId;
	}
	
	/**
	 * 获取表名
	 * @param entity
	 * @return
	 */
	private <T> String getTableName(T entity){
		String tableName = toLowerCase(entity.getClass().getSimpleName());
        Table table=entity.getClass().getAnnotation(Table.class);
        if(table!=null && !StringUtils.isEmpty(table.name())){
        	tableName=table.name();
        }
        return tableName;
	}
	
	/**
	 * 获取表名
	 * @param clazz
	 * @return
	 */
	private String getTableName(Class<?> clazz){
		String tableName = toLowerCase(clazz.getSimpleName());
        Table table=(Table)clazz.getAnnotation(Table.class);
        if(table!=null && !StringUtils.isEmpty(table.name())){
        	tableName=table.name();
        }
        return tableName;
	}
	
	/**
	 * 首字母小写
	 * @param str
	 * @return
	 */
	private String toLowerCase(String str){
		StringBuffer sb = new StringBuffer(str);
		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		return sb.toString();
	}
}
