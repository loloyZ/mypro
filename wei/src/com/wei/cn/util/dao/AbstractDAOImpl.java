package com.wei.cn.util.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

public abstract class AbstractDAOImpl {
    protected Connection conn ;
    private AbstractDAOImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * 数据的批量删除操作
     * @param ids 所有批量删除数据的id,以set集合存储
     * @return 删除成功返回true，否则返回false
     * @throws SQLException SQL异常
     */
    public boolean removeHandle(Set<?> ids,String tableName,String idName) throws SQLException{
        StringBuffer buf = new StringBuffer() ;
        buf.append("DELETE FROM").append(tableName).append("WHERE").append(idName).append("IN (") ;
        Iterator<?> iter = ids.iterator() ;
        while (iter.hasNext()) {
            buf.append(iter.next()).append(",") ;
        }
        buf.delete(buf.length()-1,buf.length()).append(")") ;
        PreparedStatement pstmt = this.conn.prepareStatement(buf.toString()) ;
        return  pstmt.executeUpdate() == ids.size() ;
    }

    /**
     * 进行模糊查询数据量的统计，如果表中没有记录统计的结果就是0
     * @param tableName 表名称
     * @param column 表列名
     * @param KeyWord 关键字
     * @return 如果有数据返回总量，否则返回0
     * @throws SQLException SQL异常
     */
    public Integer countHanle(String tableName,String column,String KeyWord) throws SQLException{
        String sql = "SELECT COUNT(*) FROM" + tableName + "WHERE" + column +"LIKE ?" ;
        PreparedStatement pstmt = this.conn.prepareStatement(sql) ;
        pstmt.setString(1,"%" + KeyWord + "%");
        ResultSet rs = pstmt.executeQuery() ;
        if (rs.next()) {
            return rs.getInt(1) ;
        } else {
            return 0 ;
        }
    }
}
