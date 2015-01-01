$ ->
  $("a.btn-like").click ->
    $.ajax(
      url: $(this).attr("url")
      type: "POST"
    ).done ->
      $("a.btn-like").addClass "disabled"
      like_count = $(".like-count").text()
      like_count = parseInt(like_count)
      like_count += 1
      $(".like-count").text like_count
      return

    return

  $("a.btn-create-tag").click ->
    tagName = $("input#tagName").val()
    $.ajax(
      url: $(this).attr("url")
      type: "POST"
      data:
        tagName: tagName
    ).done ->
      isSelected = false
      $("input[name=\"tagNames[]\"]").each ->
        isSelected = true  if $(this).val() is tagName
        return

      unless isSelected
        $("span.selected-tag").prepend "<input id=\"" + tagName + "\" name=\"tagNames[]\" type=\"hidden\" value=\"" + tagName + "\"><span id=\"" + tagName + "\" class=\"selected-tag-name tag\">" + tagName + "<a id=\"" + tagName + "\" class=\"delete-tag\" href=\"#\">x</a></span>"
        $("select[name=tag] option").each ->
          @remove()  if $(this).val() is tagName
          return

      $("#newTagModal").modal "hide"
      return

    return

  $("select#tag").change ->
    selected = $("select[name=\"tag\"] option:selected")
    tagName = selected.val()
    $("span.selected-tag").prepend "<input id=\"" + tagName + "\" name=\"tagNames[]\" type=\"hidden\" value=\"" + tagName + "\"><span id=\"" + tagName + "\"class=\"selected-tag-name tag\">" + tagName + "<a id=\"" + tagName + "\" class=\"delete-tag\" href=\"#\">x</a></span>"
    selected.remove()
    return

  $("div.label-tag").on "click", "a.delete-tag", ->
    tagName = $(this).attr("id")
    $("option.blank").after "<option value=\"" + tagName + "\">" + tagName + "</option>"
    $("div.label-tag [id=\"" + tagName + "\"]").remove()
    return

  $("input[name=\"tagNames[]\"]").each ->
    $("option[value=\"" + $(this).val() + "\"").remove()
    return

  return
