package top.meethigher.servletembedded.common;

import com.alibaba.fastjson.JSON;
import top.meethigher.servletembedded.common.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * 静态资源处理逻辑
 *
 * @author chenchuancheng
 * @since 2021/12/29 15:25
 */
public abstract class ResourcesServlet extends HttpServlet {

    /**
     * 静态资源路径，如js、css、html、jpg、png等
     */
    private final String resourcePath;


    protected ResourcesServlet(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    protected String getFilePath(String fileName) {
        return this.resourcePath + fileName;
    }

    /**
     * 返回静态资源内容
     *
     * @param fileName
     * @param response
     * @throws IOException
     */
    protected void returnResourceFile(String fileName, HttpServletResponse response) throws IOException {
        String filePath = getFilePath(fileName);
        //对静态资源进行处理
        byte[] bytes;
        //前端字体
        if (fileName.endsWith(".ttf") || fileName.endsWith(".eot") || fileName.endsWith(".woff")) {
            bytes = Utils.readByteArrayFromResource(filePath);
            if (bytes != null) {
                response.getOutputStream().write(bytes);
            }
            return;
        }
        //图片
        if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
            bytes = Utils.readByteArrayFromResource(filePath);
            if (bytes != null) {
                response.getOutputStream().write(bytes);
            }
            return;
        }
        //指定路径下文件存在，则读取内容返回
        String content = Utils.readFromResource(filePath);
        if (content == null) {
            return;
        }
        if (fileName.endsWith(".html")) {
            response.setContentType("text/html;charset=utf-8");
        } else if (fileName.endsWith(".css")) {
            response.setContentType("text/css;charset=utf-8");
        } else if (fileName.endsWith(".js")) {
            response.setContentType("text/javascript;charset=utf-8");
        } else {
            response.setContentType("text/plain;charset=utf-8");
        }
        response.getWriter().write(content);
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contextPath = req.getContextPath();
        String requestURI = req.getRequestURI();
        String servletPath = req.getServletPath();
        resp.setCharacterEncoding("utf-8");
        if (contextPath == null) {
            contextPath = "";
        }
        String uri = contextPath + servletPath;
        String path = requestURI.substring(uri.length());


        if (path.endsWith(".ccc")) {
            /**
             * 这是自定义的接口，对其中进行拦截，然后返回crud
             */
            resp.setContentType("application/json;charset=utf-8");
            String method = req.getMethod();
            if ("POST".equals(method)) {
                BufferedReader br = req.getReader();

                StringBuffer sb = new StringBuffer();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                Map<String, Object> paramMap = (Map<String, Object>) JSON.parse(sb.toString());
                resp.getWriter().print(process(path, paramMap));
            } else {
                resp.setContentType("text/plain;charset=utf-8");
                resp.getWriter().print("Halo guys, " +
                        "this request method is unsupported! " +
                        "Please try POST! ");
            }
        } else {
            if ("/".equals(path)) {
                path = "/index.html";
            }
            /**
             * 非接口内容，一律按照静态资源返回
             */
            returnResourceFile(path, resp);
        }
    }

    protected abstract String process(String path, Map<String, ? extends Object> paramMap);
}
