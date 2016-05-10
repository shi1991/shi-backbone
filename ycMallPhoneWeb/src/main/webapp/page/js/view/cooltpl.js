define(function(require,exports,module){

    var tpl = "<ul><% _.each(data,function(item){%> " +
        "<li class='product-item'>" +
        "<a href='../../goods/content/<% =item.sn %>.jhtml'>" +
        "<div><img class='product-img' src='<% =item.productImages[0].large %>' /></div>" +
        "<div class='product-info'><% =item.name %><br /><span class='product-price'><% =item.price %></span></div>" +
        "</a>" +
        "</li>" +
        "<% }); %></ul>"

    exports.tpl = tpl;

});