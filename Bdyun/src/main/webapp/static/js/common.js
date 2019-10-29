var sk=["慕课","慕","咕泡","咕","泡"];

function submitData(){
    var q=$("#q").val();
    for(var i=0;i<sk.length;i++){
        if(q.search(sk[i])!=-1){
            alert("您输入的请求关键字中有敏感词，请换个关键字！")
            return;
        }
    }
    if(q.trim()==""){
        alert("大佬，请输入您要搜索的关键字！");
        return;
    }
    window.location.href="/search?q="+q;
}

function reloadPage(){
    window.location.reload();
}
