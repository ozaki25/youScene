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
	var tagName = $('input#tagName').val();
	$.ajax({
	    url: $(this).attr("url"),
	    type: "POST",
	    data: {tagName: tagName}
	}).done(function() {
	    var isSelected = false;
	    $('input[name="tagNames[]"]').each(function() {
		if($(this).val() == tagName) isSelected = true;
	    });
	    if(!isSelected) {
		$('label[for="tag"]').after(
		    '<input name="tagNames[]" type="hidden" value="' + tagName +
		    '"><span id="' + tagName + '"class="selected-tag-name">' +
		    tagName + '</span>'
		);
		$('select[name=tag] option').each(function() {
		    if($(this).val() == tagName) this.remove();
		});
	    }
	    $('#newTagModal').modal("hide");
	});
    });
    $('select#tag').change(function() {
	var selected = $('select[name="tag"] option:selected');
	var tagName = selected.val();
	$('label[for="tag"]').after(
	    '<input name="tagNames[]" type="hidden" value="' + tagName +
	    '"><span id="' + tagName + '"class="selected-tag-name">' +
	    tagName + '</span>'
	);
	selected.remove();
    });
});