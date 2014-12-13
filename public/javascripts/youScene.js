$(function() {
  $('a.btn-like').click(function() {
    $.ajax({
      url: $(this).attr("url"),
      type: "POST"
    }).done(function() {
      $('a.btn-like').addClass("disabled");
      var like_count = $('span.like-count').text();
      like_count = parseInt(like_count);
      like_count += 1;
      $('span.like-count').text(like_count);
    });
  });
});