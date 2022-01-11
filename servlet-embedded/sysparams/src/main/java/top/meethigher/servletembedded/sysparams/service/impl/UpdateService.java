package top.meethigher.servletembedded.sysparams.service.impl;

import top.meethigher.servletembedded.common.utils.Utils;
import top.meethigher.servletembedded.sysparams.entity.SysParams;
import top.meethigher.servletembedded.sysparams.repository.SysParamsRepository;
import top.meethigher.servletembedded.sysparams.service.CrudService;

import java.util.Map;

/**
 * @author chenchuancheng
 * @since 2022/1/5 13:52
 */
public class UpdateService implements CrudService {
    @Override
    public String process(SysParamsRepository repository, Map<String, ?> paramMap) {
        SysParams sysParams = Utils.mapToBean(paramMap, SysParams.class);
        if (sysParams == null) {
            return "请传入参数paramTitle,paramName,param,paramDesc,paramType";
        }
        if (sysParams.getParam() == null) {
            return "param为必传参数";
        }
        if (sysParams.getParamName() == null) {
            return "paramName为必传参数";
        }
        if (sysParams.getParamTitle() == null) {
            return "paramTitle为必传参数";
        }
        Integer update = repository.update(sysParams);
        return String.valueOf(update);
    }
}
