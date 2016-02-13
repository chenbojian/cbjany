// ==UserScript==
// @name         hacg by cbj
// @namespace    http://your.homepage/
// @version      0.1.1
// @description  enter something useful
// @author       You
// @match        https://hacg.lol/*
// @grant        none
// ==/UserScript==

var content = $('.entry-content');
content.each(function() {
    var newContent = $(this).html().replace(/[A-Za-z0-9]{40}/g, '<div class = "magnet">magnet:?xt=urn:btih:$&</div>');
    newContent = newContent.replace(/\/s\//, 'http://pan.baidu.com/s/');
    $(this).html(newContent);
});
var magnet = $('.magnet');
magnet.css('color','blue');

magnet.click(function(){
	var allMagnets = [];
    $(".magnet").each(e => allMagnets.push(e.html()));
    console.log(allMagnets);
});
