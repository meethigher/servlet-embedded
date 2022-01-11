package top.meethigher.servletembedded.sysparams.service.impl;

import com.alibaba.fastjson.JSON;
import top.meethigher.servletembedded.common.utils.Utils;
import top.meethigher.servletembedded.sysparams.entity.SysParams;
import top.meethigher.servletembedded.sysparams.repository.SysParamsRepository;
import top.meethigher.servletembedded.sysparams.service.CrudService;

import java.util.Map;

/**
 * @author chenchuancheng
 * @since 2022/1/5 13:48
 */
public class FindByNameService implements CrudService {
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
        SysParams result = repository.findByName(paramName);

        return JSON.toJSONString(result);
    }
}
