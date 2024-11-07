
import type { ProxyOptions } from "vite";
import { createServiceConfig } from "../../src/utils/service";

/**
 * 设置网络代理
 * @param env
 */
export function createViteProxy(env: Env.ImportMeta) {
	// 是否开启代理
	const isOpenProxy = env.VITE_HTTP_PROXY === "Y";

	if (!isOpenProxy) {
		return undefined;
	}

	const { baseURL, proxyPattern } = createServiceConfig(env);

	const proxy: Record<string, ProxyOptions> = createProxyItem({ baseURL, proxyPattern });

	return proxy;
}

function createProxyItem(item: App.Service.ServiceConfig) {
	const proxy: Record<string, ProxyOptions> = {};

	proxy[item.proxyPattern] = {
		target: item.baseURL,
		changeOrigin: true,
		rewrite: (path) => path.replace(new RegExp(`^${item.proxyPattern}`), ""),
	};

	return proxy;
}
