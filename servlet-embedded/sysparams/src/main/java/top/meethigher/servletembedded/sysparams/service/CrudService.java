package top.meethigher.servletembedded.sysparams.service;

import top.meethigher.servletembedded.sysparams.repository.SysParamsRepository;

import java.util.Map;

/**
 * @author chenchuancheng
 * @since 2022/1/5 13:51
 */
public interface CrudService {

    /**
     * 处理请求
     *
     * @param repository
     * @param paramMap
     * @return
     */
    String process(SysParamsRepository repository, Map<String, ? extends Object> paramMap);
}
