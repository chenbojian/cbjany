// ==UserScript==
// @name         hacg by cbj
// @namespace    http://your.homepage/
// @version      0.1.18
// @description  enter something useful
// @author       You
// @require      https://cdnjs.cloudflare.com/ajax/libs/clipboard.js/1.5.8/clipboard.min.js
// @require      https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.6.15/browser-polyfill.min.js
// @require      https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.6.15/browser.min.js
// @include      *://*.hacg.*/*
// ==/UserScript==
/* jshint ignore:start */
var inline_src = (<><![CDATA[
/* jshint ignore:end */
/* jshint esnext: true */

// Your code here...
(function () {
    // body...
    var $ = jQuery;

    var tool = $('<div class="cbj-hacg-tool"></div>');
    $('body').append(tool);

    tool.css({
        cursor: 'pointer',
        position: 'fixed',
        right: '20px',
        bottom: '20px',
        width: '20px',
        height: '20px',
        backgroundColor: 'white'
    });

    var content = $('article').html() + $('#comments .commentList').html();
    content = content.replace('本站不提供下载', '');
    var clipboard;
    tool.click(function() {
        var links = content.match(/[A-Za-z0-9]{40}/g).map(m => `magnet:?xt=urn:btih:${m}`);
        var baiduReg = /(\/s\/\w+) (\w+)/g;
        var result, baiduLinks = [];
        while ((result = baiduReg.exec(content)) !== null) {
            result = baiduReg.exec(content);
            baiduLinks.push(`<a href="http://pan.baidu.com${result[1]}">${result[1]}</a> ${result[2]}`);
        }

        if($('.cbj-abstract').length === 0){
            buildAbstract(links.concat(baiduLinks));
        } else {
            clipboard.destroy();
            $('.cbj-abstract').remove();
        }
    });

    function buildAbstract(links) {
        links = [...new Set(links)];
        var icon = 'https://cdnjs.cloudflare.com/ajax/libs/foundicons/3.0.0/svgs/fi-page-copy.svg';
        var list = links.map((m,i) => `<li><span>${m}</span><button class="copy"><img src="${icon}" alt="Copy to clipboard"></button></li>`).join('');
        var div = $(`<div class="cbj-abstract"><ul>${list}</ul></div>`);
        $('.entry-content').prepend(div);

        var buttonStyle = {
            position: 'relative',
            display: 'inline-block',
            margin: '4px 8px',
            height: '22px',
            padding: '0',
            fontSize: '13px',
            fontWeight: 'bold',
            lineHeight: '20px',
            color: '#333',
            whiteSpace: 'nowrap',
            verticalAlign: 'middle',
            cursor: 'pointer',
            backgroundColor: '#eee',
            backgroundImage: 'linear-gradient(#fcfcfc, #eee)',
            border: '1px solid #d5d5d5',
            borderRadius: '3px'
        };
        $('button.copy').css(buttonStyle);
        $('button.copy img').css({width: '20px', height: '20px'});
        div.find('li').css('background-color','rgb(50,50,50)');
        clipboard = new Clipboard('button.copy', {target: button => button.previousSibling});
    }

})();


/* jshint ignore:start */
]]></>).toString();
var c = babel.transform(inline_src);
eval(c.code);
/* jshint ignore:end */

