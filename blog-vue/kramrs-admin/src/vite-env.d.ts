/// <reference types="vite/client" />

declare module "*.vue" {
    import type {DefineComponent} from "vue";
    const component: DefineComponent<{}, {}, any>;
    export default component;
}

declare module 'markdown-it-mark';
declare module 'markdown-it-link-attributes';
