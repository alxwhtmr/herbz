$(function() {
  var moveLeft = 20;
  var moveDown = 10;
  var idglobal;

  $('a#trigger').hover(function(e) {
    var idlocal = $(this).attr('itemid');
    idglobal = idlocal;
    console.log(idglobal);

    $('div#'+idglobal).show();
      //.css('top', e.pageY + moveDown)
      //.css('left', e.pageX + moveLeft)
      //.appendTo('body');
  }, function() {
    $('div#'+idglobal).hide();
  });

  $('a#trigger').mousemove(function(e) {
    $("div.pop-up").css('top', e.pageY + moveDown).css('left', e.pageX + moveLeft);
  });

});