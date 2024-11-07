import {createApp} from "vue";
import App from "./App.vue";
import {setupDirectives} from "./directives";
import {setupAssets, setupLazy, setupMasonry, setupViewer} from "./plugins";
import {setupRouter} from "./router";
import {setupStore} from "./store";
import {titleChange} from "@/utils/title";

import {config} from 'md-editor-v3';
import {lineNumbers} from '@codemirror/view';
import ZH_TW from '@vavt/cm-extension/dist/locale/zh-TW';
import '@vavt/cm-extension/dist/previewTheme/arknights.css';
import MarkExtension from "markdown-it-mark";
import Anchor from "markdown-it-anchor";
import LinkAttr from "markdown-it-link-attributes";

async function setupApp() {
	setupAssets();

	const app = createApp(App);

	setupStore(app);

	setupDirectives(app);

	setupLazy(app);

	setupMasonry(app);

	setupViewer(app);

	await setupRouter(app);

	app.mount("#app");

	titleChange();

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
setupApp();
