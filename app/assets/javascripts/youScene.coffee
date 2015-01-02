$ ->
  $("a.btn-like").click ->
    $.ajax(
      url: $(this).attr "url"
      type: "POST"
    ).done ->
      $("a.btn-like").addClass "disabled"
      like_count = $(".like-count").text()
      like_count = parseInt like_count
      like_count += 1
      $(".like-count").text like_count
      return
    return

  $("a.btn-create-tag").click ->
    tagName = $("input#tagName").val()
    $.ajax(
      url: $(this).attr "url"
      type: "POST"
      data:
        tagName: tagName
    ).done ->
      isExist = false
      $("input[name='tag-list']").each ->
        isExist = true if $(this).attr("id") is tagName
        return
      unless isExist
        $("tbody.tag-lists").prepend '<tr><td><input type="checkbox" id="' + tagName + '" name="tag-list"></td><td>' + tagName + '</td></tr>'
      isSelected = false
      $("input[name='tagNames[]']").each ->
        isSelected = true if $(this).val() is tagName
        return
      unless isSelected
        $("span.selected-tag").prepend "<input id=\"" + tagName + "\" name=\"tagNames[]\" type=\"hidden\" value=\"" + tagName + "\"><span id=\"" + tagName + "\" class=\"selected-tag-name tag\">" + tagName + "<a id=\"" + tagName + "\" class=\"delete-tag\" href=\"#\">×</a></span>"
      $("input#tagName").val ""
      return
    return

  $("div.label-tags").on "click", "a.delete-tag", ->
    tagName = $(this).attr "id"
    $("div.label-tags [id=\"" + tagName + "\"]").remove()
    return

  $("a.decision-select-tag").click ->
    tagNames = []
    $("input[name='tag-list']:checked").each ->
      tagNames.push $(this).attr "id"
      return
    $("span.selected-tag").empty()
    i = 0
    while i < tagNames.length
      $("span.selected-tag").prepend '<input id="' + tagNames[i] + '" name="tagNames[]" type="hidden" value="' + tagNames[i] + '"><span id="' + tagNames[i] + '" class="selected-tag-name tag">' + tagNames[i] + '<a id="' + tagNames[i] + '" class="delete-tag" href="#">×</a></span'
      i++
    $("#selectTagModal").modal "hide"
    return

  $("a.select-tag-link").click ->
    selectedTags = []
    $("span.selected-tag-name").each ->
      selectedTags.push $(this).attr "id"
      return
    $("input[name='tag-list']").removeAttr "checked"
    i = 0
    while i < selectedTags.length
      $("input[id='" + selectedTags[i] + "']").prop "checked", true
      i++
    return

  $("input.post").click ->
    selectedTagField = $("span.selected-tag").html()
    localStorage.setItem "selectedTagField", selectedTagField
    return

  selectedTagField = localStorage.getItem("selectedTagField")
  if selectedTagField?
    selectedTag = $("span.selected-tag")
    selectedTag.empty()
    selectedTag.append selectedTagField
    localStorage.removeItem "selectedTagField"

  $("input#tagName").autocomplete source: (request, response) ->
    $.ajax(
      url: "/youScene/tags/list"
      type: "GET"
      dataType: "json"
      data:
        term: request.term
    ).done (data) ->
      response $.map(data,(item) ->
        label: item.tagName
        value: item.tagName
      )
      return
    return
  return
