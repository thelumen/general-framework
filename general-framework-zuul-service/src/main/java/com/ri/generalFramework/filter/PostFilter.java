package com.ri.generalFramework.filter;

import com.netflix.zuul.ZuulFilter;
import com.ri.generalFramework.util.TraceIdUtils;

/**
 * 自定义过滤器的实现，需要继承ZuulFilter，需要重写实现下面四个方法：
 * <p>
 * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
 * pre：可以在请求被路由之前调用
 * routing：在路由请求时候被调用
 * post：在routing和error过滤器之后被调用
 * error：处理请求时发生错误时被调用
 * filterOrder：通过int值来定义过滤器的执行顺序
 * shouldFilter：返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可实现过滤器的开关。在上例中，我们直接返回true，所以该过滤器总是生效。
 * run：过滤器的具体逻辑。需要注意，这里我们通过ctx.setSendZuulResponse(false)令zuul过滤该请求，不对其进行路由，然后通过ctx.setResponseStatusCode(401)设置了其返回的错误码，当然我们也可以进一步优化我们的返回，比如，通过ctx.setResponseBody(body)对返回body内容进行编辑等。
 **/
public class PostFilter extends ZuulFilter {

    public String filterType() {
        return "post";
    }

    public int filterOrder() {
        return 15;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() {
        System.out.println("最后清除跟踪日志id结束的地方");
        // 记录全链路日志Id跟踪-start
        TraceIdUtils.removeTraceId();
       /*
       //查看服务端返回的内容
        InputStream inputStream= ctx.getResponseDataStream();
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader reader = null;
        String tempString = "";
        StringBuffer buffer = new StringBuffer();
        try{
            reader = new BufferedReader(streamReader);

            while ((tempString = reader.readLine()) != null) {
                buffer.append(tempString);
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            if (null != reader){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("service返回的内容:"+buffer.toString());*/
        return null;
    }
}