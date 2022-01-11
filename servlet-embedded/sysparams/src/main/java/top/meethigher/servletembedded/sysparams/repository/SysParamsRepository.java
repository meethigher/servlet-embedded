package top.meethigher.servletembedded.sysparams.repository;

import top.meethigher.servletembedded.sysparams.entity.SysParams;

import java.util.List;

/**
 * @author chenchuancheng
 * @since 2021/12/31 16:36
 */
public interface SysParamsRepository {
    /**
     * 查询
     *
     * @param paramNameLike 模糊匹配paramName
     * @return
     */
    List<SysParams> findAll(String paramNameLike);

    /**
     * 查询详情
     *
     * @param paramName
     * @return
     */
    SysParams findByName(String paramName);

    /**
     * 删除
     *
     * @param paramName
     * @return
     */
    Integer deleteByName(String paramName);

    /**
     * 插入
     *
     * @param sysParams
     * @return
     */
    Integer insert(SysParams sysParams);

    /**
     * 更新
     *
     * @param sysParams
     * @return
     */
    Integer update(SysParams sysParams);

}


