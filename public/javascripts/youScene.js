$(function() {
    $('a.btn-like').click(function() {
	$.ajax({
	    url: $(this).attr("url"),
	    type: "POST"
	}).done(function() {
	    $('a.btn-like').addClass("disabled");
	    var like_count = $('.like-count').text();
	    like_count = parseInt(like_count);
	    like_count += 1;
	    $('.like-count').text(like_count);
	});
    });
    $('a.btn-create-tag').click(function() {
	$.ajax({
	    url: $(this).attr("url"),
	    type: "POST",
	    data: {tagName: $('input#tagName').val()}
	}).done(function() {
	    alert("success!");
	    $('#newTagModal').modal("hide");
	}).fail(function() {
	    alert("failed!");
	});
    });
    $('select#tag').change(function() {
	var selected = $('select[name="tag"] option:selected');
	var tagName = selected.val();
	$('label[for="tag"]').after(
	    '<input name="tagNames[]" type="hidden" value="' + tagName +
	    '"><span id="' + tagName + '"class="selected-tag-name">' + tagName + '</span>'
	);
	selected.remove();
    });
});
