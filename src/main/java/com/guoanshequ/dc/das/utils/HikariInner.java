package com.guoanshequ.dc.das.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;


/**
 * 
* @ClassName: HikariInner
* @Description: TODO(这里用一句话描述这个类的作用)
* @author caops
* @date 2018年11月9日 上午9:53:08
*
*
 */
public class  HikariInner {
	//定义 Hikari 连接池配置对象
    private static HikariConfig poolConfig = null;
    //定义 Hikari 连接池对象
    private static HikariDataSource dataSource = null;
    private static final String IMPALAD_HOST = "10.10.40.12";
    private static final String IMPALAD_JDBC_PORT = "21050";
    private static final String CONNECTION_URL = "jdbc:impala://" + IMPALAD_HOST + ':' + IMPALAD_JDBC_PORT + "/;auth=noSasl;";
    private static final String JDBC_DRIVER_NAME = "com.cloudera.impala.jdbc41.Driver";

    static{
        try {
        	 poolConfig = new HikariConfig();
        	 //基本配置
         	  poolConfig.setDriverClassName(JDBC_DRIVER_NAME);
         	  poolConfig.setJdbcUrl(CONNECTION_URL);
         	  //等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒 
         	  poolConfig.setConnectionTimeout(500000);
         	  //个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟
         	  poolConfig.setIdleTimeout(600000);
         	  //一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like '%timeout%';） 
         	  poolConfig.setMaxLifetime(1800000);
         	  //连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count) 
         	  poolConfig.setMaximumPoolSize(50);
         	  poolConfig.setMinimumIdle(10);
          	  dataSource = new HikariDataSource(poolConfig);
          	
          	  System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取与指定数据库的连接
    public static HikariDataSource getInstance(){
        return dataSource;
    }

    //从连接池返回一个连接
    public static Connection getConnection(){

        Logger logger = LoggerFactory.getLogger(HikariInner.class);
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            
        } catch (Exception e) {
            logger.info("Exception is {}",e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    //释放资源
    public static void realeaseResource(ResultSet rs,PreparedStatement ps,Connection conn){
        if(null != rs){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(null != ps){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //关闭数据库连接

    public static void close(Connection conn, Statement st, ResultSet rs) {

        if(rs != null) {

            try{

                //关闭存储查询结果的ResultSet对象
                rs.close();

            }catch (Exception e) {

                e.printStackTrace();

            }

            rs= null;

        }

        if(st != null) {

            try{

                //关闭负责执行SQL命令的Statement对象
                st.close();

            }catch (Exception e) {

                e.printStackTrace();

            }

        }



        if(conn != null) {

            try{

                //将Connection连接对象还给数据库连接池
                conn.close();

            }catch (Exception e) {

                e.printStackTrace();

            }

        }
    }
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            Connection connection = getConnection();
            Statement stat=null;
            stat = connection.createStatement();
            ResultSet result =stat.executeQuery("SELECT order_sn FROM gemini.t_order limit 10;");

            while(result.next()){
                System.out.println("order1表数量：----------------"+i+"------------"+result.getObject(1));
            }
            close(connection,stat,result);
        }

	}
    
    
}
