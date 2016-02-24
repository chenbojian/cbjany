// ==UserScript==
// @name         hacg by cbj
// @namespace    http://your.homepage/
// @version      0.1.6
// @description  enter something useful
// @author       You
// @match        http*://hacg.*/*
// @match        http*://*.hacg.*/*
// ==/UserScript==

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

var content = $('article').html() + $('#comments').html();

tool.click(function() {
	var magnets = content.match(/[A-Za-z0-9]{40}/g).map(m => `magnet:?xt=urn:btih:${m}`);
	var baiduReg = /(\/s\/\w+) (\w+)/g;
	var result, baiduLinks = [];
	while(result = baiduReg.exec(content)) {
		baiduLinks.push(`<a href="http://pan.baidu.com${result[1]}">${result[1]}</a> ${result[2]}`);
	}

    if($('.cbj-abstract').length === 0){
        buildAbstract(magnets.concat(baiduLinks));
    } else {
        $('.cbj-abstract').remove();
    }
})

function buildAbstract(magnets) {
    var list = magnets.map(m => `<li>${m}</li>`).join('');
    var div = $(`<div class="cbj-abstract"><ul>${list}</ul></div>`);
    $('.entry-content').prepend(div);
}
