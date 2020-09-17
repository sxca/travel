package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid, String rname) {
        // String sql = "select count(*) from tab_route where cid = ?";
        // 1.定义sql模板
        String sql = "select count(*) from tab_route where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();

        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);// 添加？对应的值
        }

        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like ? ");
            // 加两个%的原始是sql中的模糊查询，元素中间包括rname
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        // 2.判断参数是否有值
        return template.queryForObject(sql,Integer.class,params.toArray());

    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        // String sql = "select * from tab_route where cid = ? limit ? , ?";
        // 1.定义sql模板
        String sql = "select * from tab_route where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();

        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);// 添加？对应的值
        }

        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like ? ");
            // 加两个%的原始是sql中的模糊查询，元素中间包括rname
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");//分页条件
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }
}
