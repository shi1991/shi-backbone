/**
 * Created by shi on 2016/5/9.
 */

define(function(require,exports,module){
    var Backbone = require('backbone');
    var result = Backbone.Model.extend({
        "idAttribute":"goodsId"
    });


    exports.result = result;

})