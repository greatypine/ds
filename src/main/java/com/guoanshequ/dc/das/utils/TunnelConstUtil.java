package com.guoanshequ.dc.das.utils;

/**
 * @Author: chenchuang
 * @Date: 2018/12/29 09:54
 */
public class TunnelConstUtil {

//    public static final String SQLSERVER_URL="http://localhost:8090/queryData.action";
    public static final String SQLSERVER_URL="http://datatest.guoanshequ.top/eprj/queryData.action";

    public static final String TbStore_SQL = "select *,CONVERT(varchar(100),c_modified_dt, 120) as c_modified_dt,CONVERT(varchar(100),c_open_date, 120) as c_open_date " +
            "from tb_store ";

    public static final  String TbOCount_SQL = "select *,CONVERT(varchar(100),c_mk_dt, 120) as c_mk_dt,CONVERT(varchar(100),c_count_dt, 120) as c_count_dt ," +
            "CONVERT(varchar(100),c_update_dt, 120) as c_update_dt,CONVERT(varchar(100),c_au_dt, 120) as c_au_dt,CONVERT(varchar(100),c_input_dt, 120) as c_input_dt " +
            "from tb_o_count where c_status='已审核' ";

    public static final  String TbOCountg_SQL = "select * from tb_o_countg ";

    public static final  String TbOl_SQL = "select *,CONVERT(varchar(100),c_mk_dt, 120) as c_mk_dt,CONVERT(varchar(100),c_approve_dt, 120) as c_approve_dt," +
            "CONVERT(varchar(100),c_au_dt, 120) as c_au_dt from tb_o_l where c_status='已审核'  ";

    public static final String TbsdGds_SQL="select tdg.c_guid,tdg.c_store_id,tdg.c_gcode,CONVERT(varchar(100),tdg.c_dt, 120) as c_dt,tdg.c_adno,"
            + "tdg.c_ccode,tdg.c_trademark,tdg.c_name,tdg.c_type,tdg.c_model,tdg.c_status,tdg.c_pro_status, tdg.c_sale_status,"
            + "tdg.c_store_status,tdg.c_sale_frequency,tdg.c_abc,tdg.c_number_sale,tdg.c_a_sale,tdg.c_at_sale, case when tdg.c_number_sale > 0 "
            + "then (tdg.c_at_sale/tdg.c_number_sale) else 0 end as cost_price,ts.c_map_store_id "
            + "from tbs_d_gds tdg left join tb_store ts on (tdg.c_store_id = ts.c_id) ";
    
    public static final String TbsdGds_COUNT="select count(1) as tnum from tbs_d_gds tdg ";
    

    public static String TunnelStore(String c_store_id){
        return TbStore_SQL + " where c_id='"+c_store_id+"'";
    }
    public static String TunnelTbOCount(String begintime, String endtime){
        return TbOCount_SQL + " and ((  c_au_dt >=  '"+begintime+"' and c_au_dt <= '"+endtime+"' ) or (c_approve_dt >=  '"+begintime+"' and c_approve_dt <= '"+endtime+"' ))";
    }

    public static String TunnelTbOCountg(String c_id){
        return TbOCountg_SQL + " where c_id='"+c_id+"'";
    }

    public static String TunnelTbOl(String begintime, String endtime){
        return TbOl_SQL + " and ((  c_au_dt >=  '"+begintime+"' and c_au_dt <= '"+endtime+"' ) or (c_approve_dt >=  '"+begintime+"' and c_approve_dt <= '"+endtime+"' ))";
    }

    public static String TunnelTbsdGds(String begintime, String endtime){
        return TbsdGds_SQL + " where tdg.c_dt>='"+begintime+"' and tdg.c_dt< '"+endtime+"'";
    }
    
    public static String TunnelTbsdGds_Count(String begintime, String endtime){
        return TbsdGds_COUNT + " where tdg.c_dt>='"+begintime+"' and tdg.c_dt< '"+endtime+"'";
    }

    public static String TunnelTbsdGds_splitByRow(String begintime, String endtime,int startPoint,int endPoint){
        return "SELECT	TOP "+endPoint+" o.* FROM 	( " + 
        		"		SELECT  row_number () OVER (ORDER BY c_guid DESC) AS rownumber ,* " + 
        		"		FROM " + 
        		"			( " + 
        		"			select tdg.c_guid,tdg.c_store_id,tdg.c_gcode,CONVERT(varchar(100),tdg.c_dt, 120) as c_dt,tdg.c_adno, " + 
        		"			tdg.c_ccode,tdg.c_trademark,tdg.c_name,tdg.c_type,tdg.c_model,tdg.c_status,tdg.c_pro_status, tdg.c_sale_status, " + 
        		"			tdg.c_store_status,tdg.c_sale_frequency,tdg.c_abc,tdg.c_number_sale,tdg.c_a_sale,tdg.c_at_sale, case when tdg.c_number_sale > 0  " + 
        		"			then (tdg.c_at_sale/tdg.c_number_sale) else 0 end as cost_price,ts.c_map_store_id  " + 
        		"			from tbs_d_gds tdg left join tb_store ts on (tdg.c_store_id = ts.c_id) " + 
        		"			where tdg.c_dt>='"+begintime+"' and tdg.c_dt< '"+endtime+"' " + 
        		"			) AS oo " + 
        		"	) AS o   WHERE  rownumber >= "+startPoint+" order by rownumber asc ";
    }
}
