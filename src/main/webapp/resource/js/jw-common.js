function templetRepalce(html, arg) {
    var args = arguments;
    for (var i = 1; i < args.length; i++) {
        var rg = new RegExp("\\{" + i + "\\}", "gi");
        html = html.replace(rg, args[i]);
    }
    return html;
}
function parsejsonref(a) {
    function aa(a) {
        var c = [];
        if (a && typeof a === 'object') {
            if (a["$ref"]) {
                return 1;
            }
            for (var i in a) {
                var r = aa(a[i]);
                if (r === 1)
                    c.push({o: a, p: i, v: a[i]["$ref"]});
                if (typeof r === typeof c) {
                    c = c.concat(r);
                }
            }
            return c;
        }
        return 0;
    }
    var c = aa(a);
    var $ = a;
    for (var i in c) {
        c[i].o[c[i].p] = eval(c[i].v);
    }
    return $;
}
$(function() {
    //弹窗的关闭事件
    $(document).on("click", ".popover .glyphicon-remove", function() {
        var pp = $(this).closest(".popover");
        pp.prev().popover('hide');
        pp.remove();
    });
    //弹窗时候关闭其他弹窗
    $(document).on("click", '.popover-close-other', function() {
        var out = this;
        $('.popover-close-other').filter(function() {
            return this !== out;
        }).popover('hide');
    });
});
(function($) {
    var __cache = {};
    $.extend({
        requestCacheJson: function(cache_key, url, param, func) {
            var json = __cache[cache_key];
            if (json) {
                setTimeout(function() {
                    func(json);
                }, 1);
                return;
            } else {
                return $.post(url, param, function(json) {
                    __cache[cache_key] = json;
                    func(json);
                }, "json");
            }
        },
        loadScript: function(js) {
            var oHead = document.getElementsByTagName('HEAD').item(0);
            var oScript = document.createElement("script");
            oScript.type = "text/javascript";
            oScript.src = js;
            oHead.appendChild(oScript);
        },
        request: function(fm, func, option) {
            option = option || {};
            $.submit(fm, function() {
                var data = arguments[0], message = data.success ? data.messages : data.errors, html = [];
                for (var item in message) {
                    html.push(message[item]);
                }
                if (message.redirect && !option.noredirect) {
                    location.href = message.redirect;
                    return;
                }
                if (!data.success && !message.redirect) {
                    alert(html.join(","));
                }
                func ? func.apply(this, arguments) : "";
            }, option);
        },
        submit: function(fm, func, option) {
            var xhr = null;
            $(fm).validationEngine("detach").validationEngine("attach", {"onValidationComplete": function(f, success) {
                    if (success && !xhr) {
                        var fm = $(f);
                        $(".submit", fm).attr("disabled", true);
                        var opt = $.extend(true, {
                            "url": fm.attr("action"),
                            "data": fm.serialize(),
                            "error": function() {
                                xhr = null;
                                $(":submit", fm).attr("disabled", false);
                                alert("ERROR");
                            }, "success": function() {
                                xhr = null;
                                func.apply(this, arguments);
                                $(":submit", fm).attr("disabled", false);
                            }, "type": "POST", "dataType": "json", "mimeType": "text/json"
                        }, option);
                        if (opt.dataType === "jsonp" || opt.type === "GET") {
                            opt.data = encodeURI(opt.data);
                        }
                        xhr = $.ajax(opt);
                    }
                    return false;
                }
            });
        },
        crossSubmit: function(fm, func, option) {
            $.request(fm, func, $.extend(true, {dataType: "jsonp"}, option));
        }
    });
    $(function() {
        $("form input:submit").attr("disabled", false);
    });
})(jQuery);