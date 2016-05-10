/**
 * Created by Shi on 2015/12/5.
 */

Zepto(function(){
    indexInit.init();
});

var indexInit={
    init:function(){
        this.bindTitleTapEvent();
    },
    //对限量 酷炫神器做事件绑定
    bindTitleTapEvent:function(){
        $(".menu-list").on("tap" ,".menu" , function(){
            var $this = $(this);
            $(".menu-list .select").removeClass("select");
            $this.addClass("select");

            $(".cool-products").hide();
            $("#"+$this.attr("id")+"Products").show();
        })
    }
}