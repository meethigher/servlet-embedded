package top.meethigher.servletembedded.sysparams.service.impl;

import com.alibaba.fastjson.JSON;
import top.meethigher.servletembedded.common.utils.Utils;
import top.meethigher.servletembedded.sysparams.entity.SysParams;
import top.meethigher.servletembedded.sysparams.repository.SysParamsRepository;
import top.meethigher.servletembedded.sysparams.service.CrudService;

import java.util.List;
import java.util.Map;

/**
 * @author chenchuancheng
 * @since 2022/1/5 13:47
 */
public class FindAllService implements CrudService {

    @Override
    public String process(SysParamsRepository repository, Map<String, ?> paramMap) {
        SysParams sysParams = Utils.mapToBean(paramMap, SysParams.class);
        List<SysParams> sysParamsList;
        if (sysParams == null) {
            sysParamsList = repository.findAll(null);
        } else {
            String paramName = sysParams.getParamName();
            if (paramName == null) {
                sysParamsList = repository.findAll(null);
            } else {
                sysParamsList = repository.findAll(paramName);
            }
        }
        
        return JSON.toJSONString(sysParamsList);
    }
}
