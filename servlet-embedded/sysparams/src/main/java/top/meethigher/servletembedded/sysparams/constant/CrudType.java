package top.meethigher.servletembedded.sysparams.constant;

/**
 * @author chenchuancheng
 * @since 2022/1/5 17:08
 */
@SuppressWarnings("all")
public enum CrudType {
    FIND_ALL("查询所有", "/findAll.ccc"),
    FIND_BY_NAME("通过名称查询", "/findByName.ccc"),
    DELETE_BY_NAME("通过名称删除", "/deleteByName.ccc"),
    INSERT("添加", "/insert.ccc"),
    UPDATE("更新", "/update.ccc");
    public String desc;
    public String path;

    CrudType(String desc, String path) {
        this.desc = desc;
        this.path = path;
    }
}
