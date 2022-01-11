package top.meethigher.servletembedded.sysparams.entity;

import java.io.Serializable;

/**
 * @author chenchuancheng
 * @since 2021/12/31 16:34
 */
public class SysParams implements Serializable {
    private String paramTitle;
    private String paramName;
    private String param;
    private String paramDesc;
    private String paramType;

    public String getParamTitle() {
        return paramTitle;
    }

    public void setParamTitle(String paramTitle) {
        this.paramTitle = paramTitle;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }


    @Override
    public String toString() {
        return "SysParams{" +
                "paramTitle='" + paramTitle + '\'' +
                ", paramName='" + paramName + '\'' +
                ", param='" + param + '\'' +
                ", paramDesc='" + paramDesc + '\'' +
                ", paramType='" + paramType + '\'' +
                '}';
    }
}
