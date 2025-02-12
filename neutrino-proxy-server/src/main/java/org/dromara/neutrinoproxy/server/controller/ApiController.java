package org.dromara.neutrinoproxy.server.controller;

import org.apache.commons.lang3.StringUtils;
import org.dromara.neutrinoproxy.server.base.page.PageInfo;
import org.dromara.neutrinoproxy.server.base.page.PageQuery;
import org.dromara.neutrinoproxy.server.constant.ExceptionConstant;
import org.dromara.neutrinoproxy.server.constant.NetworkProtocolEnum;
import org.dromara.neutrinoproxy.server.controller.req.proxy.DomainListReq;
import org.dromara.neutrinoproxy.server.controller.req.proxy.PortMappingCreateReq;
import org.dromara.neutrinoproxy.server.controller.res.proxy.DomainListRes;
import org.dromara.neutrinoproxy.server.controller.res.proxy.PortMappingCreateRes;
import org.dromara.neutrinoproxy.server.service.PortMappingService;
import org.dromara.neutrinoproxy.server.util.ParamCheckUtil;
import org.noear.solon.annotation.*;

@Mapping("/api")
@Controller
public class ApiController {
    @Inject
    private PortMappingService portMappingService;

    @Post
    @Mapping("/port-mapping/create")
    public PortMappingCreateRes create(PortMappingCreateReq req) {
        ParamCheckUtil.checkNotNull(req, "req");
        ParamCheckUtil.checkNotNull(req.getLicenseId(), "licenseId");
        ParamCheckUtil.checkNotNull(req.getServerPort(), "serverPort");
        ParamCheckUtil.checkNotNull(req.getClientPort(), "clientPort");
        ParamCheckUtil.checkNotEmpty(req.getProtocal(), "protocal");
        ParamCheckUtil.checkMaxLength(req.getDescription(), 50, "描述", "50");
        ParamCheckUtil.checkBytesDesc(req.getUpLimitRate(), "upLimitRate");
        ParamCheckUtil.checkBytesDesc(req.getDownLimitRate(), "downLimitRate");
        if (StringUtils.isBlank(req.getClientIp())) {
            // 没传客户端ip，默认为127.0.0.1
            req.setClientIp("127.0.0.1");
        }
        NetworkProtocolEnum networkProtocolEnum = NetworkProtocolEnum.of(req.getProtocal());
        ParamCheckUtil.checkNotNull(networkProtocolEnum, ExceptionConstant.AN_UNSUPPORTED_PROTOCOL, req.getProtocal());
        if (networkProtocolEnum != NetworkProtocolEnum.HTTP) {
            // 目前仅HTTP支持绑定域名
            req.setDomainMappings(null);
        }
        req.setProtocal(networkProtocolEnum.getDesc());
        if (null == req.getProxyResponses()) {
            req.setProxyResponses(0);
        }
        if (null == req.getProxyTimeoutMs()) {
            req.setProxyTimeoutMs(0L);
        }
        if (null == req.getSecurityGroupId()) {
            req.setSecurityGroupId(0);
        }

        return portMappingService.create(req);
    }
}
