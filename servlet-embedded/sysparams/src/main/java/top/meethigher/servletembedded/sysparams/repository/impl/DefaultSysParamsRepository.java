package top.meethigher.servletembedded.sysparams.repository.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.meethigher.servletembedded.sysparams.entity.SysParams;
import top.meethigher.servletembedded.sysparams.repository.SysParamsRepository;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author chenchuancheng
 * @since 2021/12/31 16:41
 */
public class DefaultSysParamsRepository implements SysParamsRepository {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public DefaultSysParamsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<SysParams> findAll(String paramNameLike) {
        List<SysParams> list;
        if (paramNameLike == null) {
            list = jdbcTemplate.query("select * from sysparams",
                    new BeanPropertyRowMapper<>(SysParams.class));
        } else {
            list = jdbcTemplate.query("select * from sysparams where paramname like ?",
                    new BeanPropertyRowMapper<>(SysParams.class),
                    paramNameLike);
        }
        return list;
    }

    @Override
    public SysParams findByName(String paramName) {
        return jdbcTemplate.queryForObject("select * from sysparams where paramname = ?", new BeanPropertyRowMapper<>(SysParams.class), paramName);
    }

    @Override
    public Integer deleteByName(String paramName) {
        return jdbcTemplate.update("delete from sysparams where paramname = ?", paramName);
    }

    @Override
    public Integer insert(SysParams sysParams) {
        return jdbcTemplate.update("insert into sysparams (param,paramtitle,paramtype,paramdesc,paramname) values (?,?,?,?,?)",
                sysParams.getParam(),
                sysParams.getParamTitle(),
                sysParams.getParamType(),
                sysParams.getParamDesc(),
                sysParams.getParamName());
    }

    @Override
    public Integer update(SysParams sysParams) {
        return jdbcTemplate.update("update sysparams set param=? , paramtitle=?,paramtype=?,paramdesc=?  where paramname=?",
                sysParams.getParam(),
                sysParams.getParamTitle(),
                sysParams.getParamType(),
                sysParams.getParamDesc(),
                sysParams.getParamName());

    }
}
