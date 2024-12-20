(window.webpackJsonp=window.webpackJsonp||[]).push([[27],{392:function(t,a,s){"use strict";s.r(a);var e=s(14),n=Object(e.a)({},(function(){var t=this,a=t._self._c;return a("ContentSlotsDistributor",{attrs:{"slot-key":t.$parent.slotKey}},[a("h2",{attrs:{id:"目录页说明"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#目录页说明"}},[t._v("#")]),t._v(" 目录页说明")]),t._v(" "),a("div",{staticClass:"custom-block warning"},[a("p",{staticClass:"custom-block-title"},[t._v("注意")]),t._v(" "),a("p",[t._v("目录页数据需要依赖于结构化的侧边栏数据，就是说你需要在"),a("code",[t._v("config.js")]),t._v("配置 "),a("code",[t._v("sidebar: 'structuring'")]),t._v(" 或 "),a("code",[t._v("sidebar: { mode: 'structuring', collapsable: false}")]),t._v(" 才能实现目录页数据的获取。")])]),t._v(" "),a("blockquote",[a("ul",[a("li",[t._v("目录页文件("),a("code",[t._v(".md")]),t._v("文件)可以放置在"),a("code",[t._v("二级目录")]),t._v("、"),a("code",[t._v("三级目录")]),t._v("和"),a("code",[t._v("四级目录")]),t._v("。（"),a("RouterLink",{attrs:{to:"/pages/33d574/#级别说明"}},[t._v("级别说明")]),t._v("）")],1),t._v(" "),a("li",[t._v("如果你不想在侧边栏显示目录页，你可以在"),a("code",[t._v("一级目录")]),t._v("中单独创建一个文件夹放置你的目录页("),a("code",[t._v(".md")]),t._v("文件)，并在front matter中设置"),a("code",[t._v("sidebar: false")]),t._v("。")]),t._v(" "),a("li",[t._v("如果你想让目录页和其他页面一起出现在侧边栏，你可以和其他页面共同放置在相应的文件夹。(不要设置"),a("code",[t._v("sidebar: false")]),t._v(")")]),t._v(" "),a("li",[t._v("参照下面的示例配置好front matter，然后就可以在导航栏或首页添加目录页链接了。")])])]),t._v(" "),a("p",[a("strong",[t._v("示例")]),t._v("：")]),t._v(" "),a("div",{staticClass:"language-yaml extra-class"},[a("pre",{pre:!0,attrs:{class:"language-yaml"}},[a("code",[a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("---")]),t._v("\n"),a("span",{pre:!0,attrs:{class:"token key atrule"}},[t._v("pageComponent")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("# 使用页面组件")]),t._v("\n  "),a("span",{pre:!0,attrs:{class:"token key atrule"}},[t._v("name")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(":")]),t._v(" Catalogue "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("# 组件名：Catalogue => 目录页组件")]),t._v("\n  "),a("span",{pre:!0,attrs:{class:"token key atrule"}},[t._v("data")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("# 组件所需数据")]),t._v("\n    "),a("span",{pre:!0,attrs:{class:"token key atrule"}},[t._v("path")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(":")]),t._v("  01.学习笔记/01.前端 "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("# 设置为`docs/`下面的某个文件夹相对路径，如‘01.学习笔记/01.前端’ 或 ’01.学习笔记‘ (有序号的要带序号)")]),t._v("\n    "),a("span",{pre:!0,attrs:{class:"token key atrule"}},[t._v("imgUrl")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(":")]),t._v(" /img/web.png "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("# 目录页内的图片")]),t._v("\n    "),a("span",{pre:!0,attrs:{class:"token key atrule"}},[t._v("description")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(":")]),t._v(" JavaScript、ES6、Vue框架等前端技术 "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("# 目录描述（可加入a标签）")]),t._v("\n\n"),a("span",{pre:!0,attrs:{class:"token key atrule"}},[t._v("title")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(":")]),t._v(" 前端 "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("# 页面标题")]),t._v("\n"),a("span",{pre:!0,attrs:{class:"token key atrule"}},[t._v("date")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token datetime number"}},[t._v("2020-01-12 11:51:53")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("# 创建日期")]),t._v("\n"),a("span",{pre:!0,attrs:{class:"token key atrule"}},[t._v("permalink")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(":")]),t._v(" /note/javascript "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("# 永久链接")]),t._v("\n"),a("span",{pre:!0,attrs:{class:"token key atrule"}},[t._v("sidebar")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token boolean important"}},[t._v("false")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("# 不显示侧边栏")]),t._v("\n"),a("span",{pre:!0,attrs:{class:"token key atrule"}},[t._v("article")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token boolean important"}},[t._v("false")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("# 不是文章页 (不显示面包屑栏、最近更新栏等)")]),t._v("\n"),a("span",{pre:!0,attrs:{class:"token key atrule"}},[t._v("comment")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token boolean important"}},[t._v("false")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("# 不显示评论栏")]),t._v("\n"),a("span",{pre:!0,attrs:{class:"token key atrule"}},[t._v("editLink")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token boolean important"}},[t._v("false")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("# 不显示编辑按钮")]),t._v("\n"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("---")]),t._v("\n")])])]),a("div",{staticClass:"custom-block tip"},[a("p",{staticClass:"custom-block-title"},[t._v("提示")]),t._v(" "),a("p",[t._v("配置好目录页之后，点击文章页的面包屑将会跳转到目录页。")])]),t._v(" "),a("h2",{attrs:{id:"配置项"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#配置项"}},[t._v("#")]),t._v(" 配置项")]),t._v(" "),a("h3",{attrs:{id:"pagecomponent-name"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#pagecomponent-name"}},[t._v("#")]),t._v(" pageComponent.name")]),t._v(" "),a("ul",[a("li",[t._v("组件名，必需")]),t._v(" "),a("li",[t._v("使用目录页时 "),a("strong",[t._v("必须")]),t._v(" 设置为"),a("code",[t._v("Catalogue")])])]),t._v(" "),a("h3",{attrs:{id:"pagecomponent-data"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#pagecomponent-data"}},[t._v("#")]),t._v(" pageComponent.data")]),t._v(" "),a("ul",[a("li",[t._v("组件所需数据，必需")])]),t._v(" "),a("h3",{attrs:{id:"pagecomponent-data-path"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#pagecomponent-data-path"}},[t._v("#")]),t._v(" pageComponent.data.path")]),t._v(" "),a("ul",[a("li",[t._v("要为其生成目录页的文件夹的相对路径，必需")]),t._v(" "),a("li",[t._v("必须是在"),a("code",[t._v("docs")]),t._v("目录底下的文件夹相对路径")]),t._v(" "),a("li",[t._v("示例："),a("code",[t._v("01.学习笔记")]),t._v("或"),a("code",[t._v("01.学习笔记/01.前端")]),t._v("(有序号的要带序号)")])]),t._v(" "),a("div",{staticClass:"custom-block warning"},[a("p",{staticClass:"custom-block-title"},[t._v("注意")]),t._v(" "),a("p",[t._v("在"),a("code",[t._v("v1.8.2")]),t._v("版本之前，没有"),a("code",[t._v("path")]),t._v("属性，使用"),a("code",[t._v("key")]),t._v("代替。但"),a("code",[t._v("key")]),t._v("只支持指定"),a("code",[t._v("一级目录")]),t._v("的文件夹名称。")])]),t._v(" "),a("h3",{attrs:{id:"pagecomponent-data-imgurl"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#pagecomponent-data-imgurl"}},[t._v("#")]),t._v(" pageComponent.data.imgUrl")]),t._v(" "),a("ul",[a("li",[t._v("目录页内的图片，可选。(v1.9.4之前必填)")]),t._v(" "),a("li",[t._v("图片尺寸在页面中显示为80px*80px")])]),t._v(" "),a("h3",{attrs:{id:"pagecomponent-data-description"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#pagecomponent-data-description"}},[t._v("#")]),t._v(" pageComponent.data.description")]),t._v(" "),a("ul",[a("li",[t._v("目录描述，必需")]),t._v(" "),a("li",[t._v("可加a标签（如需加入a标签时，标签内部的引号请使用单引号）")])]),t._v(" "),a("h2",{attrs:{id:"如何在导航栏中添加目录页链接"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#如何在导航栏中添加目录页链接"}},[t._v("#")]),t._v(" 如何在导航栏中添加目录页链接")]),t._v(" "),a("div",{staticClass:"language-js extra-class"},[a("pre",{pre:!0,attrs:{class:"language-js"}},[a("code",[t._v(" "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("// config.js")]),t._v("\nmodule"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(".")]),t._v("exports "),a("span",{pre:!0,attrs:{class:"token operator"}},[t._v("=")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("{")]),t._v("\n    "),a("span",{pre:!0,attrs:{class:"token literal-property property"}},[t._v("themeConfig")]),a("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("{")]),t._v("\n        "),a("span",{pre:!0,attrs:{class:"token literal-property property"}},[t._v("nav")]),a("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v("  "),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("[")]),t._v("\n            "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("// 没有二级导航时可以直接添加")]),t._v("\n           "),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("{")]),a("span",{pre:!0,attrs:{class:"token literal-property property"}},[t._v("text")]),a("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token string"}},[t._v("'目录页'")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token literal-property property"}},[t._v("link")]),a("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token string"}},[t._v("'/web/'")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("}")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("\n\n          "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("// 有二级导航时")]),t._v("\n           "),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("{")]),a("span",{pre:!0,attrs:{class:"token literal-property property"}},[t._v("text")]),a("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token string"}},[t._v("'页面'")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("\n               "),a("span",{pre:!0,attrs:{class:"token literal-property property"}},[t._v("link")]),a("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token string"}},[t._v("'/ui/'")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("   "),a("span",{pre:!0,attrs:{class:"token comment"}},[t._v("//目录页， vdoing主题新增的配置项，有二级导航时，可以点击一级导航跳到目录页")]),t._v("\n               "),a("span",{pre:!0,attrs:{class:"token literal-property property"}},[t._v("items")]),a("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("[")]),t._v("\n                 "),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("{")]),a("span",{pre:!0,attrs:{class:"token literal-property property"}},[t._v("text")]),a("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token string"}},[t._v("'HTML'")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token literal-property property"}},[t._v("link")]),a("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token string"}},[t._v("'/pages/11/'")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("}")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("\n                 "),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("{")]),a("span",{pre:!0,attrs:{class:"token literal-property property"}},[t._v("text")]),a("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token string"}},[t._v("'CSS'")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token literal-property property"}},[t._v("link")]),a("span",{pre:!0,attrs:{class:"token operator"}},[t._v(":")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token string"}},[t._v("'/pages/22/'")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("}")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("\n               "),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("]")]),t._v("\n            "),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("}")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(",")]),t._v("\n       "),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("]")]),t._v("\n    "),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("}")]),t._v("\n"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("}")]),t._v("\n")])])]),a("p",[t._v("参考："),a("a",{attrs:{href:"https://github.com/xugaoyi/vuepress-theme-vdoing/blob/master/docs/.vuepress/config/nav.js",target:"_blank",rel:"noopener noreferrer"}},[t._v(".vuepress/config/nav.js"),a("OutboundLink")],1)])])}),[],!1,null,null,null);a.default=n.exports}}]);