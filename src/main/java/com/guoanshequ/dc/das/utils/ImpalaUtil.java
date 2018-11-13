package com.guoanshequ.dc.das.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * impala 连接工具类
 * @author gbl
 *
 */
public class ImpalaUtil {
	
	
    public static List<Map<String,Object>> execute(String sql){

        Connection con = null;
        ResultSet rs = null;
        Statement stat=null;
        List<Map<String,Object>> list = null;
        try {

            con = HikariInner.getConnection();
            System.out.println("\n== impala获取connection，初始化链接，获取sql开始执行======================");
            stat = con.createStatement();
            rs = stat.executeQuery(sql);
            list = convertList(rs);

            System.out.println("== impala执行sql结束，返回数据结果 =======================\n\n");

        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        } finally {
            try {
            	HikariInner.close(con, stat, rs);
            } catch (Exception e) {
                e.printStackTrace();
                return list;
            }
        }
        return list;
    }

	private static List<Map<String, Object>> convertList(ResultSet rs) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ResultSetMetaData md = rs.getMetaData();// 获取键名
		int columnCount = md.getColumnCount();// 获取行的数量
		while (rs.next()) {
			Map<String, Object> rowData = new HashMap<String, Object>();// 声明Map
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));// 获取键名及值
			}
			list.add(rowData);
		}
		return list;
	}
	
	public static void main(String[] args) {
		String sql = "SELECT order_sn FROM gemini.t_order limit 10 ";
		List<Map<String,Object>> resultList = ImpalaUtil.execute(sql);
		System.out.println(resultList);
	}

}
