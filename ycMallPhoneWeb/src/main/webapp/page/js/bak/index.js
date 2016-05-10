/**
 * Created by Shi on 2015/12/5.
 */

/*
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
}*/



define(function(require,exports,module){
    var $ = require("zepto");
    var _ = require("underscore");
    var Backbone = require('backbone');
    var result = require('./model/result');
//    var coolView = require('./view/coolView');

    var _location = Backbone.Collection.extend({
        model:result,
        url:"http://localhost:7071/phone/APP/getMorePromotionGoods.jhtml?pageSize=1&count=10&goodsType=1",
        parse: function (response) {
            if(response.code == "1"){
                console.log(response.result);
            }else{
                alert("访问错误");
            }
        }
    });

    var loc =  new _location();

    var CoolView = Backbone.View.extend({
        initialize: function () {
            loc.fetch();
            this.render(loc);
        },
        render: function (data) {
            var tpl = "<ul><% _.each(data,function(item){%> " +
                "<li class='product-item'>" +
                "<a href='../../goods/content/<% =item.sn %>.jhtml'>" +
                "<div><img class='product-img' src='<% =item.productImages[0].large %>' /></div>" +
                "<div class='product-info'><% =item.name %><br /><span class='product-price'><% =item.price %></span></div>" +
                "</a>" +
                "</li>" +
                "<% }); %></ul>"
            var template = _.template(tpl.tpl,data);

            console.log("----")
            console.log(template);

        }
    });

    var app = new CoolView;

//    var view = coolView.CoolView(loc);
});