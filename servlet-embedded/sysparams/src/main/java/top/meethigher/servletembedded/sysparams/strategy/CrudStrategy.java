package top.meethigher.servletembedded.sysparams.strategy;

import top.meethigher.servletembedded.sysparams.constant.CrudType;
import top.meethigher.servletembedded.sysparams.service.CrudService;
import top.meethigher.servletembedded.sysparams.service.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略模式获取对象
 *
 * @author chenchuancheng
 * @since 2022/1/5 17:05
 */
public class CrudStrategy {

    private static Map<CrudType, CrudService> crudServiceMap;

    static {
        crudServiceMap = new HashMap<>();
        crudServiceMap.put(CrudType.FIND_ALL, new FindAllService());
        crudServiceMap.put(CrudType.FIND_BY_NAME, new FindByNameService());
        crudServiceMap.put(CrudType.DELETE_BY_NAME, new DeleteByNameService());
        crudServiceMap.put(CrudType.INSERT, new InsertService());
        crudServiceMap.put(CrudType.UPDATE, new UpdateService());
    }

    public static CrudService getCrud(String path) {
        for (CrudType type : CrudType.values()) {
            if (path.endsWith(type.path)) {
                return crudServiceMap.get(type);
            }
        }
        return null;
    }
}
