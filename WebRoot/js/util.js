var alertMsg = function(title,msg){
    var d = dialog({
        title:title,
        content:msg
    });
    d.show();
    return d;
}