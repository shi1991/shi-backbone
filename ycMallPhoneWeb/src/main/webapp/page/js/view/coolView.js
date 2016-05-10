/**
 * Created by shi on 2016/5/10.
 */

define(function(require,exports,module){
    var tpl = require("./cooltpl");
    var Backbone = require("../backbone");
    var _ = require("../underscore");
    var CoolView = Backbone.View.extend({
        initialize: function () {
            //this.collection.fetch();
            this.render({});
        },
        el:"#coolProducts",
        render: function (data) {

            var template = _.template(tpl.tpl,data);

            console.log(template);
        }
    });


    exports.CoolView = function (collection) {
        new CoolView({"collection":collection});
    }

});
