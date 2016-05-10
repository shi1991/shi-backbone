/**
 * Created by Shi on 2015/12/5.
 */

define(function(require,exports,module){
    var $ = require("zepto");
    var _ = require("underscore");
    var Backbone = require('backbone');
//    var result = require('./model/result');

    var result = Backbone.Model.extend({

    });

    var _location = Backbone.Collection.extend({
        model:result,
        url:"http://localhost:7071/phone/APP/getMorePromotionGoods.jhtml?pageSize=1&count=10&goodsType=1",
        parse: function (response) {
            if(response.code == "1"){
                console.log(response.result);
                return response.result;
            }else{
                alert("访问错误");
            }
        }
    });

    var CoolView = Backbone.View.extend({
        initialize: function () {
            collection = new _location();
            console.log(collection);
            var data = collection.fetch();
            this.render(data);
        },
        render: function (data) {
            _.each(data["result"],function(goods) {
                console.log(goods);
            });
        }
    });

    var app = new CoolView();

});