/**
 * Created by Shi on 2015/12/5.
 */

define(function(require,exports,module){
    var $ = require("zepto");
    var _ = require("underscore");
    var Backbone = require('backbone');
//    var result = require('./model/result');

    var result = Backbone.Model.extend({
        url:"http://localhost:7071/phone/APP/getMorePromotionGoods.jhtml?pageSize=1&count=10&goodsType=1",
        defaults:{
            code:"0",
            result:''
        }
    });

    var _location = Backbone.Collection.extend({
        model:result,
        url:"http://localhost:7071/phone/APP/getMorePromotionGoods.jhtml?pageSize=1&count=10&goodsType=1",
    });

    var tpl = "<ul><% _.each(data,function(item){%> " +
        "<li class='product-item'>" +
        "<div><img class='product-img' src='<%=item.goodsImagePath %>' /></div>" +
        "<div class='product-info'><%=item.goodsName %><br /><span class='product-price'><%=item.goodsPrice %></span></div>" +
        "</li>" +
        "<% }); %></ul>"

    var CoolView = Backbone.View.extend({
        el:'#coolProducts',
        template: _.template(tpl),
        events:{
            'click .product-img':'clickImg'
        },
        initialize: function () {
            this.listenTo(this.model,'sync change',this.render);
            this.model.fetch();
            this.render();
        },
        render:function(){
            var data = this.model.get('result');
            var html = this.template({"data":data});

            this.$el.html(html);
            return this;
        },
        clickImg:function(evt){
            alert("点击图片");
        }
    });
    var col = new _location();

//    var app = new CoolView({collection:col});
    var a = new result();
    var app = new CoolView({ model:a });

});