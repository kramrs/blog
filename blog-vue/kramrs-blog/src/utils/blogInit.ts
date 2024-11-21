import {useBlogStore} from "@/store";
import {getBlogInfo} from "@/api/blogInfo";


export async function blogInit() {
	const blog = useBlogStore();
	const res = await getBlogInfo();
	blog.setBlogInfo(res.data.data);
}
