$ ->
  #いいね
  $("a.btn-like").click ->
    $.ajax
      url: $(this).attr "url"
      type: "POST"
    .done ->
      $("a.btn-like").addClass "disabled"
      like_count = $(".like-count").text()
      like_count = parseInt like_count
      like_count += 1
      $(".like-count").text like_count

  #タグ付け
  $("a.btn-create-tag").click ->
    tagName = $("input#tagName").val()
    $.ajax
      url: $(this).attr "url"
      type: "POST"
      data:
        tagName: tagName
    .done ->
      isExist = false
      $("input[name='tag-list']").each ->
        isExist = true if $(this).attr("id") is tagName
        return
      unless isExist
        $("script#list-tag-tmpl").tmpl
          tagName: tagName
        .prependTo "tbody.tag-lists"
      isSelected = false
      $("input[name='tagNames[]']").each ->
        isSelected = true if $(this).val() is tagName
        return
      unless isSelected
        $("script#selected-tags-tmpl").tmpl
          tagName: tagName
        .prependTo "span.selected-tag"
      $("input#tagName").val ""
      return
    return

  $("div.label-tags").on "click", "a.delete-tag", ->
    tagName = $(this).attr "id"
    $("div.label-tags [id=#{tagName}]").remove()
    return

  $("a.decision-select-tag").click ->
    tagNames = []
    $("input[name='tag-list']:checked").each ->
      tagNames.push $(this).attr "id"
      return
    $("span.selected-tag").empty()
    i = 0
    while i < tagNames.length
      $("script#selected-tags-tmpl").tmpl
        tagName: tagNames[i]
      .prependTo "span.selected-tag"
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
      $("input[id='#{selectedTags[i]}']").prop "checked", true
      i++
    return

  $("input.post").click ->
    selectedTagField = $("span.selected-tag").html()
    localStorage.setItem "selectedTagField", selectedTagField
    return

  selectedTagField = localStorage.getItem "selectedTagField"
  if selectedTagField?
    selectedTag = $("span.selected-tag")
    selectedTag.empty()
    selectedTag.append selectedTagField
    localStorage.removeItem "selectedTagField"

  $("input#tagName").autocomplete source: (request, response) ->
    $.ajax
      url: "/youScene/tags/list"
      type: "GET"
      dataType: "json"
      data:
        term: request.term
    .done (data) ->
      response $.map data,(item) ->
        label: item.tagName
        value: item.tagName
      return
    return

  #画像アップロード
  $("a.btn-upload-image").click ->
    $("input#image").click()
    false;

  $("input#image").change ->
    formData = new FormData()
    formData.append "image", $("input#image").prop("files")[0]
    $.ajax
      type: "POST"
      url: $("a.btn-upload-image").attr "url"
      data: formData
      processData: false
      contentType: false
    .done (html) ->
      $("div.upload-list").prepend html
    .fail ->
      alert "アップロードに失敗しました"
    return

  $("div.upload-list").on "click", "a.image-delete", ->
    image_id = $(this).attr "image_id"
    formData = new FormData()
    console.log image_id
    formData.append "id", image_id
    $.ajax
      type: "POST"
      url: $(this).attr "url"
      data: formData
      processData: false
    .done () ->
      $("div.image-id-#{image_id}").remove()
    .fail ->
      alert "画像の削除に失敗しました"
    return

  $("div.upload-list").on "dblclick", "a.thumbnail", ->
    $("textarea#article").selection "insert",
      text: $(this).html()
      mode: "before"
    return

  $("div.upload-list").on "click", "a.image-insert-article", ->
    image_id = $(this).attr "image_id"
    $("textarea#article").selection "insert",
      text: $("a#image_id_#{image_id}").html()
      mode: "before"
    return

  #プレビュー
  $("a.preview").click ->
    title = $("input#title").val()
    article = $("textarea#article").val()
    $("div.preview").empty()
    $("div.preview").append "<div class=\"show-title\">#{title}</div>"
    $("div.preview").append "<pre class=\"show-article\">#{article}</pre>"
    return

return
