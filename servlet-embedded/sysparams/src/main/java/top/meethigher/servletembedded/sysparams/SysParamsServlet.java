package top.meethigher.servletembedded.sysparams;

import top.meethigher.servletembedded.common.ResourcesServlet;
import top.meethigher.servletembedded.sysparams.repository.SysParamsRepository;
import top.meethigher.servletembedded.sysparams.service.CrudService;
import top.meethigher.servletembedded.sysparams.strategy.CrudStrategy;

import java.util.Map;

/**
 * 实现CRUD的逻辑
 *
 * @author chenchuancheng
 * @since 2021/12/31 15:55
 */
public class SysParamsServlet extends ResourcesServlet {

    private final SysParamsRepository sysParamsRepository;

    /**
     * 读取resources目录下的sysparams中的相应url内容
     *
     * @param sysParamsRepository
     */
    public SysParamsServlet(SysParamsRepository sysParamsRepository) {
        super("/sysparams");
        this.sysParamsRepository = sysParamsRepository;
    }

    protected SysParamsServlet(String resourcePath, SysParamsRepository sysParamsRepository) {
        super(resourcePath);
        this.sysParamsRepository = sysParamsRepository;
    }

    @Override
    protected String process(String path, Map<String, ?> paramMap) {
        CrudService crud = CrudStrategy.getCrud(path);
        if (crud == null) {
            return null;
        }
        return crud.process(sysParamsRepository, paramMap);
    }
}
