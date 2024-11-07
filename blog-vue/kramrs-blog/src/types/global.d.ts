interface Window {
	$dialog?: import("naive-ui").DialogProviderInst;
	$message?: import("naive-ui").MessageProviderInst;
	$notification?: import("naive-ui").NotificationProviderInst;
}

// 解决vue-cropper打包错误
declare module "*.vue" {
	import type {DefineComponent} from "vue";
	const component: DefineComponent<{}, {}, any>;
	export default component;
}

interface ImportMeta {
	readonly env: Env.ImportMeta;
}


declare module "APlayer";
declare module "vue-cropper";
declare module 'markdown-it-mark';
declare module 'markdown-it-link-attributes';
