package org.dromara.neutrinoproxy.client.config;

import lombok.extern.slf4j.Slf4j;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.event.AppLoadEndEvent;
import org.noear.solon.core.event.EventListener;
import org.noear.solon.core.runtime.NativeDetector;

/**
 * 启动成功后信息输出
 * @author: aoshiguchen
 * @date: 2024/10/16
 */
@Slf4j
@Component
public class AppLoadEndExecutor implements EventListener<AppLoadEndEvent> {

    @Override
    public void onEvent(AppLoadEndEvent appLoadEndEvent) throws Throwable {
        // aot 阶段，不执行
        if (NativeDetector.isAotRuntime()) {
            return;
        }
        System.out.printf("""
            ---------------------------------------------------------------
            欢迎使用天意科研云-内网穿透服务
            天意云官网（https://dftianyi.com）
            ---------------
            天意科研云（https://sci.dftianyi.com）
            提供文献互助、ChatGPT、Claude、AI翻译、科研云盘、PDF全能工具等一系列科研工具
            ---------------
            天意生信云（https://bio.dftianyi.com）
            提供生信分析服务、生信服务器、Rstudio、200+款云工具等全方位生信科研服务
            ---------------------------------------------------------------
            %n""", Solon.app().cfg().get("solon.app.version"));
    }

}
