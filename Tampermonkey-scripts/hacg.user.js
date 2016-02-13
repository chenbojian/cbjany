// ==UserScript==
// @name         hacg by cbj
// @namespace    http://your.homepage/
// @version      0.1.3
// @description  enter something useful
// @author       You
// @match        http*://hacg.lol/*
// @match        http*://*.hacg.lol/*
// @grant        none
// ==/UserScript==

var content = $('.entry-content');
content.each(function(i, el) {
    var newContent = $(el).html().replace(/[A-Za-z0-9]{40}/g, '<div class = "magnet">magnet:?xt=urn:btih:$&</div>');
    newContent = newContent.replace(/(\/s\/\w+) /g, 'http://pan.baidu.com$1');
    $(el).html(newContent);
});
var magnet = $('.magnet');
magnet.css('color','blue');

magnet.click(function(){
	var allMagnets = [];
    $(".magnet").each((i, el) => allMagnets.push(el.html()));
    console.log(allMagnets);
});
