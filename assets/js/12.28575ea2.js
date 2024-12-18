(window.webpackJsonp=window.webpackJsonp||[]).push([[12],{377:function(e,i,s){"use strict";s.r(i);var v=s(14),_=Object(v.a)({},(function(){var e=this,i=e._self._c;return i("ContentSlotsDistributor",{attrs:{"slot-key":e.$parent.slotKey}},[i("h1",{attrs:{id:"首页"}},[i("a",{staticClass:"header-anchor",attrs:{href:"#首页"}},[e._v("#")]),e._v(" 首页")]),e._v(" "),i("ul",[i("li",[e._v("License统计：统计License相关数量指标")]),e._v(" "),i("li",[e._v("端口映射统计：统计端口映射相关数量指标")]),e._v(" "),i("li",[e._v("今日流量：统计当天的服务端上行/下行流量数据")]),e._v(" "),i("li",[e._v("流量汇总：统计所有(包含当天)的服务端上行/下行流量数据")]),e._v(" "),i("li",[e._v("流量监控：按天统计最近15天(可能会动态调整)每天的上行流量、下行流量、汇总流量")])]),e._v(" "),i("p",[i("img",{attrs:{src:e.$withBase("/img/run-example/home.png")}})]),e._v(" "),i("h1",{attrs:{id:"代理配置"}},[i("a",{staticClass:"header-anchor",attrs:{href:"#代理配置"}},[e._v("#")]),e._v(" 代理配置")]),e._v(" "),i("ul",[i("li",[e._v("License管理：License是客户端连接服务端的唯一合法凭证。一个License同时只能被一个客户端使用，一个License可以维护多条端口映射")]),e._v(" "),i("li",[e._v("端口映射：服务端IP+端口 -> 客户端IP+端口的四元组映射(因目前服务端单节点只有一个公网IP，所以不体现出来)，是内网穿透的基本单元。")]),e._v(" "),i("li",[e._v("限速：\n"),i("ul",[i("li",[e._v("默认所有代理没有限速")]),e._v(" "),i("li",[e._v("对License限速，将使得License下的所有端口映射都被限速")]),e._v(" "),i("li",[e._v("若License设置了限速，License下的某条映射也设置了限速，则对于该映射优先采用端口映射上的限速规则")])])]),e._v(" "),i("li",[e._v("域名管理：用于管理服务器主域名和证书，用于支持域名映射。一个主域名域名可以创建多个域名映射（配置不同的子域名）。")])]),e._v(" "),i("p",[i("img",{attrs:{src:e.$withBase("/img/run-example/license2.png")}}),e._v(" "),i("img",{attrs:{src:e.$withBase("/img/run-example/port-mapping2.png")}})]),e._v(" "),i("h1",{attrs:{id:"安全组"}},[i("a",{staticClass:"header-anchor",attrs:{href:"#安全组"}},[e._v("#")]),e._v(" 安全组")]),e._v(" "),i("p",[e._v("一个端口映射可绑定一个安全组，通过安全组的默认放行类型 + 安全组下的安全规则 控制该端口映射的安全访问规则。")]),e._v(" "),i("ul",[i("li",[e._v("场景1: 内部服务，穿透出来自己使用，不希望被其他人访问。端口映射可关联一个默认放行类型为“拒绝”的安全组，安全组下新增安全规则，将本地的出口ip添加到允许放行规则中。")]),e._v(" "),i("li",[e._v("场景2：公网服务，穿透出来开放给大家使用，但是希望拒绝某些IP访问。端口映射可关联一个默认放行类型为“允许”的安全组，安全组下新增安全规则，将黑名单ip添加到拒绝放行规则中。")])]),e._v(" "),i("p",[i("img",{attrs:{src:e.$withBase("/img/run-example/security-group1.png")}}),e._v(" "),i("img",{attrs:{src:e.$withBase("/img/run-example/security-rule1.png")}})]),e._v(" "),i("h1",{attrs:{id:"系统管理"}},[i("a",{staticClass:"header-anchor",attrs:{href:"#系统管理"}},[e._v("#")]),e._v(" 系统管理")]),e._v(" "),i("ul",[i("li",[e._v("用户管理：支持多用户，一个用户可持有多个License。由于项目目前主推个人版，所以暂是没有权限这一套，管理员之外的所有用户都属于游客。对于绝大多数操作，游客仅有只读权限。")]),e._v(" "),i("li",[e._v("端口池管理：用于统一管理服务器内网穿透端口，方便统一设置安全组。")]),e._v(" "),i("li",[e._v("端口池分组：对端口池的一个分组。\n"),i("ul",[i("li",[e._v("全局分组：该分组下的端口全局通用。")]),e._v(" "),i("li",[e._v("用户分组：该分组下的端口由分组绑定的用户独占。")]),e._v(" "),i("li",[e._v("License分组：该分组下的端口由分组绑定的License独占。")])])]),e._v(" "),i("li",[e._v("调度管理：维护服务端定时任务。方便开发、调试。正常使用时无需关心。")])]),e._v(" "),i("p",[i("img",{attrs:{src:e.$withBase("/img/run-example/user-manager1.png")}})]),e._v(" "),i("h1",{attrs:{id:"报表管理"}},[i("a",{staticClass:"header-anchor",attrs:{href:"#报表管理"}},[e._v("#")]),e._v(" 报表管理")]),e._v(" "),i("ul",[i("li",[e._v("用户流量报表：基于用户维度的流量统计")]),e._v(" "),i("li",[e._v("License流量报表：基于License的流量统计")]),e._v(" "),i("li",[e._v("用户流量月度明细：基于用户的流量月度统计")]),e._v(" "),i("li",[e._v("License流量月度明细：基于License的流量月度统计")])]),e._v(" "),i("p",[i("img",{attrs:{src:e.$withBase("/img/run-example/user-flow1.png")}})]),e._v(" "),i("h1",{attrs:{id:"日志管理"}},[i("a",{staticClass:"header-anchor",attrs:{href:"#日志管理"}},[e._v("#")]),e._v(" 日志管理")]),e._v(" "),i("ul",[i("li",[e._v("调度日志：服务端定时任务执行日志")]),e._v(" "),i("li",[e._v("登录日志：管理后端登录、退出登录的日志")]),e._v(" "),i("li",[e._v("客户端连接日志：客户端连接、断开的日志")])]),e._v(" "),i("p",[i("img",{attrs:{src:e.$withBase("/img/run-example/login-log1.png")}})])])}),[],!1,null,null,null);i.default=_.exports}}]);