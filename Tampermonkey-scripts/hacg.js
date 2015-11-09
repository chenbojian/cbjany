// ==UserScript==
// @name         hacg by cbj
// @namespace    http://your.homepage/
// @version      0.1
// @description  enter something useful
// @author       You
// @match        https://hacg.club/*
// @grant        none
// ==/UserScript==

var content = $('.entry-content');
content.each(function() {
    var newContent = $(this).html().replace(/[A-Za-z0-9]{40}/g, '<div class = "magnet">magnet:?xt=urn:btih:$&</div>');
    $(this).html(newContent);
});
var magnet = $('.magnet');
magnet.css('color','blue');

magnet.click(function(){
    console.log(this);
});
