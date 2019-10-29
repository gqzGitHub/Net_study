package com.gqz.util;

/**
 * 分页工具类
 * @author Administrator
 *
 */
public class PageUtil {

	/**
	 * 生成分页代码
	 * @param targetUrl 目标地址
	 * @param totalNum 总记录数
	 * @param currentPage 当前页
	 * @param pageSize 每页大小
	 * @return
	 */
	public static String genPagination(String targetUrl,long totalNum,int currentPage,int pageSize){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		if(totalPage==0){
			return "未查询到数据";
		}else{
			StringBuffer pageCode=new StringBuffer();
			pageCode.append("<li><a href='"+targetUrl+"/1'>首页</a></li>");
			if(currentPage>1){
				pageCode.append("<li><a href='"+targetUrl+"/"+(currentPage-1)+"' class='layui-laypage-prev'>上一页</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
			}
			for(int i=currentPage-7;i<=currentPage+7;i++){
				if(i<1||i>totalPage){
					continue;
				}
				if(i==currentPage){
					pageCode.append("<li class='active'><a href='"+targetUrl+"/"+i+"'>"+i+"</a></li>");
				}else{
					pageCode.append("<li><a href='"+targetUrl+"/"+i+"'>"+i+"</a></li>");
				}
			}
			if(currentPage<totalPage){
				pageCode.append("<li><a href='"+targetUrl+"/"+(currentPage+1)+"' class='layui-laypage-next'>下一页</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
			}
			pageCode.append("<li><a href='"+targetUrl+"/"+totalPage+"'>尾页</a></li>");
			return pageCode.toString();
		}
	}


	/**
	 * 生成搜索分页代码
	 * @param targetUrl
	 * @param totalNum
	 * @param currentPage
	 * @param pageSize
	 * @param q
	 * @return
	 */
	public static String genSearchPagination(String targetUrl,long totalNum,int currentPage,int pageSize,String q){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		if(totalPage==0){
			return "";
		}else{
			StringBuffer pageCode=new StringBuffer();
			pageCode.append("<li><a href='"+targetUrl+"?page=1&q="+q+"'>首页</a></li>");
			if(currentPage>1){
				pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage-1)+"&q="+q+"' class='layui-laypage-prev'>上一页</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
			}
			for(int i=currentPage-7;i<=currentPage+7;i++){
				if(i<1||i>totalPage){
					continue;
				}
				if(i==currentPage){
					pageCode.append("<li class='active'><a href='"+targetUrl+"?page="+i+"&q="+q+"'>"+i+"</a></li>");
				}else{
					pageCode.append("<li><a href='"+targetUrl+"?page="+i+"&q="+q+"'>"+i+"</a></li>");
				}
			}
			if(currentPage<totalPage){
				pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage+1)+"&q="+q+"' class='layui-laypage-next'>下一页</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
			}
			pageCode.append("<li><a href='"+targetUrl+"?page=1&q="+q+"'>尾页</a></li>");
			return pageCode.toString();
		}
	}

	
	
}
