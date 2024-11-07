import "@/assets/styles/index.scss";
import CalendarHeatmap from "@/components/CalendarHeatmap/index.vue";
import Echarts from "@/components/Echarts/index.vue";
import Pagination from "@/components/Pagination/index.vue";
import RightToolbar from "@/components/RightToolbar/index.vue";
import SvgIcon from "@/components/SvgIcon/index.vue";
import * as directive from "@/directive";
import "@/permission";
import router from "@/router";
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import ElementPlus from "element-plus";
import "element-plus/theme-chalk/index.css";
import "element-plus/theme-chalk/dark/css-vars.css";
import "nprogress/nprogress.css";
import {createPinia} from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import VueViewer from "v-viewer";
import "viewerjs/dist/viewer.css";
import "virtual:svg-icons-register";
import {createApp, Directive} from "vue";
import App from "./App.vue";

import {config} from 'md-editor-v3';
import {lineNumbers} from '@codemirror/view';
import ZH_TW from '@vavt/cm-extension/dist/locale/zh-TW';
import '@vavt/cm-extension/dist/previewTheme/arknights.css';
import MarkExtension from "markdown-it-mark";
import Anchor from "markdown-it-anchor";
import LinkAttr from "markdown-it-link-attributes";


const app = createApp(App);
const pinia = createPinia();


Object.keys(directive).forEach((key) => {
    app.directive(key, (directive as { [key: string]: Directive })[key]);
});

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}
config({
    markdownItPlugins(plugins, {editorId}) {
        return [
            ...plugins.map((item) => {
                if (item.type === 'taskList') {
                    return {
                        ...item,
                        options: {
                            ...item.options,
                            enabled: editorId === 'editor-preview'
                        }
                    };
                }

                return item;
            }),
            {
                type: 'mark',
                plugin: MarkExtension,
                options: {}
            },
            {
                type: 'linkAttr',
                plugin: LinkAttr,
                options: {
                    matcher(href: string) {
                        return !href.startsWith('#');
                    },
                    attrs: {
                        target: '_blank'
                    }
                }
            },
            {
                type: 'anchor',
                plugin: Anchor,
                options: {
                    // permalink: true,
                    permalink: Anchor.permalink.headerLink(),
                    // permalinkSymbol: '#',
                    // permalinkBefore: false,
                    // permalinkSpace: false,
                    slugify(s: string) {
                        return s;
                    }
                }
            }
        ];
    },
    codeMirrorExtensions(theme, extensions) {
        const _exs = [...extensions, lineNumbers()];

        // _exs[1] = basicSetup;
        return _exs;
    },
    editorConfig: {
        languageUserDefined: {
            ZH_TW
        }
    },
    editorExtensions: {
        highlight: {
            css: {
                atom: {
                    light:
                        'https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.5.1/styles/atom-one-dark.min.css',
                    dark: 'https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.5.1/styles/atom-one-dark.min.css'
                }
            }
        }
    }
});
pinia.use(piniaPluginPersistedstate);
app.use(pinia);
app.use(router);
app.use(VueViewer);
app.component("CalendarHeatmap", CalendarHeatmap);
app.component("svg-icon", SvgIcon);
app.component("Pagination", Pagination);
app.component("RightToolbar", RightToolbar);
app.component("Echarts", Echarts);
app.use(ElementPlus);
app.mount("#app");
