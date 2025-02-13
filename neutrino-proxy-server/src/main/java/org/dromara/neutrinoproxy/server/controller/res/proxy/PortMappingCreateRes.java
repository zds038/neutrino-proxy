package org.dromara.neutrinoproxy.server.controller.res.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 端口映射创建响应
 * @author: aoshiguchen
 * @date: 2022/8/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortMappingCreateRes {
    private Integer portMappingId;
}
