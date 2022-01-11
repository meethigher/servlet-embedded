package top.meethigher.servletembedded.sysparams.service.impl;

import top.meethigher.servletembedded.common.utils.Utils;
import top.meethigher.servletembedded.sysparams.entity.SysParams;
import top.meethigher.servletembedded.sysparams.repository.SysParamsRepository;
import top.meethigher.servletembedded.sysparams.service.CrudService;

import java.util.Map;

/**
 * @author chenchuancheng
 * @since 2022/1/5 13:48
 */
public class DeleteByNameService implements CrudService {
    @Override
    public String process(SysParamsRepository repository, Map<String, ?> paramMap) {
        SysParams sysParams = Utils.mapToBean(paramMap, SysParams.class);
        if (sysParams == null) {
            return "paramName为必传参数！";
        }
        String paramName = sysParams.getParamName();
        if (paramName == null) {
            return "paramName为必传参数！";
        }
        Integer number = repository.deleteByName(paramName);
        return String.valueOf(number);
    }
}
